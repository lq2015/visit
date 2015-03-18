package com.miaxis.common.taglib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.miaxis.common.util.ContextHolderUtils;
import com.miaxis.system.entity.Function;
import com.miaxis.system.entity.User;
import com.miaxis.system.service.SystemService;

/**
 * 得到用户按钮权限
 * @author liu.qiao
 *
 */
public class UserMenuFunAuth extends TagSupport {
	private static final long serialVersionUID = 1L;
	private String menuId = null;
	
	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	@SuppressWarnings("unchecked")
	public int doEndTag() throws JspException {
		JspWriter out = pageContext.getOut();
		
		StringBuffer funHtml= new StringBuffer();
		
		try {
			List<Function> list = this.getUserMenuFun(menuId);
			for(Function fun :list){
				//普通按钮
				if(fun.getFunType().equals("1")){
					funHtml.append("\n <a href='javascript:void(0)' class='easyui-linkbutton' plain='true' ");
					funHtml.append("\n id='"+fun.getId()+"' iconCls='"+fun.getIcoIndex()+"'");
					funHtml.append("\n onclick=\""+fun.getFunUrl()+"\">"+fun.getFunNameCn()+"</a>");
				}
				
				//下拉式按钮
				if(fun.getFunType().equals("2")){
					funHtml.append("\n <a href='javascript:void(0)' class='easyui-menubutton' plain='true' ");
					funHtml.append("\n menu='#mm"+fun.getId()+"'  iconCls='"+fun.getIcoIndex()+"'");
					funHtml.append("\n id='"+fun.getId()+"'>"+fun.getFunNameCn()+"</a>");
					
					//生成子菜单
					funHtml.append("\n <div id='mm"+fun.getId()+"' style='width: 100px;' iconCls='"+fun.getIcoIndex()+"'>");
					
					StringBuffer menuItemHtml= new StringBuffer();
					List<Function> menuItemList = fun.getChildren();
					for(Function funMenuItem : menuItemList){
						if(funMenuItem.getChildren().size()==0){
							menuItemHtml.append("\n <div data-options=\"iconCls:'"+funMenuItem.getIcoIndex()+"'\" ");
							menuItemHtml.append("\n onclick=\""+funMenuItem.getFunUrl()+"\">"+funMenuItem.getFunNameCn()+"</div>");
						}else{
							String funMenuItemChilds = this.buildFunItemHtml(funMenuItem);
							menuItemHtml.append("\n " + funMenuItemChilds);
						}
					}
					funHtml.append(menuItemHtml);
					
					funHtml.append("\n </div>");
				}
			}
//			System.out.println("======funHtml========"+funHtml.toString());
			out.print(funHtml.toString());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return EVAL_PAGE;
	}
	
	/**
	 * 递归生成下拉子单Html
	 * @param list
	 * @return
	 */
	public String buildFunItemHtml(Function funMenuItem){
		StringBuffer funHtml= new StringBuffer();
		funHtml.append("\n <div>");
		funHtml.append("\n 	 <span>" +funMenuItem.getFunNameCn()+"</span>");
		funHtml.append("\n 	<div style='width:150px;'>");
		
		List<Function> list = funMenuItem.getChildren();
		for(Function fun :list){
			if(fun.getChildren().size()<1){
				funHtml.append("\n 	  <div data-options=\" iconCls:'"+fun.getIcoIndex()+"'\" onclick=\""+fun.getFunUrl()+"\">" +fun.getFunNameCn()+ "</div>");
			}else{
				String funMenuItemChilds = this.buildFunItemHtml(fun);
				funHtml.append(funMenuItemChilds);
			}
		}
		
		funHtml.append("\n 		</div>");
		funHtml.append("\n </div>");
		return funHtml.toString();
	}
	
	/**
	 * 获取用户菜单功能点权限
	 * @param menuId
	 * @return
	 */
	public List<Function> getUserMenuFun(String menuId){
		HttpSession session = ContextHolderUtils.getRequest().getSession();
		ServletContext servletContext = session.getServletContext();
		
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		SystemService systemService = (SystemService)ctx.getBean("systemService");  
		
		User user = (User) session.getAttribute("userSession");
		List<Function> list =  systemService.getRoleFunAuth(user, menuId);
		
		List<Function> funList = new ArrayList();
		for(Function fun : list){
			if(!StringUtils.isNotEmpty(fun.getParentFun())){
				fun = getFunChildren(list,fun);
				funList.add(fun);
			}
		}
		return funList;
	}
	
	/**
	 * 用户菜单功能点权限递归
	 * @param list
	 * @param function
	 * @return
	 */
	public Function getFunChildren(List<Function> list ,Function function){
		List<Function> funList = new ArrayList();
		for(Function fun : list){
			if(function.getId().equals(fun.getParentFun())){
				funList.add(getFunChildren(list,fun));
			}
		}
		function.setChildren(funList);
		return function;
	}
}
