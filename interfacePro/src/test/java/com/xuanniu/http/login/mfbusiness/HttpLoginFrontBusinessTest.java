package com.xuanniu.http.login.mfbusiness;

/** 
 * @author sungq 
 * @version 创建时间：2015年5月25日 上午10:17:45 
 * 类说明 
 */

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.xuanniu.http.sql.HttpSqlPeizi;

public class HttpLoginFrontBusinessTest {
	HttpLoginFrontBusiness hlfb = new HttpLoginFrontBusiness();
	String applyPzMobile = "99913920001";
	String applyPzPassword = "1234567";
	
	String tbPzMobile_Va = "99913920002";
	String tbPzPassword_Va = "1234567";
	
	String tbPzMobile_Vb = "99913920003";
	String tbPzPassword_Vb = "1234567";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void monthApplyPostTest() {
		/**
		 * 1、月配资年化利率----------------： 0.2280 2、月配资利息费用----------------: 285.0000
		 * 3、月配资服务费用---------------：60.0000 4、月续期借款人需支付------------：345.00000000
		 */
		System.out.println("===={CASE1: 配资按月申请}====");
		String sn = " ";
		String margin = "5000";
		String usedateUnit = "2";
		String tradeAccountId = "";
		String ruleId = "12";
		String usedate = "4";
		String interest = "1.9";
		String tradeAccountApplyWay = "1";
		
		String financing_contract_sn = hlfb.pzApplyPostMonth(sn, margin, usedateUnit, tradeAccountId, ruleId, usedate, interest, tradeAccountApplyWay, applyPzMobile, applyPzPassword);
		String financing_contract_id = HttpSqlPeizi.getFinancingContract(financing_contract_sn, "financing_contract_id");
		String p2p_contract_id = HttpSqlPeizi.getP2p_Contract(financing_contract_id, "p2p_contract_id");
		System.out.println("financing_contract_sn: "+financing_contract_sn);
		System.out.println("p2p_contract_id is: " + p2p_contract_id);
	}

//	 @Test
	public void dayApplyPostTest() {
		System.out.println("===={CASE2: 配资按天申请}====");
		/**
		 * 1、Day_service_rate-------: 0.0006 2、Day_service_fee--------: 9.0000
		 * 3、天续期借款人还需支付------:36.00000000
		 */
		String margin = "5000";
		String usedateUnit = "1";
		String tradeAccountId = "";
		String ruleId = "3";
		String usedate = "4";
		String tradeStartTime = "2";

		hlfb.pzApplyPostDay(margin, usedateUnit, tradeAccountId, ruleId, usedate, tradeStartTime, applyPzMobile, applyPzPassword);
	}
	
//	@Test
	public void pzToubiaoTest() {
		String financingContractId = "1086";
		String p2p_contract_id = "984";
		String amount = "10000";

		String mobile = "77713926582";
		String password = "1234567";
		hlfb.pzToubiaoGet(financingContractId, p2p_contract_id, amount, tbPzMobile_Va, tbPzPassword_Va);
	}

}
