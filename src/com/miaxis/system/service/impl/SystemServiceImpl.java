package com.miaxis.system.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.miaxis.common.base.service.CommonServiceImpl;
import com.miaxis.common.exception.BusinessException;
import com.miaxis.common.util.BrowserUtils;
import com.miaxis.common.util.CommonUtil;
import com.miaxis.common.util.ContextHolderUtils;
import com.miaxis.common.util.QueryCondition;
import com.miaxis.system.entity.Function;
import com.miaxis.system.entity.Log;
import com.miaxis.system.entity.Menu;
import com.miaxis.system.entity.RoleFunAuth;
import com.miaxis.system.entity.RoleMenuAuth;
import com.miaxis.system.entity.User;
import com.miaxis.system.entity.UserMachineAuth;
import com.miaxis.system.service.SystemService;

@Service("systemService")
public class SystemServiceImpl extends CommonServiceImpl implements SystemService {

	public void addLog(String logcontent,  int loglevel, int operatetype) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		User user = (User) request.getSession().getAttribute("userSession");
		String broswer = BrowserUtils.checkBrowse(request);
		Log log = new Log();
		log.setLogcontent(logcontent);
		log.setLoglevel(loglevel);
		log.setOperatetype(operatetype);
		log.setNote(CommonUtil.getIpAddr());
		log.setBroswer(broswer);
		log.setOperatetime(new Date());
		log.setUser(user);
		this.commonDao.save(log);
	}

	public void saveRoleMenuAuth(String menuIds, String roleId) {
		String[] muenuIdArray = menuIds.split(",");
		
		/**
		 * 清空角色原菜单权限
		 */
		List<RoleMenuAuth> list =  this.findByProperty(RoleMenuAuth.class, "roleId", roleId);
		for(RoleMenuAuth rma : list){
			this.commonDao.delete(rma);
		}
		
		for(int i=0;i<muenuIdArray.length;i++){
			RoleMenuAuth roleMenuAuth = new RoleMenuAuth();
			roleMenuAuth.setMenuId(muenuIdArray[i]);
			roleMenuAuth.setRoleId(roleId);
			commonDao.save(roleMenuAuth);
		}
		
	}

	public void saveRoleFunAuth(String funIds, String roleId, String menuId) {
		String[] funIdArray = funIds.split(",");
		
		/**
		 * 清空角色原菜单功能点权限
		 */
		QueryCondition qc = new QueryCondition();
		qc.eq("roleId", roleId);
		qc.eq("menuId", menuId);
		List<RoleFunAuth> list =  this.getPageList(RoleFunAuth.class,null, qc);
		
		for(RoleFunAuth rfa : list){
			this.commonDao.delete(rfa);
		}
		
		for(int i=0;i<funIdArray.length;i++){
			if(funIdArray[i]!=null && !funIdArray[i].equals("") ){
				RoleFunAuth roleFunAuth = new RoleFunAuth();
				roleFunAuth.setFunId(funIdArray[i]);
				roleFunAuth.setMenuId(menuId);
				roleFunAuth.setRoleId(roleId);
				commonDao.save(roleFunAuth);
			}
		}
	}

	public List<Menu> getRoleMenuAuth(User user) {
		StringBuffer sql = new StringBuffer();
		sql.append("\n SELECT id,menuName,menuLevel,parentMenu,orderNum,isValid,icoIndex,selIcoIndex,");
		sql.append("\n 	(CASE WHEN LENGTH(linkPage)>0 THEN (CONCAT(linkPage ,'&menuId=' ,id))  ELSE '' END) AS linkPage ");
//		sql.append("\n 	(CASE WHEN LENGTH(linkPage)>0 THEN linkPage||'&'||'menuId='||id  ELSE '' END) AS linkPage ");
		//超级用户取所有
		if(user.getUserName().equals("admin") || user.getUserName().equals("super")){
			sql.append("\n FROM t_s_menu");
			sql.append("\n WHERE ISVALID='1'");
		}else{
			sql.append("\n FROM t_s_menu  WHERE  id IN (");
			sql.append("\n 	SELECT DISTINCT  menuId FROM t_s_role_menu_auth WHERE  roleId IN (");
			sql.append("\n 		SELECT roleId FROM t_s_role_user WHERE userId ='" +user.getId()+ "'");
			sql.append("\n 	)");
			sql.append("\n )");
			sql.append("\n AND ISVALID='1'");
		}
		
		sql.append("\n ORDER BY menuLevel,orderNum");
		List<Menu> list =this.commonDao.getListBySql(Menu.class,sql.toString());
		return list;
	}

	public List<Function> getRoleFunAuth(User user, String menuId) {
		StringBuffer sql = new StringBuffer();
		sql.append("\n SELECT * FROM t_s_function ");
		
		//超级用户取所有
		if(user.getUserName().equals("admin") || user.getUserName().equals("super")){
			sql.append("\n WHERE parentMenu='"+menuId+"'");
		}else{
			sql.append("\n WHERE id IN ");
			sql.append("\n 	(SELECT DISTINCT  funId FROM t_s_role_fun_auth ");
			sql.append("\n 		WHERE  roleId IN (");
			sql.append("\n 			SELECT roleId FROM t_s_role_user WHERE userId ='"+user.getId()+"'");
			sql.append("\n 		    )");
			sql.append("\n 	    AND menuId='"+menuId+"'");
			sql.append("\n 	)");
		}
		
		sql.append("\n AND ISVALID='1'");
		sql.append("\n ORDER BY funtype,orderNum");
		List<Function> list =this.commonDao.getListBySql(Function.class,sql.toString());
		return list;
	}
	
	/**
	 * 校验用户合法性
	 */
	public void verifyUserAuth(String userName, String password ,String machineCode,String hostname) {
		List list = this.commonDao.getListByHql(User.class," from User t WHERE t.status='1' and t.userName='" + userName + "'");
		
		User user = null;
		if(list.size()!=1){
			throw new BusinessException("登陆用户【"+userName+"】不存在!");
		}else{
			user = (User) list.get(0);
			
			/****************************************************
			 * 除admin外要校验授权机器
			 ****************************************************/
			/*if(!userName.equals("admin")){
				boolean passTag = false;
				List<UserMachineAuth> machinelist  = this.commonDao.getListByHql(UserMachineAuth.class, " from UserMachineAuth t WHERE  t.userid='" + user.getId() + "'");
				for(UserMachineAuth userMachineAuth :machinelist){
					String authMachine = userMachineAuth.getMachinecode();
					Date  endDate = userMachineAuth.getAuthenddate();
					
					//最后日期后延一天比较
					Calendar calendar = new GregorianCalendar(); 
				    calendar.setTime(endDate); 
				    calendar.add(calendar.DATE,1);
				    endDate=calendar.getTime(); 
					
					Date today =  new Date();
					
					if(authMachine==null) authMachine="";
					if(authMachine.equals(machineCode)){
						if(endDate.compareTo(today)<0){
							throw new BusinessException("用户【"+userName+"】在当前机器授权使用已过有效期,不能访问!<br/>可以继续申请使用授权！<br/>特征信息:<b>"+machineCode+"</b> <a href='#' onclick=authApply('"+machineCode+"')> 提交授权申请</a>");
						}
						passTag = true;
						break;
					}
				}
				
				if(!passTag){
					throw new BusinessException("用户【"+userName+"】未在当前机器授权使用,请授权后使用! <br/>特征信息:<b>"+machineCode+"</b> <a href='#' onclick=authApply('"+machineCode+"')>提交授权申请</a>");
				}
			}*/
			
			String dbpsw = user.getPassword();
			if(dbpsw==null) dbpsw="";
			if (!dbpsw.equals(CommonUtil.md5(password))) {
				throw new BusinessException("登陆密码不正确!");
			}else{
				/****************************************************
				 * 记录用户session
				 ****************************************************/
				HttpServletRequest request = ContextHolderUtils.getRequest();
				request.getSession().setAttribute("userSession", user);
				
				/****************************************************
				 * 记录登陆日志
				 ****************************************************/
				this.addLog("用户:"+ user.getRealName()+"在授权机器【"+hostname+"/"+machineCode+"】登陆成功", 1, 1);
			}
		}
	}
}
