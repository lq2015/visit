package com.miaxis.visit.service;

import com.miaxis.common.base.service.CommonService;
import com.miaxis.visit.entity.ServeCategory;

/**
 * 
 * @author liu.qiao
 * 
 */
public interface ServeCategoryService extends CommonService {
	public void addServeCategory(ServeCategory serveCategory);
	public void updateServeCategory(ServeCategory serveCategory);
	public void deleteServeCategory(String id);
}
