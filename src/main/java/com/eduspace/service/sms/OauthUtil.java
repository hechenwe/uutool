package com.eduspace.service.sms;

 
import com.eduspace.util.OauthResponse;
import com.eduspace.util.PathUtil;
import com.eduspace.util.PropertiesUtil;
import com.eeduspace.uuims.api.OauthClient;
 
import com.eeduspace.uuims.api.model.MessageModel;
import com.eeduspace.uuims.api.request.message.SendSMSRequest;
 
import com.eeduspace.uuims.api.response.message.SendSMSResponse;
import com.eeduspace.uuims.api.util.GsonUtil;
import com.google.gson.Gson;


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
	public static OauthResponse oauth(String openId,String password,String phone,String sendType,String remoteAddress){
		 PropertiesUtil pu = new PropertiesUtil(PathUtil.getClassPath()+"oauth.properties");
         String oauthUrl = pu.getString("oauthUrl"); 
		// String oauthUrl = "http://192.168.1.13/oauth/";//身份认证url
		 OauthClient client = new OauthClient(oauthUrl);
         SendSMSRequest request = new SendSMSRequest();
         request.setManagerId(openId);
         request.setPassword(password);
         request.setUserPhone(phone);
         request.setSendType(sendType);
         request.setRemoteAddress(remoteAddress);

         try {
			SendSMSResponse sendSMSResponse = client.execute(request);
			MessageModel messageModel = GsonUtil.fromObjectJson(new Gson().toJson(sendSMSResponse), "result", "messageModel", MessageModel.class);
			//请求状态码
			String responseCode = sendSMSResponse.getCode();
			OauthResponse or = new OauthResponse();
			or.setResponseCode(responseCode);
			 
			// 认证失败
			if(responseCode.equals("Success")){
				String productName = messageModel.getProductName();
				String productId = messageModel.getProductId();
				String userId = messageModel.getUserId();
	            or.setProductId(productId);
	            or.setProductName(productName);
	            or.setUserId(userId);
	        }
 		
			return or;
		} catch (Exception e) {
			 
			//e.printStackTrace();
			return null;
		}
	}
}
