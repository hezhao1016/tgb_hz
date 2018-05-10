package com.hz.tgb.encrypt.sign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.TreeMap;

/** MD5 签名工具类
 * @author hezhao
 */
public class Md5SignatureUtil {

    private static final Logger logger = LoggerFactory.getLogger(Md5SignatureUtil.class);

    /***
     * 系统默认编码u8
     */
    public static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * 生成商户签名
     * 
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String generateSign(TreeMap<String, String> srcMap, String md5Key) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] inputByteArray = (generateSignSrc(srcMap) + md5Key).getBytes(DEFAULT_ENCODING);
        messageDigest.update(inputByteArray);
        byte[] resultByteArray = messageDigest.digest();
        return byteArrayToHex(resultByteArray);
    }

    /***
     * 校验返回签名
     * 
     * @param responseMap
     * @param md5Key
     * @return
     */
    public static boolean checkSign(TreeMap<String, String> responseMap, String md5Key) {
        boolean result = false;
        try {
            String responseSign = responseMap.get("sign");
            String calSign = generateSign(responseMap, md5Key);
            result = responseSign.equalsIgnoreCase(calSign);
        } catch (Exception e) {
            logger.error("checkSign params:{},md5Key:{} error.", responseMap, md5Key);
            logger.error(e.getMessage(), e);
        }

        return result;
    }

    private static String byteArrayToHex(byte[] byteArray) {

        // 首先初始化一个字符数组，用来存放每个16进制字符
        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
        char[] resultCharArray = new char[byteArray.length * 2];

        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }

        // 字符数组组合成字符串返回
        return new String(resultCharArray);
    }

    /**
     * 生成签名原串。
     * 
     * @return
     */
    private static String generateSignSrc(TreeMap<String, String> srcMap) {
        StringBuffer sb = new StringBuffer();
        Iterator<java.util.Map.Entry<String, String>> ite = srcMap.entrySet().iterator();
        while (ite.hasNext()) {
            java.util.Map.Entry<String, String> tmpEntry = ite.next();
            if ("sign".equalsIgnoreCase(tmpEntry.getKey()) //
                    || tmpEntry.getValue().isEmpty()) {
                continue;
            }
            sb.append(tmpEntry.getKey()).append("=").append(tmpEntry.getValue()).append("&");
        }
        sb.append("key=");
        logger.debug("source:{}", sb);
        return sb.toString();
    }
}
