package com.db.trade;

import net.sf.json.JSONObject;
import xuanniuSgq.Xuanniu_db;

import com.xuanniu.util.JsonData;

/**
 * @author sungq
 * @version 创建时间：2015年5月21日 下午5:16:52 类说明
 */
public class Platform_account {

	
	static JsonData jd = new JsonData();

	@SuppressWarnings("unused")
	private static JSONObject getColumnValue(int type, String business_sn) {
		JSONObject jsobj = null;
		String sql = "select * from platform_account where type = "+type+" and business_sn ="+business_sn;
		 System.out.println(sql);
		Xuanniu_db xdb = new Xuanniu_db();
		jsobj = xdb.searchColumn("xuanniu_trade", sql);
		return jsobj;
	}
	
	String platform_account_id;
	String currency;
	String type;
	String balance;
	String available;
	String freeze;
	String create_time;
	String update_time;
	String business_type;
	String business_sn;
	@SuppressWarnings("unused")
	public static void main(String[] args) {
//		Platform_account pa = new Platform_account();
		JSONObject jsob = Platform_account.getColumnValue(2,"JK00000934");
		System.out.println("jsob");
	}
}
