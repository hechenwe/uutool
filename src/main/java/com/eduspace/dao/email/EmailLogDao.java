package com.eduspace.dao.email;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.internal.compiler.ast.ArrayAllocationExpression;
import org.springframework.stereotype.Repository;

import com.eduspace.dao.email.interfac.EmailLogDaoI;
import com.eduspace.dao.sms.interfac.SmsLogDaoI;
import com.eduspace.entity.email.EmailDayTypeStat;
import com.eduspace.entity.email.EmailLog;
import com.eduspace.entity.sms.SmsDayTypeStat;
import com.eduspace.entity.sms.SmsLog;
import com.sooncode.jdbc.Dao;
 
import com.sooncode.jdbc.sql.SQL;
@Repository
public class EmailLogDao extends Dao<EmailLog> implements EmailLogDaoI{

	@Override
	public List<String> getProductIds(String requestDate, String messageState) {
		SQL sql = new SQL();
		sql.SELECT().DISTINCT("productId").FROM().TABLE(EmailLog.class).WHERE().LIKE("requestDate", requestDate).AND().EQ("messageState", messageState);
		
		List<Map<String,Object>> oldList = jdbc.executeQueryL(sql.toString());
		List<String> list = new ArrayList<>();
		
		for (Map<String,Object> m : oldList) {
			list.add((String) m.get("productId"));
		}
		
		return list;
	}
 
	
	@Override
	public Long getNumber(String productId, String requestDate, String messageState) {
		 
		SQL sql = new SQL();
		sql = sql.SELECT().COUNT("1").AS("number").FROM().TABLE(EmailLog.class)
				.WHERE().EQ("productId", productId).AND().EQ("messageState", messageState).AND().LIKE("requestDate",requestDate);
		Map<String,Object> map = jdbc.executeQueryM(sql.toString());
		Long number = (Long) map.get("number");
		return number;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<EmailDayTypeStat> getTypeOfNumber(String productId, String requestDate) {
		SQL sql = new SQL();
		sql = sql.SELECT().COUNT("1").AS("number").PUT_KEY(",SEND_TYPE AS TYPE")
				.FROM().TABLE(EmailLog.class)
				.WHERE().EQ("productId",productId)
				.AND().LIKE("requestDate", requestDate)
				.GROUP_BY("sendType");
		
		List<EmailDayTypeStat> sdtss = new ArrayList<>();
		sdtss = (List<EmailDayTypeStat>) jdbc.executeQuerys(sql.toString(), EmailDayTypeStat.class);
		
		return sdtss;
	}


	 
}
