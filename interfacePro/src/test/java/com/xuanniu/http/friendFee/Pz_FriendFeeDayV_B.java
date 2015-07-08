package com.xuanniu.http.friendFee; 
/** 
* @author sungq 
* @version 创建时间：2015年6月13日 下午3:04:26 
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
public class Pz_FriendFeeDayV_B {
	HttpLoginFrontBusiness hlfb = new HttpLoginFrontBusiness();
	HttpLoginBackBusiness hlbb = new HttpLoginBackBusiness();
	CalcNum cn = new CalcNum();
	// ----账户-----
	// ----被推荐人、配资
//	String mobile1 = "99913920031";
	 String mobile1 = "99913920032";
	// String mobile1 = "99913920033";
	// ----推荐人
	String mobileT = "99913920021";
	// -------------------------------
	// ----投标
	String tbPzMobile_Va = "99913920002";
	// String tbPzMobile_Vb = "99913920003";
	String Password = "123456";
	// ----
	String p2pInterestByDay = "0.4375";
	// ----请求参数------
	private String margin;
	private String usedateUnit;
	private String tradeAccountId;
	private String ruleId;
	private String usedate;
	private String tradeStartTime;
	// ----投标金额
	private String tBamount;

	// -----
	public Pz_FriendFeeDayV_B(String margin, String usedateUnit, String tradeAccountId, String ruleId, String usedate, String tradeStartTime, String tBamount) {
		this.margin = margin;
		this.usedateUnit = usedateUnit;
		this.tradeAccountId = tradeAccountId;
		this.ruleId = ruleId;
		this.usedate = usedate;
		this.tradeStartTime = tradeStartTime;
		this.tBamount = tBamount;
	}
	
	@SuppressWarnings("rawtypes")
	@Parameters
	public static Collection prepareData() {
		Object[][] object = { { "40000", "1", "", "2", "4", "1", "80000" } };
		return Arrays.asList(object);
	}
	@Test
	public void Pz_FriendFeeDayV_BTest() throws InterruptedException {
		System.out.println("===={第一个配资人、配资8w、推荐人收 8w管理费*16%}====");
		/*
		 * step0、执行前检查配资人、债权人的账户余额情况
		 */
		BigDecimal pre_pz_available = hlfb.getCurrencyAccountValue(mobile1, "available");
		BigDecimal pre_tb_available = hlfb.getCurrencyAccountValue(tbPzMobile_Va, "available");
		BigDecimal pre_plat_avialable = hlfb.getPlat_Huobi_accountValue("available");
		/*
		 * step1 、配资申请
		 */
		String financing_contract_sn = hlfb.pzApplyPostDay(margin, usedateUnit, tradeAccountId, ruleId, usedate, tradeStartTime, mobile1, Password);
		String financing_contract_id = HttpSqlPeizi.getFinancingContract(financing_contract_sn, "financing_contract_id");
		String p2p_contract_id = HttpSqlPeizi.getP2p_Contract(financing_contract_id, "p2p_contract_id");
		System.out.println("day_financing_contract_id: " + financing_contract_id);
		System.out.println("day_p2p_contract_id is: " + p2p_contract_id);

		/*
		 * step2、发布标的、投标、操盘账户开通、资金划转、派息计息
		 */
		hlbb.backNormalFlow(p2p_contract_id, financing_contract_id, tBamount, tbPzMobile_Va, Password);

		BigDecimal af1_pz_available = hlfb.getCurrencyAccountValue(mobile1, "available");
		BigDecimal af1_tb_available = hlfb.getCurrencyAccountValue(tbPzMobile_Va, "available");
		BigDecimal af1_plat_avialable = hlfb.getPlat_Huobi_accountValue("available");
		// ----推荐人
		BigDecimal af1_tj_available = hlfb.getCurrencyAccountValue(mobileT, "available");
		/*
		 * step3、配资人、债权人、平台账户的变化情况
		 */
		BigDecimal act_pz_change = cn.sub(pre_pz_available, af1_pz_available);
		BigDecimal act_tb_change = cn.sub(pre_tb_available, af1_tb_available);
		BigDecimal act_pt_change = cn.sub(af1_plat_avialable, pre_plat_avialable);
		System.out.println("1、act配资人： 可用余额减少: " + act_pz_change);
		System.out.println("2、act 投标人： 可用余额减少：" + act_tb_change);
		System.out.println("3、act 平台：    可用余额增加 ：" + act_pt_change);

		
		/*
		 *step4、配资、投标人、平台的期待变化 
		 */
		BigDecimal exp_pz_change = PzCalcMoney.getDayFee_pz_less(margin, ruleId, "0", usedate);
		BigDecimal exp_tb_change = PzCalcMoney.getDayFee_service_tb_less(margin, ruleId, "0", p2pInterestByDay, tradeStartTime, tBamount);
		BigDecimal exp_pt_change = PzCalcMoney.getDayFee_service_pt_more(margin, ruleId, "0", usedate, p2pInterestByDay);
		System.out.println("1、exp配资人： 可用余额减少: " + exp_pz_change);
		System.out.println("2、exp投标人： 可用余额减少：" + exp_tb_change);
		System.out.println("3、exp平台：    可用余额增加 ：" + exp_pt_change);

		/*
		 * step5、断言
		 */
//		assertEquals(0, exp_pz_change.compareTo(act_pz_change));
//		assertEquals(0, exp_tb_change.compareTo(act_tb_change));
//		assertEquals(0, exp_pt_change.compareTo(act_pt_change));

//		/*
//		 * step6、返还佣金
//		 */
		Thread.sleep(2000);
		hlbb.returnYongjinGet();

		// ============================返还佣金部分计算===========================
		/*
		 * step7、平台减少的部分 和 推荐人 增加的部分想等
		 */
		BigDecimal af2_plat_avialable = hlfb.getPlat_Huobi_accountValue("available");
		BigDecimal af2_tj_avialable = hlfb.getCurrencyAccountValue(mobileT, "available");

		BigDecimal act_plat_change = cn.sub(af1_plat_avialable, af2_plat_avialable);
		BigDecimal act_tj_change = cn.sub(af2_tj_avialable, af1_tj_available);
		System.out.println("平台减少： " + act_plat_change);
		System.out.println("推荐人增加: " + act_tj_change);

		// ---------期待减少的具体数值
		BigDecimal day_fee_service = PzCalcMoney.getDayFee_service_pt_more(margin, ruleId, "0", usedate, p2pInterestByDay);
		BigDecimal exp_service_change = day_fee_service.multiply(new BigDecimal("0.16"));
		System.out.println("付给他推荐人的服务费: " + exp_service_change);

		// -----断言-----------
		assertEquals(0, act_plat_change.compareTo(act_tj_change));
		assertEquals(0, act_plat_change.compareTo(exp_service_change));
		assertEquals(0, act_tj_change.compareTo(exp_service_change));
	}
}


