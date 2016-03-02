package com.eduspace.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eduspace.entity.Log;
import com.eduspace.service.log.LogService;
import com.eduspace.service.rabbitmq.RabbitMqSend;
import com.eduspace.service.sms.Code;
import com.eduspace.util.OauthUtil;
import com.eduspace.util.RequestUtil;
import com.eduspace.util.ResponseCache;
import com.eduspace.util.UnResponse;
import com.eeduspace.uuims.api.exception.ApiException;
import com.eeduspace.uuims.api.model.MessageModel;
import com.eeduspace.uuims.api.response.message.SendSMSResponse;
import com.eeduspace.uuims.api.util.GsonUtil;
import com.google.gson.Gson;

/**
 * 发送短信
 * 
 * @author hechen
 * 
 */

@Controller
@RequestMapping("/sms")
public class SMScontroller {

	private static Logger logger = Logger.getLogger("SMScontroller.class");
	@Autowired
	private LogService logServic;
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
		// String requestId = ru.getString( "requestId");
		String message = ru.getString("message");
		UnResponse unResponse = new UnResponse();
		// oauth 认证
		SendSMSResponse sendSMSResponse = OauthUtil.oauth(openId, phone, password);
		MessageModel messageModel = GsonUtil.fromObjectJson(new Gson().toJson(sendSMSResponse), "result", "messageModel", MessageModel.class);
		//请求状态码
		String responseCode = sendSMSResponse.getCode();
		unResponse = ResponseCache.getCache().get(responseCode);
		// 认证失败
		if(!sendSMSResponse.getCode().equals("Success")){
            return  unResponse;
        }
		String productName = messageModel.getProductName();
		// 获取短信内容
		String code = Code.sixCode();
		if (sendType.equals("other")) {// 自定义模板
			message = Code.getOtherMessage(message, code, productName);
		} else { // 定制模板
			message = Code.getMessage(sendType, code, productName);
		}

		// 将短信内容发送到消息队列
		RabbitMqSend.send(phone + "---" + message);

		//将日志消息发送到消息队列中
		
		
		// 返回接口的Json
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("code", code);
		 
		unResponse.setResult(map);
		
		Log log = new Log();
		 
		log.setMessage("haha");
		log.setCreatDate(new Date());
		logServic.saveLog(log);
		return unResponse;
	}

}
