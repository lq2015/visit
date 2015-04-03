package com.miaxis.visit.service;

import com.miaxis.common.base.service.CommonService;
import com.miaxis.visit.entity.JobDispatch;

/**
 * 派工单管理
 * @author liu.qiao
 * 
 */
public interface JobDispatchService extends CommonService {
	public void addJobDispatch(JobDispatch jobDispatch);
	public void updateJobDispatch(JobDispatch jobDispatch);
	public void delJobDispatch(Integer id);
	/**
	 * 更新状态
	 * @param id
	 * @param status
	 */
	public void updateStatus(Integer id, String status);
	/**
	 * 派工
	 * @param id
	 */
	public void dispatchJob(Integer id);
	/**
	 * 签到
	 * @param id
	 */
	public void signSubmit(Integer id);
	/**
	 * 签离
	 * @param id
	 */
	public void outSubmit(Integer id);
	
	public void uploadFiles();
	/**
	 * 评价
	 * @param id
	 */
	public void gradeSubmit(Integer id);
}
