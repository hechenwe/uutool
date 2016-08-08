package com.eduspace.service.jpush;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.web.method.support.HandlerMethodReturnValueHandlerComposite;

import com.eduspace.dao.jpush.JpushLogDao;
import com.eduspace.entity.email.EmailLog;
import com.eduspace.entity.jpush.JpushLog;
import com.eduspace.service.rabbitmq.Do;
import com.eduspace.util.FileUtil;
import com.eduspace.util.JsonUtil;
import com.eduspace.util.PathUtil;
import com.eduspace.util.String2Date;

/**
 * 郵件 業務 和日誌記錄
 * @author pc
 *
 */
public class JpushDo implements Do {
	private static Logger logger = Logger.getLogger("EmailDo.class");
	@Override
	public void doSomething(String message) {
		logger.info("【推送消息文本】："+message);
		JpushLog log = new JpushLog();
		log = new  JsonUtil<JpushLog>().getObject(message, JpushLog.class);
		log.setSendDate(new Date());
		log.setLogDate(new Date() );
		try {
			 
			JPushService.sendpush(log.getAppKey (), log.getSercet(), log.getType(), log.getObject(), log.getTitle(),log.getContent(),log.getExtraContent()); 
			log.setMessageState("1");
			
		} catch (Exception e) {
			log.setMessageState("0"); 
			e.printStackTrace();
		}finally{
			new JpushLogDao().save(log);
		}
	}

}
