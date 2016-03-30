package com.eduspace.dao.jpush;

import java.util.List;
import java.util.Map;

import com.eduspace.dao.jpush.interfac.JpushDayTypeStatDaoI;
import com.eduspace.entity.jpush.JpushDayTypeStat;
import com.sooncode.jdbc.Dao;
import com.sooncode.jdbc.sql.SQL;

/**
 * 按天统计 短信日志
 * 
 * @author pc
 *
 */

public class JpushDayTypeStatDao extends Dao<JpushDayTypeStat>implements JpushDayTypeStatDaoI {

	@Override
	public Integer getId(String productId, String date, String type) {
		SQL sql = new SQL();
		sql.SELECT().PUT_KEY("Id").FROM().TABLE(JpushDayTypeStat.class).WHERE().LIKE("date", date)
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
	public List<Map<String, Object>> getTypeStat(String productId, String startDate,String endDate) {
		String sql = "SELECT SUM(NUMBER) AS NUMBER ,TYPE FROM JPUSH_DAY_TYPE_STAT WHERE PRODUCT_ID = '"+productId+"' AND DATE(DATE) BETWEEN '"+startDate+"' AND '"+endDate+"' GROUP BY TYPE";
		return jdbc.executeQueryL(sql);
	}
}
