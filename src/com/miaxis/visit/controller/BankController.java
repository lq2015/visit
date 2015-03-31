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
import com.miaxis.visit.entity.BankInfo;
import com.miaxis.visit.service.BankService;

/**
 * 银行网点
 * 
 * @author liu.qiao
 * 
 */

@Controller
@RequestMapping("/bank")
public class BankController extends CommonController {
	
	@Autowired
	public BankService bankService;
	
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
				.getModelMainMav("WEB-INF/pages/visit/bank/list");
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
			String qBiStatus, String qBiName) {

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

		if (StringUtils.isNotEmpty(qBiName)) {
			qc.like("biName", qBiName.concat("%"));
		}
		if (StringUtils.isNotEmpty(qBiStatus)) {
			qc.eq("biStatus", qBiStatus);
		}

		List list = commonService.getPageList(BankInfo.class, pageConfig, qc);
		return this.buidResultMap(list, list.size());
	}

	/**
	 * 修改或新增
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "insertOrUpdate")
	public ModelAndView insertOrUpdate(String id, String operationType) {
		ModelAndView mav = new ModelAndView("WEB-INF/pages/visit/bank/detail");
		if (operationType.equals("edit")) {
			BankInfo bankInfo = commonService.get(BankInfo.class, id);
			mav.getModelMap().put("bankInfo", bankInfo);
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
		BankInfo bankInfo = (BankInfo) commonService.get(BankInfo.class, id);

		if (bankInfo == null) {
			return this.buidMessageMap("该网点信息不存在!", "1");
		} else {
			try {
				commonService.delete(bankInfo);
			} catch (Exception e) {
				e.printStackTrace();
				return this.buidMessageMap("保存失败了", "1");
			}
			return this.buidMessageMap("网点【" + bankInfo.getBiName()
					+ "】信息删除成功!", "0");
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
	public Map save(BankInfo bankInfo, String operationType) {
		String msg = operationType.equals("edit") ? "修改" : "新增";
		try {
			if (operationType.equals("edit")) {
				bankService.updateBank(bankInfo);
			} else {
				bankService.addBank(bankInfo);
			}
		} catch (BusinessException e) {
			return this.buidMessageMap(e.getMessage() , "1");
		} catch (Exception e) {
			e.printStackTrace();
			return this.buidMessageMap(msg + "网点失败", "1");
		}

		return this.buidMessageMap(msg + "网点成功", "0");
	}
	
	/**
	 * 修改记录状态
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping(params = "updateStatus", method = RequestMethod.POST)
	@ResponseBody
	public Map updateStatus(String id,String status) {
		BankInfo bankInfo = commonService.get(BankInfo.class, id);
		if (bankInfo == null) {
			return this.buidMessageMap("该记录信息不存在!", "1");
		} else {
			try {
				bankInfo.setBiStatus(status);
				commonService.updateEntitie(bankInfo);
			} catch (Exception e) {
				e.printStackTrace();
				return this.buidMessageMap("修改记录状态失败", "1");
			}
			return this.buidMessageMap("修改记录操作成功!","0");
		}
	}

}