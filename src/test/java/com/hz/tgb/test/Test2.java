package com.hz.tgb.test;


import com.hz.tgb.number.AmountUtil;

import java.math.BigDecimal;

/**
 * Created by hezhao on 2017-09-13 17:55.
 */
public class Test2 {

    public static void main(String[] args) {
        double get_double = 0.5;
        String ss = String.format("%.2f",get_double);
        System.out.println(ss);


        BigDecimal bigDecimal = new BigDecimal(120.221);

        String s = String.valueOf(bigDecimal.doubleValue());
        System.out.println(s);

        try {
            String s1 = AmountUtil.changeF2Y(String.valueOf(bigDecimal));
            String s2 = AmountUtil.changeF2YRound(String.valueOf(bigDecimal.doubleValue()));

            System.out.println(s1);
            System.out.println(s2);
            System.out.println(Double.valueOf(AmountUtil.changeY2F("22.465147")));
            System.out.println(Double.valueOf(AmountUtil.changeY2FRound("22.465147")));
            System.out.println(String.format("%.2f",Double.valueOf(AmountUtil.changeY2FRound("22.465147"))));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
