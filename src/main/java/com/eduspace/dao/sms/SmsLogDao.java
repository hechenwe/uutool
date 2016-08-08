package com.eduspace.dao.sms;

import java.util.ArrayList;
 
 
import java.util.List;
import java.util.Map;
 

import org.springframework.stereotype.Repository;
import com.eduspace.dao.sms.interfac.SmsLogDaoI;
import com.eduspace.entity.sms.SmsDayTypeStat;
import com.eduspace.entity.sms.SmsLog;
 
import com.sooncode.jdbc.Dao;
import com.sooncode.jdbc.Jdbc;
import com.sooncode.jdbc.sql.SQL;

@Repository
public class SmsLogDao extends Dao<SmsLog>implements SmsLogDaoI {

	public SmsLogDao(){
		super.jdbc = new Jdbc();
	}
	public SmsLogDao(String dbKey){
		super.jdbc = new Jdbc(dbKey);
	}
	@Override
	public List<String> getProductIds(String requestDate, String messageState) {
		SQL sql = new SQL();
		sql.SELECT().DISTINCT("productId").FROM().TABLE(SmsLog.class);//.WHERE().LIKE("requestDate", requestDate).AND().EQ("messageState", messageState);

		List<Map<String, Object>> oldList = jdbc.executeQueryL(sql.toString());
		List<String> list = new ArrayList<>();

		for (Map<String, Object> m : oldList) {
			list.add((String) m.get("productId"));
		}
		return list;
	}

	@Override
	public Long getNumber(String productId, String requestDate, String messageState) {

		SQL sql = new SQL();
		sql = sql.SELECT().COUNT("1").AS("number").FROM().TABLE(SmsLog.class).WHERE().EQ("productId", productId).AND().EQ("messageState", messageState).AND().LIKE("requestDate", requestDate);
		Map<String, Object> map = jdbc.executeQueryM(sql.toString());
		Long number = (Long) map.get("number");
		return number;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SmsDayTypeStat> getTypeOfNumber(String productId, String requestDate) {
		SQL sql = new SQL();
		sql = sql.SELECT().COUNT("1").AS("number").PUT_KEY(",SEND_TYPE AS TYPE").FROM().TABLE(SmsLog.class).WHERE().EQ("productId", productId).AND().LIKE("requestDate", requestDate).GROUP_BY("sendType");

		List<SmsDayTypeStat> sdtss = new ArrayList<>();
		sdtss = (List<SmsDayTypeStat>) jdbc.executeQuerys(sql.toString(), SmsDayTypeStat.class);
		return sdtss;
	}
	@Override
	public Long getSmsSize(String dateString) {
		String sql = "SELECT COUNT(1) AS NUMBER FROM SMS_LOG WHERE REQUEST_DATE LIKE '%"+dateString+"%'";
	//	logger.info(sql);
		Map<String ,Object> map = jdbc.executeQueryM(sql);
		return (Long) map.get("number");
	}
	@Override
	public Long deleteSms(String dateString) {
		String sql = "DELETE FROM SMS_LOG WHERE REQUEST_DATE LIKE '%"+dateString+"%'";
		return jdbc.executeUpdate(sql);
	}

 

}
