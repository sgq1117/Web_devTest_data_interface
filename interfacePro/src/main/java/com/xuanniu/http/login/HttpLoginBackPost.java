package com.xuanniu.http.login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * @author sungq
 * @version 创建时间：2015年5月23日 下午4:53:33 类说明
 */

public class HttpLoginBackPost {

	/**
	 * 后台登陆Post、取得token、带着登陆client
	 * 
	 * @param url
	 * @param params
	 * @param Charset
	 * @param mobile
	 * @param password
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map post(String url, List<NameValuePair> params, String Charset, String mobile, String password) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpClient httpclient = HttpClientBuilder.create().build();
		String Str = null;
		try {
			// ---带参数协议
			httpPost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=" + Charset);
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			// ---返回检查
			CloseableHttpResponse loginResponse = httpclient.execute(httpPost);

			int statusCode = loginResponse.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = loginResponse.getEntity();
				if (entity != null) {
					Str = EntityUtils.toString(entity, "UTF-8");
					map.put("client", httpclient);
					map.put("str", Str);

				} else {
					System.out.println("返回内容为：null" + entity);
				}
			} else {
				System.out.println("Wrong, the status is: " + statusCode);
			}

		} catch (Exception e) {
			System.out.println("{Post Exception:}" + e.toString());
		} finally{
			httpPost.releaseConnection();
		}
		return map;
	}

	/**
	 * 后台登陆传递 传递账户、
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map returnBackLogin(String userName, String password) {
//		System.out.println("----后台登陆----");
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		String token = null;
		// -----类引用声明

		// ----Step1: url、参数具体化、调用
		String url = "https://back.xuanniu.com/v1/api/admin/login";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("userName", userName));
		params.add(new BasicNameValuePair("password", password));
		// ----step2: 登陆请求
		Map returnMap = post(url, params, "UTF-8", userName, password);
		String body = (String) returnMap.get("str");
		JSONObject bodyParam = JSONObject.fromObject(body);
		// -----step3: 返回Token
//		System.out.println("code: " + bodyParam.get("code"));
		if (bodyParam.get("code").equals(200)) {
			token = (String) bodyParam.get("body");
			String cookie = "token=" + token;
			map.put("client", returnMap.get("client"));
			map.put("cookie", cookie);
		} else {
			System.out.println("login failed: ");
		}
		// --------
		return map;
	}

	
	public static void main(String[] args) {
		HttpLoginBackPost hlp = new HttpLoginBackPost();
		Map loginReturn = hlp.returnBackLogin("admin", "admin");
		System.out.println("login Client is: "+loginReturn.get("client"));
		System.out.println("login cookie is: "+loginReturn.get("cookie"));
		// System.out.println("return Body:"+returnBody);
		// System.out.println("return Body:"+returnBody.get("body"));

	}

}
