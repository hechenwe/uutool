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
import com.eduspace.dao.sms.SmsLogDao;
import com.eduspace.entity.sms.SmsDayTypeStat;
import com.eduspace.entity.sms.SmsLog;
import com.eduspace.entity.sms.SmsMonthStat;
import com.eduspace.entity.sms.SmsStat;
import com.eduspace.entity.sms.SmsTemplate;
import com.eduspace.service.rabbitmq.RabbitMqSend;
import com.eduspace.service.sms.Code;
import com.eduspace.service.sms.OauthUtil;
import com.eduspace.service.sms.SmsService;
import com.eduspace.util.Ent2Map;
import com.eduspace.util.JsonUtil;
import com.eduspace.util.OauthResponse;
import com.eduspace.util.RequestUtil;
import com.eduspace.util.ResponseCache;
import com.eduspace.util.String2Date;
import com.eduspace.util.UnResponse;
import com.eeduspace.uuims.api.exception.ApiException;
import com.sooncode.jdbc.Jdbc;
import com.sooncode.jdbc.util.Pager;

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
        String remoteAddr = request.getRemoteAddr();
		// oauth 认证
		OauthResponse oauthResponse = OauthUtil.oauth(openId,password ,phone,sendType,remoteAddr);

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
		//if (sendType.equals("other")) {// 自定义模板
		//	message = Code.getOtherMessage(message, code, oauthResponse.getProductName());
		//} else { // 定制模板
			message = Code.getMessage(sendType, code, oauthResponse.getProductName());
		//}

		SmsLog messageLog = new SmsLog();
		messageLog.setOpenId(openId);
		messageLog.setSendType(sendType);
		messageLog.setRequestDate(new Date());
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
	 * 获取
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
		Map<String,Object> smsStatMap;
		if(productId==null || productId.trim().equals("")){
			smsStatMap =smsServic.smsStatDao.getTotal();
		}else{
			SmsStat ss = new SmsStat();
			ss.setProductId(productId);
			ss = smsServic.smsStatDao.get(ss);
			if(ss!=null){
				smsStatMap = Ent2Map.getMap(ss,"NOT_NEED", "productId","statId");
			}else{
				smsStatMap = new HashMap<>();
			}
		}
		 
		Map<String, Object> map = new HashMap<>();
		map.put("main", smsStatMap);
		map.putAll(smsServic.getDetail(productId, "today"));
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
	
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getAllSmsTemplate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getAllSmsTemplate(HttpServletRequest request){
		RequestUtil ru = new RequestUtil(request);
		Long pageNum = ru.getLong("pageNum");
		Long pageSize = ru.getLong("pageSize");
		SmsTemplate smsT = (SmsTemplate) ru.getEntity(SmsTemplate.class) ;
		Map <String,Object> map = new HashMap<>();
		Pager<SmsTemplate> pager = smsServic.smsTemplateDao.getPager(pageNum, pageSize, smsT);
		map.put("pager",pager);
		return map;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateSmsTemplate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateSmsTemplate(HttpServletRequest request){
		RequestUtil ru = new RequestUtil(request);
		SmsTemplate smsT = (SmsTemplate) ru.getEntity(SmsTemplate.class) ;
		Map <String,Object> map = new HashMap<>();
		if(smsT.getId()== null){
			map.put("update","failed" );
			return map;
		}
		Long n = smsServic.smsTemplateDao.update(smsT);
		if (n == 1L){
			map.put("update","success" );
		}else{
			map.put("update","failed" );
		}
		return map;
	}
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addSmsTemplate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addSmsTemplate(HttpServletRequest request){
		RequestUtil ru = new RequestUtil(request);
		SmsTemplate smsT = (SmsTemplate) ru.getEntity(SmsTemplate.class) ;//smsKey 和 smsValue 
		Map <String,Object> map = new HashMap<>();
		Long n = smsServic.smsTemplateDao.save(smsT);
		if (n > 0){
			map.put("add","success" );
		}else{
			map.put("add","failed" );
		}
		return map;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deleteSmsTemplate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteSmsTemplate(HttpServletRequest request){
		RequestUtil ru = new RequestUtil(request);
		SmsTemplate smsT = (SmsTemplate) ru.getEntity(SmsTemplate.class) ;//id 
		Map <String,Object> map = new HashMap<>();
		int n = smsServic.smsTemplateDao.delete(smsT);
		if (n == 1){
			map.put("delete","success" );
		}else{
			map.put("delete","failed" );
		}
		return map;
	}
	
}
