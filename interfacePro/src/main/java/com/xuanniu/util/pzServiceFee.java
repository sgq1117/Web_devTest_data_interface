package com.xuanniu.util;

import java.math.BigDecimal;

/**
 * @author sungq
 * @version 创建时间：2015年6月6日 下午2:09:58 类说明
 */

public class pzServiceFee {
	// {100:0.004, 200:0.007, 300:0.009, 400:0.009, 500:0.009}

	public static void main(String[] args) {
		// BigDecimal margin = new BigDecimal("15000");

		BigDecimal[] margin = { new BigDecimal(5000), new BigDecimal(10000), new BigDecimal(15000), new BigDecimal(20000), 
				new BigDecimal(25000), new BigDecimal(30000), new BigDecimal(35000), new BigDecimal(40000) };

		BigDecimal[] monServiceFee = { new BigDecimal("0.004"), new BigDecimal("0.007"), new BigDecimal("0.009"), new BigDecimal("0.009"), new BigDecimal("0.009") };
		BigDecimal monInterest = new BigDecimal("0.019");

		BigDecimal dayServiceFee = new BigDecimal("0.0012");
		BigDecimal dayNum = new BigDecimal("2");
		System.out.println("按月==================");
		// ----
		BigDecimal sumA = margin[0].add(margin[0].multiply(monServiceFee[0])).add(margin[0].multiply(monInterest));
		BigDecimal sumB = margin[0].add(margin[1].multiply(monServiceFee[1])).add(margin[1].multiply(monInterest));
		BigDecimal sumC = margin[0].add(margin[2].multiply(monServiceFee[2])).add(margin[2].multiply(monInterest));
		BigDecimal sumD = margin[0].add(margin[3].multiply(monServiceFee[3])).add(margin[3].multiply(monInterest));
		BigDecimal sumE = margin[0].add(margin[4].multiply(monServiceFee[4])).add(margin[4].multiply(monInterest));
		// ----
	
		System.out.println("一倍、借款5000  的月服务费: " + margin[0].multiply(monServiceFee[0]) + " 利息: " + margin[0].multiply(monInterest) + " 共减少: " + sumA);
		System.out.println("二倍、借款10000的月服务费: " + margin[1].multiply(monServiceFee[1]) + " 利息: " + margin[1].multiply(monInterest) + " 共减少: " + sumB);
		System.out.println("三倍、借款15000的月服务费: " + margin[2].multiply(monServiceFee[2]) + " 利息: " + margin[2].multiply(monInterest) + " 共减少: " + sumC);
		System.out.println("四倍、借款20000的月服务费: " + margin[3].multiply(monServiceFee[3]) + " 利息: " + margin[3].multiply(monInterest) + " 共减少: " + sumD);
		System.out.println("五倍、借款25000的月服务费: " + margin[4].multiply(monServiceFee[4]) + " 利息: " + margin[4].multiply(monInterest) + " 共减少: " + sumE);
		// --

		// ----
		System.out.println("按天==================");

		System.out.println("一倍、借款5000  的天服务费: " + margin[0].multiply(dayServiceFee).multiply(dayNum));
		System.out.println("二倍、借款10000的天服务费: " + margin[1].multiply(dayServiceFee).multiply(dayNum));
		System.out.println("三倍、借款15000的天服务费: " + margin[2].multiply(dayServiceFee).multiply(dayNum));
		System.out.println("四倍、借款20000的天服务费: " + margin[3].multiply(dayServiceFee).multiply(dayNum));
		System.out.println("五倍、借款25000的天服务费: " + margin[4].multiply(dayServiceFee).multiply(dayNum));
		System.out.println("六倍、借款15000的天服务费: " + margin[5].multiply(dayServiceFee).multiply(dayNum));
		System.out.println("七倍、借款20000的天服务费: " + margin[6].multiply(dayServiceFee).multiply(dayNum));
		System.out.println("八倍、借款25000的天服务费: " + margin[7].multiply(dayServiceFee).multiply(dayNum));

	}
}
