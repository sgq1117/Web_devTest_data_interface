package com.db.trade;

import com.xuanniu.util.JsonData;

import net.sf.json.JSONObject;
import xuanniuSgq.Xuanniu_db;

/**
 * @author sungq
 * @version 创建时间：2015年5月15日 下午9:11:27 类说明
 */

public class Trade_account {
	static JsonData jd = new JsonData();

	private static JSONObject getColumnValue(String uid) {
		JSONObject jsobj = null;
		String sql = "select * from trade_account where uid in('" + uid + "')";
		// System.out.println(sql);
		Xuanniu_db xdb = new Xuanniu_db();
		jsobj = xdb.searchColumn("xuanniu_trade", sql);
		return jsobj;
	}

	String trade_account_id;
	String uid;
	String currency;
	String type;
	String status;
	String total;
	String margin;
	String warning;

	public String getTrade_account_id(String uid) {
		JSONObject jsob = getColumnValue(uid);
		String trade_account_id = jd.getJsonData(jsob, "trade_account_id");
		return trade_account_id;
	}

	public String getUid(String uid) {
		JSONObject jsob = getColumnValue(uid);
		String act_uid = jd.getJsonData(jsob, "uid");
		return act_uid;
	}

	public String getCurrency(String uid) {
		JSONObject jsob = getColumnValue(uid);
		String currency = jd.getJsonData(jsob, "currency");
		return currency;
	}

	public String getType(String uid) {
		JSONObject jsob = getColumnValue(uid);
		String type = jd.getJsonData(jsob, "type");
		return type;
	}

	public String getStatus(String uid) {
		JSONObject jsob = getColumnValue(uid);
		String status = jd.getJsonData(jsob, "status");
		return status;
	}

	public String getTotal(String uid) {
		JSONObject jsob = getColumnValue(uid);
		String total = jd.getJsonData(jsob, "total");
		return total;
	}

	public String getMargin(String uid) {
		JSONObject jsob = getColumnValue(uid);
		String margin = jd.getJsonData(jsob, "margin");
		return margin;
	}

	public String getWarning(String uid) {
		JSONObject jsob = getColumnValue(uid);
		String warning = jd.getJsonData(jsob, "warning");
		return warning;
	}

	public String getForce(String uid) {
		JSONObject jsob = getColumnValue(uid);
		String force = jd.getJsonData(jsob, "force");
		return force;
	}

	public String getFinancing_rate(String uid) {
		JSONObject jsob = getColumnValue(uid);
		String financing_rate = jd.getJsonData(jsob, "financing_rate");
		return financing_rate;
	}

	public String getBorrow(String uid) {
		JSONObject jsob = getColumnValue(uid);
		String borrow = jd.getJsonData(jsob, "borrow");
		return borrow;
	}

	public String getBalance(String uid) {
		JSONObject jsob = getColumnValue(uid);
		String balance = jd.getJsonData(jsob, "balance");
		return balance;
	}

	public String getAvailable(String uid) {
		JSONObject jsob = getColumnValue(uid);
		String available = jd.getJsonData(jsob, "available");
		return available;
	}

	public String getFreeze(String uid) {
		JSONObject jsob = getColumnValue(uid);
		String freeze = jd.getJsonData(jsob, "freeze");
		return freeze;
	}

	public String getCreate_time(String uid) {
		JSONObject jsob = getColumnValue(uid);
		String create_time = jd.getJsonData(jsob, "create_time");
		return create_time;
	}

	public String getUpdate_time(String uid) {
		JSONObject jsob = getColumnValue(uid);
		String update_time = jd.getJsonData(jsob, "update_time");
		return update_time;
	}

	public String getFinancing_account_id(String uid) {
		JSONObject jsob = getColumnValue(uid);
		String financing_account_id = jd.getJsonData(jsob, "financing_account_id");
		return financing_account_id;
	}

	public String getFinancing_contract_num(String uid) {
		JSONObject jsob = getColumnValue(uid);
		String financing_contract_num = jd.getJsonData(jsob, "financing_contract_num");
		return financing_contract_num;
	}

	public String getRecently_financing_contract_end_time(String uid) {
		JSONObject jsob = getColumnValue(uid);
		String recently_financing_contract_end_time = jd.getJsonData(jsob, "recently_financing_contract_end_time");
		return recently_financing_contract_end_time;
	}

