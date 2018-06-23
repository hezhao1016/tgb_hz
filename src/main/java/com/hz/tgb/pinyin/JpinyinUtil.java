package com.hz.tgb.pinyin;

import com.github.stuxuhai.jpinyin.ChineseHelper;
import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;

/**
 * JPinyin汉字转拼音工具类
 * <br>
 * 注：在pinyin4j上进行了优化，自动识别常用多音字，还支持简体转换为繁体，检查是否为简体，是否为繁体，是否为中文字符等功能。
 *
 * @Author hezhao
 * @Time 2018-06-23 18:37
 * @Description 无
 * @Version V 1.0
 */
public class JpinyinUtil {

    private JpinyinUtil() {
        // 私有构造方法
    }

    /**
     * 转换为有声调的拼音字符串
     *
     * @param pinYinStr 汉字
     * @return 有声调的拼音字符串
     */
    public static String convertToMarkPinYin(String pinYinStr) {
        try {
            return PinyinHelper.convertToPinyinString(pinYinStr, " ", PinyinFormat.WITH_TONE_MARK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 转换为数字声调字符串
     *
     * @param pinYinStr 需转换的汉字
     * @return 转换完成的拼音字符串
     */
    public static String convertToNumberPinYin(String pinYinStr) {
        try {
            return PinyinHelper.convertToPinyinString(pinYinStr, " ", PinyinFormat.WITH_TONE_NUMBER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 转换为不带音调的拼音字符串
     *
     * @param pinYinStr 需转换的汉字
     * @return 拼音字符串
     */
    public static String convertToTonePinYin(String pinYinStr) {
        try {
            return PinyinHelper.convertToPinyinString(pinYinStr, " ", PinyinFormat.WITHOUT_TONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 转换为每个汉字对应拼音首字母字符串
     *
     * @param pinYinStr 需转换的汉字
     * @return 拼音字符串
     */
    public static String getShortPinyin(String pinYinStr) {
        try {
            return PinyinHelper.getShortPinyin(pinYinStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        // 汉字转拼音
        // 拼音格式：WITH_TONE_NUMBER--数字代表声调，WITHOUT_TONE--不带声调，WITH_TONE_MARK--带声调
        try {
            String str = "你好世界";
            // 设置声调表示格式
            System.out.println(PinyinHelper.convertToPinyinString(str, ",", PinyinFormat.WITH_TONE_MARK)); // nǐ,hǎo,shì,jiè
            // 数字表示声调
            System.out.println(PinyinHelper.convertToPinyinString(str, ",", PinyinFormat.WITH_TONE_NUMBER)); // ni3,hao3,shi4,jie4
            // 无声调
            System.out.println(PinyinHelper.convertToPinyinString(str, ",", PinyinFormat.WITHOUT_TONE)); // ni,hao,shi,jie
            // 获取拼音首字母
            System.out.println(PinyinHelper.getShortPinyin(str)); // nhsj
            // 判断是否多音字
            System.out.println(PinyinHelper.hasMultiPinyin('啊'));//true
        } catch (PinyinException e) {
            e.printStackTrace();
        }


        // 简繁体中文转换
        System.out.println("-------------------------------------");
        // 简体转繁体
        char traditionalChinese = ChineseHelper.convertToTraditionalChinese('义');
        // 繁体转简体
        String simplifiedChinese = ChineseHelper.convertToSimplifiedChinese("義");
        System.out.println(traditionalChinese);
        System.out.println(simplifiedChinese);
        // 判断是否是汉字
        System.out.println(ChineseHelper.isChinese('義')); // true
        // 是否为繁体字
        System.out.println(ChineseHelper.isTraditionalChinese('義')); // true
        // 是否包含中文
        System.out.println(ChineseHelper.containsChinese("123義456义789")); // true
    }
}