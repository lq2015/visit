package com.miaxis.common.util;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * HBC查询条件工具类
 * 
 * @author liu.qiao
 * 
 */
public class QueryCondition {
	public static final String ASC = "ASC";
	public static final String DESC = "DESC";
	
	private DetachedCriteria detachedCriteria;
	private List<String> aliasList;
	private List<Criterion>  criterionList ;
	private List<Order> orderList;

	public QueryCondition() {
		aliasList = new ArrayList();
		criterionList = new ArrayList();
		orderList = new ArrayList();
	}

	public DetachedCriteria getDetachedCriteria() {
		return detachedCriteria;
	}

	/**
	 * 设置eq(相等)查询条件
	 * 
	 * @param keyname
	 *            :字段名
	 * @param keyvalue
	 *            ：字段值
	 */
	public Criterion eq(String keyname, Object keyvalue) {
		Criterion criterion = null;
		if (keyvalue != null && keyvalue != "") {
			addAlias(keyname); 
			criterion = Restrictions.eq(keyname,keyvalue);
			addCriterions(criterion);
		}
		return criterion;
	}

	/**
	 * 设置notEq(不等)查询条件
	 * 
	 * @param keyname
	 * @param keyvalue
	 */
	public Criterion notEq(String keyname, Object keyvalue) {
		Criterion criterion = null;
		if (keyvalue != null && keyvalue != "") {
			addAlias(keyname); 
			criterion = Restrictions.ne(keyname,keyvalue);
			addCriterions(criterion);
		}
		return criterion;
	}

	/**
	 * 设置like(模糊)查询条件
	 * 
	 * @param keyname
	 * @param keyvalue1
	 * @param keyvalue2
	 */
	public Criterion like(String keyname, Object keyvalue) {
		Criterion criterion = null;
		if (keyvalue != null && keyvalue != "") {
			addAlias(keyname); 
			criterion = Restrictions.like(keyname,keyvalue);
			addCriterions(criterion);
		}
		return criterion;
	}
	
	/**
	 * 设置notLike(模糊)查询条件
	 * 
	 * @param keyname
	 * @param keyvalue1
	 * @param keyvalue2
	 */
	public Criterion notLike(String keyname, Object keyvalue) {
		Criterion criterion = null;
		if (keyvalue != null && keyvalue != "") {
			addAlias(keyname); 
			criterion = Restrictions.not(Restrictions.like(keyname,keyvalue));
			addCriterions(criterion);
		}
		return criterion;
	}

	/**
	 * 设置gt(>)查询条件
	 * 
	 * @param keyname
	 * @param keyvalue1
	 * @param keyvalue2
	 */
	public Criterion gt(String keyname, Object keyvalue) {
		Criterion criterion = null;
		if (keyvalue != null && keyvalue != "") {
			addAlias(keyname); 
			criterion = Restrictions.gt(keyname,keyvalue);
			addCriterions(criterion);
		}
		return criterion;
	}

	/**
	 * 设置lt(<)查询条件
	 * 
	 * @param keyname
	 * @param keyvalue1
	 * @param keyvalue2
	 */
	public Criterion lt(String keyname, Object keyvalue) {
		Criterion criterion = null;
		if (keyvalue != null && keyvalue != "") {
			addAlias(keyname); 
			criterion = Restrictions.lt(keyname,keyvalue);
			addCriterions(criterion);
		}
		return criterion;
	}

	/**
	 * 设置le(<=)查询条件
	 * 
	 * @param keyname
	 * @param keyvalue1
	 * @param keyvalue2
	 */
	public Criterion le(String keyname, Object keyvalue) {
		Criterion criterion = null;
		if (keyvalue != null && keyvalue != "") {
			addAlias(keyname); 
			criterion = Restrictions.le(keyname,keyvalue);
			addCriterions(criterion);
		}
		return criterion;
	}

	/**
	 * 设置ge(>=)查询条件
	 * 
	 * @param keyname
	 * @param keyvalue1
	 * @param keyvalue2
	 */
	public Criterion ge(String keyname, Object keyvalue) {
		Criterion criterion = null;
		if (keyvalue != null && keyvalue != "") {
			addAlias(keyname); 
			criterion = Restrictions.ge(keyname,keyvalue);
			addCriterions(criterion);
		}
		return criterion;
	}

	/**
	 * 设置in(包含)查询条件
	 * 
	 * @param keyname
	 * @param keyvalue1
	 * @param keyvalue2
	 */
	public Criterion in(String keyname, Object[] keyvalue) {
		Criterion criterion = null;
		if (keyvalue != null &&  keyvalue[0] != "") {
			addAlias(keyname); 
			criterion = Restrictions.in(keyname,keyvalue);
			addCriterions(criterion);
		}
		return criterion;
	}
	
