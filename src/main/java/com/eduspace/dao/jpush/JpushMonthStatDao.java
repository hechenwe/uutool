package com.eduspace.dao.jpush;

import java.util.Map;

import com.eduspace.dao.jpush.interfac.JpushMonthStatDaoI;
import com.eduspace.dao.sms.interfac.SmsMonthStatDaoI;
import com.eduspace.entity.jpush.JpushMonthStat;
import com.eduspace.entity.sms.SmsMonthStat;
import com.sooncode.jdbc.Dao;
import com.sooncode.jdbc.sql.SQL;

public class JpushMonthStatDao extends Dao<JpushMonthStat>implements JpushMonthStatDaoI {

	@Override
	public Integer getId(String productId, String month) {
		SQL sql = new SQL();

		sql.SELECT().PUT_KEY("ID").FROM().TABLE(JpushMonthStat.class).WHERE().LIKE("month", month).AND().EQ("productId", productId);
		Map<String, Object> map = jdbc.executeQueryM(sql.toString());

		if (map == null) {
			return null;
		}

		Integer id = (Integer) map.get("id");
		return id;
	}

}
