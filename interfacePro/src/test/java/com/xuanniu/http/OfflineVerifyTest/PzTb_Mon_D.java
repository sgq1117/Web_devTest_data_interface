package com.xuanniu.http.OfflineVerifyTest;

/** 
 * @author sungq 
 * @version 创建时间：2015年6月9日 下午3:26:56 
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
public class PzTb_Mon_D {

	/**
	 * 月配资、初次满标、续期未满标、后台取消合约
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
	private String xQtbamount ="10000";
	// -----
	private String pZchangeamount;
	private String tBchangeamount;
	private String pTchangeamount;

	// ----------
	public PzTb_Mon_D(String sn, String margin, String usedateUnit, String tradeAccountId, String ruleId, String usedate, String interest, String tradeAccountApplyWay, String tBamount, String xQtbamount ,String pZchangeamount, String tBchangeamount, String pTchangeamount) {
		this.sn = sn;
		this.margin = margin;
		this.usedateUnit = usedateUnit;
		this.tradeAccountId = tradeAccountId;
		this.ruleId = ruleId;
		this.usedate = usedate;
		this.interest = interest;
		this.tradeAccountApplyWay = tradeAccountApplyWay;
		this.tBamount = tBamount;
		this.xQtbamount = xQtbamount;
		
		// -------------
		this.pZchangeamount = pZchangeamount;
		this.tBchangeamount = tBchangeamount;
		this.pTchangeamount = pTchangeamount;
	}

	@SuppressWarnings("rawtypes")
	@Parameters
	public static Collection prepareData() {
		Object[][] object = { { "", "5000", "2", "", "12", "4", "1.9", "1", "15000", "5000", "5420", "14715", "135" } };
		return Arrays.asList(object);
	}

	@Test
	public void PzTb_Mon_D_Test() throws InterruptedException {
		System.out.println("===={CASE:月配资、初次满标、续期未满标、后台取消合约}====");
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
		// String financing_contract_sn = hlfb.pzApplyPostMonth(sn, margin,
		// usedateUnit, tradeAccountId, ruleId, usedate, interest,
		// tradeAccountApplyWay, applyPzMobile, Password);

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
		 * step3、 前台续期申请
		 */
		String xq_financing_contract_sn = hlfb.xqHyMonApplyPost(financing_contract_sn, "4", applyPzMobile, Password);

		/*
		 * step4、月续期合约审核通过
		 */
		hlbb.xqPassPost(xq_financing_contract_sn);
		/*
		 * step5、发布标的
		 */
		String xq_financing_contract_id = HttpSqlPeizi.getFinancingContract(xq_financing_contract_sn, "financing_contract_id");
		String xq_p2p_contract_id = HttpSqlPeizi.getP2p_Contract(xq_financing_contract_id, "p2p_contract_id");
		hlbb.sendBiaoDiGet(xq_p2p_contract_id);

		System.out.println("    月续期xq_financing_contract_sn: " + xq_financing_contract_sn);
		System.out.println("	月续期xq_financing_contract_id: " + xq_financing_contract_id);
		System.out.println("	月续期xq_p2p_contract_id: " + xq_p2p_contract_id);

		/*
		 * step10、投标
		 */
		
		hlfb.pzToubiaoGet(xq_financing_contract_id, xq_p2p_contract_id, xQtbamount, tbPzMobile_Va, Password);

		/*
		 * step11、未满标、取消
		 */
		hlbb.cancelNoManBiaoPost(xq_financing_contract_id);

		BigDecimal af_pz_available = hlfb.getCurrencyAccountValue(applyPzMobile, "available");
		BigDecimal af_tb_available = hlfb.getCurrencyAccountValue(tbPzMobile_Va, "available");
		BigDecimal af_plat_avialable = hlfb.getPlat_Huobi_accountValue("available");

		BigDecimal act_pz_change = cn.sub(pre_pz_available, af_pz_available);
		BigDecimal act_tb_change = cn.sub(pre_tb_available, af_tb_available);
		BigDecimal act_pt_change = cn.sub(af_plat_avialable, pre_plat_avialable);

//		System.out.println("1、配资人： 可用余额减少: " + act_pz_change);
//		System.out.println("2、投标人： 可用余额减少：" + act_tb_change);
//		System.out.println("3、平台：    可用余额增加 ：" + act_pt_change);
		
		/*
		 * 1、配资人减少：保证金+利息+服务费 = 5000+135+285 = 5420
		 * 2、投资人减少： 投标资金-利息=     15000-285=14715
		 * 3、平台增加： 服务费= 135
		 */
//		PzCalcMoney.getMonFee(margin, ruleId, "0", "0", interest);
		
		BigDecimal exp_pz_change = PzCalcMoney.getMonFee_pz_less(margin, ruleId, "0", "0", interest);
		BigDecimal exp_tb_change = PzCalcMoney.getMonFee_tb_less(margin, ruleId, "0", "0", interest);
		BigDecimal exp_pt_change = PzCalcMoney.getMonFee_pt_more(margin, ruleId, "0");

//		System.out.println("1、exp 配资人： 可用余额减少: " + exp_pz_change);
//		System.out.println("2、exp 投标人： 可用余额减少：" + exp_tb_change);
//		System.out.println("3、exp 平台：    可用余额增加 ：" + exp_pt_change);
		/*
		 * step8、断言
		 */
		assertEquals(0, exp_pz_change.compareTo(act_pz_change));
		assertEquals(0, exp_tb_change.compareTo(act_tb_change));
		assertEquals(0, exp_pt_change.compareTo(act_pt_change));
		
	}

}
