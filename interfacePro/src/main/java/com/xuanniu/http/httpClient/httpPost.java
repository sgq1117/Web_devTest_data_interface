package com.xuanniu.http.httpClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * @author sungq
 * @version 创建时间：2015年6月10日 下午2:45:27 类说明
 */

public class httpPost {
	public static Map post(String url, List<NameValuePair> params) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		HttpPost httpPost = new HttpPost(url);
//		CloseableHttpClient httpclient = HttpClientBuilder.create().build();
		HttpClient httpclient = new DefaultHttpClient();
		String Str = null;
		try {
			// ---带参数协议
			httpPost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=" + "UTF_8");
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			// ---
			String cookies = ((AbstractHttpClient) httpclient).getCookieStore().getCookies().toString();
			System.out.println("cookies: "+cookies);			 
			// ---返回检查
			CloseableHttpResponse loginResponse = (CloseableHttpResponse) httpclient.execute(httpPost);

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
		} finally {
			httpPost.releaseConnection();
		}
		return map;
	}

	public static void main(String[] args) {
		String url = "https://back.xuanniu.com/v1/api/admin/login";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("", ""));
		Map mapv = post("https://www.xuanniu.com/v1/api0/security/isLogin", params);
		
		
		System.out.println("mapv: " + mapv.get("client"));
		System.out.println("mapv: " + mapv.get("str"));
	}
}
