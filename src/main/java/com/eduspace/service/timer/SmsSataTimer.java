package com.eduspace.service.timer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.eduspace.dao.sms.interfac.SmsDayStatDaoI;
import com.eduspace.dao.sms.interfac.SmsDayTypeStatDaoI;
import com.eduspace.dao.sms.interfac.SmsLogDaoI;
import com.eduspace.dao.sms.interfac.SmsMonthStatDaoI;
import com.eduspace.dao.sms.interfac.SmsStatDaoI;
import com.eduspace.entity.sms.SmsDayStat;
import com.eduspace.entity.sms.SmsDayTypeStat;
import com.eduspace.entity.sms.SmsMonthStat;
import com.eduspace.entity.sms.SmsStat;
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
public class SmsSataTimer {

	private static Logger logger = Logger.getLogger("SataTimer.class");

	private SmsDayStatDaoI smsDayStatDao = (SmsDayStatDaoI) ClassCache.getImplementObject(SmsDayStatDaoI.class);

	private SmsLogDaoI smsLogDao = (SmsLogDaoI) ClassCache.getImplementObject(SmsLogDaoI.class);

	private SmsMonthStatDaoI smsMonthStatDao = (SmsMonthStatDaoI) ClassCache.getImplementObject(SmsMonthStatDaoI.class);

	private SmsStatDaoI smsStatDao = (SmsStatDaoI) ClassCache.getImplementObject(SmsStatDaoI.class);
	private SmsDayTypeStatDaoI smsDayTypeStatDao= (SmsDayTypeStatDaoI) ClassCache.getImplementObject(SmsDayTypeStatDaoI.class);

	@Scheduled(cron = "0/5 * *  * * ?")
	public void sata() {
		logger.info("【短信--定时器】：" + new Date());
		String format = "yyyy-MM-dd";

		String today = String2Date.getString(new Date(), format);
	 
        
		List<String> productIds = smsLogDao.getProductIds(today, "1");

		for (String p : productIds) {
			// --------------------------------------------------------------
			Long sucNumber = smsLogDao.getNumber(p, today, "1"); // 今天成功数
			Long falNumber = smsLogDao.getNumber(p, today, "0"); // 今天失败数

			Integer dayId = smsDayStatDao.get(p, today);

			SmsDayStat sds = new SmsDayStat();
			sds.setSucNumber(Integer.valueOf(sucNumber + ""));
			sds.setFalNumber(Integer.valueOf(falNumber + ""));
			sds.setDate(new Date());
			sds.setProductId(p);
			if (dayId != null) {
				sds.setDayId(dayId);
				smsDayStatDao.update(sds);
			} else {
				smsDayStatDao.save(sds);

			}
			// --------------------------------------------------------------
			CalendarUtil cu = new CalendarUtil(new Date());
			String startDay = cu.getFirstDayOfMonth(format);
			String endDay = cu.getLastDayOfMonth(format);
			BigDecimal sucNum = smsDayStatDao.getNumber(p, startDay, endDay, "1");
			BigDecimal falNum = smsDayStatDao.getNumber(p, startDay, endDay, "0");

			SmsMonthStat sms = new SmsMonthStat();
			sms.setProductId(p);
			sms.setSucNumber(sucNum.intValue());
			sms.setFalNumber(falNum.intValue());
			String thisMonth = String2Date.getString(new Date(), "yyyy-MM");
			sms.setMonth(thisMonth);

			Integer id = smsMonthStatDao.getId(p, thisMonth);
			if (id == null) {
				smsMonthStatDao.save(sms);

			} else {
				sms.setId(id);
				smsMonthStatDao.update(sms);
			}
			// --------------------------------------------------------------
			// 最近3天

			String today3 = cu.getChangeDay(-3, format);
			int threeFal = smsDayStatDao.getNumber(p, today3, today, "0").intValue();
			int threeSuc = smsDayStatDao.getNumber(p, today3, today, "1").intValue();
			// 最近一周
			String today7 = cu.getChangeDay(-7, format);
			int sevenFal = smsDayStatDao.getNumber(p, today7, today, "0").intValue();
			int sevenSuc = smsDayStatDao.getNumber(p, today7, today, "1").intValue();

			// 最近一个月
			String today30 = cu.getChangeDay(-30, format);
			int monthFal = smsDayStatDao.getNumber(p, today30, today, "0").intValue();
			int monthSuc = smsDayStatDao.getNumber(p, today30, today, "1").intValue();
			// 最近半年
			String todayHalfYear = cu.getChangeDay(-183, format);
			int halfYearFal = smsDayStatDao.getNumber(p, todayHalfYear, today, "0").intValue();
			int halfYearSuc = smsDayStatDao.getNumber(p, todayHalfYear, today, "1").intValue();

			// 最近一年
			String todayYear = cu.getChangeDay(-365, format);
			int yearFal = smsDayStatDao.getNumber(p, todayYear, today, "0").intValue();
			int yearSuc = smsDayStatDao.getNumber(p, todayYear, today, "1").intValue();

			SmsStat ss = new SmsStat();
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
			SmsStat oldSs =  new SmsStat(); 
			oldSs.setProductId(p)	;
			oldSs=smsStatDao.get(oldSs);
			if(oldSs ==null){
					smsStatDao.save(ss);
				
			}else{
				Integer statId = oldSs.getStatId();
				
				ss.setStatId(statId);
				smsStatDao.update(ss);
			}
			
			// --------------------------------------------------------------
			
			List<SmsDayTypeStat> sdtss =  smsLogDao.getTypeOfNumber(p, today);
			
			for (SmsDayTypeStat sdts : sdtss) {
				sdts.setProductId(p);
				sdts.setDate(new Date());
				 
				 Integer smsDayTypeStatId = smsDayTypeStatDao.getId(p, today, sdts.getType());
				if(smsDayTypeStatId==null){
					smsDayTypeStatDao.save(sdts);
					
				}else{
					sdts.setId(smsDayTypeStatId);
					smsDayTypeStatDao.update(sdts);
				} 
			}
			 
			// --------------------------------------------------------------
		}
 
	}
}
