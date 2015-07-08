package com.db.trade;

import java.math.BigDecimal;

import com.xuanniu.util.JsonData;

import net.sf.json.JSONObject;
import xuanniuSgq.Xuanniu_db;

/**
 * @author sungq
 * @version 创建时间：2015年5月15日 下午5:50:47 类说明
 */

public class Currency_account {
	static JsonData jd = new JsonData();
	static Xuanniu_db xdb = new Xuanniu_db();

	/**
	 * 返回security_User表中mobile对应的uid
	 * 
	 * @param mobile
	 * @return
	 */
	public static int getUidWithMobile(String mobile) {
		String sql = "select * from xuanniu_security.security_user where mobile in('" + mobile + "')";
		JSONObject jot = xdb.searchColumn("xuanniu_trade", sql);
		String id = jd.getJsonData(jot, "id").replace("\"", "");
		int uid = Integer.parseInt(id);
		return uid;
	}

	/**
	 * 从currency account表中取出 火币账号相关的数据
	 * 
	 * @param uid
	 * @return
	 */
	public static JSONObject getColumnValue(String mobile) {
		JSONObject jsobj = null;
		int uid = getUidWithMobile(mobile);
		String sql = "select * from currency_account where uid in('" + uid + "')";
		jsobj = xdb.searchColumn("xuanniu_trade", sql);
		return jsobj;
	}

	String currency;
	String balance;
	String available;
	String freeze;
	String create_time;
	String update_time;
	String financing_freeze;
	String p2p_freeze;

	/**
	 * 
	 * @param uid
	 * @return
	 */
	public String getCurrency(String mobile) {
		JSONObject jsob = getColumnValue(mobile);
		String act_currency = jd.getJsonData(jsob, "currency");
		return act_currency;
	}

	/**
	 * 
	 * @param uid
	 * @return
	 */
	public BigDecimal getBalance(String mobile) {
		JSONObject jsob = getColumnValue(mobile);
		String act_balance = jd.getJsonData(jsob, "balance");
		BigDecimal balance = new BigDecimal(act_balance.replace("\"", ""));
		return balance;
	}

	/**
	 * 
	 * @param uid
	 * @return
	 */
	public BigDecimal getAvailable(String mobile) {
		JSONObject jsob = getColumnValue(mobile);
		String act_available = jd.getJsonData(jsob, "available");
		BigDecimal available = new BigDecimal(act_available.replace("\"", ""));
		return available;
	}

	/**
	 * 
	 * @param uid
	 * @return
	 */
	public BigDecimal getFreeze(String mobile) {
		JSONObject jsob = getColumnValue(mobile);
		String act_freeze = jd.getJsonData(jsob, "freeze");
		BigDecimal freeze = new BigDecimal(act_freeze.replace("\"", ""));
		return freeze;
	}

	/**
	 * 
	 * @param uid
	 * @return
	 */
	public String getCreate_time(String mobile) {
		JSONObject jsob = getColumnValue(mobile);
		String act_create_time = jd.getJsonData(jsob, "create_time");
		return act_create_time;
	}

	/**
	 * 
	 * @param uid
	 * @return
	 */
	public String getUpdate_time(String mobile) {
		JSONObject jsob = getColumnValue(mobile);
		String act_update_time = jd.getJsonData(jsob, "update_time");
		return act_update_time;
	}

	/**
	 * 
	 * @param uid
	 * @return
	 */
	public BigDecimal getFinancing_freeze(String mobile) {
		JSONObject jsob = getColumnValue(mobile);
		String act_financing_freeze = jd.getJsonData(jsob, "financing_freeze");
		BigDecimal financing_freeze = new BigDecimal(act_financing_freeze.replace("\"", ""));
		return financing_freeze;
	}

	/**
	 * 
	 * @param uid
	 * @return
	 */
	public BigDecimal getP2p_freeze(String mobile) {
		JSONObject jsob = getColumnValue(mobile);
		String act_p2p_freeze = jd.getJsonData(jsob, "p2p_freeze");
		BigDecimal p2p_freeze = new BigDecimal(act_p2p_freeze.replace("\"", ""));
		return p2p_freeze;
	}

	public static void main(String[] args) {
		 Currency_account ca = new Currency_account();
//		 System.out.println("currency: "+ca.getBalance("13511111111"));

		 
//		System.out.println("uid: " + Currency_account.getUidWithMobile("13511111111"));

		// BigDecimal balance = ca.getBalance(uid);
		// System.out.println("balance: " + balance);

		// String StrBd="1048576.1054";
		// System.out.println("StrBd"+StrBd);
		// BigDecimal bd=new BigDecimal(StrBd);
		// bd=bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		// System.out.println("bd"+bd);

	}
}
