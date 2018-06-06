package com.hz.tgb.number;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * 金额工具类
 * @author hezhao
 */
public class AmountUtil {

    /***
     * 格式化金额类,设置默认值
     * 
     * @param yuanAmount
     * @return
     */
    public static BigDecimal formatDefault(String yuanAmount) {
        if (null == yuanAmount || "".equals(yuanAmount)) {
            return new BigDecimal(0);
        }

        return new BigDecimal(yuanAmount);
    }

    /***
     * 格式化元字符串
     * 
     * @param yuanAmount
     * @return
     */
    public static String formatYuan(String yuanAmount) {
        double res = new BigDecimal(yuanAmount).doubleValue();
        NumberFormat formatter = new DecimalFormat("0.00");
        return formatter.format(res);
    }

    /***
     * 分字符串转换为元，并格式化
     * 
     * @param fenAmount
     * @return
     */
    public static String formatFen(String fenAmount) {
        double res = new BigDecimal(fenAmount).movePointLeft(2).doubleValue();
        NumberFormat formatter = new DecimalFormat("0.00");
        return formatter.format(res);
    }

    /***
     * 将字符串的日利率转换为0.XX%
     * 
     * @param rateStr
     * @return
     */
    public static String rateFormatStr(String rateStr) {
        if (rateStr == null || "".equals(rateStr)) {
            return rateStr;
        }

        BigDecimal rateB = new BigDecimal(rateStr);
        // 右移2位小数点,相当于乘以100
        double rateD = rateB.movePointRight(2).doubleValue();
        NumberFormat formatter = new DecimalFormat("#.##");

        return formatter.format(rateD) + "%";
    }

    /***
     * 获取日利率描述,输入利率必须为万分之X的
     * 
     * @param formatRateStr 万分之X的值
     * @return
     */
    public static String getRateName(String formatRateStr) {
        if (null == formatRateStr || "".equals(formatRateStr)) {
            return formatRateStr;
        }
        // 拼接万X的字样
        StringBuffer sb = new StringBuffer("万").append(formatRateStr);
        return sb.toString();
    }

    /***
     * 获取日利率描述,借1000元1日利息XX元
     * 
     * @param rateStr 原始日利率值,保留6位小数
     * @return
     */
    public static String getRateDesc(String rateStr) {
        if (rateStr == null || "".equals(rateStr)) {
            return rateStr;
        }

        BigDecimal rateB = new BigDecimal(rateStr);
        // 右移3位小数点,相当于乘以1000
        double rateD = rateB.movePointRight(3).doubleValue();
        NumberFormat formatter = new DecimalFormat("#.###");
        String rateAmountStr = formatter.format(rateD);

        StringBuffer sb = new StringBuffer("借1000元1天利息").append(rateAmountStr).append("元");
        return sb.toString();
    }

    /***
     * 元转换为分
     * 
     * @param amountStr
     * @return
     */
    public static long yuanToFen(String amountStr) {
        if (amountStr == null || "".equals(amountStr)) {
            return 0L;
        }
        double res = new BigDecimal(amountStr).movePointRight(2).doubleValue();
        NumberFormat formatter = new DecimalFormat("#");
        return Long.valueOf(formatter.format(res));
    }

    /***
     * 元转换为分
     * 
     * @param amountB
     * @return
     */
    public static long yuanToFen(BigDecimal amountB) {
        double res = amountB.movePointRight(2).doubleValue();
        NumberFormat formatter = new DecimalFormat("#");
        return Long.valueOf(formatter.format(res));
    }



    // --------------------------------------------------------------------------------------

    /**金额为分的格式 */
    public static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+\\.?[0-9]*";

    /**
     * 金额元转分
     *  注意:该方法可处理贰仟万以内的金额,且若有小数位,则不限小数位的长度
     *  注意:如果你的金额达到了贰仟万以上,则不推荐使用该方法,否则计算出来的结果会令人大吃一惊
     * @param amount  金额的元进制字符串
     * @return String 金额的分进制字符串
     */
    public static String moneyYuanToFen(String amount){
        if(isEmpty(amount)){
            return amount;
        }
        //传入的金额字符串代表的是一个整数
        if(-1 == amount.indexOf(".")){
            return Integer.parseInt(amount) * 100 + "";
        }
        //传入的金额字符串里面含小数点-->取小数点前面的字符串,并将之转换成单位为分的整数表示
        int money_fen = Integer.parseInt(amount.substring(0, amount.indexOf("."))) * 100;
        //取到小数点后面的字符串
        String pointBehind = (amount.substring(amount.indexOf(".") + 1));
        //amount=12.3
        if(pointBehind.length() == 1){
            return money_fen + Integer.parseInt(pointBehind)*10 + "";
        }
        //小数点后面的第一位字符串的整数表示
        int pointString_1 = Integer.parseInt(pointBehind.substring(0, 1));
        //小数点后面的第二位字符串的整数表示
        int pointString_2 = Integer.parseInt(pointBehind.substring(1, 2));
        //amount==12.03,amount=12.00,amount=12.30
        if(pointString_1 == 0){
            return money_fen + pointString_2 + "";
        }else{
            return money_fen + pointString_1*10 + pointString_2 + "";
        }
    }


