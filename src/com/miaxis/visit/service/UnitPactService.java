package com.miaxis.visit.service;

import java.util.List;

import com.miaxis.common.base.service.CommonService;
import com.miaxis.visit.entity.UnitPact;

/**
 * 服务单位合同管理
 * @author liu.qiao
 * 
 */
public interface UnitPactService extends CommonService {
	public void addUnitPact(UnitPact unitPact,List list);
	public void updateUnitPact(UnitPact unitPact,List list);
	public void deleUnitPact(Integer id);
	public void updateStatus(Integer id, String status);
}
