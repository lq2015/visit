package com.miaxis.visit.controller;

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
import com.miaxis.common.util.PageConfig;
import com.miaxis.common.util.QueryCondition;
import com.miaxis.visit.entity.UnitInfo;
import com.miaxis.visit.service.PublicService;
import com.miaxis.visit.service.UnitService;

/**
 * 访客单位管理
 * 
 * @author liu.qiao
 * 
 */

@Controller
@RequestMapping("/unit")
public class UnitController extends CommonController {
	@Autowired
	public UnitService unitService;
	@Autowired
	public PublicService publicService;

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
		ModelAndView mav = this
				.getModelMainMav("WEB-INF/pages/visit/unit/list");
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
			String parentMenu) {

		/**
		 * 初始化分页对象
		 */
		PageConfig pageConfig = new PageConfig();
		pageConfig.setPaging(false);

		QueryCondition qc = new QueryCondition();
		if (StringUtils.isNotEmpty(sort)) {
			if (StringUtils.equals(order.toUpperCase(), QueryCondition.DESC)) {
				qc.desc(sort);
			} else {
				qc.asc(sort);
			}
		}
		qc.asc("id");

		List list = commonService.getPageList(UnitInfo.class, pageConfig, qc);
		return this.buidResultMap(list, list.size());
	}

	/**
	 * 修改或新增
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "insertOrUpdate")
	public ModelAndView insertOrUpdate(Integer id, String operationType) {
		ModelAndView mav = new ModelAndView("WEB-INF/pages/visit/unit/detail");
		if (operationType.equals("edit")) {
			UnitInfo unitInfo = commonService.get(UnitInfo.class, id);
			mav.getModelMap().put("unitInfo", unitInfo);
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
		try {
			unitService.deleUnit(Integer.parseInt(id));
		} catch (BusinessException e) {
			return this.buidMessageMap(e.getMessage(),"1");
		} catch (Exception e) {
			return this.buidMessageMap("保存失败了", "1");
		}
		return this.buidMessageMap("服务单位信息删除成功!", "0");
	}

	/**
	 * 保存
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public Map save(UnitInfo unitInfo, String operationType) {
		String msg = operationType.equals("edit") ? "修改" : "新增";
		try {
			if (operationType.equals("edit")) {
				unitService.updateUnit(unitInfo);
			} else {
				unitService.addUnit(unitInfo);
			}
		} catch (BusinessException e) {
			return this.buidMessageMap(e.getMessage(), "1");
		} catch (Exception e) {
			e.printStackTrace();
			return this.buidMessageMap(msg + "服务单位失败", "1");
		}

		return this.buidMessageMap(msg + "服务单位成功", "0");
	}

	/**
	 * 修改记录状态
	 * 
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping(params = "updateStatus", method = RequestMethod.POST)
	@ResponseBody
	public Map updateStatus(Integer id, String status) {
		try {
			unitService.updateStatus(id, status);
		} catch (BusinessException e) {
			return this.buidMessageMap(e.getMessage(), "1");
		} catch (Exception e) {
			e.printStackTrace();
			return this.buidMessageMap("修改记录状态失败", "1");
		}
		return this.buidMessageMap("修改记录操作成功!", "0");
	}

}