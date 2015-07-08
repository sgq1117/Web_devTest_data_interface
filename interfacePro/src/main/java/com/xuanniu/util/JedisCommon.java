package com.xuanniu.util;

import java.util.Iterator;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author sungq
 * @version 类说明
 */

public class JedisCommon {

	private static Jedis jedis;

	public void initial() {
		JedisPool pool = new JedisPool("172.32.21.9", 6379);
		jedis = pool.getResource();
	}

	// --set_basic--
	public void setString(String keyStr, String valueStr) {
		initial();
		jedis.set(keyStr, valueStr);
	}

	// --set_someTime <一段时间后无效>
	public void setStringTime(String keyStr, int n, String valueStr) {
		initial();
		jedis.setex(keyStr, 2, valueStr);
	}

	// --get_basic
	public String getString(String keyStr) {
		initial();
		String value = null;
		value = jedis.get(keyStr);
//		System.out.println("redis value is: "+value);
		return value;
	}

	// --del_basic
	public void delString(String keyStr) {
		initial();
		jedis.del(keyStr);
	}

	// --flush db
	public void flushDb() {
		initial();
		jedis.flushDB();
	}

	// --Get all jedis key
	public void getAllRedisKey() {
		initial();
		Set keys = jedis.keys("*");// 列出所有的key，查找特定的key如：redis.keys("foo")
		Iterator t1 = keys.iterator();
		while (t1.hasNext()) {
			Object obj1 = t1.next();
			System.out.println(obj1);
		}
	}

	public static void main(String[] args) {
		// JedisPool pool = new JedisPool("172.32.21.9", 6379);
		// Jedis jedis = pool.getResource();
		//
		// jedis.set("site", "dataguru");
		// System.out.println(jedis.get("site"));
		//
		// jedis.append("site", " is my lover");
		// System.out.println(jedis.get("site"));
		//
		// jedis.del("site"); // 删除某个键
		// System.out.println(jedis.get("site"));
		//
		// jedis.mset("name", "liuling", "age", "23", "qq", "476777389");
		// jedis.incr("age"); // 进行加1操作
		// System.out.println(jedis.get("name") + "-" + jedis.get("age") + "-"
		// + jedis.get("qq"));

		// JedisCommon jc = new JedisCommon();
		// jc.setString("hah", "havel");
		// System.out.println(jc.getString("hah"));
		// jc.delString("hah");
		// System.out.println(jc.getString("hah"));

		JedisCommon jc = new JedisCommon();
		// -------basic set time/get---
		// jc.setStringTime("hah", 2, "hah_test");
		//
		// System.out.println("Before: "+jc.getString("hah"));
		// try {
		// Thread.sleep(3000);
		// } catch (InterruptedException e) {
		// }
		//
		// System.out.println("After: "+jc.getString("hah"));

		// ---flushDb
		System.out.println("Before:");
//		jc.setString("testCheck", "checkit");
		jc.getAllRedisKey();
		jc.flushDb();
//		System.out.println("After:");
//		jc.getAllRedisKey();
		
//		jc.getString("SecurityTokenTemp_AE78A988E3A9F32735B5D3BE117D321C_3_register");

	}
}
