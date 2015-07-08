package com.xuanniu.http.sql;

import xuanniuSgq.Xuanniu_db;
import net.sf.json.JSONObject;

/**
 * @author sungq
 * @version 创建时间：2015年6月3日 上午11:29:58 类说明
 */

public class HttpSqlAcitivity {
	static Xuanniu_db xdb = new Xuanniu_db();

	/**
	 * xuanniu_security.recommend中插入推荐数据
	 * 
	 * @param uid
	 * @param recommend_type
	 * @param source_id
	 * @param activity_id
	 */
	public static void insertRecommend(String uid, String recommend_type, String source_id, String activity_id) {
		String sql = "insert into xuanniu_security.recommend (uid,recommend_type,source_id,create_time,activity_id) value ('" + uid + "','" + recommend_type + "','" + source_id + "',now(),'" + activity_id + "')";
		xdb.insert("xuanniu_trade", sql);
	}
	
	/**
	 * 删除recommend表中的推荐人
	 * @param source_id
	 */
	public static void delRecoment(String source_id){
		String sql = "delete from xuanniu_security.recommend where source_id in('"+source_id+"')";
		xdb.delRow("xuanniu_trade", sql);
	}
	/**
	 * gift_user表中、查找指定uid、指定状态status、的关键字段
	 * 
	 * @param uid
	 * @param status
	 * @param param
	 * @return
	 */
	public static String getGu3param(String uid, String status, String param) {
		String sql = "select * from xuanniu_trade.gift_user where uid in('" + uid + "') and status in ('" + status + "')";
		String paramStr = null;
		JSONObject jsob = xdb.searchColumn("xuanniu_trade", sql);
//		 System.out.println("sql search result jsob: " + jsob);
		if (jsob.toString().equals("{}")) {
			// System.out.println("jsob is: null");
			paramStr = null;
		} else {
			paramStr = jsob.getString(param);

		}
		return paramStr;
	}

	/**
	 * 删除gift_user表中的数据
	 * 
	 * @param gift_user_id
	 * @param param
	 */
	public static void delGiftUserColumn(String gift_user_id) {
		String sql = "delete from xuanniu_trade.gift_user where gift_user_id in('" + gift_user_id + "')";
		int delColumn = xdb.delRow("xuanniu_trade", sql);
		// System.out.println("delColumn: "+delColumn);

	}

	/**
	 * gift_user表中、查找记录、
	 * 
	 * @param gift_user_id
	 * @param param
	 * @return
	 */
	public static String getGu2param(String gift_user_id, String param) {
		String sql = "select * from xuanniu_trade.gift_user where gift_user_id in('" + gift_user_id + "')";
		String paramStr = null;
		JSONObject jsob = xdb.searchColumn("xuanniu_trade", sql);
		if (jsob.toString().equals("{}")) {
			// System.out.println("jsob is: null");
			paramStr = null;
		} else {
			paramStr = jsob.getString(param);
		}
		return paramStr;
	}

	public static void main(String[] args) {
		// delGiftUserColumn("125");

//		String gift_user_id_a = getGu3param("524", "1", "gift_user_id");
//		System.out.println("Before: gift_user_id: " + gift_user_id_a);
//
//		delGiftUserColumn(gift_user_id_a);
//
//		String gift_user_id_b = getGu2param("gift_user_id_a", "gift_user_id");
//		System.out.println("After: gift_user_id: " + gift_user_id_b);
		
		insertRecommend("526","1","524","1");
	}

}
