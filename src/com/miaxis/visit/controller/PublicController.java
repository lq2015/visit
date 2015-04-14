package com.miaxis.visit.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.miaxis.common.base.CommonController;
import com.miaxis.common.util.Base64Utils;
import com.miaxis.common.util.CommonUtil;
import com.miaxis.common.util.ImageUtils;
import com.miaxis.common.util.QueryCondition;
import com.miaxis.system.entity.User;
import com.miaxis.visit.entity.BankInfo;
import com.miaxis.visit.entity.DepartmentInfo;

/**
 * 业务系统公用
 * 
 * @author liu.qiao
 * 
 */

@Controller
@RequestMapping("/public")
public class PublicController extends CommonController {

	/**
	 * 获取用户管理所需的部门
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "userDept")
	@ResponseBody
	public List userDept(String persontype) {
		ArrayList list = new ArrayList();
		if(persontype.equals("1")){
			List<DepartmentInfo> list1 = (List<DepartmentInfo>) commonService
					.getPageList(DepartmentInfo.class, null, null);
			
			for(DepartmentInfo departmentInfo :list1){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", departmentInfo.getId());
				map.put("name", departmentInfo.getDiName());
				list.add(map);
			}
		}
		
		if(persontype.equals("2")){
			List<BankInfo> list2 = (List<BankInfo>) commonService
					.getPageList(BankInfo.class, null, null);
			
			for(BankInfo bankInfo :list2){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", bankInfo.getId());
				map.put("name", bankInfo.getBiName());
				list.add(map);
			}
		}

		return list;
	}
	
	/**
	 * 截图
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "operateImage", method = RequestMethod.POST)
	@ResponseBody
	public Map operateImage(HttpServletRequest request) {
		String pic_data2 = request.getParameter("pic_data");
		String pic_data = request.getParameter("pic_data");
		String pic_width = request.getParameter("pic_width");
		String pic_height = request.getParameter("pic_height");
		String pic_x = request.getParameter("pic_x");
		String pic_y = request.getParameter("pic_y");

		/****************************************************
		 * 得到临时文件
		 ****************************************************/
		String path = CommonUtil.UPLOAD_TEMP;
		String realPath = request.getSession().getServletContext()
				.getRealPath(path); // 绝对路径
		String fileName = CommonUtil.fileNameGenerator("jpg");
		String imgFile = realPath + "/" + fileName;

		// 如果文件夹不存在则创建
		File file = new File(realPath);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		}
		
		if(!pic_data.equals("")){
			/****************************************************
			 * 1.根据Base64数据生成图片文件
			 * 2.执行裁剪操作 执行完后即可生成目标图在对应文件夹内
			 ****************************************************/
			if (Base64Utils.GenerateImage(pic_data, imgFile)) {
				int _width = Integer.parseInt(pic_width);
				int _height = Integer.parseInt(pic_height);
				int _x = Integer.parseInt(pic_x);
				int _y = Integer.parseInt(pic_y);
				try {
					ImageUtils.cut(imgFile, imgFile,_x,_y, _width,_height);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			/****************************************************
			 * 1.重新获取截图后的文件Base64 2.删除临时文件
			 ****************************************************/
			pic_data = "";
			if(imgFile==null) imgFile="";
			if(!imgFile.equals("")){
				pic_data = Base64Utils.GetImageStr(imgFile);
				file = new File(imgFile);
				if (file.isFile() && file.exists()) {
					file.delete();
				}

			}
		}

		/****************************************************
		 * 1.回传修剪后的图像数据
		 ****************************************************/
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("photoData", pic_data);
		return jsonMap;
	}
}