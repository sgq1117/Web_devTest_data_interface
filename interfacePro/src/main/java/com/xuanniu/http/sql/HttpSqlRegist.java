package com.xuanniu.http.sql;

import com.xuanniu.util.JedisCommon;

import net.sf.json.JSONObject;
import xuanniuSgq.Xuanniu_db;

/**
 * @author sungq
 * @version 创建时间：2015年6月1日 上午11:58:35 类说明
 */
public class HttpSqlRegist {
	static Xuanniu_db xdb = new Xuanniu_db();
	static JedisCommon jc = new JedisCommon();
	/**
	 * 注册前清除无用的数据
	 * @param mobile
	 */
	public static void clsBeforeRegist(String mobile) {
		
		String sql0 = "select id from xuanniu_security.security_user";
		//----操作sql
		String sql1 = "delete from security_user where mobile in (" + mobile + ")";
		String sql2 = "delete from security_user_protect where uid not in (" + sql0 + ");";
		String sql3 = "delete from account where uid not in (" + sql0 + ")";
		String sql4 = "delete from security_token where uid not in (" + sql0 + ")";
		String sql5 = "delete from security_token_temp where uid not in (" + sql0 + ")";
		String sql6 = "delete from security_fail_counter where token not in (" + sql0 + ") ";
		String sql7 = "delete from security_verify_code where code_key not in (" + sql0 + ")";
		String sql8 = "delete from xuanniu_security.recommend where uid not in (" + sql0 + ")";
		String sql9 = "delete from xuanniu_trade.recommend where source_id not in (" + sql0 + ")";
		String sql10 = "delete from xuanniu_trade.recommend_detail where source_id not in (" + sql0 + ")";
		String sql11 = "delete from xuanniu_trade.recommend_summary where source_id not in (" + sql0 + ")";

		// System.out.println("sql2:" + sql2);
		xdb.delRow("xuanniu_security", sql1);
		xdb.delRow("xuanniu_security", sql2);
		xdb.delRow("xuanniu_security", sql3);
		xdb.delRow("xuanniu_security", sql4);
		xdb.delRow("xuanniu_security", sql5);
		xdb.delRow("xuanniu_security", sql6);
		xdb.delRow("xuanniu_security", sql7);
		xdb.delRow("xuanniu_security", sql8);
		xdb.delRow("xuanniu_security", sql9);
		xdb.delRow("xuanniu_security", sql10);
		xdb.delRow("xuanniu_security", sql11);

	}
	
	/**
	 * 取得用户的uid
	 * @param mobile
	 * @param param
	 * @return
	 */
	public static String getUid(String mobile,String param){
		String sql = "select * from xuanniu_security.security_user where mobile in('"+mobile+"')";
		JSONObject jsob = xdb.searchColumn("xuanniu_trade", sql);
		String paramStr = jsob.getString(param);
		return paramStr;
		
	}
	/**
	 * 身份认证
	 * @param mobile
	 * @param id_number
	 * @param name
	 */
	public static void auditStatus(String mobile,String id_number,String name){
//		String sql = "update xuanniu_security.security_user_protect set id_number = '320804197604099431',name = '洪大华',id_number_status ='1',id_photo_status='3' where uid in('567')";	
		String uid = getUid(mobile,"id");
		String sql = "update xuanniu_security.security_user_protect set id_number = '" + id_number+"',name = '" + name +"',id_number_status ='1',id_photo_status='2' where uid in ('"+uid+"')";
		System.out.println("sql: "+sql);
		xdb.updateColumn("xuanniu_trade", sql);
		jc.flushDb();
	}
	
	public static void main(String[] args) {
		HttpSqlRegist hrs = new HttpSqlRegist();
//		String registMobile = "15313926589";
//		hrs.clsBeforeRegist(registMobile);
		
//		String searchmobile = "99913920001";
//		String searchmobile = "99913920002";
//		String searchmobile = "99913920003";
//		System.out.println(searchmobile+" uid is:"+getUid(searchmobile,"id"));
		
		auditStatus("99913920001","f3ea90535183269b822353373d545aea0364a612b53f050c1562f9e38d32025a","雷峥1");
//		System.out.println();
	}
}
