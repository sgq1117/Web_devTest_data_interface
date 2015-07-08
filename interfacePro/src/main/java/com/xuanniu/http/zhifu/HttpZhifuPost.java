package com.xuanniu.http.zhifu; 

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.xuanniu.http.login.method.HttpLoginFrontGP;

/** 
* @author sungq 
* @version 创建时间：2015年6月2日 下午6:02:51 
* 类说明 
*/ 

public class HttpZhifuPost {
	HttpLoginFrontGP frontgp = new HttpLoginFrontGP();

//	static String mobile = "99913920001";
//	static String password = "123456";
	
//	static String[] arccount = {"99913920001","123456"};
	static String[] arccount = {"15313926589","1234567"};
	
	public String zhifuPost(String businessType,String payType,String productName,String amount,String returnUrl,String frpId,String bankId){
		String url = "https://www.xuanniu.com/v1/api/deposit/redirect";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("businessType",businessType));
		params.add(new BasicNameValuePair("payType",payType ));
		params.add(new BasicNameValuePair("productName", productName));
		params.add(new BasicNameValuePair("amount", amount));
		params.add(new BasicNameValuePair("returnUrl", returnUrl));
		params.add(new BasicNameValuePair("frpId", frpId));
		params.add(new BasicNameValuePair("bankId", bankId));
		String jsonStr = frontgp.frontActionPost(url, params, arccount[0], arccount[1]);
//		System.out.println("jsonStr: "+jsonStr);
		return jsonStr;
	}
	
	public static void main(String[] args){
		HttpZhifuPost zfp = new HttpZhifuPost();
		zfp.zhifuPost("40", "7", "", "0.01", "", "003", "86");
	}
}


