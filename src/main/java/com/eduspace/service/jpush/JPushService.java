package com.eduspace.service.jpush;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.PushPayload.Builder;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JPushService {

	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Contenttype", "task");
		map.put("type", "QUESTIONUPLOAD");
		map.put("Title", "上传自拍题");
		map.put("id", "38");
		map.put("testtype", "");

		sendpush("c175dffbff40387521efdc13", "341a6c5117eaae87030c0a2c","ALIAS","a2235c798bae43909655dc8dc9863099", "好学生", "下单成功！","这是个测试");

		// JPushClient jpush = new JPushClient("163e261df7d83816273eacc6 ",
		// "ec86eb0e8ada5b85082cd163");
		// //jpush.setEnableSSL(true);
		// int sendNo = 1;
		// String imei = "";
		// String msgTitle = "";
		// String msgContent = "";
		// jpush.sendNotificationWith
		// MessageResult msgResult = jpush.sendCustomMessageWithImei(sendNo,
		// imei, msgTitle, msgContent);
		// if (null != msgResult) {
		// if (msgResult.getErrcode() == ErrorCodeEnum.NOERROR.value()) {
		// System.out.println("发送成功， sendNo=" + msgResult.getSendno());
		// } else {
		// System.out.println("发送失败， 错误代码=" + msgResult.getErrcode() + ", 错误消息="
		// + msgResult.getErrmsg());
		// }
		// } else {
		// System.out.println("无法获取数据");
		// }
	}

	 /**
	  * 推送
	  * @param appKey
	  * @param sercet
	  * @param type  "ALIAS" 或者 "TAG"
	  * @param object 推送对象
	  * @param title 标题
	  * @param content 正文
	  */
	public static void sendpush(String appKey, String sercet, String type, String object, String title, String content,String extras) {

		Map<String, String> map = new HashMap<>();
		JPushClient jpush = new JPushClient(sercet, appKey, 1);
		Builder builder = PushPayload.newBuilder();
		map.put("from", "JPush");
		map.put("time", "" + new Date().getTime());
		map.put("content", extras);
		Platform platform = Platform.android_ios();
		AndroidNotification androidNotification = AndroidNotification.newBuilder().setTitle(title).addExtras(map).build();
		IosNotification iosNotification = IosNotification.newBuilder().incrBadge(1).setContentAvailable(true).addExtras(map).build();
		Message message = Message.newBuilder().setMsgContent(content).addExtras(map).build();
		Options options = Options.newBuilder().setTimeToLive(86400 * 10).setApnsProduction(false).build();
		Notification notification = Notification.newBuilder().setAlert(content).addPlatformNotification(androidNotification).addPlatformNotification(iosNotification).build();
		builder.setPlatform(platform);
		builder.setMessage(message);
		builder.setNotification(notification);
		builder.setOptions(options);
		if (type.equals("ALIAS")) {
			builder.setAudience(Audience.alias(object));

		} else if (type.equals("TAG")) {
			builder.setAudience(Audience.tag(object));
		}else if(type.equals("ALL")){
            builder.setAudience(Audience.all());
        }
		 
		try {
			jpush.sendPush(builder.build());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}