	/**
	 * 设置notIn(不包含)查询条件
	 * 
	 * @param keyname
	 * @param keyvalue1
	 * @param keyvalue2
	 */
	public Criterion notIn(String keyname, Object[] keyvalue) {
		Criterion criterion = null;
		if (keyvalue != null &&  keyvalue[0] != "") {
			addAlias(keyname); 
			criterion = Restrictions.not(Restrictions.in(keyname,keyvalue));
			addCriterions(criterion);
		}
		return criterion;
	}

	/**
	 * 设置isNull查询条件
	 * 
	 * @param keyname
	 * @param keyvalue1
	 * @param keyvalue2
	 */
	public Criterion isNull(String keyname) {
		Criterion criterion = null;
		addAlias(keyname); 
		criterion = Restrictions.isNull(keyname);
		addCriterions(criterion);
		return criterion;
	}

	/**
	 * 设置isNull查询条件
	 * 
	 * @param keyname
	 * @param keyvalue1
	 * @param keyvalue2
	 */
	public Criterion isNotNull(String keyname) {
		Criterion criterion = null;
		addAlias(keyname); 
		criterion = Restrictions.isNotNull(keyname);
		addCriterions(criterion);
		return criterion;
	}

	/**
	 * 设置between(之间)查询条件
	 * 
	 * @param keyname
	 * @param keyvalue1
	 * @param keyvalue2
	 */
	public Criterion between(String keyname, Object keyvalue1, Object keyvalue2) {
		Criterion criterion = null;
		
		addAlias(keyname); 
		criterion = Restrictions.between(keyname, keyvalue1, keyvalue2);
		addCriterions(criterion);
		return criterion;
	}
	
	/**
	 * 设置notBetween(未在...之间)查询条件
	 * 
	 * @param keyname
	 * @param keyvalue1
	 * @param keyvalue2
	 */
	public Criterion notBetween(String keyname, Object keyvalue1, Object keyvalue2) {
		Criterion criterion = null;
		
		addAlias(keyname); 
		criterion = Restrictions.not(Restrictions.between(keyname, keyvalue1, keyvalue2));
		addCriterions(criterion);
		return criterion;
	}

	/**
	 * 设置or查询条件
	 * @param criterion1
	 * @param criterion2
	 * @return
	 */
	public Criterion or(Criterion criterion1,Criterion criterion2) {
		
		//从criterionList去除2个生成Criterion
		criterionList.remove(criterionList.size() -1);
		criterionList.remove(criterionList.size() -1);
		Criterion criterion = Restrictions.or(criterion1, criterion2);
		addCriterions(criterion);
		return criterion;
	}
	
	/**
	 * 设置and查询条件
	 * @param criterion1
	 * @param criterion2
	 * @return
	 */
	public Criterion and(Criterion criterion1,Criterion criterion2) {
		//从criterionList去除2个生成Criterion
		criterionList.remove(criterionList.size() -1);
		criterionList.remove(criterionList.size() -1);
		Criterion criterion = Restrictions.and(criterion1, criterion2);
		addCriterions(criterion);
		return criterion;
	}
	
	/**
	 * 倒序
	 * @param keyname
	 */
	public void desc(String keyname){
		orderList.add(Order.desc(keyname));
	}
	
	/**
	 * 升序
	 * @param keyname
	 */
	public void asc(String keyname){
		orderList.add(Order.asc(keyname));
	}

	/**
	 * 创建外键表关联对象List
	 * @param col  对"user.name" 这样的字段进行解析，创建user关联对象
	 */
	private void addAlias(String col) {
		String name="";
		if (col.indexOf(".") > 0) {
			if (col.indexOf(".") > 0) {
				String tmp = new StringBuffer(col).reverse().toString();
				tmp = tmp.substring(tmp.indexOf(".")+1);
				name = new StringBuffer(tmp).reverse().toString();
				
				// 利用list的包含方法,进行判断是否已经创建过Alias
		        if(!aliasList.contains(name)){
		        	aliasList.add(name);
		        }
			}
		}
	}
	
	/**
	 * 创建查询条件List
	 * @param criterion
	 */
	private void addCriterions(Criterion criterion) {
		// 利用list的包含方法,进行判断是否已经创建
        if(!criterionList.contains(criterion)){
        	criterionList.add(criterion);
        }
	}
	
	/**
	 * 创建排序List
	 * @param criterion
	 */
	private void addOrders(Order order) {
		// 利用list的包含方法,进行判断是否已经创建
        if(!orderList.contains(order)){
        	orderList.add(order);
        }
	}
	
	/**
	 * 加载查询条件
	 * @param c
	 */
	public void addQueryCondition(Class c) {
		this.detachedCriteria = DetachedCriteria.forClass(c);
		
		//创建外键表关联
		for (int i=0;i<aliasList.size();i++) {
			String name= aliasList.get(0);
			detachedCriteria.createAlias(name, name);
		}
		
		//创建查询条件
		for (Criterion criterion : criterionList) {
			detachedCriteria.add(criterion);
		}
		
		//创建排序条件
		for (Order order : orderList) {
			detachedCriteria.addOrder(order);
		}
	}
}
