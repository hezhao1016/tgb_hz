/**
 * 
 */
package com.hz.tgb.number;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 80114515 数字工具类库
 */
public class NumericUtil {

    /***
     * 私有类构造方法
     */
    private NumericUtil() {

    }
    
    // 判断一个字符串是否都为数字  
    public static  boolean isDigit(String strNum) {  
        Pattern pattern = Pattern.compile("[0-9]{1,}");  
        Matcher matcher = pattern.matcher((CharSequence) strNum);  
        return matcher.matches();  
    }

    /***
     * 字符串转int类型
     * 
     * @param key
     * @param defaultValue
     * @return
     */
    public static int parseInt(String key, int defaultValue) {
        int value = defaultValue;
        if (key != null && !"".equals(key)){
            try {
                value = Integer.parseInt(key);
            } catch (Exception localException) {
            }
        }
            
        return value;
    }

    /***
     * 判断是否为连续数字或相同数字
     * 
     * @param strings 待判定字符串
     * @return true为是false为不是
     */
    public static boolean isSerialNumber(String strings) {
        boolean result = true;

        // 判断是否为数字,如果不是直接返回不是连续数字
        if (!StringUtils.isNumeric(strings)) {
            return false;
        }

        // 长度只有一位返回不为连续数字
        if (strings.length() < 2) {
            return false;
        }

        // 是否为相同数字
        if (!isSameNumber(strings)) {
            // 是否为升序连续
            if (!isAscSerialNumer(strings)) {
                // 是否为降序连续
                if (!isDescSerialNumber(strings)) {
                    result = false;
                }
            }
        }

        return result;
    }

    /***
     * 检查是否为相同数字
     * 
     * @param strings 待判定字符串
     * @return true为是false为不是
     */
    public static boolean isSameNumber(String strings) {
        boolean isSame = true;
        int numer;
        numer = charToInt(strings.charAt(0));
        // 遍历密保答案检查是否为相同的数字
        for (int i = 1; i < strings.length(); i++) {
            int tmp = charToInt(strings.charAt(i));
            if (numer != tmp) {
                // 若数字相同的情况下继续遍历
                isSame = false;
                break;
            }
        }

        return isSame;
    }

    /***
     * 是否为降序数字
     * 
     * @param strings 待判定字符串
     * @return true为是false为不是
     */
    public static boolean isDescSerialNumber(String strings) {
        boolean isDesc = true;
        int numer;
        numer = charToInt(strings.charAt(0));
        // 遍历密保答案检查是否为降序连续数字
        for (int i = 1; i < strings.length(); i++) {
            int tmp = charToInt(strings.charAt(i));
            if (numer <= tmp) {
                // 数字相同非降序退出循环
                isDesc = false;
                break;
            } else {
                numer--;
                if (numer == tmp) {
                    // 可能为降序连续
                } else {
                    isDesc = false;
                    break;
                }
            }
        }

        return isDesc;
    }

    /***
     * 判断是否为升序数字
     * 
     * @param strings 待判定字符串
     * @return true为是false为不是
     */
    public static boolean isAscSerialNumer(String strings) {
        boolean isAsc = true;
        ;
        int numer = charToInt(strings.charAt(0));
        // 遍历密保答案检查是否为升序连续数字
        for (int i = 1; i < strings.length(); i++) {
            int tmp = charToInt(strings.charAt(i));
            if (numer == tmp) {
                // 若相同非升序,退出
                isAsc = false;
                break;
            } else if (numer < tmp) {
                numer++;
                if (numer == tmp) {
                    // 可能为升序连续
                } else {
                    isAsc = false;
                    break;
                }
            } else {
                isAsc = false;
                break;
            }
        }

        return isAsc;
    }

    /***
     * char转换为int数字,不做ascii码转换
     * 
     * @param c 字符
     * @return
     */
    public static int charToInt(char c) {
        return Integer.valueOf(String.valueOf(c));
    }
}
