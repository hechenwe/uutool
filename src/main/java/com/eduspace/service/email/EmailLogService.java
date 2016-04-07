package com.eduspace.service.email;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.eduspace.dao.CommonDao;
import com.eduspace.dao.email.interfac.EmailDayStatDaoI;
import com.eduspace.dao.email.interfac.EmailDayTypeStatDaoI;
import com.eduspace.dao.email.interfac.EmailLogDaoI;
import com.eduspace.dao.email.interfac.EmailMonthStatDaoI;
import com.eduspace.dao.email.interfac.EmailStatDaoI;
import com.eduspace.entity.email.EmailDayStat;
import com.eduspace.entity.email.EmailDayTypeStat;
import com.eduspace.entity.email.EmailLog;
import com.eduspace.entity.email.EmailMonthStat;
import com.eduspace.entity.sms.SmsDayTypeStat;
import com.eduspace.entity.sms.SmsLog;
import com.eduspace.entity.sms.SmsMonthStat;
import com.eduspace.util.CalendarUtil;
import com.eduspace.util.ClassCache;
import com.sooncode.jdbc.Jdbc;

@Service
public class EmailLogService {
	
	public EmailDayStatDaoI emailDayStatDao = (EmailDayStatDaoI) ClassCache.getImplementObject(EmailDayStatDaoI.class);

	public EmailLogDaoI emailLogDao = (EmailLogDaoI) ClassCache.getImplementObject(EmailLogDaoI.class);

	public EmailMonthStatDaoI emailMonthStatDao = (EmailMonthStatDaoI) ClassCache.getImplementObject(EmailMonthStatDaoI.class);

	public EmailStatDaoI emailStatDao = (EmailStatDaoI) ClassCache.getImplementObject(EmailStatDaoI.class);
	public EmailDayTypeStatDaoI emailDayTypeStatDao = (EmailDayTypeStatDaoI) ClassCache.getImplementObject(EmailDayTypeStatDaoI.class);
	
	public Map<String,Object> getDetail(String productId,String type){
    	Map<String,Object> map= new HashMap<>();
    	
    	CalendarUtil cu = new CalendarUtil(new Date());
		String format = "yyyy-MM-dd";
		String today = cu.getString(format);
		String startDay = today;
    	if(type.equals("today")){
    		startDay = today;
    	}else if(type.equals("day3")){
    		
    		startDay= cu.getChangeDay(-3, format); 
    		 
    	}else if(type.equals("day7")){
    		startDay = cu.getChangeDay(-7, format); 
    	 
    	}else if(type.equals("day30")){
    		startDay = cu.getChangeDay(-30, format); 
    	 
    	}else if(type.equals("day183")){
    		startDay = cu.getChangeDay(-183, format); 
    		 
    	}else if(type.equals("day365")){
    		startDay = cu.getChangeDay(-365, format); 
    	} else{
    		map.put("message","参数type错误！");
    		return map;
    	}
    	
    	map.put("typeStat",emailDayTypeStatDao.getTypeStat(productId, startDay, today));
		map.put("scopeData",CommonDao.getScopeStat(new Jdbc(), productId, type, EmailLog.class, EmailDayStat.class, EmailMonthStat.class));
		return map;
    }

}
