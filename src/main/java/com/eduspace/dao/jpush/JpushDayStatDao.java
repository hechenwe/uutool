package com.eduspace.dao.jpush;

import java.math.BigDecimal;
import java.util.Map;

import com.eduspace.dao.jpush.interfac.JpushDayStatDaoI;
import com.eduspace.dao.sms.interfac.SmsDayStatDaoI;
import com.eduspace.entity.jpush.JpushDayStat;
import com.eduspace.entity.sms.SmsDayStat;
import com.eduspace.entity.sms.SmsLog;
import com.sooncode.jdbc.Dao;
import com.sooncode.jdbc.sql.SQL;

/**
 * 按天统计 短信日志
 * 
 * @author pc
 *
 */

public class JpushDayStatDao extends Dao<JpushDayStat>implements JpushDayStatDaoI {

	@Override
	public Integer get(String productId, String date) {
		SQL sql = new SQL();
		
		sql.SELECT().PUT_KEY("DAY_Id").FROM().TABLE(JpushDayStat.class).WHERE().LIKE("date", date).AND().EQ("productId", productId);
		Map<String, Object> map = jdbc.executeQueryM(sql.toString());

		if (map == null) {
			return null;
		}

		Integer id = (Integer) map.get("dayId");
		return id;
	}

	@Override
	public BigDecimal getNumber(String productId, String startDate, String endDate, String messageState) {

		SQL sql = new SQL();
		if(messageState.equals("1")){
			sql = sql.SELECT().SUM("sucNumber");
		}else{
			sql = sql.SELECT().SUM("falNumber");
		}
		sql = sql.AS("number").FROM().TABLE(JpushDayStat.class).WHERE().EQ("productId", productId).AND().DATE("date", startDate, endDate);
		Map<String, Object> map = jdbc.executeQueryM(sql.toString());
		BigDecimal number = (BigDecimal) map.get("number");
		return number;
	}

}
