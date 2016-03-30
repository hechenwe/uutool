package com.eduspace.service.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduspace.dao.sms.SmsLogDao;
import com.eduspace.entity.sms.SmsLog;
 

@Service
public class LogService {
	@Autowired
	private SmsLogDao logDao;

	public void saveLog(SmsLog log) {
		logDao.save( log);
	}

}
