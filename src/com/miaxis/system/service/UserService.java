package com.miaxis.system.service;

import com.miaxis.common.base.service.CommonService;
import com.miaxis.system.entity.User;

/**
 * 
 * @author liu.qiao
 * 
 */
public interface UserService extends CommonService {
	public void addUser(User user,String roleIds);
	public void updateUser(User user,String roleIds);
}
