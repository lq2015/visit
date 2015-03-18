package com.miaxis.system.service;

import com.miaxis.common.base.service.CommonService;
import com.miaxis.system.entity.UserMachineAuthRecord;

/**
 * 用户授权申请与审核
 * @author liu.qiao
 * 
 */
public interface UserMachineAuthRecordService extends CommonService {
	/**
	 * 授权申请
	 * @param id
	 */
	public void applySubmit(UserMachineAuthRecord userMachineAuthRecord);
	/**
	 * 授权审批
	 * @param ids
	 */
	public void approveSubmit(String ids,String result);
	
	
}
