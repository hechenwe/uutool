package com.eduspace.entity.jpush;

/**
 * 推送日志
 * 
 * @author pc
 *
 */
public class JpushLog {
	/** 日志编号 */
	private Integer logId;

	private String openId;

	/**电话号码 */
	private String phone;

	/***/
	private String requestId;
    /**
     * 推送对象
     */
	private String object;
	
	/**
	 * 推送内容
	 */
	private String content;
	
	private String sercet;
	private String appKey;

	
	private String contentType; 
	private String type ; 
	private String title ; 
	private String id  ; 
	private String testType;  
	
	 
	private String msgContent ; 
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

	/**状态 */
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

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSercet() {
		return sercet;
	}

	public void setSercet(String sercet) {
		this.sercet = sercet;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTestType() {
		return testType;
	}

	public void setTestType(String testType) {
		this.testType = testType;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	 

}
