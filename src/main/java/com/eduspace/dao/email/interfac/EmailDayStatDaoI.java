package com.eduspace.dao.email.interfac;

import java.math.BigDecimal;

import com.eduspace.entity.email.EmailDayStat;
import com.sooncode.jdbc.DaoI;

public interface EmailDayStatDaoI extends DaoI<EmailDayStat> {
    
	 
public Integer get (String productId,String date);	
public BigDecimal getNumber(String productId, String startDate,String endDate, String messageState) ; 
}
