package com.eduspace.service.sms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.log4j.Logger;

import com.eduspace.util.PathUtil;
import com.eduspace.util.PropertiesUtil;
 
/**
 * 发送短信验证码
 * @author hechen
 *
 */
public class SMSGateway {
	private static Logger logger = Logger.getLogger("SMSGateway.class");
    /**
     * 发送短信
     * @param telephone 手机号码
     * @param massage 短信内容
     * @return
     * @throws Exception 
     */
	public  static void send(String telephone, String massage) throws Exception {
		String src = PathUtil.getClassPath();
		PropertiesUtil pu = new PropertiesUtil(src+"smsCode.properties");
        String urlString = pu.getString("url");
        String apikey = pu.getString("apikey");
        String username = pu.getString("username");
        String password = pu.getString("password");
		StringBuffer sb = new StringBuffer(urlString); // 创建StringBuffer对象用来操作字符串
		//try {
			sb.append("apikey="+apikey); // APIKEY
			sb.append("&username="+username); // 用户名
			sb.append("&password="+password); // 向StringBuffer追加密码
			sb.append("&mobile=" + telephone); // 向StringBuffer追加手机号码
			sb.append("&content=" + URLEncoder.encode(massage, "GBK"));// 向StringBuffer追加消息内容转URL标准码
			//System.out.println("【短信验证码】"+sb.toString()); // 输出结果
			URL url = new URL(sb.toString());
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 打开url连接
			connection.setRequestMethod("POST");// 设置url请求方式 ‘get’ 或者 ‘post’
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream())); // 发送
			logger.info("【短信验证码】"+in.readLine()); // 输出结果

		//} catch (Exception e) {
		//	e.printStackTrace();
		//}
		 
	}
}