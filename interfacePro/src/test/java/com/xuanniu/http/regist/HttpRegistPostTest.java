package com.xuanniu.http.regist;

/** 
 * @author sungq 
 * @version 创建时间：2015年6月1日 上午10:33:01 
 * 类说明 
 */

//import static org.junit.Assert.*;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.db.trade.Currency_account;
import com.xuanniu.http.login.mfbusiness.HttpLoginFrontBusiness;
import com.xuanniu.http.sql.HttpSqlCurrencyAccount;
import com.xuanniu.http.sql.HttpSqlRegist;
import com.xuanniu.util.CalcNum;

public class HttpRegistPostTest {
	HttpLoginFrontBusiness hlfb = new HttpLoginFrontBusiness();
	Currency_account ca = new Currency_account();
	HttpSqlCurrencyAccount cab = new HttpSqlCurrencyAccount();
	CalcNum cn = new CalcNum();

	// ======================================
	String password = "123456";
	// ======================================
	private String mobileSun ="15313926589";
	   
	
//	String mobile = "99913920001";
//	 String mobile ="99913920002";
	// String mobile ="99913920003";
//	   String mobile ="99913920021";
	
//	String mobile ="99913930003";
//	String mobile = "15313926589";
	 
//		String mobile ="99913920021";
//		String mobile ="99913920031";
//		String mobile ="99913920032";
//		String mobile ="99913920033";
	//=========================================   
	 String mobile = "99913920004";
	// String mobile = "99913920005";
	// String mobile = "99913920014";
	// String mobile = "99913920010";
	// String mobile = "99913920011";

	// String mobile = "99913920012";
	// String mobile = "99913920013";
	// ======================================
	// String mobile = "99913920006";
	// String mobile = "99913920007";
	// ======================================
	
//	private BigDecimal moneyup = new BigDecimal(1);
//	private BigDecimal moneyup = new BigDecimal(10000);
//	private BigDecimal moneyup = new BigDecimal(252);
//	private BigDecimal moneyup = new BigDecimal(5420);
//	private BigDecimal moneyup = new BigDecimal(5072);
	private BigDecimal moneyup = new BigDecimal(30000000);

//	@Test
	public void clRegistAccount() {
		System.out.println("===={清除}====" + mobileSun);
		 String mobile = "15313926589";
//		String mobile = "13146690904";
		HttpSqlRegist.clsBeforeRegist(mobile);
	}

//	 @Test
	public void registOnlyTest() throws Exception {
		System.out.println("===={注册}====" + mobile);
		HttpSqlRegist.clsBeforeRegist(mobile);
		
		HttpRegistPost hrp = new HttpRegistPost();
		hrp.phone(mobile);
	}

	
	@Test
	public void registAuditStatusTest() throws Exception {		
//		String mobile1 ="99913920001";
//		String mobile1 ="99913920002";
//		String mobile1 ="99913920003";
		
//		String mobile1 ="99913930003";
		
		
		String mobile1 ="99913920004";
		
//		String mobile1 ="99913920014";
		
//		String mobile1 ="99913920021";
//		String mobile1 ="99913920031";
//		String mobile1 ="99913920032";
//		String mobile1 ="99913920033";
		//--------------
		String password1 = "123456";
		String id_number = "f3ea90535183269b822353373d545aea0364a612b53f050c1562f9e38d32025a";
		String name = "黄峥峥";
		//----注册前清理
		HttpSqlRegist.clsBeforeRegist(mobile1);
		//----注册
		HttpRegistPost hrp = new HttpRegistPost();
		hrp.phone(mobile1);
		//---身份认证
		HttpSqlRegist.auditStatus(mobile1, id_number, name);
		//----充值
		chongZhiTest(mobile1,password1);
	}

	// @Test
	public void getUidTest() {
		// String mobile = "15313926589";
		System.out.println(mobile + " uid is:" + HttpSqlRegist.getUid(mobile, "id"));
	}

	
	public void chongZhiTest(String mobile,String password) {
		System.out.println("----CASE1: 用户充值判断");
		hlfb.indexSumary(mobile, password);
		// ---1充值前额度
		BigDecimal pre_balance = ca.getBalance(mobile);
		BigDecimal pre_available = ca.getAvailable(mobile);
		// ---2-充值操作----可调用相应的方法
		cab.updateChongZhi(mobile, moneyup);
		// ---3-充值后额度
		BigDecimal af_balance = ca.getBalance(mobile);
		BigDecimal af_available = ca.getAvailable(mobile);
		// ---4-前后相减法、比较充值的额度是否如预期
		BigDecimal balanceAdd = cn.sub(af_balance, pre_balance);
		BigDecimal availableAdd = cn.sub(af_available, pre_available);

		System.out.println("	充值前 总额：" + pre_balance + "充值前 可用：" + pre_available);
		System.out.println("	充值后总额：" + af_balance + "充值后可用：" + af_available);

		assertEquals(0, cn.comparTo(balanceAdd, moneyup));
		assertEquals(0, cn.comparTo(availableAdd, moneyup));
	}
//	@Test
	public void chongzhi(){
		chongZhiTest(mobile,password);
	}

}
