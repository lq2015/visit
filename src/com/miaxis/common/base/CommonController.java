package com.miaxis.common.base;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;

import com.miaxis.common.base.service.CommonService;
import com.miaxis.common.util.ContextHolderUtils;
import com.miaxis.system.entity.User;

public class CommonController {
	@Autowired
	public CommonService commonService;
	
	protected static final Logger logger = Logger
			.getLogger(CommonController.class);
	
	/**
	 * 解决SingMvc 
	 * @param request
	 * @param binder
	 */
	@InitBinder
	protected void init(HttpServletRequest request, ServletRequestDataBinder binder){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		   dateFormat.setLenient(false);
		   binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	/**
	 * 构建json数据Map
	 * 
	 * @param resultList
	 * @return
	 */
	public Map buidResultMap(List resultList,Integer total) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		if (resultList != null) {
			jsonMap.put("rows", resultList);
			jsonMap.put("total", total);
		} else {
			jsonMap.put("rows", "");
			jsonMap.put("total", "0");
		}
		return jsonMap;
	}

	/**
	 * 构建返回消息Map
	 * 
	 * @param message
	 * @param result
	 * @return
	 */
	public Map buidMessageMap(String message, String result) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("message", message);
		jsonMap.put("result", result);
		return jsonMap;
	}
	
	/**
	 * 构建返回消息Map
	 * 
	 * @param message
	 * @param result
	 * @return
	 */
	public Map buidMessageMap(String message, Map result) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("message", message);
		jsonMap.put("result", result);
		return jsonMap;
	}
	
	/**
	 * 获取登陆用户
	 * @return
	 */
	public User getLoginUser(){
		HttpServletRequest request = ContextHolderUtils.getRequest();
		User user = (User) request.getSession().getAttribute("userSession");
		return user;
	}
	
	/**
	 * 模块主入口，得到模块的菜单功能点权限
	 * @param url
	 * @return
	 */
	public ModelAndView getModelMainMav(String url){
		HttpServletRequest request = ContextHolderUtils.getRequest();
		ModelAndView mav = new ModelAndView(url);
		String menuId = request.getParameter("menuId");
		mav.getModelMap().put("menuId",menuId);
		return mav; 
	}
}
