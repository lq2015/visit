package com.miaxis.visit.service;

import com.miaxis.common.base.service.CommonService;
import com.miaxis.visit.entity.DepartmentInfo;

/**
 * 
 * @author liu.qiao
 * 
 */
public interface DepartmentService extends CommonService {
	public void addDepartment(DepartmentInfo departmentInfo);
	public void updateDepartment(DepartmentInfo departmentInfo);
}
