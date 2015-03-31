package com.miaxis.visit.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.miaxis.common.base.CommonController;
import com.miaxis.common.exception.BusinessException;
import com.miaxis.common.util.CommonUtil;
import com.miaxis.common.util.PageConfig;
import com.miaxis.common.util.QueryCondition;
import com.miaxis.visit.entity.ServeCategory;
import com.miaxis.visit.entity.UnitPact;
import com.miaxis.visit.entity.UnitPactPic;
import com.miaxis.visit.service.PublicService;
import com.miaxis.visit.service.UnitPactService;

/**
 * 访客单位服务合同管理
 * 
 * @author liu.qiao
 * 
 */

@Controller
@RequestMapping("/unitPact")
public class UnitPactController extends CommonController {
	@Autowired
	public UnitPactService unitPactService;
	@Autowired
	public PublicService publicService;

	/**
	 * 主页
	 * 
	 * @param role
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "main")
	public ModelAndView main(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = this
				.getModelMainMav("WEB-INF/pages/visit/unitPact/list");
		return mav;
	}

	/**
	 * 获取列表
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "list")
	@ResponseBody
	public Map list(String page, String sort, String order, String rows,
			String parentMenu) {

		/**
		 * 初始化分页对象
		 */
		PageConfig pageConfig = new PageConfig();
		pageConfig.setPaging(false);

		QueryCondition qc = new QueryCondition();
		if (StringUtils.isNotEmpty(sort)) {
			if (StringUtils.equals(order.toUpperCase(), QueryCondition.DESC)) {
				qc.desc(sort);
			} else {
				qc.asc(sort);
			}
		}
		qc.asc("id");

		List list = commonService.getPageList(UnitPact.class, pageConfig, qc);
		return this.buidResultMap(list, list.size());
	}

	/**
	 * 修改或新增
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "insertOrUpdate")
	public ModelAndView insertOrUpdate(Integer id, String operationType) {
		ModelAndView mav = new ModelAndView(
				"WEB-INF/pages/visit/unitPact/detail");
		if (operationType.equals("edit")) {
			UnitPact unitPact = commonService.get(UnitPact.class, id);
			List<UnitPactPic> list = commonService.getListByHql(UnitPactPic.class, "from UnitPactPic where upPactId="+id);
			mav.getModelMap().put("unitPact", unitPact);
			mav.getModelMap().put("pactPicList", list);
			mav.getModelMap().put("operationType", "edit");
		} else {
			mav.getModelMap().put("operationType", "insert");
		}

		return mav;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "del", method = RequestMethod.POST)
	@ResponseBody
	public Map del(String id) {
		try {
			unitPactService.deleUnitPact(Integer.parseInt(id));
		} catch (BusinessException e) {
			return this.buidMessageMap(e.getMessage(), "1");
		} catch (Exception e) {
			return this.buidMessageMap("保存失败了", "1");
		}
		return this.buidMessageMap("服务合同信息删除成功!", "0");
	}

	/**
	 * 保存
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public Map save(
			@RequestParam(value = "files", required = false) MultipartFile[] files,
			UnitPact unitPact, String operationType) {
		String msg = operationType.equals("edit") ? "修改" : "新增";
		
		try {
			ArrayList list = new ArrayList();
			for(int i=0;i<files.length;i++){
				Map<String, String> map = publicService.uploadPic(files[i], null, 
						CommonUtil.UNIT_PACT, false);
				list.add(map);
			}
			
			if (operationType.equals("edit")) {
				unitPactService.updateUnitPact(unitPact,list);
			} else {
				unitPactService.addUnitPact(unitPact,list);
			}
		} catch (BusinessException e) {
			return this.buidMessageMap(e.getMessage(), "1");
		} catch (Exception e) {
			e.printStackTrace();
			return this.buidMessageMap(msg + "服务合同失败", "1");
		}

		return this.buidMessageMap(msg + "服务合同成功", "0");
	}

	/**
	 * 修改记录状态
	 * 
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping(params = "updateStatus", method = RequestMethod.POST)
	@ResponseBody
	public Map updateStatus(Integer id, String status) {
		try {
			unitPactService.updateStatus(id, status);
		} catch (BusinessException e) {
			return this.buidMessageMap(e.getMessage(), "1");
		} catch (Exception e) {
			e.printStackTrace();
			return this.buidMessageMap("修改记录状态失败", "1");
		}
		return this.buidMessageMap("修改记录操作成功!", "0");
	}
	
	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "delPactPic", method = RequestMethod.POST)
	@ResponseBody
	public Map delPactPic(String id) {
		try {
			UnitPactPic unitPactPic = commonService.get(UnitPactPic.class, Integer.parseInt(id));
			if(unitPactPic==null){
				return this.buidMessageMap("删除的合同附件记录不存在!", "1");
			}else{
				commonService.delete(unitPactPic);
			}
		} catch (Exception e) {
			return this.buidMessageMap("保存失败了", "1");
		}
		return this.buidMessageMap("服务合同信息删除成功!", "0");
	}

	/**
	 * 获取服务类别树
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "getServeItem")
	@ResponseBody
	public ArrayList getServeItem() {

		// 得到第一级分类信息
		String hql = " from ServeCategory where scLevel=1";
		List<ServeCategory> firstLevelList = commonService.getListByHql(
				ServeCategory.class, hql);

		// 得到第二级分类信息
		String hql2 = " from ServeCategory where scLevel=2";
		List<ServeCategory> secondLevelList = commonService.getListByHql(
				ServeCategory.class, hql2);

		// 组合树格式
		ArrayList parentList = new ArrayList();
		for (ServeCategory serveCategory1 : firstLevelList) {
			ArrayList chirldList = new ArrayList();
			Map<String, Object> firstMap = new HashMap<String, Object>();
			firstMap.put("id", serveCategory1.getId());
			firstMap.put("text", serveCategory1.getScCategory());
			// cityMap.put("iconCls", "icon-tip");

			for (ServeCategory serveCategory2 : secondLevelList) {
				if (serveCategory2.getScParent().equals(serveCategory1.getId())) {
					Map<String, Object> secondMap = new HashMap<String, Object>();
					secondMap.put("id", serveCategory2.getId());
					secondMap.put("text", serveCategory2.getScCategory());
					chirldList.add(secondMap);
				}
			}
			if (chirldList.size() > 0) {
				firstMap.put("children", chirldList);
			}
			parentList.add(firstMap);
		}
		return parentList;
	}
}