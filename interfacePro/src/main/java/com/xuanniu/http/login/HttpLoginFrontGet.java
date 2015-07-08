package com.xuanniu.http.login;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 * @author sungq
 * @version 类说明
 */

public class HttpLoginFrontGet {
	/**
	 * 将带有jsonpcall的字符串去掉冗余字符、转换成json字符串
	 * 
	 * @param Str
	 * @return
	 */
	public String convertoJsonStr(String Str) {
		String jsonStr = null;
		String str = Str.replaceAll("jsonpcallback", "");
		String str1 = str.replace("(", "");
		jsonStr = str1.replace(")", "").trim();
		// System.out.println(jsonStr);
		return jsonStr;

	}

	/**
	 * 取得token：将jsonpcallback的数据转换成json字符串、提取需要的数据
	 * 
	 * @param jsonStr
	 * @return
	 */
	public String getToken(String Str) {
		String token = null;
		String jsonStr = null;
		try {
			/*
			 * 转换json字符串
			 */
			jsonStr = convertoJsonStr(Str);
			/*
			 * 形成json对象、记性转换
			 */
			JSONObject outParam = JSONObject.fromObject(jsonStr);
			JSONObject bodyJson = (JSONObject) outParam.get("body");

			token = (String) bodyJson.get("token");

		} catch (Exception e) {
			System.out.println("get Token exception e: " + e.toString());
		} finally {

		}

		return token;
	}


	/**
	 * 前台登录返回client和cookie
	 * @param mobile
	 * @param password
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map returnFrontLogin(String mobile, String password){
		String url = "https://security.xuanniu.com/v1/api0/security/login?jsonpcallback=jsonpcallback&mobile=" + mobile + "&password=" + password;
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		CloseableHttpClient httpclient = HttpClientBuilder.create().build();
		String cookie = null;
		String token = null;
		HttpGet httpget = null;
		try {
			String jsonStr = null;
			httpget = new HttpGet(url);

			CloseableHttpResponse response = httpclient.execute(httpget);
			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				jsonStr = EntityUtils.toString(entity, "utf-8");
			} else {
				System.out.println("status code: " + statusCode);
				jsonStr = String.valueOf(statusCode);
			}
			// System.out.println(jsonStr);
			token = getToken(jsonStr);
			
		} catch (Exception e) {
			System.out.println("getClientCookie exception e:" + e.toString());
		} finally {
			cookie = "token=" + token;
			map.put("client", httpclient);
			map.put("cookie", cookie);
//			 System.out.println("token : " + token+" cookie: " + cookie);
			httpget.releaseConnection();

		}
		return map;

		
	}
	
	public static void main(String[] args) {
		HttpLoginFrontGet hl = new HttpLoginFrontGet();
//		System.out.println(hl.getClientCookieWithParamUrl("88813926585", "1234567"));

	}
}
