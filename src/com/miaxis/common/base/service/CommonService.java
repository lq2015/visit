package com.miaxis.common.base.service;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.miaxis.common.util.QueryCondition;
import com.miaxis.common.util.PageConfig;

/**
 * Service接口基类
 * 
 * @param <T>
 * @param <PK>
 */
public interface CommonService {

	/**
	 * 根据实体名称和主键获取实体
	 * 
	 * @param <T>
	 * @param entityName
	 * @param id
	 * @return
	 */
	public <T> T get(Class<T> entityName, Serializable id);

	/**
	 * 根据实体名字获取唯一记录
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public <T> T findUniqueByProperty(Class<T> entityClass,
			String propertyName, Object value);

	/**
	 * 按属性查找对象列表.
	 */
	public <T> List<T> findByProperty(Class<T> entityClass,
			String propertyName, Object value);

	/**
	 * 删除实体
	 * 
	 * @param <T>
	 * 
	 * @param <T>
	 * 
	 * @param <T>
	 * @param entitie
	 */
	public <T> void delete(T entitie);

	public <T> void deleteEntityById(Class entityName, Serializable id);

	/**
	 * 更新指定的实体
	 * 
	 * @param <T>
	 * @param entity
	 */
	public <T> void updateEntitie(T entity);

	public <T> void updateEntityById(Class entityName, Serializable id);

	/**
	 * 保存
	 * 
	 * @param <T>
	 * @param entity
	 * @return
	 */
	public <T> Serializable save(T entity);

	public <T> void batchSave(List<T> entitys);
	
	/**
	 * 根据HQL查询
	 * @param <T>
	 * @param sql
	 * @return
	 */
	public <T> List<T> getListByHql(Class<T> entityClass,final String hql);
	
	/**
	 * 根据SQL查询
	 * @param <T>
	 * @param sql
	 * @return
	 */
	public <T> List<T> getListBySql(Class<T> entityClass,final String sql);
	
	/**
	 * 根据HQL分页查询
	 * @param <T>
	 * @param entityClass
	 * @param pageConfig
	 * @param sql
	 * @return
	 */
	public List getPageListByHql(PageConfig pageConfig, String hql);
	
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
	public <T> List<T> getPageList(Class<T> entityClass, PageConfig pageConfig,QueryCondition queryCondition);
	
	/**
	 * 执行update、delete这种的hql语句
	 */
	public void executeHql(String hql);
	
	/**
	 * 执行sql语句
	 */
	public void executeSql(String sql);
	/**
	 * 得到一字段值
	 * @param hql
	 * @return
	 */
	public Object getColumnValueByHql(String hql);
	/**
	 * 根据SQL分页查询
	 * @param <T>
	 * @param entityClass
	 * @param pageConfig
	 * @param sql
	 * @return
	 */
	public  <T> List<T> getPageListBySql(Class<T> entityClass,PageConfig pageConfig, String sql);
	
}
