package com.eduspace.util;

import com.eeduspace.uuims.api.OauthClient;
import com.eeduspace.uuims.api.exception.ApiException;
import com.eeduspace.uuims.api.request.message.SendSMSRequest;
import com.eeduspace.uuims.api.response.message.SendSMSResponse;

/**
 * 认证工具
 * @author pc
 *
 */
public class OauthUtil {
    /**
     * 认证
     * @param openId
     * @param phone
     * @param password
     * @return
     */
	public static SendSMSResponse oauth(String openId,String phone,String password){
		 String oauthUrl = "http://192.168.1.13/oauth/";//身份认证url
		 OauthClient client = new OauthClient(oauthUrl);
         SendSMSRequest request = new SendSMSRequest();
         request.setManagerId(openId);
         request.setPassword(password);
         request.setUserPhone(phone);

         try {
			SendSMSResponse response = client.execute(request);
			return response;
		} catch (ApiException e) {
			 
			e.printStackTrace();
			return null;
		}
	}
}
