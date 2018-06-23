package com.hz.tgb.pinyin;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/** pinyin4j 汉字转拼音工具类
 * <br>
 * 注：对多音字语句的处理不太理想
 *
 * @Author hezhao
 * @Time 2018-06-23 18:37
 * @Description 无
 * @Version V 1.0
 */
public class Pinyin4jUtil {
    /** pinyin4j格式类 */
    private static HanyuPinyinOutputFormat format;

    /** 拼音字符串数组 */
    private static String[] pinyin;

    enum Type {
        /** 全部大写 */
        UPPERCASE,
        /** 全部小写 */
        LOWERCASE,
        /** 首字母大写 */
        FIRSTUPPER
    }

    private Pinyin4jUtil(){
        // 私有构造方法
    }

    static{
        format = new HanyuPinyinOutputFormat();
        /*
         * 设置需要转换的拼音格式
         * 以汉字 ‘天’ 为例
         * HanyuPinyinToneType.WITHOUT_TONE 转换为tian
         * HanyuPinyinToneType.WITH_TONE_MARK 转换为tian1
         * HanyuPinyinVCharType.WITH_U_UNICODE 转换为tiān
         *
         */
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        pinyin = null;
    }

    /**
     * 对单个中文字符进行转换, 当转换的符串不是汉字，就返回null
     * @param c 需转换的汉字字符串
     * @return 拼音字符
     */
    public static String getCharacterPinYin(char c) {
        try {
            // 执行转换
            pinyin = PinyinHelper.toHanyuPinyinStringArray(c, format);
        } catch(BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }

        // pinyin4j规则，当转换的符串不是汉字，就返回null
        if(null == pinyin) {
            return null;
        }

        // 多音字会返回一个多音字拼音的数组，pinyiin4j并不能有效判断该字的读音
        // 多音字取第一个值
        return pinyin[0];
    }

    /**
     * 对单个字进行转换,默认分隔符是空格
     * @param str 需转换的汉字字符串
     * @return 拼音字符串
     */
    public static String getStringPinYin(String str) {
        return getStringPinYin(str, " ");
    }

    /**
     * 对单个字进行转换
     * @param str 需转换的汉字字符串
     * @param spera 分隔符，例如 \'
     * @return 拼音字符串
     */
    public static String getStringPinYin(String str, String spera) {
        StringBuilder sb = new StringBuilder();
        String tempStr;

        for(int i = 0; i < str.length(); ++i) {
            tempStr = getCharacterPinYin(str.charAt(i));
            if(tempStr == null) {
                // 如果str.charAt(i)不是汉字，则保持原样
                sb.append(str.charAt(i));
            } else {
                sb.append(tempStr);
                // 分词
                if ( i < str.length() - 1 && null != getCharacterPinYin(str.charAt(i + 1))) {
                    sb.append(spera);
                }
            }
        }
        return sb.toString().trim();
    }

    /**
     * 获取拼音首字母(大写)
     * @param str 字符串
     * @return str为颐和园 ,return获取到的是YHY
     */
    public static String toPinYinUppercase(String str) {
        return toPinYin(str, "", Type.UPPERCASE);
    }

    /**
     * 获取拼音首字母(大写)
     * @param str 字符串
     * @param spera 转换字母间隔加的字符串,如果不需要为""
     * @return str为颐和园 ,spera为** return获取到的是Y**H**Y
     */
    public static String toPinYinUppercase(String str,String spera) {
        return toPinYin(str, spera, Type.UPPERCASE);
    }

    /**
     * 获取拼音首字母(小写)
     * @param str 字符串
     * @return  str为颐和园 ,return获取到的是yhy
     */
    public static String toPinYinLowercase(String str) {
        return toPinYin(str, "", Type.LOWERCASE);
    }

    /**
     * 获取拼音首字母(小写)
     * @param str 字符串
     * @param spera 转换字母间隔加的字符串,如果不需要为""
     * @return  str为颐和园 ,spera为** return获取到的是y**h**y
     */
    public static String toPinYinLowercase(String str,String spera){
        return toPinYin(str, spera, Type.LOWERCASE);
    }

    /**
     * 获取第一个字符的拼音首字母(大写)
     * @param str 字符串
     * @return str为颐和园 ,return获取到的是Y
     */
    public static String toPinYinUppercaseInitials(String str) {
        String initials = null;
        String py = toPinYinUppercase(str);
        if(py.length()>1){
            initials = py.substring(0, 1);
        }
        if(py.length()<=1){
            initials = py;
        }
        return initials.trim();
    }

    /**
     * 获取第一个字符的拼音首字母(小写)
     * @param str 字符串
     * @return str为颐和园 ,return获取到的是y
     */
    public static String toPinYinLowercaseInitials(String str) {
        String initials = null;
        String py = toPinYinLowercase(str);
        if(py.length()>1){
            initials = py.substring(0, 1);
        }
        if(py.length()<=1){
            initials = py;
        }
        return initials.trim();
    }

    /**
     * 获取拼音首字母，如果不是汉字或者没有对应的拼音，则不作转换
     * @param str    字符串
     * @param spera  默认,可为""
     * @param type   转换格式
     * @return 按照转换格式转换成字符串
     */
    public static String toPinYin(String str, String spera, Type type) {
        if(str == null || str.trim().length()==0) {
            return "";
        }
        try {
            if(type == Type.UPPERCASE) {
                format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
            } else{
                format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
            }
            StringBuilder sb = new StringBuilder();
            String tempStr;
            String[] t;
            for(int i=0;i<str.length();i++){
                char c = str.charAt(i);
                if((int)c <= 128)  {
                    sb.append(c);
                }else{
                    t = PinyinHelper.toHanyuPinyinStringArray(c, format);
                    if(t == null) {
                        sb.append(c);
                    }else{
                        tempStr = t[0];
                        if(type == Type.FIRSTUPPER) {
                            tempStr = t[0].toUpperCase().charAt(0) + tempStr.substring(1);
                        }
                        if(tempStr.length()>=1){
                            tempStr = tempStr.substring(0, 1);
                        }
                        sb.append(tempStr + (i == str.length()-1 ? "" : spera));
                    }
                }
            }
            return sb.toString().trim();
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        char c = '你';
        System.out.println(Pinyin4jUtil.getCharacterPinYin(c));

        String str = "哈哈，我爱 Coding";
        System.out.println(Pinyin4jUtil.getStringPinYin(str));
        System.out.println(Pinyin4jUtil.getStringPinYin(str, "\'"));

        System.out.println("-----------------------------------");

        System.out.println(Pinyin4jUtil.toPinYin("颐和园","", Type.FIRSTUPPER)); //输出结果：YHY
        System.out.println(Pinyin4jUtil.toPinYinUppercase("颐和园")); //输出结果：YHY
        System.out.println(Pinyin4jUtil.toPinYinUppercase("颐和园", "**")); //输出结果：Y**H**Y
        System.out.println(Pinyin4jUtil.toPinYinLowercase("颐和园")); //输出结果：yhy
        System.out.println(Pinyin4jUtil.toPinYinLowercase("颐和园","**")); //输出结果：y**h**y
        System.out.println(Pinyin4jUtil.toPinYinUppercaseInitials("颐和园")); //输出结果：Y
        System.out.println(Pinyin4jUtil.toPinYinLowercaseInitials("颐和园")); //输出结果：y
    }
}