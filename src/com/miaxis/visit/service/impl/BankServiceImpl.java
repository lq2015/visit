package com.miaxis.visit.service.impl;

import org.springframework.stereotype.Service;

import com.miaxis.common.base.service.CommonServiceImpl;
import com.miaxis.common.exception.BusinessException;
import com.miaxis.visit.entity.BankInfo;
import com.miaxis.visit.service.BankService;

@Service("bankService")
public class BankServiceImpl extends CommonServiceImpl implements BankService {

	@Override
	public void addBank(BankInfo bankInfo) {
		String id = bankInfo.getId();
		BankInfo bank = this.get(BankInfo.class, id);
		if(bank!=null){
			throw new BusinessException("网点编码【"+id+"】已经存在,请更换新的网点编码。" );
		}
		
		bankInfo.setBiStatus(BankInfo.Status.INPUT.getCode());
		this.commonDao.save(bankInfo);
	}

	@Override
	public void updateBank(BankInfo bankInfo) {
		String id = bankInfo.getId();
		BankInfo bank = this.get(BankInfo.class, id);
		if(bank==null){
			throw new BusinessException("网点编码【"+id+"】的网点信息不存在。" );
		}
		
		bankInfo.setBiStatus(bank.getBiStatus());
		this.commonDao.updateEntitie(bankInfo);
	}

}
