package com.db.trade;

import java.math.BigDecimal;

import com.xuanniu.util.JsonData;

import xuanniuSgq.Xuanniu_db;
import net.sf.json.JSONObject;

/**
 * @author sungq
 * @version 创建时间：2015年5月16日 下午3:07:33 类说明
 */

public class Financing_risk_rule {
	static JsonData jd = new JsonData();
	static Xuanniu_db xdb = new Xuanniu_db();

	private static JSONObject getColumnValue(int id) {

		JSONObject jsobj = null;
		String sql = "select * from financing_risk_rule where id in('" + id + "')";
		// System.out.println(sql);
		jsobj = xdb.searchColumn("xuanniu_trade", sql);
		return jsobj;
	}

	String id;
	String currency;
	String financing_min;
	String financing_max;
	String month_warning_rate;
	String month_force_rate;
	String day_warning_rate;
	String day_force_rate;
	String uid;

	public String getId(int id) {
		JSONObject jsob = getColumnValue(id);
		String act_id = jd.getJsonData(jsob, "id");
		return act_id;
	}

	public String getCurrency(int id) {
		JSONObject jsob = getColumnValue(id);
		String currency = jd.getJsonData(jsob, "currency");
		return currency;
	}

	public BigDecimal getFinancing_min(int id) {
		JSONObject jsob = getColumnValue(id);
		String financing_min = jd.getJsonData(jsob, "financing_min");
		BigDecimal act_financing_min = new BigDecimal(financing_min.replace("\"", ""));
		return act_financing_min;
	}

	public BigDecimal getFinancing_max(int id) {
		JSONObject jsob = getColumnValue(id);
		String financing_max = jd.getJsonData(jsob, "financing_max");
		BigDecimal act_financing_max = new BigDecimal(financing_max.replace("\"", ""));
		return act_financing_max;
	}

	public BigDecimal getMonth_warning_rate(int id) {
		JSONObject jsob = getColumnValue(id);
		BigDecimal month_warning_rate = new BigDecimal(jd.getJsonData(jsob, "month_warning_rate").replace("\"", ""));
		return month_warning_rate;
	}

	public BigDecimal getMonth_force_rate(int id) {
		JSONObject jsob = getColumnValue(id);
		BigDecimal month_force_rate = new BigDecimal(jd.getJsonData(jsob, "month_force_rate").replace("\"", ""));
		return month_force_rate;
	}

	public BigDecimal getDay_warning_rate(int id) {
		JSONObject jsob = getColumnValue(id);
		String day_warning_rate = jd.getJsonData(jsob, "day_warning_rate");
		BigDecimal act_day_warning_rate = new BigDecimal(day_warning_rate.replace("\"", ""));
		return act_day_warning_rate;
	}

	public BigDecimal getDay_force_rate(int id) {
		JSONObject jsob = getColumnValue(id);
		BigDecimal day_force_rate = new BigDecimal(jd.getJsonData(jsob, "day_force_rate").replace("\"", ""));
		return day_force_rate;
	}

	public String getUid(int id) {
		JSONObject jsob = getColumnValue(id);
		String uid = jd.getJsonData(jsob, "uid");
		return uid;
	}

	public static void main(String[] args) {
		Financing_risk_rule frr = new Financing_risk_rule();
		// System.out.println(getColumnValue(1));
		System.out.println(frr.getDay_force_rate(2));
	}

}
