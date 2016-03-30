package com.eduspace.dao.email;

import java.util.Map;

import com.eduspace.dao.email.interfac.EmailMonthStatDaoI;
import com.eduspace.dao.sms.interfac.SmsMonthStatDaoI;
import com.eduspace.entity.email.EmailMonthStat;
import com.eduspace.entity.sms.SmsMonthStat;
import com.sooncode.jdbc.Dao;
import com.sooncode.jdbc.sql.SQL;

public class EmailMonthStatDao extends Dao<EmailMonthStat>implements EmailMonthStatDaoI {

	@Override
	public Integer getId(String productId, String month) {
		SQL sql = new SQL();

		sql.SELECT().PUT_KEY("ID").FROM().TABLE(EmailMonthStat.class).WHERE().LIKE("month", month).AND().EQ("productId", productId);
		Map<String, Object> map = jdbc.executeQueryM(sql.toString());

		if (map == null) {
			return null;
		}

		Integer id = (Integer) map.get("id");
		return id;
	}

}
