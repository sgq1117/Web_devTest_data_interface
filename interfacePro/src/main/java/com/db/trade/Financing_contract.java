package com.db.trade;

import java.math.BigDecimal;

import com.xuanniu.util.JsonData;

import net.sf.json.JSONObject;
import xuanniuSgq.Xuanniu_db;

/**
 * @author sungq
 * @version 创建时间：2015年5月15日 下午6:22:38 类说明
 */

public class Financing_contract {
	static JsonData jd = new JsonData();

	/**
	 * 
	 * @param uid
	 * @return
	 */
	private static JSONObject getColumnValue(String financing_contract_sn) {
		JSONObject jsobj = null;
		String sql = "select * from financing_contract where financing_contract_sn in('" + financing_contract_sn + "')";
		// System.out.println(sql);
		Xuanniu_db xdb = new Xuanniu_db();
		jsobj = xdb.searchColumn("xuanniu_trade", sql);
		return jsobj;
	}

	String financing_contract_id;
	String rule_id;
	String currency;
	String financing_contract_sn;
	String total;
	String margin;
	String borrow;
	String warning_amount;
	String force_amount;
	String interest;
	String financing_rate;
	String service_rate;
	String start_time;
	String end_time;
	String usedate_unit;
	String usedate;
	String create_time;
	String update_time;
	String trade_account_id;
	String trade_account_open_type;
	String trade_account_apply_way;
	String financing_status;
	String audit_type;
	String audit_time;
	String financing_over_way;
	String financing_cancle_admin_id;
	String financing_cancle_time;
	String renewal;
	String parent_financing_contract_sn;
	String start_trade_type_byday;

	public String getFinancing_contract_id(String financing_contract_sn) {
		JSONObject jsob = getColumnValue(financing_contract_sn);
		String act_Financing_contract_id = jd.getJsonData(jsob, "financing_contract_id");
		return act_Financing_contract_id;
	}

	public BigDecimal getRule_id(String financing_contract_sn) {
		JSONObject jsob = getColumnValue(financing_contract_sn);
		String act_rule_id = jd.getJsonData(jsob, "rule_id");
		BigDecimal acct_url_id = new BigDecimal(act_rule_id.replace("\"", ""));
		return acct_url_id;
	}

	public String getCurrency(String financing_contract_sn) {
		JSONObject jsob = getColumnValue(financing_contract_sn);
		String act_currency = jd.getJsonData(jsob, "currency");
		return act_currency;
	}

	public String getFinancing_contract_sn(String financing_contract_sn) {
		JSONObject jsob = getColumnValue(financing_contract_sn);
		String act_financing_contract_sn = jd.getJsonData(jsob, "financing_contract_sn");
		return act_financing_contract_sn;
	}

	public String getTotal(String financing_contract_sn) {
		JSONObject jsob = getColumnValue(financing_contract_sn);
		String act_total = jd.getJsonData(jsob, "total");
		return act_total;
	}

	public BigDecimal getMargin(String financing_contract_sn) {
		// System.out.println("financing_contract_sn: "+financing_contract_sn);
		JSONObject jsob = getColumnValue(financing_contract_sn);
		// System.out.println("jsob is: "+jsob);
		String margin = jd.getJsonData(jsob, "margin");
		// System.out.println("margin is: " + margin);
		BigDecimal act_margin = new BigDecimal(margin.replace("\"", ""));
		return act_margin;
	}

	public BigDecimal getBorrow(String financing_contract_sn) {
		JSONObject jsob = getColumnValue(financing_contract_sn);
		String borrow = jd.getJsonData(jsob, "borrow");
		BigDecimal act_borrow = new BigDecimal(borrow.replace("\"", ""));
		return act_borrow;
	}

	public BigDecimal getWarning_amount(String financing_contract_sn) {
		JSONObject jsob = getColumnValue(financing_contract_sn);
		String warning_amount = jd.getJsonData(jsob, "warning_amount");
		BigDecimal act_warning_amount = new BigDecimal(warning_amount.replace("\"", ""));
		return act_warning_amount;
	}

	public BigDecimal getForce_amount(String financing_contract_sn) {
		JSONObject jsob = getColumnValue(financing_contract_sn);
		String force_amount = jd.getJsonData(jsob, "force_amount");
		BigDecimal act_force_amount = new BigDecimal(force_amount.replace("\"", ""));
		return act_force_amount;
	}

	public BigDecimal getInterest(String financing_contract_sn) {
		JSONObject jsob = getColumnValue(financing_contract_sn);
		String interest = jd.getJsonData(jsob, "interest");
		BigDecimal act_interest = new BigDecimal(interest.replace("\"", ""));
//		act_interest = act_interest.multiply(new BigDecimal("100"));
		return act_interest;
	}

	public String getFinancing_rate(String financing_contract_sn) {
		JSONObject jsob = getColumnValue(financing_contract_sn);
		String financing_rate = jd.getJsonData(jsob, "financing_rate");
		return financing_rate;
	}

	public String getService_rate(String financing_contract_sn) {
		JSONObject jsob = getColumnValue(financing_contract_sn);
		String service_rate = jd.getJsonData(jsob, "service_rate");
		return service_rate;
	}

