package com.eduspace.entity.sms;

import java.io.Serializable;

/**
 * 短信模板
 * 
 * @author pc
 *
 */
public class SmsTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6520493783969612219L;
	 
	private Integer id;
	private String smsKey;
	private String smsValue;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSmsKey() {
		return smsKey;
	}

	public void setSmsKey(String smsKey) {
		this.smsKey = smsKey;
	}

	public String getSmsValue() {
		return smsValue;
	}

	public void setSmsValue(String smsValue) {
		this.smsValue = smsValue;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	 
}
