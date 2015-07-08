package com.xuanniu.http.regist; 

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import com.xuanniu.util.JedisCommon;

/** 
* @author sungq 
* @version 创建时间：2015年5月30日 下午12:28:23 
* 类说明 
*/ 

public class HttpRegistGet {
	/**
	 * 
	 * 带client的Get请求
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public String reqTextGet(HttpClient httpclient ,String url) throws Exception {
//		HttpClient httpclient = new DefaultHttpClient();
		String jsonStr = null;
		HttpGet httpget = new HttpGet(url);
		// System.out.println("request line:" + httpget.getRequestLine());
		// 配置请求的超时设置
		// RequestConfig requestConfig = RequestConfig.custom()
		// .setConnectionRequestTimeout(50).setConnectTimeout(50)
		// .setSocketTimeout(50).build();
		// httpget.setConfig(requestConfig);

		HttpResponse response = httpclient.execute(httpget);

		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode == 200) {
			HttpEntity entity = response.getEntity();
			jsonStr = EntityUtils.toString(entity, "utf-8");
			System.out.println("get request: "+jsonStr);

		} else {
			System.out.println("status code: " + statusCode);
			jsonStr = String.valueOf(statusCode);
		}
		// System.out.println(jsonStr);
		httpget.releaseConnection();
		return jsonStr;
	}

	
	/**
	 * 
	 * 带client的Get请求
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public String reqTextGetWith_Cookie(HttpClient httpclient,String url,String uid) throws Exception {
//		HttpClient httpclient = new DefaultHttpClient();
		String jsonStr = null;
		HttpGet httpget = new HttpGet(url);
		//----
		String recommend = "rcmd="+uid+"__";	
		httpget.addHeader(new BasicHeader("Cookie", recommend));
		//----
		HttpResponse response = httpclient.execute(httpget);

		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode == 200) {
			HttpEntity entity = response.getEntity();
			jsonStr = EntityUtils.toString(entity, "utf-8");
			System.out.println("get request: "+jsonStr);

		} else {
			System.out.println("status code: " + statusCode);
			jsonStr = String.valueOf(statusCode);
		}
		// System.out.println(jsonStr);
		httpget.releaseConnection();
		return jsonStr;
	}
	
	/**
	 * Base request 图片流
	 * 
	 * @param url
	 * @return
	 */
	public Map reqImgGet(String url) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		HttpClient httpclient = new DefaultHttpClient();
		String token = null;
		String ImageCode = null;
		HttpGet httpget = new HttpGet(url);

		try {
			httpget.setHeader("Accept", "image/png,image/*;q=0.8,*/*;q=0.5");
			httpget.setHeader("Accept-Encoding", "gzip, deflate");
			
//			System.out.println("请求 uri=" + httpget.getURI());
			// 执行 get 请求
			HttpResponse httpResponse = httpclient.execute(httpget);
			// 获取响应实体
			String cookies = ((AbstractHttpClient) httpclient).getCookieStore().getCookies().toString();
			token = cookies.substring(cookies.indexOf("value") + 6, cookies.indexOf("domain") - 2).trim();
			
//			 System.out.println(cookies);
//			System.out.println("token: " + token);
			// -----redis 获取图片验证码
			JedisCommon jc = new JedisCommon();
			String SecurityTokenTemp = "SecurityTokenTemp_" + token + "_3_register";
			JSONObject jsobsTt = JSONObject.fromObject(jc.getString(SecurityTokenTemp));
			String codekeyVal = (String) jsobsTt.get("codeKey");

			String SecurityVerifyCode = "SecurityVerifyCode_" + codekeyVal;
			JSONObject jsobsVc = JSONObject.fromObject(jc.getString(SecurityVerifyCode));
			ImageCode = (String) jsobsVc.get("code");
//			System.out.println("图片验证码: " + ImageCode);
			
			map.put("token", token);
			map.put("client", httpclient);
			map.put("ImgCode", ImageCode);

		} catch (Exception e) {
			System.out.println("图片验证码获取token失败");
			e.printStackTrace();
		} finally {
			httpget.releaseConnection();
			map.put("client", httpclient);
			map.put("ImageCode", ImageCode);
		}
		return map;
	}
	
	public Map reqImgWithRcmdGet(String url,String uid){
		HashMap<String, Object> map = new HashMap<String, Object>();
		HttpClient httpclient = new DefaultHttpClient();
		String token = null;
		String ImageCode = null;
		
		HttpGet httpget = new HttpGet(url);
		String recommend = "rcmd="+uid+"__";
		System.out.println("recommend: "+recommend);
		try {
			//----cookie url:
			httpget.setHeader("Accept", "image/png,image/*;q=0.8,*/*;q=0.5");
			httpget.setHeader("Accept-Encoding", "gzip, deflate");
			httpget.addHeader(new BasicHeader("Cookie", recommend));
			System.out.println("请求 uri=" + httpget.getURI());
			//----			
			//---- 执行 get 请求
			HttpResponse httpResponse = httpclient.execute(httpget);
			// 获取响应实体
			String cookies = ((AbstractHttpClient) httpclient).getCookieStore().getCookies().toString();
			 System.out.println("cookies: "+cookies);
			
//			token = cookies.substring(cookies.indexOf("value") + 6, cookies.indexOf("domain") - 2).trim();		
//			System.out.println("token: " + token);
			// -----redis 获取图片验证码
			JedisCommon jc = new JedisCommon();
			String SecurityTokenTemp = "SecurityTokenTemp_" + token + "_3_register";
			JSONObject jsobsTt = JSONObject.fromObject(jc.getString(SecurityTokenTemp));
			String codekeyVal = (String) jsobsTt.get("codeKey");

			String SecurityVerifyCode = "SecurityVerifyCode_" + codekeyVal;
			JSONObject jsobsVc = JSONObject.fromObject(jc.getString(SecurityVerifyCode));
			ImageCode = (String) jsobsVc.get("code");
//			System.out.println("图片验证码: " + ImageCode);
			
			map.put("token", token);
			map.put("client", httpclient);
			map.put("ImgCode", ImageCode);

		} catch (Exception e) {
			System.out.println("图片验证码获取token失败");
			e.printStackTrace();
		} finally {
			httpget.releaseConnection();
			map.put("client", httpclient);
			map.put("ImageCode", ImageCode);
		}
		return map;
	}
	
	public static void main(String[] args) {
		HttpRegistGet hpg= new HttpRegistGet();
		String url = "http://www.xuanniu.com:8080/?rcmd=514";
//		String url = "https://www.xuanniu.com/v1/api0/image/captcha?action=register";				
		Map mapv = hpg.reqImgWithRcmdGet(url,"514");
		System.out.println("Mapv token: "+mapv.get("token"));		
//		hpg.reqImgGet("https://www.xuanniu.com/v1/api0/image/captcha?action=register");
	}
}


