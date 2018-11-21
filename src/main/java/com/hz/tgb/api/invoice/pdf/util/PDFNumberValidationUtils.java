package com.hz.tgb.api.invoice.pdf.util;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hezhao on 2018/9/18 16:16
 */
public class PDFNumberValidationUtils {

    private PDFNumberValidationUtils() {
        // 私有类构造方法
    }

    private static boolean isMatch(String regex, String orginal) {
        if (orginal != null && !orginal.trim().equals("")) {
            Pattern pattern = Pattern.compile(regex);
            Matcher isNum = pattern.matcher(orginal);
            return isNum.matches();
        } else {
            return false;
        }
    }

    public static boolean isPositiveInteger(String orginal) {
        return isMatch("^\\+{0,1}[1-9]\\d*", orginal);
    }

    public static boolean isNegativeInteger(String orginal) {
        return isMatch("^-[1-9]\\d*", orginal);
    }

    public static boolean isWholeNumber(String orginal) {
        return isMatch("[+-]{0,1}0", orginal) || isPositiveInteger(orginal) || isNegativeInteger(orginal);
    }

    public static boolean isPositiveDecimal(String orginal) {
        return isMatch("\\+{0,1}[0]\\.[1-9]*|\\+{0,1}[1-9]\\d*\\.\\d*", orginal);
    }

    public static boolean isNegativeDecimal(String orginal) {
        return isMatch("^-[0]\\.[1-9]*|^-[1-9]\\d*\\.\\d*", orginal);
    }

    public static boolean isDecimal(String orginal) {
        return isMatch("[-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+", orginal);
    }

    public static boolean isRealNumber(String orginal) {
        return isWholeNumber(orginal) || isDecimal(orginal);
    }

    public static String scientificChange(String str) {
        str = str.trim().replaceAll(" ", "");
        if (str.indexOf("E") > -1) {
            BigDecimal bd = new BigDecimal(str);
            return bd.toPlainString();
        } else {
            return isRealNumber(str) ? str : "";
        }
    }

    public static void main(String[] args) {
        boolean r = isRealNumber("+482.3423");
        System.out.println(r);
        String str = "2.0090102E9";
        BigDecimal bd = new BigDecimal(str);
        System.out.println(bd.toPlainString());
    }

}
