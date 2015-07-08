package com.xuanniu.http.login.method;

/** 
 * @author sungq 
 * @version 创建时间：2015年5月24日 下午3:31:44 
 * 类说明 
 */

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.xuanniu.http.sql.HttpSqlPeizi;

public class HttpLoginFrontGPTest {
	HttpLoginFrontGP frontgp = new HttpLoginFrontGP();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("====Front Begin Test Begin====");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("====Front End Test Begin====");
	}

	// @Test
	public void pzApplyDayPostTest() {
		System.out.println("===={CASE1: 配资按天申请}====");

		// -----Step1: url、参数具体化、调用
		String url = "http://www.xuanniu.com/v1/api0/trade/disposeTreaty";
		String mobile = "99913926577";
		String password = "1234567";
		// ----post请求参数构造
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("margin", "5000"));
		params.add(new BasicNameValuePair("usedateUnit", "1"));
		params.add(new BasicNameValuePair("tradeAccountId", ""));
		params.add(new BasicNameValuePair("ruleId", "3"));
		params.add(new BasicNameValuePair("usedate", "4"));
		params.add(new BasicNameValuePair("tradeStartTime", "2"));
		// ---- step2: 请求执行
		String jsonStr = frontgp.frontActionPost(url, params, mobile, password);
		// System.out.println("按天申请配资： "+jsonStr);
		// ---- step3: 合约sn
		JSONObject jsob = JSONObject.fromObject(jsonStr);
		String financing_contract_sn = (String) jsob.get("body");
		// System.out.println("financing_contract_sn: "+financing_contract_sn);
		
//		return financing_contract_sn;
		
		String financing_contract_id = HttpSqlPeizi.getFinancingContract(financing_contract_sn, "financing_contract_id");
		String p2p_contract_id = HttpSqlPeizi.getP2p_Contract(financing_contract_id, "p2p_contract_id");
		System.out.println("p2p_contract_id is: " + p2p_contract_id);

	}

	@Test
	public void pzApplyMonthTest() {
		System.out.println("===={CASE1: 配资按月申请}====");
		// -----Step1: url、参数具体化、调用
		String url = "http://www.xuanniu.com/v1/api0/trade/disposeTreaty";
		String mobile = "99913926577";
		String password = "1234567";
		// ----post请求参数构造
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("sn", ""));
		params.add(new BasicNameValuePair("margin", "5000"));
		params.add(new BasicNameValuePair("usedateUnit", "2"));
		params.add(new BasicNameValuePair("tradeAccountId", ""));
		params.add(new BasicNameValuePair("ruleId", "12"));
		params.add(new BasicNameValuePair("usedate", "4"));
		params.add(new BasicNameValuePair("interest", "1.9"));
		params.add(new BasicNameValuePair("tradeAccountApplyWay", "1"));
		// ---- step2: 执行
		String jsonStr = frontgp.frontActionPost(url, params, mobile, password);
		// System.out.println("按月申请配资： "+jsonStr);
		// ---Step2: json数据获取
		/*
		 * 形成json对象、记性转换
		 */
		JSONObject jsob = JSONObject.fromObject(jsonStr);
		String financing_contract_sn = (String) jsob.get("body");
		// System.out.println("financing_contract_sn: "+financing_contract_sn);

		String financing_contract_id = HttpSqlPeizi.getFinancingContract(financing_contract_sn, "financing_contract_id");
		String p2p_contract_id = HttpSqlPeizi.getP2p_Contract(financing_contract_id, "p2p_contract_id");
		System.out.println("p2p_contract_id is: " + p2p_contract_id);

	}

//	@Test
	public void pzToubiaoGetTest() {
		System.out.println("===={CASE2: 配资投标}====");

		String financingContractId = "1086";
		String p2p_contract_id = "984";
		String amount = "10000";

		String mobile = "77713926582";
		String password = "1234567";

		String url = "http://www.xuanniu.com/v1/api/p2pBuy?p2pContractId=" + p2p_contract_id + "&financingContractId=" + financingContractId + "&amount=" + amount;
		String tbResult = frontgp.frontActionGet(url, mobile, password);
		// System.out.println("tbResult: "+tbResult);
	}

	// @Test
	public void moneyChangeTest() {
		/*
		 * 融资人、债权人、火币账户的变化增加
		 */
		BigDecimal pre_avialable_a = HttpSqlPeizi.getCurrency_AcountValue("99913926577", "available");
		BigDecimal pre_balance_a = HttpSqlPeizi.getCurrency_AcountValue("99913926577", "balance");

		BigDecimal pre_avialable_b = HttpSqlPeizi.getCurrency_AcountValue("77713926582", "available");
		BigDecimal pre_balance_b = HttpSqlPeizi.getCurrency_AcountValue("77713926582", "balance");

	}

//	@Test
	public void pzXqApplyTest() {
		String mobile = "77713926582";
		String password = "1234567";
		
		String url = "http://www.xuanniu.com/v1/api/contract/continueInfo";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("sn", "PZ00001063"));
		params.add(new BasicNameValuePair("usedate", "2"));
		// ---- step2: 执行
		String jsonStr = frontgp.frontActionPost(url, params, mobile, password);
		System.out.println("jsonStr: "+jsonStr);
	}

}
