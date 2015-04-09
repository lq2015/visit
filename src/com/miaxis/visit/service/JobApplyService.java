package com.miaxis.visit.service;

import com.miaxis.common.base.service.CommonService;
import com.miaxis.visit.entity.JobApply;

/**
 * 派工申请
 * @author liu.qiao
 * 
 */
public interface JobApplyService extends CommonService {
	/**
	 * 新增申请
	 * @param jobApply
	 */
	public void addJobApply(JobApply jobApply);
	/**
	 * 取消申请
	 * @param id
	 */
	public void cancleJobApply(String id);
	
	/**
	 * 驳回申请
	 * @param id
	 */
	public void rejectJobApply(String id);
	
}
