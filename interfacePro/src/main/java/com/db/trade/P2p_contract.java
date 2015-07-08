package com.db.trade;

import com.xuanniu.util.JsonData;

import net.sf.json.JSONObject;
import xuanniuSgq.Xuanniu_db;

/**
 * @author sungq
 * @version 创建时间：2015年5月15日 下午8:16:32 类说明
 */

public class P2p_contract {
	static JsonData jd = new JsonData();

	private static JSONObject getColumnValue(String financing_contract_id) {
		JSONObject jsobj = null;
		String sql = "select * from p2p_contract where financing_contract_id in ('"+financing_contract_id+"')";
		// System.out.println(sql);
		Xuanniu_db xdb = new Xuanniu_db();
		jsobj = xdb.searchColumn("xuanniu_trade", sql);
		return jsobj;
	}
	
	String p2p_contract_id;
	String financing_contract_id;
	String p2p_contract_sn;
	String prod_name;
	String prod_desc;
	String p2p_type;
	String raise_status;
	String bid_type;
	String create_time;
	String update_time;
	String usedate_unit;
	String interest;
	String raise_price;
	String raise_amount;
	String fact_raise_amount;
	String finish_rate;
	String finish_time;
	String financing_account_id;
	String usedate;
	String period;
	String financing_leverage;
	String raise_rest_money;
	String start_time;
	String end_time;
	String next_bill_time;
	String bid_limit;
	String limit_amount;
	String forfeit;
	
	
	public String getP2p_contract_id(String financing_contract_id) {
		JSONObject jsob = getColumnValue(financing_contract_id);
		String p2p_contract_id = jd.getJsonData(jsob, "p2p_contract_id");
		return p2p_contract_id;
	}
	public String getFinancing_contract_id(String financing_contract_id) {
		JSONObject jsob = getColumnValue(financing_contract_id);
		String act_financing_contract_id = jd.getJsonData(jsob, "financing_contract_id");
		return act_financing_contract_id;
	}
	public String getP2p_contract_sn(String financing_contract_id) {
		JSONObject jsob = getColumnValue(financing_contract_id);
		String p2p_contract_sn = jd.getJsonData(jsob, "p2p_contract_sn");
		return p2p_contract_sn;
	}
	public String getProd_name(String financing_contract_id) {
		JSONObject jsob = getColumnValue(financing_contract_id);
		String prod_name = jd.getJsonData(jsob, "prod_name");
		return prod_name;
	}
	public String getProd_desc(String financing_contract_id) {
		JSONObject jsob = getColumnValue(financing_contract_id);
		String prod_desc = jd.getJsonData(jsob, "prod_desc");
		return prod_desc;
	}
	public String getP2p_type(String financing_contract_id) {
		JSONObject jsob = getColumnValue(financing_contract_id);
		String p2p_type = jd.getJsonData(jsob, "p2p_type");
		return p2p_type;
	}
	public String getRaise_status(String financing_contract_id) {
		JSONObject jsob = getColumnValue(financing_contract_id);
		String raise_status = jd.getJsonData(jsob, "raise_status");
		return raise_status;
	}
	public String getBid_type(String financing_contract_id) {
		JSONObject jsob = getColumnValue(financing_contract_id);
		String bid_type = jd.getJsonData(jsob, "bid_type");
		return bid_type;
	}
	public String getCreate_time(String financing_contract_id) {
		JSONObject jsob = getColumnValue(financing_contract_id);
		String create_time = jd.getJsonData(jsob, "create_time");
		return create_time;
	}
	public String getUpdate_time(String financing_contract_id) {
		JSONObject jsob = getColumnValue(financing_contract_id);
		String update_time = jd.getJsonData(jsob, "update_time");
		return update_time;
	}
	public String getUsedate_unit(String financing_contract_id) {
		JSONObject jsob = getColumnValue(financing_contract_id);
		String usedate_unit = jd.getJsonData(jsob, "usedate_unit");
		return usedate_unit;
	}
	public String getInterest(String financing_contract_id) {
		JSONObject jsob = getColumnValue(financing_contract_id);
		String interest = jd.getJsonData(jsob, "interest");
		return interest;
	}
	public String getRaise_price(String financing_contract_id) {
		JSONObject jsob = getColumnValue(financing_contract_id);
		String raise_price = jd.getJsonData(jsob, "raise_price");
		return raise_price;
	}
	public String getRaise_amount(String financing_contract_id) {
		JSONObject jsob = getColumnValue(financing_contract_id);
		String raise_amount = jd.getJsonData(jsob, "raise_amount");
		return raise_amount;
	}
	public String getFact_raise_amount(String financing_contract_id) {
		JSONObject jsob = getColumnValue(financing_contract_id);
		String fact_raise_amount = jd.getJsonData(jsob, "fact_raise_amount");
		return fact_raise_amount;
	}
	public String getFinish_rate(String financing_contract_id) {
		JSONObject jsob = getColumnValue(financing_contract_id);
		String finish_rate = jd.getJsonData(jsob, "finish_rate");
		return finish_rate;
	}
	public String getFinish_time(String financing_contract_id) {
		JSONObject jsob = getColumnValue(financing_contract_id);
		String finish_time = jd.getJsonData(jsob, "finish_time");
		return finish_time;
	}
	public String getFinancing_account_id(String financing_contract_id) {
		JSONObject jsob = getColumnValue(financing_contract_id);
		String financing_account_id = jd.getJsonData(jsob, "financing_account_id");
		return financing_account_id;
	}
	public String getUsedate(String financing_contract_id) {
		JSONObject jsob = getColumnValue(financing_contract_id);
		String usedate = jd.getJsonData(jsob, "usedate");
		return usedate;
	}
	public String getPeriod(String financing_contract_id) {
		JSONObject jsob = getColumnValue(financing_contract_id);
		String period = jd.getJsonData(jsob, "period");
		return period;
	}
	public String getFinancing_leverage(String financing_contract_id) {
		JSONObject jsob = getColumnValue(financing_contract_id);
		String financing_leverage = jd.getJsonData(jsob, "financing_leverage");
		return financing_leverage;
	}
	public String getRaise_rest_money(String financing_contract_id) {
		JSONObject jsob = getColumnValue(financing_contract_id);
		String raise_rest_money = jd.getJsonData(jsob, "raise_rest_money");
		return raise_rest_money;
	}
	public String getStart_time(String financing_contract_id) {
		JSONObject jsob = getColumnValue(financing_contract_id);
		String start_time = jd.getJsonData(jsob, "start_time");
		return start_time;
	}
	public String getEnd_time(String financing_contract_id) {
		JSONObject jsob = getColumnValue(financing_contract_id);
		String end_time = jd.getJsonData(jsob, "end_time");
		return end_time;
	}
	public String getNext_bill_time(String financing_contract_id) {
		JSONObject jsob = getColumnValue(financing_contract_id);
		String next_bill_time = jd.getJsonData(jsob, "next_bill_time");
		return next_bill_time;
	}
	public String getBid_limit(String financing_contract_id) {
		JSONObject jsob = getColumnValue(financing_contract_id);
		String bid_limit = jd.getJsonData(jsob, "bid_limit");
		return bid_limit;
	}
	public String getLimit_amount(String financing_contract_id) {
		JSONObject jsob = getColumnValue(financing_contract_id);
		String limit_amount = jd.getJsonData(jsob, "limit_amount");
		return limit_amount;
	}
	public String getForfeit(String financing_contract_id) {
		JSONObject jsob = getColumnValue(financing_contract_id);
		String forfeit = jd.getJsonData(jsob, "forfeit");
		return forfeit;
	}

	
	public static void main(String[] args) {
		JSONObject jsob = P2p_contract.getColumnValue("499");
		System.out.println(jd.getJsonData(jsob,"financing_contract_id"));
		System.out.println(jd.getJsonData(jsob,"fact_raise_amount"));
		
		P2p_contract pc =new P2p_contract();
		System.out.println(pc.getRaise_amount("499"));
	}

}
