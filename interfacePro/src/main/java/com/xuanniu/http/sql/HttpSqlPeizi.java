package com.xuanniu.http.sql; 

import java.math.BigDecimal;

import net.sf.json.JSONObject;
import xuanniuSgq.Xuanniu_db;

/** 
* @author sungq 
* @version 创建时间：2015年5月24日 下午4:36:08 
* 类说明 
*/ 

public class HttpSqlPeizi {
	static Xuanniu_db xuanniudb = new Xuanniu_db();
	
	/**
	 * 从 xuanniu_trade.currency_account表中取得相应字段值
	 * @param mobile
	 * @param param
	 * @return
	 */
	public static BigDecimal getCurrency_AcountValue(String mobile,String param){
		String sql = "select * from xuanniu_trade.currency_account where uid in(select id from xuanniu_security.security_user where mobile in('"+mobile+"'))";
//		System.out.println("sql: "+sql);
		JSONObject jsob = xuanniudb.searchColumn("xuanniu_trade", sql);
//		System.out.println("jot is: "+jsob.get("available"));
		String paramStr = jsob.getString(param);
		BigDecimal act_paramStr = new BigDecimal(paramStr);
		return act_paramStr;
	}
	/**
	 * 从xuanniu_trade.financing_contract表中取得相关数据
	 * @param financing_contract_sn
	 * @param param
	 * @return
	 */
	public static String getFinancingContract(String financing_contract_sn, String param){
		
		String sql = "select * from xuanniu_trade.financing_contract where financing_contract_sn in ('"+financing_contract_sn+"')";
		JSONObject jsob = xuanniudb.searchColumn("xuanniu_trade", sql);
		String paramStr = jsob.getString(param);
//		BigDecimal act_paramStr = new BigDecimal(paramStr);
		return paramStr;
	}
	
	/**
	 * 从xuaniu_trade.p2p_contract_id 表中取得相关数据 
	 * @param p2p_contract_id
	 * @param param
	 * @return
	 */
	public static String getP2p_Contract(String financing_contract_id,String param){
		
		String sql = "select * from xuanniu_trade.p2p_contract where financing_contract_id in ('"+financing_contract_id+"')";
		JSONObject jsob = xuanniudb.searchColumn("xuanniu_trade", sql);
//		System.out.println("p2p_contract jsob: "+ jsob);
//		System.out.println("param is: "+param);
		String paramStr = jsob.getString(param);
//		BigDecimal act_paramStr = new BigDecimal(paramStr);
		return paramStr;
	}
	
	/**
	 * 从xuanniu_trade.platform_account取得平台火币账户金额
	 * @param type
	 * @param param
	 * @return
	 */
	public static BigDecimal getPlat_Huobi_accountValue(String param){
		String sql = "select * from xuanniu_trade.platform_account where type  in ('1')";
		JSONObject jsob = xuanniudb.searchColumn("xuanniu_trade", sql);
		String paramStr = jsob.getString(param);
		BigDecimal act_paramStr = new BigDecimal(paramStr);
		return act_paramStr;
	}
	/**
	 * 从xuanniu_trade.platform_account取得平台代付账户余额
	 * @param business_sn
	 * @param param
	 * @return
	 */
	public static BigDecimal getPlat_daifu_accountValue (String business_sn,String param){
		String sql = "select * from xuanniu_trade.platform_account where type in ('"+2+"') and business_sn in('"+business_sn+"')";
		JSONObject jsob = xuanniudb.searchColumn("xuanniu_trade", sql);
		String paramStr = jsob.getString(param);
		BigDecimal act_paramStr = new BigDecimal(paramStr);
		return act_paramStr;
	}
	
	/**
	 * 获取操盘账户trade_account中的内容
	 * @param trade_account_id
	 * @param param
	 * @return
	 */
	public static String getTrade_Account(String trade_account_id,String param){
		
		String sql = "select * from xuanniu_trade.trade_account where trade_account_id in ('"+trade_account_id+"')";
		JSONObject jsob = xuanniudb.searchColumn("xuanniu_trade", sql);
		String paramStr = jsob.getString(param);
		return paramStr;
	}
	public static void main(String[] args){
		
		System.out.println("1、当前可用余额: "+getCurrency_AcountValue("99913920001","available"));
//		System.out.println("2、financint_contract id is: "+ getFinancingContract("PZ00001082","financing_contract_id"));
//		System.out.println("3、p2p_contract_id is: "+ getP2p_Contract("1082","p2p_contract_id"));
//		System.out.println("4、plat 货币账户余额："+getPlat_Huobi_accountValue("available"));
//		System.out.println("5、plat 代付账户余额："+getPlat_daifu_accountValue("JK00001230","available"));
		
//		System.out.println("1592, 操盘账户中的保证金："+getTrade_Account("1592","margin"));
	}
}


