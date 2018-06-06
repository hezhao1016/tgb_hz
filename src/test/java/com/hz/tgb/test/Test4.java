package com.hz.tgb.test;

import com.hz.tgb.datetime.DateUtil;
import com.hz.tgb.number.AmountUtil;
import com.hz.tgb.number.NumberToCN;
import com.hz.tgb.number.NumberUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by hezhao on 2017-09-18 17:43.
 */
public class Test4 {

    public static void main(String[] args) {

        double div = 50;

//        double div = 0.7 / 100;
        String format = String.format("%.2f", div);
//        String format = div+"";
        System.out.println(format);

        String s = "0.00";


        String s0 = AmountUtil.changeF2Y("46521565484989489.50");
        System.out.println(NumberUtil.formatFloatNumber(s0));

        String s1 = AmountUtil.changeF2YRound("46521565484989489.50");
        System.out.println(NumberUtil.formatFloatNumber(s1));

        String s2 = AmountUtil.changeF2Y("46521565484989489.50");


        System.out.println("------------------");
        double   f   =   111231.5585;
        BigDecimal b   =   new   BigDecimal(f);
        double   f1   =   b.setScale(3,   BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println(f1);

        System.out.println(new DecimalFormat("#.00").format(3.1415926));


        System.out.println( new BigDecimal(46521569489.25).movePointLeft(2));

        System.out.println("--------------------");
        Double amount = Double.valueOf(AmountUtil.changeF2YRound(123110456789.0+""));
        String amountChinese = NumberToCN.number2CNMontrayUnit(new BigDecimal(Double.valueOf(NumberUtil.formatFloatNumber(amount))));
        System.out.println(amount);
        System.out.println(NumberUtil.formatFloatNumber(amount));
        System.out.println(amountChinese);


        System.out.println("----------------------");
        //分转元
        String realIncomeYuan = AmountUtil.changeF2YRound(String.valueOf(108505152.50));
        String s3 = NumberUtil.formatFloatNumber(realIncomeYuan);
        System.out.println(s3);

        System.out.println("--------------------");
        System.out.println(DateUtil.getLastMonth());
    }
}