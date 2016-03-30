package com.eduspace.service.jpush;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eduspace.dao.CommonDao;
import com.eduspace.dao.jpush.interfac.JpushDayStatDaoI;
import com.eduspace.dao.jpush.interfac.JpushDayTypeStatDaoI;
import com.eduspace.dao.jpush.interfac.JpushLogDaoI;
import com.eduspace.dao.jpush.interfac.JpushMonthStatDaoI;
import com.eduspace.dao.jpush.interfac.JpushStatDaoI;
import com.eduspace.entity.email.EmailDayStat;
import com.eduspace.entity.email.EmailLog;
import com.eduspace.entity.email.EmailMonthStat;
import com.eduspace.entity.jpush.JpushDayStat;
import com.eduspace.entity.jpush.JpushDayTypeStat;
import com.eduspace.entity.jpush.JpushLog;
import com.eduspace.entity.jpush.JpushMonthStat;
import com.eduspace.entity.jpush.JpushStat;
import com.eduspace.util.CalendarUtil;
import com.eduspace.util.ClassCache;
import com.eduspace.util.Ent2Map;
import com.eduspace.util.RequestUtil;
import com.eduspace.util.String2Date;
import com.sooncode.jdbc.Jdbc;

@Service
public class JpushLogService {
	public JpushDayStatDaoI jpushDayStatDao = (JpushDayStatDaoI) ClassCache.getImplementObject(JpushDayStatDaoI.class);

	public JpushLogDaoI jpushLogDao = (JpushLogDaoI) ClassCache.getImplementObject(JpushLogDaoI.class);

	public JpushMonthStatDaoI jpushMonthStatDao = (JpushMonthStatDaoI) ClassCache.getImplementObject(JpushMonthStatDaoI.class);

	public JpushStatDaoI jpushStatDao = (JpushStatDaoI) ClassCache.getImplementObject(JpushStatDaoI.class);
	public JpushDayTypeStatDaoI jpushDayTypeStatDao = (JpushDayTypeStatDaoI) ClassCache.getImplementObject(JpushDayTypeStatDaoI.class);
	
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
    	
    	map.put("dayTypeStats",jpushDayTypeStatDao.getTypeStat(productId, startDay, today));
		map.put("scopeData",CommonDao.getScopeStat(new Jdbc(), productId, type, JpushLog.class, JpushDayStat.class, JpushMonthStat.class));
		return map;
    }
	
}
