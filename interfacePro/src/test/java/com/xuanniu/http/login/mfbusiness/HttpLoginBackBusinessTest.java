
package com.xuanniu.http.login.mfbusiness; 
/** 
* @author sungq 
* @version 创建时间：2015年5月25日 上午10:07:12 
* 类说明 
*/ 

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class HttpLoginBackBusinessTest {
	HttpLoginBackBusiness backgp = new HttpLoginBackBusiness();
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	/**
	 * 配资成功、操盘资金划转成功、要前台申请终止合约、满标取消合约
	 * @param financingContractSn
	 * @param uid
	 */
	@Test
	public void mainBiaoTerminalGetTest(String financingContractSn, String uid){
//		String financingContractSn = "PZ00001055";
//		String uid = "267";
		backgp.manBiaoTerminalGet( financingContractSn,  uid);
	}
	/**
	 * 未满标终止
	 * @param financingContractId
	 */
	@Test
	public void NoManBiaoCancelPostTest(String financingContractId){
		// String financingContractId = "1086";
		backgp.cancelNoManBiaoPost(financingContractId);
		
	}
	/**
	 * p2p标的发布
	 * @param p2pContractId
	 */
	@Test
	public void sendBiaoDiGetTest(String p2pContractId){
		// String p2pContractId = "986";
		backgp.sendBiaoDiGet(p2pContractId);;
	}
	
	/**
	 * 开通操盘账户
	 * @param p2pContractId
	 */
	@Test
	public void ktAccountGetTest(String p2pContractId){
		backgp.ktAccountGet(p2pContractId);
	}
	
	/**
	 * 派息计息资金划转
	 */
	public void payBillGetTest(){
		backgp.payBillGet();
	}
	
}

