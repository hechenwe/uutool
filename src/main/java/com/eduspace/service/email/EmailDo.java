package com.eduspace.service.email;

import java.util.Date;
import org.apache.log4j.Logger;
import com.eduspace.dao.email.EmailLogDao;
import com.eduspace.entity.email.EmailLog;
import com.eduspace.service.rabbitmq.Do;
import com.eduspace.util.JsonUtil;
import com.eduspace.util.String2Date;

/**
 * 郵件 業務 和日誌記錄
 * 
 * @author pc
 *
 */
public class EmailDo implements Do {
	private static Logger logger = Logger.getLogger("EmailDo.class");

	@Override
	public void doSomething(String message) {
		logger.info("【郵件消息文本】：" + message);
		EmailLog log = new EmailLog();
		log = new JsonUtil<EmailLog>().getObject(message, EmailLog.class);
		log.setSendDate(new Date());
		log.setLogDate(new Date());
		try {

			String body = log.getBody();// new
										// FileUtil().readFile(PathUtil.getWebRoot()+"email.html");

			body = body.replace("${activeUrl}", log.getBody());

			EmailService.sendEmail(log.getEmail(), log.getSubject(), body);
			log.setMessageState("1");

		} catch (Exception e) {
			log.setMessageState("0");
			e.printStackTrace();
		} finally {
			new EmailLogDao().save(log);
		}
	}

}
