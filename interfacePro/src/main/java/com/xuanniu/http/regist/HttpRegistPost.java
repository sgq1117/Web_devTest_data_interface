package com.xuanniu.http.regist;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.xuanniu.util.JedisCommon;

/**
 * @author sungq
 * @version 创建时间：2015年5月30日 下午12:34:18 类说明
 */

public class HttpRegistPost {
	HttpRegistGet hrbg = new HttpRegistGet();

	/**
	 * Basic Post method
	 * 
	 * @param url
	 * @param params
	 * @param Charset
	 * @return
	 */
	public static String basePost(HttpClient httpclient, String url, List<NameValuePair> params) {
		// HashMap<String, Object> map = new HashMap<String, Object>();
		HttpPost httpPost = new HttpPost(url);
		// CloseableHttpClient httpclient = HttpClientBuilder.create().build();
		String Str = null;
		try {
			// ---带参数协议

			httpPost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");

			httpPost.setEntity(new UrlEncodedFormEntity(params));

			// ----返回检查
			HttpResponse clientResponse = httpclient.execute(httpPost);
			// ----检查状态
			int statusCode = clientResponse.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = clientResponse.getEntity();
				Str = EntityUtils.toString(entity, "UTF-8");
//				System.out.println("base post str:"+ Str);
			} else {
				System.out.println("base post status code :" + statusCode);
				System.out.println("base post entiry toString : " + Str);
			}
		

		} catch (Exception e) {
			System.out.println("{Post Exception:}" + e.toString());
		} finally {
			httpPost.releaseConnection();
		}
		return Str;
	}

	/**
	 * 图片验证码的请求验证、
	 */
	@SuppressWarnings("rawtypes")
	public void imgCodeValid() {
		// ----请求验证码
		Map imgClient = hrbg.reqImgGet("https://www.xuanniu.com/v1/api0/image/captcha?action=register");
		// -----获取执行的client和图片验证码
		HttpClient httpclient = (HttpClient) imgClient.get("client");
		String imgCode = (String) imgClient.get("ImgCode");
//		System.out.println("imgCode: "+imgCode);

		// ----验证验证码
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("action", "register"));
		params.add(new BasicNameValuePair("code", imgCode));

		String validUrl = "https://www.xuanniu.com/v1/api0/captcha/valid";

		// ----验证码的验证
		String imageCodeResult = basePost(httpclient, validUrl, params);
		System.out.println("imageCode result: "+imageCodeResult);
	}

	/**
	 * 手机短信、发送、验证、密码设置
	 * 
	 * @param mobile
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public void phone(String mobile) throws Exception {
		System.out.println("====手机短信发送、验证====");
		JedisCommon jc = new JedisCommon();
		jc.flushDb();
		// ====step1: 手机短信发送请求url================================
		String phoneCodeApplyUrl = "https://www.xuanniu.com/v1/api0/register/apply";
		// ----step1.0: 手机短信发送请求、获得client
		Map imgClient = hrbg.reqImgGet("https://www.xuanniu.com/v1/api0/image/captcha?action=register");
		HttpClient httpclient = (HttpClient) imgClient.get("client");
		// ----step1.1: 手机短信发送请求
		List<NameValuePair> applyParams = new ArrayList<NameValuePair>();
		applyParams.add(new BasicNameValuePair("mobile", mobile));		
		basePost(httpclient, phoneCodeApplyUrl, applyParams);

		// ====step2: 手机短信验证请求验证url===============================
		String phoneVerifyUrl = "https://www.xuanniu.com/v1/api0/mobile/register/check";
		// ---- step2.1: 短信验证参数构造
		String token = (String) imgClient.get("token");

		String SecurityTokenTemp = "SecurityTokenTemp_" + token + "_1_register";
		JSONObject jsobsTt = JSONObject.fromObject(jc.getString(SecurityTokenTemp));
		String codekeyVal = (String) jsobsTt.get("codeKey");

		String SecurityVerifyCode = "SecurityVerifyCode_" + codekeyVal;
		JSONObject jsobsVc = JSONObject.fromObject(jc.getString(SecurityVerifyCode));
		String mobileCode = (String) jsobsVc.get("code");

		System.out.println("mobile code is: " + mobileCode);

		// ----2.2 短信验证请求
		List<NameValuePair> verifyParams = new ArrayList<NameValuePair>();
		verifyParams.add(new BasicNameValuePair("mobileCode", mobileCode));

		basePost(httpclient, phoneVerifyUrl, verifyParams);

		// ====step4: 手机密码设置

		String pwdSetUrl = "https://security.xuanniu.com/v1/api0/security/register?jsonpcallback=callback&password=123456&terminalType=1";			
		hrbg.reqTextGet(httpclient, pwdSetUrl);
		
	}
	
	public static void main(String[] args) throws Exception {
		HttpRegistPost hrp = new HttpRegistPost();
		hrp.phone("15313926589");
//		hrp.imgCodeValid();
		
	}
}
