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
import com.miaxis.common.util.DateUtil;
import com.miaxis.common.util.PageConfig;
import com.miaxis.common.util.QueryCondition;
import com.miaxis.system.entity.User;
import com.miaxis.visit.entity.DepartmentInfo;
import com.miaxis.visit.entity.JobApply;
import com.miaxis.visit.service.JobApplyService;

/**
 * 派工申请
 * 
 * @author liu.qiao
 * 
 */

@Controller
@RequestMapping("/jobApply")
public class JobApplyController extends CommonController {
	
	@Autowired
	public JobApplyService jobApplyService;
	
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
		ModelAndView mav = this.getModelMainMav("WEB-INF/pages/visit/jobApply/list");
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
			String qJaStatus, String qJaJobBank) {

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

		if (StringUtils.isNotEmpty(qJaJobBank)) {
			qc.eq("bankInfo.id", qJaJobBank);
		}
		if (StringUtils.isNotEmpty(qJaStatus)) {
			qc.eq("jaStatus", qJaStatus);
		}
		
		User user = this.getLoginUser();
		/*****************************************
		 * 如果登陆人员是总部,则对显示拥有该管理服务项目的数据
		 ******************************************/
		if(user.getPersontype().equals(User.PersonType.GENERAL.getCode())){
			String departmantId = user.getDepartmant();
			DepartmentInfo dept = commonService.get(DepartmentInfo.class, departmantId);
			if(dept!=null){
				String itemId = dept.getDiServeItemId();
				if(itemId==null) itemId="";
				qc.like("jaServeItemId", itemId.concat("%"));
			}
		}
		
		/*****************************************
		 * 如果登陆人员是支行,则对显示该支行的数据
		 ******************************************/
		if(user.getPersontype().equals(User.PersonType.BRANCH.getCode())){
			qc.like("bankInfo.id", user.getDepartmant());
		}

		List list = commonService.getPageList(JobApply.class, pageConfig, qc);
		return this.buidResultMap(list, list.size());
	}
	
	/**
	 * 服务申请
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "apply")
	public ModelAndView apply() {
		ModelAndView mav = new ModelAndView("WEB-INF/pages/visit/jobApply/apply");
		User user = this.getLoginUser();
		if(user.getPersontype().equals(User.PersonType.BRANCH.getCode())){
			mav.getModelMap().put("bankId", user.getDepartmant());
		}
		
		return mav;
	}

	/**
	 * 服务申请派工
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "applyDispatch")
	public ModelAndView applyDispatch(String id) {
		String jobBankId="";
		String jobBankName="";
		String serveItem="";
		String serveItemId="";
		String jobContent ="";
		String jobDate ="";
		
		if(id==null) id="";
		if(!id.equals("")){
			JobApply jobApply = commonService.get(JobApply.class, Integer.parseInt(id));
			if(jobApply!=null){
				jobBankId = jobApply.getBankInfo().getId();
				jobBankName = jobApply.getBankInfo().getBiName();
				serveItem = jobApply.getJaServeItem();
				serveItemId = jobApply.getJaServeItemId();
				jobContent = jobApply.getJaJobContent();
				jobDate = DateUtil.getYYYY_MM_DD(jobApply.getJaJobDate());
			}
		}
		
		ModelAndView mav = new ModelAndView("WEB-INF/pages/visit/jobApply/dispatch");
		mav.getModelMap().put("applyId", id);
		mav.getModelMap().put("jobBankId", jobBankId);
		mav.getModelMap().put("jobBankName", jobBankName);
		mav.getModelMap().put("serveItemId", serveItemId);
		mav.getModelMap().put("serveItem", serveItem);
		mav.getModelMap().put("jobContent", jobContent);
		mav.getModelMap().put("jobDate", jobDate);
		
		return mav;
	}


	/**
	 * 保存
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public Map save(JobApply jobApply, String operationType) {
		String msg = operationType.equals("edit") ? "修改" : "新增";
		try{
			jobApplyService.addJobApply(jobApply);
		} catch (BusinessException e) {
			return this.buidMessageMap(e.getMessage() , "1");
		} catch (Exception e) {
			e.printStackTrace();
			return this.buidMessageMap(msg + "派工申请失败", "1");
		}

		return this.buidMessageMap(msg + "派工申请成功", "0");
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
		
		try {
			if(status.equals("9")){
				jobApplyService.cancleJobApply(id);
			}
			if(status.equals("3")){
				jobApplyService.rejectJobApply(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return this.buidMessageMap("修改记录状态失败", "1");
		}
		return this.buidMessageMap("修改记录操作成功!","0");
	}

}