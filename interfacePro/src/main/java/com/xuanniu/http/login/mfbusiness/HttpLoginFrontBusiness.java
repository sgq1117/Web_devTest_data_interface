package com.xuanniu.http.login.mfbusiness;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.xuanniu.http.login.method.HttpLoginBackGP;
import com.xuanniu.http.login.method.HttpLoginBackGPTest;
import com.xuanniu.http.login.method.HttpLoginFrontGP;
import com.xuanniu.http.sql.HttpSqlPeizi;

/**
 * @author sungq
 * @version 创建时间：2015年5月25日 上午10:02:26 类说明
 */

public class HttpLoginFrontBusiness {
	HttpLoginFrontGP frontgp = new HttpLoginFrontGP();
//	HttpLoginBackBusiness hlbb = new HttpLoginBackBusiness();
	static String mobile = "99913920002";
	static String password = "123456";

	/**
	 * 按天配资申请
	 * 
	 * @param margin
	 * @param usedateUnit
	 * @param tradeAccountId
	 * @param ruleId
	 * @param usedate
	 * @param tradeStartTime
	 * @param mobile
	 * @param password
	 */
	public String pzApplyPostDay(String margin, String usedateUnit, String tradeAccountId, String ruleId, String usedate, String tradeStartTime, String mobile, String password) {
		System.out.println("base:按天配资");
//		hlbb.payBillGet();
		// -----Step1: url、参数具体化、调用
		String url = "https://www.xuanniu.com/v1/api0/trade/disposeTreaty";
		// ----post请求参数构造
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("margin", margin));
		params.add(new BasicNameValuePair("usedateUnit", usedateUnit));
		params.add(new BasicNameValuePair("tradeAccountId", tradeAccountId));
		params.add(new BasicNameValuePair("ruleId", ruleId));
		params.add(new BasicNameValuePair("usedate", usedate));
		params.add(new BasicNameValuePair("tradeStartTime", tradeStartTime));
		// ---- step2: 请求执行
		String jsonStr = frontgp.frontActionPost(url, params, mobile, password);
		 System.out.println("按天申请配资： "+jsonStr);
		// ---- step3: 合约sn
		System.out.println("pzApplyPostDay jsonStr: " + jsonStr);
		JSONObject jsob = JSONObject.fromObject(jsonStr);

