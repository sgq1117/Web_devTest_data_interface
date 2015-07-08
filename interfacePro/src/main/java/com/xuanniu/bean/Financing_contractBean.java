package com.xuanniu.bean;

import xuanniuSgq.Xuanniu_db;

import com.db.trade.Financing_contract;
import com.xuanniu.util.CalcNum;

/**
 * @author sungq
 * @version 创建时间：2015年5月16日 下午2:05:25 类说明
 */
public class Financing_contractBean {
	Financing_contract fc = new Financing_contract();
	CalcNum cn = new CalcNum();
	Xuanniu_db xdb = new Xuanniu_db();

	public void getRelateInfo(String financing_contract_sn) {

		String financing_contract_id = fc.getFinancing_contract_id(financing_contract_sn);
		System.out.print("financing_contract_id :" + financing_contract_id + "\t");

		String financing_status = fc.getFinancing_status(financing_contract_sn);
		System.out.println("financing_status" + financing_status);
	}

	public static void main(String[] args) {
		Financing_contractBean fcb = new Financing_contractBean();
		fcb.getRelateInfo("PZ499");
		
		
	}
}
