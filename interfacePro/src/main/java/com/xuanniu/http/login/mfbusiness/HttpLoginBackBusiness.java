package com.xuanniu.http.login.mfbusiness;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.xuanniu.http.login.method.HttpLoginBackGP;

/**
 * @author sungq
 * @version 创建时间：2015年5月25日 上午10:02:41 类说明
 */

public class HttpLoginBackBusiness {
	HttpLoginBackGP backgp = new HttpLoginBackGP();
	HttpLoginFrontBusiness hlfb = new HttpLoginFrontBusiness();
	static String mobile = "99913920002";
	static String password = "123456";

	/**
	 * 配资成功、操盘资金划转成功、要前台申请终止合约、满标取消合约
	 * 
	 * @param financingContractSn
	 * @param uid
	 */
	public void manBiaoTerminalGet(String financingContractSn, String uid) {
		// String financingContractSn = "PZ00001055";
		// String uid = "267";
		String terminalUrl = "https://back.xuanniu.com/p2p/terminal?financingContractSn=" + financingContractSn + "&uid=" + uid;
		String jsonStr = backgp.backActionGet(terminalUrl, "admin", "admin");
		System.out.println("满标终止: " + jsonStr);
	}

	/**
	 * 未满标终止
	 * 
	 * @param financingContractId
	 */
	public void cancelNoManBiaoPost(String financingContractId) {
		String url = "https://back.xuanniu.com/v1/api/contract/cancel";
		// String id = "1086";

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id", financingContractId));

		String jsonstr = backgp.backActionPost(url, params, "admin", "admin");
		System.out.println("未满标终止: " + jsonstr);
	}

	/**
	 * 未发起结算、结算中
	 * 
	 * @param financing_Contract_id
	 */
	public void jieSuanPost(String financing_Contract_id) {
		String url = "https://back.xuanniu.com/v1/api/contract/finish";
		// String financing_Contract_id = "1378"
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id", financing_Contract_id));

		String jsonstr = backgp.backActionPost(url, params, "admin", "admin");
		System.out.println("未发起结算: " + jsonstr);

	}

	/**
	 * 续期通过
	 * 
	 * @param financing_contract_sn
	 */
	public void xqPassPost(String financing_contract_sn) {
		String url = "https://back.xuanniu.com/v1/api/contract/passRenewal";
		// String sn = "PZ00001404"
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("sn", financing_contract_sn));

