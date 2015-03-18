package com.miaxis.common.base.dao;

import java.util.List;

import com.miaxis.common.util.QueryCondition;
import com.miaxis.common.util.PageConfig;

public interface ICommonDao extends IGenericBaseCommonDao {
	/**
	 * 根据HQL分页查询
	 * @param <T>
	 * @param entityClass
	 * @param pageConfig
	 * @param sql
	 * @return
	 */
	public  List getPageListByHql(PageConfig pageConfig, String sql);
	
	/**
	 * 根据SQL分页查询
	 * @param <T>
	 * @param entityClass
	 * @param pageConfig
	 * @param sql
	 * @return
	 */
	public  List getPageListBySql(PageConfig pageConfig, String sql);
	
	/**
	 * 分页查询
	 * 
	 * @param <T>
	 * @param entityClass
	 *            实体类
	 * @param pageConfig
	 *            分页对象
	 * @param condition
	 *            List<Criterion>对象
	 * @return
	 */
	
	public <T> List <T> getPageList(Class<T> entityClass,PageConfig pageConfig,QueryCondition queryCondition) ;
	public <T> List<T> getPageListBySql(Class<T> entityClass,PageConfig pageConfig, String sql);
}
