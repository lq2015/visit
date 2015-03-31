package com.miaxis.visit.service;

import com.miaxis.common.base.service.CommonService;
import com.miaxis.visit.entity.BankInfo;

/**
 * 
 * @author liu.qiao
 * 
 */
public interface BankService extends CommonService {
	public void addBank(BankInfo bankInfo);
	public void updateBank(BankInfo bankInfo);
}
