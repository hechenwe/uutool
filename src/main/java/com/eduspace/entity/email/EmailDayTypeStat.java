package com.eduspace.entity.email;

/**
 * 按天统计短信日志
 * 
 * @author HeChen
 *
 */

public class EmailDayTypeStat implements java.io.Serializable {
	/** 序列化 */
	private static final long serialVersionUID = 1L;

	/** 编号 */

	private Integer id;

	/** 产品编号 */
	private String productId;

	/** 日期 */
	private java.util.Date date;

	/** 短信类别 */
	private String type;

	/** 请求数量 */
	private Long number;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public java.util.Date getDate() {
		return date;
	}

	public void setDate(java.util.Date date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "{\"id\":\"" + id + "\",\"productId\":\"" + productId + "\",\"date\":\"" + date + "\",\"type\":\"" + type + "\",\"number\":\"" + number + "\"}  ";
	}

}
