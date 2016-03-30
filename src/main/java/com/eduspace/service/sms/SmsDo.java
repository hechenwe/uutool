package com.eduspace.service.sms;

import java.util.Date;

import com.eduspace.dao.sms.SmsLogDao;
import com.eduspace.entity.sms.SmsLog;
import com.eduspace.service.rabbitmq.Do;
import com.eduspace.util.JsonUtil;
import com.eduspace.util.String2Date;

/**
 * 短信 發送 和日誌記錄
 * @author pc
 *
 */
public class SmsDo implements Do {

	@Override
	public void doSomething(String mqMessage) {
		// TODO Auto-generated method stub
		SmsLog log = new SmsLog();
		log = new  JsonUtil<SmsLog>().getObject(mqMessage, SmsLog.class);
		log.setSendDate(String2Date.getString(new Date()) );
		try{
		SMSGateway.send(log.getPhone(), log.getMessage());
		log.setMessageState("1");
		}catch(Exception e) {
			log.setMessageState("0");
			e.printStackTrace();
		}finally {
			log.setLogDate(String2Date.getString(new Date()));
			new SmsLogDao().save(log);
			 
		}
	}

}
