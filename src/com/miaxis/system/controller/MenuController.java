package com.miaxis.system.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.miaxis.common.base.CommonController;
import com.miaxis.common.util.PageConfig;
import com.miaxis.common.util.QueryCondition;
import com.miaxis.system.entity.Menu;

/**
 * 系统菜单
 * 
 * @author liu.qiao
 * 
 */

@Controller
@RequestMapping("/menu")
public class MenuController extends CommonController {
	/**
	 * 主页
	 * 
	 * @param menu
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "main")
	public ModelAndView main(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = this.getModelMainMav("WEB-INF/pages/system/menu/menuList");
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
			String qMenuLevel, String qIsValid) {

		/**
		 * 初始化分页对象
		 */
		PageConfig pageConfig = new PageConfig();
		pageConfig.initData(page, rows, false);
		
		QueryCondition qc = new QueryCondition();
		if(StringUtils.isNotEmpty(sort)){
			if (StringUtils.equals(order.toUpperCase(), QueryCondition.DESC)){
				qc.desc(sort);
			}else{
				qc.asc(sort);
			}
		}
		qc.asc("orderNum");
		
		if (StringUtils.isNotEmpty(qMenuLevel)) {
			qc.eq("menuLevel", qMenuLevel);
		}
		if (StringUtils.isNotEmpty(qIsValid)) {
			qc.eq("isValid", qIsValid);
		}
		
		

		List list = commonService.getPageList(Menu.class,pageConfig,qc);
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
		Menu menu = commonService.get(Menu.class, id);
		ModelAndView mav = new ModelAndView(
				"WEB-INF/pages/system/menu/menuDetail");
		mav.getModelMap().put("menu", menu);
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
				"WEB-INF/pages/system/menu/menuDetail");
		if (operationType.equals("edit")) {
			Menu menu = commonService.get(Menu.class, id);
			mav.getModelMap().put("menu", menu);
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
		Menu menu = (Menu) commonService.get(Menu.class, id);

		if (menu == null) {
			return this.buidMessageMap("该菜单信息不存在!", "1");
		} else {
			try {
				commonService.delete(menu);
			} catch (Exception e) {
				return this.buidMessageMap("保存失败了", "1");
			}
			return this.buidMessageMap("菜单【" + menu.getMenuName() + "】信息删除成功!",
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
	public Map save(Menu menu, String operationType) {
		String msg = operationType.equals("edit") ? "修改" : "新增";
		try {
			
			//根据父菜单重置层级
			Integer level = 1 ;
			if(StringUtils.isNotEmpty(menu.getParentMenu())){
				Menu parent = (Menu) commonService.get(Menu.class, menu.getParentMenu());
				if(parent!=null){
					level = parent.getMenuLevel() +1;
				}
			}
			menu.setMenuLevel(level);
			
			if (operationType.equals("edit")) {
				String message = "";
				Menu r = (Menu) commonService.get(Menu.class, menu.getId());
				
				if (r == null) {
					return this.buidMessageMap("该菜单信息不存在!", "1");
				} else {
					menu.setLinkPage(menu.getLinkPage().replace("\"", "'"));
					commonService.updateEntitie(menu);
				}
			} else {
				menu.setLinkPage(menu.getLinkPage().replace("\"", "'"));
				commonService.save(menu);
			}
		} catch (Exception e) {
			return this.buidMessageMap(msg + "菜单失败", "1");
		}

		return this.buidMessageMap(msg + "菜单成功", "0");
	}
	
}