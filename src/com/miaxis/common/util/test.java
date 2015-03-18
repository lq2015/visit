package com.miaxis.common.util;

import java.util.Calendar;
import java.util.Date;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		String idCard ="33062319780901223X";
//		Short year = IdcardUtils.getYearByIdCard(idCard);
//		Short month = IdcardUtils.getMonthByIdCard(idCard);
//		Short day = IdcardUtils.getDateByIdCard(idCard);
//		
//		System.out.println(DateUtil.getYYYY_MM_DD(DateUtil.getShortDate(year+"-"+month+ "-"+day)));
//		
//		System.out.println(IdcardUtils.getGenderByIdCard(idCard));
//		String tt = Base64Utils.GetImageStr("D:/Workspaces/道路运输从业人员管理/从业管理/WebRoot/upload/student/20141023115721218_637.bmp");
//		Base64Utils.GenerateImage(tt,"d:/1.bmp");
		System.out.println(CommonUtil.md5("123456"));
		
		Date one = DateUtil.getShortDate("2014-09-01");
		Date two = DateUtil.getShortDate("2014-08-01");
		
		System.out.println(one.after(two));
		
	}

}
