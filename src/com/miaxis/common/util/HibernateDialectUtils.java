package com.miaxis.common.util;

import javax.servlet.ServletContext;

import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 获取系统数据库方言工具
 * @author liu.qiao
 *
 */
public class HibernateDialectUtils {
	
	/**
	 * 得到系统数据库方言
	 * @return
	 */
	public static String getDialect(){
		ServletContext sc  = ContextHolderUtils.getSession().getServletContext();
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(sc); 
		SessionFactoryImpl sessionFactory =(SessionFactoryImpl)context.getBean(SessionFactory.class);
		return sessionFactory.getDialect().toString();
	}
	
	/**
	 * oracle
	 * @return
	 */
	public static boolean isOracle(){
		String dialect = getDialect();
		if(dialect.toLowerCase().indexOf("oracle")>-1){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * mysql
	 * @return
	 */
	public static boolean isMysql(){
		String dialect = getDialect();
		if(dialect.toLowerCase().indexOf("mysql")>-1){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * sqlserver
	 * @return
	 */
	public  static boolean isSqlServer(){
		String dialect = getDialect();
		if(dialect.toLowerCase().indexOf("sqlserver")>-1){
			return true;
		}else{
			return false;
		}
	}
}
