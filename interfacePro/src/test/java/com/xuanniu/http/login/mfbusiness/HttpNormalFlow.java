package com.xuanniu.http.login.mfbusiness; 
/** 
* @author sungq 
* @version 创建时间：2015年5月25日 下午3:30:34 
* 类说明 
*/ 

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.xuanniu.http.login.method.HttpLoginBackGP;

public class HttpNormalFlow {
	
	HttpLoginBackBusiness hlbb= new HttpLoginBackBusiness();
	HttpLoginFrontBusiness hlfb = new HttpLoginFrontBusiness();
	String tbPzMobile_Va = "99913920002";
	String tbPzPassword_Va = "1234567";
	
	String financing_contract_id = "1334";
	String p2p_contract_id = "1228";	
	
	String amount = "15000";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("===={Begin: 发标的、投标、开账户、资金划转}====");
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("===={End: 发标的、投标、开账户、资金划转}====");
	}

//	@Test
	public void sendBiaoDiGetTest() {
		System.out.println("===={发布标的}======");
		hlbb.sendBiaoDiGet(p2p_contract_id);	
	}
	
//	@Test
	public void pzToubiaoGetTest(){
		System.out.println("===={投标====}====");
		hlfb.pzToubiaoGet(financing_contract_id, p2p_contract_id, amount, tbPzMobile_Va, tbPzPassword_Va);
	}
	
//	@Test
	public void ktAccountGetTest(){
		hlbb.ktAccountGet(p2p_contract_id);
	}
	
//	@Test
	public void transferMondyGetTest(){
		hlbb.transferMondyGet(financing_contract_id);
	}
//	@Test
	public void payBillGet(){
		hlbb.payBillGet();
	}
	
	
	
}


