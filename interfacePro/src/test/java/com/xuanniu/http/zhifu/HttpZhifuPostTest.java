package com.xuanniu.http.zhifu; 
/** 
* @author sungq 
* @version 创建时间：2015年6月2日 下午6:46:37 
* 类说明 
*/ 

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class HttpZhifuPostTest {
	HttpZhifuPost zfp = new HttpZhifuPost();

	@Test
	public void ZhifuPostTest() {
		/*
		 * 
		 */
		System.out.println("工商银行ICBC、页面跳转、001、84");
		zfp.zhifuPost("40", "7", "", "0.01", "", "001", "84");
		
		System.out.println("招商银行CMB、页面跳转、002、85");
		zfp.zhifuPost("40", "7", "", "0.01", "", "002", "85");
		
		System.out.println("建设银行CCB、页面跳转、003、86");
		zfp.zhifuPost("40", "7", "", "0.01", "", "003", "86");
		
		System.out.println("中国银行BOC、页面跳转、004、87");
		zfp.zhifuPost("40", "7", "", "0.01", "", "004", "87");
		
		System.out.println("农业银行ABC、页面跳转、005、88");
		zfp.zhifuPost("40", "7", "", "0.01", "", "005", "88");
		
		
		System.out.println("交通银行BCN、页面跳转、006、89");
		zfp.zhifuPost("40", "7", "", "0.01", "", "006", "89");
		
		System.out.println("浦发银行SPDB、页面跳转、007、90");
		zfp.zhifuPost("40", "7", "", "0.01", "", "007", "90");
		
		System.out.println("广发银行CGB、页面跳转、008、91");
		zfp.zhifuPost("40", "7", "", "0.01", "", "008", "91");
		
		System.out.println("中信银行CICTC、页面跳转、009、92");
		zfp.zhifuPost("40", "7", "", "0.01", "", "009", "92");
		
		
		System.out.println("光大银行CEB、页面跳转、010、93");
		zfp.zhifuPost("40", "7", "", "0.01", "", "010", "93");
		
		
		System.out.println("兴业银行CIB、页面跳转、011、94");
		zfp.zhifuPost("40", "7", "", "0.01", "", "011", "94");
		
		System.out.println("平安银 行PAB、页面跳转、012、95");
		zfp.zhifuPost("40", "7", "", "0.01", "", "012", "95");
		
		
		System.out.println("民生银 行CMBC、页面跳转、013、96");
		zfp.zhifuPost("40", "7", "", "0.01", "", "013", "96");
		
		System.out.println("华夏银 行HXB、页面跳转、014、97");
		zfp.zhifuPost("40", "7", "", "0.01", "", "014", "97");
		
		System.out.println("邮储银行PSBC、页面跳转、020、98");
		zfp.zhifuPost("40", "7", "", "0.01", "", "020", "98");
	}

}


