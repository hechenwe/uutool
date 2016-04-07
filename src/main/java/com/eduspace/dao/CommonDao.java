package com.eduspace.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.eduspace.util.CalendarUtil;
import com.sooncode.jdbc.Jdbc;
import com.sooncode.jdbc.sql.SQL;
import com.sooncode.jdbc.util.T2E;

/**
 * 公共Dao
 * 
 * @author pc
 *
 */
public class CommonDao {
	public static Logger logger = Logger.getLogger("CommonDao.class");
	public static List<Map<String, Object>> getScopeStat(Jdbc jdbc, String productId, String type, Class<?> logEnity, Class<?> dayStatEnity, Class<?> monthEntity) {
		CalendarUtil cu = new CalendarUtil(new Date());
		String format = "yyyy-MM-dd";
		String today = cu.getString(format);
		String sqlString = "";
		if (type.equals("today")) {
			SQL sql = new SQL();

			if (productId == null || productId.equals("")) {
				sql = sql.SELECT().COUNT("1").AS("number").PUT_KEY(",MESSAGE_STATE AS TYPE , DATE_FORMAT ( REQUEST_DATE , '%H') AS DATE").FROM().TABLE(logEnity).WHERE().LIKE("requestDate", today).PUT_KEY("GROUP BY	DATE ,MESSAGE_STATE");
			} else {
				sql = sql.SELECT().COUNT("1").AS("number").PUT_KEY(",MESSAGE_STATE AS TYPE , DATE_FORMAT ( REQUEST_DATE , '%H') AS DATE").FROM().TABLE(logEnity).WHERE().EQ("productId", productId).AND().LIKE("requestDate", today).PUT_KEY("GROUP BY	DATE ,MESSAGE_STATE");
			}
            logger.info("【CommonDao.list】"+sql.toString());
			List<Map<String, Object>> oldList = jdbc.executeQueryL(sql.toString());
			List<Map<String, Object>> newList = new ArrayList<>();
			Map<String, Map<String, Object>> supMap = new HashMap<>();
			// 数据转换---
			for (Map<String, Object> m : oldList) {
				String date = (String) m.get("date");
				String typ = (String) m.get("type");
				Long number = (Long) m.get("number");
				Map<String, Object> ma = supMap.get(date);
				if (ma == null) {
					ma = new HashMap<>();
				}
				if (typ.equals("1")) {
					ma.put("sucNumber", number);
				} else {
					ma.put("falNumber", number);
				}
				supMap.put(date, ma);

			}
			for (Entry<String, Map<String, Object>> ent : supMap.entrySet()) {

				Map<String, Object> subMap = new HashMap<>();
				subMap.put("date", ent.getKey());
				Long sucNumber = (Long) ent.getValue().get("sucNumber");
				if (sucNumber == null) {
					sucNumber = 0L;
				}
				subMap.put("sucNumber", sucNumber);
				Long falNumber = (Long) ent.getValue().get("falNumber");
				if (falNumber == null) {
					falNumber = 0L;
				}
				subMap.put("falNumber", falNumber);
				newList.add(subMap);
			}
			// ---数据转换
			return newList;

		} else if (type.equals("day3")) {
			String day3 = cu.getChangeDay(-3, format);
			if (productId == null || productId.equals("")) {
				sqlString = "SELECT DATE_FORMAT(DATE,'%Y-%m-%d') AS DATE ,SUM(FAL_NUMBER) AS FAL_NUMBER ,SUM(SUC_NUMBER) AS SUC_NUMBER FROM " + T2E.field2Column(dayStatEnity.getSimpleName()) + " WHERE  DATE(DATE) BETWEEN '" + day3 + "' AND '" + today + "' GROUP BY DATE_FORMAT(DATE,'%Y-%m-%d')";
			} else {
				sqlString = "SELECT DATE_FORMAT(DATE,'%Y-%m-%d') AS DATE ,FAL_NUMBER ,SUC_NUMBER FROM " + T2E.field2Column(dayStatEnity.getSimpleName()) + " WHERE PRODUCT_ID='" + productId + "' AND DATE(DATE) BETWEEN '" + day3 + "' AND '" + today + "'";
			}

		} else if (type.equals("day7")) {
			String day7 = cu.getChangeDay(-7, format);
			if (productId == null || productId.equals("")) {
				sqlString = "SELECT DATE_FORMAT(DATE,'%Y-%m-%d') AS DATE ,SUM(FAL_NUMBER) AS FAL_NUMBER ,SUM(SUC_NUMBER) AS SUC_NUMBER FROM " + T2E.field2Column(dayStatEnity.getSimpleName()) + " WHERE DATE(DATE) BETWEEN '" + day7 + "' AND '" + today + "' GROUP BY DATE_FORMAT(DATE,'%Y-%m-%d')";
			} else {
				sqlString = "SELECT DATE_FORMAT(DATE,'%Y-%m-%d') AS DATE ,FAL_NUMBER ,SUC_NUMBER FROM " + T2E.field2Column(dayStatEnity.getSimpleName()) + " WHERE PRODUCT_ID='" + productId + "' AND DATE(DATE) BETWEEN '" + day7 + "' AND '" + today + "'";
			}

		} else if (type.equals("day30")) {
			String day30 = cu.getChangeDay(-30, format);
			if (productId == null || productId.equals("")) {
				sqlString = "SELECT DATE_FORMAT(DATE,'%Y-%m-%d') AS DATE ,SUM(FAL_NUMBER) AS FAL_NUMBER ,SUM(SUC_NUMBER) AS SUC_NUMBER FROM " + T2E.field2Column(dayStatEnity.getSimpleName()) + " WHERE DATE(DATE) BETWEEN '" + day30 + "' AND '" + today + "' GROUP BY DATE_FORMAT(DATE,'%Y-%m-%d')";
			} else {
				sqlString = "SELECT DATE_FORMAT(DATE,'%Y-%m-%d') AS DATE ,FAL_NUMBER ,SUC_NUMBER FROM " + T2E.field2Column(dayStatEnity.getSimpleName()) + " WHERE PRODUCT_ID='" + productId + "' AND DATE(DATE) BETWEEN '" + day30 + "' AND '" + today + "'";
			}

		} else if (type.equals("day183")) {
			String day183 = cu.getChangeMonth(-6, format);
			if (productId == null || productId.equals("")) {
				sqlString = "SELECT DATE_FORMAT(STR_TO_DATE(MONTH, '%Y-%m-%d'),'%Y-%m'	) AS DATE,SUM(FAL_NUMBER) AS FAL_NUMBER ,SUM(SUC_NUMBER) AS SUC_NUMBER FROM " + T2E.field2Column(monthEntity.getSimpleName()) + " WHERE STR_TO_DATE(MONTH, '%Y-%m-%d') BETWEEN '" + day183 + "' AND '" + today + "' GROUP BY DATE_FORMAT(STR_TO_DATE(MONTH, '%Y-%m-%d'),'%Y-%m'	)";
			} else {
				sqlString = "SELECT DATE_FORMAT(STR_TO_DATE(MONTH, '%Y-%m-%d'),'%Y-%m'	) AS DATE,FAL_NUMBER,SUC_NUMBER FROM " + T2E.field2Column(monthEntity.getSimpleName()) + " WHERE PRODUCT_ID = '" + productId + "' AND STR_TO_DATE(MONTH, '%Y-%m-%d') BETWEEN '" + day183 + "' AND '" + today + "'";
			}

		} else if (type.equals("day365")) {
			String day365 = cu.getChangeMonth(-12, format);
			if (productId == null || productId.equals("")) {
				sqlString = "SELECT DATE_FORMAT(STR_TO_DATE(MONTH, '%Y-%m-%d'),'%Y-%m'	) AS DATE,SUM(FAL_NUMBER) AS FAL_NUMBER ,SUM(SUC_NUMBER) AS SUC_NUMBER FROM " + T2E.field2Column(monthEntity.getSimpleName()) + " WHERE STR_TO_DATE(MONTH, '%Y-%m-%d') BETWEEN '" + day365 + "' AND '" + today + "' GROUP BY DATE_FORMAT(STR_TO_DATE(MONTH, '%Y-%m-%d'),'%Y-%m'	)";
			} else {
				sqlString = "SELECT DATE_FORMAT(STR_TO_DATE(MONTH, '%Y-%m-%d'),'%Y-%m'	) AS DATE,FAL_NUMBER,SUC_NUMBER FROM " + T2E.field2Column(monthEntity.getSimpleName()) + " WHERE PRODUCT_ID = '" + productId + "' AND STR_TO_DATE(MONTH, '%Y-%m-%d') BETWEEN '" + day365 + "' AND '" + today + "'";
			}

		} else {
			return null;
		}

		return jdbc.executeQueryL(sqlString);
	}
}
