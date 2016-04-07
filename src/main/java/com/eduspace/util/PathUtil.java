package com.eduspace.util;

import java.io.File;

/**
 * 工程路径 工具类
 * @author pc
 *
 */
public class PathUtil {
 
	public static String getSrc() {
		 String src = new PathUtil().getClasses();
		 return src;
	}
	private String getClasses() {
		   String path = this.getClass().getResource("/").getPath();
		   File file = new File(path);
		   String classesPath = file.toString()+File.separatorChar;
		   return classesPath;
		  
		}

}
