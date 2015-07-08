package com.xuanniu.util;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.xuanniu.bean.FinancingRiskRuleBean;
import com.xuanniu.http.sql.HttpSqlMoney;

/**
 * @author sungq
 * @version 创建时间：2015年5月16日 下午6:34:53 类说明
 */

public class PzCalcMoney {

	/**
	 * 计算单月利息-根据借款额
	 * 
	 * @param borrow
	 * @param interest
	 * @return
	 */
	public static BigDecimal getMonInterestFee_borrow(BigDecimal borrow, String interest) {
		BigDecimal act1_interest = new BigDecimal(interest);
		BigDecimal act2_interest = act1_interest.divide(new BigDecimal("100"));
		BigDecimal interest_fee = borrow.multiply(act2_interest);
		// System.out.println("interest_fee: " + interest_fee);
		// System.out.println("inner borrow: "+borrow);
		// System.out.println("inner interest: "+interest);
		return interest_fee;

	}

	/**
	 * 取得单月服务费--根据借款额 >>根据ruleid、取得配资比例 ; 再根据配资比例、取得月服务费率
	 * 
	 * @param borrow
	 * @param uid
	 * @return
	 */
	public static BigDecimal getMonServiceFee_borrow(BigDecimal borrow, String ruleId, String uid) {
		// ----配资比率
		BigDecimal financing_rate = HttpSqlMoney.getFiancingRule(ruleId, "financing_rate");
		String financing_rate_str = financing_rate.multiply(new BigDecimal("100")).toString();
		// ----服务费率
		BigDecimal month_service_rate = HttpSqlMoney.getMonServiceRate(uid, financing_rate_str);
		// ---- 服务费
		BigDecimal month_service_fee = borrow.multiply(month_service_rate);
		// System.out.println("month_service_fee: " + month_service_fee);
		return month_service_fee;
	}

	/**
	 * 计算单月利息
	 * 
	 * @param borrow
	 * @param interest
	 * @return
	 */
	public static BigDecimal getMonInterestFee_mragin(String margin, String ruleId, String interest) {
		// ----
		BigDecimal borrow = getBorrow(margin, ruleId, "financing_rate");
		// ----
		BigDecimal act1_interest = new BigDecimal(interest);
		BigDecimal act2_interest = act1_interest.divide(new BigDecimal("100"));
		BigDecimal interest_fee = borrow.multiply(act2_interest);
		// System.out.println("interest_fee: " + interest_fee);
		// System.out.println("inner borrow: "+borrow);
		// System.out.println("inner interest: "+interest);
		return interest_fee;

	}

	/**
	 * 取得单月服务费
	 * 
	 * @param borrow
	 * @param uid
	 * @return
	 */
	public static BigDecimal getMonServiceFee_margin(String margin, String ruleId, String serv_uid) {
		BigDecimal borrow = getBorrow(margin, ruleId, "financing_rate");
		// ----配资比率
		BigDecimal financing_rate = HttpSqlMoney.getFiancingRule(ruleId, "financing_rate");
		String financing_rate_str = financing_rate.multiply(new BigDecimal("100")).toString();
		// ----服务费率
		BigDecimal month_service_rate = HttpSqlMoney.getMonServiceRate(serv_uid, financing_rate_str);
		// ----计算服务费
		BigDecimal month_service_fee = borrow.multiply(month_service_rate);
		// System.out.println("month_service_fee: " + month_service_fee);
		return month_service_fee;
	}

	/**
	 * 天配资、配资人总共减少的额度
	 * 
	 * @param margin
	 * @param ruleId
	 * @param serve_uid
	 * @param usedate
	 * @return
	 */
	public static BigDecimal getDayFee_pz_less(String margin, String ruleId, String serve_uid, String usedate) {
		BigDecimal borrow = getBorrow(margin, ruleId, "financing_rate");
		BigDecimal single_day_service_fee = getDayFee_Service_borrow(borrow, serve_uid);
		BigDecimal act_use_date = new BigDecimal(usedate);
		BigDecimal sum_day_servicefee = single_day_service_fee.multiply(act_use_date);
		// --本金+服务费
		BigDecimal decimal_margin = new BigDecimal(margin);
		BigDecimal pz_less = decimal_margin.add(sum_day_servicefee);
		// ---
		return pz_less;
	}

