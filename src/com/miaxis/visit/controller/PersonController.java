package com.miaxis.visit.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.miaxis.common.base.CommonController;
import com.miaxis.common.util.PageConfig;
import com.miaxis.common.util.QueryCondition;
import com.miaxis.visit.entity.PersonInfo;

/**
 * 访客人员管理
 * 
 * @author liu.qiao
 * 
 */

@Controller
@RequestMapping("/person")
public class PersonController extends CommonController {
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
		if(StringUtils.isNotEmpty(sort)){
			if (StringUtils.equals(order.toUpperCase(), QueryCondition.DESC)){
				qc.desc(sort);
			}else{
				qc.asc(sort);
			}
		}
		qc.asc("orderNum");
		
		if (StringUtils.isNotEmpty(parentMenu)) {
			qc.eq("parentMenu", parentMenu);
		}else{
			return null;
		}

		List list = commonService.getPageList(PersonInfo.class,pageConfig,qc);
		return this.buidResultMap(list,list.size());
	}


	/**
	 * 修改或新增
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "insertOrUpdate")
	public ModelAndView insertOrUpdate(String id, String operationType) {
		ModelAndView mav = new ModelAndView(
				"WEB-INF/pages/visit/person/detail");
		if (operationType.equals("edit")) {
			PersonInfo personInfo = commonService.get(PersonInfo.class, id);
			mav.getModelMap().put("personInfo", personInfo);
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
		PersonInfo personInfo = (PersonInfo) commonService.get(PersonInfo.class, id);

		if (personInfo == null) {
			return this.buidMessageMap("该人员信息不存在!", "1");
		} else {
			try {
				commonService.delete(personInfo);
			} catch (Exception e) {
				return this.buidMessageMap("保存失败了", "1");
			}
			return this.buidMessageMap("人员【"+personInfo.getPiName()+"】信息删除成功!",	"0");
		}
	}

	/**
	 * 保存
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public Map save(PersonInfo personInfo, String operationType) {
		String msg = operationType.equals("edit") ? "修改" : "新增";
		try {
			if (operationType.equals("edit")) {
				String message = "";
				PersonInfo r = (PersonInfo) commonService.get(PersonInfo.class, personInfo.getId());
				
				if (r == null) {
					return this.buidMessageMap("该人员信息不存在!", "1");
				} else {
					commonService.updateEntitie(personInfo);
				}
			} else {
				commonService.save(personInfo);
			}
		} catch (Exception e) {
			return this.buidMessageMap(msg + "人员失败", "1");
		}

		return this.buidMessageMap(msg + "人员成功", "0");
	}
	
}