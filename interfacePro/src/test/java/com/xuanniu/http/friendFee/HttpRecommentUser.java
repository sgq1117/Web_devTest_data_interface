package com.xuanniu.http.friendFee;

/** 
 * @author sungq 
 * @version 创建时间：2015年6月10日 下午3:36:22 
 * 类说明 
 */

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.xuanniu.http.regist.HttpRegistPost;
import com.xuanniu.http.regist.HttpRegistPostTest;
import com.xuanniu.http.sql.HttpSqlAcitivity;
import com.xuanniu.http.sql.HttpSqlRegist;

public class HttpRecommentUser {
	HttpRegistPostTest hrpt = new HttpRegistPostTest();
	// ----
	// ----推荐人
	String mobileT = "99913920021";
	// ----被推荐人
	String mobile0 = "99913920030";
	String mobile1 = "99913920031";
	String mobile2 = "99913920032";
	String mobile3 = "99913920033";
	String mobile4 = "99913920034";
	String mobile5 = "99913920035";
	

	public void registAuditStatus(String mobile) throws Exception {

		// --------------
		String password = "123456";
		String id_number = "f3ea90535183269b822353373d545aea0364a612b53f050c1562f9e38d32025a";
		String name = "黄峥";
		// ----注册前清理
		HttpSqlRegist.clsBeforeRegist(mobile);
		// ----注册
		HttpRegistPost hrp = new HttpRegistPost();
		hrp.phone(mobile);
		// ---身份认证
		HttpSqlRegist.auditStatus(mobile, id_number, name);
		// ----充值
		hrpt.chongZhiTest(mobile, password);
	}

	@Test
	public void recommendUser() throws Exception {
		registAuditStatus(mobileT);
		
		registAuditStatus(mobile0);
		registAuditStatus(mobile1);
		registAuditStatus(mobile2);
		registAuditStatus(mobile3);
		registAuditStatus(mobile4);
		registAuditStatus(mobile5);

		/*
		 * 取得相关的uid
		 */
		String source_id = HttpSqlRegist.getUid(mobileT, "id");
		
		String uid0 = HttpSqlRegist.getUid(mobile0, "id");
		String uid1 = HttpSqlRegist.getUid(mobile1, "id");
		String uid2 = HttpSqlRegist.getUid(mobile2, "id");
		String uid3 = HttpSqlRegist.getUid(mobile3, "id");
		String uid4 = HttpSqlRegist.getUid(mobile4, "id");
		String uid5 = HttpSqlRegist.getUid(mobile5, "id");

		System.out.println("source_id: " + source_id);
		System.out.println("0、uid0: " + uid0);
		System.out.println("1、uid1: " + uid1);
		System.out.println("2、uid2： " + uid2);
		System.out.println("3、uid3: " + uid3);
		System.out.println("2、uid4： " + uid4);
		System.out.println("3、uid5: " + uid5);
		// ----删除推荐记录
		HttpSqlAcitivity.delRecoment(source_id);
		// ----插入推荐记录
		HttpSqlAcitivity.insertRecommend(uid0, "1", source_id, "0");
		HttpSqlAcitivity.insertRecommend(uid1, "1", source_id, "0");
		HttpSqlAcitivity.insertRecommend(uid2, "1", source_id, "0");
		HttpSqlAcitivity.insertRecommend(uid3, "1", source_id, "0");
		HttpSqlAcitivity.insertRecommend(uid4, "1", source_id, "0");
		HttpSqlAcitivity.insertRecommend(uid5, "1", source_id, "0");
		
	}

}
