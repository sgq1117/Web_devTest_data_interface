package com.xuanniu.http.login.mzautocase;

/** 
 * @author sungq 
 * @version 创建时间：2015年5月27日 下午2:36:34 
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

public class PzTbV1MonTest {
	HttpLoginFrontBusiness hlfb = new HttpLoginFrontBusiness();
	HttpLoginBackBusiness hlbb = new HttpLoginBackBusiness();
	CalcNum cn = new CalcNum();
	// ----账户
	String applyPzMobile = "99913920001";
	String applyPzPassword = "123456";

	String tbPzMobile_Va = "99913920002";
	String tbPzPassword_Va = "123456";

	String tbPzMobile_Vb = "99913920003";
	String tbPzPassword_Vb = "123456";
	// ----配资参数
	String sn = "";
	String margin = "5000";
	String usedateUnit = "2";
	String tradeAccountId = "";
	String ruleId = "12";
	String usedate = "4";
	String interest = "1.9";
	String tradeAccountApplyWay = "1";
	// ----
	String tBamount = "15000";


	/**
	 *月配资、初次基本流ok
	 */
	@Test
	public void pzMonthFlowTest() {
		System.out.println("===={CASE: 按月配资 、发标的、投标、开账户、资金划转}====");
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
		

		/*
		 * step7、执行前检查配资人、债权人的账户余额情况
		 */
		BigDecimal af_pz_available = hlfb.getCurrencyAccountValue(applyPzMobile, "available");
		BigDecimal af_tb_available = hlfb.getCurrencyAccountValue(tbPzMobile_Va, "available");
		BigDecimal af_plat_avialable = hlfb.getPlat_Huobi_accountValue("available");
		/*
		 * step8、配资人、债权人、平台账户的变化情况
		 */
		BigDecimal act_pz_change = cn.sub(pre_pz_available, af_pz_available);
		BigDecimal act_tb_change = cn.sub(pre_tb_available, af_tb_available);
		BigDecimal act_pl_change = cn.sub(af_plat_avialable, pre_plat_avialable);
		System.out.println("1、配资人： 可用余额减少: " + act_pz_change);
		System.out.println("2、投标人： 可用余额减少：" + act_tb_change);
		System.out.println("3、平台：    可用余额增加 ：" + act_pl_change);
	
		/**
		 * step9、数据变化
		 * 1、债权人减少：保证金 、利息、服务费：5000+60+345=5345
		 * 2、投標人減少：投标金、得到的利息:15000-285=14715.0000
		 * 3、平台增加： 利息：60
		 */
		BigDecimal exp_pz_change = new BigDecimal("5345");
		BigDecimal exp_tb_change = new BigDecimal("14715");
		BigDecimal exp_pl_change = new BigDecimal("60");
		
		/*
		 * step10、断言
		 */
		assertEquals(0,exp_pz_change.compareTo(act_pz_change));
		assertEquals(0,exp_tb_change.compareTo(act_tb_change));
		assertEquals(0,exp_pl_change.compareTo(act_pl_change));

	}

}