		String jsonstr = backgp.backActionPost(url, params, "admin", "admin");
		System.out.println("续期通过: " + jsonstr);
	}

	/**
	 * 续期不通过
	 * 
	 * @param financing_contract_sn
	 */
	public void xqUnPassPost(String financing_contract_sn) {
		String url = "https://back.xuanniu.com/v1/api/contract/unPassRenewal";
		// String sn = "PZ00001404"
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("sn", financing_contract_sn));

		String jsonstr = backgp.backActionPost(url, params, "admin", "admin");
		System.out.print("续期不通过: " + jsonstr);

	}

	/**
	 * 
	 * @param financing_contract_id
	 * @param p2p_contract_id
	 */
	public void xqMonShengXiaoPost(String financing_contract_id, String p2p_contract_id) {
		String url = "https://back.xuanniu.com/v1/api/contract/renewalTransform";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("financingId", financing_contract_id));
		params.add(new BasicNameValuePair("p2pId", p2p_contract_id));
		// ----请求
		String jsonstr = backgp.backActionPost(url, params, "admin", "admin");
		System.out.print("月续期生效: " + jsonstr);

	}

	/**
	 * 发布p2p标的
	 * 
	 * @param p2pContractId
	 */
	public void sendBiaoDiGet(String p2p_contract_id) {
		// String p2pContractId = "986";
		String url = "https://back.xuanniu.com//p2p/finish?p2pContractId=" + p2p_contract_id;
		String jsonStr = backgp.backActionGet(url, "admin", "admin");
		System.out.println("p2p发布标的: " + jsonStr);
	}

	/**
	 * 开通操盘账户
	 * 
	 * @param p2pContractId
	 */
	public void ktAccountGet(String p2p_contract_id) {

		// String p2pContractId = "986";
		String url = "https://back.xuanniu.com/p2p/openaccount?p2pContractId=" + p2p_contract_id;
		String jsonStr = backgp.backActionGet(url, "admin", "admin");
		System.out.println("开通操盘账户：" + jsonStr);
	}

	/**
	 * 资金划转
	 * 
	 * @param financingContractId
	 */
	public void transferMondyGet(String financing_contract_id) {
		// String financingContractId = "1090";
		String url = "https://back.xuanniu.com/p2p/financingtransform?financingContractId=" + financing_contract_id;
		String jsonStr = backgp.backActionGet(url, "admin", "admin");
		System.out.println("资金划转： " + jsonStr);
	}

	/**
	 * 派息计息资金划转
	 */
	public void payBillGet() {
		String url = "https://back.xuanniu.com/p2p/paybill";
		String jsonStr = backgp.backActionGet(url, "admin", "admin");
		System.out.println("支付账单: " + jsonStr);
	}

	/**
	 * 开通操盘账户
	 * 
	 * @param financing_contract_id
	 */
	public void createTradeAccountPost(String financing_contract_id) {
		String url = "https://back.xuanniu.com/v1/api/contract/createTradeAccount";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id", financing_contract_id));
		// ----请求
		String jsonstr = backgp.backActionPost(url, params, "admin", "admin");
		System.out.println("开通操盘账户: " + jsonstr);

	}

	/**
	 * 资金划转
	 * 
	 * @param financing_contract_id
	 */
	public void financingTransferPost(String financing_contract_id) {
		String url = "https://back.xuanniu.com/v1/api/contract/financingTransform";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id", financing_contract_id));
		// --
		String jsonstr = backgp.backActionPost(url, params, "admin", "admin");
		System.out.println("资金划转: " + jsonstr);
	}

	/**
	 * 返回佣金的调度程序	
	 */
	public void returnYongjinGet(){
		System.out.println("====返回佣金操作====");
		String url = "https://back.xuanniu.com/recommend/bonusScheduler";
		String jsonStr = backgp.backActionGet(url, "admin", "admin");
//		System.out.println("返回佣金: " + jsonStr);
	}
	/**
	 * 发标的、投标、开账户、划转资金
	 * 
	 * @param p2p_contract_id
	 * @param financing_contract_id
	 * @param tBamount
	 * @param tbPzMobile_Va
	 * @param tbPzPassword_Va
	 */
	public void backTbNormal(String p2p_contract_id, String financing_contract_id, String tBamount, String tbPzMobile_Va, String tbPzPassword_Va) {
		sendBiaoDiGet(p2p_contract_id);
		hlfb.pzToubiaoGet(financing_contract_id, p2p_contract_id, tBamount, tbPzMobile_Va, tbPzPassword_Va);
		createTradeAccountPost(financing_contract_id);
		financingTransferPost(financing_contract_id);
	}

	
	/***
	 * 发标的、开通操盘账户、资金划转、派息计息
	 * 
	 * @param p2pContractId
	 * @param financingContractId
	 */
	public void backNormalFlow(String p2p_contract_id, String financing_contract_id, String tBamount, String tbPzMobile_Va, String tbPzPassword_Va) {
		// ----发标的、开账户、资金划转、派息计息划转
		sendBiaoDiGet(p2p_contract_id);
		hlfb.pzToubiaoGet(financing_contract_id, p2p_contract_id, tBamount, tbPzMobile_Va, tbPzPassword_Va);
//		System.out.println("投标资金: "+tBamount);
		createTradeAccountPost(financing_contract_id);
		financingTransferPost(financing_contract_id);
		// ktAccountGet(p2p_contract_id);
		// transferMondyGet(financing_contract_id);
		payBillGet();
	}
	
	/**
	 * 创建操盘账户、资金划转
	 * @param financing_contract_id
	 */
	public void backAccountBill(String financing_contract_id){
		createTradeAccountPost(financing_contract_id);
		financingTransferPost(financing_contract_id);
		payBillGet();
	}
	/**
	 * 待开通操盘账户
	 * @param p2p_contract_id
	 * @param financing_contract_id
	 * @param tBamount
	 * @param tbPzMobile_Va
	 * @param tbPzPassword_Va
	 */
	public void backWillCreateAccount(String p2p_contract_id, String financing_contract_id, String tBamount, String tbPzMobile_Va, String tbPzPassword_Va){
		sendBiaoDiGet(p2p_contract_id);
		hlfb.pzToubiaoGet(financing_contract_id, p2p_contract_id, tBamount, tbPzMobile_Va, tbPzPassword_Va);
	}
	/**
	 * 发标的、投标
	 * 
	 * @param p2p_contract_id
	 * @param financing_contract_id
	 * @param tBamount
	 * @param tbPzMobile_Va
	 * @param tbPzPassword_Va
	 */
	public void backCancelBd(String p2p_contract_id, String financing_contract_id, String tBamount, String tbPzMobile_Va, String tbPzPassword_Va) {
		// ----发标的、开账户、资金划转、派息计息划转
		sendBiaoDiGet(p2p_contract_id);
		hlfb.pzToubiaoGet(financing_contract_id, p2p_contract_id, tBamount, tbPzMobile_Va, tbPzPassword_Va);
		cancelNoManBiaoPost(financing_contract_id);
	}

	/**
	 * 发标的、开通操盘账户、资金划转、拍戏鸡西
	 * 
	 * @param p2p_contract_id
	 * @param financing_contract_id
	 * @param giftId
	 * @param amount
	 * @param mobile
	 * @param password
	 */
	public void backCouponFlow(String p2p_contract_id, String financing_contract_id, String giftId, String tBamount, String tbPzMobile_Va, String tbPzPassword_Va) {
		sendBiaoDiGet(p2p_contract_id);
		hlfb.pzTbGiftGet(financing_contract_id, p2p_contract_id, giftId, tBamount, tbPzMobile_Va, tbPzPassword_Va);
		createTradeAccountPost(financing_contract_id);
		financingTransferPost(financing_contract_id);
		payBillGet();
	}
	/**
	 * 添加炒股理财项目
	 * @param stockFinancingId
	 * @param name
	 * @param amount
	 * @param period
	 * @param minInterest
	 * @param maxInterest
	 * @param minAmount
	 * @param startTime
	 * @param endTime
	 * @param financingAccountId
	 */
	public void backStockAddProPost(String stockFinancingId,String name,String amount,String period,String minInterest,String maxInterest,String minAmount,String startTime,String endTime,String financingAccountId){
		String url = "https://back.xuanniu.com/stock/save";
		// String sn = "PZ00001404"
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("stockFinancingId", stockFinancingId));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("amount", amount));
		params.add(new BasicNameValuePair("period", period));
		params.add(new BasicNameValuePair("minInterest", minInterest));
		params.add(new BasicNameValuePair("maxInterest", maxInterest));
		params.add(new BasicNameValuePair("minAmount", minAmount));
		params.add(new BasicNameValuePair("startTime", startTime));
		params.add(new BasicNameValuePair("endTime", endTime));
		params.add(new BasicNameValuePair("financingAccountId", financingAccountId));
			
		String jsonstr = backgp.backActionPost(url, params, "admin", "admin");
		System.out.println("添加炒股理财项目: " + jsonstr);
	}
	
	/**
	 * 发布标的
	 * @param stock_financing_id
	 */
	public void backStockSendBiaoPost(String stock_financing_id){	
		String url = "https://back.xuanniu.com/stock/publish";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id", stock_financing_id));
		
		String jsonstr = backgp.backActionPost(url, params, "admin", "admin");
		System.out.println("炒股理财发布标的: " + jsonstr);
	}
	
	/**
	 * 炒股理财创建操盘账户
	 * @param stockFinancingOrderId
	 * @param uid
	 * @param stockFinancingId
	 */
	public void backStockCreateTradeAccountPost(String stockFinancingOrderId,String uid,String stockFinancingId ){
		String url = "https://back.xuanniu.com/stock/order/createStockTradeAccount";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("stockFinancingOrderId", stockFinancingOrderId));
		params.add(new BasicNameValuePair("uid", uid));
		params.add(new BasicNameValuePair("stockFinancingId:", stockFinancingId));
		
		String jsonstr = backgp.backActionPost(url, params, "admin", "admin");
		System.out.println("炒股理财创建操盘账户: " + jsonstr);
		
	}
	
	/**
	 * 炒股理财资金划转
	 * @param stock_financing_detail_id
	 */
	public void backStockTransformPost(String stock_financing_detail_id){
		String url = "https://back.xuanniu.com/stock/order/stockTransform";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id", stock_financing_detail_id));
		
		String jsonstr = backgp.backActionPost(url, params, "admin", "admin");
		System.out.println("炒股理财资金划转: " + jsonstr);
	}
	public static void main(String[] args) {
		HttpLoginBackBusiness hlbb = new HttpLoginBackBusiness();
		// hlbb.noManBiaoCancelPost("1354");
		// hlbb.xqPassPost("PZ00001403");
		// hlbb.xqUnPassPost("PZ00001401");
		// hlbb.xqShengXiaoPost("1334", "1228");
		// hlbb.jieSuanPost("1306");
		hlbb.payBillGet();
//		hlbb.returnYongjinGet();

	}
}
