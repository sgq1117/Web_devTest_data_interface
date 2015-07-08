package com.xuanniu.util;

import net.sf.json.JSONObject;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author sungq
 * @version 类说明
 */

public class JsonData {

	/**
	 * Get speicial Data from jsonString
	 * 
	 * @param jsonStr
	 * @param param
	 * @return
	 */
	public String getJsonData(String jsonStr, String param) {
		String paramData = null;
		// String jsonStr = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = mapper.readTree(jsonStr);
			paramData = node.get(param).toString();
		} catch (Exception e) {
			System.out.println("get Content msg String error: " + e.toString());
		}
		return paramData;
	}
	
	/**
	 * 把配资返回的结果转成json形式
	 * @param str
	 * @return
	 */
	public String convertToJsonStr(String str) {
		String jsonStr = null;
		String str1 = str.replaceAll("var INIT_RULE_DATA =", "");
		jsonStr = str1.trim();
		return jsonStr;
	}

	/**
	 * 获取json 数值对应的数据、为string
	 * @param str
	 * @param param
	 * @return
	 */
	public String getConentMsg(String str, String param) {
		String paramData = null;
		String jsonStr = null;
		try {
			jsonStr = convertToJsonStr(str);
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = mapper.readTree(jsonStr);
			paramData = node.get(param).toString();
		} catch (Exception e) {
			System.out.println("get Content msg String error: " + e.toString());
		}
		return paramData;
	}

	/**
	 * 返回jsonObj中指定的字符串
	 * @param jsonObj
	 * @param param
	 * @return
	 */
	public String getJsonData(JSONObject jsonObj,String param) {
		String paramData = null;
		String jsonStr = jsonObj.toString();
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = mapper.readTree(jsonStr);
			paramData = node.get(param).toString();
		} catch (Exception e) {
			System.out.println("get Content msg String error: " + e.toString());
		}
		return paramData;
		
	}
}
