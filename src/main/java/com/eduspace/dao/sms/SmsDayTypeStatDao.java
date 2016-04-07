package com.eduspace.dao.sms;

import java.util.List;
import java.util.Map;

import com.eduspace.dao.sms.interfac.SmsDayTypeStatDaoI;
import com.eduspace.entity.sms.SmsDayStat;
import com.eduspace.entity.sms.SmsDayTypeStat;
import com.eduspace.entity.sms.SmsStat;
import com.eduspace.util.Ent2Map;
import com.sooncode.jdbc.Dao;
import com.sooncode.jdbc.sql.SQL;

/**
 * 按天统计 短信日志
 * 
 * @author pc
 *
 */

public class SmsDayTypeStatDao extends Dao<SmsDayTypeStat>implements SmsDayTypeStatDaoI {

	@Override
	public Integer getId(String productId, String date, String type) {
		SQL sql = new SQL();
		sql.SELECT().PUT_KEY("Id").FROM().TABLE(SmsDayTypeStat.class).WHERE().LIKE("date", date)
		.AND().EQ("productId", productId)
		.AND().EQ("type", type);
		Map<String, Object> map = jdbc.executeQueryM(sql.toString());

		if (map == null) {
			return null;
		}

		Integer id = (Integer) map.get("id");
		return id;
	}

	@Override
	public List<Map<String, Object>> getSmsTypeStat(String productId, String startDate,String endDate) {
		String sql ="";
		if(productId==null || productId.trim().equals("")){
			 sql = "SELECT SUM(NUMBER) AS NUMBER ,TYPE FROM SMS_DAY_TYPE_STAT WHERE DATE(DATE) BETWEEN '"+startDate+"' AND '"+endDate+"' GROUP BY TYPE";
			 logger.info("【getSmsTypeStat】："+sql);
		}else{
			 sql = "SELECT SUM(NUMBER) AS NUMBER ,TYPE FROM SMS_DAY_TYPE_STAT WHERE PRODUCT_ID = '"+productId+"' AND DATE(DATE) BETWEEN '"+startDate+"' AND '"+endDate+"' GROUP BY TYPE";
			 
		}
		return jdbc.executeQueryL(sql);
	}

}
