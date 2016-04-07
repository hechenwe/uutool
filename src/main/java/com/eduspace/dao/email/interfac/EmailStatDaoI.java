package com.eduspace.dao.email.interfac;
 
import java.util.Map;

import com.eduspace.entity.email.EmailStat;
import com.sooncode.jdbc.DaoI;

public interface EmailStatDaoI extends DaoI<EmailStat> {
     
	/**
	 * 获取所用项目的邮件数据 
	 * @return
	 */
	public Map<String,Object> getTotal(); 
}
