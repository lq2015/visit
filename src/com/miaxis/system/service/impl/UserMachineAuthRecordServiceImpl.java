package com.miaxis.system.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.miaxis.common.base.service.CommonServiceImpl;
import com.miaxis.common.exception.BusinessException;
import com.miaxis.common.util.ContextHolderUtils;
import com.miaxis.common.util.DateUtil;
import com.miaxis.system.entity.User;
import com.miaxis.system.entity.UserMachineAuth;
import com.miaxis.system.entity.UserMachineAuthRecord;
import com.miaxis.system.service.UserMachineAuthRecordService;

@Service("userMachineAuthRecordService")
public class UserMachineAuthRecordServiceImpl extends CommonServiceImpl
		implements UserMachineAuthRecordService {

	public void applySubmit(UserMachineAuthRecord userMachineAuthRecord) {
		String loginname = userMachineAuthRecord.getLoginname();
		String machinecode = userMachineAuthRecord.getMachinecode();
		User applyUser =null;
		String today= DateUtil.getYYYY_MM_DD(new Date());
		
		/****************************************************
		 * 检测用户是否存在
		 ****************************************************/
		List list = this.commonDao.getListByHql(User.class,
				" from User t WHERE t.userName='" + loginname + "'");

		if (list.size()< 0) {
			throw new BusinessException("提交申请的使用用户【" + loginname + "】不存在");
		}
		
		/****************************************************
		 * 检测用户是否已经有使用授权
		 ****************************************************/
		applyUser = (User)list.get(0);
		List list2 = this.commonDao.getListByHql(User.class,
				" from UserMachineAuth t WHERE t.userid='" + applyUser.getId() 
				+ "' and t.machinecode='" + machinecode+"' and t.authenddate>=to_date('"+today+"','yyyy-MM-dd') ");
		
		if (list2.size()> 0) {
			throw new BusinessException("使用用户【" + loginname + "】已经有在机器【"+machinecode+"】的使用授权，无须申请!");
		}
		
		/****************************************************
		 * 检测用户是否重复申请
		 ****************************************************/
		List list3 = this.commonDao.getListByHql(User.class,
				" from UserMachineAuthRecord t WHERE t.loginname='" + loginname 
				+ "' and t.machinecode='" + machinecode+"' and t.status ='0' and t.authenddate>=to_date('"+today+"','yyyy-MM-dd') ");

		if (list3.size()>0) {
			throw new BusinessException("登陆用户【" + loginname + "】在机器【"+machinecode+"】授权使用还有未审核的申请记录,无须重复申请!");
		}
		
		/****************************************************
		 * 检测用户是否重复申请
		 ****************************************************/
		userMachineAuthRecord.setStatus("0");
		this.commonDao.save(userMachineAuthRecord);
	}

	public void approveSubmit(String id, String result) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		User user = (User) request.getSession().getAttribute("userSession");
		User applyUser  = null;

		/****************************************************
		 * 修改审核结果
		 ****************************************************/
		UserMachineAuthRecord  userMachineAuthRecord = this.commonDao.get(UserMachineAuthRecord.class, id);
		if(userMachineAuthRecord==null){
			throw new BusinessException("需审核记录的不存在!");
		}
		
		/****************************************************
		 * 检测用户是否存在
		 ****************************************************/
		String loginname = userMachineAuthRecord.getLoginname();
		List list = this.commonDao.getListByHql(User.class,
				" from User t WHERE t.userName='" + loginname + "'");
		
		if(list.size()>0){
			applyUser  = (User)list.get(0);
		}else{
			throw new BusinessException("提交申请审核的使用用户【" + loginname + "】不存在");
		}
		
		/****************************************************
		 * 修改审核结果
		 ****************************************************/
		userMachineAuthRecord.setApproveMan(user.getId());
		userMachineAuthRecord.setApproveTime(new Date());
		userMachineAuthRecord.setStatus(result);
		this.commonDao.updateEntitie(userMachineAuthRecord);
		
		/****************************************************
		 * 修改审核为通过
		 ****************************************************/
		if(result.equals("1")){
			String machinecode = userMachineAuthRecord.getMachinecode();
			List list2 = this.commonDao.getListByHql(User.class,
					" from UserMachineAuth t WHERE t.userid='" + applyUser.getId() 
					+ "' and t.machinecode='" + machinecode+"'");
			
			if(list2.size()>0){
				UserMachineAuth userMachineAuth = (UserMachineAuth)list2.get(0);
				userMachineAuth.setAuthenddate(userMachineAuthRecord.getAuthenddate());
				userMachineAuth.setMachinename(userMachineAuthRecord.getMachinename());
				userMachineAuth.setMemo(userMachineAuthRecord.getMemo());
				this.commonDao.updateEntitie(userMachineAuth);
			}else{
				UserMachineAuth userMachineAuth = new UserMachineAuth();
				userMachineAuth.setAuthenddate(userMachineAuthRecord.getAuthenddate());
				userMachineAuth.setMachinecode(userMachineAuthRecord.getMachinecode());
				userMachineAuth.setMachinename(userMachineAuthRecord.getMachinename());
				userMachineAuth.setUserid(applyUser.getId());
				userMachineAuth.setMemo(userMachineAuthRecord.getMemo());
				this.commonDao.save(userMachineAuth);
			}
		}
	}
}
