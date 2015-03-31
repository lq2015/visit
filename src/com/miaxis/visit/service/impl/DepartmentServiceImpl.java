package com.miaxis.visit.service.impl;

import org.springframework.stereotype.Service;

import com.miaxis.common.base.service.CommonServiceImpl;
import com.miaxis.common.exception.BusinessException;
import com.miaxis.visit.entity.DepartmentInfo;
import com.miaxis.visit.service.DepartmentService;

@Service("departmentService")
public class DepartmentServiceImpl extends CommonServiceImpl implements DepartmentService {

	@Override
	public void addDepartment(DepartmentInfo departmentInfo) {
		String id = departmentInfo.getId();
		DepartmentInfo department = this.get(DepartmentInfo.class, id);
		if(department!=null){
			throw new BusinessException("部门编码【"+id+"】已经存在,请更换新的部门编码。" );
		}
		
		departmentInfo.setDiStatus(DepartmentInfo.Status.INPUT.getCode());
		this.commonDao.save(departmentInfo);
	}

	@Override
	public void updateDepartment(DepartmentInfo departmentInfo) {
		String id = departmentInfo.getId();
		DepartmentInfo department = this.get(DepartmentInfo.class, id);
		if(department==null){
			throw new BusinessException("部门编码【"+id+"】的部门信息不存在。" );
		}
		
		departmentInfo.setDiStatus(department.getDiStatus());
		this.commonDao.updateEntitie(departmentInfo);
	}

}
