package com.eduspace.service.rabbitmq;


import org.apache.log4j.Logger;

import com.eduspace.util.PathUtil;
import com.eduspace.util.PropertiesUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMqSend {
	 //队列名称  
    //private final static String QUEUE_NAME = "SMS_QUEUE";  
    private static Logger logger = Logger.getLogger("RabbitMQ.class");
    /**
     * 将消息发送到消息队列中
     * @param queueName 队列名称  
     * @param message 消息
     */
    public static void send(String queueName,String message)  
    {  
        /** 
         * 创建连接连接到MabbitMQ 
         */  
        ConnectionFactory factory = new ConnectionFactory();  
        //设置MabbitMQ所在主机ip或者主机名  
         PropertiesUtil pu = new PropertiesUtil(PathUtil.getSrc()+"rabbitMQ.properties");
         String host = pu.getString("host");
		 Integer port = pu.getInt("port");
		 String username = pu.getString("username");
		 String password = pu.getString("password");
		 //factory.setHost("localhost");
         factory.setHost(host);  
         factory.setPort(port);
         factory.setUsername(username);
         factory.setPassword(password);
         factory.setVirtualHost("/tool");
        
        //创建一个连接  
        Connection connection;
		try {
			connection = factory.newConnection();
			//创建一个频道  
			Channel channel = connection.createChannel();  
			//指定一个队列  
			channel.queueDeclare(queueName, false, false, false, null);  
			//发送的消息  
			//往队列中发出一条消息  
			channel.basicPublish("", queueName, null, message.getBytes());  
			logger.info("【消息队列】发送的消息:"+message);
			//关闭频道和连接  
			channel.close();  
			connection.close();  
		} catch (Exception e) {
			e.printStackTrace();
		}  
		
     } 
    
   

}
