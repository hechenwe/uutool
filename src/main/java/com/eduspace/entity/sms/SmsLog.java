package com.eduspace.entity.sms;


/**
 * 短信日志
 * @author pc
 *
 */
public class SmsLog {
	/** 日志编号 */
	private Integer logId;
	
	private String openId;
	
	/** 短信发送目标电话号码 */
	private String phone;
	
	/***/
	private String requestId;
	
	/** 短信内容 */
	private String message;
	
	/** 产品编号 */
	private String productId;
	
	/** 产品名称 */
	private String productName;
	
	/** 用户编号 */
	private String userId;
	
	/** 请求时间 */
	private String requestDate;
	
	/** 短信发送时间 */
	private String sendDate;
	
	/** 日志生成时间 */
	private String logDate;
	
	/**短信类别*/
	private String sendType;

	/** 短信状态 */
	private String messageState;

	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	 

	public String getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public String getLogDate() {
		return logDate;
	}

	public void setLogDate(String logDate) {
		this.logDate = logDate;
	}

	public String getMessageState() {
		return messageState;
	}

	public void setMessageState(String messageState) {
		this.messageState = messageState;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	
}
