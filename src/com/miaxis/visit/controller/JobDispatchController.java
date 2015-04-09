package com.miaxis.visit.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.miaxis.common.util.DateUtil;
import com.miaxis.common.util.PageConfig;
import com.miaxis.common.util.QueryCondition;
import com.miaxis.system.entity.User;
import com.miaxis.visit.entity.BankInfo;
import com.miaxis.visit.entity.GradeDetail;
import com.miaxis.visit.entity.GradeMaster;
import com.miaxis.visit.entity.JobBillPic;
import com.miaxis.visit.entity.JobDispatch;
import com.miaxis.visit.entity.PersonInfo;
import com.miaxis.visit.entity.UnitInfo;
import com.miaxis.visit.entity.UnitPact;
import com.miaxis.visit.entity.UnitPactPic;
import com.miaxis.visit.service.JobDispatchService;
import com.miaxis.visit.service.PublicService;

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
			String qJdJobBank, String qJdUnit ,String qJdStatus) {

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

		if (StringUtils.isNotEmpty(qJdUnit)) {
			qc.eq("unit.id", Integer.parseInt(qJdUnit));
		}
		if (StringUtils.isNotEmpty(qJdJobBank)) {
			qc.eq("bankInfo.id", qJdJobBank);
		}

		if (StringUtils.isNotEmpty(qJdStatus)) {
			qc.eq("jdStatus", qJdStatus);
		}

		List list = commonService.getPageList(JobDispatch.class, pageConfig, qc);
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
		
		/****************************************************
		 * applyId 值从服务申请时派工
		 ***************************************************/
		String applyId = request.getParameter("applyId");

		try {
			if (operationType.equals("edit")) {
				jobDispatchService.updateJobDispatch(jobDispatch);
			} else {
				jobDispatchService.addJobDispatch(jobDispatch,applyId);
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
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "grade")
	public ModelAndView grade(String jobId) {
		List<GradeDetail> list = null;
		GradeMaster gradeMaster = null;
		String masterId ="";
		String describe = "";
		String suggest = "";
		Integer score =null ;
		Integer s1=null,s2=null,s3=null,s4=null,s5=null,s6=null,s7=null,s8=null,s9=null,s10=null;
		
		if(jobId==null) jobId="";
		if(!jobId.equals("")){
			List<GradeMaster> masterList = commonService.getListByHql(GradeMaster.class, "from GradeMaster where gmJobId="+jobId);
			if(masterList.size()>0){
				gradeMaster = masterList.get(0);
			}
			if(gradeMaster!=null){
				masterId = gradeMaster.getId().toString();
				describe = gradeMaster.getGmDescribe();
				suggest = gradeMaster.getGmSuggest();
				score = gradeMaster.getGmScore();
				
				list = commonService.getListByHql(GradeDetail.class, "from GradeDetail where gdMasterId="+masterId);
				
				for(GradeDetail gradeDetail:list){
					if(gradeDetail.getGdGradeItem().equals("s1")){
						s1 = gradeDetail.getGdScore();
					}
					if(gradeDetail.getGdGradeItem().equals("s2")){
						s2 = gradeDetail.getGdScore();
					}
					if(gradeDetail.getGdGradeItem().equals("s3")){
						s3 = gradeDetail.getGdScore();
					}
					if(gradeDetail.getGdGradeItem().equals("s4")){
						s4 = gradeDetail.getGdScore();
					}
					if(gradeDetail.getGdGradeItem().equals("s5")){
						s5 = gradeDetail.getGdScore();
					}
					if(gradeDetail.getGdGradeItem().equals("s6")){
						s6 = gradeDetail.getGdScore();
					}
					if(gradeDetail.getGdGradeItem().equals("s7")){
						s7 = gradeDetail.getGdScore();
					}
					if(gradeDetail.getGdGradeItem().equals("s8")){
						s8 = gradeDetail.getGdScore();
					}
					if(gradeDetail.getGdGradeItem().equals("s9")){
						s9 = gradeDetail.getGdScore();
					}
					if(gradeDetail.getGdGradeItem().equals("s10")){
						s10 = gradeDetail.getGdScore();
					}
				}
			}
		}
		
		ModelAndView mav = this.getModelMainMav("WEB-INF/pages/visit/job/grade");
		mav.getModelMap().put("masterId", masterId);
		mav.getModelMap().put("jobId", jobId);
		mav.getModelMap().put("s1", s1);
		mav.getModelMap().put("s2", s2);
		mav.getModelMap().put("s3", s3);
		mav.getModelMap().put("s4", s4);
		mav.getModelMap().put("s5", s5);
		mav.getModelMap().put("s6", s6);
		mav.getModelMap().put("s7", s7);
		mav.getModelMap().put("s8", s8);
		mav.getModelMap().put("s9", s9);
		mav.getModelMap().put("s10", s10);
		mav.getModelMap().put("score", score);
		mav.getModelMap().put("describe", describe);
		mav.getModelMap().put("suggest",suggest);
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
	public Map gradeSubmit(HttpServletRequest request) {
		Integer jobId = Integer.parseInt(request.getParameter("jobId"));
		String s1 = request.getParameter("s1");
		String s2 = request.getParameter("s2");
		String s3 = request.getParameter("s3");
		String s4 = request.getParameter("s4");
		String s5 = request.getParameter("s5");
		String s6 = request.getParameter("s6");
		String s7 = request.getParameter("s7");
		String s8 = request.getParameter("s8");
		String s9 = request.getParameter("s9");
		String s10 = request.getParameter("s10");
		String describe = request.getParameter("describe");
		String suggest = request.getParameter("suggest");

		try {
			jobDispatchService.gradeSubmit(jobId, s1, s2, s3, s4, s5, s6, s7,
					s8, s9, s10, describe, suggest);
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
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(Integer id) {
		ModelAndView mav = this.getModelMainMav("WEB-INF/pages/visit/job/upload");
		
		List<JobBillPic> list = commonService.getListByHql(JobBillPic.class, "from JobBillPic where jbJobId="+id);
		mav.getModelMap().put("billPicList", list);
		
		mav.getModelMap().put("jobId", id);
		return mav;
	}

	/**
	 * 提交维修单保存
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "uploadFiles")
	@ResponseBody
	public Map uploadFiles(@RequestParam(value = "files", required = false) MultipartFile[] files,
			HttpServletRequest request) {
		String jobId = request.getParameter("jobId");
		try {
			ArrayList list = new ArrayList();
			for(int i=0;i<files.length;i++){
				String fileName = files[i].getOriginalFilename();
				if(fileName==null) fileName="";
				if(fileName.equals("")) continue;
				
				Map<String, String> map = publicService.uploadPic(files[i], null, 
						CommonUtil.JOB, false);
				list.add(map);
			}
			
			jobDispatchService.uploadFiles(list,Integer.parseInt(jobId));
		} catch (BusinessException e) {
			return this.buidMessageMap(e.getMessage(), "1");
		} catch (Exception e) {
			e.printStackTrace();
			return this.buidMessageMap("上传维修单失败", "1");
		}

		return this.buidMessageMap("上传维修单成功", "0");
	}
	
	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "delJobPic", method = RequestMethod.POST)
	@ResponseBody
	public Map delJobPic(String id) {
		try {
			JobBillPic jobBillPic = commonService.get(JobBillPic.class, Integer.parseInt(id));
			if(jobBillPic==null){
				return this.buidMessageMap("删除的工作单记录不存在!", "1");
			}else{
				commonService.delete(jobBillPic);
			}
		} catch (Exception e) {
			return this.buidMessageMap("保存失败了", "1");
		}
		return this.buidMessageMap("工作单信息删除成功!", "0");
	}

	/**
	 * 打印介绍信
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "printLetter")
	public ModelAndView printLetter(Integer id) {
		String personName = "";
		String personIdnum = "";
		String jobBank = "";
		Integer personNum = null;
		String jobDate = "";
		String jobContent = "";

		JobDispatch job = commonService.get(JobDispatch.class, id);
		if (job != null) {
			String ids = job.getJdPersonIds();
			personNum = ids.split(",").length;
			if (personNum > 0) {
				String personId = ids.split(",")[0];
				PersonInfo person = commonService.get(PersonInfo.class,
						Integer.parseInt(personId));
				if (person != null) {
					personIdnum = person.getPiIdnum();
					personName = person.getPiName();
				}
			}

			BankInfo bank = job.getBankInfo();
			if (bank != null) {
				jobBank = bank.getBiName();
			}

			jobDate = DateUtil.getYYYYMMDD_CN(new Date());
			jobContent = job.getJdJobContent();
		}

		ModelAndView mav = this
				.getModelMainMav("WEB-INF/pages/visit/job/printLetter");
		mav.getModelMap().put("id", id);
		mav.getModelMap().put("personName", personName);
		mav.getModelMap().put("personIdnum", personIdnum);
		mav.getModelMap().put("personNum", personNum);
		mav.getModelMap().put("jobBank", jobBank);
		mav.getModelMap().put("jobContent", jobContent);
		mav.getModelMap().put("jobDate", jobDate);

		return mav;
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
	 * 
	 * @param unit
	 * @return
	 */
	@RequestMapping(params = "selectPerson")
	public ModelAndView selectPerson(Integer unitId) {
		ModelAndView mav = this
				.getModelMainMav("WEB-INF/pages/visit/job/selectPerson");
		mav.getModelMap().put("unitId", unitId);
		return mav;
	}
}