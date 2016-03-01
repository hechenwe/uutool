package com.eduspace.util;
/**
 * 工程路径 工具类
 * @author pc
 *
 */
public class PathUtil {
 
    /**
     * 获取src路径
     * @return
     */
	public static String getSrc() {
		String src = Thread.currentThread().getContextClassLoader().getResource("/").getPath() + "";
		return src;
	}
    /**
     * 获取webroot路径
     * @return
     */
	public static String getWebRoot() {
		String webRoot = System.getProperty("tool.root");
		return webRoot;
	}

}
