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

 
import com.eduspace.entity.sms.SmsDayTypeStat;
import com.eduspace.entity.sms.SmsLog;
import com.eduspace.entity.sms.SmsStat;
import com.eduspace.service.rabbitmq.RabbitMqSend;
import com.eduspace.service.sms.Code;
import com.eduspace.service.sms.SmsService;
import com.eduspace.util.JsonUtil;
import com.eduspace.util.OauthResponse;
import com.eduspace.util.OauthUtil;
import com.eduspace.util.RequestUtil;
import com.eduspace.util.ResponseCache;
import com.eduspace.util.String2Date;
import com.eduspace.util.UnResponse;
import com.eeduspace.uuims.api.exception.ApiException;

/**
 * 发送短信
 * 
 * @author hechen
 * 
 */

@Controller
@RequestMapping("/sms")
public class SMScontroller {

	
	public static Logger logger = Logger.getLogger("SMScontroller.class");
    @Autowired
	private SmsService smsServic ;
	 
	/**
	 * 获取验证码
	 * 
	 * @param request
	 * @param session
	 * @return
	 * @throws ApiException
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/verificationCode", method = RequestMethod.POST)
	@ResponseBody
	public UnResponse verificationCode(HttpServletRequest request) throws ApiException, IOException {
		RequestUtil ru = new RequestUtil(request);
		String openId = ru.getString("openId");
		String password = ru.getString("password");
		String phone = ru.getString("phone");
		String sendType = ru.getString("sendType");
		String requestId = ru.getString("requestId");
		String message = ru.getString("message");

		// oauth 认证
		OauthResponse oauthResponse = OauthUtil.oauth(openId, phone, password);

		// 请求状态码
		String responseCode = oauthResponse.getResponseCode();

		UnResponse unResponse = new UnResponse();
		unResponse = ResponseCache.getCache().get(responseCode);
		// 认证失败
		if (!responseCode.equals("Success")) {
			return unResponse;
		}

		// 获取短信内容
		String code = Code.sixCode();// 短信验证码
		if (sendType.equals("other")) {// 自定义模板
			message = Code.getOtherMessage(message, code, oauthResponse.getProductName());
		} else { // 定制模板
			message = Code.getMessage(sendType, code, oauthResponse.getProductName());
		}

		SmsLog messageLog = new SmsLog();
		messageLog.setOpenId(openId);
		messageLog.setSendType(sendType);
		messageLog.setRequestDate(String2Date.getString(new Date()));
		messageLog.setMessage(message);
		messageLog.setPhone(phone);
		messageLog.setProductId(oauthResponse.getProductId());
		messageLog.setProductName(oauthResponse.getProductName());
		messageLog.setRequestId(requestId);
		messageLog.setUserId(oauthResponse.getUserId());
		// 将短信内容发送到消息队列
		RabbitMqSend.send("SMS_QUEUE", new JsonUtil<SmsLog>().getJson(messageLog));
		// 返回接口的Json
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		unResponse.setRequestId(requestId);
		unResponse.setResult(map);
		return unResponse;
	}

	/**
	 * 获取验证码
	 * 
	 * @param request
	 * @param session
	 * @return
	 * @throws ApiException
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/getSmsLogIndex", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getSmsLogIndex(HttpServletRequest request) {
		RequestUtil ru = new RequestUtil(request);
		String productId = ru.getString("productId");
		SmsStat ss = new SmsStat();
		ss.setProductId(productId);
		ss = smsServic.smsStatDao.get(ss);

		SmsDayTypeStat sdts = new SmsDayTypeStat();

		sdts.setProductId(productId);
		sdts.setDate(new Date());

		String today = String2Date.getString(new Date(), "yyyy-MM-dd");

		List<SmsDayTypeStat> sdtss = smsServic.smsDayTypeStatDao.startGets(sdts).like("date", today).endGets();
        
		List<Map<String, Object>> list = smsServic.smsLogDao.getScopeStat(productId, "today");
		
		Map<String, Object> map = new HashMap<>();
		map.put("smsStat", ss);
		map.put("smsDayTypeStats", sdtss);
		map.put("hourData", list);

		return map;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getDetail(HttpServletRequest request){
		RequestUtil ru = new RequestUtil(request);
		String productId = ru.getString("productId");
		String type = ru.getString("type");
		Map <String,Object> map = new HashMap<>();
		map = smsServic.getDetail(productId, type) ;
		return map;
	}
}
