package com.eduspace.service.rabbitmq;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

/**
 * 消息队列 监听器
 * 
 * @author pc
 *
 */
public class RabbitMQlistener implements ServletContextListener {
	/**
	 * 消息队列线程
	 */
	private RabbitMqRecieve recieve;

	

	 
	public void contextInitialized(ServletContextEvent event) {
		if (recieve == null) {
			recieve = new RabbitMqRecieve();
			recieve.start(); // servlet 上下文初始化时启动 socket
		}

	}
	
	 
	public void contextDestroyed(ServletContextEvent event) {
		 if (recieve != null && recieve.isInterrupted()) {  
			 recieve.interrupt();  
	        }  

	}

}
