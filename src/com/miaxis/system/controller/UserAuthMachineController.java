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
import com.miaxis.common.exception.BusinessException;
import com.miaxis.common.util.CommonUtil;
import com.miaxis.common.util.PageConfig;
import com.miaxis.common.util.QueryCondition;
import com.miaxis.system.entity.UserMachineAuth;
import com.miaxis.system.entity.UserMachineAuthRecord;
import com.miaxis.system.service.UserMachineAuthRecordService;

/**
 * 系统用户授权机器信息
 * 
 * @author liu.qiao
 * 
 */

@Controller
@RequestMapping("/userAuthMachine")
public class UserAuthMachineController extends CommonController {
	@Autowired
	private UserMachineAuthRecordService userMachineAuthRecordService;
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
		String userId = request.getParameter("userId");
		String userName = request.getParameter("userName");
		ModelAndView mav = new ModelAndView(
				"WEB-INF/pages/system/user/authMachine");
		mav.getModelMap().put("userId", userId);
		mav.getModelMap().put("userName", userName);
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
			String userId) {
		/**
		 * 初始化分页对象
		 */
		PageConfig pageConfig = new PageConfig();
		pageConfig.initData(page, rows, true);

		QueryCondition qc = new QueryCondition();

		if (StringUtils.isNotEmpty(sort)) {
			if (StringUtils.equals(order.toUpperCase(), QueryCondition.DESC)) {
				qc.desc(sort);
			} else {
				qc.asc(sort);
			}
		}

		if (StringUtils.isNotEmpty(userId)) {
			qc.eq("userid", userId);
		}

		List list = commonService.getPageList(UserMachineAuth.class,
				pageConfig, qc);
		return this.buidResultMap(list, pageConfig.getTotalCount());
	}

	/**
	 * 新增
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "insert")
	public ModelAndView insert(String userId, String userName) {
		ModelAndView mav = new ModelAndView(
				"WEB-INF/pages/system/user/authMachineDetail");
		mav.getModelMap().put("userId", userId);
		mav.getModelMap().put("userName", userName);
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
		UserMachineAuth userMachineAuth = (UserMachineAuth) commonService.get(
				UserMachineAuth.class, id);

		if (userMachineAuth == null) {
			return this.buidMessageMap("该授权信息记录不存在!", "1");
		} else {
			try {
				commonService.delete(userMachineAuth);
			} catch (Exception e) {
				return this.buidMessageMap("授权信息记录保存失败！", "1");
			}
			return this.buidMessageMap("授权信息记录删除成功!", "0");
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
	public Map save(UserMachineAuth userMachineAuth) {
		try {
			commonService.save(userMachineAuth);
		} catch (Exception e) {
			return this.buidMessageMap("保存授权信息失败", "1");
		}

		return this.buidMessageMap("保存授权信息成功", "0");
	}

	/**
	 * 授权申请
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "apply")
	public ModelAndView apply(HttpServletRequest request,
			HttpServletResponse response) {
		String loginname = request.getParameter("loginname");
		String machinecode = request.getParameter("machinecode");
		String hostname = request.getParameter("hostname");
		
		ModelAndView mav = new ModelAndView(
				"WEB-INF/pages/system/user/authMachine/apply");
		
		mav.getModelMap().put("loginname", loginname);
		mav.getModelMap().put("machinecode", machinecode);
		mav.getModelMap().put("hostname", hostname);
		return mav;
	}

	/**
	 * 提交授权申请
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "applySubmit")
	@ResponseBody
	public Map applySubmit(UserMachineAuthRecord userMachineAuthRecord,HttpServletRequest request) {
		try {
			String ip =CommonUtil.getIpAddr();
			String hostname = userMachineAuthRecord.getMachinename();
			String machinename = hostname + "["+ip+"]";
			userMachineAuthRecord.setMachinename(machinename);
			
			userMachineAuthRecordService.applySubmit(userMachineAuthRecord);
		} catch (BusinessException e) {
			return this.buidMessageMap(e.getMessage(), "2");
		} catch (Exception e) {
			return this.buidMessageMap("授权申请提交失败", "1");
		}

		return this.buidMessageMap("授权申请提交成功,等管理员审核通过后即可使用。", "0");
	}
	
	/**
	 * 
	 * 授权申请审批主页
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "approvemain")
	@ResponseBody
	public ModelAndView approvemain(String ids,String studentIds,String result) {
		ModelAndView mav = this.getModelMainMav("WEB-INF/pages/system/user/authMachine/approvemain");
		return mav;
	}
	
	/**
	 * 获取列表
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "approvelist")
	@ResponseBody
	public Map approvelist(String page, String sort, String order, String rows,
			String qloginname,String qStatus) {
		/**
		 * 初始化分页对象
		 */
		PageConfig pageConfig = new PageConfig();
		pageConfig.initData(page, rows, true);

		QueryCondition qc = new QueryCondition();

		if (StringUtils.isNotEmpty(sort)) {
			if (StringUtils.equals(order.toUpperCase(), QueryCondition.DESC)) {
				qc.desc(sort);
			} else {
				qc.asc(sort);
			}
		}

		if (StringUtils.isNotEmpty(qloginname)) {
			qc.eq("loginname", qloginname);
		}
		
		if (StringUtils.isNotEmpty(qStatus)) {
			qc.eq("status", qStatus);
		}

		List list = commonService.getPageList(UserMachineAuthRecord.class,pageConfig, qc);
		return this.buidResultMap(list, pageConfig.getTotalCount());
	}

	
	/**
	 * 
	 * 授权申请审批
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "approveSubmit", method = RequestMethod.POST)
	@ResponseBody
	public Map approveSubmit(String id,String result) {
		try {
			userMachineAuthRecordService.approveSubmit(id,result);
		} catch (BusinessException e) {
			return this.buidMessageMap(e.getMessage(), "1");
		} catch (Exception e) {
			return this.buidMessageMap("审验操作失败!", "1");
		}
		return this.buidMessageMap("审验操作成功!", "0");
	}
}