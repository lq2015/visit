package com.miaxis.common.base.dao;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 类描述：DAO层泛型基类接口
 * 
 */
public interface IGenericBaseCommonDao {

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
	 * @param entityClass
	 * @param sql
	 * @return
	 */
	public <T> List<T> getListByHql(Class<T> entityClass,String hql);
	
	/**
	 * 根据SQL查询
	 * @param <T>
	 * @param entityClass
	 * @param sql
	 * @return
	 */
	public <T> List<T> getListBySql(Class<T> entityClass,final String sql);
	
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
}
