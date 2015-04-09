package com.miaxis.visit.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.miaxis.common.base.service.CommonServiceImpl;
import com.miaxis.common.exception.BusinessException;
import com.miaxis.common.util.ContextHolderUtils;
import com.miaxis.system.entity.User;
import com.miaxis.visit.entity.BankInfo;
import com.miaxis.visit.entity.GradeDetail;
import com.miaxis.visit.entity.GradeMaster;
import com.miaxis.visit.entity.JobApply;
import com.miaxis.visit.entity.JobBillPic;
import com.miaxis.visit.entity.JobDispatch;
import com.miaxis.visit.entity.UnitInfo;
import com.miaxis.visit.entity.UnitPactPic;
import com.miaxis.visit.service.JobDispatchService;

@Service("jobDispatchService")
public class JobDispatchServiceImpl extends CommonServiceImpl implements
		JobDispatchService {

	@Override
	public void addJobDispatch(JobDispatch jobDispatch,String applyId) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		User user = (User) request.getSession().getAttribute("userSession");

		UnitInfo unitInfo = this.commonDao.get(UnitInfo.class,
				jobDispatch.getJdUnit());
		jobDispatch.setUnit(unitInfo);

		BankInfo bankInfo = this.commonDao.get(BankInfo.class,
				jobDispatch.getJdJobBank());
		jobDispatch.setBankInfo(bankInfo);

		jobDispatch.setJdOperateTime(new Date());
		jobDispatch.setUser(user);

		jobDispatch.setJdStatus(JobDispatch.Status.INPUT.getCode());
		jobDispatch.setJdIsSms(JobDispatch.IsSms.NO.getCode());
		Integer jobId = (Integer)this.commonDao.save(jobDispatch);
		
		/*****************************************************************
		 * 如果是派工是从服务申请派工，则更新申请单信息
		 ******************************************************************/
		if(applyId==null) applyId="";
		if(!applyId.equals("")){
			JobApply jobApply = this.commonDao.get(JobApply.class, Integer.parseInt(applyId));
			if(jobApply!=null){
				jobApply.setJaJobId(jobId);
				jobApply.setApproveUser(user);
				jobApply.setJaApproveTime(new Date());
				jobApply.setJaStatus(JobApply.Status.DISPATCH.getCode());
				this.commonDao.updateEntitie(jobApply);
			}
		}
	}

	@Override
	public void updateJobDispatch(JobDispatch jobDispatch) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		User user = (User) request.getSession().getAttribute("userSession");

		Integer id = jobDispatch.getId();
		JobDispatch job = this.get(JobDispatch.class, id);
		if (job == null) {
			throw new BusinessException("修改的派工单记录不存在。");
		}

		UnitInfo unitInfo = this.commonDao.get(UnitInfo.class,
				jobDispatch.getJdUnit());
		jobDispatch.setUnit(unitInfo);

		BankInfo bankInfo = this.commonDao.get(BankInfo.class,
				jobDispatch.getJdJobBank());
		jobDispatch.setBankInfo(bankInfo);

		jobDispatch.setJdOperateTime(new Date());
		jobDispatch.setUser(user);

		jobDispatch.setJdStatus(job.getJdStatus());
		jobDispatch.setJdIsSms(JobDispatch.IsSms.NO.getCode());
		this.commonDao.updateEntitie(jobDispatch);
	}

	@Override
	public void delJobDispatch(Integer id) {
		JobDispatch job = this.get(JobDispatch.class, id);
		if (job == null) {
			throw new BusinessException("删除的派工单记录不存在。");
		}

		this.commonDao.delete(job);
	}

	@Override
	public void updateStatus(Integer id, String status) {
		JobDispatch job = this.get(JobDispatch.class, id);
		if (job == null) {
			throw new BusinessException("修改的派工单记录不存在。");
		}
		job.setJdStatus(status);
		this.commonDao.updateEntitie(job);
	}

	@Override
	public void dispatchJob(Integer id) {
		JobDispatch job = this.get(JobDispatch.class, id);
		if (job == null) {
			throw new BusinessException("需要派工的派工单记录不存在。");
		}
		job.setJdStatus(JobDispatch.Status.DISPATCH.getCode());
		this.commonDao.updateEntitie(job);
	}

	@Override
	public void signSubmit(Integer id) {
		JobDispatch job = this.get(JobDispatch.class, id);
		if (job == null) {
			throw new BusinessException("需要签到的派工单记录不存在。");
		}
		job.setJdSignTime(new Date());
		job.setJdStatus(JobDispatch.Status.SIGN.getCode());
		this.commonDao.updateEntitie(job);
	}

	@Override
	public void outSubmit(Integer id) {
		JobDispatch job = this.get(JobDispatch.class, id);
		if (job == null) {
			throw new BusinessException("需要签离的派工单记录不存在。");
		}
		job.setJdOutTime(new Date());
		job.setJdStatus(JobDispatch.Status.OUT.getCode());
		this.commonDao.updateEntitie(job);
	}

	@Override
	public void uploadFiles(List list,Integer jobId) {
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			String picUrl = (String)map.get("filePath");
			String picData = (String)map.get("picData");
			
			JobBillPic jobBillPic = new JobBillPic();
			jobBillPic.setJbJobId(jobId);
			jobBillPic.setJbPic(picData);
			jobBillPic.setJbPicUrl(picUrl);
			this.commonDao.save(jobBillPic);
		}
	}

	@Override
	public void gradeSubmit(Integer jobId, String s1, String s2,
			String s3, String s4, String s5, String s6, String s7, String s8,
			String s9, String s10, String describe, String suggest) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		User user = (User) request.getSession().getAttribute("userSession");

		if (s1 == null)
			s1 = "";
		if (s2 == null)
			s2 = "";
		if (s3 == null)
			s3 = "";
		if (s4 == null)
			s4 = "";
		if (s5 == null)
			s5 = "";
		if (s6 == null)
			s6 = "";
		if (s7 == null)
			s7 = "";
		if (s8 == null)
			s8 = "";
		if (s9 == null)
			s9 = "";
		if (s10 == null)
			s10 = "";

		if (s1.equals(""))
			s1 = "0";
		if (s2.equals(""))
			s2 = "0";
		if (s3.equals(""))
			s3 = "0";
		if (s4.equals(""))
			s4 = "0";
		if (s5.equals(""))
			s5 = "0";
		if (s6.equals(""))
			s6 = "0";
		if (s7.equals(""))
			s7 = "0";
		if (s8.equals(""))
			s8 = "0";
		if (s9.equals(""))
			s9 = "0";
		if (s10.equals(""))
			s10 = "0";

		Integer score = Integer.parseInt(s1) + Integer.parseInt(s2)
				+ Integer.parseInt(s3) + Integer.parseInt(s4)
				+ Integer.parseInt(s5) + Integer.parseInt(s6)
				+ Integer.parseInt(s7) + Integer.parseInt(s8)
				+ Integer.parseInt(s9) + Integer.parseInt(s10);

		JobDispatch job = this.get(JobDispatch.class, jobId);
		if (job == null) {
			throw new BusinessException("需要评价的派工单记录不存在。");
		}
		
		/****************************************************************
		 * 主表新增
		 ****************************************************************/
		GradeMaster gradeMaster = new GradeMaster();
		gradeMaster.setGmDescribe(describe);
		gradeMaster.setGmSuggest(suggest);
		gradeMaster.setGmJobDate(new Date());
		gradeMaster.setGmJobId(jobId);
		gradeMaster.setGmOperator(user.getId());
		gradeMaster.setGmOperateTime(new Date());
		gradeMaster.setGmJobBank(job.getJdJobBank());
		gradeMaster.setGmJobDate(job.getJdSignTime());
		gradeMaster.setGmUnit(job.getJdUnit());
		gradeMaster.setGmScore(score);
		
		Integer masterId = (Integer)this.commonDao.save(gradeMaster);
		
		/****************************************************************
		 * 明细表新增
		 ****************************************************************/
		if(!s1.equals("0")){
			GradeDetail gradeDetail= new GradeDetail();
			gradeDetail.setGdMasterId(masterId);
			gradeDetail.setGdGradeItem("s1");
			gradeDetail.setGdScore(Integer.parseInt(s1));
			this.commonDao.save(gradeDetail);
		}
		if(!s2.equals("0")){
			GradeDetail gradeDetail= new GradeDetail();
			gradeDetail.setGdMasterId(masterId);
			gradeDetail.setGdGradeItem("s2");
			gradeDetail.setGdScore(Integer.parseInt(s2));
			this.commonDao.save(gradeDetail);
		}
		if(!s3.equals("0")){
			GradeDetail gradeDetail= new GradeDetail();
			gradeDetail.setGdMasterId(masterId);
			gradeDetail.setGdGradeItem("s3");
			gradeDetail.setGdScore(Integer.parseInt(s3));
			this.commonDao.save(gradeDetail);
		}
		if(!s4.equals("0")){
			GradeDetail gradeDetail= new GradeDetail();
			gradeDetail.setGdMasterId(masterId);
			gradeDetail.setGdGradeItem("s4");
			gradeDetail.setGdScore(Integer.parseInt(s4));
			this.commonDao.save(gradeDetail);
		}
		if(!s5.equals("0")){
			GradeDetail gradeDetail= new GradeDetail();
			gradeDetail.setGdMasterId(masterId);
			gradeDetail.setGdGradeItem("s5");
			gradeDetail.setGdScore(Integer.parseInt(s5));
			this.commonDao.save(gradeDetail);
		}
		if(!s6.equals("0")){
			GradeDetail gradeDetail= new GradeDetail();
			gradeDetail.setGdMasterId(masterId);
			gradeDetail.setGdGradeItem("s6");
			gradeDetail.setGdScore(Integer.parseInt(s6));
			this.commonDao.save(gradeDetail);
		}
		if(!s7.equals("0")){
			GradeDetail gradeDetail= new GradeDetail();
			gradeDetail.setGdMasterId(masterId);
			gradeDetail.setGdGradeItem("s7");
			gradeDetail.setGdScore(Integer.parseInt(s7));
			this.commonDao.save(gradeDetail);
		}
		if(!s8.equals("0")){
			GradeDetail gradeDetail= new GradeDetail();
			gradeDetail.setGdMasterId(masterId);
			gradeDetail.setGdGradeItem("s8");
			gradeDetail.setGdScore(Integer.parseInt(s8));
			this.commonDao.save(gradeDetail);
		}
		if(!s9.equals("0")){
			GradeDetail gradeDetail= new GradeDetail();
			gradeDetail.setGdMasterId(masterId);
			gradeDetail.setGdGradeItem("s9");
			gradeDetail.setGdScore(Integer.parseInt(s9));
			this.commonDao.save(gradeDetail);
		}
		if(!s10.equals("0")){
			GradeDetail gradeDetail= new GradeDetail();
			gradeDetail.setGdMasterId(masterId);
			gradeDetail.setGdGradeItem("s10");
			gradeDetail.setGdScore(Integer.parseInt(s10));
			this.commonDao.save(gradeDetail);
		}
	}
}
