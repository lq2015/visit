package com.miaxis.common.springmvc;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.miaxis.common.util.DateUtil;
import com.miaxis.system.entity.User;

public class Interceptor extends HandlerInterceptorAdapter {
	private List<String> excludeUrls;
	private String runTimeOut;

	public String getRunTimeOut() {
		return runTimeOut;
	}

	public void setRunTimeOut(String runTimeOut) {
		this.runTimeOut = runTimeOut;
	}

	public List<String> getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
//		System.out.println("拦截器结束。。。。");
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView mav) throws Exception {
//		System.out.println("拦截器工作。。。。");
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
//		System.out.println("拦截器开启。。。。");
		String requestPath = getRequestPath(request);// 用户访问的资源地址
		User user = (User)request.getSession().getAttribute("userSession");
		
		if(requestPath.indexOf("qc.do")>=0){
			return true;
		}
		
		if (excludeUrls.contains(requestPath)) {
			return true;
		} else {
			if (user == null) {
				response.sendRedirect("main.do?login");
				return false;
			}else{
				/****************************************************
				 * 检测Session的用户的ID或用户名是否为空
				 ****************************************************/
				String realName = user.getRealName();
				String userId = user.getId();
				if(realName==null) realName="";
				if(userId==null) userId="";
				
				if(realName.equals("") || userId.equals("") ){
					response.sendRedirect("main.do?login");
					return false;
				}
				
				/****************************************************
				 * 登陆时检测LIC授权
				 ****************************************************/
				if(!"main.do?login".equals(requestPath)){
					if(!user.getUserName().equals("super")){
						boolean isPass = checkSystemLic(runTimeOut);
						if(!isPass){
							response.sendRedirect("main.do?login");
							return false;
						}
					}
				}
			}
		}
		
		
		return true;
	}
	
	/**
	 * 获得请求路径
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestPath(HttpServletRequest request) {
		String requestPath = request.getRequestURI() + "?" + request.getQueryString();
		if (requestPath.indexOf("&") > -1) {// 去掉其他参数
			requestPath = requestPath.substring(0, requestPath.indexOf("&"));
		}
		requestPath = requestPath.substring(request.getContextPath().length() + 1);// 去掉项目路径
		return requestPath;
	}
	
	/**
	 * SYSTEM VERSION
	 * @return
	 */
	private boolean checkSystemLic(String time){
		if(time==null) time="";
		if(time.equals("")) {
			time="1433088000000";
		} 
		
		String endDateStr = DateUtil.convert2String(Long.parseLong(time), DateUtil.DATE_FORMAT);
		Date endDate = DateUtil.getShortDate(endDateStr);
		if(endDate==null) return false;
		
//		System.out.println(endDateStr);
//		System.out.println(DateUtil.getYYYY_MM_DD(new Date()));
		
		if(endDate.before(new Date())){
			return false;
		}
		
		return true;
	}
}
