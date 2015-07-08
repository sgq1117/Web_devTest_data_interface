package com.xuanniu.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author sungq
 * @version 创建时间：2015年5月16日 上午10:35:53 类说明
 */

public class CalcNum {

	/**
	 * 求和
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public BigDecimal add(BigDecimal a, BigDecimal b) {
		BigDecimal result;
		result = a.add(b);
		return result;
	}
	/**
	 * 相减
	 * @param a
	 * @param b
	 * @return
	 */
	public BigDecimal sub(BigDecimal a, BigDecimal b) {
		BigDecimal result;
		result = a.subtract(b);
		return result;
	}
	/**
	 * 相乘
	 * @param a
	 * @param b
	 * @return
	 */
	public BigDecimal mul(BigDecimal a, BigDecimal b) {
		BigDecimal result;
		result = a.multiply(b);
		return result;
	}
	/**
	 * 相除
	 * @param a
	 * @param b
	 * @return
	 */
	public BigDecimal div(BigDecimal a, BigDecimal b){
		BigDecimal result;
		result = a.divide(b,0,RoundingMode.UP);
		return result;
	}
	
	/**
	 * 数值大小比较
	 * @param a
	 * @param b
	 * @return
	 */
	public int comparTo(BigDecimal a, BigDecimal b){
		int r = a.compareTo(b);
		return r;
	}
	
}
