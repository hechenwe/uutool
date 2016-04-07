package com.eduspace.dao.jpush.interfac;

import java.util.Map;

import com.eduspace.entity.jpush.JpushStat;
import com.sooncode.jdbc.DaoI;

public interface JpushStatDaoI extends DaoI<JpushStat> {

	public Map<String, Object> getTotal();

}
