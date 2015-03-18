package com.miaxis.system.service;

import java.util.List;

import com.miaxis.common.base.service.CommonService;
import com.miaxis.system.entity.Function;
import com.miaxis.system.entity.Menu;
import com.miaxis.system.entity.User;

/**
 * 
 * @author liu.qiao
 * 
 */
public interface SystemService extends CommonService {
	/**
	 * 日志添加
	 * @param LogContent 内容
	 * @param loglevel 级别
	 * @param operatetype 类型
	 */
	public void addLog(String logcontent, int loglevel,int operatetype);
	/**
	 * 保存角色菜单权限
	 * @param menuIds
	 * @param roleId
	 */
	public void saveRoleMenuAuth(String menuIds ,String roleId);
	/**
	 * 保存角色菜单功能点权限
	 * @param funIds
	 * @param roleId
	 * @param menuId
	 */
	public void saveRoleFunAuth(String funIds ,String roleId, String menuId);
	
	/**
	 * 得到用户的菜单权限
	 * @param user
	 * @return
	 */
	public List<Menu> getRoleMenuAuth(User user);
	
	/**
	 * 得到用户的菜单功能点权限
	 * @param user
	 * @return
	 */
	public List<Function> getRoleFunAuth(User user,String menuId);
	
	/**
	 * 校验用户合法性
	 * @param userName
	 * @param password
	 */
	public void verifyUserAuth(String userName,String password,String machineCode,String hostname);
}
