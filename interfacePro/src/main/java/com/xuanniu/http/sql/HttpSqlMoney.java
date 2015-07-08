package com.xuanniu.http.sql;

import java.math.BigDecimal;

import net.sf.json.JSONObject;
import xuanniuSgq.Xuanniu_db;

/**
 * @author sungq
 * @version 创建时间：2015年6月3日 下午4:32:17 类说明
 */

public class HttpSqlMoney {
	static Xuanniu_db xdb = new Xuanniu_db();
	/**
	 * 返回月服务费率
	 * @param uid
	 * @param financing_rate
	 * @return
	 */
	public static BigDecimal getMonServiceRate(String uid, String financing_rate) {
		String sql = "select * from xuanniu_trade.financing_config where uid in('" + uid + "')";
		JSONObject jsob = xdb.searchColumn("xuanniu_trade", sql);
		String jsob_Param = jsob.getString("month_service_rate");
		// BigDecimal act_paramVal = new BigDecimal(paramVal);
		JSONObject jsob_mon_rate = JSONObject.fromObject(jsob_Param);
		String fin_rate_val = null;
		if (financing_rate.equals("100")) {
			fin_rate_val = jsob_mon_rate.get("100").toString();
		} else if (financing_rate.equals("200")) {
			fin_rate_val = jsob_mon_rate.get("200").toString();
		} else if (financing_rate.equals("300")) {
			fin_rate_val = jsob_mon_rate.get("300").toString();
		} else if (financing_rate.equals("400")) {
			fin_rate_val = jsob_mon_rate.get("400").toString();
		} else if (financing_rate.equals("500")) {
			fin_rate_val = jsob_mon_rate.get("500").toString();
		}

		BigDecimal return_rate = new BigDecimal(fin_rate_val);
		return return_rate;
	}

	/**
	 * financing_config 用于根据uid、取得：天服务费率
	 * 
	 * @param uid
	 * @param param
	 * @return
	 */
	public static BigDecimal getDayServiceRate(String uid) {
		String sql = "select * from xuanniu_trade.financing_config where uid in('" + uid + "')";
		JSONObject jsob = xdb.searchColumn("xuanniu_trade", sql);
		// System.out.println("jsob: " + jsob);
		String paramVal = jsob.getString("day_service_rate");
		BigDecimal act_paramVal = new BigDecimal(paramVal);
		return act_paramVal;
	}

	/**
	 * financing_risk_rule 用于根据id、uid、取得：月、天平仓线警告线
	 * 
	 * @param id
	 * @param uid
	 * @param param
	 * @return
	 */
	public static BigDecimal getFinancingRiskRule(String id, String uid, String param) {
		String sql = "select * from xuanniu_trade.financing_risk_rule where id in('" + id + "') and uid in('" + uid + "')";
		JSONObject jsob = xdb.searchColumn("xuanniu_trade", sql);
		String paramVal = jsob.getString(param);
		BigDecimal act_paramVal = new BigDecimal(paramVal);
		return act_paramVal;
	}

	/**
	 * financing_rule 主要用于根据rule_id取得：配资比例
	 * 
	 * @param id
	 * @param param
	 * @return
	 */
	public static BigDecimal getFiancingRule(String ruleId, String param) {
		String sql = "select * from xuanniu_trade.financing_rule where id in('" + ruleId + "')";
		BigDecimal act_paramVal = null;
		JSONObject jsob = xdb.searchColumn("xuanniu_trade", sql);
		String paramVal = jsob.getString(param);
		act_paramVal = new BigDecimal(paramVal);
		if (param.equals("financing_rate")) {
			act_paramVal = act_paramVal.divide(new BigDecimal("100"));
		}
		return act_paramVal;
	}

	public static void main(String[] args) {
//		BigDecimal rate = getFiancingRule("12", "financing_rate");
//		System.out.println("rate: " + rate);

		BigDecimal mon_service_rate = getMonServiceRate("0", "200");
		System.out.println("mon_service_rate:" + mon_service_rate);

		// BigDecimal day_service_rate = getFinancingConfig("0",
		// "day_service_rate");
		// System.out.println("天费率: " + day_service_rate);
		//
		// BigDecimal month_warning_rate = getFinancingRiskRule("1", "0",
		// "month_warning_rate");
		// System.out.println("month_warning_rate: " + month_warning_rate);
	}
}