	/**
	 * 天配资、和 续期配资、总共缴纳的服务费
	 * 
	 * @param margin
	 * @param ruleId
	 * @param serve_uid
	 * @param usedate
	 * @return
	 */
	public static BigDecimal getDayFee_service_withxq_all(String margin, String ruleId, String serve_uid, String usedate) {
		BigDecimal borrow = getBorrow(margin, ruleId, "financing_rate");
		BigDecimal single_day_service_fee = getDayFee_Service_borrow(borrow, serve_uid);
		// ----配资时间
		BigDecimal act_use_date = new BigDecimal(usedate);
		BigDecimal sum_day_servicefee_withxq = single_day_service_fee.multiply(act_use_date).multiply(new BigDecimal("2"));
		return sum_day_servicefee_withxq;
	}

	/**
	 * 天配资、投标人增加的服务费
	 * 
	 * @param margin
	 * @param ruleId
	 * @param serve_uid
	 * @param p2pInterestByDay
	 * @param usedate
	 * @return
	 */
	public static BigDecimal getDayFee_service_tbwithxq_more(String margin, String ruleId, String serve_uid, String p2pInterestByDay, String usedate) {
		// ----一天的服务费
		BigDecimal borrow = getBorrow(margin, ruleId, "financing_rate");
		BigDecimal single_day_service_fee = getDayFee_Service_borrow(borrow, serve_uid);

		// ----投标人得到的费率
		BigDecimal decimal_p2pInterestByDay = new BigDecimal(p2pInterestByDay);
		BigDecimal tb_single_service_fee = single_day_service_fee.multiply(decimal_p2pInterestByDay);
		// System.out.println("投标人一天得到的利息是： " + tb_single_service_fee);
		// ----
		// String tradeCalcStart_Time = null;
		// if (tradeStartTime.equals("1") || tradeStartTime.equals("2") ) {
		// tradeCalcStart_Time = "4";
		// }
		BigDecimal decimal_usedate = new BigDecimal(usedate);
		// System.out.println("费用天数; "+decimal_usedate);
		BigDecimal tb_service_fee = tb_single_service_fee.multiply(decimal_usedate).multiply(new BigDecimal("2"));
		// System.out.println("投标人要得到的利息是:" + tb_service_fee);
		// ---------------
		return tb_service_fee;
	}

	/**
	 * 天配资、续期平台增加的部分
	 * 
	 * @param margin
	 * @param ruleId
	 * @param serve_uid
	 * @param usedate
	 * @param p2pInterestByDay
	 * @return
	 */

	public static BigDecimal getDayFee_service_ptwithXq_more(String margin, String ruleId, String serve_uid, String usedate, String p2pInterestByDay) {
		BigDecimal p2pInterestAll = new BigDecimal("1");
		BigDecimal decimal_p2pInterestByDay = new BigDecimal(p2pInterestByDay);
		BigDecimal pt_day_rate = p2pInterestAll.subtract(decimal_p2pInterestByDay);
		// ----总共的天服务费
		BigDecimal sum_day_servicefee = getDayFee_service_All(margin, ruleId, serve_uid, usedate);
		// ----平台可以增加的部分
		BigDecimal pt_service_fee = sum_day_servicefee.multiply(pt_day_rate).multiply(new BigDecimal("2"));
		return pt_service_fee;
	}

	/**
	 * N天配资共需要缴纳的天服务费
	 * 
	 * @param margin
	 * @param ruleId
	 * @param serve_uid
	 * @param usedate
	 * @return
	 */
	public static BigDecimal getDayFee_service_All(String margin, String ruleId, String serve_uid, String usedate) {
		BigDecimal borrow = getBorrow(margin, ruleId, "financing_rate");
		BigDecimal single_day_service_fee = getDayFee_Service_borrow(borrow, serve_uid);
		// ----配资时间
		BigDecimal act_use_date = new BigDecimal(usedate);
		BigDecimal sum_day_servicefee = single_day_service_fee.multiply(act_use_date);
		// System.out.println("sum_day_servicefee: " + sum_day_servicefee);
		return sum_day_servicefee;
	}

