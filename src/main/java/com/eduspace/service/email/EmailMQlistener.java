package com.eduspace.service.email;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.eduspace.service.rabbitmq.RabbitMqRecieve;

/**
 * 郵件消息队列 监听器
 * 
 * @author pc
 *
 */
public class EmailMQlistener implements ServletContextListener {
	/**
	 * 消息队列线程
	 */
	public RabbitMqRecieve recieve;
 

	public void contextDestroyed(ServletContextEvent sce) {
		
		if (this.recieve != null && recieve.isInterrupted()) {  
			 recieve.interrupt();  
	        }  

	}


	public void contextInitialized(ServletContextEvent sce) {
		if (recieve == null) {
			recieve = new RabbitMqRecieve ("EMAIL_QUEUE",new EmailDo());
			recieve.start(); // servlet 上下文初始化时启动 socket
		}
		
	}

}
