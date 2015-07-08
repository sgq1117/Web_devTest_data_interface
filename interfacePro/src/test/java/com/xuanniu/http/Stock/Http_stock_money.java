package com.xuanniu.http.Stock;

/** 
 * @author sungq 
 * @version 创建时间：2015年6月15日 下午5:42:24 
 * 类说明 
 */

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.xuanniu.http.login.mfbusiness.HttpLoginBackBusiness;
import com.xuanniu.http.login.mfbusiness.HttpLoginFrontBusiness;
import com.xuanniu.http.sql.HttpSqlStock;

public class Http_stock_money {
	HttpLoginBackBusiness hlbb = new HttpLoginBackBusiness();
	HttpLoginFrontBusiness hlfb = new HttpLoginFrontBusiness();
	// ----step1: 投标名称根据时间戳来定----
	static long currentTime = System.currentTimeMillis();
	Date da = new Date(currentTime);
	SimpleDateFormat dateFormat = new SimpleDateFormat("MMddHHmmss");
	String time = dateFormat.format(da);
	// --------
	String name = time;
	// ----step2: 前台登陆账号
	String tbMobile = "99913920001";
	String tbPassword = "123456";

	@Test
	public void Http_stock_moneyAllTest() {
		// ----step1: 添加炒股理财项目
		System.out.println("===={添加炒股理财项目、发标的、投标、开账户、资金划转、模拟派息<更改数据库中的字段>}====");
		hlbb.backStockAddProPost("", name, "4000", "3", "0.3", "0.99", "3000", "Mon Jun 15 2015 00:00:00 GMT+0800", "Fri Jul 31 2015 00:00:00 GMT+0800", "3");
		System.out.print("	项目名称: " + name);
		// ----step2: 发布标的
		String stock_financing_id = HttpSqlStock.getStockFinancing(name, "stock_financing_id");
		System.out.println("	stock_financing_id: " + stock_financing_id);
		hlbb.backStockSendBiaoPost(stock_financing_id);
		// ----step3: 投标
		String url = "https://www.xuanniu.com/stockBid?giftId=0&stockFinancingId=" + stock_financing_id + "&amount=4000";
		hlfb.stockToubiaoGet(url, tbMobile, tbPassword);
		// ----step4：开通操盘账户
		String stock_financing_order_id = HttpSqlStock.getStockFinancingOrder(stock_financing_id, "stock_financing_order_id");
		String uid = HttpSqlStock.getStockFinancingOrder(stock_financing_id, "uid");
		hlbb.backStockCreateTradeAccountPost(stock_financing_order_id, uid, stock_financing_id);
		// ----step5:资金划转
		String stock_financing_detail_id = HttpSqlStock.getStockFinancingDetail(stock_financing_id, "stock_financing_detail_id");
		hlbb.backStockTransformPost(stock_financing_detail_id);

		// ---- step6、模拟派息、更改数据库中的字段
		HttpSqlStock.updateStockFinancing("2015-06-18", stock_financing_id);
		HttpSqlStock.updateStockFinancingBill("2015-06-17", stock_financing_id);

	}
	
	
//	@Test
	public void Http_stock_partITest() {
		// ----step1: 添加炒股理财项目
		System.out.println("===={添加炒股理财项目、发标的、}====");
		hlbb.backStockAddProPost("", name, "4000", "4", "0.3", "0.99", "3000", "Mon Jun 15 2015 00:00:00 GMT+0800", "Mon Aug 31 2015 00:00:00 GMT+0800", "3");
		System.out.println("	项目名称: " + name);
		// ----step2: 发布标的
		String stock_financing_id = HttpSqlStock.getStockFinancing(name, "stock_financing_id");
		hlbb.backStockSendBiaoPost(stock_financing_id);
	}

	public static void main(String[] args) {

		// ----发布 post: https://back.xuanniu.com/stock/publish id=31
		// ----投标 get:
		// https://www.xuanniu.com/stockBid?giftId=0&stockFinancingId=31&amount=1000
	}
}
