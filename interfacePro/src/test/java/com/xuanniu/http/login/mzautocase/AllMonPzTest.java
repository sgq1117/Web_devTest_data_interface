package com.xuanniu.http.login.mzautocase;

/** 
 * @author sungq 
 * @version 创建时间：2015年6月6日 下午3:05:10 
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
import com.xuanniu.util.CalcNum;

@RunWith(Parameterized.class)
public class AllMonPzTest {

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

	private String sn;
	private String margin;
	private String usedateUnit;
	private String tradeAccountId;
	private String ruleId;
	private String usedate;
	private String interest;
	private String tradeAccountApplyWay;
	private String freeze;

	public AllMonPzTest(String sn, String margin, String usedateUnit, String tradeAccountId, String ruleId, String usedate, String interest, String tradeAccountApplyWay, String freeze) {
		this.sn = sn;
		this.margin = margin;
		this.usedateUnit = usedateUnit;
		this.tradeAccountId = tradeAccountId;
		this.ruleId = ruleId;
		this.usedate = usedate;
		this.interest = interest;
		this.tradeAccountApplyWay = tradeAccountApplyWay;
		this.freeze = freeze;
	}

	@Parameters
	public static Collection prepareData() {
		Object[][] object = { { "", "5000", "2", "", "10", "4", "1.9", "1", "5115" }, 
				{ "", "5000", "2", "", "11", "4", "1.9", "1", "5260" }, 
				{ "", "5000", "2", "", "12", "4", "1.9", "1", "5420" }, 
				{ "", "5000", "2", "", "13", "4", "1.9", "1", "5560" },
				{ "", "5000", "2", "", "14", "4", "1.9", "1", "5700" } };
		return Arrays.asList(object);
	}

	@BeforeClass
	public static void enter() {
		System.out.println("===={CASE: 按月配资 类型测试}====");
	}
	
	/**
	 * 月配资、1~5倍、扣除利息、服务费、断言
	 */
	@Test
	public void pzTypeTest() {
		
		/*
		 * step0、执行前检查配资人、债权人的账户余额情况
		 */
		BigDecimal pre_pz_available = hlfb.getCurrencyAccountValue(applyPzMobile, "available");
		
		/*
		 * step1 、配资申请
		 */	
		BigDecimal rule =new BigDecimal(ruleId);
		BigDecimal financing_rate = rule.subtract(new BigDecimal("9"));
		System.out.print("本金: "+margin+" 月配资："+ financing_rate +" 倍配资 \n" );
		String financing_contract_sn = hlfb.pzApplyPostMonth(sn, margin, usedateUnit, tradeAccountId, ruleId, usedate, interest, tradeAccountApplyWay, applyPzMobile, applyPzPassword);

		/*
		 * step2、执行前检查配资人、债权人的账户余额情况
		 */
		BigDecimal af_pz_available = hlfb.getCurrencyAccountValue(applyPzMobile, "available");
		/*
		 * step3、
		 */
		BigDecimal act_pz_change = cn.sub(pre_pz_available, af_pz_available);
		/*
		 * freeze
		 */
		BigDecimal exp_freeze = new BigDecimal(freeze);
		assertEquals(0, exp_freeze.compareTo(act_pz_change));
//		System.out.print("合约sn: " + financing_contract_sn);
		System.out.println("配资人： 可用余额减少: " + act_pz_change);

	}

}
