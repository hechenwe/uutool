package com.eduspace.util;

/**
 * Oauth 認證 返回值數據模型
 * 
 * @author pc
 * 
 */
public class OauthResponse {
	private String responseCode;
	private String productName;
	private String productId;
	private String userId;

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
