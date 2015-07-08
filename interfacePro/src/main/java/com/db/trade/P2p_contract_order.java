package com.db.trade;

import com.xuanniu.util.JsonData;

import net.sf.json.JSONObject;
import xuanniuSgq.Xuanniu_db;

/**
 * @author sungq
 * @version 创建时间：2015年5月15日 下午8:47:10 类说明
 */

public class P2p_contract_order {
	
	static JsonData jd = new JsonData();

	private static JSONObject getColumnValue(String p2p_contract_id) {
		JSONObject jsobj = null;
		String sql = "select * from p2p_contract_order where p2p_contract_id in ('"+p2p_contract_id+"')";
		// System.out.println(sql);
		Xuanniu_db xdb = new Xuanniu_db();
		jsobj = xdb.searchColumn("xuanniu_trade", sql);
		return jsobj;
	}
	

	String p2p_order_id;
	String p2p_contract_id;
	String p2p_order_sn;
	String uid;
	String bid_price;
	String bid_amount;
	String status;
	String create_time;
	String update_time;
	
	public String getP2p_order_id(String p2p_contract_id) {
		JSONObject jsob = getColumnValue(p2p_contract_id);
		String p2p_order_id = jd.getJsonData(jsob, "p2p_order_id");
		return p2p_order_id;
	}

	public String getP2p_contract_id(String p2p_contract_id) {
		JSONObject jsob = getColumnValue(p2p_contract_id);
		String act_p2p_contract_id = jd.getJsonData(jsob, "p2p_contract_id");
		return act_p2p_contract_id;
	}

	public String getP2p_order_sn(String p2p_contract_id) {
		JSONObject jsob = getColumnValue(p2p_contract_id);
		String p2p_order_sn = jd.getJsonData(jsob, "p2p_order_sn");
		return p2p_order_sn;
	}

	public String getUid(String p2p_contract_id) {
		JSONObject jsob = getColumnValue(p2p_contract_id);
		String uid = jd.getJsonData(jsob, "uid");
		return uid;
	}

	public String getBid_price(String p2p_contract_id) {
		JSONObject jsob = getColumnValue(p2p_contract_id);
		String bid_price = jd.getJsonData(jsob, "bid_price");
		return bid_price;
	}

	public String getBid_amount(String p2p_contract_id) {
		JSONObject jsob = getColumnValue(p2p_contract_id);
		String bid_amount = jd.getJsonData(jsob, "bid_amount");
		return bid_amount;
	}

	public String getStatus(String p2p_contract_id) {
		JSONObject jsob = getColumnValue(p2p_contract_id);
		String status = jd.getJsonData(jsob, "status");
		return status;
	}

	public String getCreate_time(String p2p_contract_id) {
		JSONObject jsob = getColumnValue(p2p_contract_id);
		String create_time = jd.getJsonData(jsob, "create_time");
		return create_time;
	}

	public String getUpdate_time(String p2p_contract_id) {
		JSONObject jsob = getColumnValue(p2p_contract_id);
		String update_time = jd.getJsonData(jsob, "update_time");
		return update_time;
	}

	public static void main (String[] args){
		
		P2p_contract_order pco = new P2p_contract_order();
//		getColumnValue("394");
//		jd.getJsonData(getColumnValue("394"),"p2p_contract_id");
		System.out.println(pco.getP2p_order_sn("568"));
		
		
	}

}
