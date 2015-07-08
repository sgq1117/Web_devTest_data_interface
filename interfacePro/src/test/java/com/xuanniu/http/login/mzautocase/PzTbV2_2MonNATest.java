package com.xuanniu.http.login.mzautocase;

/** 
 * @author sungq 
 * @version 创建时间：2015年5月28日 下午12:15:03 
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

public class PzTbV2_2MonNATest {
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

	@Test
	public void pzMonManTerminalTest() {
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
		String financing_contract_sn = hlfb.pzApplyPostMonth(sn, margin, usedateUnit, tradeAccountId, ruleId, usedate, interest, tradeAccountApplyWay, applyPzMobile, applyPzPassword);

		String financing_contract_id = HttpSqlPeizi.getFinancingContract(financing_contract_sn, "financing_contract_id");
		String p2p_contract_id = HttpSqlPeizi.getP2p_Contract(financing_contract_id, "p2p_contract_id");
		System.out.println("mon_financing_contract_id: " + financing_contract_id);
		System.out.println("mon_p2p_contract_id is: " + p2p_contract_id);

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
		 * step7、后台满标终止
		 */
		
		hlbb.jieSuanPost(financing_contract_id);
		
		BigDecimal af_pz_available = hlfb.getCurrencyAccountValue(applyPzMobile, "available");
		BigDecimal af_tb_available = hlfb.getCurrencyAccountValue(tbPzMobile_Va, "available");
		BigDecimal af_plat_avialable = hlfb.getPlat_Huobi_accountValue("available");
		
		
		/*
		 * step8、执行前检查配资人、债权人的账户余额情况
		 */
		BigDecimal act_pz_change = cn.sub(af_pz_available, pre_pz_available);
		BigDecimal act_tb_change = cn.sub(af_tb_available, pre_tb_available);
		BigDecimal act_pl_change = cn.sub(af_plat_avialable, pre_plat_avialable);
		
		System.out.println("1、配资人： 可用余额减少: " + act_pz_change);
		System.out.println("2、投标人： 可用余额增加：" + act_tb_change);
		System.out.println("3、平台：    可用余额增加 ：" + act_pl_change);
	
		/*
		 * step9
		 * 1、期待投资人可用月余额减少：首月利息＋服务费＋罚息(所有未付利息20%)= 285 + 60 + (285 * 0.2 * 3) = 516
		 * 2、期待债权人可用余额增加：　首月利息　＋　罚息　(285*0.2*3*0.3) 　=　285 + 51.3 =336.3
		 * 3、期待平台收入增加:        服务费　＋　罚息(285*0.2*3*0.7)     =  60 + 119.7 = 179.7
		 */
		
		BigDecimal exp_pz_change = new BigDecimal("-516");
		BigDecimal exp_tb_change = new BigDecimal("336.3");
		BigDecimal exp_pl_change = new BigDecimal("179.7");

		/*
		 * step10、 断言
		 */
		assertEquals(0, exp_pz_change.compareTo(act_pz_change));
		assertEquals(0, exp_tb_change.compareTo(act_tb_change));
		assertEquals(0, exp_pl_change.compareTo(act_pl_change));

	}

}
