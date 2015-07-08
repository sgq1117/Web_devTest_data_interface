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
 * @version 创建时间：2015年6月11日 上午10:41:55 类说明
 */

@RunWith(Parameterized.class)
public class PzTb_Z_Test {
	HttpLoginFrontBusiness hlfb = new HttpLoginFrontBusiness();
	HttpLoginBackBusiness hlbb = new HttpLoginBackBusiness();
	CalcNum cn = new CalcNum();

	// ----账户-----
	// ----被推荐人、配资
	// String mobile1 = "99913920033";
	// String mobile1 = "99913920032";
//	String mobile1 = "99913920001";
	String mobile1 = "99913920004";
	// ----推荐人
	// String mobileT = "99913920002";
	// -------------------------------
	// ----投标
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
	public PzTb_Z_Test(String sn, String margin, String usedateUnit, String tradeAccountId, String ruleId, String usedate, String interest, String tradeAccountApplyWay, String tBamount) {
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
		Object[][] object = { { "", "2000", "2", "", "10", "4", "1.9", "1", "2000" } };
		return Arrays.asList(object);
	}
	
	@Test
	public void PzTb_Z() throws InterruptedException {
		System.out.println("配资人-本金490000、配资980000 ");
		/*
		 * step0、执行前检查配资人、债权人的账户余额情况
		 */
		BigDecimal pre_pz_available = hlfb.getCurrencyAccountValue(mobile1, "available");
		BigDecimal pre_tb_available = hlfb.getCurrencyAccountValue(tbPzMobile_Va, "available");
		BigDecimal pre_plat_avialable = hlfb.getPlat_Huobi_accountValue("available");

		/*
		 * step1 、配资申请
		 */
		String financing_contract_sn = hlfb.pzApplyPostMonth(sn, margin, usedateUnit, tradeAccountId, ruleId, usedate, interest, tradeAccountApplyWay, mobile1, Password);
		String financing_contract_id = HttpSqlPeizi.getFinancingContract(financing_contract_sn, "financing_contract_id");
		String p2p_contract_id = HttpSqlPeizi.getP2p_Contract(financing_contract_id, "p2p_contract_id");
		System.out.println("mon_financing_contract_id: " + financing_contract_id);
		System.out.println("mon_p2p_contract_id is: " + p2p_contract_id);

		/*
		 * step2、发布标的、投标、操盘账户开通、资金划转、派息计息
		 */
		hlbb.backNormalFlow(p2p_contract_id, financing_contract_id, tBamount, tbPzMobile_Va, Password);

//		/*
//		 * step3、执行后检查配资人、债权人、推荐人、的账户余额情况
//		 */
//		BigDecimal af1_pz_available = hlfb.getCurrencyAccountValue(mobile1, "available");
//		BigDecimal af1_tb_available = hlfb.getCurrencyAccountValue(tbPzMobile_Va, "available");
//		BigDecimal af1_plat_avialable = hlfb.getPlat_Huobi_accountValue("available");
//
//		/*
//		 * step4、配资人、债权人、平台账户的变化情况
//		 */
//		BigDecimal act_pz_change = cn.sub(pre_pz_available, af1_pz_available);
//		BigDecimal act_tb_change = cn.sub(pre_tb_available, af1_tb_available);
//		BigDecimal act_pl_change = cn.sub(af1_plat_avialable, pre_plat_avialable);
//		// System.out.println("1、配资人： 可用余额减少: " + act_pz_change);
//		System.out.println("2、实际投标人： 可用余额减少：" + act_tb_change);
//		// System.out.println("3、平台：    可用余额增加 ：" + act_pl_change);
//
//		// BigDecimal borrow = PzCalcMoney.getMonFee_service(margin, ruleId,
//		// "0");
//		BigDecimal exp_pz_change = PzCalcMoney.getMonFee_pz_less(margin, ruleId, "0", "0", interest);
//		BigDecimal exp_tb_change = PzCalcMoney.getMonFee_tb_less(margin, ruleId, "0", "0", interest);
//		BigDecimal exp_pt_change = PzCalcMoney.getMonFee_pt_more(margin, ruleId, "0");
//
//		// System.out.println("a、配资人： 可用余额减少: "+exp_pz_change);
//		System.out.println("b、期待投标人： 可用余额减少：" + exp_tb_change);
//		// System.out.println("c、投标人： 可用余额减少："+exp_pt_change);
//
//		/*
//		 * step5、断言
//		 */
//		assertEquals(0, exp_pz_change.compareTo(act_pz_change));
//		assertEquals(0, exp_tb_change.compareTo(act_tb_change));
//		assertEquals(0, exp_pt_change.compareTo(act_pl_change));

	}
}