	/**
	 * 平台得到的总共服务费
	 * 
	 * @param all_service_fee
	 * @param p2pInterestByDay
	 * @return
	 */
	public static BigDecimal getDayFee_service_pt_more(String margin, String ruleId, String serve_uid, String usedate, String p2pInterestByDay) {
		BigDecimal p2pInterestAll = new BigDecimal("1");
		BigDecimal decimal_p2pInterestByDay = new BigDecimal(p2pInterestByDay);
		BigDecimal pt_day_rate = p2pInterestAll.subtract(decimal_p2pInterestByDay);
		// ----总共的天服务费
		BigDecimal sum_day_servicefee = getDayFee_service_All(margin, ruleId, serve_uid, usedate);
		// ----平台可以增加的部分
		BigDecimal pt_service_fee = sum_day_servicefee.multiply(pt_day_rate);
		return pt_service_fee;
	}

	/**
	 * 投标人、得到的服务费
	 * 
	 * @param margin
	 * @param ruleId
	 * @param serve_uid
	 * @param p2pInterestByDay
	 * @param tradeStartTime
	 * @return
	 */
	public static BigDecimal getDayFee_service_tb_less(String margin, String ruleId, String serve_uid, String p2pInterestByDay, String tradeStartTime, String tBamount) {
		// ----一天的服务费
		// ---- step1----服务费增加的部分
		BigDecimal borrow = getBorrow(margin, ruleId, "financing_rate");
		BigDecimal single_day_service_fee = getDayFee_Service_borrow(borrow, serve_uid);

		// ----投标人得到的费率
		BigDecimal decimal_p2pInterestByDay = new BigDecimal(p2pInterestByDay);
		BigDecimal tb_single_service_fee = single_day_service_fee.multiply(decimal_p2pInterestByDay);
		// System.out.println("投标人一天得到的利息是： " + tb_single_service_fee);
		// ----
		String tradeCalcStart_Time = null;
		if (tradeStartTime.equals("1")) {
			tradeCalcStart_Time = "2";
		} else {
			tradeCalcStart_Time = "1";
		}
		BigDecimal decimal_tradeStartTime = new BigDecimal(tradeCalcStart_Time);
		BigDecimal tb_service_fee = tb_single_service_fee.multiply(decimal_tradeStartTime);
		// System.out.println("投标人要得到的利息是:" + tb_service_fee);
		// -----step2------保证金和服务费做运算
		// ----投标资金
		BigDecimal decimal_tBamount = new BigDecimal(tBamount);
		BigDecimal tb_fee_change = decimal_tBamount.subtract(tb_service_fee);

		return tb_fee_change;
	}

