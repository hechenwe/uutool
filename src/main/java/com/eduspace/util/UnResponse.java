package com.eduspace.util;

/**
 * 统一返回数据模型
 * 
 * @author pc
 *
 */
public class UnResponse {
	/**
	 * 请求ID(主要用于快速定位请求log日志)
	 */
	private String requestId;
	/**
	 * 请求状态码
	 */
	private String code;
	/**
	 * 请求状态类型
	 */
	private String httpCode;
	/**
	 * 操作提示信息
	 */
	private String message;
	/**
	 * 返回结果
	 */
	private Object result;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getHttpCode() {
		return httpCode;
	}

	public void setHttpCode(String httpCode) {
		this.httpCode = httpCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * 返回结果
	 */
	public Object getResult() {
		return result;
	}
	/**
	 * 返回结果
	 */
	public void setResult(Object result) {
		this.result = result;
	}

}
