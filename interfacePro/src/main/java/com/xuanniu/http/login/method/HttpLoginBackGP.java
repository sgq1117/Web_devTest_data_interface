package com.xuanniu.http.login.method;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.xuanniu.http.login.HttpLoginBackPost;

/**
 * @author sungq
 * @version 创建时间：2015年5月23日 下午5:40:08 类说明
 */

public class HttpLoginBackGP {
	HttpLoginBackPost hlbP = new HttpLoginBackPost();

	/**
	 * Back Post方式登陆 、执行get操作
	 * 
	 * @param url
	 * @param userName
	 * @param password
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String backActionGet(String url, String userName, String password) {
		Map loginReturn = hlbP.returnBackLogin(userName, password);
		HttpGet httpget = new HttpGet(url);
		String jsonStr = null;
		// ----client cokie
		CloseableHttpClient httpclient = (CloseableHttpClient) loginReturn.get("client");
		String cookie = (String) loginReturn.get("cookie");

		// ---httpGet待请求
		httpget.setHeader(cookie, cookie);
		// ----请求
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
				System.out.println("back request get status code: " + statusCode);
				jsonStr = String.valueOf(statusCode);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			System.out.println("1、client exception");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("2、io exception");
			e.printStackTrace();
		}

		return jsonStr;
	}

	/**
	 * Back Post方式登录、执行Post操作
	 * 
	 * @param url
	 * @param userName
	 * @param password
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String backActionPost(String url,List<NameValuePair> params, String userName, String password) {

		// ----登陆
		Map loginReturn = hlbP.returnBackLogin(userName, password);
		HttpPost httpPost = new HttpPost(url);
		String jsonStr = null;
		// ----client cokie
		CloseableHttpClient httpclient = (CloseableHttpClient) loginReturn.get("client");
		String cookie = (String) loginReturn.get("cookie");
		// ----设定cookie、请求数据方式、参数传入
		try {
			/*
			 * Post操作、设定 cookie、数据请求方式、参数编码
			 */
			httpPost.setHeader(cookie, cookie);
			httpPost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=" + "UTF-8");
			// ----
//			List<NameValuePair> params = new ArrayList<NameValuePair>();
//			params.add(new BasicNameValuePair("id", id));
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			// ---返回检查
			CloseableHttpResponse paramResoponse = httpclient.execute(httpPost);

			int statusCode = paramResoponse.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = paramResoponse.getEntity();
				if (entity != null) {
					jsonStr = EntityUtils.toString(entity, "UTF-8");
				} else {
					System.out.println("status is {200}、but content is null");
					return null;
				}
			} else {
				System.out.println("status is not {200}、but as " + statusCode);
			}
		} catch (Exception e) {
			System.out.println("post 登陆后、执行post异常");
		}

		return jsonStr;

	}

	public static void main(String[] args) {

		String terminalUrl1 = "http://back.xuanniu.com/p2p/terminal?financingContractSn=PZ00001004&uid=267";
		HttpLoginBackGP pzt = new HttpLoginBackGP();
		// String body = pzt.backLoginGet(terminalUrl1, "admin", "admin");
		// System.out.println("body: " + body);

	}

}
