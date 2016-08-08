package com.eduspace.util;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Request获取参数的工具类
 * 
 * @author pc
 *
 */
public class RequestUtil {

	private HttpServletRequest request;

	public RequestUtil(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * 获取String类型的参数
	 * 
	 * @param request
	 * @param parameterName
	 * @return
	 */
	public String getString(String parameterName) {

		String str = this.request.getParameter(parameterName);
		if (str != null) {
			return str.trim();
		} else {
			return null;
		}

	}

	/**
	 * 获取 request中的实体模型 对象
	 * 
	 * @param entityClass
	 *            实体模型的类型
	 * @return 模型对象
	 */
	public Object getEntity(Class<?> entityClass) {

		try {
			Object object = entityClass.newInstance();
			Field[] fields = entityClass.getDeclaredFields();
			for (Field field : fields) {
				String type = field.getType().getSimpleName();
				String key = field.getName();
				Object value = switchData(type, key);

				if (value == null) {
					continue;
				}

				PropertyDescriptor pd = new PropertyDescriptor(key, entityClass);
				Method method = pd.getWriteMethod();
				method.invoke(object, value);
			}
			return object;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取int类型的参数
	 * 
	 * @param request
	 * @param parameterName
	 * @return
	 */
	public Integer getInt(String parameterName) {
		String str = this.request.getParameter(parameterName);
		Integer parameter = null;
		if (str != null && !str.equals("")) {
			try {
				parameter = Integer.parseInt(str.trim());
			} catch (Exception e) {

			}
		}
		return parameter;
	}

	/**
	 * 获取long类型的参数
	 * 
	 * @param request
	 * @param parameterName
	 * @return
	 */
	public Long getLong(String parameterName) {
		String str = this.request.getParameter(parameterName);
		Long parameter = null;
		if (str != null && !str.equals("")) {
			try {
				parameter = Long.parseLong(str.trim());
			} catch (Exception e) {

			}
		}
		return parameter;
	}

	/**
	 * 获取boolean类型的参数
	 * 
	 * @param request
	 * @param parameterName
	 * @return
	 */
	public Boolean getBoolean(String parameterName) {
		String str = this.request.getParameter(parameterName);
		Boolean parameter = null;
		if (str != null && !str.equals("")) {
			try {
				parameter = Boolean.parseBoolean(str.trim());
			} catch (Exception e) {

			}
		}
		return parameter;
	}

	/**
	 * 获取byte类型的参数
	 * 
	 * @param request
	 * @param parameterName
	 * @return
	 */
	public Byte getByte(String parameterName) {
		String str = this.request.getParameter(parameterName);
		Byte parameter = null;
		if (str != null && !str.equals("")) {
			try {
				parameter = Byte.parseByte(str.trim());
			} catch (Exception e) {

			}
		}
		return parameter;
	}

	/**
	 * 获取short类型的参数
	 * 
	 * @param request
	 * @param parameterName
	 * @return
	 */
	public Short getShort(String parameterName) {
		String str = this.request.getParameter(parameterName);
		Short parameter = null;
		if (str != null && !str.equals("")) {
			try {
				parameter = Short.parseShort(str.trim());
			} catch (Exception e) {

			}
		}
		return parameter;
	}

	/**
	 * 获取float类型的参数
	 * 
	 * @param request
	 * @param parameterName
	 * @return
	 */
	public Float getFloat(String parameterName) {
		String str = this.request.getParameter(parameterName);
		Float parameter = null;
		if (str != null && !str.equals("")) {
			try {
				parameter = Float.parseFloat(str.trim());
			} catch (Exception e) {

			}
		}
		return parameter;
	}

	/**
	 * 获取double类型的参数
	 * 
	 * @param request
	 * @param parameterName
	 * @return
	 */
	public Double getDouble(String parameterName) {
		String str = this.request.getParameter(parameterName);
		Double parameter = null;
		if (str != null && !str.equals("")) {
			try {
				parameter = Double.parseDouble(str.trim());
			} catch (Exception e) {

			}
		}
		return parameter;
	}

	/**
	 * 获取Date类型的参数
	 * 
	 * @param request
	 * @param parameterName
	 *            参数名称
	 * @param formatDate
	 *            时间格式 如："yyyy-MM-dd"
	 * @return
	 */
	public Date getDate(String parameterName, String formatDate) {
		String str = this.request.getParameter(parameterName);
		Date parameter = null;
		if (str != null && !str.equals("")) {
			try {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate);// 小写的mm表示的是分钟
				parameter = simpleDateFormat.parse(str.trim());
			} catch (Exception e) {

			}
		}
		return parameter;
	}

	private Object switchData(String type, String key) {

		Object obj = new Object();
		switch (type) {
		case "Integer":
			obj = getInt(key);
			break;
		case "Long":
			obj = getLong(key);
			break;
		case "Short":
			obj = getShort(key);
			break;
		case "Boolean":
			obj = getBoolean(key);
			break;
		case "Float":
			obj = getFloat(key);
			break;
		case "Double":
			obj = getDouble(key);
			break;
		case "Byte":
			obj = getByte(key);
			break;
		case "Date":
			obj = getDate(key, "yyyy-MM-dd hh:mm:ss");
			break;
		default:
			obj = getString(key);
		}
		return obj;
	}
	/**
	 * 将json字符串写入response输入流中
	 * 
	 * @param jsonString
	 * @param response
	 */
	public static void printWriterJson(String jsonString, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter printWriter = null;
		try {
			printWriter = response.getWriter();
			printWriter.append(jsonString);
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			printWriter.close();
		}
	}
}
