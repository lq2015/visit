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
import com.miaxis.visit.entity.UnitPact;

/**
 * 服务合同管理
 * 
 * @author liu.qiao
 * 
 */

@Controller
@RequestMapping("/unitPact")
public class UnitPactController extends CommonController {
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

		List list = commonService.getPageList(UnitPact.class,pageConfig,qc);
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
				"WEB-INF/pages/visit/unitPact/pactDetail");
		if (operationType.equals("edit")) {
			UnitPact unitPact = commonService.get(UnitPact.class, id);
			mav.getModelMap().put("unitPact", unitPact);
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
		UnitPact unitPact = (UnitPact) commonService.get(UnitPact.class, id);

		if (unitPact == null) {
			return this.buidMessageMap("该合同信息不存在!", "1");
		} else {
			try {
				commonService.delete(unitPact);
			} catch (Exception e) {
				return this.buidMessageMap("保存失败了", "1");
			}
			return this.buidMessageMap("合同【"+unitPact.getUpNumber()+"】信息删除成功!",	"0");
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
	public Map save(UnitPact unitPact, String operationType) {
		String msg = operationType.equals("edit") ? "修改" : "新增";
		try {
			if (operationType.equals("edit")) {
				String message = "";
				UnitPact r = (UnitPact) commonService.get(UnitPact.class, unitPact.getId());
				
				if (r == null) {
					return this.buidMessageMap("该合同信息不存在!", "1");
				} else {
					commonService.updateEntitie(unitPact);
				}
			} else {
				commonService.save(unitPact);
			}
		} catch (Exception e) {
			return this.buidMessageMap(msg + "合同失败", "1");
		}

		return this.buidMessageMap(msg + "合同成功", "0");
	}
	
}