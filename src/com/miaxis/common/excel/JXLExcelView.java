package com.miaxis.common.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.web.servlet.view.document.AbstractJExcelView;


/**
 * jxl导出方式View
 *   Controller层调用示例：
 * 		Map model = new HashMap();
 * 		model.put("fileName", "用户信息");	//定义导出文件名	
 * 		model.put("list", users);	//定义导出list	
 *      model.put("columns", new String[] { "id", "name", "age", "sex","password", "address" }); //字义List中Bean字段属性
 *		model.put("titles", new String[] { "编号", "姓名", "年龄", "性别", "密码","地址" }); //定义导出的中文字段
 *		model.put("widths", new Integer[] { 20, 20, 20, 20,20,20}); //定义导出的中文字段宽度
 *      return new ModelAndView(new JXLExcelView(), model);
 * @author liu.qiao
 *
 */
public class JXLExcelView extends AbstractJExcelView {
	/**
	 * excel文件名
	 */
	private String fileName;
	/**
	 * excel字段名
	 */
	private String[] columnNames;
	/**
	 * Bena对象字段属性名
	 */
	private String[] dbColumnNames;
	/**
	 * excel字段宽度
	 */
	private Integer[] columnWidths;
	
	
	@Override
	public void buildExcelDocument(Map<String, Object> map,
			WritableWorkbook work, HttpServletRequest req,
			HttpServletResponse response) {
		
		String[] titles = (String[]) map.get("titles");
		if (null != titles && titles.length > 0) {
			this.columnNames = titles;
		}else{
			System.out.println("没有定义需要导出的titles");
		}
		
		String[] columns = (String[]) map.get("columns");
		if (null != columns && columns.length > 0) {
			this.dbColumnNames = columns;
		}else{
			System.out.println("没有定义需要导出的columns");
		}
		
		Integer[] widths = (Integer[]) map.get("widths");
		if (null != widths && widths.length > 0) {
			this.columnWidths = widths;
		}
		
		String name = (String) map.get("fileName");
		if(null!= name){
			this.fileName = name;
		}else{
			this.fileName ="导出文件";
		}

		OutputStream os = null;
		try {
			String excelName = this.fileName + ".xls";
			// 设置response方式,使执行此controller时候自动出现下载页面,而非直接使用excel打开
			response.setContentType("APPLICATION/OCTET-STREAM");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ URLEncoder.encode(excelName, "UTF-8"));
			os = response.getOutputStream();
			
			// 全局设置
			WorkbookSettings setting = new WorkbookSettings();
			java.util.Locale locale = new java.util.Locale("zh", "CN");
			setting.setLocale(locale);
			setting.setEncoding("ISO-8859-1");
			
			// 创建工作薄
			work = Workbook.createWorkbook(os); // 建立excel文件
			
			// sheet名称
			String sheetName = this.fileName;
			List list = (List) map.get("list");
			createSheet(work,list,sheetName);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			// 写入文件
			try {
				work.write();
				work.close();
				os.flush();
				os.close();
			} catch (WriteException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}
	
	/**********************************************************************
	 * 创建Sheet
	 * @param work
	 * @param list
	 * @param sheetName
	 *********************************************************************/
	private <T> void createSheet(WritableWorkbook work, List<T> list , String sheetName) {
		int pageRowIndex=0;
		jxl.write.WritableSheet ws = null;
        
		try {
			int pageNum =1; //页号
			int totalSize = list.size();
			int countPerPage = 10000; //每页显示几条
			
			// 创建第一个工作表
			ws = work.createSheet(sheetName+pageNum, pageNum); // sheet名称
			// 添加标题
			addColumNameToWsheet(ws);
			
			for (int i = 1; i <= totalSize; i++) {
				if ((i >= (pageNum - 1) * countPerPage) && (i <= pageNum * countPerPage)) {
					pageRowIndex++;
					//添加内容
					jxl.write.Label wlabel = null;
					jxl.write.WritableCellFormat wcf = getFormat();
					int cols = dbColumnNames.length;
					String columnName = null;
					Object value = null;
					
					T t = (T) list.get(i-1);
					for (int j = 0; j < cols; j++) {
						columnName = dbColumnNames[j];
						value = PropertyUtils.getProperty(t, columnName);
						wlabel = new jxl.write.Label(j, (pageRowIndex), value + "", wcf);
						wlabel = new jxl.write.Label(j, (pageRowIndex), value + "");
						ws.addCell(wlabel);
					}
					
					if(i%countPerPage==0){
						pageNum++;
						pageRowIndex=0;
						
						// 创建第一个工作表
						ws = work.createSheet(sheetName+pageNum, pageNum); // sheet名称
						// 添加标题
						addColumNameToWsheet(ws);
					}
				}
			}
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**********************************************************************
	 * 添加标题样式
	 * @param wsheet
	 * @throws RowsExceededException
	 * @throws WriteException
	 **********************************************************************/
	private void addColumNameToWsheet(jxl.write.WritableSheet wsheet)
			throws RowsExceededException, WriteException {

		// 设置excel标题
		jxl.write.WritableFont wfont = getFont();
		if (null == wfont) {
			wfont = new WritableFont(WritableFont.ARIAL,
					WritableFont.DEFAULT_POINT_SIZE, WritableFont.BOLD);

		}
		jxl.write.WritableCellFormat wcfFC = getFormat();
		if (null == wcfFC) {
			wcfFC = new jxl.write.WritableCellFormat(wfont);
			try {
				wcfFC.setWrap(true);// 自动换行
				wcfFC.setAlignment(Alignment.CENTRE);
				wcfFC.setVerticalAlignment(VerticalAlignment.CENTRE);// 设置对齐方式
			} catch (WriteException e) {
				e.printStackTrace();
			}
		}

		jxl.write.Label wlabel1 = null;
		String[] columNames = columnNames;
		if (null == columNames)
			return;
		int colSize = columNames.length;

		Integer[] colsWidth = columnWidths;
		if (null == colsWidth) {
			colsWidth = new Integer[colSize];
			for (int i = 0; i < colSize; i++) {
				colsWidth[i] = 20;
			}
		}

		int temp = 0;
		String colName = null;
		for (int i = 0; i < colSize; i++) {
			colName = columNames[i];
			if (null == colName || "".equals(colName))
				colName = "";
			wlabel1 = new jxl.write.Label(i, 0, colName, wcfFC);
			wsheet.addCell(wlabel1);
			temp = colsWidth[i].intValue();
			// 默认设置列宽
			temp = temp == 0 ? 20 : temp;
			wsheet.setColumnView(i, temp);
		}

	}

	/**********************************************************************
	 * 设置格式
	 * @return
	 **********************************************************************/
	private WritableCellFormat getFormat() {

		jxl.write.WritableFont wfont = getFont();
		jxl.write.WritableCellFormat wcfFC = new jxl.write.WritableCellFormat(
				wfont);
		try {
			wcfFC.setWrap(true);
			wcfFC.setAlignment(Alignment.CENTRE);
			wcfFC.setVerticalAlignment(VerticalAlignment.CENTRE);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return wcfFC;
	}

	/**********************************************************************
	 * 设置字体
	 * @return
	 *********************************************************************/
	private WritableFont getFont() {
		return new WritableFont(WritableFont.ARIAL,
				WritableFont.DEFAULT_POINT_SIZE, WritableFont.BOLD);
	}
}
