package com.miaxis.common.listener;

import javax.servlet.ServletContextEvent;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * 系统初始化监听器,在系统启动时运行,进行一些初始化工作
 * @author liu.qiao
 *
 */
public class InitListener  implements javax.servlet.ServletContextListener {

	public void contextDestroyed(ServletContextEvent arg0) {
		
	}
	
	public void contextInitialized(ServletContextEvent event) {
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		
		/**
		 * 第一部分：对基础数据进行缓存
		 */
		System.out.println("*************************************");
		System.out.println("*             系统初始化完毕                                             *");
		System.out.println("*************************************");
	}
}
