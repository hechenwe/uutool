package com.eduspace.service.rabbitmq;
import org.apache.log4j.Logger;

import com.eduspace.service.sms.SMSGateway;
import com.eduspace.util.PathUtil;
import com.eduspace.util.PropertiesUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

/**
 * 消息队列线程
 * @author pc
 *
 */
public class RabbitMqRecieve extends Thread {
	// 队列名称
	private final static String QUEUE_NAME = "SMS_QUEUE";
	private static Logger logger = Logger.getLogger("Recieve.class");

	public void run() {
		
		 PropertiesUtil pu = new PropertiesUtil(PathUtil.getSrc()+"rabbitMQ.properties");
		 String host = pu.getString("host");
		 Integer port = pu.getInt("port");
		 String username = pu.getString("username");
		 String password = pu.getString("password");
		 
		// 打开连接和创建频道，与发送端一样
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		
	    //factory.setHost(host);factory.setVirtualHost("test_vhost");factory.setPort(port);factory.setUsername(username);factory.setPassword(password);
		Connection connection;
		QueueingConsumer consumer = null;
		try {
			connection = factory.newConnection();
			Channel channel = connection.createChannel();
			// 声明队列，主要为了防止消息接收者先运行此程序，队列还不存在时创建队列。
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			// 创建队列消费者
			consumer = new QueueingConsumer(channel);
			// 指定消费队列
			channel.basicConsume(QUEUE_NAME, true, consumer);
			logger.info("[消息队列]开始监听...");
			while (!this.isInterrupted()) {// 线程未中断执行循环
					Thread.sleep(3000); // 每隔3000ms执行一次
					Delivery delivery = consumer.nextDelivery();
					String message = new String(delivery.getBody());
					logger.info("[消息队列]捕获的消息:" + message);
					String [] strings = message.split("---");
					SMSGateway.send(strings[0], strings[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