	/**
	 * 天配资、投标人增加的服务费
	 * 
	 * @param margin
	 * @param ruleId
	 * @param serve_uid
	 * @param p2pInterestByDay
	 * @param tradeStartTime
	 * @return
	 */
	public static BigDecimal getDayFee_service_tb_more(String margin, String ruleId, String serve_uid, String p2pInterestByDay, String usedate) {
		// ----一天的服务费
		BigDecimal borrow = getBorrow(margin, ruleId, "financing_rate");
		BigDecimal single_day_service_fee = getDayFee_Service_borrow(borrow, serve_uid);

		// ----投标人得到的费率
		BigDecimal decimal_p2pInterestByDay = new BigDecimal(p2pInterestByDay);
		BigDecimal tb_single_service_fee = single_day_service_fee.multiply(decimal_p2pInterestByDay);
		// System.out.println("投标人一天得到的利息是： " + tb_single_service_fee);
		// ----
		// String tradeCalcStart_Time = null;
		// if (tradeStartTime.equals("1") || tradeStartTime.equals("2") ) {
		// tradeCalcStart_Time = "4";
		// }
		BigDecimal decimal_usedate = new BigDecimal(usedate);
		// System.out.println("费用天数; "+decimal_usedate);
		BigDecimal tb_service_fee = tb_single_service_fee.multiply(decimal_usedate);
		// System.out.println("投标人要得到的利息是:" + tb_service_fee);
		// ---------------
		return tb_service_fee;
	}
	/**
	 * 总共服务费、平台获得的部分
	 * @param borrow
	 * @param uid
	 * @param usedate
	 * @param p2pInterestByDay
	 * @return
	 */
	public static BigDecimal getDayFee_SumService_borrow_pt(BigDecimal borrow, String uid, String usedate,String p2pInterestByDay ) {
		BigDecimal day_service_fee = getDayFee_Service_borrow(borrow, uid);
		BigDecimal act_day = new BigDecimal(usedate);
		BigDecimal sum_day_servicefee = day_service_fee.multiply(act_day);
		// System.out.println("sum_day_servicefee: " + sum_day_servicefee);
		//----平台获得的比例部分:
		BigDecimal p2pInterestAll = new BigDecimal("1");
		BigDecimal decimal_p2pInterestByDay = new BigDecimal(p2pInterestByDay);
		BigDecimal pt_day_rate = p2pInterestAll.subtract(decimal_p2pInterestByDay);
		//----
		BigDecimal pt_sumDay_servicefee = sum_day_servicefee.multiply(pt_day_rate);
		//---
		return pt_sumDay_servicefee;
	}
	/**
	 * 
	 * @param borrow
	 * @param uid
	 * @param day
	 * @return
	 */
	public static BigDecimal getDayFee_SumService_borrow(BigDecimal borrow, String uid, String usedate) {
		BigDecimal day_service_fee = getDayFee_Service_borrow(borrow, uid);
		BigDecimal act_day = new BigDecimal(usedate);
		BigDecimal sum_day_servicefee = day_service_fee.multiply(act_day);
		// System.out.println("sum_day_servicefee: " + sum_day_servicefee);
		return sum_day_servicefee;
	}

	
	
	/**
	 * 
	 * 取得总共服务费
	 * 
	 * @param borrow
	 * @param uid
	 * @param param
	 * @return
	 */
	public static BigDecimal getDayFee_Service_borrow(BigDecimal borrow, String uid) {
		BigDecimal day_serviceRate = HttpSqlMoney.getDayServiceRate(uid);
		BigDecimal day_serviceFee = borrow.multiply(day_serviceRate);
		// System.out.println("serviceFee: " + serviceFee);
		return day_serviceFee;
	}

	/**
	 * 计算借款额度、适用于天、月
	 *
	 * 
	 * @param margin
	 * @param ruleId
	 * @param param
	 * @return
	 */
	public static BigDecimal getBorrow(String margin, String ruleId, String param) {
		BigDecimal rate = HttpSqlMoney.getFiancingRule(ruleId, param);
		BigDecimal act_margin = new BigDecimal(margin);
		BigDecimal borrow = act_margin.multiply(rate);
		// System.out.println("borrow:" + borrow);
		return borrow;
	}

	/**
	 * 月警告线
	 * 
	 * @param borrow
	 * @param uid
	 * @return
	 */
	public static BigDecimal getMonWarnFee(BigDecimal borrow, String uid) {
		String id = getFiRiskRule(borrow);
		BigDecimal month_warning_rate = HttpSqlMoney.getFinancingRiskRule(id, uid, "month_warning_rate");
		BigDecimal mon_warning_fee = borrow.multiply(month_warning_rate);
		// System.out.println("mon_warning_fee: " + mon_warning_fee);
		return mon_warning_fee;
	}

	/**
	 * 月平仓线
	 * 
	 * @param borrow
	 * @param uid
	 * @return
	 */
	public static BigDecimal getMonForceFee(BigDecimal borrow, String uid) {
		String id = getFiRiskRule(borrow);
		BigDecimal month_force_rate = HttpSqlMoney.getFinancingRiskRule(id, uid, "month_force_rate");
		BigDecimal mon_force_fee = borrow.multiply(month_force_rate);
		// System.out.println("mon_warning_fee: " + mon_force_fee);
		return mon_force_fee;
	}

