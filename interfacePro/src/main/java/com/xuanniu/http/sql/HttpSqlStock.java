package com.xuanniu.http.sql;

import xuanniuSgq.Xuanniu_db;
import net.sf.json.JSONObject;

/**
 * @author sungq
 * @version 创建时间：2015年6月15日 下午6:42:22 类说明
 */

public class HttpSqlStock {
	static Xuanniu_db xdb = new Xuanniu_db();

	/**
	 * stock_financing中查找数据
	 * 
	 * @param param
	 * @param searchParam
	 * @return
	 */
	public static String getStockFinancing(String name, String searchParam) {
		String sql = "select * from xuanniu_trade.stock_financing where name in('" + name + "');";
		JSONObject jsob = xdb.searchColumn("xuanniu_trade", sql);
		String paramVal = jsob.getString(searchParam);
		return paramVal;
	}
	/**
	 * stock_financing中更新数据
	 * @param end_time
	 * @param stock_financing_id
	 */
	public static void updateStockFinancing(String end_time, String stock_financing_id) {
		String sql = "update xuanniu_trade.stock_financing  set end_time = '"+end_time+"' where stock_financing_id in ('" + stock_financing_id + "')";
		System.out.println("updateStockFinancing sql: "+sql);
		xdb.updateColumn("xuanniu_trade", sql);
	}
	
	/**
	 * stock_financing_bill中更新数据
	 * @param bill_time
	 * @param stock_financing_id
	 */
	public static void updateStockFinancingBill(String bill_time,String stock_financing_id ){
		String sql = " update xuanniu_trade.stock_financing_bill set bill_time = '"+bill_time+"' where stock_financing_id in('"+stock_financing_id +"')";
		System.out.println("updateStockFinancingBill sql: "+sql);
		xdb.updateColumn("xuanniu_trade", sql);
		
	}
	/**
	 * stock_financing_order表中取得数据、
	 * 
	 * @param param
	 * @param searchParam
	 * @return
	 */
	public static String getStockFinancingOrder(String stock_financing_id, String searchParam) {
		String sql = "select * from xuanniu_trade.stock_financing_order where stock_financing_id in ('" + stock_financing_id + "');";
		// System.out.println("sql : "+sql);
		JSONObject jsob = xdb.searchColumn("xuanniu_trade", sql);
		String paramVal = jsob.getString(searchParam);
		return paramVal;
	}

	/**
	 * stock_financing_detail表中取得数据、
	 * 
	 * @param stock_financing_id
	 * @param searchParam
	 * @return
	 */
	public static String getStockFinancingDetail(String stock_financing_id, String searchParam) {
		String sql = "select * from xuanniu_trade.stock_financing_detail where stock_financing_id in ('" + stock_financing_id + "')";
		JSONObject jsob = xdb.searchColumn("xuanniu_trade", sql);
		String paramVal = jsob.getString(searchParam);
		return paramVal;
	}

	public static void main(String[] args) {
		String stock_financing_id = getStockFinancing("0616162916", "stock_financing_id");
		System.out.println("stock financing id is: " + stock_financing_id);
		

		// ----
//		String stockOrder_id = getStockFinancingOrder(stock_financing_id, "stock_financing_order_id");
//		System.out.println("stock financing_order_id is: " + stockOrder_id);
//
//		String stockOrder_uid = getStockFinancingOrder(stock_financing_id, "uid");
//		System.out.println("stock uid is: " + stockOrder_uid);
//		// ----
//		String stock_financing_detail_id = getStockFinancingDetail(stock_financing_id, "stock_financing_detail_id");
//		System.out.println("stock_financing_detail_id  is: " + stock_financing_detail_id);
//		updateStockFinancing("2015-06-17",stock_financing_id);
		updateStockFinancingBill("2015-06-18",stock_financing_id);
	}
}
