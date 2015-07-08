package com.db.trade;

import java.math.BigDecimal;

import com.xuanniu.util.JsonData;

import xuanniuSgq.Xuanniu_db;
import net.sf.json.JSONObject;

/**
 * 本金借款倍数相关的类
 * @author sungq
 * @version 创建时间：2015年5月16日 下午3:07:51 类说明
 */

public class Financing_rule {
	static JsonData jd = new JsonData();
	static Xuanniu_db xdb = new Xuanniu_db();

	private static JSONObject getColumnValue(int id) {
		JSONObject jsobj = null;
		String sql = "select * from financing_rule where id in('" + id + "')";
		// System.out.println(sql);
		jsobj = xdb.searchColumn("xuanniu_trade", sql);
		return jsobj;
	}

	String id;
	String rule_name;

	public String getId(int id) {
		JSONObject jsob = getColumnValue(id);
		String act_id = jd.getJsonData(jsob, "id");
		return act_id;
	}

	public String getRule_name(int id) {
		JSONObject jsob = getColumnValue(id);
		String rule_name = jd.getJsonData(jsob, "rule_name");
		return rule_name;
	}

	public String getCurrency(int id) {
		JSONObject jsob = getColumnValue(id);
		String currency = jd.getJsonData(jsob, "currency");
		return currency;
	}

	public BigDecimal getFinancing_rate(int id) {
		JSONObject jsob = getColumnValue(id);
		BigDecimal financing_rate = new BigDecimal(jd.getJsonData(jsob, "financing_rate").replace("\"", ""));
		BigDecimal act_financing_rate = financing_rate.divide(new BigDecimal(100));
		return act_financing_rate;
	}

	public BigDecimal getInterest_min(int id) {
		JSONObject jsob = getColumnValue(id);
		BigDecimal interest_min = new BigDecimal(jd.getJsonData(jsob, "interest_min").replace("\"", ""));
		return interest_min;
	}

	public BigDecimal getInterest_max(int id) {
		JSONObject jsob = getColumnValue(id);
		BigDecimal interest_max = new BigDecimal(jd.getJsonData(jsob, "interest_max").replace("\"", ""));
		return interest_max;
	}

	public String getUsedate_unit(int id) {
		JSONObject jsob = getColumnValue(id);
		String usedate_unit = jd.getJsonData(jsob, "usedate_unit");
		return usedate_unit;
	}

	public BigDecimal getUsedate_min(int id) {
		JSONObject jsob = getColumnValue(id);
		BigDecimal usedate_min = new BigDecimal(jd.getJsonData(jsob, "usedate_min").replace("\"", ""));
		return usedate_min;
	}

	public BigDecimal getUsedate_max(int id) {
		JSONObject jsob = getColumnValue(id);
		BigDecimal usedate_max = new BigDecimal(jd.getJsonData(jsob, "usedate_max").replace("\"", ""));
		return usedate_max;
	}

	public String getStatus(int id) {
		JSONObject jsob = getColumnValue(id);
		String status = jd.getJsonData(jsob, "status");
		return status;
	}

	public String getCreate_time(int id) {
		JSONObject jsob = getColumnValue(id);
		String create_time = jd.getJsonData(jsob, "create_time");
		return create_time;
	}

	public String getUpdate_time(int id) {
		JSONObject jsob = getColumnValue(id);
		String update_time = jd.getJsonData(jsob, "update_time");
		return update_time;
	}

	String currency;
	String financing_rate;
	String interest_min;
	String interest_max;
	String usedate_unit;
	String usedate_min;
	String usedate_max;
	String status;
	String create_time;
	String update_time;

	public static void main(String[] args) {
		Financing_rule fr = new Financing_rule();
		System.out.println(fr.getFinancing_rate(12));
		// System.out.println(getColumnValue(1));
	}

}
