package com.eduspace.service.sms;

import java.util.Random;

import com.eduspace.util.PathUtil;
import com.eduspace.util.PropertiesUtil;

/**
 * 验证码
 * 
 * @author pc
 *
 */
public class Code {

	public static String getMessage(String sendType, String code, String productName) {

		String src = PathUtil.getSrc();
		PropertiesUtil pu = new PropertiesUtil(src + "smsCode.properties");
		String message = pu.getString(sendType);

		if (message != null) {
			message = message.replace("${code}", code);
			message = message.replace("${productName}", productName);
			return message;
		}else{
			return null;
		}

	}
	
	
    /**
     * 自定义
     * @param sendType
     * @param message
     * @param code
     * @param productName
     * @return
     */
	public static String getOtherMessage(String message, String code, String productName) {
		if ( message != null) {
			message = message.replace("${code}", code);
			message = message.replace("${productName}", productName);
			return message;
		} else {
			return null;
		}

	}

	/*
	 * 产生随机的六位数
	 * 
	 * @return
	 */
	public static String sixCode() {
		Random rad = new Random();
		String result = rad.nextInt(1000000) + "";
		if (result.length() != 6) {
			return sixCode();
		}
		return result;
	}

}
