package com.eduspace.service.jpush;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.eduspace.service.rabbitmq.RabbitMqRecieve;

/**
 * 郵件消息队列 监听器
 * 
 * @author pc
 *
 */
public class Jpushlistener implements ServletContextListener {
	/**
	 * 消息队列线程
	 */
	public RabbitMqRecieve recieve;
 

	public void contextDestroyed(ServletContextEvent arg0) {
		
		if (this.recieve != null && recieve.isInterrupted()) {  
			 recieve.interrupt();  
	        }  

	}


	public void contextInitialized(ServletContextEvent arg0) {
		if (recieve == null) {
			recieve = new RabbitMqRecieve ("JPUSH_QUEUE",new JpushDo());
			recieve.start(); // servlet 上下文初始化时启动 socket
		}
		
	}

}
