package com.miaxis.visit.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.miaxis.common.base.CommonController;
import com.miaxis.common.exception.BusinessException;
import com.miaxis.common.util.PageConfig;
import com.miaxis.common.util.QueryCondition;
import com.miaxis.visit.entity.ServeCategory;
import com.miaxis.visit.service.ServeCategoryService;

/**
 * 服务类别
 * 
 * @author liu.qiao
 * 
 */

@Controller
@RequestMapping("/serveCategory")
public class ServeCategoryController extends CommonController {
	@Autowired
	public ServeCategoryService serveCategoryService;
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
		ModelAndView mav = this.getModelMainMav("WEB-INF/pages/visit/serveCategory/list");
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
			String qScParent, Integer qScLevel) {

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

		if (StringUtils.isNotEmpty(qScParent)) {
			qc.like("scParent", qScParent);
		}
		
		if(qScLevel!=null){
			qc.eq("scLevel",qScLevel);
		}

		List list = commonService.getPageList(ServeCategory.class, pageConfig, qc);
		return this.buidResultMap(list, list.size());
	}

	/**
	 * 修改或新增
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "insertOrUpdate")
	public ModelAndView insertOrUpdate(String id, String operationType) {
		ModelAndView mav = new ModelAndView("WEB-INF/pages/visit/serveCategory/detail");
		if (operationType.equals("edit")) {
			ServeCategory serveCategory = commonService.get(ServeCategory.class, id);
			mav.getModelMap().put("serveCategory", serveCategory);
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
			serveCategoryService.deleteServeCategory(id);
		} catch (BusinessException e) {
			return this.buidMessageMap(e.getMessage() , "1");
		} catch (Exception e) {
			e.printStackTrace();
			return this.buidMessageMap("保存失败了", "1");
		}
		return this.buidMessageMap("服务类别信息删除成功!", "0");
	}

	/**
	 * 保存
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public Map save(ServeCategory serveCategory, String operationType) {
		String msg = operationType.equals("edit") ? "修改" : "新增";
		try {
			if (operationType.equals("edit")) {
				serveCategoryService.updateServeCategory(serveCategory);
			} else {
				serveCategoryService.addServeCategory(serveCategory);
			}
		} catch (BusinessException e) {
			return this.buidMessageMap(e.getMessage() , "1");
		} catch (Exception e) {
			e.printStackTrace();
			return this.buidMessageMap(msg + "服务类别失败", "1");
		}

		return this.buidMessageMap(msg + "服务类别成功", "0");
	}
	
	
	/**
	 * 获取列表
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "getParent")
	@ResponseBody
	public List getParent() {
		String  hql = " from ServeCategory where  scLevel = 1" ;
		List<ServeCategory> list = commonService.getListByHql(ServeCategory.class, hql);
		return list;
	}
}