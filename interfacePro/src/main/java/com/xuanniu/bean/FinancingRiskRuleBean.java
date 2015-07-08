package com.xuanniu.bean;

import java.math.BigDecimal;

import com.db.trade.Financing_config;
import com.db.trade.Financing_risk_rule;
import com.db.trade.Financing_rule;

/**
 * @author sungq
 * @version 创建时间：2015年5月16日 下午4:03:24 类说明
 */

public class FinancingRiskRuleBean {
	Financing_risk_rule frr = new Financing_risk_rule();
	Financing_config fcf = new Financing_config();

	/**
	 * 
	 * @param ruleId
	 * @param margin
	 * @return
	 */
	public BigDecimal calBorrow(int ruleId, BigDecimal margin) {
		FinancingRuleBean fr = new FinancingRuleBean();
		BigDecimal rule_rate = fr.getFinancing_rate(ruleId);
		BigDecimal borrow = margin.multiply(rule_rate);
		return borrow;
	}

	/**
	 * 单月服务费
	 * 
	 * @param borrow
	 * @param uid
	 * @return
	 */
	public BigDecimal getSingleMonthServiceFee(BigDecimal borrow, int uid) {
		BigDecimal Month_service_fee;
		BigDecimal month_service_rate = fcf.getMonth_service_rate(uid);
		Month_service_fee = borrow.multiply(month_service_rate);
		return Month_service_fee;
	}
	/**
	 * 单日服务费
	 * @param borrow
	 * @param uid
	 * @return
	 */
	public BigDecimal getSingDayServiceFee(BigDecimal borrow, int uid) {
		BigDecimal Day_service_fee;
		BigDecimal day_service_rate = fcf.getDay_service_rate(uid);
		Day_service_fee = borrow.multiply(day_service_rate);
		return Day_service_fee; 

	}
	/**
	 * 利息
	 * @param borrow
	 * @param interest
	 * @return
	 */
	public BigDecimal getInterestFee(BigDecimal borrow, BigDecimal interest){
		BigDecimal interestFee;
		interestFee = borrow.multiply(interest);
		return interestFee;
	}
	/**
	 * n天的服务费
	 * @param interestFee
	 * @param usedate
	 * @return
	 */
	
	

	/**
	 * 根据所配资金额确定 、平仓线、警告线所属范围
	 * 
	 * @param borrow
	 * @return
	 */
	public static int getLevel(BigDecimal borrow) {
		int id = 0;

		BigDecimal[] borrowLevel = new BigDecimal[5];
		borrowLevel[0] = new BigDecimal(1000);
		borrowLevel[1] = new BigDecimal(10000);
		borrowLevel[2] = new BigDecimal(100000);
		borrowLevel[3] = new BigDecimal(300000);
		borrowLevel[4] = new BigDecimal(500000);

		// ----根据借款额度定位平仓线、警告线所属范围
		if (((borrow.compareTo(borrowLevel[0]) == 1) || (borrow.compareTo(borrowLevel[0]) == 0)) && (borrow.compareTo(borrowLevel[1]) == -1)) {
			id = 1;
		} else if (((borrow.compareTo(borrowLevel[1]) == 1) || (borrow.compareTo(borrowLevel[1]) == 0)) && (borrow.compareTo(borrowLevel[2]) == -1)) {
			id = 2;
		} else if (((borrow.compareTo(borrowLevel[2]) == 1) || (borrow.compareTo(borrowLevel[2]) == 0)) && (borrow.compareTo(borrowLevel[3]) == -1)) {
			id = 3;
		} else if (((borrow.compareTo(borrowLevel[3]) == 1) || (borrow.compareTo(borrowLevel[3]) == 0)) && ((borrow.compareTo(borrowLevel[4]) == -1) || (borrow.compareTo(borrowLevel[4]) == 0))) {
			id = 4;
		} else {
			System.out.println("no vlaue");
		}
		return id;
	}

	/**
	 * 月警告线、borrow由calBorrow() 计算，id由 getLevel()计算
	 * 
	 * @param borrow
	 * @param id
	 * @return
	 */
	public BigDecimal getMonthWarnline(BigDecimal borrow) {
		int id = getLevel(borrow);
		BigDecimal month_warning_fee;
		BigDecimal month_warning_rate = frr.getMonth_warning_rate(id);
		month_warning_fee = borrow.multiply(month_warning_rate);
		return month_warning_fee;
	}

	/**
	 * 月平仓线
	 * 
	 * @param borrow
	 * @param id
	 * @return
	 */
	public BigDecimal getMonthForceline(BigDecimal borrow) {
		int id = getLevel(borrow);
		BigDecimal month_force_fee;
		BigDecimal month_force_rate = frr.getMonth_force_rate(id);
		month_force_fee = borrow.multiply(month_force_rate);
		return month_force_fee;
	}

	/**
	 * 天警告线
	 * 
	 * @param borrow
	 * @param id
	 * @return
	 */
	public BigDecimal getDayWarnline(BigDecimal borrow) {
		int id = getLevel(borrow);
		BigDecimal day_warning_fee;
		BigDecimal day_warning_rate = frr.getDay_warning_rate(id);
		day_warning_fee = borrow.multiply(day_warning_rate);
		return day_warning_fee;

	}

	/**
	 * 天平仓线的计算
	 * 
	 * @param borrow
	 * @param id
	 * @return
	 */
	public BigDecimal getDayForceline(BigDecimal borrow) {
		int id = getLevel(borrow);
		BigDecimal day_force_fee;
		BigDecimal day_force_rate = frr.getDay_force_rate(id);
		day_force_fee = borrow.multiply(day_force_rate);
		return day_force_fee;
	}

	public static void main(String[] args) {
		FinancingRiskRuleBean frrb = new FinancingRiskRuleBean();
		// System.out.println(getLevel(new BigDecimal(100000)));

		System.out.println(frrb.calBorrow(14, new BigDecimal(5000)));

		System.out.println(frrb.getMonthWarnline(new BigDecimal(10000)));
		System.out.println(frrb.getMonthForceline(new BigDecimal(10000)));
	}
}
