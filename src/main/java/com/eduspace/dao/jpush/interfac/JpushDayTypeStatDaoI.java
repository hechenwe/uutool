package com.eduspace.dao.jpush.interfac;

 

 
 
import java.util.List;
import java.util.Map;

import com.eduspace.entity.jpush.JpushDayTypeStat;
import com.sooncode.jdbc.DaoI;

public interface JpushDayTypeStatDaoI extends DaoI<JpushDayTypeStat> {
    
 /**
  * 获取 	SmsDayTypeStat 的id 
  * @param productId
  * @param date
  * @param type
  * @return
  */
 public Integer getId(String productId, String date,String type );

List<Map<String, Object>> getTypeStat(String productId, String startDate, String endDate);
}
