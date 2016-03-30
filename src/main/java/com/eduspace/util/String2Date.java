package com.eduspace.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class String2Date {

private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";	
	
/**
* 日期转换成字符串
* @param date 
* @return str
*/
public static String getString(Date date) {
  
   SimpleDateFormat format = new SimpleDateFormat(FORMAT);
   String str = format.format(date);
   return str;
} 

/**
 * 日期转换成字符串
 * @param date 
 * @return str
 */
public static String getString(Date date,String formatString) {
	
	SimpleDateFormat format = new SimpleDateFormat(formatString);
	String str = format.format(date);
	return str;
} 

 
/**
 * 字符串转换成日期
 * @param str
 * @return date
 */
public static Date getDate(String string) {
	
	SimpleDateFormat format = new SimpleDateFormat(FORMAT);
	Date date = null;
	try {
		date = format.parse(string);
	} catch (ParseException e) {
		e.printStackTrace();
	}
	return date;
}
/**
 * 字符串转换成日期
 * @param str
 * @return date
 */
public static Date getDate(String string,String formatString) {
	
	SimpleDateFormat format = new SimpleDateFormat(formatString);
	Date date = null;
	try {
		date = format.parse(string);
	} catch (ParseException e) {
		e.printStackTrace();
	}
	return date;
}

public static void main(String[] args) {
  
   
}

}