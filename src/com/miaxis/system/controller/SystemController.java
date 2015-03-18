package com.miaxis.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.miaxis.common.base.CommonController;
import com.miaxis.common.util.PageConfig;
import com.miaxis.common.util.QueryCondition;
import com.miaxis.system.entity.Function;
import com.miaxis.system.entity.Log;
import com.miaxis.system.entity.Menu;
import com.miaxis.system.service.SystemService;

/**
 * 系统
 * 
 * @author liu.qiao
 * 
 */

@Controller
@RequestMapping("/system")
public class SystemController extends CommonController {
	@Autowired
	private SystemService systemService;

	/**
	 * 主页
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "logMain")
	public ModelAndView main(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = this.getModelMainMav("WEB-INF/pages/system/log/logList");
		return mav;
	}

	/**
	 * 获取列表
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "logList")
	@ResponseBody
	public Map logList(String page, String sort, String order, String rows,
			String qLogOperateType, String qName) {

		/**
		 * 初始化分页对象
		 */
		PageConfig pageConfig = new PageConfig();
		pageConfig.initData(page, rows, true);
		
		/***************************************************************
		 * 按原始SQL查询
		 ***************************************************************
		String sql="select a.*,b.realName from t_s_log a,t_s_user b where a.userid=b.id ";
		if (StringUtils.isNotEmpty(qLogOperateType)) {
			sql = sql + " and operatetype= "+ qLogOperateType;
		}
		if (StringUtils.isNotEmpty(qName)) {
			sql = sql + " and realName like  '"+ qName.concat("%"+"'");
		}
		List list = systemService.getPageListBySql( pageConfig, sql);*/
		
		/***************************************************************
		 * 按HQL查询
		 ***************************************************************
		String hql="select a  from Log a,User b where a.userid=b.id ";
		if (StringUtils.isNotEmpty(qLogOperateType)) {
			hql = hql + " and a.operatetype= "+ qLogOperateType;
		}
		if (StringUtils.isNotEmpty(qName)) {
			hql = hql + " and b.realName like  '"+ qName.concat("%"+"'");
		}
		List list = systemService.getPageListByHql(pageConfig, hql);*/

		/***************************************************************
		 * 按QBC查询
		 ***************************************************************/
		QueryCondition qc = new QueryCondition();
		
		if(StringUtils.isNotEmpty(sort)){
			if (StringUtils.equals(order.toUpperCase(), QueryCondition.DESC)){
				qc.desc(sort);
			}else{
				qc.asc(sort);
			}
		}else{
			qc.desc("operatetime");
		}
		
		if (StringUtils.isNotEmpty(qLogOperateType)) {
			qc.eq("operatetype", Integer.parseInt(qLogOperateType));
		}
		if (StringUtils.isNotEmpty(qName)) {
			qc.like("user.userName", qName.concat("%"));
		}
		
		List list = systemService.getPageList(Log.class,pageConfig, qc);
		return this.buidResultMap(list,pageConfig.getTotalCount());
	}
	
	/**
	 * 得到菜单树
	 * @param levels
	 * @return
	 */
	@RequestMapping(params = "menuComBoTree")
	@ResponseBody
	public ArrayList menuComBoTree(String tag) {
		
		/**
		 * 初始化分页对象
		 */
		PageConfig pageConfig = new PageConfig();
		pageConfig.setPaging(false);
		
		QueryCondition qc = new QueryCondition();
		qc.asc("orderNum");
		
		List<Menu> list = systemService.getPageList(Menu.class,pageConfig ,qc);
		
		Menu parentMenu = new Menu();
		parentMenu.setId("");
		parentMenu.setMenuName("系统菜单");
		
		ArrayList list1 = getMenuMap(parentMenu,list);
		
		ArrayList list0 = new ArrayList();
		Map<String, Object> map0 = new HashMap<String, Object>();
		
		if (StringUtils.isNotEmpty(tag)) {
			map0.put("id",parentMenu.getIcoIndex());
			map0.put("text", parentMenu.getMenuName());
			map0.put("children", list1);
			list0.add(map0);
			return list0;
		}else{
			return list1;
		}
		
	}
	
	/**
	 * 递归取生成菜单ComBoTree的ArrayList
	 * @param parentMenu
	 * @param list
	 * @return
	 */
	public ArrayList getMenuMap(Menu parentMenu, List<Menu> list  ){
		ArrayList parentList = new ArrayList();
		for (Menu childrenMenu : list) {
			ArrayList chirldList = null;
			Map<String, Object>  map = new HashMap<String, Object>();
			
			String parentMenuId = childrenMenu.getParentMenu();
			if(parentMenuId==null) parentMenuId="";
			
			if(parentMenuId.trim().equals(parentMenu.getId())){
				map.put("id", childrenMenu.getId());
				map.put("text", childrenMenu.getMenuName());
				map.put("iconCls", childrenMenu.getIcoIndex());
				map.put("menulevel", childrenMenu.getMenuLevel());
				chirldList = getMenuMap(childrenMenu,list);
				if(chirldList!=null){
					map.put("children", chirldList);
				}
				parentList.add(map);
			}
		}
		
		return parentList;
	}
	
	/**
	 * 得到菜单功能树
	 * @param levels
	 * @return
	 */
	@RequestMapping(params = "funComBoTree")
	@ResponseBody
	public ArrayList funComBoTree(String parentMenu) {
		
		/**
		 * 初始化分页对象
		 */
		PageConfig pageConfig = new PageConfig();
		pageConfig.setPaging(false);
		
		QueryCondition qc = new QueryCondition();
		if (StringUtils.isNotEmpty(parentMenu)) {
			qc.in("funType",new String[]{"2","3"}); //只查询下拉式按钮 和下拉子菜单
			qc.eq("parentMenu", parentMenu);
		}
		qc.asc("orderNum");
		
		List<Function> list = systemService.getPageList(Function.class,pageConfig ,qc);
		
		Function fun = new Function();
		fun.setId("");
		return getFunMap(fun,list);
	}
	
	/**
	 * 递归取生成菜单功能点ComBoTree的ArrayList
	 * @param parentMenu
	 * @param list
	 * @return
	 */
	public ArrayList getFunMap(Function parentFun, List<Function> list  ){
		ArrayList parentList = new ArrayList();
		for (Function childrenFun : list) {
			ArrayList chirldList = null;
			Map<String, Object>  map = new HashMap<String, Object>();
			
			String parentFunId = childrenFun.getParentFun();
			if(parentFunId==null) parentFunId="";
			
			if(parentFunId.trim().equals(parentFun.getId())){
				map.put("id", childrenFun.getId());
				map.put("text", childrenFun.getFunNameCn());
				map.put("iconCls", childrenFun.getIcoIndex());
				chirldList = getFunMap(childrenFun,list);
				if(chirldList!=null){
					map.put("children", chirldList);
				}
				parentList.add(map);
			}
		}
		
		return parentList;
	}
}