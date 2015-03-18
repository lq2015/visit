package com.miaxis.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.apache.commons.lang.StringUtils;


public class DateUtil {
	// 短日期格式
	public static String DATE_FORMAT = "yyyy-MM-dd";

	// 长日期格式
	public static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public static String getYYYYMM(Date d){
	    SimpleDateFormat formatter;
	    formatter = new SimpleDateFormat ("yyyyMM");
	    String ctime = formatter.format(d);
	    return ctime;
	}

	public static String getYYYY_MM_DD(Date date) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		if (date == null) {
			return null;
		}
		return sf.format(date);
	}

	public static String getYYYYMMDD_CN(Date date) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
		if (date == null) {
			return null;
		}
		return sf.format(date);
	}

	/**
	 * 获取现在时间
	 * 
	 * @return返回短时间格式 yyyy-MM-dd
	 */
	public static Date getShortDate(String dateStr) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date stortDate = null;
		try {
			stortDate = formatter.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return stortDate;
	}

    /**
     * 获取现在时间
     * 
     * @return返回短时间格式 yyyy-MM-dd
     */
    public static Date getStringToDate(String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date stortDate = null;
        try {
            stortDate = formatter.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return stortDate;
    }
	/**
	 * 获取现在时间
	 * 
	 * @return返回短时间格式 yyyy-MM-dd
	 */
	public static Date getNowDateShort() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		// ParsePosition pos = new ParsePosition(8);
		Date currentTime_2 = null;
		try {
			currentTime_2 = formatter.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return currentTime_2;
	}

	public static String getNowdateFormat(Date d, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String formattedDate = formatter.format(new Date());
		return formattedDate;
	}

	/**
	 * 加天
	 * 
	 * @param s
	 * @param n
	 * @return
	 */
	public static Date addDay(Date sDate, int n) {
		try {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(sDate);
			calendar.add(Calendar.DATE, n);// 增加一天
			return calendar.getTime();
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 加月
	 * @param sDate
	 * @param n
	 * @return
	 */
	public static Date addMonth(Date sDate, int n) {
		try {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(sDate);
			calendar.add(Calendar.MONTH, n);// 增加一月
			return calendar.getTime();
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 将日期格式的字符串转换为长整型
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static long convert2long(String date, String format) {
		try {
			if (StringUtils.isNotBlank(date)) {
				if (StringUtils.isBlank(format))
					format = DateUtil.TIME_FORMAT;
				SimpleDateFormat sf = new SimpleDateFormat(format);
				return sf.parse(date).getTime();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0l;
	}

	/**
	 * 将长整型数字转换为日期格式的字符串
	 * 
	 * @param time
	 * @param format
	 * @return
	 */
	public static String convert2String(long time, String format) {
		if (time > 0l) {
			if (StringUtils.isBlank(format))
				format = DateUtil.TIME_FORMAT;
			SimpleDateFormat sf = new SimpleDateFormat(format);
			Date date = new Date(time);
			return sf.format(date);
		}
		return "";
	}

	/**
	 * 获取当前系统的日期
	 * 
	 * @return
	 */
	public static long curTimeMillis() {
		return System.currentTimeMillis();
	}

	public static void main(String[] args) {
		// SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		// String formattedDate = formatter.format(new Date());
		// System.out.println(formattedDate);
	}
}