	public String getStart_time(String financing_contract_sn) {
		JSONObject jsob = getColumnValue(financing_contract_sn);
		String start_time = jd.getJsonData(jsob, "start_time");
		return start_time;
	}

	public String getEnd_time(String financing_contract_sn) {
		JSONObject jsob = getColumnValue(financing_contract_sn);
		String end_time = jd.getJsonData(jsob, "end_time");
		return end_time;
	}

	public BigDecimal getUsedate_unit(String financing_contract_sn) {
		JSONObject jsob = getColumnValue(financing_contract_sn);
		String usedate_unit = jd.getJsonData(jsob, "usedate_unit");
		BigDecimal act_usedate_unit = new BigDecimal(usedate_unit.replace("\"", ""));
		return act_usedate_unit;
	}

	public BigDecimal getUsedate(String financing_contract_sn) {
		JSONObject jsob = getColumnValue(financing_contract_sn);
		String usedate = jd.getJsonData(jsob, "usedate");
		
		BigDecimal act_usedate = new BigDecimal(usedate.replace("\"", ""));
		return act_usedate;
	}

	public String getCreate_time(String financing_contract_sn) {
		JSONObject jsob = getColumnValue(financing_contract_sn);
		String create_time = jd.getJsonData(jsob, "create_time");
		return create_time;
	}

	public String getUpdate_time(String financing_contract_sn) {
		JSONObject jsob = getColumnValue(financing_contract_sn);
		String update_time = jd.getJsonData(jsob, "update_time");
		return update_time;
	}

	public BigDecimal getTrade_account_id(String financing_contract_sn) {
		JSONObject jsob = getColumnValue(financing_contract_sn);
		String trade_account_id = jd.getJsonData(jsob, "trade_account_id");
		System.out.println("trade_account_id: "+trade_account_id);
		BigDecimal act_trade_account_id = new BigDecimal(trade_account_id.replace("\"", ""));
		return act_trade_account_id;
	}

	public String getTrade_account_open_type(String financing_contract_sn) {
		JSONObject jsob = getColumnValue(financing_contract_sn);
		String trade_account_open_type = jd.getJsonData(jsob, "trade_account_open_type");
		return trade_account_open_type;
	}

	public String getTrade_account_apply_way(String financing_contract_sn) {
		JSONObject jsob = getColumnValue(financing_contract_sn);
		String trade_account_apply_way = jd.getJsonData(jsob, "trade_account_apply_way");
		return trade_account_apply_way;
	}

	public String getFinancing_status(String financing_contract_sn) {
		JSONObject jsob = getColumnValue(financing_contract_sn);
		String financing_status = jd.getJsonData(jsob, "financing_status");
		return financing_status;
	}

	public String getAudit_type(String financing_contract_sn) {
		JSONObject jsob = getColumnValue(financing_contract_sn);
		String audit_type = jd.getJsonData(jsob, "audit_type");
		return audit_type;
	}

	public String getAudit_time(String financing_contract_sn) {
		JSONObject jsob = getColumnValue(financing_contract_sn);
		String audit_time = jd.getJsonData(jsob, "audit_time");
		return audit_time;
	}

	public String getFinancing_over_way(String financing_contract_sn) {
		JSONObject jsob = getColumnValue(financing_contract_sn);
		String financing_over_way = jd.getJsonData(jsob, "financing_over_way");
		return financing_over_way;
	}

	public String getFinancing_cancle_admin_id(String financing_contract_sn) {
		JSONObject jsob = getColumnValue(financing_contract_sn);
		String financing_cancle_admin_id = jd.getJsonData(jsob, "financing_cancle_admin_id");
		return financing_cancle_admin_id;
	}

	public String getFinancing_cancle_time(String financing_contract_sn) {
		JSONObject jsob = getColumnValue(financing_contract_sn);
		String financing_cancle_time = jd.getJsonData(jsob, "financing_cancle_time");
		return financing_cancle_time;
	}

	public String getRenewal(String financing_contract_sn) {
		JSONObject jsob = getColumnValue(financing_contract_sn);
		String renewal = jd.getJsonData(jsob, "renewal");
		return renewal;
	}

	public String getParent_financing_contract_sn(String financing_contract_sn) {
		JSONObject jsob = getColumnValue(financing_contract_sn);
		String parent_financing_contract_sn = jd.getJsonData(jsob, "parent_financing_contract_sn");
		return parent_financing_contract_sn;
	}

	public BigDecimal getStart_trade_type_byday(String financing_contract_sn) {
		JSONObject jsob = getColumnValue(financing_contract_sn);
		String start_trade_type_byday = jd.getJsonData(jsob, "start_trade_type_byday");
		BigDecimal act_start_trade_type_byday = new BigDecimal(start_trade_type_byday.replace("\"", ""));
		return act_start_trade_type_byday;
	}

	public static void main(String[] args) {
		Financing_contract fc = new Financing_contract();
		// JSONObject jsob = getColumnValue("PZ499");
		// String status = jd.getJsonData(jsob, "financing_status");
		// System.out.println(fc.getFinancing_contract_id("PZ534"));
		System.out.println(fc.getUsedate("PZ536"));
	}
}
