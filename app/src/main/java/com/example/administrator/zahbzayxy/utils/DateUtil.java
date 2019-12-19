package com.example.administrator.zahbzayxy.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static final String DEFAULTPATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String yyyy_MM_dd = "yyyy-MM-dd";
	public static final String chinese = "yyyy年MM月dd日 HH:mm:ss";
	/**
	 * 获取系统时间
	 * 
	 * */
	public static String getNow(String formatStr){
		Calendar calendar = Calendar.getInstance();
	    SimpleDateFormat sf = new SimpleDateFormat(formatStr);
        String str = sf.format(calendar.getTime());
        return str;
	}

	
	public static String getCurrentTime() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String formatStr = sdf.format(date);
		return formatStr;
	}

	public static String getCurrentTimeAll() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULTPATTERN);
		String formatStr = sdf.format(date);
		return formatStr;
	}


	/**
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date date,String pattern){
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}
	
	
	/**
	 * 格式化时间  yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date){
		return formatDate(date,DEFAULTPATTERN);
	}
	public static String formatDate1(Date date){
		return formatDate(date,yyyy_MM_dd);
	}
	public static Date getAfterDate(Date date,int day){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE,cal.get(Calendar.DATE)+day);
		return cal.getTime();
	}
	
	public static String secondFormat(int seconds){
		StringBuffer sb=new StringBuffer();
		int temp=0;
		temp = seconds/3600;
		sb.append((temp<10)?"0"+temp+":":""+temp+":");

		temp=seconds%3600/60;
		sb.append((temp<10)?"0"+temp+":":""+temp+":");

		temp=seconds%3600%60;
		sb.append((temp<10)?"0"+temp:""+temp);
		String timeFormat = sb.toString();
		return timeFormat;
	}

	public static Integer HoursFormatSeconds(String time){
		String temp[] = time.split(":");
		int hours = Integer.valueOf(temp[0]);
		int minutes = Integer.valueOf(temp[1]);
		int seconds = Integer.valueOf(temp[2]);
		Integer allSeconds = hours * 60 * 60 + minutes * 60 + seconds;
		//System.out.println("秒数：" + allSeconds);
		return allSeconds;
	}




//	String time="h时mm分ss秒";
//	long s=Integer.parseInt(time.substring(0,time.indexOf("时")))*3600;    //小时
//	s+=Integer.parseInt(time.substring(time.indexOf("时")+1,time.indexOf("分")))*60;    //分钟
//	s+=Integer.parseInt(time.substring(time.indexOf("分")+1,time.indexOf("秒")));    //秒
}
