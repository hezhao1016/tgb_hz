/**
 * 
 */
package com.hz.tgb.encrypt;

import com.hz.tgb.common.ByteUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/** SHA-256摘要工具类
 * @author hezhao
 */
public class SHA256Util {

    private static final Logger logger = LoggerFactory.getLogger(SHA256Util.class);

    private static final String SHA256_ALGORITHM = "SHA-256";

    /***
     * 系统默认编码u8
     */
    public static final String DEFAULT_ENCODING = "UTF-8";

    /***
     * SHA256摘要，默认使用U8编码
     * 
     * @param source
     * @return
     */
    public static String sha256(String source) {
        return sha256(source, DEFAULT_ENCODING);
    }

    /***
     * SHA摘要,指定编码
     * 
     * @param source
     * @param encoding
     * @return
     */
    public static String sha256(String source, String encoding) {
        try {
            // 使用
            MessageDigest messageDigest = MessageDigest.getInstance(SHA256_ALGORITHM);
            messageDigest.update(source.getBytes(encoding));
            byte[] result = messageDigest.digest();
            // 转换byte为16进制字符串
            return ByteUtil.bytesToHexString(result);
        } catch (NoSuchAlgorithmException e) {
            logger.error("sha-256 NoSuchAlgorithmException error.", e);
        } catch (UnsupportedEncodingException e) {
            logger.error("sha-256 UnsupportedEncodingException error.", e);
        }

        return null;
    }
    
    public static void main(String[] args){
        System.out.println(sha256("123456231231232131232ADASDAADADAQQweqweqweqeqeqeqeqweqwewqewqeqwwq12312"));
    }
}
