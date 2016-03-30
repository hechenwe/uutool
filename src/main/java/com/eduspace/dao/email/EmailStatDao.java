package com.eduspace.dao.email;

import org.springframework.stereotype.Repository;

import com.eduspace.dao.email.interfac.EmailStatDaoI;
import com.eduspace.dao.sms.interfac.SmsStatDaoI;
import com.eduspace.entity.email.EmailStat;
import com.eduspace.entity.sms.SmsStat;
import com.sooncode.jdbc.Dao;
 
@Repository
public class EmailStatDao extends Dao<EmailStat> implements EmailStatDaoI {
 
	
	
}
