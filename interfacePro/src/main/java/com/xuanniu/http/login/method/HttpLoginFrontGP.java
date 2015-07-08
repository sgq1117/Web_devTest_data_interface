package com.xuanniu.http.login.method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.xuanniu.http.login.HttpLoginFrontGet;

/**
 * @author sungq
 * @version 创建时间：2015年5月24日 下午3:06:30 类说明
 */

public class HttpLoginFrontGP {
	HttpLoginFrontGet hlfg = new HttpLoginFrontGet();

	/**
	 * 前台Get方式登录、执行Get操作
	 * 
	 * @param url
	 * @param userName
	 * @param password
	 * @return
	 */
	public String frontActionGet(String url, String userName, String password) {
		Map frontloginReturn = hlfg.returnFrontLogin(userName, password);
		HttpGet httpget = new HttpGet(url);
		String jsonStr = null;
		// ----client cokie
		CloseableHttpClient httpclient = (CloseableHttpClient) frontloginReturn.get("client");
		String cookie = (String) frontloginReturn.get("cookie");
		// ---httpGet待请求
		httpget.setHeader(cookie, cookie);
		try {
			/*
			 * Get操作
			 */
			CloseableHttpResponse response = httpclient.execute(httpget);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				jsonStr = EntityUtils.toString(entity, "utf-8");
			} else {
				System.out.println(" status code: " + statusCode);
				jsonStr = String.valueOf(statusCode);
			}
		} catch (Exception e) {
			System.out.println("====前台Get方式登录、执行get请求异常====");
			e.printStackTrace();
		}

		return jsonStr;
	}

	/**
	 * 前台Get方式登录、执行Post操作
	 * 
	 * @param url
	 * @param params
	 * @param userName
	 * @param password
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String frontActionPost(String url, List<NameValuePair> params, String mobile, String password) {
		// ----登陆
		Map frontloginReturn = hlfg.returnFrontLogin(mobile, password);
		HttpPost httpPost = new HttpPost(url);
		String jsonStr = null;
		// ----client cokie
		CloseableHttpClient httpclient = (CloseableHttpClient) frontloginReturn.get("client");
		String cookie = (String) frontloginReturn.get("cookie");
		try {
			/*
			 * Post操作、设定 cookie、数据请求方式、参数编码
			 */
			httpPost.setHeader(cookie, cookie);
			httpPost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=" + "UTF-8");
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			// ---返回检查
			CloseableHttpResponse paramResponse = httpclient.execute(httpPost);
			int statusCode = paramResponse.getStatusLine().getStatusCode();

			if (statusCode == 200) {
				HttpEntity entity = paramResponse.getEntity();
				if (entity != null) {
					jsonStr = EntityUtils.toString(entity, "UTF-8");
					// System.out.println("jsonStr: "+jsonStr);
				} else {
					System.out.println("status is {200}、but content is null");
					return null;
				}
			} else if (statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
//				System.out.println("临时跳转 ==>" + HttpStatus.SC_MOVED_TEMPORARILY);
				Header[] header = paramResponse.getHeaders("Location");
//				 System.out.println("header : "+Arrays.toString(header));
				for (int i = 0; i < header.length; i++) {
					System.out.println( "====>" + header[i]);
				}
			} else if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY) {
//				System.out.println("永久跳转 ==>" + HttpStatus.SC_MOVED_PERMANENTLY);
				Header[] header = paramResponse.getHeaders("Location");
				for (int i = 0; i < header.length; i++) {
					System.out.println(i + "====> " + header[i]);
				}
			} else {

				System.out.println("status is not {200}、but as " + statusCode);
			}

		} catch (Exception e) {
			System.out.println("====前台Get方式登录、执行get请求异常====");
			e.printStackTrace();
		}
		return jsonStr;
	}

	public static void main(String[] args) {
		HttpLoginFrontGP frontgp = new HttpLoginFrontGP();
		String url = "http://www.xuanniu.com/v1/api0/trade/disposeTreaty";
		String userName = "99913926577";
		String password = "1234567";
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("margin", "5000"));
		params.add(new BasicNameValuePair("usedateUnit", "1"));
		params.add(new BasicNameValuePair("tradeAccountId", ""));
		params.add(new BasicNameValuePair("ruleId", "3"));
		params.add(new BasicNameValuePair("usedate", "4"));
		params.add(new BasicNameValuePair("tradeStartTime", "2"));
		String jsonStr = frontgp.frontActionPost(url, params, userName, password);
		System.out.println("按天申请配资： " + jsonStr);
	}
}
