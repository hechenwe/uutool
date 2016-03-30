package com.eduspace.dao.email.interfac;

 

 
import java.util.List;
import java.util.Map;

import com.eduspace.entity.email.EmailDayTypeStat;
import com.sooncode.jdbc.DaoI;

public interface EmailDayTypeStatDaoI extends DaoI<EmailDayTypeStat> {
    
 /**
  * 获取 	SmsDayTypeStat 的id 
  * @param productId
  * @param date
  * @param type
  * @return
  */
 public Integer getId(String productId, String date,String type );

 public  List<Map<String, Object>> getTypeStat(String productId, String startDate, String endDate);
}
