package com.keith.core.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang3.time.DateFormatUtils;


public class DateUtil {
	private static String timezone;
	
	public static String getCurrentTimeZone(){
		if(timezone == null){
			System.out.println("Init local timezone once");
			TimeZone tz = TimeZone.getDefault();  
			int offset = tz.getRawOffset();  
			timezone = "\\"+String.format("%s%02d%02d", offset >= 0 ? "+" : "-", offset / 3600000, (offset / 60000) % 60);
		}
		return timezone;
	}
	/**
	 * 两个日期间隔的天数
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int dateSubtract(String date1, String date2) {
		if (date1 == null || "".equals(date1))
			date1 = Date2Str(new java.util.Date());
		if (date2 == null || "".equals(date2))
			date2 = Date2Str(new java.util.Date());
		long daterange = Str2Date(date1, "yyyy-MM-dd").getTime()
				- Str2Date(date2, "yyyy-MM-dd").getTime();
		long time = 1000 * 60 * 60 * 24;
		return (int) (daterange / time);
	}
	public DateUtil() {
	}

	/**
	 * 根据设定日期获取该月的最后一天
	 * @author fanhui
	 * 2006-12-25
	 * @param sDate1
	 * @return
	 */
	public static java.util.Date getLastDayOfMonth(java.util.Date sDate1) {
		Calendar cDay1 = Calendar.getInstance();
		cDay1.setTime(sDate1);
		final int lastDay = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);
		java.util.Date lastDate = cDay1.getTime();
		lastDate.setDate(lastDay);
		return lastDate;
	}

	/**
	 * 根据设定日期获取该月的第一天
	 * @author fanhui
	 * 2006-12-25
	 * @param sDate1
	 * @return
	 */
	public static java.util.Date getFirstDayOfMonth(java.util.Date sDate1) {
		Calendar cDay1 = Calendar.getInstance();
		cDay1.setTime(sDate1);
		final int firstDay = cDay1.getActualMinimum(Calendar.DAY_OF_MONTH);
		java.util.Date firstDate = cDay1.getTime();
		firstDate.setDate(firstDay);
		return firstDate;
	}

	/**
	 * 将格式为"dd/MMM/yyyy:HH:mm:ss"的EnglishLocate的字符转换为Date对象
	 * 
	 * @author Winefox
	 * @param date
	 * @return
	 */
	public static String tansFormat(String date) {
		String rev = null;
		SimpleDateFormat from = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss",
				Locale.ENGLISH);
		SimpleDateFormat to = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date dt = null;
		try {
			dt = (java.util.Date) from.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		rev = to.format(dt);
		return rev;
	}

	/**
	 * 返回当前时间
	 * @return
	 */
	public static Date getCurrentTime() {
		return Calendar.getInstance().getTime();
	}
	
	/**
	 * 返回当前时间
	 * @return
	 */
	public static String getCurrentDate() {
		return Date2Str(Calendar.getInstance().getTime(),"yyyyMMdd");
	}
	
	/**
	 * 计算时间间隔
	 * @param oriTime 
	 * @param unit，单位
	 * @param interval，间隔值，负数往前，正数往后
	 * @return
	 */
	public static Date getTimeInterval(int unit,int interval, Date oriTime) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(oriTime);
		ca.add(unit, interval);
		return ca.getTime();
	}
	
	/**
	 * 获取前一天的日期: 格式为2005-09-09
	 * @return
	 */
	public static Date getYesterdayDate() {
		Calendar calendar = Calendar.getInstance();//获取的是系统当前时间
        calendar.add(Calendar.DATE, -1);    //得到前一天
        return calendar.getTime();
	}


	
	/**
	 * 将"yyyy-MM-dd"格式的字符串转为日期型返回

	 * 
	 * @param date
	 * @return
	 */
	public static Date Str2Date(String date) {
		try {
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
			if (date.length() > 10) {
				ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			}
			java.util.Date d = ft.parse(date);
			
			return new Date(d.getTime());
		} catch (Exception ex) {
			return new Date(Calendar.getInstance().getTime().getTime());
		}
	}
	
	public static java.util.Date Str2Date_SO(String date) {
		try {
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
			if (date.length() > 10) {
				ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			}
			java.util.Date d = ft.parse(date);
			
			//return new Date(d.getTime());
			return d;
		} catch (Exception ex) {
			return new Date(Calendar.getInstance().getTime().getTime());
		}
	}

	/**
	 * 将格式为pattern的字符串型转换为日期型返回

	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static java.util.Date Str2Date(String date, String pattern) {
		try {
			SimpleDateFormat ft = new SimpleDateFormat(pattern);
			java.util.Date d = ft.parse(date);
			return d;
		} catch (Exception ex) {
			return null;
		}
	}

	/*
	 * 将格式为"yyyy-MM-dd"的字符串转为java.util.Date类型返回
	 * 
	 */
	public static java.util.Date Str2utilDate(String date, boolean all) {
		try {
			SimpleDateFormat ft = null;
			if (all)
				ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			else
				ft = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date d = ft.parse(date);
			return d;
		} catch (Exception ex) {
			return null;
		}
	}

	public static java.util.Date StrutilDate(String date, String pattern) {
		try {
			SimpleDateFormat ft = new SimpleDateFormat(pattern);
			java.util.Date d = ft.parse(date);
			return d;
		} catch (Exception ex) {
			return null;
		}
	}


	public static String Date2Str(Date date, String pattern) {
		if (date == null)
			return "";
		SimpleDateFormat ft = new SimpleDateFormat(pattern);
		return ft.format(date);
	}

	public static String Date2Str(Date date) {
		if (date == null)
			date = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return ft.format(date);
	}

	public static String getYear(Date date) {
		SimpleDateFormat ft = new SimpleDateFormat("yyyy");
		return ft.format(date);
	}

	public static String getMonth(Date date) {
		SimpleDateFormat ft = new SimpleDateFormat("MM");
		return ft.format(date);
	}

	public static String getDay(Date date) {
		SimpleDateFormat ft = new SimpleDateFormat("dd");
		return ft.format(date);
	}

	public static String getHour(Date date) {
		SimpleDateFormat ft = new SimpleDateFormat("HH");
		return ft.format(date);
	}

	public static String getMinute(Date date) {
		SimpleDateFormat ft = new SimpleDateFormat("mm");
		return ft.format(date);
	}

	//算出N天后的日期
	public static String getGoDay(int i, String date) {
		// 加N天
		GregorianCalendar gc = new GregorianCalendar();
		Date daytime=DateUtil.Str2Date(date, "yyyy-MM-dd");
		if(daytime==null)
			daytime=new Date();
		gc.setTime(daytime);
		gc.set(Calendar.DAY_OF_YEAR, gc.get(Calendar.DAY_OF_YEAR) + i);
		return DateUtil.Date2Str(gc.getTime(), "yyyy-MM-dd");
	}
	
	//算出时间后的N小时
	public static Date getRangeHour(int i, Date date) {
		Calendar calendar = Calendar.getInstance();
		if(date!=null)
			calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, i); 
		return calendar.getTime();
	}

	//算两时间段内的天数 time1 为大时间, time2 为小时间
	public static long getQuot(String time1, String time2) {
		long quot = 0;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			quot = date1.getTime() - date2.getTime();
			quot = quot / 1000 / 60 / 60 / 24;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return quot;
	}

	
	//判断时间
	public static boolean getShiJianDuan(String date1) {
		// 加N天
		boolean flag=false;
		GregorianCalendar gc1 = new GregorianCalendar();
		gc1.setTime(DateUtil.Str2Date(date1, "yyyy-MM-dd"));
		GregorianCalendar gc2 = new GregorianCalendar();
		gc2.setTime(new Date());
		if(gc1.compareTo(gc2)<=0){
			flag=true;
		}
		return flag;
	}
	
	public static String getJxsChuandanTime(String date) {
		
		if(Integer.parseInt(getHour(Str2Date(date)))>=10) {
			date = getGoDay(1,date);
		}
		return date.substring(0, 10);
	}
	
	/**
	 * 整数(秒数)转换为时分秒格式(HH:mm:ss)   modify:luyx #2013/3/13
	 */
	// a integer to xx:xx:xx
    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = "00:" + unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    } 
    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }	
	
    /**
     * 百分比格式化数字
     */
    public String myPercent(int y,int z){
    	String baifenbi="";
    	double baiy = y*1.0;
    	double baiz = z*1.0;
    	double fen  = baiy/baiz;
    	DecimalFormat df = new DecimalFormat("##.00%");  //##.00%  百分比格式，后面不足两位的用0补齐
    	baifenbi = df.format(fen);
    	return baifenbi;
    }
    
    /**
     * 今天凌晨时间
     * @return
     */
    public static Date getTodayZero(){
    	Calendar ca = Calendar.getInstance();
		ca.set(ca.get(Calendar.YEAR), ca.get(Calendar.MONTH), ca.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
    	return ca.getTime();
    }
    
    /**
     * 昨天凌晨的时间
     * @return
     */
    public static Date getYesterdayZero(){
    	Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE, -1);
        ca.set(ca.get(Calendar.YEAR), ca.get(Calendar.MONTH), ca.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        return ca.getTime();
    }
    
    /**
     * 计算昨天的初始时间标签
     * @return
     */
    public static long getYesterdayStamp(){
    	Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE, -1);
        ca.set(ca.get(Calendar.YEAR), ca.get(Calendar.MONTH), ca.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        return ca.getTimeInMillis()/1000;
    }
    
    /**
     * 计算今天的初始时间标签
     * @return
     */
    public static long getTodayStamp(){
    	Calendar ca = Calendar.getInstance();
		ca.set(ca.get(Calendar.YEAR), ca.get(Calendar.MONTH), ca.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
    	return ca.getTimeInMillis()/1000;
    }
    
    /**
     * 计算明天的初始时间标签
     * @return
     */
    public static long getTomorrowStamp(){
    	Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE, 1);
        ca.set(ca.get(Calendar.YEAR), ca.get(Calendar.MONTH), ca.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        return ca.getTimeInMillis()/1000;
    }
    
    /**
     * 返回带时区的日期格式   yyyyMMddHHmmssZ
     * @param date
     * @return
     */
    public static String strToDateStrTimeZone(String date){
    	SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddHHmmss");
    	Date time=Str2utilDate(date,true); 
    	return (ft.format(time)+getCurrentTimeZone()).replace("\\", "");
    }
    
    /**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}
    
    public static Date getDateStart(Date date) {
		if(date==null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date= sdf.parse(formatDate(date, "yyyy-MM-dd")+" 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static Date getDateEnd(Date date) {
		if(date==null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date= sdf.parse(formatDate(date, "yyyy-MM-dd") +" 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * 获取当前时间的上一个整数时间点  适用于能被10整除的间隔 用户计算利率使用
	 * @return
	 */
	public static long getBeforeNowDate(int step){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		String time=sdf.format(getCurrentTime());
		long nowTime=Long.parseLong(time);
		long offset=nowTime%step;
		return nowTime-offset;
	}
	/**
	 * 获取当前时间的上一个整数时间点  适用于能被10整除的间隔  用户计算利率使用
	 * @return
	 */
	public static long getNextNowDate(int step){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.MINUTE, 10);
		String time=sdf.format(cal.getTime());
		long nowTime=Long.parseLong(time);
		long offset=nowTime%step;
		return nowTime-offset;
	}
	/**
	 * 获取下一个利率时间
	 * @param step
	 * @return
	 */
	public static Date getNextRateTime(int step){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.MINUTE, 10);
		String time=sdf.format(cal.getTime());
		long nowTime=Long.parseLong(time);
		long offset=nowTime%step;
		cal.add(Calendar.MINUTE, -(int)offset);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();	
	}
	public static void main(String[] args) {
		Calendar c=Calendar.getInstance();
		c.set(Calendar.MONTH, 4);
		c.set(Calendar.DAY_OF_MONTH, 19);
		System.out.println(DateUtil.Date2Str(c.getTime()));
		Calendar cc=Calendar.getInstance();
		System.out.println((c.getTimeInMillis()-cc.getTimeInMillis())/1000/60/60/24);
	}
	/**
	 * 获取当前的时间戳
	 * @return
	 */
	public static long getTimeLong(){
		return System.currentTimeMillis();
	} 
	/**
	 * 获取之后多少天0点的时间
	 * @return
	 */
	public static Date getNextDays(int days){
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, days);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	/**
	 * 获取之后days天houer点的时间
	 * @return
	 */
	public static Date getNextDays(int days,int houer){
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, days);
		cal.set(Calendar.HOUR_OF_DAY, houer);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	public static String getYyyyMMddHHmmss(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(getCurrentTime());
	}
}
