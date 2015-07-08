package com.xuanniu.chongzhi;

/** 
 * @author sungq 
 * @version 创建时间：2015年5月16日 上午11:36:58 
 * 类说明 
 */

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.xuanniu.http.login.mfbusiness.HttpLoginFrontBusiness;
import com.xuanniu.http.sql.HttpSqlCurrencyAccount;
import com.db.trade.Currency_account;
import com.xuanniu.util.CalcNum;

@RunWith(Parameterized.class)
public class CurrencyAccountTest {
	HttpLoginFrontBusiness hlfb = new HttpLoginFrontBusiness();
	Currency_account ca = new Currency_account();
	HttpSqlCurrencyAccount cab = new HttpSqlCurrencyAccount();
	CalcNum cn = new CalcNum();


	private String mobile;
	private String password = "123456";
	private BigDecimal moneyUp;

	public CurrencyAccountTest(String mobile, BigDecimal moneyUp) {
		this.mobile = mobile;
		this.moneyUp = moneyUp;
	}

	@SuppressWarnings("rawtypes")
	@Parameters
	public static Collection prepareData() {
		// String mobile = "15313926589";
//		 String mobile = "99913920001";
		// String mobile = "99913920002";
//		String mobile = "99913920003";
		// String mobile = "99913920004";
		// String mobile = "99913920005";
		// String mobile = "99913920006";
		// String mobile = "99913920007";

		// String mobile = "99913920008";
		// String mobile = "99913920009";
		 String mobile = "99913920010";
		// String mobile = "15313926589";

		// String mobile = "99913920006";
		// String mobile = "99913920007";

		BigDecimal moneyup = new BigDecimal(3000000);

		Object[][] object = { { mobile, moneyup } };
		return Arrays.asList(object);

	}

	@Test
	public void chongZhiTest() {
		System.out.println("----CASE1: 用户充值判断");
		hlfb.indexSumary(mobile, password);
		// ---1充值前额度
		BigDecimal pre_balance = ca.getBalance(mobile);
		BigDecimal pre_available = ca.getAvailable(mobile);
		// ---2-充值操作----可调用相应的方法
		cab.updateChongZhi(mobile, moneyUp);
		// ---3-充值后额度
		BigDecimal af_balance = ca.getBalance(mobile);
		BigDecimal af_available = ca.getAvailable(mobile);
		// ---4-前后相减法、比较充值的额度是否如预期
		BigDecimal balanceAdd = cn.sub(af_balance, pre_balance);
		BigDecimal availableAdd = cn.sub(af_available, pre_available);

		System.out.println("	充值前 总额：" + pre_balance + "充值前 可用：" + pre_available);
		System.out.println("	充值后总额：" + af_balance + "充值后可用：" + af_available);

		assertEquals(0, cn.comparTo(balanceAdd, moneyUp));
		assertEquals(0, cn.comparTo(availableAdd, moneyUp));
	}

	// @Test
	public void balanceSumTest() {
		System.out.println("----CASE2: 总额判断　balance=available+freeze");
		BigDecimal balance = ca.getBalance(mobile);

		BigDecimal available = ca.getAvailable(mobile);
		BigDecimal freeze = ca.getFreeze(mobile);
		BigDecimal sumTotal = cn.add(available, freeze);
		assertEquals(balance, sumTotal);
	}

	// @Test
	public void freezeSumTest() {
		System.out.println("----CASE3: 冻结判断　freeze=financing_freeze+p2p_freeze");
		BigDecimal freeze = ca.getFreeze(mobile);

		BigDecimal financing_freeze = ca.getFinancing_freeze(mobile);
		BigDecimal p2p_freeze = ca.getP2p_freeze(mobile);
		BigDecimal sumFreeze = cn.add(financing_freeze, p2p_freeze);
		assertEquals(freeze, sumFreeze);

	}

	// @Test
	public void getCurrencyMoney() {
		System.out.println("---- 账户金额判断----");
		// ---1充值前额度
		String rongziMobile = "99913926577";
		BigDecimal balance = ca.getBalance(rongziMobile);
		BigDecimal available = ca.getAvailable(rongziMobile);
		BigDecimal all_freeze = ca.getFreeze(rongziMobile);
		BigDecimal Financing_freeze = ca.getFinancing_freeze(rongziMobile);
		BigDecimal P2p_freeze = ca.getP2p_freeze(rongziMobile);

		System.out.println("balance=======>: " + balance + "    available======>:" + available);
		System.out.println("all_freeze====>: " + all_freeze + "\nFin_freeze====>: " + Financing_freeze + "\nP2p_freeze====>: " + P2p_freeze);

	}

}
