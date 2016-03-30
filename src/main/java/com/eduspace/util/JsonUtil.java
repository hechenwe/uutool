package com.eduspace.util;

import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * JSON数据格式 与 JAVA对象之间的转换 
 * @author hechen
 *
 * @param <T>
 */
public class JsonUtil<T> {

	/**
	 * 从一个JSON 对象字符格式中得到一个java对象，形如： {"id" : idValue, "name" : nameValue,"aBean" : {"aBeanId" : aBeanIdValue, ...}}
	 * 
	 * 
	 * @param object
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  T getObject(String jsonString, Class<T> clazz) {
		JSONObject jsonObject = null;
		try {
			jsonObject = JSONObject.fromObject(jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (T) JSONObject.toBean(jsonObject, clazz);
	}

	/**
	 * 从一个JSON数组得到一个java对象数组，形如： [{"id" : idValue, "name" : nameValue}, {"id" :idValue, "name" : nameValue}, ...]
	 * 
	 * 
	 * @param object
	 * @param clazz
	 * @return
	 */
	public List<T> getObjects(String jsonString, Class<T> clazz) {
		
		JSONArray array = JSONArray.fromObject(jsonString);
		List<T> objects = new ArrayList<T>();
		for (int i = 0; i < array.size(); i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			@SuppressWarnings("unchecked")
			T object = (T) JSONObject.toBean(jsonObject, clazz);
			objects.add(object);
		}
		return objects;
	}
	
	
    /**
     * 获得JSON字符串
     * @param obj
     * @return
     */
	public String getJson (Object obj){
		 
		return JSONObject.fromObject(obj).toString();
		
	}
	
 
}
