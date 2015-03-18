package com.miaxis.common.base.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miaxis.common.base.dao.ICommonDao;
import com.miaxis.common.util.PageConfig;
import com.miaxis.common.util.QueryCondition;

@Service("commonService")
public class CommonServiceImpl implements CommonService {
	@Autowired
	public ICommonDao commonDao = null;

	public <T> T get(Class<T> entityName, Serializable id) {
		return commonDao.get(entityName, id);
	}

	public <T> T findUniqueByProperty(Class<T> entityClass,
			String propertyName, Object value) {
		return commonDao.findUniqueByProperty(entityClass, propertyName, value);
	}

	public <T> List<T> findByProperty(Class<T> entityClass,
			String propertyName, Object value) {
		return commonDao.findByProperty(entityClass, propertyName, value);
	}

	public <T> void delete(T entitie) {
		commonDao.delete(entitie);
	}

	public <T> void deleteEntityById(Class entityName, Serializable id) {
		commonDao.deleteEntityById(entityName, id);
	}

	public <T> void updateEntitie(T entity) {
		commonDao.updateEntitie(entity);
	}

	public <T> void updateEntityById(Class entityName, Serializable id) {
		commonDao.updateEntityById(entityName, id);
	}

	public <T> Serializable save(T entity) {
		return commonDao.save(entity);
	}

	public <T> void batchSave(List<T> entitys) {
		commonDao.batchSave(entitys);
	}
	
	public <T> List<T> getListBySql(Class<T> entityClass,String sql) {
		return commonDao.getListBySql(entityClass,sql);
	}

	public  List  getPageListBySql(PageConfig pageConfig, String sql) {
		return commonDao.getPageListBySql( pageConfig, sql);
	}

	public <T> List<T> getListByHql(Class<T> entityClass, String hql) {
		return commonDao.getListByHql(entityClass, hql);
	}

	public List getPageListByHql(PageConfig pageConfig, String hql) {
		return commonDao.getPageListByHql( pageConfig, hql);
	}

	public <T> List<T> getPageList(Class<T> entityClass,PageConfig pageConfig, QueryCondition queryCondition) {
		return commonDao.getPageList(entityClass,pageConfig, queryCondition);
	}

	public void executeHql(String hql) {
		commonDao.executeHql(hql);
	}

	public void executeSql(String sql) {
		commonDao.executeSql(sql);
	}

	public Object getColumnValueByHql(String hql) {
		return commonDao.getColumnValueByHql(hql);
	}

	public <T> List<T> getPageListBySql(Class<T> entityClass,
			PageConfig pageConfig, String sql) {
		return commonDao.getPageListBySql(entityClass, pageConfig, sql);
	}
}
