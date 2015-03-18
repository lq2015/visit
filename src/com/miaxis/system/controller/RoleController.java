package com.miaxis.system.controller;

import java.util.HashMap;
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
import com.miaxis.common.util.PageConfig;
import com.miaxis.common.util.QueryCondition;
import com.miaxis.system.entity.Role;
import com.miaxis.system.entity.RoleFunAuth;
import com.miaxis.system.entity.RoleMenuAuth;
import com.miaxis.system.service.SystemService;

/**
 * 系统角色
 * 
 * @author liu.qiao
 * 
 */

@Controller
@RequestMapping("/role")
public class RoleController extends CommonController {
	@Autowired
	private SystemService systemService;
	
	/**
	 * 主页
	 * 
	 * @param role
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "main")
	public ModelAndView main(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = this.getModelMainMav("WEB-INF/pages/system/role/roleList");
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
	public Map list(String page, String sort, String order, String rows) {

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
		
		List list = commonService.getPageList(Role.class,pageConfig,qc);
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
		Role role = commonService.get(Role.class, id);
		ModelAndView mav = new ModelAndView(
				"WEB-INF/pages/system/role/roleDetail");
		mav.getModelMap().put("role", role);
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
		ModelAndView mav = new ModelAndView(
				"WEB-INF/pages/system/role/roleDetail");
		if (operationType.equals("edit")) {
			Role role = commonService.get(Role.class, id);
			mav.getModelMap().put("role", role);
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
		Role role = (Role) commonService.get(Role.class, id);

		if (role == null) {
			return this.buidMessageMap("该角色信息不存在!", "1");
		} else {
			try {
				commonService.delete(role);
			} catch (Exception e) {
				return this.buidMessageMap("保存失败了", "1");
			}
			return this.buidMessageMap("角色【" + role.getRoleName() + "】信息删除成功!",
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
	public Map save(Role role, String operationType) {
		String msg = operationType.equals("edit") ? "修改" : "新增";
		try {
			if (operationType.equals("edit")) {
				String message = "";
				Role r = (Role) commonService.get(Role.class, role.getId());
				if (r == null) {
					return this.buidMessageMap("该角色信息不存在!", "1");
				} else {
					commonService.updateEntitie(role);
				}
			} else {
				commonService.save(role);
			}
		} catch (Exception e) {
			return this.buidMessageMap(msg + "角色失败", "1");
		}

		return this.buidMessageMap(msg + "角色成功", "0");
	}
	
	/**
	 * 权限设置
	 * 
	 * @param role
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "authority")
	public ModelAndView authority(HttpServletRequest request,
			HttpServletResponse response) {
		String roleId = request.getParameter("roleId");
		String authIds="";
		
		List<RoleMenuAuth> list =  commonService.findByProperty(RoleMenuAuth.class, "roleId", roleId);
		for(RoleMenuAuth rma : list){
			if(authIds.equals("")){
				authIds = rma.getMenuId();
			}else{
				authIds = authIds +","+ rma.getMenuId();
			}
		}
		
		ModelAndView mav = new ModelAndView("WEB-INF/pages/system/role/authority");
		mav.getModelMap().put("roleId", roleId);
		mav.getModelMap().put("authIds", authIds);
		return mav;
	}
	
	/**
	 * 得到角色功能点权限
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "getRoleFunAuthIds")
	@ResponseBody
	public Map getRoleFunAuthIds(String menuId ,String roleId) {
		QueryCondition qc = new QueryCondition();
		qc.eq("roleId", roleId);
		qc.eq("menuId", menuId);
		List<RoleFunAuth> list =  commonService.getPageList(RoleFunAuth.class,null,qc);
		
		String ids="";
		for(RoleFunAuth rfa : list){
			if(ids.equals("")){
				ids = rfa.getFunId();
			}else{
				ids = ids +","+ rfa.getFunId();
			}
		}
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("data", ids);
		return jsonMap;
	}
	
	/**
	 * 保存角色权限
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "saveRoleMenuAuth")
	@ResponseBody
	public Map saveRoleMenuAuth(String menuIds ,String roleId) {
		try {
			systemService.saveRoleMenuAuth(menuIds, roleId);
		} catch (Exception e) {
			return this.buidMessageMap("保存菜单权限失败", "1");
		}

		return this.buidMessageMap("保存菜单权限成功", "0");
	}
	
	/**
	 * 保存角色权限
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "saveRoleFunAuth")
	@ResponseBody
	public Map saveRoleFunAuth(String funIds ,String roleId, String menuId) {
		try {
			systemService.saveRoleFunAuth(funIds, roleId, menuId);
		} catch (Exception e) {
			return this.buidMessageMap("保存菜单功能点权限失败", "1");
		}

		return this.buidMessageMap("保存菜单功能点权限成功", "0");
	}
	
	/**
	 * 获取所有角色
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "listAll")
	@ResponseBody
	public Map listAll(String page, String sort, String order, String rows) {

		/**
		 * 初始化分页对象
		 */
		PageConfig pageConfig = new PageConfig();
		pageConfig.setPaging(false);
		
		QueryCondition qc = new QueryCondition();
		if(StringUtils.isNotEmpty(sort)){
			if (StringUtils.equals(order.toUpperCase(), QueryCondition.DESC)){
				qc.desc(sort);
			}else{
				qc.asc(sort);
			}
		}
		
		List list = commonService.getPageList(Role.class,pageConfig,qc);
		return this.buidResultMap(list,pageConfig.getTotalCount());
	}

	
}