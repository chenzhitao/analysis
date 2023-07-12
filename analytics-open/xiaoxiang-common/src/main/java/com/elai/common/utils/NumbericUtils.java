package com.elai.common.utils;

import java.math.BigDecimal;

/**
 * 字符串工具类
 *
 * @author waoqi
 */
public class NumbericUtils extends org.apache.commons.lang3.math.NumberUtils {

    public static BigDecimal calcDivide(int number1,int number2,int scale) {
        BigDecimal b1 = new BigDecimal(number1).setScale(8,BigDecimal.ROUND_HALF_UP);
        BigDecimal b2 = new BigDecimal(number2).setScale(8,BigDecimal.ROUND_HALF_UP);
        BigDecimal div = b1.divide(b2,scale);
        div=div.setScale(scale,BigDecimal.ROUND_HALF_UP);
        return div;
    }

    public static void main(String[] args) {
        System.out.println(NumbericUtils.calcDivide(3,4,4));
    }
}
