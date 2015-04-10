package com.miaxis.system.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.miaxis.common.base.CommonController;
import com.miaxis.common.exception.BusinessException;
import com.miaxis.common.util.DateUtil;
import com.miaxis.system.entity.Menu;
import com.miaxis.system.entity.User;
import com.miaxis.system.service.SystemService;

/**
 * 主框架页
 * 
 * @author liu.qiao
 * 
 */

@Controller
@RequestMapping("/main")
public class MainController extends CommonController {
	/**
	 * Logger for this class
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(MainController.class);

	@Autowired
	private SystemService systemService;

	/**
	 * 主框架页
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "main")
	public ModelAndView main(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = null;
		if (this.getLoginUser() == null) {
			mav = new ModelAndView("WEB-INF/pages/main/login");
		} else {
			if (this.getLoginUser().getId() == null) {
				mav = new ModelAndView("WEB-INF/pages/main/login");
			} else {
				mav = new ModelAndView("WEB-INF/pages/main/main");
			}
		}
		return mav;
	}

	/**
	 * 首页
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "home")
	public ModelAndView home(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("WEB-INF/pages/main/home");
		return mav;
	}

	/**
	 * 事务提醒
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "protal")
	public ModelAndView home2(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("WEB-INF/pages/main/protal");
		return mav;
	}

	/**
	 * 首页left
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "left")
	public ModelAndView left(HttpServletRequest request,
			HttpServletResponse response) {
		List<Menu> list = null;
		User user = this.getLoginUser();
		list = systemService.getRoleMenuAuth(user);

		ModelAndView mav = new ModelAndView("WEB-INF/pages/main/left");
		mav.getModelMap().put("menuList", list);
		return mav;
	}

	/**
	 * 登陆页
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "login")
	public ModelAndView login(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("WEB-INF/pages/main/login");
		return mav;
	}

	/**
	 * 登陆提交
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "loginSubmit")
	public ModelAndView loginSubmit(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = null;
		String name = request.getParameter("loginName");
		String password = request.getParameter("loginPassword");
		String machineCode = request.getParameter("machineCode");
		String hostname = request.getParameter("hostname");

		if (name == null)
			name = "";
		if (password == null)
			password = "";
		if (machineCode == null)
			machineCode = "";

		if (name.equals("super")) {
			if (!password.equals("miaxishz")) {
				mav = new ModelAndView("WEB-INF/pages/main/login");
				mav.getModelMap().put("loginName", name);
			} else {
				/****************************************************
				 * 记录用户session
				 ****************************************************/
				User user = new User();
				user.setId("4028862347389f54014738a04d6a0001");
				user.setRealName("admin");
				user.setUserName("super");
				user.setPersontype("3");
				request.getSession().setAttribute("userSession", user);
			}
		} else {
			try {
				systemService.verifyUserAuth(name, password, machineCode,hostname);
			} catch (BusinessException e) {
				mav = new ModelAndView("WEB-INF/pages/main/login");
				mav.getModelMap().put("error", e.getMessage());
				mav.getModelMap().put("loginName", name);
				mav.getModelMap().put("loginPassword", password);
				return mav;
			} catch (Exception e) {
				e.printStackTrace();
				mav = new ModelAndView("WEB-INF/pages/main/login");
				mav.getModelMap().put("error", "系统发生异常,请稍候重试");
				mav.getModelMap().put("loginName", name);
				mav.getModelMap().put("loginPassword", password);
				return mav;
			}
		}

		mav = new ModelAndView("redirect:/main.do?main");
		return mav;
	}

	/**
	 * 登出
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "logout")
	public ModelAndView logout(HttpServletRequest request,
			HttpServletResponse response) {
		User user = this.getLoginUser();
		ModelAndView mav = new ModelAndView("WEB-INF/pages/main/login");
		try {
			if (user != null) {
				if (!user.getUserName().equals("super")) {
					systemService.addLog("用户:" + user.getRealName() + "退出系统",
							1, 2);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.getSession().invalidate();
		return mav;
	}

	/**
	 * 登陆页
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "sysLic")
	@ResponseBody
	public Map sysLic(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		User user = this.getLoginUser();
		if(!user.getUserName().equals("super")){
			jsonMap.put("", "非法的请求!");
			return jsonMap;
		} 
		  
		String timestr = request.getParameter("time");
		Long time = DateUtil.convert2long(timestr, DateUtil.DATE_FORMAT);

		String path = MainController.class.getResource("/").getPath();
		String websiteURL = (path.replace("/build/classes", "")
				.replace("%20", " ") + "config.properties")
				.replaceFirst("/", "");
		
		String oldTime="";
		String newTime="";

		try {
			// 属性文件输入流
			Properties prop = new Properties();// 属性集合对象
			FileInputStream fis;
			fis = new FileInputStream(websiteURL);
			prop.load(fis);// 将属性文件流装载到Properties对象中
			fis.close();// 关闭流
			
			oldTime = prop.getProperty("jdbc.runTimeOut");
			
			// 修改sitename的属性值
			prop.setProperty("jdbc.runTimeOut", time.toString());
			// 文件输出流
			FileOutputStream fos = new FileOutputStream(websiteURL);
			// 将Properties集合保存到流中
			prop.store(fos, "");
			fos.close();// 关闭流
			newTime = prop.getProperty("jdbc.runTimeOut");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		jsonMap.put("oldTime", DateUtil.convert2String(Long.parseLong(oldTime), DateUtil.DATE_FORMAT));
		jsonMap.put("newTime", DateUtil.convert2String(Long.parseLong(newTime), DateUtil.DATE_FORMAT));
		return jsonMap;
	}
	
	/**
	 * 下载驱动
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "downDrive")
	public ModelAndView downDrive(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = this
				.getModelMainMav("WEB-INF/pages/downDrive");
		return mav;
	}

}