package com.xuanniu.http.login.method;

/** 
 * @author sungq 
 * @version 创建时间：2015年5月24日 下午2:58:13 
 * 类说明 
 */

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.xuanniu.http.sql.HttpSqlPeizi;

public class HttpLoginBackGPTest {
	HttpLoginBackGP backgp = new HttpLoginBackGP();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("====Back Test Begin====");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("====Back Test End====");
	}

	// @Test
	public void manBiaoTerminalGetTest() {
		System.out.println("===={CASE1: }配资成功、操盘资金划转成功、满标取消合约===");
		String financingContractSn = "PZ00001055";
		String uid = "267";
		String terminalUrl = "http://back.xuanniu.com/p2p/terminal?financingContractSn=" + financingContractSn + "&uid=" + uid;
		String jsonStr = backgp.backActionGet(terminalUrl, "admin", "admin");
		System.out.println("满标终止: " + jsonStr);
	}



//	 @Test
	public void noManBiaoCancelPostTest() {
		System.out.println("===={CASE3: }未满标、终止合约====");

		// ----step1: 融资人、债权人、火币账户检查

		BigDecimal pre_avialable_a = HttpSqlPeizi.getCurrency_AcountValue("99913926577", "available");

		BigDecimal pre_avialable_b = HttpSqlPeizi.getCurrency_AcountValue("77713926582", "available");

		// ----step2: 执行请求
		String url = "http://back.xuanniu.com/v1/api/contract/cancel";
		String id = "1086";

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id", id));

		String jsonstr = backgp.backActionPost(url, params, "admin", "admin");
		// -----step3： step1: 融资人、债权人、火币账户检查
		BigDecimal af_avialable_a = HttpSqlPeizi.getCurrency_AcountValue("99913926577", "available");

		BigDecimal af_avialable_b = HttpSqlPeizi.getCurrency_AcountValue("77713926582", "available");
		//-数值比较	
		BigDecimal available_a_change = af_avialable_a.subtract(pre_avialable_a);
		BigDecimal available_b_change = af_avialable_b.subtract(pre_avialable_b);
		
		System.out.println("投资人可用余额增加: "+available_a_change);
		System.out.println("融资人可用余额增加: "+available_b_change);	
		
		System.out.println("提前终止：" + jsonstr);
	}
	
	@Test
	public void xqActionTest(){
//		http://back.xuanniu.com/v1/api/contract/unPassRenewal
//			id:1088
	}
	@Test
	public void sendBiaoDiGetTest() {
		System.out.println("===={CASE2: 发布标的}====");
		String p2pContractId = "986";
		String url = "http://back.xuanniu.com//p2p/finish?p2pContractId=" + p2pContractId;
		String jsonStr = backgp.backActionGet(url, "admin", "admin");
		System.out.println("发布标的: " + jsonStr);

	}
	 @Test
	public void ktAccountGetTest() {
		System.out.println("===={CASE4: 开通操盘账户}====");
		String p2pContractId = "986";
		String url = "http://back.xuanniu.com/p2p/openaccount?p2pContractId=" + p2pContractId;
		String jsonStr = backgp.backActionGet(url, "admin", "admin");
		System.out.println("开通操盘账户：" + jsonStr);

	}

	 @Test
	public void transferMondyGetTest() {
		System.out.println("===={CASE5: 待资金划入 }====");
		String financingContractId = "1090";
		String url = "http://back.xuanniu.com/p2p/financingtransform?financingContractId=" + financingContractId;
		String jsonStr = backgp.backActionGet(url, "admin", "admin");
		System.out.println("资金划转： " + jsonStr);
	}

	 @Test
	public void payBillGetTest() {
		System.out.println("===={CAS6: } 资金划转后、支付账单操作====}");
		String url = "http://back.xuanniu.com/p2p/paybill";
		String jsonStr = backgp.backActionGet(url, "admin", "admin");
		System.out.println("支付账单: " + jsonStr);

	}

}