    /**
     * 金额元转分
     *  该方法会将金额中小数点后面的数值,四舍五入后只保留两位....如12.345-->12.35
     *  注意:该方法可处理贰仟万以内的金额
     *  注意:如果你的金额达到了贰仟万以上,则非常不建议使用该方法,否则计算出来的结果会令人大吃一惊
     * @param amount  金额的元进制字符串
     * @return String 金额的分进制字符串
     */
    public static String moneyYuanToFenByRound(String amount){
        if(isEmpty(amount)){
            return amount;
        }
        if(-1 == amount.indexOf(".")){
            return Integer.parseInt(amount) * 100 + "";
        }
        int money_fen = Integer.parseInt(amount.substring(0, amount.indexOf("."))) * 100;
        String pointBehind = (amount.substring(amount.indexOf(".") + 1));
        if(pointBehind.length() == 1){
            return money_fen + Integer.parseInt(pointBehind)*10 + "";
        }
        int pointString_1 = Integer.parseInt(pointBehind.substring(0, 1));
        int pointString_2 = Integer.parseInt(pointBehind.substring(1, 2));
        //下面这种方式用于处理pointBehind=245,286,295,298,995,998等需要四舍五入的情况
        if(pointBehind.length() > 2){
            int pointString_3 = Integer.parseInt(pointBehind.substring(2, 3));
            if(pointString_3 >= 5){
                if(pointString_2 == 9){
                    if(pointString_1 == 9){
                        money_fen = money_fen + 100;
                        pointString_1 = 0;
                        pointString_2 = 0;
                    }else{
                        pointString_1 = pointString_1 + 1;
                        pointString_2 = 0;
                    }
                }else{
                    pointString_2 = pointString_2 + 1;
                }
            }
        }
        if(pointString_1 == 0){
            return money_fen + pointString_2 + "";
        }else{
            return money_fen + pointString_1*10 + pointString_2 + "";
        }
    }


    /**
     * 金额分转元
     *  注意:如果传入的参数中含小数点,则直接原样返回
     *  该方法返回的金额字符串格式为<code>00.00</code>,其整数位有且至少有一个,小数位有且长度固定为2
     * @param amount  金额的分进制字符串
     * @return String 金额的元进制字符串
     */
    public static String moneyFenToYuan(String amount){
        if(isEmpty(amount)){
            return amount;
        }
        if(amount.indexOf(".") > -1){
            return amount;
        }
        if(amount.length() == 1){
            return "0.0" + amount;
        }else if(amount.length() == 2){
            return "0." + amount;
        }else{
            return amount.substring(0, amount.length()-2) + "." + amount.substring(amount.length()-2);
        }
    }

    /**
     * 将分为单位的转换为元 （除100）
     *
     * @param amount 可为小数
     * @return
     */
    public static String changeF2Y(String amount){
        BigDecimal bigDecimal = new BigDecimal(amount);
        return String.valueOf(bigDecimal.movePointLeft(2));
    }

    /**
     * 将分为单位的转换为元 （除100） 四舍五入 保留两位小数
     *
     * @param amount 可为小数
     * @return
     */
    public static String changeF2YRound(String amount){
        BigDecimal bigDecimal = new BigDecimal(amount);
        bigDecimal = bigDecimal.movePointLeft(2);
        BigDecimal one = new BigDecimal("1");
        return String.valueOf(bigDecimal.divide(one, 2, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    /**
     * 将元为单位的转换为分 （乘100）
     *
     * @param amount
     * @return
     */
    public static String changeY2F(String amount){
        BigDecimal bigDecimal = new BigDecimal(amount);
        return String.valueOf(bigDecimal.movePointRight(2));
    }

    /**
     * 将元为单位的转换为分 （乘100） 四舍五入 保留两位小数
     *
     * @param amount 可为小数
     * @return
     */
    public static String changeY2FRound(String amount){
        BigDecimal bigDecimal = new BigDecimal(amount);
        bigDecimal = bigDecimal.movePointRight(2);
        BigDecimal one = new BigDecimal("1");
        return String.valueOf(bigDecimal.divide(one, 2, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    /**
     * 判断输入的字符串参数是否为空
     * @return boolean 空则返回true,非空则flase
     */
    public static boolean isEmpty(String input) {
        return null==input || 0==input.length() || 0==input.replaceAll("\\s", "").length();
    }


    /**
     * 判断输入的字节数组是否为空
     * @return boolean 空则返回true,非空则flase
     */
    public static boolean isEmpty(byte[] bytes){
        return null==bytes || 0==bytes.length;
    }
}
