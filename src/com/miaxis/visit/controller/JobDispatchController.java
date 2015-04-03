package com.miaxis.visit.controller;

import java.util.ArrayList;
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
import com.miaxis.common.util.PageConfig;
import com.miaxis.common.util.QueryCondition;
import com.miaxis.system.entity.User;
import com.miaxis.visit.entity.JobDispatch;
import com.miaxis.visit.entity.PersonInfo;
import com.miaxis.visit.entity.UnitInfo;
import com.miaxis.visit.service.JobDispatchService;

/**
 * 派工单管理
 * 
 * @author liu.qiao
 * 
 */

@Controller
@RequestMapping("/jobDispatch")
public class JobDispatchController extends CommonController {
	@Autowired
	public JobDispatchService jobDispatchService;

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
		ModelAndView mav = this.getModelMainMav("WEB-INF/pages/visit/job/list");
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
			String qPiWorkUnit, String qPiName) {

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

		if (StringUtils.isNotEmpty(qPiWorkUnit)) {
			qc.eq("unitInfo.id", Integer.parseInt(qPiWorkUnit));
		}

		if (StringUtils.isNotEmpty(qPiName)) {
			qc.like("piName", qPiName.concat("%"));
		}

		List list = commonService
				.getPageList(JobDispatch.class, pageConfig, qc);
		return this.buidResultMap(list, list.size());
	}

	/**
	 * 修改或新增
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "insertOrUpdate")
	public ModelAndView insertOrUpdate(Integer id, String operationType,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("WEB-INF/pages/visit/job/detail");
		User user = (User) request.getSession().getAttribute("userSession");

		if (operationType.equals("edit")) {
			JobDispatch jobDispatch = commonService.get(JobDispatch.class, id);
			mav.getModelMap().put("jobDispatch", jobDispatch);
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
	public Map del(Integer id) {

		try {
			jobDispatchService.delJobDispatch(id);
		} catch (BusinessException e) {
			return this.buidMessageMap(e.getMessage(), "1");
		} catch (Exception e) {
			return this.buidMessageMap("派工单信息删除失败!", "1");
		}
		return this.buidMessageMap("派工单信息删除成功!", "0");
	}

	/**
	 * 保存
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public Map save(JobDispatch jobDispatch, HttpServletRequest request) {
		String operationType = request.getParameter("operationType");
		String msg = operationType.equals("edit") ? "修改" : "新增";

		try {
			if (operationType.equals("edit")) {
				jobDispatchService.updateJobDispatch(jobDispatch);
			} else {
				jobDispatchService.addJobDispatch(jobDispatch);
			}
		} catch (BusinessException e) {
			return this.buidMessageMap(e.getMessage(), "1");
		} catch (Exception e) {
			e.printStackTrace();
			return this.buidMessageMap(msg + "派工单失败", "1");
		}

		return this.buidMessageMap(msg + "派工单成功", "0");
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
			jobDispatchService.updateStatus(id, status);
		} catch (BusinessException e) {
			return this.buidMessageMap(e.getMessage(), "1");
		} catch (Exception e) {
			e.printStackTrace();
			return this.buidMessageMap("修改记录状态失败", "1");
		}
		return this.buidMessageMap("修改记录操作成功!", "0");
	}
	
	/**
	 * 派工
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "dispatchJob", method = RequestMethod.POST)
	@ResponseBody
	public Map dispatchJob(Integer id) {
		try {
			jobDispatchService.dispatchJob(id);
		} catch (BusinessException e) {
			return this.buidMessageMap(e.getMessage(), "1");
		} catch (Exception e) {
			e.printStackTrace();
			return this.buidMessageMap("派工失败", "1");
		}
		return this.buidMessageMap("派工成功!", "0");
	}
	
	/**
	 * 签到
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "sign")
	public ModelAndView sign(Integer id) {
		ModelAndView mav = this.getModelMainMav("WEB-INF/pages/visit/job/sign");
		mav.getModelMap().put("id", id);
		return mav;
	}
	
	/**
	 * 签到提交
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "signSubmit", method = RequestMethod.POST)
	@ResponseBody
	public Map signSubmit(Integer id) {
		try {
			jobDispatchService.signSubmit(id);
		} catch (BusinessException e) {
			return this.buidMessageMap(e.getMessage(), "1");
		} catch (Exception e) {
			e.printStackTrace();
			return this.buidMessageMap("签到失败", "1");
		}
		return this.buidMessageMap("签到成功!", "0");
	}
	
	/**
	 * 签离
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "out", method = RequestMethod.POST)
	@ResponseBody
	public Map out(Integer id) {
		try {
			jobDispatchService.outSubmit(id);
		} catch (BusinessException e) {
			return this.buidMessageMap(e.getMessage(), "1");
		} catch (Exception e) {
			e.printStackTrace();
			return this.buidMessageMap("签离失败", "1");
		}
		return this.buidMessageMap("签离成功!", "0");
	}
	
	/**
	 * 评价
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "grade")
	public ModelAndView grade(Integer id) {
		ModelAndView mav = this.getModelMainMav("WEB-INF/pages/visit/job/grade");
		mav.getModelMap().put("id", id);
		return mav;
	}
	
	/**
	 * 评价提交
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "gradeSubmit", method = RequestMethod.POST)
	@ResponseBody
	public Map gradeSubmit(Integer id) {
		try {
			jobDispatchService.gradeSubmit(id);
		} catch (BusinessException e) {
			return this.buidMessageMap(e.getMessage(), "1");
		} catch (Exception e) {
			e.printStackTrace();
			return this.buidMessageMap("评价提交失败", "1");
		}
		return this.buidMessageMap("评价提交成功!", "0");
	}
	
	/**
	 * 上传维修单
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(Integer id) {
		ModelAndView mav = this.getModelMainMav("WEB-INF/pages/visit/job/upload");
		mav.getModelMap().put("id", id);
		return mav;
	}
	
	/**
	 * 保存
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "uploadFiles")
	@ResponseBody
	public Map uploadFiles(@RequestParam(value = "files", required = false) MultipartFile[] files ,
			PersonInfo personInfo, HttpServletRequest request) {
		return null;
	}

	/**
	 * 获取服务类关联的单位
	 * 
	 * @return
	 */
	@RequestMapping(params = "getServeUnit")
	@ResponseBody
	public List getServeUnit(String serveItem) {
		String hql = " from UnitInfo a ,UnitPact b "
				+ "where  a.uiStatus = '1' and a.id=b.upUnitId and b.upServeItem like '"
				+ serveItem + "%' ";
		List<Object[]> list = commonService.getListByHql(null, hql);

		ArrayList resultList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			Object[] objects = list.get(i);
			UnitInfo unitInfo = (UnitInfo) objects[0];
			resultList.add(unitInfo);
		}

		return resultList;
	}
	
	/**
	 * 选择人员
	 * @param unit
	 * @return
	 */
	@RequestMapping(params = "selectPerson")
	public ModelAndView selectPerson(Integer unitId) {
		ModelAndView mav = this.getModelMainMav("WEB-INF/pages/visit/job/selectPerson");
		mav.getModelMap().put("unitId", unitId);
		return mav;
	}
}