package com.xuanniu.http.OfflineVerifyTest; 
/** 
* @author sungq 
* @version 创建时间：2015年6月11日 下午1:56:43 
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

@RunWith(Parameterized.class)
public class PzTb_Z_willTradeAccount {

	HttpLoginFrontBusiness hlfb = new HttpLoginFrontBusiness();
	HttpLoginBackBusiness hlbb = new HttpLoginBackBusiness();
	CalcNum cn = new CalcNum();

	// ----账户-----
	// ----被推荐人、配资
	// String mobile1 = "99913920033";
	// String mobile1 = "99913920032";
	String mobile1 = "99913920004";
	// ----推荐人
	// String mobileT = "99913920002";
	// -------------------------------
	// ----投标
	String tbPzMobile_Va = "99913920002";
	String tbPzMobile_Vb = "99913920003";

	String Password = "123456";
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
	public PzTb_Z_willTradeAccount(String sn, String margin, String usedateUnit, String tradeAccountId, String ruleId, String usedate, String interest, String tradeAccountApplyWay, String tBamount) {
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
		Object[][] object = { { "", "49000", "2", "", "11", "4", "1.9", "1", "98000" } };
		return Arrays.asList(object);
	}
	@Test
	public void PzTb_Z_willTradeAccountTest() {
		System.out.println(" 待开通操盘账户 ");
		/*
		 * step0、执行前检查配资人、债权人的账户余额情况
		 */
		BigDecimal pre_pz_available = hlfb.getCurrencyAccountValue(mobile1, "available");
		BigDecimal pre_tb_available = hlfb.getCurrencyAccountValue(tbPzMobile_Va, "available");
		BigDecimal pre_plat_avialable = hlfb.getPlat_Huobi_accountValue("available");

		/*
		 * step1 、配资申请
		 */
		String financing_contract_sn = hlfb.pzApplyPostMonth(sn, margin, usedateUnit, tradeAccountId, ruleId, usedate, interest, tradeAccountApplyWay, mobile1, Password);
		String financing_contract_id = HttpSqlPeizi.getFinancingContract(financing_contract_sn, "financing_contract_id");
		String p2p_contract_id = HttpSqlPeizi.getP2p_Contract(financing_contract_id, "p2p_contract_id");
		System.out.println("mon_financing_contract_id: " + financing_contract_id);
		System.out.println("mon_p2p_contract_id is: " + p2p_contract_id);

		/*
		 * step2、发布标的、投标、
		 */
		hlbb.backWillCreateAccount(p2p_contract_id, financing_contract_id, tBamount, tbPzMobile_Va, Password);
	}

}


