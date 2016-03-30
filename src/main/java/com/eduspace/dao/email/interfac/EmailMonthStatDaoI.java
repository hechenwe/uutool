package com.eduspace.dao.email.interfac;

 

import com.eduspace.entity.email.EmailMonthStat;
import com.sooncode.jdbc.DaoI;

public interface EmailMonthStatDaoI extends DaoI<EmailMonthStat> {
  
	/**
	 * 获取 smsMonthStat 的 id 
	 * @param productId 产品编号
	 * @param month 月份（包含年）如：2016-04
	 * @return
	 */
public Integer getId(String productId,String month);
	 
}