		String financing_contract_sn = (String) jsob.get("body");
		return financing_contract_sn;
	}

	/**
	 * 按月配资申请
	 * 
	 * @param sn
	 * @param margin
	 * @param usedateUnit
	 * @param tradeAccountId
	 * @param ruleId
	 * @param usedate
	 * @param interest
	 * @param tradeAccountApplyWay
	 * @param mobile
	 * @param password
	 */
	public String pzApplyPostMonth(String sn, String margin, String usedateUnit, String tradeAccountId, String ruleId, String usedate, String interest, String tradeAccountApplyWay, String mobile, String password) {
//		hlbb.payBillGet();
		// -----Step1: url、参数具体化、调用
		String url = "https://www.xuanniu.com/v1/api0/trade/disposeTreaty";
		// ----post请求参数构造
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("sn", ""));
		params.add(new BasicNameValuePair("margin", margin));
		params.add(new BasicNameValuePair("usedateUnit", usedateUnit));
		params.add(new BasicNameValuePair("tradeAccountId", tradeAccountId));
		params.add(new BasicNameValuePair("ruleId", ruleId));
		params.add(new BasicNameValuePair("usedate", usedate));
		params.add(new BasicNameValuePair("interest", interest));
		params.add(new BasicNameValuePair("tradeAccountApplyWay", tradeAccountApplyWay));

		// ---- step2: 执行
		String jsonStr = frontgp.frontActionPost(url, params, mobile, password);
		System.out.println("按月申请配资： " + jsonStr);
		// ---- step3: 合约sn
		JSONObject jsob = JSONObject.fromObject(jsonStr);
		String financing_contract_sn = (String) jsob.get("body");
		return financing_contract_sn;
	}

	/**
	 * 月配资 、待红包
	 * 
	 * @param sn
	 * @param margin
	 * @param usedateUnit
	 * @param ruleId
	 * @param usedate
	 * @param interest
	 * @param tradeStartTime
	 * @param tradeAccountId
	 * @param tradeAccountApplyWay
	 * @param giftId
	 * @param mobile
	 * @param password
	 * @return
	 */
	public String pzApplyAllPostMon(String sn, String margin, String usedateUnit, String ruleId, String usedate, String interest, String tradeStartTime, String tradeAccountId, String tradeAccountApplyWay, String giftId, String mobile, String password) {
		System.out.println("======月配资、用红包========");
		String financing_contract_sn = null;
		// -----Step1: url、参数具体化、调用
		String url = "https://www.xuanniu.com/v1/api0/trade/disposeTreaty";
		// ----post请求参数构造
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("sn", sn));
		params.add(new BasicNameValuePair("margin", margin));
		params.add(new BasicNameValuePair("usedateUnit", usedateUnit));
		params.add(new BasicNameValuePair("ruleId", ruleId));
		params.add(new BasicNameValuePair("usedate", usedate));
		params.add(new BasicNameValuePair("interest", interest));
		params.add(new BasicNameValuePair("tradeStartTime", tradeStartTime));
		params.add(new BasicNameValuePair("tradeAccountId", tradeAccountId));
		params.add(new BasicNameValuePair("tradeAccountApplyWay", tradeAccountApplyWay));
		params.add(new BasicNameValuePair("giftId", giftId));
		// ----step2:执行
		String jsonStr = frontgp.frontActionPost(url, params, mobile, password);
		// ----step3:合约检查
		JSONObject jsob = JSONObject.fromObject(jsonStr);
		System.out.println("pzApplyAllPostMon jsob: " + jsob);
		financing_contract_sn = (String) jsob.get("body");
		System.out.println("Mon 合约sn: " + financing_contract_sn);
		return financing_contract_sn;
	}

	/**
	 * 天配资、
	 * 
	 * @param sn
	 * @param margin
	 * @param usedateUnit
	 * @param ruleId
	 * @param usedate
	 * @param interest
	 * @param tradeStartTime
	 * @param tradeAccountId
	 * @param giftId
	 * @param mobile
	 * @param password
	 * @return
	 */
	public String pzApplyAllPostDay(String sn, String margin, String usedateUnit, String ruleId, String usedate, String interest, String tradeStartTime, String tradeAccountId, String giftId, String mobile, String password) {
		System.out.println("======天配资、用红包========");
		String financing_contract_sn = null;
		// -----Step1: url、参数具体化、调用
		String url = "https://www.xuanniu.com/v1/api0/trade/disposeTreaty";
		// ----post请求参数构造
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("sn", sn));
		params.add(new BasicNameValuePair("margin", margin));
		params.add(new BasicNameValuePair("usedateUnit", usedateUnit));
		params.add(new BasicNameValuePair("ruleId", ruleId));
		params.add(new BasicNameValuePair("usedate", usedate));
		params.add(new BasicNameValuePair("interest", interest));
		params.add(new BasicNameValuePair("tradeStartTime", tradeStartTime));
		params.add(new BasicNameValuePair("tradeAccountId", tradeAccountId));
		params.add(new BasicNameValuePair("giftId", giftId));
		// ----step2:执行
		String jsonStr = frontgp.frontActionPost(url, params, mobile, password);
		// ----step3:合约检查
		JSONObject jsob = JSONObject.fromObject(jsonStr);
		financing_contract_sn = (String) jsob.get("body");
		System.out.println("Day 合约sn: " + financing_contract_sn);
		return financing_contract_sn;
	}

	/**
	 * 充值前生成操盘账户
	 * 
	 * @param mobile
	 * @param password
	 */
	public void indexSumary(String mobile, String password) {
		System.out.println("===={Begin 注册后生成操盘账户}====");
		String url = "https://www.xuanniu.com/v1/api/indexSummary";
		// ----post请求参数构造
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("pageIndex", "1"));
		params.add(new BasicNameValuePair("pageSize", "10"));
		
