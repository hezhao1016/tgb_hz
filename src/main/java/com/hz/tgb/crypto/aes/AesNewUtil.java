package com.hz.tgb.crypto.aes;

import com.hz.tgb.common.ByteUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * 新的AES加解密工具类,用于兼容与Andriod的加解密
 * @author 80114515
 */
public class AesNewUtil {
    private static final Logger logger = LoggerFactory.getLogger(AesNewUtil.class);

    /***
     * 系统默认编码u8
     */
    public static final String DEFAULT_ENCODING = "UTF-8";

    // 算法定义
    private static final String AES_ALGORITHM = "AES";
    // 指定填充方式
    private static final String AES_PADDING = "AES/CBC/PKCS5Padding";

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
//            content = align(content,false);
            aesKey = align(aesKey,true);

            IvParameterSpec zeroIv = new IvParameterSpec(aesKey.getBytes());  
            SecretKeySpec key = new SecretKeySpec(aesKey.getBytes(encoding), AES_ALGORITHM);
            Cipher cipher = Cipher.getInstance(AES_PADDING);// 创建密码器
            byte[] byteContent = content.getBytes(encoding);
            cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);// 初始化
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
        } catch (InvalidAlgorithmParameterException e) {
            logger.error("aes encrypt InvalidAlgorithmParameterException error.", e);
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
//        cipherText = align(cipherText,false);
        aesKey = align(aesKey,true);

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
        aesKey = align(aesKey,true);
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
            IvParameterSpec zeroIv = new IvParameterSpec(aesKey.getBytes()); 
            SecretKeySpec key = new SecretKeySpec(aesKey.getBytes(encoding), AES_ALGORITHM);
            Cipher cipher = Cipher.getInstance(AES_PADDING);// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);// 初始化
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
        } catch (InvalidAlgorithmParameterException e) {
            logger.error("aes decrypt InvalidAlgorithmParameterException error.", e);
        }

        return null;
    }

    //补全字符
    public static String align(String str,boolean isKey){
        if (str == null || str.equals("")) {
            return "";
        }

        // 如果是密码，需要确保其长度为16
        if(isKey){
            if(str.length() > 16){
                return str.substring(0,16);
            }else{
                return align(str,false);
            }
        }else{
            // 如果是被加密字符串或长度不足的密码，则确保其长度为16的整数倍
            int zerocount = 16 - str.length() % 16;
            for (int i = 0; i < zerocount; i++) {
                str = str + '0';
            }
            return str;
        }
    }

    public static void main(String[] args) {
        System.out.println(encrypt("13530629093", "lWdZ8g70EezTqHqQOpDWwPxARZjMrzEXuPA8NO5J2uX"));
    }
}
