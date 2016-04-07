package com.eduspace.util;

import java.io.File;

/**
 * 工程路径 工具类
 * @author pc
 *
 */
public class PathUtil {
 
	public static String getSrc() {
		   Object obj = new Object();
		   String path = obj.getClass().getResource("/").getPath();
		   File file = new File(path);
		   String classesPath = file.toString()+File.separatorChar;
		   obj = null;
		   return classesPath;
	}
	 
public static void main(String[] args) {
	System.out.println("PathUtil.main()"+getSrc());
}
}
