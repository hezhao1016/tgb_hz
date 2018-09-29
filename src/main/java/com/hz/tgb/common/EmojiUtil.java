package com.hz.tgb.common;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 表情符工具类
 * @author hezhao
 */
public class EmojiUtil {

    private static final Logger logger = LoggerFactory.getLogger(EmojiUtil.class);

    public static String filter(String str){

        // 若为空则直接返回
        if (StringUtils.isBlank(str)) {
            return str;
        }

        // 判断是否包含表情符
        if(!containsEmoji(str)){
            logger.info("str:{} contain emoji:{}", str, containsEmoji(str));
            return str;
        }

        // 获取
        StringBuffer buf = new StringBuffer(str.length());
        int len = str.length();
        for (int i = 0; i < len; i++) {
            char codePoint = str.charAt(i);
            // 若当前字符不为表情符,则添加到返回
            if (isNotEmojiChar(codePoint)) {
                buf.append(codePoint);
            }
        }

        logger.info("str:{} filter emoji:{}", str, buf);
        return buf.toString();
    }

    private static boolean isNotEmojiChar(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))|| ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))|| ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    public static boolean containsEmoji(String source) {
        int len = source.length();
        boolean isEmoji = false;
        for (int i = 0; i < len; i++) {
            char hs = source.charAt(i);
            if (0xd800 <= hs && hs <= 0xdbff) {
                if (source.length() > 1) {
                    char ls = source.charAt(i + 1);
                    int uc = ((hs - 0xd800) * 0x400) + (ls - 0xdc00) + 0x10000;
                    if (0x1d000 <= uc && uc <= 0x1f77f) {
                        return true;
                    }
                }
            } else {
                // non surrogate
                if (0x2100 <= hs && hs <= 0x27ff && hs != 0x263b) {
                    return true;
                } else if (0x2B05 <= hs && hs <= 0x2b07) {
                    return true;
                } else if (0x2934 <= hs && hs <= 0x2935) {
                    return true;
                } else if (0x3297 <= hs && hs <= 0x3299) {
                    return true;
                } else if (hs == 0xa9 || hs == 0xae || hs == 0x303d
                        || hs == 0x3030 || hs == 0x2b55 || hs == 0x2b1c
                        || hs == 0x2b1b || hs == 0x2b50 || hs == 0x231a) {
                    return true;
                }
                if (!isEmoji && source.length() > 1 && i < source.length() - 1) {
                    char ls = source.charAt(i + 1);
                    if (ls == 0x20e3) {
                        return true;
                    }
                }
            }
        }
        return isEmoji;
    }

    public static void main(String[] args){
        String str = "\\xF0\\x9F\\x98\\x82";
        for (int i = 0; i < str.length(); i++) {
            System.err.println(i+ "--------->" + isNotEmojiChar(str.charAt(i)));
        }

        System.out.println(str);
        System.out.println(containsEmoji(str));
    }
}
