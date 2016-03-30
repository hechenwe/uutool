package com.eduspace.service.rabbitmq;
/**
 * 消息隊列 接收后處理的業務
 * @author pc
 *
 */
public interface Do {
	/**
	 * 處理業務
	 * @param message 消息隊列中消息文本
	 */
public void doSomething(String message);
}
