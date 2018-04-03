package test;

import com.hz.tgb.common.number.AmountUtils;

import java.math.BigDecimal;

/**
 * Created by hezhao on 2017-09-13 17:55.
 */
public class Test4 {

    public static void main(String[] args) {
        double get_double = 0.5;
        String ss = String.format("%.2f",get_double);
        System.out.println(ss);


        BigDecimal bigDecimal = new BigDecimal(120.221);

        String s = String.valueOf(bigDecimal.doubleValue());
        System.out.println(s);

        try {
            String s1 = AmountUtils.changeF2Y(String.valueOf(bigDecimal));
            String s2 = AmountUtils.changeF2YRound(String.valueOf(bigDecimal.doubleValue()));

            System.out.println(s1);
            System.out.println(s2);
            System.out.println(Double.valueOf(AmountUtils.changeY2F("22.465147")));
            System.out.println(Double.valueOf(AmountUtils.changeY2FRound("22.465147")));
            System.out.println(String.format("%.2f",Double.valueOf(AmountUtils.changeY2FRound("22.465147"))));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
