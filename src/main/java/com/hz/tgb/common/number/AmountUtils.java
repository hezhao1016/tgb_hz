package com.hz.tgb.common.number;

/**
 * com.util.AmountUtils
 * @description  金额元分之间转换工具类
 * @author zcm0708@sina.com
 * @2012-2-7下午12:58:00
 */
public class AmountUtils {

    /**金额为分的格式 */
    public static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+\\.?[0-9]*";

    /**
     * 将分为单位的转换为元 （除100）
     *
     * @param amount 可为小数
     * @return
     * @throws Exception
     */
    public static String changeF2Y(String amount){
        if(!amount.matches(CURRENCY_FEN_REGEX)) {
            return amount;
        }
        return String.valueOf(BigDecimalUtil.div(Double.valueOf(amount),100));
    }

    /**
     * 将分为单位的转换为元 （除100） 四舍五入
     *
     * @param amount 可为小数
     * @return
     * @throws Exception
     */
    public static String changeF2YRound(String amount){
        if(!amount.matches(CURRENCY_FEN_REGEX)) {
            return amount;
        }
        return String.valueOf(BigDecimalUtil.div(Double.valueOf(amount),100,2));
    }

    /**
     * 将元为单位的转换为分 （乘100）
     *
     * @param amount
     * @return
     */
    public static String changeY2F(String amount){
        if(!amount.matches(CURRENCY_FEN_REGEX)) {
            return amount;
        }
        return String.valueOf(BigDecimalUtil.mul(Double.valueOf(amount),100));
    }

    /**
     * 将元为单位的转换为分 （乘100） 四舍五入
     *
     * @param amount 可为小数
     * @return
     */
    public static String changeY2FRound(String amount){
        if(!amount.matches(CURRENCY_FEN_REGEX)) {
            return amount;
        }
        return String.valueOf(BigDecimalUtil.round(BigDecimalUtil.mul(Double.valueOf(amount),100),2));
    }

}