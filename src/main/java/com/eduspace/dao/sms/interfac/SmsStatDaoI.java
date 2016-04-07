package com.eduspace.dao.sms.interfac;

import java.util.Map;

import com.eduspace.entity.sms.SmsStat;
import com.sooncode.jdbc.DaoI;

public interface SmsStatDaoI extends DaoI<SmsStat> {

	public Map<String, Object> getTotal();

}
