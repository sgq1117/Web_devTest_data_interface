package com.xuanniu.http.friendFee;

/** 
 * @author sungq 
 * @version 创建时间：2015年6月13日 下午4:27:33 
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
public class Tb_FriendFeeMonMultiV_F {
	HttpLoginFrontBusiness hlfb = new HttpLoginFrontBusiness();
	HttpLoginBackBusiness hlbb = new HttpLoginBackBusiness();
	CalcNum cn = new CalcNum();
	// ----账户-----
	// ----被推荐人、配资
	String applyPzMobile = "99913920001";
	// String applyPzMobile = "99913920004";
	// String tbPzMobile_Va = "99913920002";
	// String tbPzMobile_Vb = "99913920003";
	// ----推荐人
	String mobileT = "99913920021";
	// -------------------------------
	// ----投标人
	String mobile1 = "99913920031";
	String mobile2 = "99913920032";
	String mobile3 = "99913920033";
	String mobile4 = "99913920034";
	// String mobile1 = "99913920035";

	String Password = "123456";
	// ----
	String p2pInterestByDay = "0.4375";
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
	public Tb_FriendFeeMonMultiV_F(String sn, String margin, String usedateUnit, String tradeAccountId, String ruleId, String usedate, String interest, String tradeAccountApplyWay, String tBamount) {
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
		Object[][] object = { { "", "230000", "2", "", "13", "4", "1.9", "1", "920000" } };
		return Arrays.asList(object);
	}

	@Test
	public void Tb_FriendFeeMonMultiV_F() throws InterruptedException {
		System.out.println("===={本金23w、4倍配资、配资92w，4个人进行投标、每个人投标23w、每个人收23W*10%的管理费}====");
		/*
		 * step0、执行前检查配资人、债权人的账户余额情况
		 */
		// BigDecimal pre_pz_available =
		// hlfb.getCurrencyAccountValue(applyPzMobile, "available");
		// BigDecimal pre_tb_available = hlfb.getCurrencyAccountValue(mobile1,
		// "available");
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
		 * step2、发布标的
		 */
		hlbb.sendBiaoDiGet(p2p_contract_id);
		/*
		 * step3、投标
		 */
		hlfb.pzToubiaoGet(financing_contract_id, p2p_contract_id, margin, mobile1, Password);
		hlfb.pzToubiaoGet(financing_contract_id, p2p_contract_id, margin, mobile2, Password);
		hlfb.pzToubiaoGet(financing_contract_id, p2p_contract_id, margin, mobile3, Password);
		hlfb.pzToubiaoGet(financing_contract_id, p2p_contract_id, margin, mobile4, Password);
		Thread.sleep(1000);
		/*
		 * step4、开通账户、资金划转
		 */
		hlbb.backAccountBill(financing_contract_id);
		/*
		 * step4.1、资金变化、平台act
		 */
		BigDecimal af1_plat_avialable = hlfb.getPlat_Huobi_accountValue("available");
		BigDecimal act_pt_change = cn.sub(af1_plat_avialable, pre_plat_avialable);
		System.out.println("3、act 平台：    可用余额增加 ：" + act_pt_change);
		/*
		 * step4.2、资金变化、平台exp
		 */
		// ----期待平台增加的部分
		BigDecimal exp_pt_change = PzCalcMoney.getMonFee_pt_more(margin, ruleId, "0");
		System.out.println("3、exp 平台：" + exp_pt_change);

		// ----计算之前推荐人的金额
		BigDecimal af1_tj_available = hlfb.getCurrencyAccountValue(mobileT, "available");
		// -------------
		Thread.sleep(2000);
		hlbb.returnYongjinGet();

		// ============================返还佣金部分计算===========================
		/*
		 * step7、平台减少的部分 和 推荐人 增加的部分想等
		 */
		BigDecimal af2_plat_avialable = hlfb.getPlat_Huobi_accountValue("available");
		BigDecimal af2_tj_avialable = hlfb.getCurrencyAccountValue(mobileT, "available");
		// ---------平台减少 和 推荐人增加 想等
		BigDecimal act_plat_change = cn.sub(af1_plat_avialable, af2_plat_avialable);
		BigDecimal act_tj_change = cn.sub(af2_tj_avialable, af1_tj_available);
		System.out.println("平台减少： " + act_plat_change);
		System.out.println("推荐人增加: " + act_tj_change);

		// ---------期待减少的具体数值
		BigDecimal mon_fee_service = PzCalcMoney.getMonFee_service_margin_pt(margin, ruleId, "0");
		BigDecimal exp_service_change = mon_fee_service.multiply(new BigDecimal(usedate)).multiply(new BigDecimal("0.1"));
		System.out.println("付给他推荐人的服务费: " + exp_service_change);

		// -----断言-----------
		assertEquals(0, act_plat_change.compareTo(act_tj_change));
		assertEquals(0, act_plat_change.compareTo(exp_service_change));
		assertEquals(0, act_tj_change.compareTo(exp_service_change));

	}

}
