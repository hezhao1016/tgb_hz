package com.hz.tgb.crypto.sign;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA签名工具类
 * 
 * @author Yaphis 2016年8月23日 下午2:13:17
 */
public class RSASignature {

    private static final Logger LOG = LoggerFactory.getLogger(RSASignature.class);
    /**
     * 签名算法
     */
    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    private RSASignature() {
    }

    /**
     * RSA私钥加签
     * 
     * @param content 待签名内容
     * @param privateKey 签名私钥
     * @param charset 字符集
     * @return
     */
    public static String sign(String content, String privateKey, String charset) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initSign(priKey);
            signature.update(content.getBytes(charset));
            byte[] signed = signature.sign();
            return new String(Base64.encodeBase64(signed), charset);
        } catch (Exception e) {
            LOG.error("sign throws Exception:", e);
            throw new RuntimeException(e.toString());
        }
    }

    /**
     * RSA私钥加签
     * 
     * @param content 待签名内容
     * @param privateKey 签名私钥
     * @return
     */
    public static String sign(String content, String privateKey) {
        return sign(content, privateKey, "UTF-8");
    }

    /**
     * RSA签名检查
     * 
     * @param content 待签名内容
     * @param sign 签名
     * @param publicKey 签名公钥
     * @param charset 字符集
     * @return
     */
    public static boolean signCheck(String content, String sign, String publicKey, String charset) {
        if (StringUtils.isBlank(content) || StringUtils.isBlank(sign) || StringUtils.isBlank(publicKey)) {
            LOG.error("one of content:{},sign:{},publicKey:{} is blank!", new Object[] { content, sign, publicKey });
            return false;
        }
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = Base64.decodeBase64(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initVerify(pubKey);
            signature.update(content.getBytes(charset));
            return signature.verify(Base64.decodeBase64(sign));
        } catch (Exception e) {
            LOG.error("signCheck throws Exception:", e);
            throw new RuntimeException(e.toString());
        }
    }

    /**
     * RSA签名检查
     * 
     * @param content 待签名内容
     * @param sign 签名
     * @param publicKey 签名公钥
     * @return
     */
    public static boolean signCheck(String content, String sign, String publicKey) {
        return signCheck(content, sign, publicKey);
    }
}
