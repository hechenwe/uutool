package com.eduspace.dao.sms.interfac;

 

 
import java.util.List;
import java.util.Map;

import com.eduspace.entity.sms.SmsDayTypeStat;
import com.sooncode.jdbc.DaoI;

public interface SmsDayTypeStatDaoI extends DaoI<SmsDayTypeStat> {
    
 /**
  * 获取 	SmsDayTypeStat 的id 
  * @param productId
  * @param date
  * @param type
  * @return
  */
 public Integer getId(String productId, String date,String type );
 /**
  * 获取 短信的类型 统计信息
  * @param productId
  * @param startDate 起始日期
  * @param endDate 结束日期
  * @return
  */
 public List<Map<String,Object>> getSmsTypeStat(String productId,String startDate,String endDate);
}
