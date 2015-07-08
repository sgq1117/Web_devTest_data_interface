package com.xuanniu.http.OfflineVerifyTest;

/** 
 * @author sungq 
 * @version 创建时间：2015年6月9日 上午9:27:43 
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

import com.db.trade.Currency_account;
import com.xuanniu.http.login.mfbusiness.HttpLoginBackBusiness;
import com.xuanniu.http.login.mfbusiness.HttpLoginFrontBusiness;
import com.xuanniu.http.sql.HttpSqlCurrencyAccount;
import com.xuanniu.http.sql.HttpSqlPeizi;
import com.xuanniu.util.CalcNum;
import com.xuanniu.util.PzCalcMoney;

@RunWith(Parameterized.class)
public class PzTb_Day_A {
	/**
	 * 天配资(配资、投标 、资金划转 ok)
	 */
	// ---------
	HttpLoginFrontBusiness hlfb = new HttpLoginFrontBusiness();
	HttpLoginBackBusiness hlbb = new HttpLoginBackBusiness();
	CalcNum cn = new CalcNum();
	// ----账户-----
//	String applyPzMobile = "99913920001";
	 String applyPzMobile = "99913920004";
	// String applyPzMobile = "99913930003";

	String tbPzMobile_Va = "99913920002";
	String tbPzMobile_Vb = "99913920003";
	String Password = "123456";
	// -----
	String p2pInterestByDay = "0.4375";
	// ----请求参数------
	private String margin;
	private String usedateUnit;
	private String tradeAccountId;
	private String ruleId;
	private String usedate;
	private String tradeStartTime;
	// ----投标金额
	private String tBamount;

	// -----

	public PzTb_Day_A(String margin, String usedateUnit, String tradeAccountId, String ruleId, String usedate, String tradeStartTime, String tBamount) {
		this.margin = margin;
		this.usedateUnit = usedateUnit;
		this.tradeAccountId = tradeAccountId;
		this.ruleId = ruleId;
		this.usedate = usedate;
		this.tradeStartTime = tradeStartTime;
		this.tBamount = tBamount;

	}

	@SuppressWarnings("rawtypes")
	@Parameters
	public static Collection prepareData() {
		 Object[][] object = { { "5000", "1", "", "3", "4", "1", "15000" }};

//		Object[][] object = { { "5000", "1", "", "3", "4", "1", "15000" }, { "5000", "1", "", "3", "4", "2", "15000", "5072", "14993.25", "45" } };
		
//		Object[][] object = { { "5000", "1", "", "3", "4", "2", "15000" } };
		
		return Arrays.asList(object);
	}

	@Test
	public void PzTb_Day_A1Test() {
		System.out.println("===={CASE1: 按天配资、今天交易./明天交易 、发标的、投标、开账户、资金划转、}====");
		/*
		 * step0、执行前检查配资人、债权人的账户余额情况
		 */
		BigDecimal pre_pz_available = hlfb.getCurrencyAccountValue(applyPzMobile, "available");
		BigDecimal pre_tb_available = hlfb.getCurrencyAccountValue(tbPzMobile_Va, "available");
		BigDecimal pre_plat_avialable = hlfb.getPlat_Huobi_accountValue("available");
		/*
		 * step1 、配资申请
		 */
		String financing_contract_sn = hlfb.pzApplyPostDay(margin, usedateUnit, tradeAccountId, ruleId, usedate, tradeStartTime, applyPzMobile, Password);
		String financing_contract_id = HttpSqlPeizi.getFinancingContract(financing_contract_sn, "financing_contract_id");
		String p2p_contract_id = HttpSqlPeizi.getP2p_Contract(financing_contract_id, "p2p_contract_id");
		System.out.println("day_financing_contract_id: " + financing_contract_id);
		System.out.println("day_p2p_contract_id is: " + p2p_contract_id);

		/*
		 * step2、发布标的、投标、操盘账户开通、资金划转、派息计息
		 */
		hlbb.backNormalFlow(p2p_contract_id, financing_contract_id, tBamount, tbPzMobile_Va, Password);

		BigDecimal af_pz_available = hlfb.getCurrencyAccountValue(applyPzMobile, "available");
		BigDecimal af_tb_available = hlfb.getCurrencyAccountValue(tbPzMobile_Va, "available");
		BigDecimal af_plat_avialable = hlfb.getPlat_Huobi_accountValue("available");
		/*
		 * step3、配资人、债权人、平台账户的变化情况
		 */
		BigDecimal act_pz_change = cn.sub(pre_pz_available, af_pz_available);
		BigDecimal act_tb_change = cn.sub(pre_tb_available, af_tb_available);
		BigDecimal act_pt_change = cn.sub(af_plat_avialable, pre_plat_avialable);
		System.out.println("1、act配资人： 可用余额减少: " + act_pz_change);
		System.out.println("2、act 投标人： 可用余额减少：" + act_tb_change);
		System.out.println("3、act 平台：    可用余额增加 ：" + act_pt_change);

		// ---- 天配资、数据变化
		/*
		 * step4、数据变化 1、债权人减少：保证金 +服务费(Total服务费=18/天 * <4天>)：5000+72=5072
		 * 2、投标人減少：投标金、得到的利息<18元/天*0.3.375*2=13.5>:15000-13.5=14986.5 3、平台增加：
		 * 利息：<18元/天 0.625 * 4 > ==45
		 */

		/*
		 * step4、数据变化 1、债权人减少：保证金 +服务费(Total服务费=18/天 * <4天>)：5000+72=5072
		 * 2、投标人減少：投标金、得到的利息<18元/天*0.375=6.75>:15000-6.75=14993.25 3、平台增加：
		 * 利息：<18元/天 0.625 * 4 > ==45
		 */

		// PzCalcMoney.getDayFee(margin, ruleId, "0", "0", usedate);

		/*
		 * step4、配资、投标人、平台的期待变化
		 */
		BigDecimal exp_pz_change = PzCalcMoney.getDayFee_pz_less(margin, ruleId, "0", usedate);
		BigDecimal exp_tb_change = PzCalcMoney.getDayFee_service_tb_less(margin, ruleId, "0", p2pInterestByDay, tradeStartTime, tBamount);
		BigDecimal exp_pt_change = PzCalcMoney.getDayFee_service_pt_more(margin, ruleId, "0", usedate, p2pInterestByDay);
		System.out.println("1、exp配资人： 可用余额减少: " + exp_pz_change);
		System.out.println("2、exp投标人： 可用余额减少：" + exp_tb_change);
		System.out.println("3、exp平台：    可用余额增加 ：" + exp_pt_change);

		/*
		 * step5、断言
		 */
		assertEquals(0, exp_pz_change.compareTo(act_pz_change));
		assertEquals(0, exp_tb_change.compareTo(act_tb_change));
		assertEquals(0, exp_pt_change.compareTo(act_pt_change));

	}

}
