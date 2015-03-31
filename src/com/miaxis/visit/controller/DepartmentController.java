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
import com.miaxis.visit.entity.DepartmentInfo;
import com.miaxis.visit.service.DepartmentService;

/**
 * 银行部门
 * 
 * @author liu.qiao
 * 
 */

@Controller
@RequestMapping("/department")
public class DepartmentController extends CommonController {
	@Autowired
	public DepartmentService departmentService;
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
				.getModelMainMav("WEB-INF/pages/visit/dept/list");
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
			String qDiStatus, String qDiName) {

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

		if (StringUtils.isNotEmpty(qDiName)) {
			qc.like("diName", qDiName);
		}
		if (StringUtils.isNotEmpty(qDiStatus)) {
			qc.eq("diStatus", qDiStatus);
		}

		List list = commonService.getPageList(DepartmentInfo.class, pageConfig, qc);
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
		ModelAndView mav = new ModelAndView("WEB-INF/pages/visit/dept/detail");
		if (operationType.equals("edit")) {
			DepartmentInfo department = commonService.get(DepartmentInfo.class, id);
			mav.getModelMap().put("departmentInfo", department);
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
		DepartmentInfo department = (DepartmentInfo) commonService.get(DepartmentInfo.class, id);

		if (department == null) {
			return this.buidMessageMap("该部门信息不存在!", "1");
		} else {
			try {
				commonService.delete(department);
			} catch (Exception e) {
				e.printStackTrace();
				return this.buidMessageMap("保存失败了", "1");
			}
			return this.buidMessageMap("部门【" + department.getDiName()	+ "】信息删除成功!", "0");
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
	public Map save(DepartmentInfo department, String operationType) {
		String msg = operationType.equals("edit") ? "修改" : "新增";
		try {
			if (operationType.equals("edit")) {
				departmentService.updateDepartment(department);
			} else {
				departmentService.addDepartment(department);
			}
		} catch (BusinessException e) {
			return this.buidMessageMap(e.getMessage() , "1");
		} catch (Exception e) {
			e.printStackTrace();
			return this.buidMessageMap(msg + "部门失败", "1");
		}

		return this.buidMessageMap(msg + "部门成功", "0");
	}
	
	/**
	 * 修改记录状态
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping(params = "updateStatus", method = RequestMethod.POST)
	@ResponseBody
	public Map updateStatus(String id,String status) {
		DepartmentInfo department = commonService.get(DepartmentInfo.class, id);
		if (department == null) {
			return this.buidMessageMap("该记录信息不存在!", "1");
		} else {
			try {
				department.setDiStatus(status);
				commonService.updateEntitie(department);
			} catch (Exception e) {
				e.printStackTrace();
				return this.buidMessageMap("修改记录状态失败", "1");
			}
			return this.buidMessageMap("修改记录操作成功!","0");
		}
	}
}