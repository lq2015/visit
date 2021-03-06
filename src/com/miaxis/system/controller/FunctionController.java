package com.miaxis.system.controller;

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
import com.miaxis.system.entity.Function;

/**
 * 系统功能点功能点
 * 
 * @author liu.qiao
 * 
 */

@Controller
@RequestMapping("/function")
public class FunctionController extends CommonController {
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

		List list = commonService.getPageList(Function.class,pageConfig,qc);
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
				"WEB-INF/pages/system/menu/functionDetail");
		if (operationType.equals("edit")) {
			Function function = commonService.get(Function.class, id);
			mav.getModelMap().put("function", function);
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
		Function function = (Function) commonService.get(Function.class, id);

		if (function == null) {
			return this.buidMessageMap("该功能点信息不存在!", "1");
		} else {
			try {
				commonService.delete(function);
			} catch (Exception e) {
				return this.buidMessageMap("保存失败了", "1");
			}
			return this.buidMessageMap("功能点【"+function.getFunNameCn()+"】信息删除成功!",	"0");
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
	public Map save(Function function, String operationType) {
		String msg = operationType.equals("edit") ? "修改" : "新增";
		try {
			if (operationType.equals("edit")) {
				String message = "";
				Function r = (Function) commonService.get(Function.class, function.getId());
				
				if (r == null) {
					return this.buidMessageMap("该功能点信息不存在!", "1");
				} else {
					function.setFunUrl(function.getFunUrl().replace("\"", "'"));
					commonService.updateEntitie(function);
				}
			} else {
				function.setFunUrl(function.getFunUrl().replace("\"", "'"));
				commonService.save(function);
			}
		} catch (Exception e) {
			return this.buidMessageMap(msg + "功能点失败", "1");
		}

		return this.buidMessageMap(msg + "功能点成功", "0");
	}
	
}