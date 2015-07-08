package com.xuanniu.http.OfflineVerifyTest;

/** 
 * @author sungq 
 * @version 创建时间：2015年6月9日 下午2:15:51 
 * 类说明 
 */

import static org.junit.Assert.assertEquals;

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
public class PzTb_Day_CT {

	/**
	 * 天配资、满标、前台不申请终止、后台终止
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
	// ---------------
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
	private String pZchangeamount;
	private String tBchangeamount;
	private String pTchangeamount;

	public PzTb_Day_CT(String margin, String usedateUnit, String tradeAccountId, String ruleId, String usedate, String tradeStartTime, String tBamount, String pZchangeamount, String tBchangeamount, String pTchangeamount) {
		this.margin = margin;
		this.usedateUnit = usedateUnit;
		this.tradeAccountId = tradeAccountId;
		this.ruleId = ruleId;
		this.usedate = usedate;
		this.tradeStartTime = tradeStartTime;
		this.tBamount = tBamount;
		this.pZchangeamount = pZchangeamount;
		this.tBchangeamount = tBchangeamount;
		this.pTchangeamount = pTchangeamount;
	}

	@SuppressWarnings("rawtypes")
	@Parameters
	public static Collection prepareData() {
		Object[][] object = { { "5000", "1", "", "3", "4", "1", "15000", "72", "27", "45" }, { "5000", "1", "", "3", "4", "2", "15000", "72", "27", "45" } };
		return Arrays.asList(object);

	}

	@Test
	public void PzTb_Day_CT_Test() throws InterruptedException {
		System.out.println("===={CASE2_2: 天配资 、发标的、投标、开账户、资金划转、初次满标、前台不申请终止、后台终止}====");
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
		Thread.sleep(1000);

		/*
		 * step3、满标后台取消标的、终止结算
		 */

		hlbb.jieSuanPost(financing_contract_id);
		BigDecimal af_pz_available = hlfb.getCurrencyAccountValue(applyPzMobile, "available");
		BigDecimal af_tb_available = hlfb.getCurrencyAccountValue(tbPzMobile_Va, "available");
		BigDecimal af_plat_avialable = hlfb.getPlat_Huobi_accountValue("available");

		/*
		 * step4、 配资人、债权人、平台账户的变化情况
		 */
		BigDecimal act_pz_change = cn.sub(pre_pz_available, af_pz_available);
		BigDecimal act_tb_change = cn.sub(af_tb_available, pre_tb_available);
		BigDecimal act_pt_change = cn.sub(af_plat_avialable, pre_plat_avialable);
		/*
		 * step5、
		 */
		System.out.println("1、配资人： 可用余额减少: " + act_pz_change);
		System.out.println("2、投标人： 可用余额增加：" + act_tb_change);
		System.out.println("3、平台：    可用余额增加 ：" + act_pt_change);
		/*
		 * step6、 1、期待配资人可用余额减少 服务费==>18*4=72 2、期待投标人可用余额增加
		 * 利息===>18元/天*0.3.375*4=27 3、期待平台货币账户余额增加 =====>18元/天 0.625 * 4=45
		 */
		// PzCalcMoney.getDayFee(margin, ruleId, "0", "0", usedate);

		BigDecimal exp_pz_change = PzCalcMoney.getDayFee_service_All(margin, ruleId, "0", usedate);
		BigDecimal exp_tb_change = PzCalcMoney.getDayFee_service_tb_more(margin, ruleId, "0", p2pInterestByDay, usedate);
		BigDecimal exp_pt_change = PzCalcMoney.getDayFee_service_pt_more(margin, ruleId, "0", usedate, p2pInterestByDay);
		/*
		 * step7、断言
		 */
		assertEquals(0, exp_pz_change.compareTo(act_pz_change));
		assertEquals(0, exp_tb_change.compareTo(act_tb_change));
		assertEquals(0, exp_pt_change.compareTo(act_pt_change));
	}

}
