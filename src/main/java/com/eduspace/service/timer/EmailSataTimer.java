package com.eduspace.service.timer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.eduspace.dao.email.interfac.EmailDayStatDaoI;
import com.eduspace.dao.email.interfac.EmailDayTypeStatDaoI;
import com.eduspace.dao.email.interfac.EmailLogDaoI;
import com.eduspace.dao.email.interfac.EmailMonthStatDaoI;
import com.eduspace.dao.email.interfac.EmailStatDaoI;
import com.eduspace.entity.email.EmailDayStat;
import com.eduspace.entity.email.EmailDayTypeStat;
import com.eduspace.entity.email.EmailMonthStat;
import com.eduspace.entity.email.EmailStat;
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
public class EmailSataTimer {

	private static Logger logger = Logger.getLogger("SataTimer.class");

	private EmailDayStatDaoI emailDayStatDao = (EmailDayStatDaoI) ClassCache.getImplementObject(EmailDayStatDaoI.class);

	private EmailLogDaoI emailLogDao = (EmailLogDaoI) ClassCache.getImplementObject(EmailLogDaoI.class);

	private EmailMonthStatDaoI emailMonthStatDao = (EmailMonthStatDaoI) ClassCache.getImplementObject(EmailMonthStatDaoI.class);

	private EmailStatDaoI emailStatDao = (EmailStatDaoI) ClassCache.getImplementObject(EmailStatDaoI.class);
	private EmailDayTypeStatDaoI emailDayTypeStatDao= (EmailDayTypeStatDaoI) ClassCache.getImplementObject(EmailDayTypeStatDaoI.class);

	@Scheduled(cron = "0/5 * *  * * ?")
	public void sata() {
		logger.info("【邮件--定时器】：" + new Date());
		String format = "yyyy-MM-dd";

		String today = String2Date.getString(new Date(), format);
	 

		List<String> productIds = emailLogDao.getProductIds(today, "1");

		for (String p : productIds) {
			// --------------------------------------------------------------
			Long sucNumber = emailLogDao.getNumber(p, today, "1"); // 今天成功数
			Long falNumber = emailLogDao.getNumber(p, today, "0"); // 今天失败数

			Integer dayId = emailDayStatDao.get(p, today);

			EmailDayStat sds = new EmailDayStat();
			sds.setSucNumber(Integer.valueOf(sucNumber + ""));
			sds.setFalNumber(Integer.valueOf(falNumber + ""));
			sds.setDate(new Date());
			sds.setProductId(p);
			if (dayId != null) {
				sds.setId(dayId);
				emailDayStatDao.update(sds);
			} else {
				emailDayStatDao.save(sds);

			}
			// --------------------------------------------------------------
			CalendarUtil cu = new CalendarUtil(new Date());
			String startDay = cu.getFirstDayOfMonth(format);
			String endDay = cu.getLastDayOfMonth(format);
			BigDecimal sucNum = emailDayStatDao.getNumber(p, startDay, endDay, "1");
			BigDecimal falNum = emailDayStatDao.getNumber(p, startDay, endDay, "0");

			EmailMonthStat email = new EmailMonthStat();
			email.setProductId(p);
			email.setSucNumber(sucNum.intValue());
			email.setFalNumber(falNum.intValue());
			String thisMonth = String2Date.getString(new Date(), "yyyy-MM");
			email.setMonth(thisMonth);

			Integer id = emailMonthStatDao.getId(p, thisMonth);
			if (id == null) {
				emailMonthStatDao.save(email);

			} else {
				email.setId(id);
				emailMonthStatDao.update(email);
			}
			// --------------------------------------------------------------
			// 最近3天

			String today3 = cu.getChangeDay(-3, format);
			int threeFal = emailDayStatDao.getNumber(p, today3, today, "0").intValue();
			int threeSuc = emailDayStatDao.getNumber(p, today3, today, "1").intValue();
			// 最近一周
			String today7 = cu.getChangeDay(-7, format);
			int sevenFal = emailDayStatDao.getNumber(p, today7, today, "0").intValue();
			int sevenSuc = emailDayStatDao.getNumber(p, today7, today, "1").intValue();

			// 最近一个月
			String today30 = cu.getChangeDay(-30, format);
			int monthFal = emailDayStatDao.getNumber(p, today30, today, "0").intValue();
			int monthSuc = emailDayStatDao.getNumber(p, today30, today, "1").intValue();
			// 最近半年
			String todayHalfYear = cu.getChangeDay(-183, format);
			int halfYearFal = emailDayStatDao.getNumber(p, todayHalfYear, today, "0").intValue();
			int halfYearSuc = emailDayStatDao.getNumber(p, todayHalfYear, today, "1").intValue();

			// 最近一年
			String todayYear = cu.getChangeDay(-365, format);
			int yearFal = emailDayStatDao.getNumber(p, todayYear, today, "0").intValue();
			int yearSuc = emailDayStatDao.getNumber(p, todayYear, today, "1").intValue();

			EmailStat ss = new EmailStat();
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
			EmailStat oldSs =  new EmailStat(); 
			oldSs.setProductId(p)	;
			oldSs=emailStatDao.get(oldSs);
			if(oldSs ==null){
					emailStatDao.save(ss);
				
			}else{
				Integer statId = oldSs.getStatId();
				
				ss.setStatId(statId);
				emailStatDao.update(ss);
			}
			
			// --------------------------------------------------------------
			
			List<EmailDayTypeStat> sdtss =  emailLogDao.getTypeOfNumber(p, today);
			
			for (EmailDayTypeStat sdts : sdtss) {
				sdts.setProductId(p);
				sdts.setDate(new Date());
				
				Integer emailDayTypeStatId = emailDayTypeStatDao.getId(p, today, sdts.getType());
				if(emailDayTypeStatId==null){
					emailDayTypeStatDao.save(sdts);
					
				}else{
					sdts.setId(emailDayTypeStatId);
					emailDayTypeStatDao.update(sdts);
				}
				
				
			}
			
			
			 
			// --------------------------------------------------------------
		}

 
		
	 
		
		
		
	}
}
