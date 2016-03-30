package com.eduspace.entity.email;

/**
 * 郵件日志
 * 
 * @author pc
 *
 */
public class EmailLog {
	/** 日志编号 */
	private Integer logId;

	private String openId;

	/** 短信发送目标电话号码 */
	private String phone;

	/***/
	private String requestId;

	/** 郵件地址 */
	private String email;
	/** 主題 */
	private String subject;
	/** 郵件內容 */
	private String body;

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

	/** 短信状态 */
	private String messageState;
	
	private String sendType;
	
	

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
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

	@Override
	public String toString() {
		return "{\"logId\":\"" + logId + "\",\"openId\":\"" + openId + "\",\"phone\":\"" + phone + "\",\"requestId\":\"" + requestId + "\",\"email\":\"" + email + "\",\"subject\":\"" + subject + "\",\"body\":\"" + body + "\",\"productId\":\"" + productId + "\",\"productName\":\"" + productName + "\",\"userId\":\"" + userId + "\",\"requestDate\":\"" + requestDate + "\",\"sendDate\":\"" + sendDate + "\",\"logDate\":\"" + logDate + "\",\"messageState\":\"" + messageState + "\"}  ";
	}

}
