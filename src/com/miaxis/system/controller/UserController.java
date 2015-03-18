package com.miaxis.system.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.miaxis.common.base.CommonController;
import com.miaxis.common.util.CommonUtil;
import com.miaxis.common.util.PageConfig;
import com.miaxis.common.util.QueryCondition;
import com.miaxis.system.entity.Role;
import com.miaxis.system.entity.RoleUser;
import com.miaxis.system.entity.User;
import com.miaxis.system.entity.UserMachineAuth;
import com.miaxis.system.service.UserService;

/**
 * 系统用户
 * 
 * @author liu.qiao
 * 
 */

@Controller
@RequestMapping("/user")
public class UserController extends CommonController {
	@Autowired
	private UserService userService;

	/**
	 * 主页
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "main")
	public ModelAndView main(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = this.getModelMainMav("WEB-INF/pages/system/user/userList");
		return mav;
	}

	/**
	 * 获取列表
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "list")
	@ResponseBody
	public Map list(String page, String sort, String order, String rows,
			String quserName, String qrealName,String qUserStatus) {

		/**
		 * 初始化分页对象
		 */
		PageConfig pageConfig = new PageConfig();
		pageConfig.initData(page, rows, true);
		
		QueryCondition qc = new QueryCondition();
		
		if(StringUtils.isNotEmpty(sort)){
			if (StringUtils.equals(order.toUpperCase(), QueryCondition.DESC)){
				qc.desc(sort);
			}else{
				qc.asc(sort);
			}
		}
		
		if (StringUtils.isNotEmpty(quserName)) {
			qc.like("userName", quserName.concat("%"));
		}
		if (StringUtils.isNotEmpty(qrealName)) {
			qc.like("realName", qrealName.concat("%"));
		}
		if (StringUtils.isNotEmpty(qUserStatus)) {
			qc.eq("status", qUserStatus);
		}
		
		List list = userService.getPageList(User.class,pageConfig,qc);
		return this.buidResultMap(list,pageConfig.getTotalCount());
	}

	/**
	 * 查看明细
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "detail")
	public ModelAndView detail(String id) {
		User user = userService.get(User.class, id);
		ModelAndView mav = new ModelAndView(
				"WEB-INF/pages/system/user/userDetail");
		mav.getModelMap().put("user", user);
		return mav;
	}

	/**
	 * 修改或新增
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "insertOrUpdate")
	public ModelAndView insertOrUpdate(String id, String operationType) {
		ModelAndView mav = new ModelAndView("WEB-INF/pages/system/user/userDetail");
		if (operationType.equals("edit")) {
			User user = userService.get(User.class, id);
			
			//提取角色相关信息
			String roleNames="",roleIds="";
			List<RoleUser> list =  commonService.findByProperty(RoleUser.class, "userId", user.getId());
			for(RoleUser ru : list){
				String roleId = ru.getRoleId();
				if(roleId==null || roleId.equals("")){
					continue;
				}
				Role role = commonService.get(Role.class, roleId);
				if(roleNames.equals("")){
					roleNames = role.getRoleName();
					roleIds = role.getId();
				}else{
					roleNames = roleNames +","+ role.getRoleName();
					roleIds = roleIds +","+ role.getId();
				}
			}
			
			mav.getModelMap().put("user", user);
			mav.getModelMap().put("roleNames", roleNames);
			mav.getModelMap().put("roleIds", roleIds);
			mav.getModelMap().put("operationType", "edit");
		} else {
			mav.getModelMap().put("operationType", "insert");
		}
		
		return mav;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "del", method = RequestMethod.POST)
	@ResponseBody
	public Map del(String id) {
		String message = "";
		User user = (User) userService.get(User.class, id);

		if (user == null) {
			return this.buidMessageMap("该用户信息不存在!", "1");
		} else {
			try {
				userService.delete(user);
			} catch (Exception e) {
				return this.buidMessageMap("保存失败了", "1");
			}
			return this.buidMessageMap("用户【" + user.getRealName() + "】信息删除成功!",
					"0");
		}
	}

	/**
	 * 保存
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public Map save(User user,String roleIds ,String operationType) {
		String msg = operationType.equals("edit") ? "修改" : "新增";
		try {
			if (operationType.equals("edit")) {
				String message = "";
				User u = (User) userService.get(User.class, user.getId());
				if (u == null) {
					return this.buidMessageMap("该用户信息不存在!", "1");
				} else {
					user.setPassword(u.getPassword());
					user.setStatus(u.getStatus());
					user.setFix(u.getFix());
					userService.updateUser(user, roleIds);
				}
			} else {
				user.setPassword(CommonUtil.md5(User.initial_Pwd));
				user.setStatus("0");
				user.setFix("0");
				userService.addUser(user,roleIds);
			}
			
		} catch (Exception e) {
			return this.buidMessageMap(msg + "用户失败", "1");
		}

		return this.buidMessageMap(msg + "用户成功", "0");
	}
	
	/**
	 * 修改密码
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "changeUserPwd")
	public ModelAndView changeUserPwd(String id) {
		ModelAndView mav = new ModelAndView("WEB-INF/pages/system/user/changeUserPwd");
		return mav;
	}
	
	/**
	 * 修改密码提交
	 * @param id
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	@RequestMapping(params = "changeUserPwdSubmit")
	@ResponseBody
	public Map changeUserPwdSubmit(String id ,String oldPassword,String newPassword) {
		User user = userService.get(User.class, id);
		if (user == null) {
			return this.buidMessageMap("该用户信息不存在!", "1");
		} else {
			if(!user.getPassword().equals(CommonUtil.md5(oldPassword))){
				return this.buidMessageMap("旧密码不符，请重新输入!", "1");
			}else{
				user.setPassword(CommonUtil.md5(newPassword));
				userService.updateEntitie(user);
			}
		}
		return this.buidMessageMap("用户密码修改成功", "0");
	}
	
	/**
	 * 修改记录状态
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping(params = "updateStatus", method = RequestMethod.POST)
	@ResponseBody
	public Map updateStatus(String id,String status) {
		User user = userService.get(User.class, id);
		if (user == null) {
			return this.buidMessageMap("该记录信息不存在!", "1");
		} else {
			try {
				user.setStatus(status);
				userService.updateEntitie(user);
			} catch (Exception e) {
				return this.buidMessageMap("修改记录状态失败", "1");
			}
			return this.buidMessageMap("修改记录操作成功!","0");
		}
	}
	
	/**
	 * 选择角色
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "selectRole")
	public ModelAndView selectRole(HttpServletRequest request,
			HttpServletResponse response) {
		String userId= request.getParameter("userId");
		String userName= request.getParameter("userName");
		ModelAndView mav = new ModelAndView("WEB-INF/pages/system/user/selectRole");
		mav.getModelMap().put("userId", userId);
		mav.getModelMap().put("userName", userName);
		return mav;
	}
}