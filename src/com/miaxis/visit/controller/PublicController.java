package com.miaxis.visit.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.miaxis.common.base.CommonController;
import com.miaxis.common.util.Base64Utils;
import com.miaxis.common.util.CommonUtil;
import com.miaxis.common.util.ImageUtils;
import com.miaxis.common.util.QueryCondition;
import com.miaxis.system.entity.User;
import com.miaxis.visit.entity.BankInfo;
import com.miaxis.visit.entity.DepartmentInfo;

/**
 * 业务系统公用
 * 
 * @author liu.qiao
 * 
 */

@Controller
@RequestMapping("/public")
public class PublicController extends CommonController {

	/**
	 * 获取用户管理所需的部门
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "userDept")
	@ResponseBody
	public List userDept(String persontype) {
		ArrayList list = new ArrayList();
		if(persontype.equals("1")){
			List<DepartmentInfo> list1 = (List<DepartmentInfo>) commonService
					.getPageList(DepartmentInfo.class, null, null);
			
			for(DepartmentInfo departmentInfo :list1){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", departmentInfo.getId());
				map.put("name", departmentInfo.getDiName());
				list.add(map);
			}
		}
		
		if(persontype.equals("2")){
			List<BankInfo> list2 = (List<BankInfo>) commonService
					.getPageList(BankInfo.class, null, null);
			
			for(BankInfo bankInfo :list2){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", bankInfo.getId());
				map.put("name", bankInfo.getBiName());
				list.add(map);
			}
		}

		return list;
	}
}