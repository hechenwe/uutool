package com.eduspace.dao.email.interfac;

import java.util.List;

import com.eduspace.entity.email.EmailDayTypeStat;
import com.eduspace.entity.email.EmailLog;
import com.sooncode.jdbc.DaoI;

public interface EmailLogDaoI extends DaoI<EmailLog> {
    /**
     * 获取 产品的编号 集
     * @param requestDate 短信发送日期
     * @param messageState 短信状态
     * @return 产品的编号 集
     */ 
	public List<String> getProductIds(String requestDate,String messageState);
	/**
	 * 
	 * @param productId 产品编号
	 * @param requestDate 请求时间
	 * @param messageState 短信状态
	 * @return 短信数量
	 */
	public Long getNumber(String productId, String requestDate, String messageState) ; 

	/**
	 * 获取当日 短信类型 和短信类型对应的短信数量
	 * @param productId 产品编号
	 * @param requestDate 请求时间
	 * @return 把 短信类别  和短信数量 封装在 SmsDayTypeStat 内
	 */
	public List<EmailDayTypeStat> getTypeOfNumber(String productId, String requestDate);
		
	/**
	 * 获取某日 短信日志条数 
	 */
	public Long getSmsSize(String dateString) ;
	
	
	/**
	 * 删除某日 短信日志条数 
	 */
	public Long deleteSms(String dateString) ;
}
