package com.eduspace.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日历 工具类
 * 
 * @author pc
 *
 */
public class CalendarUtil {

	private Date date;
	private Calendar calendar;

	public CalendarUtil(Date date) {
		this.date = date;
		this.calendar = Calendar.getInstance();
		calendar.setTime(date);
	}

	/**
	 * 获取日期的字符串格式
	 * 
	 * @param format
	 *            日期格式
	 * @return
	 */
	public String getString(String format) {
		String str = new SimpleDateFormat(format).format(date);
		return str;
	}

	public Integer getYear() {

		int year = calendar.get(Calendar.YEAR);

		return year;
	}

	public Integer getMonth() {

		int month = calendar.get(Calendar.MONTH) + 1;

		return month;
	}

	public Integer getDay() {

		int month = calendar.get(Calendar.DAY_OF_MONTH);

		return month;
	}

	/**
	 * 星期 （星期日 为 0）
	 * 
	 * @return
	 */
	public Integer getDayOfWeek() {
		int week = calendar.get(Calendar.DAY_OF_WEEK);
		return week;
	}

	/**
	 * 本月的第一天
	 * 
	 * @return
	 */
	public String getFirstDayOfMonth(String format) {
		Calendar newcalendar = Calendar.getInstance();
		newcalendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		String str = new SimpleDateFormat(format).format(newcalendar.getTime());
		return str;
	}

	/**
	 * 本月的最后一天
	 * 
	 * @return
	 */
	public String getLastDayOfMonth(String format) {

		Calendar newcalendar = Calendar.getInstance();
		newcalendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		String str = new SimpleDateFormat(format).format(newcalendar.getTime());
		return str;
		 
	}

	/**
	 * 下一天
	 * 
	 * @return
	 */
	public String getNextDay(String format) {
		Calendar newcalendar = Calendar.getInstance();
		newcalendar.setTime(calendar.getTime());
		newcalendar.add(Calendar.DAY_OF_MONTH, 1);
		String str = new SimpleDateFormat(format).format(newcalendar.getTime());
		return str;
	}

	/**
	 * 上一天
	 * 
	 * @return
	 */
	public String getLastDay(String format) {

		Calendar newcalendar = Calendar.getInstance();
		newcalendar.setTime(calendar.getTime());
		newcalendar.add(Calendar.DAY_OF_MONTH, -1);
		String str = new SimpleDateFormat(format).format(newcalendar.getTime());
		return str;
	}
	/**
	 * 前后变化几天
	 * @param changeDay 变化的天数 （往后为正数 ，往前为负数）
	 * @param format 时间格式
	 * @return 
	 */
	public String getChangeDay(int changeDay,String format) {
		
		Calendar newcalendar = Calendar.getInstance();
		newcalendar.setTime(calendar.getTime());
		newcalendar.add(Calendar.DAY_OF_MONTH, changeDay);
		String str = new SimpleDateFormat(format).format(newcalendar.getTime());
		return str;
		
	}
	/**
	 * 前后变化几月
	 * @param changeDay 变化的月数 （往后为正数 ，往前为负数）
	 * @param format 时间格式
	 * @return 
	 */
	public String getChangeMonth(int changeMonth,String format) {
		
		Calendar newcalendar = Calendar.getInstance();
		newcalendar.setTime(calendar.getTime());
		newcalendar.add(Calendar.MONTH, changeMonth);
		String str = new SimpleDateFormat(format).format(newcalendar.getTime());
		return str;
		
	}
	public static void main(String[] args) {
		CalendarUtil cu = new CalendarUtil(new Date());
		 
		;
		System.out.println(cu.getChangeMonth(-3, "yyyy-MM-dd"));
	}
}
