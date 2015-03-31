package com.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.miaxis.common.util.Base64Utils;
import com.miaxis.common.util.CommonUtil;

public class test {
	public static void main(String[] args) {  
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");  
//        CoachPosterService us = (CoachPosterService)ctx.getBean("coachPosterService");  
//        us.findByProperty(CoachPoster.class, "title", "张三"); 
//       System.out.println( CommonUtil.md5("miaxishz"));
		
//		Base64Utils Base64Utils = new Base64Utils();
//		String ls = Base64Utils.GetImageStr("d:\\111.bmp");
//		Base64Utils.GenerateImage(ls,"e:\\222.bmp");
//		System.out.println("");
		String str="d:\\data\\1.jpg";
		str = str.replace("\\", "/");
		System.out.println(str.substring(0, str.lastIndexOf("/")));
        
    }  
}
