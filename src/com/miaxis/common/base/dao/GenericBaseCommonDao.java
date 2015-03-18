package com.miaxis.common.base.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.Assert;

/**
 * 类描述： DAO层泛型基类
 * 
 * @param <T>
 * @param <PK>
 */
public abstract class GenericBaseCommonDao<T, PK extends Serializable>
		implements IGenericBaseCommonDao {

	/**
	 * 初始化Log4j的一个实例
	 */
	private static final Logger logger = Logger
			.getLogger(GenericBaseCommonDao.class);

	/**
	 * 注入一个sessionFactory属性,并注入到父类(HibernateDaoSupport)
	 * **/
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	public Session getSession() {
		// 事务必须是开启的(Required)，否则获取不到
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 创建单一Criteria对象
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param criterions
	 * @return
	 */
	private <T> Criteria createCriteria(Class<T> entityClass) {
		Criteria criteria = getSession().createCriteria(entityClass);
		return criteria;
	}

	/**
	 * 创建Criteria对象带属性比较
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param criterions
	 * @return
	 */
	private <T> Criteria createCriteria(Class<T> entityClass,
			Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	/**
	 * 根据Id获取对象。
	 */
	public <T> T get(Class<T> entityName, Serializable id) {
		return (T) getSession().get(entityName, id);
	}

	/**
	 * 根据实体名字获取唯一记录
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public <T> T findUniqueByProperty(Class<T> entityClass,
			String propertyName, Object value) {
		Assert.hasText(propertyName);
		return (T) createCriteria(entityClass,
				Restrictions.eq(propertyName, value)).uniqueResult();
	}

	/**
	 * 按属性查找对象列表.
	 */
	public <T> List<T> findByProperty(Class<T> entityClass,
			String propertyName, Object value) {
		Assert.hasText(propertyName);
		return (List<T>) createCriteria(entityClass,
				Restrictions.eq(propertyName, value)).list();
	}

	/**
	 * 根据传入的实体删除对象
	 */
	public <T> void delete(T entity) {
		try {
			getSession().delete(entity);
			getSession().flush();
			if (logger.isDebugEnabled()) {
				logger.debug("删除成功," + entity.getClass().getName());
			}
		} catch (RuntimeException e) {
			logger.error("删除异常", e);
			throw e;
		}
	}

	/**
	 * 根据主键删除指定的实体
	 * 
	 * @param <T>
	 * @param entityName
	 */
	public <T> void deleteEntityById(Class entityName, Serializable id) {
		delete(get(entityName, id));
		getSession().flush();
	}

	/**
	 * 更新指定的实体
	 * 
	 * @param <T>
	 * @param entity
	 */
	public <T> void updateEntitie(T entity) {
		entity = (T) getSession().merge(entity);
		getSession().update(entity);
		getSession().flush();
	}

	/**
	 * 根据主键更新实体
	 */
	public <T> void updateEntityById(Class entityName, Serializable id) {
		updateEntitie(get(entityName, id));

	}

	/**
	 * 根据传入的实体持久化对象
	 */
	public <T> Serializable save(T entity) {
		try {
			Serializable id = getSession().save(entity);
			getSession().flush();
			if (logger.isDebugEnabled()) {
				logger.debug("保存实体成功," + entity.getClass().getName());
			}
			return id;
		} catch (RuntimeException e) {
			logger.error("保存实体异常", e);
			throw e;
		}
	}

	/**
	 * 批量保存数据
	 * 
	 * @param <T>
	 * @param entitys
	 *            要持久化的临时实体对象集合
	 */
	public <T> void batchSave(List<T> entitys) {
		for (int i = 0; i < entitys.size(); i++) {
			getSession().save(entitys.get(i));
			if (i % 20 == 0) {
				// 20个对象后才清理缓存，写入数据库
				getSession().flush();
				getSession().clear();
			}
		}
		// 最后清理一下----防止大于20小于40的不保存
		getSession().flush();
		getSession().clear();
	}
	
	/**
	 * 根据HQL查询
	 */
	public  <T> List<T> getListByHql(Class<T> entityClass,String hql) {
		Query querys = getSession().createQuery(hql);
		return (List<T>) querys.list();
	}
	
	/**
	 * 根据SQL查询
	 */
	public <T> List<T> getListBySql(Class<T> entityClass,String sql) {
		List list =null;
		if(entityClass!=null){
			Query querys = getSession().createSQLQuery(sql).addEntity(entityClass);
			list = querys.list();
		}else{
			Query querys = getSession().createSQLQuery(sql);
			list = querys.list();
		}
		return list;
	}
	
	/**
	 * 执行update、delete这种的hql语句
	 */
	public void executeHql(String hql) {
		this.getSession().createQuery(hql).executeUpdate();
	}
	
	/**
	 * 执行sql语句
	 */
	public void executeSql(String sql) {
		getSession().createSQLQuery(sql).executeUpdate();
	}
	
	/**
	 * 得到一字段值
	 * @param hql
	 * @return
	 */
	public Object getColumnValueByHql(String hql){
		Object c = this.getSession().createQuery(hql).uniqueResult();
		return c;
	}
}
