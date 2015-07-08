package com.xuanniu.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Set;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import xuanniuSgq.Xuanniu_db;

/**
 * @author sungq
 * @version 类说明
 */

public class Conf {
	
	
//	public static String loginParamUrl = "http://security.xuanniu.com/v1/api0/security/login?jsonpcallback=jsonpcallback&mobile=" + mobile + "&password=" + password;

	// public static String loginUrl =
	// "http://security.xuanniu.com/v1/api0/security/login?jsonpcallback=jsonpcallback&mobile=15313926589&password=123456";

	public static String loginUrl = "http://security.xuanniu.com/v1/api0/security/login?jsonpcallback=jsonpcallback&mobile=88813926589&password=1234567";
	public static int uid = 187;

	/**
	 * httpClient
	 */
//	public static CloseableHttpClient httpclient = HttpClientBuilder.create().build();
	/**
	 * sql查询相关
	 */
//	public static Xuanniu_db tradeSql = new Xuanniu_db();
//	public static HashMap<String, String> selectMap = new HashMap<String, String>(); // ----请求参数字段名称映射
//	public static Set<String> selectKey = selectMap.keySet();// ---- 取得里面的key的集合

	public static void main(String[] args) {
		String loginUrl1 = "http://security.xuanniu.com/v1/api0/security/login?jsonpcallback=jsonpcallback&mobile=15313926589&password=123456";
		String mobile = "15313926589";
		String password = "123456";
		String loginUrl_pre = "http://security.xuanniu.com/v1/api0/security/login?jsonpcallback=jsonpcallback&mobile=" + mobile + "&password=" + password;

		if (loginUrl1.equals(loginUrl_pre)) {
			System.out.println("OK");
		} else {
			System.out.println("Nok");
		}

	}

}
