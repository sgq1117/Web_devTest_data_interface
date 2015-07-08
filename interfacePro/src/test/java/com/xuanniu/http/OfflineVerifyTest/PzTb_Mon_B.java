package com.xuanniu.http.OfflineVerifyTest;

/** 
 * @author sungq 
 * @version 创建时间：2015年6月9日 上午10:57:21 
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

import com.xuanniu.http.login.mfbusiness.HttpLoginBackBusiness;
import com.xuanniu.http.login.mfbusiness.HttpLoginFrontBusiness;
import com.xuanniu.http.sql.HttpSqlPeizi;
import com.xuanniu.util.CalcNum;

@RunWith(Parameterized.class)
public class PzTb_Mon_B {
	/**
	 * 月配资、 投标、 未满标取消标的
	 */
	// ---------
	HttpLoginFrontBusiness hlfb = new HttpLoginFrontBusiness();
	HttpLoginBackBusiness hlbb = new HttpLoginBackBusiness();
	CalcNum cn = new CalcNum();
	// ----账户-----
	String applyPzMobile = "99913920001";
	String tbPzMobile_Va = "99913920002";
	String tbPzMobile_Vb = "99913920003";
	String Password = "123456";
	// ----请求参数------
	private String sn;
	private String margin;
	private String usedateUnit;
	private String tradeAccountId;
	private String ruleId;
	private String usedate;
	private String interest;
	private String tradeAccountApplyWay;
	// ----投标金额
	private String tBamount;

	// ----
	public PzTb_Mon_B(String sn, String margin, String usedateUnit, String tradeAccountId, String ruleId, String usedate, String interest, String tradeAccountApplyWay, String tBamount) {
		this.sn = sn;
		this.margin = margin;
		this.usedateUnit = usedateUnit;
		this.tradeAccountId = tradeAccountId;
		this.ruleId = ruleId;
		this.usedate = usedate;
		this.interest = interest;
		this.tradeAccountApplyWay = tradeAccountApplyWay;
		this.tBamount = tBamount;
	}

	@SuppressWarnings("rawtypes")
	@Parameters
	public static Collection prepareData() {
		Object[][] object = { { "", "5000", "2", "", "12", "4", "1.9", "1", "11000" }, { "", "5000", "2", "", "12", "4", "1.9", "1", "12000" } };
		return Arrays.asList(object);
	}

	@Test
	public void PzTb_Mon_B_Test() {
		System.out.println("===={CASE: 按月配资 、发标的、投标 、未满标、取消标的 、资金变化}====");
		/*
		 * step0、执行前检查配资人、债权人的账户余额情况
		 */
		BigDecimal pre_pz_available = hlfb.getCurrencyAccountValue(applyPzMobile, "available");
		BigDecimal pre_tb_available = hlfb.getCurrencyAccountValue(tbPzMobile_Va, "available");
		BigDecimal pre_plat_avialable = hlfb.getPlat_Huobi_accountValue("available");
		String financing_contract_sn = hlfb.pzApplyPostMonth(sn, margin, usedateUnit, tradeAccountId, ruleId, usedate, interest, tradeAccountApplyWay, applyPzMobile, Password);
		/*
		 * step1 、配资申请
		 */
		String financing_contract_id = HttpSqlPeizi.getFinancingContract(financing_contract_sn, "financing_contract_id");
		String p2p_contract_id = HttpSqlPeizi.getP2p_Contract(financing_contract_id, "p2p_contract_id");
		System.out.println("mon_financing_contract_id: " + financing_contract_id);
		System.out.println("mon_p2p_contract_id is: " + p2p_contract_id);
		/*
		 * step2、发布标的、投标、操盘账户开通、资金划转、派息计息
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

		// ---- 月配资、数据变化
		/*
		 * step4、数据变化、所有数据不变；配资人、投标人、数据保持不变
		 */
		BigDecimal exp_all_change = new BigDecimal("0");

		/*
		 * step5、断言
		 */
		assertEquals(0, exp_all_change.compareTo(act_pz_change));
		assertEquals(0, exp_all_change.compareTo(act_tb_change));
		assertEquals(0, exp_all_change.compareTo(act_pl_change));

	}

}
