package com.eduspace.controller;

import java.io.IOException;
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
import com.eduspace.entity.jpush.JpushDayTypeStat;
import com.eduspace.entity.jpush.JpushLog;
import com.eduspace.entity.jpush.JpushMonthStat;
import com.eduspace.entity.jpush.JpushStat;
import com.eduspace.service.jpush.JpushLogService;
import com.eduspace.service.rabbitmq.RabbitMqSend;
import com.eduspace.service.sms.OauthUtil;
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
@RequestMapping("/jpush")
public class JpushController {

	public static Logger logger = Logger.getLogger("JpushController.class");
	@Autowired
	public JpushLogService jpushLogService;

	/**
	 * 
	 * @param request
	 * @return
	 * @throws ApiException
	 * @throws IOException
	 */
	@RequestMapping(value = "/push", method = RequestMethod.POST)
	@ResponseBody
	public UnResponse push(HttpServletRequest request) throws ApiException, IOException {
		RequestUtil ru = new RequestUtil(request);

		String openId = ru.getString("openId");
		String password = ru.getString("password");
		String phone = "";
		ru.getString("phone");
		// String requestId = ru.getString("requestId");

		JpushLog log = (JpushLog) ru.getEntity(JpushLog.class);
		log.setRequestDate(String2Date.getString(new Date()));
		String remoteAddr = request.getRemoteAddr();
		// oauth 认证
		OauthResponse oauthResponse = OauthUtil.oauth(openId, password, phone, "jpush", remoteAddr);
		UnResponse unResponse = new UnResponse();
		if (oauthResponse==null) {
			return unResponse;
		}
		// 请求状态码
		String responseCode = oauthResponse.getResponseCode();

		// unResponse.setRequestId(requestId);
		unResponse = ResponseCache.getCache().get(responseCode);
		// 认证失败
		if (!responseCode.equals("Success")) {
			return unResponse;
		}

		log.setUserId(oauthResponse.getUserId());
		log.setProductId(oauthResponse.getProductId());
		log.setProductName(oauthResponse.getProductName());
		RabbitMqSend.send("JPUSH_QUEUE", new JsonUtil<JpushLog>().getJson(log));

		return unResponse;
	}

	@RequestMapping(value = "/getJpushLogIndex", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getJpushLogIndex(HttpServletRequest request) {
		RequestUtil ru = new RequestUtil(request);
		String productId = ru.getString("productId");
		JpushStat es = new JpushStat();
		Map<String, Object> jpushStatMap;
		if (productId == null || "".equals(productId)) {
			jpushStatMap = jpushLogService.jpushStatDao.getTotal();
		} else {

			es.setProductId(productId);
			es = jpushLogService.jpushStatDao.get(es);
			jpushStatMap = Ent2Map.getMap(es, "NOT_NEED", "statId", "productId");
		}


		Map<String, Object> map = new HashMap<>();
		map.put("main", jpushStatMap);
		map.putAll(jpushLogService.getDetail(productId, "today"));

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
		map = jpushLogService.getDetail(productId, type);
		return map;
	}
}
