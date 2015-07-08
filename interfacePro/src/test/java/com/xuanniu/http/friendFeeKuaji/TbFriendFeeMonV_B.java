package com.xuanniu.http.friendFeeKuaji;

/** 
 * @author sungq 
 * @version 创建时间：2015年6月13日 下午1:10:40 
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
import com.xuanniu.util.PzCalcMoney;

@RunWith(Parameterized.class)
public class TbFriendFeeMonV_B {

	HttpLoginFrontBusiness hlfb = new HttpLoginFrontBusiness();
	HttpLoginBackBusiness hlbb = new HttpLoginBackBusiness();
	CalcNum cn = new CalcNum();
	// ----账户-----
	// ----被推荐人、配资
	String applyPzMobile = "99913920001";
	// String applyPzMobile = "99913920004";
	// String tbPzMobile_Va = "99913920002";
	// String tbPzMobile_Vb = "99913920003";
	// ----推荐人
	String mobileT = "99913920021";
	// -------------------------------
	// ----投标人
	String mobile1 = "99913920031";
	// String mobile1 = "99913920032";
	// String mobile1 = "99913920033";
	String Password = "123456";
	// ----
	String p2pInterestByDay = "0.4375";
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
	public TbFriendFeeMonV_B(String sn, String margin, String usedateUnit, String tradeAccountId, String ruleId, String usedate, String interest, String tradeAccountApplyWay, String tBamount) {
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
		Object[][] object = { { "", "5000", "2", "", "11", "4", "1.9", "1", "10000" } };
		return Arrays.asList(object);
	}

	@Test
	public void TbFriendFeeMonV_BTest() throws InterruptedException {
		System.out.println("===={第二个投标人、投标 1w、  推荐人收 (1-0.8)w 管理费*7% + 0.8w管理费* 8%}=={投标人和被推荐人是同一个人}==");
		/*
		 * step0、执行前检查配资人、债权人的账户余额情况
		 */
		BigDecimal pre_pz_available = hlfb.getCurrencyAccountValue(applyPzMobile, "available");
		BigDecimal pre_tb_available = hlfb.getCurrencyAccountValue(mobile1, "available");
		BigDecimal pre_plat_avialable = hlfb.getPlat_Huobi_accountValue("available");

		/*
		 * step1 、配资申请
		 */
		String financing_contract_sn = hlfb.pzApplyPostMonth(sn, margin, usedateUnit, tradeAccountId, ruleId, usedate, interest, tradeAccountApplyWay, applyPzMobile, Password);
		String financing_contract_id = HttpSqlPeizi.getFinancingContract(financing_contract_sn, "financing_contract_id");
		String p2p_contract_id = HttpSqlPeizi.getP2p_Contract(financing_contract_id, "p2p_contract_id");
		System.out.println("mon_financing_contract_id: " + financing_contract_id);
		System.out.println("mon_p2p_contract_id is: " + p2p_contract_id);
		/*
		 * step2、发布标的、投标、操盘账户开通、资金划转、派息计息
		 */
		hlbb.backNormalFlow(p2p_contract_id, financing_contract_id, tBamount, mobile1, Password);
		/*
		 * step3、执行后检查配资人、债权人、推荐人、的账户余额情况
		 */
		BigDecimal af1_pz_available = hlfb.getCurrencyAccountValue(applyPzMobile, "available");
		BigDecimal af1_tb_available = hlfb.getCurrencyAccountValue(mobile1, "available");
		BigDecimal af1_plat_avialable = hlfb.getPlat_Huobi_accountValue("available");
		// ----推荐人
		BigDecimal af1_tj_available = hlfb.getCurrencyAccountValue(mobileT, "available");

		BigDecimal act_pz_change = cn.sub(pre_pz_available, af1_pz_available);
		BigDecimal act_tb_change = cn.sub(pre_tb_available, af1_tb_available);
		BigDecimal act_pt_change = cn.sub(af1_plat_avialable, pre_plat_avialable);

		System.out.println("1、act配资人： 可用余额减少: " + act_pz_change);
		System.out.println("2、act 投标人： 可用余额减少：" + act_tb_change);
		System.out.println("3、act 平台：    可用余额增加 ：" + act_pt_change);
		/*
		 * step4、配资人、债权人、平台账户的变化情况
		 */

		BigDecimal exp_pz_change = PzCalcMoney.getMonFee_pz_less(margin, ruleId, "0", "0", interest);
		BigDecimal exp_tb_change = PzCalcMoney.getMonFee_tb_less(margin, ruleId, "0", "0", interest);
		BigDecimal exp_pt_change = PzCalcMoney.getMonFee_pt_more(margin, ruleId, "0");

		System.out.println("a、exp 配资人： 可用余额减少: " + exp_pz_change);
		System.out.println("b、exp 期待投标人： 可用余额减少：" + exp_tb_change);
		System.out.println("c、exp 平台：" + exp_pt_change);

		/*
		 * step5、断言
		 */
		assertEquals(0, exp_pz_change.compareTo(act_pz_change));
		assertEquals(0, exp_tb_change.compareTo(act_tb_change));
		assertEquals(0, exp_pt_change.compareTo(act_pt_change));

		/*
		 * step6、返还佣金
		 */
		Thread.sleep(2000);
		hlbb.returnYongjinGet();
		// ============================返还佣金部分计算===========================
		/*
		 * step7、平台减少的部分 和 推荐人 增加的部分想等
		 */
		BigDecimal af2_plat_avialable = hlfb.getPlat_Huobi_accountValue("available");
		BigDecimal af2_tj_avialable = hlfb.getCurrencyAccountValue(mobileT, "available");

		// ---------平台减少 和 推荐人增加 想等
		BigDecimal act_plat_change = cn.sub(af1_plat_avialable, af2_plat_avialable);
		BigDecimal act_tj_change = cn.sub(af2_tj_avialable, af1_tj_available);
		System.out.println("平台减少： " + act_plat_change);
		System.out.println("推荐人增加: " + act_tj_change);

		// ---------期待减少的具体数值-首先计算借款额度
		BigDecimal borrowAll = PzCalcMoney.getBorrow(margin, ruleId, "financing_rate");
		// ----borrow_1:一档配资总额 ：|| borrow_A1：一档还可计算服务费用的额度
		BigDecimal borrow_1 = new BigDecimal("10000");
		BigDecimal borrow_A1 = borrow_1.subtract(new BigDecimal("8000"));

		// ----第一档减少的比率、金额
		BigDecimal mon_fee_service_A1 = PzCalcMoney.getMonFee_service_borrow(borrow_A1, ruleId, "0");
		BigDecimal exp_service_change_A1 = mon_fee_service_A1.multiply(new BigDecimal("0.07"));

		// ----第二档减少的比率、金额
		BigDecimal borrow_B1 = borrowAll.subtract(borrow_A1);
		BigDecimal mon_fee_service_B1 = PzCalcMoney.getMonFee_service_borrow(borrow_B1, ruleId, "0");
		BigDecimal exp_service_change_B1 = mon_fee_service_B1.multiply(new BigDecimal("0.08"));

		// ----总共减少
		BigDecimal exp_service_change = exp_service_change_A1.add(exp_service_change_B1);
		System.out.println("付给推荐人的服务费是： " + exp_service_change);

		// ----断言
		assertEquals(0, act_plat_change.compareTo(act_tj_change));
		assertEquals(0, act_plat_change.compareTo(exp_service_change));
		assertEquals(0, act_tj_change.compareTo(exp_service_change));

	}

}
