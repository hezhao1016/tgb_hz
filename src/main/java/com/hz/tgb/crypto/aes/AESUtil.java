package com.hz.tgb.crypto.aes;

import com.hz.tgb.common.ByteUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author 80114515 AES加解密工具类
 */
public class AESUtil {

    private static final Logger logger = LoggerFactory.getLogger(AESUtil.class);

    /***
     * 系统默认编码u8
     */
    public static final String DEFAULT_ENCODING = "UTF-8";

    // 算法定义
    private static final String AES_ALGORITHM = "AES";

    /***
     * AES加密
     * 
     * @param content 待加密内容
     * @param aesKey 密钥
     * @return
     */
    public static String encrypt(String content, String aesKey) {
        // 默认使用U8编码
        return encrypt(content, aesKey, DEFAULT_ENCODING);
    }

    /***
     * AES加密
     * 
     * @param content 待加密内容
     * @param aesKey 密钥
     * @param encoding 编码
     * @return
     */
    public static String encrypt(String content, String aesKey, String encoding) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance(AES_ALGORITHM);
            // 指定随机数算法,解决windows与linux不兼容的问题
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(aesKey.getBytes(encoding));
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, AES_ALGORITHM);
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);// 创建密码器
            byte[] byteContent = content.getBytes(encoding);
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            // 加密
            byte[] result = cipher.doFinal(byteContent);
            // 转换16进制字符串后返回
            return ByteUtil.bytesToHexString(result);
        } catch (NoSuchAlgorithmException e) {
            logger.error("aes encrypt NoSuchAlgorithmException error.", e);
        } catch (NoSuchPaddingException e) {
            logger.error("aes encrypt NoSuchPaddingException error.", e);
        } catch (UnsupportedEncodingException e) {
            logger.error("aes encrypt UnsupportedEncodingException error.", e);
        } catch (InvalidKeyException e) {
            logger.error("aes encrypt InvalidKeyException error.", e);
        } catch (IllegalBlockSizeException e) {
            logger.error("aes encrypt IllegalBlockSizeException error.", e);
        } catch (BadPaddingException e) {
            logger.error("aes encrypt BadPaddingException error.", e);
        }

        return null;
    }

    /***
     * AES解密，输入密文16进制字符串,默认使用U8编码
     * 
     * @param cipherText
     * @param aesKey
     * @return
     */
    public static String decrypt(String cipherText, String aesKey) {
        // 将16进制的密文转换为byte类型
        byte[] cipherTextByte = ByteUtil.hexStringToBytes(cipherText);
        if (cipherTextByte == null) {
            return null;
        }
        // 默认使用U8编码返回原字符串
        return decrypt(cipherTextByte, aesKey, DEFAULT_ENCODING);
    }

    /***
     * AES解密，输入为byte数组,默认使用U8编码
     * 
     * @param cipherTextByte 加密内容
     * @param aesKey 密钥
     * @return
     */
    public static String decrypt(byte[] cipherTextByte, String aesKey) {
        // 默认使用U8编码返回原字符串
        return decrypt(cipherTextByte, aesKey, DEFAULT_ENCODING);
    }

    /***
     * AES解密，输入为byte数组,可以指定编码
     * 
     * @param cipherTextByte 加密内容
     * @param aesKey 密钥
     * @param encoding 编码
     * @return
     */
    public static String decrypt(byte[] cipherTextByte, String aesKey, String encoding) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance(AES_ALGORITHM);
            // 指定随机数算法,解决windows与linux不兼容的问题
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(aesKey.getBytes(encoding));
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, AES_ALGORITHM);
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(cipherTextByte);
            return new String(result, encoding);
        } catch (NoSuchAlgorithmException e) {
            logger.error("aes decrypt NoSuchAlgorithmException error.", e);
        } catch (NoSuchPaddingException e) {
            logger.error("aes decrypt NoSuchPaddingException error.", e);
        } catch (InvalidKeyException e) {
            logger.error("aes decrypt InvalidKeyException error.", e);
        } catch (IllegalBlockSizeException e) {
            logger.error("aes decrypt IllegalBlockSizeException error.", e);
        } catch (BadPaddingException e) {
            logger.error("aes decrypt BadPaddingException error.", e);
        } catch (UnsupportedEncodingException e) {
            logger.error("aes decrypt UnsupportedEncodingException error.", e);
        }

        return null;
    }
    
    public static void main(String[] args) {
        System.out.println(encrypt("13530629093", "lWdZ8g70EezTqHqQOpDWwPxARZjMrzEXuPA8NO5J2uX"));
        System.out.println(decrypt("506475599128d1eef32fbaed9ce14f38", "lWdZ8g70EezTqHqQOpDWwPxARZjMrzEXuPA8NO5J2uX"));
    }

}
