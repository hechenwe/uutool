package com.eduspace.dao.sms;

import java.util.Map;

import com.eduspace.dao.sms.interfac.SmsMonthStatDaoI;
import com.eduspace.entity.sms.SmsMonthStat;
import com.sooncode.jdbc.Dao;
import com.sooncode.jdbc.sql.SQL;

public class SmsMonthStatDao extends Dao<SmsMonthStat>implements SmsMonthStatDaoI {

	@Override
	public Integer getId(String productId, String month) {
		SQL sql = new SQL();

		sql.SELECT().PUT_KEY("ID").FROM().TABLE(SmsMonthStat.class).WHERE().LIKE("month", month).AND().EQ("productId", productId);
		Map<String, Object> map = jdbc.executeQueryM(sql.toString());

		if (map == null) {
			return null;
		}

		Integer id = (Integer) map.get("id");
		return id;
	}

}
