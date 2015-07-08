package com.xuanniu.http.login.mzautocase;

/** 
 * @author sungq 
 * @version 创建时间：2015年5月28日 下午2:52:26 
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

public class XqTbV3_1MonTest {

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
	String xQtbamount = "10000";

	@Test
	public void test() {
		System.out.println("===={CASE3_1:月配资、初次满标、续期未满标、后台取消合约}====");
		/*
		 * step0、执行前检查配资人、债权人、平台的账户余额情况
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
		System.out.println("	初次mon_financing_contract_id: " + financing_contract_id);
		System.out.println("	初次mon_p2p_contract_id is: " + p2p_contract_id);

		
		/*
		 * step2、发布标的、投标、操盘账户开通、资金划转、派息计息
		 */
		hlbb.backNormalFlow(p2p_contract_id, financing_contract_id, tBamount, tbPzMobile_Va, tbPzPassword_Va);

		/*
		 * step7、 前台续期申请
		 */
		String xq_financing_contract_sn = hlfb.xqHyMonApplyPost(financing_contract_sn, "4", applyPzMobile, applyPzPassword);

		/*
		 * step8、月续期合约审核通过
		 */
		hlbb.xqPassPost(xq_financing_contract_sn);

		/*
		 * step9、发布标的
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
		hlfb.pzToubiaoGet(xq_financing_contract_id, xq_p2p_contract_id, xQtbamount, tbPzMobile_Va, tbPzPassword_Va);

		/*
		 * step11、未满标、取消
		 */
		hlbb.cancelNoManBiaoPost(xq_financing_contract_id);

		/*
		 * step12、配资人、债权人、平台账户的变化情况 1、初次配资成功、配资人扣除： 保证金+服务费+利息 2、初次配资成功、投标人扣除：
		 * 投标资金 3、平台得到： 服务费
		 * 
		 * 续期未满比标、取消合约 1、配资人、保证金+服务费 原路返还 2、投标人、配资资金 原路返还 4、平台：不便
		 * 
		 * 最终的数据变化： 1、配资人减少： 保证金+服务费+利息 5000+285+60= 5345 2、投资人减少： 投标资金+ 首月利息 =
		 * 15000-285=14175 3、平台： 服务费= 60
		 */

		BigDecimal af_pz_available = hlfb.getCurrencyAccountValue(applyPzMobile, "available");
		BigDecimal af_tb_available = hlfb.getCurrencyAccountValue(tbPzMobile_Va, "available");
		BigDecimal af_plat_avialable = hlfb.getPlat_Huobi_accountValue("available");

		BigDecimal act_pz_change = cn.sub(pre_pz_available, af_pz_available);
		BigDecimal act_tb_change = cn.sub(pre_tb_available, af_tb_available);
		BigDecimal act_pl_change = cn.sub(af_plat_avialable, pre_plat_avialable);
		System.out.println("1、配资人： 可用余额减少: " + act_pz_change);
		System.out.println("2、投标人： 可用余额减少：" + act_tb_change);
		System.out.println("3、平台：    可用余额增加 ：" + act_pl_change);

		/*
		 * step13、断言比较
		 */
		BigDecimal exp_pz_change = new BigDecimal("5345");
		BigDecimal exp_tb_change = new BigDecimal("14715");
		BigDecimal exp_pl_change = new BigDecimal("60");
		assertEquals(0, exp_pz_change.compareTo(act_pz_change));
		assertEquals(0, exp_tb_change.compareTo(act_tb_change));
		assertEquals(0, exp_pl_change.compareTo(act_pl_change));
	}

}