	/**
	 * 天警告线
	 * 
	 * @param borrow
	 * @param uid
	 * @return
	 */
	public static BigDecimal getDayWarnFee(BigDecimal borrow, String uid) {
		String id = getFiRiskRule(borrow);
		BigDecimal day_warning_rate = HttpSqlMoney.getFinancingRiskRule(id, uid, "day_warning_rate");
		BigDecimal day_warning_fee = borrow.multiply(day_warning_rate);
		// System.out.println("day_warning_fee: " + day_warning_fee);
		return day_warning_fee;
	}

	public static BigDecimal getDayForceFee(BigDecimal borrow, String uid) {
		String id = getFiRiskRule(borrow);
		BigDecimal day_force_rate = HttpSqlMoney.getFinancingRiskRule(id, uid, "day_force_rate");
		BigDecimal day_force_fee = borrow.multiply(day_force_rate);
		// System.out.println("day_force_fee: " + day_force_fee);
		return day_force_fee;
	}

	/**
	 * 根据配资额度取得所在级别
	 * 
	 * @param borrow
	 * @return
	 */
	public static String getFiRiskRule(BigDecimal borrow) {
		String id = null;
		if (borrow.compareTo(new BigDecimal("10000")) == -1) {
			id = "1";
		} else if (((borrow.compareTo(new BigDecimal("10000")) == 1) || (borrow.compareTo(new BigDecimal("10000")) == 0)) && (borrow.compareTo(new BigDecimal("100000")) == -1)) {
			id = "2";
		} else if (((borrow.compareTo(new BigDecimal("100000")) == 1) || (borrow.compareTo(new BigDecimal("100000")) == 0)) && (borrow.compareTo(new BigDecimal("300000")) == -1)) {
			id = "3";
		} else if (((borrow.compareTo(new BigDecimal("300000")) == 1) || (borrow.compareTo(new BigDecimal("300000")) == 0)) && (borrow.compareTo(new BigDecimal("1000000")) == -1)) {
			id = "4";
		}
		// System.out.println("id :" + id);
		return id;
	}

	/**
	 * 单月产生服务费、资金: 保证金 * 比率
	 * 
	 * @param margin
	 * @param ruleId
	 * @param service_uid
	 * @return
	 */
	public static BigDecimal getMonFee_service_margin_pt(String margin, String ruleId, String service_uid) {
		BigDecimal borrow = getBorrow(margin, ruleId, "financing_rate");
		// ----单月服务费
		BigDecimal mon_service_fee = getMonServiceFee_borrow(borrow, ruleId, service_uid);
		// System.out.println("月服务费: " + mon_service_fee);
		// mon_service_fee.toString();
		return mon_service_fee;
	}

	/**
	 * 单月产生服务费、资金: 借款额度 * 比率
	 * 
	 * @param borrow
	 * @param ruleId
	 * @param service_uid
	 * @return
	 */
	public static BigDecimal getMonFee_service_borrow(BigDecimal borrow, String ruleId, String service_uid) {
		// ----单月服务费
		BigDecimal mon_service_fee = getMonServiceFee_borrow(borrow, ruleId, service_uid);
		// System.out.println("月服务费: " + mon_service_fee);
		// mon_service_fee.toString();
		return mon_service_fee;
	}

	/**
	 * 单月的 服务费+利息
	 * 
	 * @param margin
	 * @param ruleId
	 * @param interest
	 * @param serv_uid
	 * @return
	 */
	public static BigDecimal getMonFee_serveFee_interestFee(String margin, String ruleId, String interest, String serv_uid) {
		// ----单月利息
		BigDecimal single_mon_interestFee = getMonInterestFee_mragin(margin, ruleId, interest);
		// ----单月服务费
		BigDecimal single_mon_serveFee = getMonServiceFee_margin(margin, ruleId, serv_uid);
		// ----单月费用
		BigDecimal sum_fee = single_mon_interestFee.add(single_mon_serveFee);
		return sum_fee;
	}

