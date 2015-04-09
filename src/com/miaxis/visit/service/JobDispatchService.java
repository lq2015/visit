package com.miaxis.visit.service;

import java.util.List;

import com.miaxis.common.base.service.CommonService;
import com.miaxis.visit.entity.JobDispatch;

/**
 * 派工单管理
 * 
 * @author liu.qiao
 * 
 */
public interface JobDispatchService extends CommonService {
	public void addJobDispatch(JobDispatch jobDispatch,String applyId);

	public void updateJobDispatch(JobDispatch jobDispatch);

	public void delJobDispatch(Integer id);

	/**
	 * 更新状态
	 * 
	 * @param id
	 * @param status
	 */
	public void updateStatus(Integer id, String status);

	/**
	 * 派工
	 * 
	 * @param id
	 */
	public void dispatchJob(Integer id);

	/**
	 * 签到
	 * 
	 * @param id
	 */
	public void signSubmit(Integer id);

	/**
	 * 签离
	 * 
	 * @param id
	 */
	public void outSubmit(Integer id);
	
	/**
	 * 上传维修单
	 */
	public void uploadFiles(List list,Integer jobId);

	/**
	 * 评价
	 * 
	 * @param id
	 */
	public void gradeSubmit(Integer jobId, String s1, String s2, String s3,
			String s4, String s5, String s6, String s7, String s8, String s9,
			String s10,String describe,String suggest);
}
