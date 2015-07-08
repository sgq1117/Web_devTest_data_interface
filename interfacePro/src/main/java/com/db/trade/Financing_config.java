package com.db.trade;

import java.math.BigDecimal;

import com.xuanniu.util.JsonData;

import xuanniuSgq.Xuanniu_db;
import net.sf.json.JSONObject;

/**
 * 服务费相关的、目前服务费仅仅是根据uid来划分的、还没有根据不同金额的范围层次来划分
 * 
 * @author sungq
 * @version 创建时间：2015年5月16日 下午3:07:03 类说明
 */

public class Financing_config {
	static JsonData jd = new JsonData();
	static Xuanniu_db xdb = new Xuanniu_db();

	private static JSONObject getColumnValue(int uid) {
		JSONObject jsobj = null;
		String sql = "select * from financing_config where uid in ('" + uid + "')";
		// System.out.println(sql);
		jsobj = xdb.searchColumn("xuanniu_trade", sql);
		return jsobj;

	}

	String id;
	String currency;
	String margin_min;
	String margin_max;
	String month_service_rate;
	String day_service_rate;
	String uid;

	public String getId(int uid) {
		JSONObject jsob = getColumnValue(uid);
		String id = jd.getJsonData(jsob, "id");
		return id;
	}

	public String getCurrency(int uid) {
		JSONObject jsob = getColumnValue(uid);
		String currency = jd.getJsonData(jsob, "currency");
		return currency;
	}

	public BigDecimal getMargin_min(int uid) {
		JSONObject jsob = getColumnValue(uid);
		String margin_min = jd.getJsonData(jsob, "margin_min");
		BigDecimal act_margin_min = new BigDecimal(margin_min.replace("\"", ""));
		return act_margin_min;
	}

	public BigDecimal getMargin_max(int uid) {
		JSONObject jsob = getColumnValue(uid);
		String margin_max = jd.getJsonData(jsob, "margin_max");
		BigDecimal act_margin_max = new BigDecimal(margin_max.replace("\"", ""));
		return act_margin_max;
	}

	public BigDecimal getMonth_service_rate(int uid) {
		JSONObject jsob = getColumnValue(uid);
		String month_service_rate = jd.getJsonData(jsob, "month_service_rate");
		BigDecimal act_month_service_rate = new BigDecimal(month_service_rate.replace("\"", ""));
		return act_month_service_rate;
	}

	public BigDecimal getDay_service_rate(int uid) {
		JSONObject jsob = getColumnValue(uid);
		String day_service_rate = jd.getJsonData(jsob, "day_service_rate");
		BigDecimal act_day_service_rate = new BigDecimal(day_service_rate.replace("\"", ""));
		return act_day_service_rate;
	}

	public String getUid(int uid) {
		JSONObject jsob = getColumnValue(uid);
		String act_uid = jd.getJsonData(jsob, "uid");
		return act_uid;
	}

	public static void main(String[] args) {
		Financing_config fc = new Financing_config();
		// ---------
		JSONObject jsob = getColumnValue(0);
		System.out.println(jd.getJsonData(jsob, "margin_min"));
		// --------
		BigDecimal service_rate = fc.getDay_service_rate(0);
		System.out.println(service_rate);

	}
}
