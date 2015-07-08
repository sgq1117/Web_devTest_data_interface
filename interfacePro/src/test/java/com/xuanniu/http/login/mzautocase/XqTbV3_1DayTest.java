package com.xuanniu.http.login.mzautocase;

/** 
 * @author sungq 
 * @version 创建时间：2015年5月28日 下午2:49:12 
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

public class XqTbV3_1DayTest {
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

	String margin = "5000";
	String usedateUnit = "1";
	String tradeAccountId = "";
	String ruleId = "3";
	String usedate = "4";
	String tradeStartTime = "2";
	// ----
	String tBamount = "15000";

	@Test
	public void xqDayNomanTerminalTest() {
		System.out.println("===={CASE3_1:天配资、初次满标、续期、后台取消合约}====");

		/*
		 * step0、执行前检查配资人、债权人的账户余额情况
		 */
		BigDecimal pre_pz_available = hlfb.getCurrencyAccountValue(applyPzMobile, "available");
		BigDecimal pre_tb_available = hlfb.getCurrencyAccountValue(tbPzMobile_Va, "available");
		BigDecimal pre_plat_avialable = hlfb.getPlat_Huobi_accountValue("available");
		/*
		 * step1 、配资申请
		 */
		String financing_contract_sn = hlfb.pzApplyPostDay(margin, usedateUnit, tradeAccountId, ruleId, usedate, tradeStartTime, applyPzMobile, applyPzPassword);
		String financing_contract_id = HttpSqlPeizi.getFinancingContract(financing_contract_sn, "financing_contract_id");
		String p2p_contract_id = HttpSqlPeizi.getP2p_Contract(financing_contract_id, "p2p_contract_id");
		System.out.println("day_financing_contract_id: " + financing_contract_id);
		System.out.println("day_p2p_contract_id is: " + p2p_contract_id);
		/*
		 * sep2、发布标的
		 */
		hlbb.sendBiaoDiGet(p2p_contract_id);
		/*
		 * step3、投标
		 */
		hlfb.pzToubiaoGet(financing_contract_id, p2p_contract_id, tBamount, tbPzMobile_Va, tbPzPassword_Va);
		/*
		 * step4、开通操盘账户
		 */
		hlbb.ktAccountGet(p2p_contract_id);
		/*
		 * step5、资金划转
		 */
		hlbb.transferMondyGet(financing_contract_id);
		/*
		 * step6、用于派息计息操作
		 */
		hlbb.payBillGet();

		/*
		 * step7、 前台续期申请
		 */
		hlfb.xqHyApplyDayPost(financing_contract_sn, "4", applyPzMobile, applyPzPassword);

		/*
		 * step8、后台终止
		 */
		hlbb.jieSuanPost(financing_contract_id);
		// hlbb.cancelNoManBiaoPost(financing_contract_id);

		/*
		 * step8、配资人、债权人、平台账户的变化情况
		 */
		BigDecimal af_pz_available = hlfb.getCurrencyAccountValue(applyPzMobile, "available");
		BigDecimal af_tb_available = hlfb.getCurrencyAccountValue(tbPzMobile_Va, "available");
		BigDecimal af_plat_avialable = hlfb.getPlat_Huobi_accountValue("available");

		BigDecimal act_pz_change = cn.sub(af_pz_available, pre_pz_available);
		BigDecimal act_tb_change = cn.sub(af_tb_available, pre_tb_available);
		BigDecimal act_pl_change = cn.sub(af_plat_avialable, pre_plat_avialable);
		System.out.println("1、配资人： 可用余额减少: " + act_pz_change);
		System.out.println("2、投标人： 可用余额增加：" + act_tb_change);
		System.out.println("3、平台：    可用余额增加 ：" + act_pl_change);

		/*
		 * step9 
		 * 1、配资人: 保证金原路返还: 扣掉利息： 原来4天+续期4天共8天 （9元/天 * 8 =72元） 
		 * 2、债权人: 投标资金+ 利息、：72元*0.3 = 21.6 
		 * 3、平台 : 利息费用 : 72元*0.7 = 50.4
		 */
		BigDecimal exp_pz_change = new BigDecimal("-72.0000");
		BigDecimal exp_tb_change = new BigDecimal("21.6000");
		BigDecimal exp_pl_change = new BigDecimal("50.4");
		
		 /*
		 * step10
		 */
		 assertEquals(0, exp_pz_change.compareTo(act_pz_change));
		 assertEquals(0, exp_tb_change.compareTo(act_tb_change));
		 assertEquals(0, exp_pl_change.compareTo(act_pl_change));

	}
}