//		String indexSummary = frontgp.frontActionGet(url, mobile, password);
		String indexList = frontgp.frontActionPost(url, params, mobile, password); 
		System.out.println("===={End 注册后生成操盘账户}====");
	}

	/**
	 * 配资投标、不带红包的参数
	 * 
	 * @param financingContractId
	 * @param p2p_contract_id
	 * @param amount
	 * @param mobile
	 * @param password
	 */
	public void pzToubiaoGet(String financingContractId, String p2p_contract_id, String amount, String mobile, String password) {
		System.out.println("====投标====");
		String giftId = "";
		// String url = "https://www.xuanniu.com/v1/api0/p2pBuy?p2pContractId="
		// + p2p_contract_id + "&financingContractId=" + financingContractId +
		// "&amount=" + amount + "&giftId="+giftId;
		String url = "https://www.xuanniu.com/p2pBid?p2pContractId=" + p2p_contract_id + "&financingContractId=" + financingContractId + "&amount=" + amount + "&giftId=" + giftId;

		// System.out.println("url: "+url);
		String tbResult = frontgp.frontActionGet(url, mobile, password);
		// System.out.println("tbResult: "+tbResult);
	}

	/**
	 * 配资投标、带红包参数giftId
	 * 
	 * @param financing_contract_id
	 * @param p2p_contract_id
	 * @param giftId
	 * @param amount
	 * @param mobile
	 * @param password
	 */
	public void pzTbGiftGet(String financing_contract_id, String p2p_contract_id, String giftId, String amount, String mobile, String password) {
		System.out.println("====红包投标====");
		// String url = "https://www.xuanniu.com/v1/api/p2pBuy?p2pContractId=" +
		// p2p_contract_id + "&financingContractId=" + financing_contract_id +
		// "&amount=" + amount + "&giftId=" + giftId;
		String url = "https://www.xuanniu.com/p2pBid?p2pContractId=" + p2p_contract_id + "&financingContractId=" + financing_contract_id + "&amount=" + amount + "&giftId=" + giftId;
		// System.out.println("投标url is: "+url);
		String tbGiftResult = frontgp.frontActionGet(url, mobile, password);

	}

	/**
	 * 从xuanniu_xuanniu_trade.currency_account、获取钱数：如balance、available、freeze、
	 * financing_freeze、p2p_freeze
	 * 
	 * @param mobile
	 * @param param
	 * @return
	 */
	public BigDecimal getCurrencyAccountValue(String mobile, String param) {
		BigDecimal paramValue = HttpSqlPeizi.getCurrency_AcountValue(mobile, param);
		return paramValue;

	}

	/**
	 * 取得平台货币账户额度
	 * 
	 * @param param
	 * @return
	 */
	public BigDecimal getPlat_Huobi_accountValue(String param) {
		BigDecimal paramStr = HttpSqlPeizi.getPlat_Huobi_accountValue(param);
		return paramStr;
	}

	/**
	 * 取得平台代付账户金额
	 * 
	 * @param business_sn
	 * @param param
	 * @return
	 */
	public BigDecimal getPlat_daifu_accountValue(String business_sn, String param) {
		BigDecimal paramStr = HttpSqlPeizi.getPlat_daifu_accountValue(business_sn, param);
		return paramStr;
	}

	/**
	 * 前台申请终止合约
	 * 
	 * @param financing_contract_sn
	 * @param mobile
	 * @param passsword
	 */
	public void terminalApplyPost(String financing_contract_sn, String mobile, String passsword) {
		// String financing_contract_sn = "PZ00001357";
		// ----step1: 参数获取、
		String trade_account_id = HttpSqlPeizi.getFinancingContract(financing_contract_sn, "trade_account_id");
		System.out.println("终止申请的trade_account_id 是: " + trade_account_id);

		String url = "https://www.xuanniu.com/v1/api/contract/stopAsset";
		// ----step2: post请求参数构造
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("sn", financing_contract_sn));
		params.add(new BasicNameValuePair("accountId", trade_account_id));
		// ---- step2: 执行
		String jsonStr = frontgp.frontActionPost(url, params, mobile, password);
		System.out.println("前台申请终止jsonStr: " + jsonStr);

	}

	/**
	 * 天续期终止
	 * 
	 * @param financing_contract_sn
	 * @param usedate
	 * @param mobile
	 * @param passsword
	 */
	@SuppressWarnings("unused")
	public void xqHyApplyDayPost(String financing_contract_sn, String usedate, String mobile, String passsword) {

		// String continueInfoUrl =
		// "https://www.xuanniu.com/v1/api/contract/continueInfo";
		// ----step2: post请求参数构造
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("sn", financing_contract_sn));
		params.add(new BasicNameValuePair("usedate", usedate));
		// String jsonStrV1 = frontgp.frontActionPost(continueInfoUrl, params,
		// mobile, password);
		// System.out.println("continue info jsonStr: " + jsonStrV1);

		String continueUrl = "https://www.xuanniu.com/v1/api/contract/continue";
		String jsonStrV2 = frontgp.frontActionPost(continueUrl, params, mobile, password);
		// System.out.println("continue jsonStr: " + jsonStrV2);
		// JSONObject jsob = JSONObject.fromObject(jsonStrV2);
		// String financing_contract_sn = (String) jsob.get("body");
	}

	/**
	 * 月续期终止
	 * 
	 * @param financing_contract_sn
	 * @param usedate
	 * @param mobile
	 * @param passsword
	 */
	@SuppressWarnings("unused")
	public String xqHyMonApplyPost(String financing_contract_sn, String usedate, String mobile, String passsword) {
		String continueInfoUrl = "https://www.xuanniu.com/v1/api/contract/continueInfo";
		// ----step2: post请求参数构造
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("sn", financing_contract_sn));
		params.add(new BasicNameValuePair("usedate", usedate));
		// String jsonStrV1 = frontgp.frontActionPost(continueInfoUrl, params,
		// mobile, password);
		// System.out.println("continue info jsonStr: " + jsonStrV1);

		String continueUrl = "https://www.xuanniu.com/v1/api/contract/continue";
		String jsonStrV2 = frontgp.frontActionPost(continueUrl, params, mobile, password);
		JSONObject jsob = JSONObject.fromObject(jsonStrV2);
		String xq_financing_contract_sn = (String) jsob.get("body");
		return xq_financing_contract_sn;

	}

	/**
	 * 追加保证金
	 * 
	 * @param tradeAccountId
	 * @param inAmount
	 */
	public void appendAssurance(String tradeAccountId, String inAmount, String mobile, String passsword) {
		String assuranceUrl = "https://www.xuanniu.com/v1/api/financing/in";
		// ----step2: post请求参数构造
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tradeAccountId", tradeAccountId));
		params.add(new BasicNameValuePair("inAmount", inAmount));

		String jsonStr = frontgp.frontActionPost(assuranceUrl, params, mobile, password);
		System.out.println("追加保证金 jsonStr is: " + jsonStr);
	}

	/**
	 * 抽奖
	 * 
	 * @param url
	 * @param mobile
	 * @param password
	 */
	public void choujiangGet(String url, String mobile, String password) {
		String jsonChoujiang = frontgp.frontActionGet(url, mobile, password);
		System.out.println("jsonChoujiang: " + jsonChoujiang);
	}
	
	/**
	 * 炒股理财投标
	 * @param url
	 * @param mobile
	 * @param password
	 */
	public void stockToubiaoGet(String url,String mobile, String password){
		String jsonTouBiao = frontgp.frontActionGet(url, mobile, password);
//		System.out.println("stock toubiao: " + jsonTouBiao);
	}
	
	public static void main(String[] args) {
		HttpLoginFrontBusiness hlfb = new HttpLoginFrontBusiness();
		// String financing_sn = hlfb.pzApplyPostMonth("", "2000", "", "",
		// "12","1", "1.6", "1", mobile, password);
		// System.out.println("financing_sn: "+financing_sn);

		// System.out.println("1、"+mobile + " 的可用余额是: " +
		// hlfb.getCurrencyAccountValue(mobile, "available"));
		// System.out.println("2、平台货币账户可用余额是: " +
		// hlfb.getPlat_Huobi_accountValue("available"));
		// System.out.println("3、平台代付账户可用金额是：" +
		// hlfb.getPlat_daifu_accountValue("JK00001147", "available"));

		// hlfb.terminalApplyPost("PZ00001362", mobile, password);

		// hlfb.xqHyApplyPost("PZ00001453","4", mobile, password);
		// hlfb.xqHyApplyDayPost("PZ00001458","4", mobile, password);

		// hlfb.appendAssurance("1603", "6000", mobile, password);

		// hlfb.pzApplyAllPostMon("", "5000", "2", "12", "4", "1.9", "", "",
		// "2", "19", mobile, password);
		// hlfb.pzApplyAllPostDay("", "5000", "1", "3", "4", "", "1", "", "17",
		// mobile, password);
		// hlfb.pzTbGiftGet("1572", "1454", "27", "1000", mobile, password);

	}

}
