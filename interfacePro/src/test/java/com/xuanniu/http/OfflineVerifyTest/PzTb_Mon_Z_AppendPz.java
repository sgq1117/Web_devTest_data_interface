package com.xuanniu.http.OfflineVerifyTest;

/** 
 * @author sungq 
 * @version 创建时间：2015年6月15日 下午3:12:10 
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
public class PzTb_Mon_Z_AppendPz {

	// ---------
	HttpLoginFrontBusiness hlfb = new HttpLoginFrontBusiness();
	HttpLoginBackBusiness hlbb = new HttpLoginBackBusiness();
	CalcNum cn = new CalcNum();
	// ----账户-----
//	String applyPzMobile = "99913920001";
	 String applyPzMobile = "99913920004";
	 
	String tbPzMobile_Va = "99913920002", tbPzMobile_Vb = "99913920003";
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
	public PzTb_Mon_Z_AppendPz(String sn, String margin, String usedateUnit, String tradeAccountId, String ruleId, String usedate, String interest, String tradeAccountApplyWay, String tBamount) {
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
		Object[][] object = { { "", "5000", "2", "", "12", "4", "1.9", "1", "15000" } };
		return Arrays.asList(object);
	}

	@Test
	public void PzTb_Mon_AppendPzTest() {
		System.out.println("==Step1=={月配资、追加配资:同一个操盘账户下下挂两个配资合约}====");
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
		System.out.println("mon_financing_contract_id: " + financing_contract_id);
		System.out.println("mon_p2p_contract_id is: " + p2p_contract_id);
		/*
		 * step2、发布标的、投标、操盘账户开通、资金划转、派息计息
		 */
		hlbb.backNormalFlow(p2p_contract_id, financing_contract_id, tBamount, tbPzMobile_Va, Password);

		/*
		 * step3、执行前检查配资人、债权人的账户余额情况
		 */
		BigDecimal af1_pz_available = hlfb.getCurrencyAccountValue(applyPzMobile, "available");
		BigDecimal af1_tb_available = hlfb.getCurrencyAccountValue(tbPzMobile_Va, "available");
		BigDecimal af1_plat_avialable = hlfb.getPlat_Huobi_accountValue("available");
		/*
		 * step4、配资人、债权人、平台账户的变化情况
		 */
		BigDecimal act_pz_change = cn.sub(pre_pz_available, af1_pz_available);
		BigDecimal act_tb_change = cn.sub(pre_tb_available, af1_tb_available);
		BigDecimal act_pt_change = cn.sub(af1_plat_avialable, pre_plat_avialable);
		
		 System.out.println("1、act 配资人： 可用余额减少: " + act_pz_change);
		 System.out.println("2、act 投标人： 可用余额减少：" + act_tb_change);
		 System.out.println("3、act 平台：    可用余额增加 ：" + act_pt_change);

		// PzCalcMoney.getMonFee(margin, ruleId, "0", "0", interest);
		/**
		 * step4.1、数据变化 1、债权人减少：保证金 、利息、服务费：5000+285+135=5420
		 * 2、投標人減少：投标金、得到的利息:15000-285=14715.0000 3、平台增加： 利息：135
		 */

		BigDecimal exp_pz_change = PzCalcMoney.getMonFee_pz_less(margin, ruleId, "0", "0", interest);
		BigDecimal exp_tb_change = PzCalcMoney.getMonFee_tb_less(margin, ruleId, "0", "0", interest);
		BigDecimal exp_pt_change = PzCalcMoney.getMonFee_pt_more(margin, ruleId, "0");

		 System.out.println("1、exp 配资人： 可用余额减少: " + exp_pz_change);
		 System.out.println("2、exp 投标人： 可用余额减少：" + exp_tb_change);
		 System.out.println("3、exp 平台：    可用余额增加 ：" + exp_pt_change);

		/*
		 * step5、断言
		 */
		assertEquals(0, exp_pz_change.compareTo(act_pz_change));
		assertEquals(0, exp_tb_change.compareTo(act_tb_change));
		assertEquals(0, exp_pt_change.compareTo(act_pt_change));
		
		/*
		 * step6、追加配资、要带上trade_account
		 */
		String trade_account_id = HttpSqlPeizi.getFinancingContract(financing_contract_sn, "trade_account_id");
		System.out.println("==Step2=={月配资、带上已有操盘账户、追加配资}====");
		String append_financing_contract_sn = hlfb.pzApplyPostMonth(sn, margin, usedateUnit, trade_account_id, ruleId, usedate, interest, tradeAccountApplyWay, applyPzMobile, Password);
		String append_financing_contract_id = HttpSqlPeizi.getFinancingContract(append_financing_contract_sn, "financing_contract_id");
		String append_p2p_contract_id = HttpSqlPeizi.getP2p_Contract(append_financing_contract_id, "p2p_contract_id");
		System.out.println("mon_append_financing_contract_id: " + append_financing_contract_id);
		System.out.println("mon_apend_p2p_contract_id is: " + append_p2p_contract_id);
		
		/*
		 * step7、发布标的、投标、操盘账户开通、资金划转、派息计息
		 */
		hlbb.backNormalFlow(append_p2p_contract_id, append_financing_contract_id, tBamount, tbPzMobile_Va, Password);

		/*
		 * step8、追加配资后资金变化情况
		 */
		BigDecimal af2_pz_available = hlfb.getCurrencyAccountValue(applyPzMobile, "available");
		BigDecimal af2_tb_available = hlfb.getCurrencyAccountValue(tbPzMobile_Va, "available");
		BigDecimal af2_plat_avialable = hlfb.getPlat_Huobi_accountValue("available");

		BigDecimal act2_pz_change = cn.sub(af1_pz_available, af2_pz_available);
		BigDecimal act2_tb_change = cn.sub(af1_tb_available, af2_tb_available);
		BigDecimal act2_pt_change = cn.sub(af2_plat_avialable, af1_plat_avialable);
		
		System.out.println("1、act2 追加配资配资人： 可用余额减少: " + act2_pz_change);
		System.out.println("2、act2 追加配资投标人： 可用余额减少：" + act2_tb_change);
		System.out.println("3、act2 追加配资平台：    可用余额增加 ：" + act2_pt_change);
		
		/*
		 * step9、断言
		 */
		assertEquals(0, exp_pz_change.compareTo(act2_pz_change));
		assertEquals(0, exp_tb_change.compareTo(act2_tb_change));
		assertEquals(0, exp_pt_change.compareTo(act2_pt_change));
		
		
		
	}

}
