package com.eduspace.service.rabbitmq;

import org.apache.log4j.Logger;

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
	private  String queueName ;//= "SMS_QUEUE";
	private Do doo;
	private static Logger logger = Logger.getLogger("Recieve.class");
    
    public RabbitMqRecieve(String queueName,Do doo){
    	this.queueName=queueName;
    	this.doo = doo;
    }
	
	public void run() {
		
		 PropertiesUtil pu = new PropertiesUtil(PathUtil.getSrc()+"rabbitMQ.properties");
		 String host = pu.getString("host");
		 Integer port = pu.getInt("port");
		 String username = pu.getString("username");
		 String password = pu.getString("password");
		 Integer sleepTime = pu.getInt("sleep_time");
		 
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
			channel.queueDeclare(queueName, false, false, false, null);
			// 创建队列消费者
			consumer = new QueueingConsumer(channel);
			// 指定消费队列
			channel.basicConsume(queueName, true, consumer);
			logger.info("【消息队列】开始监听...");
			while (!this.isInterrupted()) {// 线程未中断执行循环
					Thread.sleep(sleepTime); //  
					Delivery delivery = consumer.nextDelivery();
					String mqMessage = new String(delivery.getBody());
					
					doo.doSomething(mqMessage);
					
					
					/*MessageLog log = new MessageLog();
					log = new  JsonUtil<MessageLog>().getObject(mqMessage, MessageLog.class);
					log.setSendDate(String2Date.getString(new Date()) );
					try{
					SMSGateway.send(log.getPhone(), log.getMessage());
					log.setMessageState("1");
					}catch(Exception e) {
						log.setMessageState("0");
						e.printStackTrace();
					}finally {
						log.setLogDate(String2Date.getString(new Date()));
						new LogDao().save(log);
						 
					}*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
