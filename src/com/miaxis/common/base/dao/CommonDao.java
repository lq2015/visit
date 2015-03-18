package com.miaxis.common.base.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.miaxis.common.util.QueryCondition;
import com.miaxis.common.util.PageConfig;

/**
 * 公共扩展方法
 * 
 */
@Repository
public class CommonDao extends GenericBaseCommonDao implements ICommonDao,
		IGenericBaseCommonDao {
	/**
	 * 根据传入的查询hql语句，返回List结果集
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param pageConfig
	 * @param hql
	 * @return
	 */
	public List getPageListByHql(PageConfig pageConfig, String hql) {
		String countsql = "select count(*) "
				+ hql.substring(hql.toLowerCase().indexOf("from"));
		Query query = this.getSession().createQuery(countsql);
		Integer count = new Integer(((Long) query.list().get(0)).intValue());
		pageConfig.setTotalCount(count);// 总记录数

		final int firstResult = pageConfig.getCurPageNO();
		final int maxResults = pageConfig.getPageSize();

		query = this.getSession().createQuery(hql);
		query.setFirstResult(firstResult - 1);// 开始记录行:数据库rownum从0开始
		query.setMaxResults(maxResults);// 每页显示行数
		return query.list();

	}

	/**
	 * 根据传入的查询sql语句，返回List结果集
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param pageConfig
	 * @param sql
	 * @return
	 */
	public List getPageListBySql(PageConfig pageConfig, String sql) {
		String countsql = "select count(*) "
				+ sql.substring(sql.indexOf("from"));
		Query query = this.getSession().createSQLQuery(countsql);
		Integer count = new Integer(
				((BigInteger) query.list().get(0)).intValue());
		pageConfig.setTotalCount(count);// 总记录数

		final int firstResult = pageConfig.getCurPageNO();
		final int maxResults = pageConfig.getPageSize();

		query = this.getSession().createSQLQuery(sql);
		query.setFirstResult((firstResult - 1)* pageConfig.getPageSize());// 开始记录行:数据库rownum从0开始
		query.setMaxResults(maxResults);// 每页显示行数
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();

	}
	
	/**
	 * 根据传入的查询sql语句，返回List结果集
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param pageConfig
	 * @param sql
	 * @return
	 */
	public <T> List<T> getPageListBySql(Class<T> entityClass,PageConfig pageConfig, String sql) {
		String countsql = "select count(*) "
				+ sql.substring(sql.toLowerCase().indexOf("from"));
		Query query = this.getSession().createSQLQuery(countsql);
		List object = query.list();
		
		Integer count = 0;
		if(object==null){
			count = 0;
		}else{
			if(object.size()>0){
				count = new Integer(
						((BigDecimal) (object.get(0))).intValue());
			}else{
				count = 0;
			}
		}
		
		pageConfig.setTotalCount(count);// 总记录数

		final int firstResult = pageConfig.getCurPageNO();
		final int maxResults = pageConfig.getPageSize();

		query = this.getSession().createSQLQuery(sql);
		query.setFirstResult((firstResult - 1)* pageConfig.getPageSize());// 开始记录行:数据库rownum从0开始
		query.setMaxResults(maxResults);// 每页显示行数
//		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setResultTransformer(Transformers.aliasToBean(entityClass)); 
		return query.list();

	}

	/**
	 * 分页查询
	 * 
	 * @param <T>
	 * @param entityClass
	 *            实体类
	 * @param pageConfig
	 *            分页对象
	 * @param condition
	 *            b.List<Criterion>对象
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getPageList(Class<T> entityClass,PageConfig pageConfig, QueryCondition queryCondition) {
		DetachedCriteria detachedCriteria = null;
		
		//加载查询条件
		if(queryCondition!=null){
			queryCondition.addQueryCondition(entityClass);
			detachedCriteria = queryCondition.getDetachedCriteria();
		}else{
			detachedCriteria = DetachedCriteria.forClass(entityClass);;
		}
		
		Criteria c = detachedCriteria.getExecutableCriteria(getSession());

		// 先把Projection和OrderBy条件取出来,清空两者来执行Count操作
		CriteriaImpl impl = (CriteriaImpl) c;
		Projection projection = impl.getProjection();
		final int allCounts = ((Long) c.setProjection(
				Projections.rowCount()).uniqueResult()).intValue();
		c.setProjection(null);
		if (projection == null) {
			c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}

		if (pageConfig != null) {
			if (pageConfig.isPaging()) {// 是否分页
				c.setFirstResult((pageConfig.getCurPageNO() - 1)
						* pageConfig.getPageSize());
				c.setMaxResults(pageConfig.getPageSize());
			}

			// 计算总页数
			pageConfig.setTotalCount(allCounts);
			pageConfig.calculatePages(allCounts);
		}

		return c.list();

	}
}
