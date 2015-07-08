package com.xuanniu.http.login.mzautocase;

/** 
 * @author sungq 
 * @version 创建时间：2015年5月27日 下午4:01:21 
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

/**
 * 天续期、初次满标后台终止
 * 
 * @author Administrator
 * 
 */
public class PzTbV2_2DayNATest {
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
	public void pzDayManTerminalTest() {
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
		 * step7、满标后台取消标的、终止结算
		 */

		hlbb.jieSuanPost(financing_contract_id);

		BigDecimal af_pz_available = hlfb.getCurrencyAccountValue(applyPzMobile, "available");
		BigDecimal af_tb_available = hlfb.getCurrencyAccountValue(tbPzMobile_Va, "available");
		BigDecimal af_plat_avialable = hlfb.getPlat_Huobi_accountValue("available");

		/*
		 * step8、配资人、债权人、平台账户的变化情况
		 */
		BigDecimal act_pz_change = cn.sub(af_pz_available, pre_pz_available);
		BigDecimal act_tb_change = cn.sub(af_tb_available, pre_tb_available);
		BigDecimal act_pl_change = cn.sub(af_plat_avialable, pre_plat_avialable);

		System.out.println("1、配资人： 可用余额减少: " + act_pz_change);
		System.out.println("2、投标人： 可用余额增加：" + act_tb_change);
		System.out.println("3、平台：    可用余额增加 ：" + act_pl_change);

		/*
		 * step9、 
		 * 1、期待配资人可用余额减少 服务费 36 
		 * 2、期待投标人可用余额增加 利息 10.8 
		 * 3、期待平台货币账户余额增加 25.2
		 */
		BigDecimal exp_pz_change = new BigDecimal("-36");
		BigDecimal exp_tb_change = new BigDecimal("10.8");
		BigDecimal exp_pl_change = new BigDecimal("25.2");

		/*
		 * step10、 断言
		 */
		assertEquals(0, exp_pz_change.compareTo(act_pz_change));
		assertEquals(0, exp_tb_change.compareTo(act_tb_change));
		assertEquals(0, exp_pl_change.compareTo(act_pl_change));
	}

}