	/**
	 * 配资人还需支付的罚息
	 * 
	 * @param margin
	 * @param ruleId
	 * @param interest
	 * @param serv_uid
	 * @param usedate
	 * @param financingPunishInterest
	 * @return
	 */
	public static BigDecimal getMonFee_fx_pz(String margin, String ruleId, String interest, String serv_uid, String usedate, String financingPunishInterest) {
		// ----单月利息
		BigDecimal single_mon_interestFee = getMonInterestFee_mragin(margin, ruleId, interest);
		// ----单月服务费
		BigDecimal single_mon_serveFee = getMonServiceFee_margin(margin, ruleId, serv_uid);
		// ----单月费用
		BigDecimal sum_fee = single_mon_interestFee.add(single_mon_serveFee);
		// ----总共额度的比率
		BigDecimal decimal_usedate = new BigDecimal(usedate);
		BigDecimal act_usedate = decimal_usedate.subtract(new BigDecimal("1"));
		BigDecimal also_sum_fee = sum_fee.multiply(act_usedate).multiply(new BigDecimal(financingPunishInterest));
		// ----
		return also_sum_fee;
	}

	/**
	 * 投标人分得罚息部分
	 * 
	 * @param also_sum_fee
	 * @param financingPunishInterest
	 * @return
	 */
	public static BigDecimal getMonFee_fx_tb(BigDecimal also_sum_fee, String p2pInterestByDay) {
		// --投标人分得罚息
		BigDecimal tb_faxi = also_sum_fee.multiply(new BigDecimal(p2pInterestByDay));
		// ----
		return tb_faxi;
	}

	/**
	 * 平台分得罚息部分
	 * 
	 * @param also_sum_fee
	 * @param also_tb_fx
	 * @return
	 */
	public static BigDecimal getMonFee_fx_pt(BigDecimal also_sum_fee, BigDecimal also_tb_fx) {
		BigDecimal pt_fi = also_sum_fee.subtract(also_tb_fx);
		return pt_fi;
	}

	/**
	 * 月配资：配资人减少： 本金+利息+服务费
	 * 
	 * @param margin
	 * @param ruleId
	 * @param service_uid
	 * @param warnForce_uid
	 * @param interest
	 * @return
	 */
	public static BigDecimal getMonFee_pz_less(String margin, String ruleId, String service_uid, String warnForce_uid, String interest) {
		System.out.print("=={月配资、配资人减少:}====");
		BigDecimal borrow = getBorrow(margin, ruleId, "financing_rate");
		// ----单月服务费
		BigDecimal mon_service_fee = getMonServiceFee_borrow(borrow, ruleId, service_uid);
		// System.out.println("月服务费: " + mon_service_fee);
		// ----单月利息
		BigDecimal mon_interest_fee = getMonInterestFee_borrow(borrow, interest);
		// System.out.println("月利息费：" + mon_interest_fee);
		// ----单月减少：即需付金额： 本金+服务费+利息
		BigDecimal margin_decimal = new BigDecimal(margin);
		BigDecimal mon_fee = margin_decimal.add(mon_service_fee).add(mon_interest_fee);
		System.out.println("即单月需要付金额：" + mon_fee);
		return mon_fee;
	}

	/**
	 * 投标人减少： 本金-服务费
	 * 
	 * @param margin
	 * @param ruleId
	 * @param service_uid
	 * @param warnForce_uid
	 * @param interest
	 * @return
	 */
	public static BigDecimal getMonFee_tb_less(String margin, String ruleId, String service_uid, String warnForce_uid, String interest) {
		System.out.print("=={月配资、投标人减少:}====");
		BigDecimal borrow = getBorrow(margin, ruleId, "financing_rate");
		// ----单月利息
		BigDecimal mon_interest_fee = getMonInterestFee_borrow(borrow, interest);
		System.out.println("月利息费：" + mon_interest_fee);
		// ----单月减少： 即投标资金-利息
		BigDecimal tb_decimal = borrow.subtract(mon_interest_fee);
		// ----
		return tb_decimal;

	}

	/**
	 * 平台增加： 服务费
	 * 
	 * @param margin
	 * @param ruleId
	 * @param service_uid
	 * @return
	 */
	public static BigDecimal getMonFee_pt_more(String margin, String ruleId, String service_uid) {
		// ----单月服务费、即平台增加
		System.out.print("=={月配资、平台增加:}====");
		BigDecimal borrow = getBorrow(margin, ruleId, "financing_rate");
		BigDecimal mon_service_fee = getMonServiceFee_borrow(borrow, ruleId, service_uid);
		System.out.println("月服务费: " + mon_service_fee);
		return mon_service_fee;
	}

