package com.eduspace.dao.sms.interfac;

 

import com.eduspace.entity.sms.SmsMonthStat;
import com.sooncode.jdbc.DaoI;

public interface SmsMonthStatDaoI extends DaoI<SmsMonthStat> {
  
	/**
	 * 获取 smsMonthStat 的 id 
	 * @param productId 产品编号
	 * @param month 月份（包含年）如：2016-04
	 * @return
	 */
public Integer getId(String productId,String month);
	 
}
