package com.eduspace.dao.email;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.eduspace.dao.email.interfac.EmailStatDaoI;
 
import com.eduspace.entity.email.EmailStat;
 
import com.sooncode.jdbc.Dao;
 

@Repository
public class EmailStatDao extends Dao<EmailStat>implements EmailStatDaoI {

	@Override
	public Map<String,Object> getTotal() {
		String sql = "SELECT SUM(today_suc) AS today_suc,SUM(today_fal) AS today_fal,SUM(three_suc) AS three_suc,SUM(three_fal) AS three_fal,SUM(seven_suc) AS seven_suc,SUM(seven_fal) AS seven_fal,SUM(month_suc) AS month_suc,SUM(month_fal) AS month_fal,SUM(half_year_suc) AS half_year_suc,SUM(half_year_fal) AS half_year_fal,SUM(year_suc) AS year_suc,SUM(year_fal) AS year_fal FROM email_stat";
		Map<String,Object> map =  jdbc.executeQueryM(sql);
		return map;
	}

}
