package com.hz.tgb.crypto;

/**
 * Java实现字符与Unicode互转
 *
 * Created by hezhao on 2018/10/20 20:57
 */
public class UnicodeUtil {

    /*
    Java实现字符与Unicode互转，有两个主要方法：
    - Integer.toHexString(); // 转码
    - Integer.parseInt(); // 解码
    通过以上两个方法实现对字符的转码与解码。
    */

    /**
     * Unicode转中文
     * @param source
     * @return
     */
    public static String unicode(String source){
        StringBuffer sb = new StringBuffer();
        char [] source_char = source.toCharArray();
        String unicode = null;
        for (int i=0;i<source_char.length;i++) {
            unicode = Integer.toHexString(source_char[i]);
            if (unicode.length() <= 2) {
                unicode = "00" + unicode;
            }
            sb.append("\\u" + unicode);
        }
        return sb.toString();
    }

    /**
     * 中文转Unicode
     * @param unicode
     * @return
     */
    public static String decodeUnicode(String unicode) {
        StringBuffer sb = new StringBuffer();

        String[] hex = unicode.split("\\\\u");

        for (int i = 1; i < hex.length; i++) {
            int data = Integer.parseInt(hex[i], 16);
            sb.append((char) data);
        }
        return sb.toString();
    }

    /**
     * 中文转Unicode
     * @param dataStr
     * @return
     */
    public static String decodeUnicode2(String dataStr) {
        int start = 0;
        int end = 0;
        final StringBuffer buffer = new StringBuffer();
        while (start > -1) {
            end = dataStr.indexOf("\\u", start + 2);
            String charStr = null;
            if (end == -1) {
                charStr = dataStr.substring(start + 2, dataStr.length());
            } else {
                charStr = dataStr.substring(start + 2, end);
            }
            char letter = (char) Integer.parseInt(charStr, 16);
            buffer.append(new Character(letter).toString());
            start = end;
        }
        return buffer.toString();
    }

    public static void main(String[] args) {
        String str = "假装这里有Emoji表情";

        String s1 = unicode(str);
        System.out.println(s1);

        System.out.println("decodeUnicode:" + decodeUnicode(s1));

        System.out.println("decodeUnicode2:" + decodeUnicode2(s1));

    }

}
