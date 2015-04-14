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
 * 首页protal
 * 
 * @author liu.qiao
 * 
 */

@Controller
@RequestMapping("/protal")
public class ProtalController extends CommonController {
	@Autowired
	public PublicService publicService;

	/**
	 * 获取列表
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "panel1")
	@ResponseBody
	public Map panel1() {
		String sql="";

		return null;
	}
}