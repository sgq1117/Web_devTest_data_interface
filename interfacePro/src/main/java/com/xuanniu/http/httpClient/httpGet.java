package com.xuanniu.http.httpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * @author sungq
 * @version 创建时间：2015年6月10日 下午2:45:44 类说明
 */

public class httpGet {

	public static void get(String url) throws ClientProtocolException, IOException {
		// ---------
		String jsonStr = null;
		HttpGet httpget = new HttpGet(url);
		// ----
		httpget.addHeader("Cookie", "514__");
		// ----
		HttpClient httpclient = new DefaultHttpClient();
		HttpResponse response = httpclient.execute(httpget);
		// ----
		String cookies = ((AbstractHttpClient) httpclient).getCookieStore().getCookies().toString();
		System.out.println("====cookies: " + cookies);

		// ----------
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode == 200) {
			HttpEntity entity = response.getEntity();
			jsonStr = EntityUtils.toString(entity, "utf-8");
			System.out.println("get request: " + jsonStr);

		} else {
			System.out.println("status code: " + statusCode);
			jsonStr = String.valueOf(statusCode);
		}
		// System.out.println(jsonStr);
		httpget.releaseConnection();
		// return jsonStr;
	}

	/**
	 * get request A
	 * @param url
	 */
	@SuppressWarnings("unused")
	public static void getTestA(String url) {
		BasicCookieStore cookieStore = new BasicCookieStore();
		CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
	
			HttpGet httpget = new HttpGet(url);
			try {
				CloseableHttpResponse response1 = httpclient.execute(httpget);
				HttpEntity entity = response1.getEntity();
				//----
				System.out.println("----{3}Initial set of cookies:");
				List<Cookie> cookies = cookieStore.getCookies();
				if (cookies.isEmpty()) {
					System.out.println("None");
				} else {
					for (int i = 0; i < cookies.size(); i++) {
						System.out.println("- " + cookies.get(i).toString());
					}
				}
				//----				
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	/**
	 * get request B
	 * @param url
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public void getTestB(String url) throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpClientContext context = HttpClientContext.create();
		BasicCookieStore cookieStore = new BasicCookieStore();
		context.setCookieStore(cookieStore);

		HttpGet httpget = new HttpGet(url);
		CloseableHttpResponse response = httpclient.execute(httpget);
		try {
		    List<Cookie> cookies = cookieStore.getCookies();
		    if (cookies.isEmpty()) {
		        System.out.println("None");
		    } else {
		        for (int i = 0; i < cookies.size(); i++) {
		            System.out.println("- " + cookies.get(i).toString());
		        }
		    }
		    EntityUtils.consume(response.getEntity());
		} finally {
		    response.close();
		}
	}
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		httpGet hg = new httpGet();
		
		// String url = "https://www.xuanniu.com/register";
		// String url = "https://www.xuanniu.com/?rcmd=532";
//		String url = "https://www.xuanniu.com/";
//		hg.get(url);
		
		String url  = "http://www.xuanniu.com:8080/?rcmd=514";
//		String url  = "https://www.baidu.com";
		hg.getTestB(url);

	}
}
