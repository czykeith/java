package com.keith.core.util;

import java.math.BigDecimal;

/**
 * 浮点数精确计算工具类
 * @author czyke
 *
 */
public class DoubleCalUtil {
    /**
     * 加法
     */
	public static double add(double a,double b){
		BigDecimal aa=new BigDecimal(Double.toString(a));
		aa=aa.add(new BigDecimal(Double.toString(b)));
		return aa.doubleValue();
	}
	  /**
     * 减法
     */
	public static double subtract(double a,double b){
		BigDecimal aa=new BigDecimal(Double.toString(a));
		aa=aa.subtract(new BigDecimal(Double.toString(b)));
		return aa.doubleValue();
	}
	  /**
     * 乘法
     */
	public static double multiply(double a,double b){
		BigDecimal aa=new BigDecimal(Double.toString(a));
		aa=aa.multiply(new BigDecimal(Double.toString(b)));
		return aa.doubleValue();
	}
	  /**
     * 除法
     */
	public static double divide(double a,double b){
		BigDecimal aa=new BigDecimal(Double.toString(a));
		aa=aa.divide(new BigDecimal(Double.toString(b)));
		return aa.doubleValue();
	}
}
