package com.eduspace.dao.sms;

import org.springframework.stereotype.Repository;

import com.eduspace.dao.sms.interfac.SmsStatDaoI;
import com.eduspace.entity.sms.SmsStat;
import com.sooncode.jdbc.Dao;
 
@Repository
public class SmsStatDao extends Dao<SmsStat> implements SmsStatDaoI {
 
	
	
}
