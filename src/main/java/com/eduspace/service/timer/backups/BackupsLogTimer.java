package com.eduspace.service.timer.backups;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.eduspace.dao.email.interfac.EmailLogDaoI;
import com.eduspace.dao.jpush.interfac.JpushLogDaoI;
import com.eduspace.dao.sms.SmsLogDao;
import com.eduspace.dao.email.EmailLogDao;
import com.eduspace.dao.jpush.JpushLogDao;
import com.eduspace.dao.sms.interfac.SmsLogDaoI;
import com.eduspace.entity.email.EmailLog;
import com.eduspace.entity.jpush.JpushLog;
import com.eduspace.entity.sms.SmsLog;
import com.eduspace.util.CalendarUtil;
import com.eduspace.util.ClassCache;
import com.eduspace.util.PathUtil;
import com.eduspace.util.PropertiesUtil;

/**
 * 备份日志 定时器
 * 
 * @author pc
 *
 */
@Service
public class BackupsLogTimer {

	private static Logger logger = Logger.getLogger("SataTimer.class");

	private SmsLogDaoI smsLogDao = (SmsLogDaoI) ClassCache.getImplementObject(SmsLogDaoI.class);
	private EmailLogDaoI emailLogDao = (EmailLogDaoI) ClassCache.getImplementObject(EmailLogDaoI.class);
	private JpushLogDaoI jpushLogDao = (JpushLogDaoI) ClassCache.getImplementObject(JpushLogDaoI.class);

	
	private SmsLogDao bSmsLogDao = new SmsLogDao("tool_backups");
	private EmailLogDao bEmailLogDao = new EmailLogDao("tool_backups");
	private JpushLogDao bJpushLogDao = new JpushLogDao("tool_backups");
	
	@Scheduled(cron = "0 0 12 * * ?")//每天中午12点
	public void backups() {
		logger.info("【备份日志--定时器】：" + new Date());
		PropertiesUtil pu = new PropertiesUtil(PathUtil.getClassPath()+File.separatorChar+"backups_log.properties");
		CalendarUtil cu = new CalendarUtil(new Date());
		String format = "yyyy-MM-dd";
		String startDay= cu.getChangeDay(-1*pu.getInt("day"), format); 
		int pageSize = pu.getInt("pageSize");
		backupsSms(startDay,pageSize);
		backupsEmail(startDay,pageSize);
		backupsJpush(startDay,pageSize);
		
	}
	
	/**
	 * 
	 * @param startDay
	 * @param pageSize
	 */
	private void backupsSms(String startDay,int pageSize){
		SmsLog smsLog = new SmsLog();
		smsLog.setRequestDate(new Date());
		Long smsSize = smsLogDao.getSmsSize(startDay);
		for(int i = 0;i<=smsSize/pageSize;i++){
			List<SmsLog> smsLogs = smsLogDao.startGets(smsLog).like("requestDate",startDay).limit(i+1,pageSize).endGets();
			bSmsLogDao.saves(smsLogs);
		}
		 smsLogDao.deleteSms(startDay);
	}
	private void backupsEmail(String startDay,int pageSize){
		EmailLog emailLog = new EmailLog();
		emailLog.setRequestDate(new Date());
		Long emailSize = emailLogDao.getSmsSize(startDay);
		for(int i = 0;i<=emailSize/pageSize;i++){
			List<EmailLog> smsLogs = emailLogDao.startGets(emailLog).like("requestDate",startDay).limit(i+1,pageSize).endGets();
			bEmailLogDao.saves(smsLogs);
		}
		emailLogDao.deleteSms(startDay);
	}
	private void backupsJpush(String startDay,int pageSize){
		JpushLog jpushLog = new JpushLog();
		jpushLog.setRequestDate(new Date());
		Long jpushSize = jpushLogDao.getSmsSize(startDay);
		for(int i = 0;i<=jpushSize/pageSize;i++){
			List<JpushLog> smsLogs = jpushLogDao.startGets(jpushLog).like("requestDate",startDay).limit(i+1,pageSize).endGets();
			bJpushLogDao.saves(smsLogs);
		}
		jpushLogDao.deleteSms(startDay);
	}
}
