package com.xuanniu.http.OfflineVerifyTest;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.xuanniu.http.login.mfbusiness.HttpLoginBackBusiness;
import com.xuanniu.http.login.mfbusiness.HttpLoginFrontBusiness;
import com.xuanniu.http.sql.HttpSqlPeizi;
import com.xuanniu.util.CalcNum;
import com.xuanniu.util.PzCalcMoney;

/**
 * @author sungq
 * @version 创建时间：2015年6月9日 上午10:41:38 类说明
 */

@RunWith(Parameterized.class)
public class PzTb_Day_B {
	/**
	 * 天配资(配资、投标 、、资金划转 ok)、初次未满标、取消标的
	 */
	// ---------
	HttpLoginFrontBusiness hlfb = new HttpLoginFrontBusiness();
	HttpLoginBackBusiness hlbb = new HttpLoginBackBusiness();
	CalcNum cn = new CalcNum();
	// ----账户-----
//	String applyPzMobile = "99913920001";
	String applyPzMobile = "99913920004";
	String tbPzMobile_Va = "99913920002";
	String tbPzMobile_Vb = "99913920003";
	String Password = "123456";
	// ----请求参数------
	private String margin;
	private String usedateUnit;
	private String tradeAccountId;
	private String ruleId;
	private String usedate;
	private String tradeStartTime;
	// ----投标金额
	private String tBamount;

	public PzTb_Day_B(String margin, String usedateUnit, String tradeAccountId, String ruleId, String usedate, String tradeStartTime, String tBamount) {
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
		Object[][] object = { { "5000", "1", "", "3", "4", "1", "10000" }, { "5000", "1", "", "3", "4", "2", "10000" } };
		return Arrays.asList(object);
	}

	@Test
	public void PzTb_Day_B1Test() {
		System.out.println("===={CASE2_1: 按天配资 、发标的、投标、初次未满标、后台终止}====");
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
		 * step2、发标的、投标(不满)
		 */
		hlbb.backCancelBd(p2p_contract_id, financing_contract_id, tBamount, tbPzMobile_Va, Password);
		BigDecimal af_pz_available = hlfb.getCurrencyAccountValue(applyPzMobile, "available");
		BigDecimal af_tb_available = hlfb.getCurrencyAccountValue(tbPzMobile_Va, "available");
		BigDecimal af_plat_avialable = hlfb.getPlat_Huobi_accountValue("available");

		/*
		 * step3、配资人、债权人、平台账户的变化情况
		 */
		BigDecimal act_pz_change = cn.sub(pre_pz_available, af_pz_available);
		BigDecimal act_tb_change = cn.sub(pre_tb_available, af_tb_available);
		BigDecimal act_pl_change = cn.sub(af_plat_avialable, pre_plat_avialable);
		System.out.println("1、配资人： 可用余额减少: " + act_pz_change);
		System.out.println("2、投标人： 可用余额减少：" + act_tb_change);
		System.out.println("3、平台：    可用余额增加 ：" + act_pl_change);

		// ---- 天配资、数据变化
		/*
		 * step4、数据变化、所有数据不变；配资人、投标人、数据保持不变
		 */
		// PzCalcMoney.getDayFee(margin, ruleId, "0", "0", usedate);

		BigDecimal exp_all_change = new BigDecimal("0");
		/*
		 * step7、断言比较
		 */
		assertEquals(0, exp_all_change.compareTo(act_pz_change));
		assertEquals(0, exp_all_change.compareTo(act_tb_change));
		assertEquals(0, exp_all_change.compareTo(act_pl_change));

	}

}
