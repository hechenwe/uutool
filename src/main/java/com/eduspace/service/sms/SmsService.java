package com.eduspace.service.sms;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import org.springframework.stereotype.Service;

import com.eduspace.dao.CommonDao;
import com.eduspace.dao.sms.interfac.SmsDayStatDaoI;
import com.eduspace.dao.sms.interfac.SmsDayTypeStatDaoI;
import com.eduspace.dao.sms.interfac.SmsLogDaoI;
import com.eduspace.dao.sms.interfac.SmsMonthStatDaoI;
import com.eduspace.dao.sms.interfac.SmsStatDaoI;
import com.eduspace.entity.sms.SmsDayStat;
import com.eduspace.entity.sms.SmsDayTypeStat;
import com.eduspace.entity.sms.SmsLog;
import com.eduspace.entity.sms.SmsMonthStat;
import com.eduspace.util.CalendarUtil;
import com.eduspace.util.ClassCache;
import com.sooncode.jdbc.Jdbc;

@Service
public class SmsService {
	public SmsDayStatDaoI smsDayStatDao = (SmsDayStatDaoI) ClassCache.getImplementObject(SmsDayStatDaoI.class);

	public SmsLogDaoI smsLogDao = (SmsLogDaoI) ClassCache.getImplementObject(SmsLogDaoI.class);

	public SmsMonthStatDaoI smsMonthStatDao = (SmsMonthStatDaoI) ClassCache.getImplementObject(SmsMonthStatDaoI.class);

	public SmsStatDaoI smsStatDao = (SmsStatDaoI) ClassCache.getImplementObject(SmsStatDaoI.class);
	public SmsDayTypeStatDaoI smsDayTypeStatDao = (SmsDayTypeStatDaoI) ClassCache.getImplementObject(SmsDayTypeStatDaoI.class);

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
    	
    	map.put("typeStat",smsDayTypeStatDao.getSmsTypeStat(productId, startDay, today));
		map.put("scopeData",CommonDao.getScopeStat(new Jdbc(), productId, type, SmsLog.class, SmsDayStat.class, SmsMonthStat.class));
		return map;
    }

}
