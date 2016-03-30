package com.eduspace.dao.sms.interfac;

import java.math.BigDecimal;

import com.eduspace.entity.sms.SmsDayStat;
import com.sooncode.jdbc.DaoI;

public interface SmsDayStatDaoI extends DaoI<SmsDayStat> {
    
	 
public Integer get (String productId,String date);	
public BigDecimal getNumber(String productId, String startDate,String endDate, String messageState) ; 
}
