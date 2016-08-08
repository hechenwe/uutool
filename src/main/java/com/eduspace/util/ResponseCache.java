package com.eduspace.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 接口返回参数 缓存
 * 
 * @author pc
 *
 */
public class ResponseCache {
	private static Map<String, UnResponse> cache = new HashMap<String, UnResponse>();

	static {
		
		PropertiesUtil pu = new PropertiesUtil(PathUtil.getClassPath()+"response.properties");
		Map<String,String> map = pu.getKeyAndValue();
        for (Map.Entry<String, String> entry : map.entrySet()) {
        	 String code = entry.getKey();
        	 String value = entry.getValue();
        	 String[] values = value.split("-->") ;
        	 String httpCode = values[0];
        	 String message = values[1];
        	 UnResponse ur = new UnResponse();
        	 ur.setCode(code);
        	 ur.setHttpCode(httpCode);
        	 ur.setMessage(message);
			 cache.put(code,ur);
		}
	}

	public static Map<String, UnResponse> getCache() {
		return cache;
	}
	
	
}
