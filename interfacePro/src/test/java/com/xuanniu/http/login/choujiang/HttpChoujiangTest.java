package com.xuanniu.http.login.choujiang;

/** 
 * @author sungq 
 * @version 创建时间：2015年6月3日 上午10:46:34 
 * 类说明 
 */

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.xuanniu.http.login.mfbusiness.HttpLoginBackBusiness;
import com.xuanniu.http.login.mfbusiness.HttpLoginFrontBusiness;
import com.xuanniu.http.sql.HttpSqlRegist;
import com.xuanniu.http.sql.HttpSqlAcitivity;

public class HttpChoujiangTest {
	HttpLoginFrontBusiness hlfb = new HttpLoginFrontBusiness();
	HttpLoginBackBusiness hlbb = new HttpLoginBackBusiness();
	// ----账户
	String applyPzMobile = "99913920001";
	String applyPzPassword = "123456";

	String tbPzMobile_Va = "99913920002";
	String tbPzPassword_Va = "123456";

	static String tbPzMobile_Vb = "99913920003";
	String tbPzPassword_Vb = "123456";

//	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("===={清除gift_user中的抽奖记录}====");
		String uid = HttpSqlRegist.getUid(tbPzMobile_Vb, "id");

		String gift_user_id = HttpSqlAcitivity.getGu3param(uid, "1", "gift_user_id");

		System.out.println("gift_user_id: " + gift_user_id);
		HttpSqlAcitivity.delGiftUserColumn(gift_user_id);

	}
	
	@Test
	public void AddFriendTest() {
		System.out.println("====添加好友联系人====");
//		String regist_id = HttpRegistSql.getUid(tbPzMobile_Va, "id");
		String regist_id = "125";
		String recommend_type = "1";
		String source_id = HttpSqlRegist.getUid(tbPzMobile_Va, "id");
		String activity_id = "1";
		System.out.println("regist_id: " + regist_id + " source_id" + source_id);
		HttpSqlAcitivity.insertRecommend(regist_id, recommend_type, source_id, activity_id);
	}
	
	// @Test
	public void CjWithoutRecommendTest() {
		System.out.println("===={初次抽奖、无好友注册、仅能抽奖 一次、}====");
		// ----step1: 抽奖操作
		String url = "https://www.xuanniu.com/v1/api0/gift/lottery?jsonpcallback=jQuery1102039793504029512405_1433296029014&_=1433296029015";
		hlfb.choujiangGet(url, tbPzMobile_Vb, tbPzPassword_Vb);

		// ----step2: 取得抽奖后的 uid
		String uid = HttpSqlRegist.getUid(tbPzMobile_Vb, "id");
		String gift_user_id_a = HttpSqlAcitivity.getGu3param(uid, "1", "gift_user_id");
		System.out.println("First: gift_user_id: " + gift_user_id_a);
		// ---- step3: 抽奖
		hlfb.choujiangGet(url, tbPzMobile_Vb, tbPzPassword_Vb);

		// ----step3: 删除该抽奖记录
		HttpSqlAcitivity.delGiftUserColumn(gift_user_id_a);

		// ----step4: 再次抽奖
		hlfb.choujiangGet(url, tbPzMobile_Vb, tbPzPassword_Vb);

		// ----step5:取得抽奖后 的uid
		String uid2 = HttpSqlRegist.getUid(tbPzMobile_Vb, "id");
		String gift_user_id_a2 = HttpSqlAcitivity.getGu3param(uid2, "1", "gift_user_id");
		System.out.println("Second: gift_user_id: " + gift_user_id_a2);

		// ----step6: 删除该抽奖记录
		// HttpAcitivitySql.delGiftUserColumn(gift_user_id_a2);
	}

//	@Test
	public void CjWithRecommendTest() {
		System.out.println("===={推荐的好友注册、活动类型正确、最多能抽奖两次}====");
		String regist_id = HttpSqlRegist.getUid(tbPzMobile_Va, "id");
		String recommend_type = "1";
		String source_id = HttpSqlRegist.getUid(tbPzMobile_Vb, "id");
		String activity_id = "1";
		System.out.println("regist_id: " + regist_id + " source_id" + source_id);
		// ----step1: 抽奖--Ok
		String url = "https://www.xuanniu.com/v1/api0/gift/lottery?jsonpcallback=jQuery1102039793504029512405_1433296029014&_=1433296029015";
		hlfb.choujiangGet(url, tbPzMobile_Vb, tbPzPassword_Vb);

		// ----setp2: 抽奖--Nok
		hlfb.choujiangGet(url, tbPzMobile_Vb, tbPzPassword_Vb);
		// ----step3: 添加好友推荐人
		HttpSqlAcitivity.insertRecommend(regist_id, recommend_type, source_id, activity_id);
		// ----step4: 抽奖--Ok
		hlfb.choujiangGet(url, tbPzMobile_Vb, tbPzPassword_Vb);

		// ----step5:再抽奖----NOk
		hlfb.choujiangGet(url, tbPzMobile_Vb, tbPzPassword_Vb);

		// ---step6、删除推荐人
		HttpSqlAcitivity.delRecoment(source_id);

		// ----step7、删除添加的抽奖记录
		// String gift_user_id_v1 = HttpAcitivitySql.getGu3param(source_id, "1",
		// "gift_user_id");
		// HttpAcitivitySql.delGiftUserColumn(gift_user_id_v1);
		// String gift_user_id_v2 = HttpAcitivitySql.getGu3param(source_id, "1",
		// "gift_user_id");
		// HttpAcitivitySql.delGiftUserColumn(gift_user_id_v2);
		// System.out.println("gift_user_id_v1: " + gift_user_id_v1 +
		// " gift_user_id_v2: " + gift_user_id_v2);

	}

	// @Test
	public void CjWithWrongActivityTest() {
		System.out.println("===={推荐的好友注册、活动类型、正确错误、最多能抽奖二次、一次 }====");
		String regist_id = HttpSqlRegist.getUid(tbPzMobile_Va, "id");
		String recommend_type = "0";
		String source_id = HttpSqlRegist.getUid(tbPzMobile_Vb, "id");
		String activity_id = "1";
		System.out.println("regist_id: " + regist_id + " source_id" + source_id);
		// ----step1: 抽奖--Ok
		String url = "https://www.xuanniu.com/v1/api0/gift/lottery?jsonpcallback=jQuery1102039793504029512405_1433296029014&_=1433296029015";
		hlfb.choujiangGet(url, tbPzMobile_Vb, tbPzPassword_Vb);
		// ----setp2: 抽奖--Nok
		hlfb.choujiangGet(url, tbPzMobile_Vb, tbPzPassword_Vb);
		// ----step3: 添加好友推荐人
		HttpSqlAcitivity.insertRecommend(regist_id, recommend_type, source_id, activity_id);
		// ----step4: 抽奖--ok
		hlfb.choujiangGet(url, tbPzMobile_Vb, tbPzPassword_Vb);
		// ---step5: 删除推荐人
		HttpSqlAcitivity.delRecoment(source_id);

		// ----step7、删除添加的抽奖记录
		String gift_user_id_v1 = HttpSqlAcitivity.getGu3param(source_id, "1", "gift_user_id");
		HttpSqlAcitivity.delGiftUserColumn(gift_user_id_v1);
		String gift_user_id_v2 = HttpSqlAcitivity.getGu3param(source_id, "1", "gift_user_id");
		HttpSqlAcitivity.delGiftUserColumn(gift_user_id_v2);
		System.out.println("gift_user_id_v1: " + gift_user_id_v1 + " gift_user_id_v2: " + gift_user_id_v2);

	}
}