	/**
	 * 月配资付款信息
	 * 
	 * @param margin
	 * @param ruleId
	 * @param service_uid
	 * @param warnForce_uid
	 * @param interest
	 */
	public static void getMonFee_output(String margin, String ruleId, String service_uid, String warnForce_uid, String interest) {
		System.out.println("	");

		BigDecimal borrow = getBorrow(margin, ruleId, "financing_rate");
		// ----单月服务费
		BigDecimal mon_service_fee = getMonServiceFee_borrow(borrow, ruleId, service_uid);
		System.out.println("月服务费: " + mon_service_fee);
		// ----单月利息
		BigDecimal mon_interest_fee = getMonInterestFee_borrow(borrow, interest);
		System.out.println("月利息费：" + mon_interest_fee);
		// ----单月需付金额： 本金+服务费+利息
		BigDecimal margin_decimal = new BigDecimal(margin);
		BigDecimal mon_fee = margin_decimal.add(mon_service_fee).add(mon_interest_fee);
		System.out.println("单月需要付金额：" + mon_fee);
		// -----月警告线
		BigDecimal mon_warn_fee = getMonWarnFee(borrow, warnForce_uid);
		System.out.println("月警告线: " + mon_warn_fee);
		// ----月平仓线
		BigDecimal mon_force_fee = getMonForceFee(borrow, warnForce_uid);
		System.out.println("月平仓线:" + mon_force_fee);
	}

	/**
	 * 天配资付款信息
	 * 
	 * @param margin
	 * @param ruleId
	 * @param service_uid
	 * @param warnForce_uid
	 * @param day
	 */
	public static void getDayFee_output(String margin, String ruleId, String service_uid, String warnForce_uid, String day) {
		BigDecimal borrow = getBorrow(margin, ruleId, "financing_rate");
		// ----单天服务费
		BigDecimal day_service_fee = getDayFee_Service_borrow(borrow, service_uid);
		System.out.println("天服务费: " + day_service_fee);
		// ----n天服务费
		BigDecimal sum_day_service_fee = getDayFee_SumService_borrow(borrow, service_uid, day);
		System.out.println(day + " :天 服务费 " + sum_day_service_fee);

		// ----那天需要付费: 本金+服务费
		BigDecimal margin_decimal = new BigDecimal(margin);
		BigDecimal sum_day_fee = margin_decimal.add(sum_day_service_fee);
		System.out.println(day + " :天  共需要付费: " + sum_day_fee);

		// ----天平仓线
		BigDecimal day_warn_fee = getDayWarnFee(borrow, warnForce_uid);
		System.out.println("天警告线: " + day_warn_fee);
		// ----天警告线
		BigDecimal day_force_fee = getDayForceFee(borrow, warnForce_uid);
		System.out.println("天平仓线:" + day_force_fee);
	}

	public static void main(String[] args) {
		PzCalcMoney cm = new PzCalcMoney();

		// cm.getMonFee("5000", "12", "0", "0", "1.9");
		cm.getMonFee_output("5000", "3", "0", "0", "4");
		// getFiRiskRule(new BigDecimal("8000"));
		// getFiRiskRule(new BigDecimal("80000"));
		// getFiRiskRule(new BigDecimal("100000"));
		// getFiRiskRule(new BigDecimal("300000"));

		// BigDecimal borrow = getBorrow("5000", "12", "financing_rate");
		// getMonServiceFee(borrow, "12", "0");

		// getDayServiceFee(borrow, "0");
		// getSumDayServiceFee(borrow, "0", "4");
		// getServiceFee(borrow, "0", "month_service_rate");
		// getServiceFee(borrow, "0", "day_service_rate");
		// getMonInterestFee(borrow, "1.9");
		// getMonWarnFee(borrow, "0");
		// getMonForceFee(borrow, "0");
		// getDayWarnFee(borrow, "0");
		// getDayForceFee(borrow, "0");

	}
}
