package com.xuanniu.bean;

import java.math.BigDecimal;

import com.db.trade.Financing_rule;

/**
 * @author sungq
 * @version 创建时间：2015年5月16日 下午3:58:51 类说明
 */

public class FinancingRuleBean {
	Financing_rule fr = new Financing_rule();

	/**
	 * 取得借款比例、按天、按月都适用
	 * @param id ----杠杆比例、界面传送的id
	 * @return
	 */
	public BigDecimal getFinancing_rate(int id) {
		BigDecimal frate = fr.getFinancing_rate(id);
		return frate;
	}
	
	public static void main(String[] args){
		FinancingRuleBean frb = new FinancingRuleBean();
		System.out.println(frb.getFinancing_rate(13));
	}
}
