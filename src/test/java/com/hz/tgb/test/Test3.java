package com.hz.tgb.test;

import com.hz.tgb.common.StringUtil;
import com.hz.tgb.number.AmountUtil;
import com.hz.tgb.number.NumberToCN;
import com.hz.tgb.number.NumberUtil;

import java.math.BigDecimal;

/**
 * Created by hezhao on 2017-09-15 18:26.
 */
public class Test3 {


    public static void main(String[] args) {


        Double d = new Double("155615656561.5151");
        System.out.println("d:="+String.valueOf(d));
        java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        System.out.println("d:="+nf.format(d));


        System.out.println(formatFloatNumber(new BigDecimal(d)));

        BigDecimal num=new BigDecimal(d);
        System.out.println(num);


        System.out.println(formatFloatNumber(new BigDecimal(AmountUtil.moneyFenToYuan(5194845613266.15+""))));
        System.out.println(AmountUtil.moneyFenToYuan(5194845613266.15+""));


        String realIncomeYuan = "-0.1";
        System.out.println(formatFloatNumber(new BigDecimal(realIncomeYuan)));

        String sss = "13734040.75";
        String aaa = changeF2YRoundFour(sss);

        System.out.println(new BigDecimal(aaa).doubleValue());

        String total = "17210212.48";
        BigDecimal total_realIncome = new BigDecimal(total);

        Double amount = 0.0;
        BigDecimal amountL = new BigDecimal(0);
        String amountChinese = "";

        //采用汇总表的数据
        amountL = total_realIncome;

        amount = Double.valueOf(changeF2YRoundFour(amountL.doubleValue()+""));

        // 三种 金额 转大写
        amountChinese = NumberToCN.number2CNMontrayUnit(new BigDecimal(Double.valueOf(formatFloatNumber(amount))));

        if(amountChinese == null || "".equals(amountChinese)){
            amountChinese = "零";
        }

        System.out.println(formatFloatNumber(amount));
        System.out.println(amountChinese);

        System.out.println(StringUtil.moneyUppercase(new BigDecimal(Double.valueOf(formatFloatNumber(amount))).doubleValue()));

        System.out.println(NumberUtil.toChineseUpper(new BigDecimal(Double.valueOf(formatFloatNumber(amount))).doubleValue() + ""));

        System.out.println(AmountUtil.changeY2F("0.45"));

    }

//    /**
//     * 当浮点型数据位数超过10位之后，数据变成科学计数法显示。用此方法可以使其正常显示。
//     * @param value
//     * @return Sting
//     */
//    public static String formatFloatNumber(double value) {
//        if(value != 0.00){
//            java.text.DecimalFormat df = new java.text.DecimalFormat("########.00");
//            return df.format(value);
//        }else{
//            return "0.00";
//        }
//
//    }
    public static String formatFloatNumber(BigDecimal value) {
        if(value != null){

            if(value.doubleValue() < 1 && value.doubleValue() >-1){
                return value+"";
            }

            if(value.doubleValue() != 0.00){
                java.text.DecimalFormat df = new java.text.DecimalFormat("########.00");
                return df.format(value.doubleValue());
            }else{
                return "0.00";
            }
        }
        return "";
    }

//    public static String formatFloatNumber(String valuestr) {
//        Double value = Double.valueOf(valuestr);
//
//        if(value != null){
//            if(value.doubleValue() != 0.00){
//                java.text.DecimalFormat df = new java.text.DecimalFormat("########.00");
//                return df.format(value.doubleValue());
//            }else{
//                return "0.00";
//            }
//        }
//        return "";
//    }

    /**
     * 将分为单位的转换为元 四舍五入 保留四位小数
     *
     * @param amount 可为小数
     * @return
     */
    public static String changeF2YRoundFour(String amount){
        BigDecimal bigDecimal = new BigDecimal(amount);
        bigDecimal = bigDecimal.movePointLeft(2);
        BigDecimal one = new BigDecimal("1");
        return String.valueOf(bigDecimal.divide(one, 4, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    /**
     * 当浮点型数据位数超过10位之后，数据变成科学计数法显示。用此方法可以使其正常显示。
     * @param value
     * @return Sting
     */
    public static String formatFloatNumber(Double value) {
        if(value != null){
            if(value.doubleValue() != 0.00){
                if(value.doubleValue() < 1 && value.doubleValue() >-1){
                    return String.format("%.4f",value);
                }

                java.text.DecimalFormat df = new java.text.DecimalFormat("########.0000");
                return df.format(value.doubleValue());
            }else{
                return "0.0000";
            }
        }
        return "";
    }

}
