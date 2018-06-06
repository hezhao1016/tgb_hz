package com.hz.tgb.number;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * 单位转换工具类
 * 
 * @author Yaphis 2015年6月26日 下午5:35:32
 */
public class UnitArith {

    private UnitArith() {

    }

    /**
     * 分变元,并保留两位小数
     * 
     * @param amount
     * @return
     */
    public static String fenToyuan(String amount) {
        double res = new BigDecimal(amount).movePointLeft(2).doubleValue();
        NumberFormat formatter = new DecimalFormat("0.00");
        return formatter.format(res);
    }

    /**
     * 分变元,并保留两位小数
     * 
     * @param amount
     * @return
     */
    public static String fenToyuan(double amount) {
        double res = new BigDecimal(amount + "").movePointLeft(2).doubleValue();// 使用String来构造,否则会出现小数
        NumberFormat formatter = new DecimalFormat("0.00");
        return formatter.format(res);
    }

    /**
     * 元变分,并保留两位小数
     * 
     * @param amount
     * @return
     */
    public static String yuanTofen(String amount) {
        double res = new BigDecimal(amount).movePointRight(2).doubleValue();
        NumberFormat formatter = new DecimalFormat("0");
        return formatter.format(res);
    }

    /**
     * 元变分,并保留两位小数
     * 
     * @param amount
     * @return
     */
    public static String yuanTofen(double amount) {
        double res = new BigDecimal(amount + "").movePointRight(2).doubleValue();// 使用String来构造,否则会出现小数
        NumberFormat formatter = new DecimalFormat("0");
        return formatter.format(res);
    }

    /**
     * 金额四舍五入，并保留两位小数
     * 
     * @param amount
     * @return
     */
    public static BigDecimal setScale2(double amount) {
        BigDecimal res = new BigDecimal(amount + "");
        return res.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 元变分
     * 
     * @param amount
     * @return
     */
    public static int yuan2fen(BigDecimal amount) {
        return Integer.parseInt(yuanTofen(amount.doubleValue()));
    }

    /**
     * 分变元
     * 
     * @param amount
     * @return
     */
    public static BigDecimal fen2yuan(int amount) {
        return new BigDecimal(fenToyuan(amount + ""));
    }

    /**
     * 判断参数是否大于0
     * 
     * @param param
     * @return
     */
    public static boolean greaterThanZero(BigDecimal param) {
        if (null == param) {
            return false;
        }
        return param.compareTo(new BigDecimal(0)) > 0;
    }

    /**
     * 判断参数是否大于等于0
     * 
     * @param param
     * @return
     */
    public static boolean greaterOrEqualThanZero(BigDecimal param) {
        if (null == param) {
            return false;
        }
        return param.compareTo(new BigDecimal(0)) >= 0;
    }
}
