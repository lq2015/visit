package com.miaxis.system.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.miaxis.common.base.service.CommonServiceImpl;
import com.miaxis.system.entity.RoleUser;
import com.miaxis.system.entity.User;
import com.miaxis.system.service.UserService;

@Service("userService")
public class UserServiceImpl extends CommonServiceImpl implements UserService {

	public void addUser(User user, String roleIds) {
		this.save(user);
		String[] ids = roleIds.split(",");
		for(int i=0;i<ids.length;i++){
			RoleUser roleUser = new RoleUser();
			roleUser.setRoleId(ids[i]);
			roleUser.setUserId(user.getId());
			this.commonDao.save(roleUser);
		}
	}
	
	public void updateUser(User user, String roleIds) {
		
		this.commonDao.updateEntitie(user);
		
		/**
		 * 清空用户原角色
		 */
		List<RoleUser> list =  this.findByProperty(RoleUser.class, "userId", user.getId());
		for(RoleUser ru : list){
			this.commonDao.delete(ru);
		}
		
		/**
		 * 重新新增
		 */
		String[] ids = roleIds.split(",");
		for(int i=0;i<ids.length;i++){
			RoleUser roleUser = new RoleUser();
			roleUser.setRoleId(ids[i]);
			roleUser.setUserId(user.getId());
			this.commonDao.save(roleUser);
		}
	}
}
