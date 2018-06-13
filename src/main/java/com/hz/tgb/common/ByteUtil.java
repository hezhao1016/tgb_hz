package com.hz.tgb.common;

/**
 * byte转字符串相关工具类
 * @author hezhao
 */
public class ByteUtil {

    /***
     * byte数组转换为16进制字符串
     *
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        StringBuffer stringBuffer = new StringBuffer();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuffer.append(0);
            }
            stringBuffer.append(hv);
        }
        return stringBuffer.toString();
    }

    /***
     * 16进制字符串转换为byte数组,输入字符串要大写
     *
     * @param hexString
     * @return
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || "".equals(hexString)) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * Long转换成Byte数组
     * @param l
     * @return
     */
    public static byte[] toByteArray(Long l) {
        byte[] b = new byte[8];

        for (int i = 0; i < b.length; ++i) {
            b[7 - i] = (byte) ((int) (l.longValue() >>> i * 8));
        }

        return b;
    }

    /**
     * Integer转换成Byte数组
     * @param i
     * @return
     */
    public static byte[] toByteArray(Integer i) {
        byte[] b = new byte[4];

        for (int j = 0; j < 4; ++j) {
            b[3 - j] = (byte) (i.intValue() >>> j * 8);
        }

        return b;
    }
}
