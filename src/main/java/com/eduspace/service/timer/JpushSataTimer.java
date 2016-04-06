package com.eduspace.service.timer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.eduspace.dao.jpush.interfac.JpushDayStatDaoI;
import com.eduspace.dao.jpush.interfac.JpushDayTypeStatDaoI;
import com.eduspace.dao.jpush.interfac.JpushLogDaoI;
import com.eduspace.dao.jpush.interfac.JpushMonthStatDaoI;
import com.eduspace.dao.jpush.interfac.JpushStatDaoI;
import com.eduspace.entity.jpush.JpushDayStat;
import com.eduspace.entity.jpush.JpushDayTypeStat;
import com.eduspace.entity.jpush.JpushMonthStat;
import com.eduspace.entity.jpush.JpushStat;
import com.eduspace.util.CalendarUtil;
import com.eduspace.util.ClassCache;
import com.eduspace.util.String2Date;

/**
 * 统计短信日志 定时器
 * 
 * @author pc
 *
 */
@Service
public class JpushSataTimer {

	private static Logger logger = Logger.getLogger("SataTimer.class");

	private JpushDayStatDaoI jpushDayStatDao = (JpushDayStatDaoI) ClassCache.getImplementObject(JpushDayStatDaoI.class);

	private JpushLogDaoI jpushLogDao = (JpushLogDaoI) ClassCache.getImplementObject(JpushLogDaoI.class);

	private JpushMonthStatDaoI jpushMonthStatDao = (JpushMonthStatDaoI) ClassCache.getImplementObject(JpushMonthStatDaoI.class);

	private JpushStatDaoI jpushStatDao = (JpushStatDaoI) ClassCache.getImplementObject(JpushStatDaoI.class);
	private JpushDayTypeStatDaoI jpushDayTypeStatDao= (JpushDayTypeStatDaoI) ClassCache.getImplementObject(JpushDayTypeStatDaoI.class);

	@Scheduled(cron = "0/60 * *  * * ?")
	public void sata() {
		logger.info("【推送--定时器】：" + new Date());
		String format = "yyyy-MM-dd";

		String today = String2Date.getString(new Date(), format);
	 

		List<String> productIds = jpushLogDao.getProductIds(today, "");

		for (String p : productIds) {
			// --------------------------------------------------------------
			Long sucNumber = jpushLogDao.getNumber(p, today, "1"); // 今天成功数
			Long falNumber = jpushLogDao.getNumber(p, today, "0"); // 今天失败数

			Integer dayId = jpushDayStatDao.get(p, today);

			JpushDayStat sds = new JpushDayStat();
			sds.setSucNumber(Integer.valueOf(sucNumber + ""));
			sds.setFalNumber(Integer.valueOf(falNumber + ""));
			sds.setDate(new Date());
			sds.setProductId(p);
			if (dayId != null) {
				sds.setDayId(dayId);
				jpushDayStatDao.update(sds);
			} else {
				jpushDayStatDao.save(sds);

			}
			// --------------------------------------------------------------
			CalendarUtil cu = new CalendarUtil(new Date());
			String startDay = cu.getFirstDayOfMonth(format);
			String endDay = cu.getLastDayOfMonth(format);
			BigDecimal sucNum = jpushDayStatDao.getNumber(p, startDay, endDay, "1");
			BigDecimal falNum = jpushDayStatDao.getNumber(p, startDay, endDay, "0");

			JpushMonthStat jpush = new JpushMonthStat();
			jpush.setProductId(p);
			jpush.setSucNumber(sucNum.intValue());
			jpush.setFalNumber(falNum.intValue());
			String thisMonth = String2Date.getString(new Date(), "yyyy-MM");
			jpush.setMonth(thisMonth);

			Integer id = jpushMonthStatDao.getId(p, thisMonth);
			if (id == null) {
				jpushMonthStatDao.save(jpush);

			} else {
				jpush.setId(id);
				jpushMonthStatDao.update(jpush);
			}
			// --------------------------------------------------------------
			// 最近3天

			String today3 = cu.getChangeDay(-3, format);
			int threeFal = jpushDayStatDao.getNumber(p, today3, today, "0").intValue();
			int threeSuc = jpushDayStatDao.getNumber(p, today3, today, "1").intValue();
			// 最近一周
			String today7 = cu.getChangeDay(-7, format);
			int sevenFal = jpushDayStatDao.getNumber(p, today7, today, "0").intValue();
			int sevenSuc = jpushDayStatDao.getNumber(p, today7, today, "1").intValue();

			// 最近一个月
			String today30 = cu.getChangeDay(-30, format);
			int monthFal = jpushDayStatDao.getNumber(p, today30, today, "0").intValue();
			int monthSuc = jpushDayStatDao.getNumber(p, today30, today, "1").intValue();
			// 最近半年
			String todayHalfYear = cu.getChangeDay(-183, format);
			int halfYearFal = jpushDayStatDao.getNumber(p, todayHalfYear, today, "0").intValue();
			int halfYearSuc = jpushDayStatDao.getNumber(p, todayHalfYear, today, "1").intValue();

			// 最近一年
			String todayYear = cu.getChangeDay(-365, format);
			int yearFal = jpushDayStatDao.getNumber(p, todayYear, today, "0").intValue();
			int yearSuc = jpushDayStatDao.getNumber(p, todayYear, today, "1").intValue();

			JpushStat ss = new JpushStat();
			ss.setProductId(p);
			ss.setTodaySuc(sucNumber.intValue());
			ss.setTodayFal(falNumber.intValue());

			ss.setThreeSuc(threeSuc);
			ss.setThreeFal(threeFal);

			ss.setSevenSuc(sevenSuc);
			ss.setSevenFal(sevenFal);
			
			ss.setMonthFal(monthFal);
			ss.setMonthSuc(monthSuc);
			
			ss.setHalfYearFal(halfYearFal);
			ss.setHalfYearSuc(halfYearSuc);
			
			ss.setYearFal(yearFal);
			ss.setYearSuc(yearSuc);
			JpushStat oldSs =  new JpushStat(); 
			oldSs.setProductId(p)	;
			oldSs=jpushStatDao.get(oldSs);
			if(oldSs ==null){
					jpushStatDao.save(ss);
				
			}else{
				Integer statId = oldSs.getStatId();
				
				ss.setStatId(statId);
				jpushStatDao.update(ss);
			}
			
			// --------------------------------------------------------------
			
			List<JpushDayTypeStat> sdtss =  jpushLogDao.getTypeOfNumber(p, today);
			
			for (JpushDayTypeStat sdts : sdtss) {
				sdts.setProductId(p);
				sdts.setDate(new Date());
				
				Integer jpushDayTypeStatId = jpushDayTypeStatDao.getId(p, today, sdts.getType());
				if(jpushDayTypeStatId==null){
					jpushDayTypeStatDao.save(sdts);
					
				}else{
					sdts.setId(jpushDayTypeStatId);
					jpushDayTypeStatDao.update(sdts);
				}
				
				
			}
			
			
			 
			// --------------------------------------------------------------
		}

 
		
	 
		
		
		
	}
}
