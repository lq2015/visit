package com.miaxis.visit.service.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.miaxis.common.base.service.CommonServiceImpl;
import com.miaxis.common.exception.BusinessException;
import com.miaxis.common.util.ContextHolderUtils;
import com.miaxis.system.entity.User;
import com.miaxis.visit.entity.BankInfo;
import com.miaxis.visit.entity.JobDispatch;
import com.miaxis.visit.entity.UnitInfo;
import com.miaxis.visit.service.JobDispatchService;

@Service("jobDispatchService")
public class JobDispatchServiceImpl extends CommonServiceImpl implements JobDispatchService {

	@Override
	public void addJobDispatch(JobDispatch jobDispatch) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		User user = (User) request.getSession().getAttribute("userSession");
		
		UnitInfo unitInfo = this.commonDao.get(UnitInfo.class, jobDispatch.getJdUnit());
		jobDispatch.setUnitInfo(unitInfo);
	
		BankInfo bankInfo = this.commonDao.get(BankInfo.class, jobDispatch.getJdJobBank());
		jobDispatch.setBankInfo(bankInfo);
		
		jobDispatch.setJdOperateTime(new Date());
		jobDispatch.setUser(user);
		
		jobDispatch.setJdStatus(JobDispatch.Status.INPUT.getCode());
		jobDispatch.setJdIsSms(JobDispatch.IsSms.NO.getCode());
		this.commonDao.save(jobDispatch);
	}

	@Override
	public void updateJobDispatch(JobDispatch jobDispatch) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		User user = (User) request.getSession().getAttribute("userSession");
		
		Integer id = jobDispatch.getId();
		JobDispatch job = this.get(JobDispatch.class, id);
		if(job==null){
			throw new BusinessException("修改的派工单记录不存在。" );
		}
		
		UnitInfo unitInfo = this.commonDao.get(UnitInfo.class, jobDispatch.getJdUnit());
		jobDispatch.setUnitInfo(unitInfo);
	
		BankInfo bankInfo = this.commonDao.get(BankInfo.class, jobDispatch.getJdJobBank());
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
		if(job==null){
			throw new BusinessException("删除的派工单记录不存在。" );
		}
		
		this.commonDao.delete(job);
	}

	@Override
	public void updateStatus(Integer id, String status) {
		JobDispatch job = this.get(JobDispatch.class, id);
		if(job==null){
			throw new BusinessException("修改的派工单记录不存在。" );
		}
		job.setJdStatus(status);
		this.commonDao.updateEntitie(job);
	}

	@Override
	public void dispatchJob(Integer id) {
		JobDispatch job = this.get(JobDispatch.class, id);
		if(job==null){
			throw new BusinessException("需要派工的派工单记录不存在。" );
		}
		job.setJdStatus(JobDispatch.Status.DISPATCH.getCode());
		this.commonDao.updateEntitie(job);
	}

	@Override
	public void signSubmit(Integer id) {
		JobDispatch job = this.get(JobDispatch.class, id);
		if(job==null){
			throw new BusinessException("需要签到的派工单记录不存在。" );
		}
		job.setJdStatus(JobDispatch.Status.SIGN.getCode());
		this.commonDao.updateEntitie(job);
	}

	@Override
	public void outSubmit(Integer id) {
		JobDispatch job = this.get(JobDispatch.class, id);
		if(job==null){
			throw new BusinessException("需要签离的派工单记录不存在。" );
		}
		job.setJdStatus(JobDispatch.Status.OUT.getCode());
		this.commonDao.updateEntitie(job);
	}

	@Override
	public void uploadFiles() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gradeSubmit(Integer id) {
		// TODO Auto-generated method stub
		
	}
}
