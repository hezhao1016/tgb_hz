package com.hz.tgb.number;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 货币，金融工具类
 * @author hezhao
 * @Time   2016年1月13日 上午10:18:52
 * @Description 无
 * @version V 1.0
 */
public class RMBUtil {
    /**
     * 汉语中数字大写
     */
    private static final String[] CN_UPPER_NUMBER = { "零", "壹", "贰", "叁", "肆",
            "伍", "陆", "柒", "捌", "玖" };
    /**
     * 汉语中货币单位大写，这样的设计类似于占位符
     */
    private static final String[] CN_UPPER_MONETRAY_UNIT = { "分", "角", "圆",
            "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "兆", "拾",
            "佰", "仟" };
    /**
     * 特殊字符：整
     */
    private static final String CN_FULL = "整";
    /**
     * 特殊字符：负
     */
    private static final String CN_NEGATIVE = "负";
    /**
     * 金额的精度，默认值为2
     */
    private static final int MONEY_PRECISION = 2;
    /**
     * 特殊字符：零元整
     */
    private static final String CN_ZEOR_FULL = "零元" + CN_FULL;

    /**
     * 把输入的金额转换为汉语中人民币的大写
     *
     * @param numberOfMoney
     *            输入的金额
     * @return 对应的汉语大写
     */
    public static String number2CNMontrayUnit(BigDecimal numberOfMoney) {
        StringBuffer sb = new StringBuffer();
        // -1, 0, or 1 as the value of this BigDecimal is negative, zero, or
        // positive.
        int signum = numberOfMoney.signum();
        // 零元整的情况
        if (signum == 0) {
            return CN_ZEOR_FULL;
        }
        //这里会进行金额的四舍五入
        long number = numberOfMoney.movePointRight(MONEY_PRECISION)
                .setScale(0, 4).abs().longValue();
        // 得到小数点后两位值
        long scale = number % 100;
        int numUnit = 0;
        int numIndex = 0;
        boolean getZero = false;
        // 判断最后两位数，一共有四中情况：00 = 0, 01 = 1, 10, 11
        if (!(scale > 0)) {
            numIndex = 2;
            number = number / 100;
            getZero = true;
        }
        if ((scale > 0) && (!(scale % 10 > 0))) {
            numIndex = 1;
            number = number / 10;
            getZero = true;
        }
        int zeroSize = 0;
        while (true) {
            if (number <= 0) {
                break;
            }
            // 每次获取到最后一个数
            numUnit = (int) (number % 10);
            if (numUnit > 0) {
                if ((numIndex == 9) && (zeroSize >= 3)) {
                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[6]);
                }
                if ((numIndex == 13) && (zeroSize >= 3)) {
                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[10]);
                }
                sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                sb.insert(0, CN_UPPER_NUMBER[numUnit]);
                getZero = false;
                zeroSize = 0;
            } else {
                ++zeroSize;
                if (!(getZero) && numUnit != 0) {
                    sb.insert(0, CN_UPPER_NUMBER[numUnit]);
                }
                if (numIndex == 2) {
                    if (number > 0) {
                        sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                    }
                } else if (((numIndex - 2) % 4 == 0) && (number % 1000 > 0)) {
                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                }
                getZero = true;
            }
            // 让number每次都去掉最后一个数
            number = number / 10;
            ++numIndex;
        }
        // 如果signum == -1，则说明输入的数字为负数，就在最前面追加特殊字符：负
        if (signum == -1) {
            sb.insert(0, CN_NEGATIVE);
        }
        // 输入的数字小数点后两位为"00"的情况，则要在最后追加特殊字符：整
        if (!(scale > 0)) {
            sb.append(CN_FULL);
        }
        return sb.toString();
    }

    /**
     * 将每三个数字加上逗号处理（通常使用金额方面的编辑）
     *
     * @param str
     *            无逗号的数字
     * @return 加上逗号的数字
     *
     */
    public static String addComma(String str) {
        if (StringUtils.isNotBlank(str)) {
            str = str.replace(".00", "");
        }
        if (StringUtils.isNotBlank(str)) {
            str = str.replace(".", "");
        }
        // 将传进数字反转
        String reverseStr = new StringBuilder(str).reverse().toString();
        String strTemp = "";
        for (int i = 0; i < reverseStr.length(); i++) {
            if (i * 3 + 3 > reverseStr.length()) {
                strTemp += reverseStr.substring(i * 3, reverseStr.length());
                break;
            }
            strTemp += reverseStr.substring(i * 3, i * 3 + 3) + ",";
        }
        // 将 【789,456,】 中最后一个【,】去除
        if (strTemp.endsWith(",")) {
            strTemp = strTemp.substring(0, strTemp.length() - 1);
        }
        // 将数字重新反转
        String resultStr = new StringBuilder(strTemp).reverse().toString();
        return resultStr;
    }


    private static final Character[] CN_NUMERIC = {
            '一', '二', '三', '四', '五', '六', '七', '八', '九',
            '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖',
            '十', '百', '千', '拾', '佰', '仟', '万', '亿', '○', 'Ｏ', '零' };

    private static Map<Character, Integer> cnNumeric = null;

    static {
        cnNumeric = new HashMap<>(40, 0.85f);
        for (int j = 0; j < 9; j++)
            cnNumeric.put(CN_NUMERIC[j], j + 1);
        for (int j = 9; j < 18; j++)
            cnNumeric.put(CN_NUMERIC[j], j - 8);
        cnNumeric.put('两', 2);
        cnNumeric.put('十', 10);
        cnNumeric.put('拾', 10);
        cnNumeric.put('百', 100);
        cnNumeric.put('佰', 100);
        cnNumeric.put('千', 1000);
        cnNumeric.put('仟', 1000);
        cnNumeric.put('万', 10000);
        cnNumeric.put('亿', 100000000);
        cnNumeric.put('○', 0);
        cnNumeric.put('Ｏ', 0);
        cnNumeric.put('零', 0);
    }

    /**
     * 大写金额字符转换为数字，例如：'一' -> 1, 伍 -> 5, 仟 -> 1000
     * @param c
     * @return
     */
    public static int parseCNNumeric(char c) {
        Integer i = cnNumeric.get(c);
        if (i == null)
            return -1;
        return i.intValue();
    }

    /**
     * 大写金额转小写数字
     * @param cnn
     * @param flag 是否含有字这些字符：(佰->百, 仟->千, 拾->十, 零->'')
     * @return
     *
     */
    public static int cnNumericToArabic(String cnn, boolean flag) {

        cnn = cnn.trim();
        if (cnn.length() == 1)
            return parseCNNumeric(cnn.charAt(0));

        if (flag)
            cnn = cnn.replace('佰', '百').replace('仟', '千').replace('拾', '十')
                    .replace('零', ' ');
        // System.out.println(cnn);
        int yi = -1, wan = -1, qian = -1, bai = -1, shi = -1;
        int val = 0;
        yi = cnn.lastIndexOf('亿');
        if (yi > -1) {
            val += cnNumericToArabic(cnn.substring(0, yi), false) * 100000000;
            if (yi < cnn.length() - 1)
                cnn = cnn.substring(yi + 1, cnn.length());
            else
                cnn = "";

            if (cnn.length() == 1) {
                int arbic = parseCNNumeric(cnn.charAt(0));
                if (arbic <= 10)
                    val += arbic * 10000000;
                cnn = "";
            }
        }

        wan = cnn.lastIndexOf('万');
        if (wan > -1) {
            val += cnNumericToArabic(cnn.substring(0, wan), false) * 10000;
            if (wan < cnn.length() - 1)
                cnn = cnn.substring(wan + 1, cnn.length());
            else
                cnn = "";
            if (cnn.length() == 1) {
                int arbic = parseCNNumeric(cnn.charAt(0));
                if (arbic <= 10)
                    val += arbic * 1000;
                cnn = "";
            }
        }

        qian = cnn.lastIndexOf('千');
        if (qian > -1) {
            val += cnNumericToArabic(cnn.substring(0, qian), false) * 1000;
            if (qian < cnn.length() - 1)
                cnn = cnn.substring(qian + 1, cnn.length());
            else
                cnn = "";
            if (cnn.length() == 1) {
                int arbic = parseCNNumeric(cnn.charAt(0));
                if (arbic <= 10)
                    val += arbic * 100;
                cnn = "";
            }
        }

        bai = cnn.lastIndexOf('百');
        if (bai > -1) {
            val += cnNumericToArabic(cnn.substring(0, bai), false) * 100;
            if (bai < cnn.length() - 1)
                cnn = cnn.substring(bai + 1, cnn.length());
            else
                cnn = "";
            if (cnn.length() == 1) {
                int arbic = parseCNNumeric(cnn.charAt(0));
                if (arbic <= 10)
                    val += arbic * 10;
                cnn = "";
            }
        }

        shi = cnn.lastIndexOf('十');
        if (shi > -1) {
            if (shi == 0)
                val += 1 * 10;
            else
                val += cnNumericToArabic(cnn.substring(0, shi), false) * 10;
            if (shi < cnn.length() - 1)
                cnn = cnn.substring(shi + 1, cnn.length());
            else
                cnn = "";
        }

        cnn = cnn.trim();
        for (int j = 0; j < cnn.length(); j++)
            val += parseCNNumeric(cnn.charAt(j))
                    * Math.pow(10, cnn.length() - j - 1);

        return val;
    }

    public static int qCNNumericToArabic(String cnn) {
        int val = 0;
        cnn = cnn.trim();
        for (int j = 0; j < cnn.length(); j++)
            val += parseCNNumeric(cnn.charAt(j))
                    * Math.pow(10, cnn.length() - j - 1);
        return val;
    }

    public static void main(String[] args) {
        //System.out.println(addComma("20000000"));

        double money = 14515100.2;
        BigDecimal numberOfMoney = new BigDecimal(money);
        String s = number2CNMontrayUnit(numberOfMoney);
        System.out.println("你输入的金额为：【"+ money +"】   #--# [" +s.toString()+"]");

        int val = 0;
        long ss = System.nanoTime();
        val = cnNumericToArabic("壹仟肆佰伍拾壹万伍仟壹佰五十二", true);
        System.out.println(val);
        val = cnNumericToArabic("一九九八", true);
        System.out.println(val);
        long e = System.nanoTime();
        System.out.format("Done[" + val + "], cost: %.5fsec\n",
                ((float) (e - ss)) / 1E9);

        System.out.println(addComma("123456789"));
    }

}
