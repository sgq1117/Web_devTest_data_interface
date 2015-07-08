package com.xuanniu.http.OfflineVerifyTest;

/** 
 * @author sungq 
 * @version 创建时间：2015年6月9日 下午2:22:45 
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
public class PzTb_Mon_CT {

	/**
	 * 月配资、满标、前台申请终止、后台终止
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
	// ----
	String p2pInterestByDay = "0.4375";
	String financingPunishInterest = "0.2";
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
	// -----
	private String pZchangeamount;
	private String tBchangeamount;
	private String pTchangeamount;

	// ----------
	public PzTb_Mon_CT(String sn, String margin, String usedateUnit, String tradeAccountId, String ruleId, String usedate, String interest, String tradeAccountApplyWay, String tBamount, String pZchangeamount, String tBchangeamount, String pTchangeamount) {
		this.sn = sn;
		this.margin = margin;
		this.usedateUnit = usedateUnit;
		this.tradeAccountId = tradeAccountId;
		this.ruleId = ruleId;
		this.usedate = usedate;
		this.interest = interest;
		this.tradeAccountApplyWay = tradeAccountApplyWay;
		this.tBamount = tBamount;
		// -------------
		this.pZchangeamount = pZchangeamount;
		this.tBchangeamount = tBchangeamount;
		this.pTchangeamount = pTchangeamount;
	}

	@SuppressWarnings("rawtypes")
	@Parameters
	public static Collection prepareData() {
		Object[][] object = { { "", "5000", "2", "", "12", "4", "1.9", "1", "15000", "672", "379.5", "292.5" } };
		return Arrays.asList(object);
	}

	@Test
	public void PzTb_Mon_CT_Test() throws InterruptedException {
		System.out.println("===={CASE2_2: 月配资 、发标的、投标、开账户、资金划转、初次满标、前台不申请终止、后台终止}====");
		/*
		 * step0、执行前检查配资人、债权人的账户余额情况
		 */
		BigDecimal pre_pz_available = hlfb.getCurrencyAccountValue(applyPzMobile, "available");
		BigDecimal pre_tb_available = hlfb.getCurrencyAccountValue(tbPzMobile_Va, "available");
		BigDecimal pre_plat_avialable = hlfb.getPlat_Huobi_accountValue("available");
		/*
		 * step1 、配资申请
		 */
		String financing_contract_sn = hlfb.pzApplyPostMonth(sn, margin, usedateUnit, tradeAccountId, ruleId, usedate, interest, tradeAccountApplyWay, applyPzMobile, Password);

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
		 * step3、后台满标终止
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

		System.out.println("1、配资人： 可用余额减少: " + act_pz_change);
		System.out.println("2、投标人： 可用余额增加：" + act_tb_change);
		System.out.println("3、平台：    可用余额增加 ：" + act_pt_change);

		/*
		 * step5 1、投资人减少：所有（已付利息+服务费)+(未付利息+服务费)的20%:
		 * (135+285)+(135+285)*3*0.2=420+252 = 672 2、配资人增加: 已经得到（利息）+（罚息所得）285+
		 * (135+285)*3*0.2*0.375 = 285+94.5=379.5 3、平台增加: 已经得到的(服务费) + (罚息所得)
		 * 135 +　(135+285)*3*0.2*0.625 = 292.5
		 */
		// PzCalcMoney.getMonFee_output(margin, ruleId, "0", "0", interest);
		// BigDecimal exp_pz_change = new BigDecimal(pZchangeamount);
		// BigDecimal exp_tb_change = new BigDecimal(tBchangeamount);
		// BigDecimal exp_pl_change = new BigDecimal(pTchangeamount);

		// ----服务费+利息+罚息
		BigDecimal exp_pz_basic = PzCalcMoney.getMonFee_serveFee_interestFee(margin, ruleId, interest, "0");
		BigDecimal exp_pz_fx = PzCalcMoney.getMonFee_fx_pz(margin, ruleId, interest, "0", usedate, financingPunishInterest);
		// System.out.print("pz:利息+服务费: "+exp_pz_basic);
		// System.out.println(" pz:利息+服务费罚息部分:"+exp_pz_fx);
		BigDecimal exp_pz_change = exp_pz_basic.add(exp_pz_fx);

		// ----利息+罚息部分
		BigDecimal exp_tb_interest = PzCalcMoney.getMonInterestFee_mragin(margin, ruleId, interest);
		BigDecimal exp_tb_fx = PzCalcMoney.getMonFee_fx_tb(exp_pz_fx, p2pInterestByDay);
		// System.out.print("tb月利息: "+exp_tb_interest);
		// System.out.println(" ====>tb罚息:"+exp_tb_fx);
		BigDecimal exp_tb_change = exp_tb_interest.add(exp_tb_fx);

		// ---服务费+罚息部分
		BigDecimal exp_pt_serve = PzCalcMoney.getMonServiceFee_margin(margin, ruleId, "0");
		BigDecimal exp_pt_fx = PzCalcMoney.getMonFee_fx_pt(exp_pz_fx, exp_tb_fx);
		// System.out.print("pt月服务费:"+exp_pt_serve);
		// System.out.println(" ====>pt 罚息:"+exp_pt_fx);
		BigDecimal exp_pt_change = exp_pt_serve.add(exp_pt_fx);
		/*
		 * step6、断言
		 */
		assertEquals(0, exp_pz_change.compareTo(act_pz_change));
		assertEquals(0, exp_tb_change.compareTo(act_tb_change));
		assertEquals(0, exp_pt_change.compareTo(act_pt_change));

	}

}
