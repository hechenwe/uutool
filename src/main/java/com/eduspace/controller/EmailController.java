package com.eduspace.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eduspace.dao.CommonDao;
import com.eduspace.dao.email.interfac.EmailDayStatDaoI;
import com.eduspace.dao.email.interfac.EmailDayTypeStatDaoI;
import com.eduspace.dao.email.interfac.EmailLogDaoI;
import com.eduspace.dao.email.interfac.EmailMonthStatDaoI;
import com.eduspace.dao.email.interfac.EmailStatDaoI;
import com.eduspace.entity.email.EmailDayTypeStat;
import com.eduspace.entity.email.EmailLog;
import com.eduspace.entity.email.EmailMonthStat;
import com.eduspace.entity.email.EmailStat;
import com.eduspace.service.email.EmailLogService;
import com.eduspace.service.rabbitmq.RabbitMqSend;
import com.eduspace.service.sms.OauthUtil;
import com.eduspace.util.ClassCache;
import com.eduspace.util.Ent2Map;
import com.eduspace.util.JsonUtil;
import com.eduspace.util.OauthResponse;
import com.eduspace.util.RequestUtil;
import com.eduspace.util.ResponseCache;
import com.eduspace.util.String2Date;
import com.eduspace.util.UnResponse;
import com.eeduspace.uuims.api.exception.ApiException;
import com.sooncode.jdbc.Jdbc;

/**
 * 发送短信
 * 
 * @author hechen
 * 
 */

@Controller
@RequestMapping("/email")
public class EmailController {

	public static Logger logger = Logger.getLogger("SMScontroller.class");
	@Autowired
	private EmailLogService emailLogService;

	/**
	 * 
	 * @param request
	 * @return
	 * @throws ApiException
	 * @throws IOException
	 */
	@RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
	@ResponseBody
	public UnResponse sendEmail(HttpServletRequest request) throws ApiException, IOException {
		RequestUtil ru = new RequestUtil(request);

		String openId = ru.getString("openId");
		String password = ru.getString("password");
		String phone = ru.getString("phone");
		String requestId = ru.getString("requestId");
		String email = ru.getString("email");
		String subject = ru.getString("subject");
		String body = ru.getString("body");
		String sendType = ru.getString("sendType");
		UnResponse unResponse = new UnResponse();

		String remoteAddr = request.getRemoteAddr();
		// oauth 认证
		OauthResponse oauthResponse = OauthUtil.oauth(openId, password, phone, sendType, remoteAddr);
		if (oauthResponse==null) {
			return unResponse;
		}
		// 请求状态码
		String responseCode = oauthResponse.getResponseCode();

		unResponse.setRequestId(requestId);
		unResponse = ResponseCache.getCache().get(responseCode);
		// 认证失败
		if (!responseCode.equals("Success")) {
			return unResponse;
		}

		EmailLog emailLog = new EmailLog();
		emailLog.setOpenId(openId);
		emailLog.setRequestDate(String2Date.getString(new Date()));
		emailLog.setBody(body);
		emailLog.setEmail(email);
		emailLog.setSubject(subject);
		emailLog.setPhone(phone);
		emailLog.setProductId(oauthResponse.getProductId());
		emailLog.setProductName(oauthResponse.getProductName());
		emailLog.setRequestId(requestId);
		emailLog.setUserId(oauthResponse.getUserId());
		emailLog.setSendType(sendType);
		RabbitMqSend.send("EMAIL_QUEUE", new JsonUtil<EmailLog>().getJson(emailLog));

		return unResponse;
	}

	/**
	 * 
	 * 
	 * @param request
	 * @param session
	 * @return
	 * @throws ApiException
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/getEmailLogIndex", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getEmailLogIndex(HttpServletRequest request) {
		RequestUtil ru = new RequestUtil(request);
		String productId = ru.getString("productId");
		EmailStat es = new EmailStat();
		Map<String, Object> emailStatMap;
		if (productId != null && !productId.equals("")) {
			es.setProductId(productId);
			es = emailLogService.emailStatDao.get(es);
			emailStatMap = Ent2Map.getMap(es, "NOT_NEED", "statId", "productId");
		} else {
			emailStatMap = emailLogService.emailStatDao.getTotal();
		}

		 
		Map<String, Object> map = new HashMap<>();
		map.put("main", emailStatMap);
		map.putAll(emailLogService.getDetail(productId, "today"));
		 

		return map;
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getDetail(HttpServletRequest request) {
		RequestUtil ru = new RequestUtil(request);
		String productId = ru.getString("productId");
		String type = ru.getString("type");
		Map<String, Object> map = new HashMap<>();
		map = emailLogService.getDetail(productId, type);
		return map;
	}
}
