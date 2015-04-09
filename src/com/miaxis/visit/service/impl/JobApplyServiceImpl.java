package com.miaxis.visit.service.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.miaxis.common.base.service.CommonServiceImpl;
import com.miaxis.common.exception.BusinessException;
import com.miaxis.common.util.ContextHolderUtils;
import com.miaxis.system.entity.User;
import com.miaxis.visit.entity.BankInfo;
import com.miaxis.visit.entity.JobApply;
import com.miaxis.visit.service.JobApplyService;

@Service("jobApplyService")
public class JobApplyServiceImpl extends CommonServiceImpl implements JobApplyService {

	@Override
	public void addJobApply(JobApply jobApply) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		User user = (User) request.getSession().getAttribute("userSession");
		
		BankInfo bankInfo = this.commonDao.get(BankInfo.class,
				jobApply.getJaJobBank());
		jobApply.setBankInfo(bankInfo);
		
		jobApply.setApplyUser(user);
		jobApply.setJaApplyTime(new Date());
		jobApply.setJaStatus(JobApply.Status.APPLY.getCode());
		this.commonDao.save(jobApply);
	}

	@Override
	public void cancleJobApply(String id) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		User user = (User) request.getSession().getAttribute("userSession");
		
		JobApply jobApply = this.commonDao.get(JobApply.class, Integer.parseInt(id));
		if(jobApply==null){
			throw new BusinessException("派工申请信息不存在。" );
		}
		
		jobApply.setApplyUser(user);
		jobApply.setJaApplyTime(new Date());
		jobApply.setJaStatus(JobApply.Status.CANCEL.getCode());
		this.commonDao.updateEntitie(jobApply);
	}

	@Override
	public void rejectJobApply(String id) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		User user = (User) request.getSession().getAttribute("userSession");
		
		JobApply jobApply = this.commonDao.get(JobApply.class, Integer.parseInt(id));
		if(jobApply==null){
			throw new BusinessException("派工申请信息不存在。" );
		}
		
		jobApply.setApproveUser(user);
		jobApply.setJaApproveTime(new Date());
		jobApply.setJaStatus(JobApply.Status.REJECT.getCode());
		this.commonDao.updateEntitie(jobApply);
	}
}
