package com.xuanniu.http.login.mzautocase;

/** 
 * @author sungq 
 * @version 创建时间：2015年6月1日 下午8:22:47 
 * 类说明 
 */

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.xuanniu.http.login.mfbusiness.HttpLoginBackBusiness;
import com.xuanniu.http.login.mfbusiness.HttpLoginFrontBusiness;
import com.xuanniu.http.sql.HttpSqlPeizi;
import com.xuanniu.util.CalcNum;

public class AppendMargin_MonTest {
	HttpLoginFrontBusiness hlfb = new HttpLoginFrontBusiness();
	HttpLoginBackBusiness hlbb = new HttpLoginBackBusiness();
	CalcNum cn = new CalcNum();
	// ----账户
	String applyPzMobile = "99913920003";
	String applyPzPassword = "123456";

	String tbPzMobile_Va = "99913920002";
	String tbPzPassword_Va = "123456";

	String tbPzMobile_Vb = "99913920003";
	String tbPzPassword_Vb = "123456";
	// ----配资参数
	String sn = " ";
	String margin = "5000";
	String usedateUnit = "2";
	String tradeAccountId = "";
	String ruleId = "12";
	String usedate = "4";
	String interest = "1.9";
	String tradeAccountApplyWay = "1";
	// ----
	String tBamount = "15000";
	String appendMargin = "600";

	@Test
	public void AppendMargin_MonTest() throws InterruptedException {
		System.out.println("====月配资、基本流、 追加保证金====");
		/*
		 * step0、执行前检查配资人、债权人的账户余额情况
		 */
		BigDecimal pre_pz_available = hlfb.getCurrencyAccountValue(applyPzMobile, "available");
		BigDecimal pre_tb_available = hlfb.getCurrencyAccountValue(tbPzMobile_Va, "available");
		BigDecimal pre_plat_avialable = hlfb.getPlat_Huobi_accountValue("available");

		/*
		 * step1 、配资申请
		 */
		String financing_contract_sn = hlfb.pzApplyPostMonth(sn, margin, usedateUnit, tradeAccountId, ruleId, usedate, interest, tradeAccountApplyWay, applyPzMobile, applyPzPassword);

		String financing_contract_id = HttpSqlPeizi.getFinancingContract(financing_contract_sn, "financing_contract_id");
		String p2p_contract_id = HttpSqlPeizi.getP2p_Contract(financing_contract_id, "p2p_contract_id");
		System.out.println("mon_financing_contract_id: " + financing_contract_id);
		System.out.println("mon_p2p_contract_id is: " + p2p_contract_id);

		/*
		 * step2、发布标的、投标、操盘账户开通、资金划转、派息计息
		 */
		hlbb.backNormalFlow(p2p_contract_id, financing_contract_id, tBamount, tbPzMobile_Va, tbPzPassword_Va);

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

		/*
		 * step4、数据变化
		 */
		BigDecimal exp_pz_change = new BigDecimal("5420");
		BigDecimal exp_tb_change = new BigDecimal("14715");
		BigDecimal exp_pl_change = new BigDecimal("135");

		/*
		 * step5、断言
		 */
		assertEquals(0, exp_pz_change.compareTo(act_pz_change));
		assertEquals(0, exp_tb_change.compareTo(act_tb_change));
		assertEquals(0, exp_pl_change.compareTo(act_pl_change));

		/*
		 * step6：追加保证金、金额:6000
		 */

		String trade_account_id = HttpSqlPeizi.getFinancingContract(financing_contract_sn, "trade_account_id");
		String pre_margin = HttpSqlPeizi.getTrade_Account(trade_account_id, "margin");
		hlfb.appendAssurance(trade_account_id, appendMargin, applyPzMobile, applyPzPassword);
		
		Thread.sleep(1000);
		BigDecimal af1_pz_available = hlfb.getCurrencyAccountValue(applyPzMobile, "available");
		/*
		 * step7: 取得之后操盘账户、保证金额度
		 */
		String af_margin = HttpSqlPeizi.getTrade_Account(trade_account_id, "margin");

		BigDecimal pre_num_margin = new BigDecimal(pre_margin);
		BigDecimal af_num_margin = new BigDecimal(af_margin);

		BigDecimal act_num_margin = cn.sub(af_num_margin, pre_num_margin);

		System.out.println("实际保证金增加：" + act_num_margin);

		BigDecimal exp_num_margin = new BigDecimal(appendMargin);
		assertEquals(0, exp_num_margin.compareTo(act_num_margin));
		
		
		//=============================================================================
		/*
		 * step8、前台申请终止
		 */
		hlfb.terminalApplyPost(financing_contract_sn, applyPzMobile, applyPzPassword);
		Thread.sleep(2000);
		
		/*
		 * step9、后台终止合约
		 */
		hlbb.jieSuanPost(financing_contract_id);
		/*
		 * step10、检查配资人资金的最终数额
		 */
		BigDecimal af2_pz_available = hlfb.getCurrencyAccountValue(applyPzMobile, "available");
		BigDecimal act2_pz_change = cn.sub(af2_pz_available, af1_pz_available);
		System.out.println("配资人配资、追加保证金后、实际、变化的金额是： "+act2_pz_change);
		

	}

}
