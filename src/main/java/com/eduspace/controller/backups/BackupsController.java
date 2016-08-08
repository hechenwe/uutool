package com.eduspace.controller.backups;

import java.util.HashMap;
 
 
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eduspace.dao.email.EmailLogDao;
import com.eduspace.dao.jpush.JpushLogDao;
import com.eduspace.dao.sms.SmsLogDao;
import com.eduspace.entity.email.EmailLog;
import com.eduspace.entity.jpush.JpushLog;
import com.eduspace.entity.sms.SmsLog;
import com.eduspace.util.RequestUtil;
import com.sooncode.jdbc.util.Pager;

/**
 * 短信日志备份
 * @author pc
 *
 */
@Controller
@RequestMapping("backupsController")
public class BackupsController {

	private SmsLogDao bSmsLogDao = new SmsLogDao("tool_backups");
	private EmailLogDao bEmailLogDao = new EmailLogDao("tool_backups");
	private JpushLogDao bJpushLogDao = new JpushLogDao("tool_backups");
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping( value="getSmsLogBackups", method=RequestMethod.POST )
	@ResponseBody
	public Map<String,Object> getSmsLogBackups(HttpServletRequest request){
		RequestUtil ru = new RequestUtil(request);
		Long pageNum = ru.getLong("pageNum") ;
		Long pageSize = ru.getLong("pageSize") ;
		 
		SmsLog smsLog = (SmsLog) ru.getEntity(SmsLog.class);
		
		Pager<SmsLog> smsLogs = bSmsLogDao.getPager(pageNum, pageSize, smsLog);
		
		Map <String,Object> map = new HashMap<>();
		map.put("page",smsLogs);
		return map;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping( value="deleteSmsLogBackups", method=RequestMethod.POST )
	@ResponseBody
	public Map<String,Object> deleteSmsLogBackups(HttpServletRequest request){
		RequestUtil ru = new RequestUtil(request);
		String dateString = ru.getString("dateString") ;
		Long n = bSmsLogDao.deleteSms(dateString);
		Map <String,Object> map = new HashMap<>();
		if(n >0){
			map.put("delete","success");
		}else{
			map.put("delete","failed");
		}
		return map;
	}
	
	
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping( value="getEmailLogBackups", method=RequestMethod.POST )
	@ResponseBody
	public Map<String,Object> getEmailLogBackups(HttpServletRequest request){
		RequestUtil ru = new RequestUtil(request);
		Long pageNum = ru.getLong("pageNum") ;
		Long pageSize = ru.getLong("pageSize") ;
		
		EmailLog emailLog = (EmailLog) ru.getEntity(EmailLog.class);
		
		Pager<EmailLog> emailLogs = bEmailLogDao.getPager(pageNum, pageSize, emailLog);
		
		Map <String,Object> map = new HashMap<>();
		map.put("page",emailLogs);
		return map;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping( value="deleteEmailLogBackups", method=RequestMethod.POST )
	@ResponseBody
	public Map<String,Object> deleteEmailLogBackups(HttpServletRequest request){
		RequestUtil ru = new RequestUtil(request);
		String dateString = ru.getString("dateString") ;
		Long n = bEmailLogDao.deleteSms(dateString);
		Map <String,Object> map = new HashMap<>();
		if(n >0){
			map.put("delete","success");
		}else{
			map.put("delete","failed");
		}
		return map;
	}
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping( value="getJpushLogBackups", method=RequestMethod.POST )
	@ResponseBody
	public Map<String,Object> getJpushLogBackups(HttpServletRequest request){
		RequestUtil ru = new RequestUtil(request);
		Long pageNum = ru.getLong("pageNum") ;
		Long pageSize = ru.getLong("pageSize") ;
		JpushLog jpushLog = (JpushLog) ru.getEntity(JpushLog.class);
		Pager<JpushLog> jpushLogs = bJpushLogDao.getPager(pageNum, pageSize, jpushLog);
		Map <String,Object> map = new HashMap<>();
		map.put("page",jpushLogs);
		return map;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping( value="deleteJpushLogBackups", method=RequestMethod.POST )
	@ResponseBody
	public Map<String,Object> deleteJpushLogBackups(HttpServletRequest request){
		RequestUtil ru = new RequestUtil(request);
		String dateString = ru.getString("dateString") ;
		Long n = bJpushLogDao.deleteSms(dateString);
		Map <String,Object> map = new HashMap<>();
		if(n >0){
			map.put("delete","success");
		}else{
			map.put("delete","failed");
		}
		return map;
	}
}
