package com.miaxis.visit.service.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.miaxis.common.base.service.CommonServiceImpl;
import com.miaxis.common.exception.BusinessException;
import com.miaxis.common.util.ContextHolderUtils;
import com.miaxis.system.entity.User;
import com.miaxis.visit.entity.PersonInfo;
import com.miaxis.visit.entity.UnitInfo;
import com.miaxis.visit.service.UnitService;

@Service("unitService")
public class UnitServiceImpl extends CommonServiceImpl implements UnitService {

	@Override
	public void addUnit(UnitInfo unitInfo) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		User user = (User) request.getSession().getAttribute("userSession");
		
		unitInfo.setUiCrdate(new Date());
		unitInfo.setUiOperateTime(new Date());
		unitInfo.setUiOperator(user.getId());
		unitInfo.setUiStatus(UnitInfo.Status.NORMAL.getCode());
		this.commonDao.save(unitInfo);
	}

	@Override
	public void updateUnit(UnitInfo unitInfo) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		User user = (User) request.getSession().getAttribute("userSession");
		
		Integer id = unitInfo.getId();
		UnitInfo unit = this.get(UnitInfo.class, id);
		if(unit==null){
			throw new BusinessException("单位【"+unitInfo.getUiName()+"】的信息不存在。" );
		}
		
		unitInfo.setUiCrdate(unit.getUiCrdate());
		unitInfo.setUiOperateTime(new Date());
		unitInfo.setUiOperator(user.getId());
		unitInfo.setUiStatus(unit.getUiStatus());
		this.commonDao.updateEntitie(unitInfo);
	}

	@Override
	public void deleUnit(Integer id) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		User user = (User) request.getSession().getAttribute("userSession");
		
		UnitInfo unitInfo = this.commonDao.get(UnitInfo.class, id);
		if (unitInfo == null) {
			throw new BusinessException("要删除服务单位信息不存在!");
		} else {
			this.commonDao.delete(unitInfo);
		}
	}

	@Override
	public void updateStatus(Integer id, String status) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		User user = (User) request.getSession().getAttribute("userSession");
		
		UnitInfo unitInfo = this.commonDao.get(UnitInfo.class, id);
		if (unitInfo == null) {
			throw new BusinessException("要修改服务单位信息不存在!");
		} else {
			unitInfo.setUiStatus(status);
			this.commonDao.updateEntitie(unitInfo);
		}
	}
}
