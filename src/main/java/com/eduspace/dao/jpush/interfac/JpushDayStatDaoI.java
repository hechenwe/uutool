package com.eduspace.dao.jpush.interfac;

import java.math.BigDecimal;

import com.eduspace.entity.jpush.JpushDayStat;
import com.sooncode.jdbc.DaoI;

public interface JpushDayStatDaoI extends DaoI<JpushDayStat> {
    
	 
public Integer get (String productId,String date);	
public BigDecimal getNumber(String productId, String startDate,String endDate, String messageState) ; 
}