	public String getTrade_channel_account(String uid) {
		JSONObject jsob = getColumnValue(uid);
		String trade_channel_account = jd.getJsonData(jsob, "trade_channel_account");
		return trade_channel_account;
	}

	public String getTrade_channel_password(String uid) {
		JSONObject jsob = getColumnValue(uid);
		String trade_channel_password = jd.getJsonData(jsob, "trade_channel_password");
		return trade_channel_password;
	}

	public String getTrade_channel_status(String uid) {
		JSONObject jsob = getColumnValue(uid);
		String trade_channel_status = jd.getJsonData(jsob, "trade_channel_status");
		return trade_channel_status;
	}

	public String getTotal_interest(String uid) {
		JSONObject jsob = getColumnValue(uid);
		String total_interest = jd.getJsonData(jsob, "total_interest");
		return total_interest;
	}

	public String getPaid_interest(String uid) {
		JSONObject jsob = getColumnValue(uid);
		String paid_interest = jd.getJsonData(jsob, "paid_interest");
		return paid_interest;
	}

	public String getProfit(String uid) {
		JSONObject jsob = getColumnValue(uid);
		String profit = jd.getJsonData(jsob, "profit");
		return profit;
	}

	public String getTrade_channel_type(String uid) {
		JSONObject jsob = getColumnValue(uid);
		String trade_channel_type = jd.getJsonData(jsob, "trade_channel_type");
		return trade_channel_type;
	}

	public String getCreate_status(String uid) {
		JSONObject jsob = getColumnValue(uid);
		String create_status = jd.getJsonData(jsob, "create_status");
		return create_status;
	}

	public String getTrade_channel_encpwd(String uid) {
		JSONObject jsob = getColumnValue(uid);
		String trade_channel_encpwd = jd.getJsonData(jsob, "trade_channel_encpwd");
		return trade_channel_encpwd;
	}

	public String getChannel_total(String uid) {
		JSONObject jsob = getColumnValue(uid);
		String channel_total = jd.getJsonData(jsob, "channel_total");
		return channel_total;
	}

	public String getChannel_available(String uid) {
		JSONObject jsob = getColumnValue(uid);
		String channel_available = jd.getJsonData(jsob, "channel_available");
		return channel_available;
	}

	public String getChannel_freeze(String uid) {
		JSONObject jsob = getColumnValue(uid);
		String channel_freeze = jd.getJsonData(jsob, "channel_freeze");
		return channel_freeze;
	}

	public String getChannel_profit(String uid) {
		JSONObject jsob = getColumnValue(uid);
		String channel_profit = jd.getJsonData(jsob, "channel_profit");
		return channel_profit;
	}

	public String getChannel_stock(String uid) {
		JSONObject jsob = getColumnValue(uid);
		String channel_stock = jd.getJsonData(jsob, "channel_stock");
		return channel_stock;
	}

	public String getFinancing_account_type(String uid) {
		JSONObject jsob = getColumnValue(uid);
		String financing_account_type = jd.getJsonData(jsob, "financing_account_type");
		return financing_account_type;
	}

	public String getUsedate_unit(String uid) {
		JSONObject jsob = getColumnValue(uid);
		String usedate_unit = jd.getJsonData(jsob, "usedate_unit");
		return usedate_unit;
	}

	String force;
	String financing_rate;
	String borrow;
	String balance;
	String available;
	String freeze;
	String create_time;
	String update_time;
	String financing_account_id;
	String financing_contract_num;
	String recently_financing_contract_end_time;
	String trade_channel_account;
	String trade_channel_password;
	String trade_channel_status;
	String total_interest;
	String paid_interest;
	String profit;
	String trade_channel_type;
	String create_status;
	String trade_channel_encpwd;
	String channel_total;
	String channel_available;
	String channel_freeze;
	String channel_profit;
	String channel_stock;
	String financing_account_type;
	String usedate_unit;

	public static void main(String[] args) {
//		Trade_account ta = new Trade_account();
//		getColumnValue("164");
//		String financing_account_id = jd.getJsonData(getColumnValue("164"), "trade_account_id");
//		System.out.println(financing_account_id);
//		
//		System.out.println(ta.getTrade_account_id("164"));

		
//		String financingContractSn = "PZ00001004";
//		String uid = "267";
//		String url = "http://back.xuanniu.com:8080/p2p/terminal?financingContractSn="+financingContractSn+"+&uid="+uid;
//		System.out.println(url);
	}
}
