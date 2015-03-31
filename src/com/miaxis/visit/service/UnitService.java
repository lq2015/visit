package com.miaxis.visit.service;

import com.miaxis.common.base.service.CommonService;
import com.miaxis.visit.entity.UnitInfo;

/**
 * 服务单位管理
 * @author liu.qiao
 * 
 */
public interface UnitService extends CommonService {
	public void addUnit(UnitInfo unitInfo);
	public void updateUnit(UnitInfo unitInfo);
	public void deleUnit(Integer id);
	public void updateStatus(Integer id, String status);
}
