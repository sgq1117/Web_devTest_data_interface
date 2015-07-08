package com.xuanniu.http.sql;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import xuanniuSgq.Xuanniu_db;

import com.db.trade.Currency_account;
import com.xuanniu.util.CalcNum;
import com.xuanniu.util.Conf;

/**
 * @author sungq
 * @version 创建时间：2015年5月16日 上午10:25:47 类说明
 */

public class HttpSqlCurrencyAccount {
	Currency_account ca = new Currency_account();
	CalcNum cn = new CalcNum();
	Xuanniu_db xdb = new Xuanniu_db();
	int uid = Conf.uid;

	/**
	 * 充值操作
	 * 
	 * @param mobile
	 * @param moneyUp
	 */
	public void updateChongZhi(String mobile, BigDecimal moneyUp) {
		// --原有数值
		BigDecimal pre_balance = ca.getBalance(mobile);
		BigDecimal pre_available = ca.getAvailable(mobile);
		// System.out.println("充值前 总额：" + pre_balance);
		// System.out.println("充值前 可用：" + pre_available);

		// ---想加后数值
		BigDecimal af_balance = cn.add(pre_balance, moneyUp);
		BigDecimal af_available = cn.add(pre_available, moneyUp);

		int uid = Currency_account.getUidWithMobile(mobile);

		String sql2 = "update currency_account set available=" + af_available + ", balance=" + af_balance + " where uid in('" + uid + "')";
		xdb.updateColumn("xuanniu_trade", sql2);

		BigDecimal pre_balance_v2 = ca.getBalance(mobile);
		BigDecimal pre_available_v2 = ca.getAvailable(mobile);

		// System.out.println("充值后 总额：" + pre_balance_v2);
		// System.out.println("充值后 可用：" + pre_available_v2);
	}

	public Map getCurrencyAccountFieldValue(int uid) {
		HashMap<String, BigDecimal> map = new HashMap<String, BigDecimal>();

		return map;
	}

	public static void main(String[] args) {
		/**
		 * 充值
		 */
		HttpSqlCurrencyAccount cab = new HttpSqlCurrencyAccount();
		BigDecimal money = new BigDecimal(20);
		cab.updateChongZhi("77713926582", money);

	}
}
