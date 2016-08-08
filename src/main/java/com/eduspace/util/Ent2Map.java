package com.eduspace.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实体 与 map
 * 
 * @author pc
 *
 */
public class Ent2Map {

	/**
	 * 将实体类的非空属性映射成Map
	 * 
	 * @param entity
	 *            实体
	 * @param fields
	 *            (1).不提供fields参数时，表示将实体类的非空属性映射成Map.
	 *            (2).当提供2个以上fields参数时，第一个参数值指定为："NEED" 和 "NOT_NEED"; (2.1)
	 *            "NEED" 表示 只转换fields中提供的属性. (2.2) "NOT_NEED" 表示 不转换fields中提供的属性.
	 * @return
	 */
	public static Map<String, Object> getMap(Object entity, String... fields) {
        if(entity == null){
        	return new HashMap<>();
        }
		
		List<String> fList = Arrays.asList(fields);
		int size = fields.length;

		Class<?> clas = entity.getClass();
		Field[] fs = clas.getDeclaredFields();

		if (size == 0) { // 将实体类的非空属性映射成Map
			Map<String, Object> map = new HashMap<>();
			for (Field field : fs) {
				String fieldName = field.getName();
				if (!field.getName().equals("serialVersionUID")) {
					putMap(map, clas, entity, fieldName);
				}
			}
			return map;

		} else if (size >= 2) {
			if (fields[0].equals("NEED")) {// 只转换fields中提供的属性.
				Map<String, Object> map = new HashMap<>();
				for (Field field : fs) {
					String fieldName = field.getName();
					if (!fieldName.equals("serialVersionUID") && fList.contains(fieldName)) {
						putMap(map, clas, entity, fieldName);
					}
				}
				return map;

			} else if (fields[0].equals("NOT_NEED")) {// 表示 不转换fields中提供的属性.
				Map<String, Object> map = new HashMap<>();
				for (Field field : fs) {
					String fieldName = field.getName();
					if (!fieldName.equals("serialVersionUID") && !fList.contains(fieldName)) {
						putMap(map, clas, entity, fieldName);
					}
				}
				return map;
			} else { // 不符合标准
				return new HashMap<>();
			}

		} else {// 不符合标准
			return new HashMap<>();
		}
	}
	/**
	 * 将实体类的非空属性映射成List
	 * 
	 * @param entitys
	 *            实体集
	 * @param fields
	 *            (1).不提供fields参数时，表示将实体类的非空属性映射成Map.
	 *            (2).当提供2个以上fields参数时，第一个参数值指定为："NEED" 和 "NOT_NEED"; (2.1)
	 *            "NEED" 表示 只转换fields中提供的属性. (2.2) "NOT_NEED" 表示 不转换fields中提供的属性.
	 * @return
	 */
	public static List<Map<String, Object>> getList(List<?> entitys, String... fields) {
		List<Map<String, Object>> list = new ArrayList<>();
		for (Object entity : entitys) {
			Map<String, Object> map = getMap(entity, fields);
			list.add(map);
		}
		return list;
	}

	private static void putMap(Map<String, Object> map, Class<?> clas, Object entity, String fieldName) {
		Object value = new Object();
		try {
			PropertyDescriptor pd = new PropertyDescriptor(fieldName, clas);
			Method method = pd.getReadMethod();
			value = method.invoke(entity);
			map.put(fieldName, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	 
}
