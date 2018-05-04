package com.hz.tgb.encryption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author hezhao
 * @Time 2018-05-04 2:26
 * @Description 无
 * @Version V 1.0
 */
public class HashUtil {

    private static Logger log = LoggerFactory.getLogger(HashUtil.class);

    public HashUtil() {
    }

    public static byte[] hash(String algorithm, byte[] bytes) throws NoSuchAlgorithmException {
        if (algorithm != null && bytes != null) {
            MessageDigest md = MessageDigest.getInstance(algorithm.toUpperCase());
            return md.digest(bytes);
        } else {
            return null;
        }
    }

    public static byte[] hash(String algorithm, String str) throws NoSuchAlgorithmException {
        return str == null ? null : hash(algorithm, str.getBytes());
    }

    public static byte[] hashMD5(byte[] bytes) {
        try {
            return hash("MD5", bytes);
        } catch (NoSuchAlgorithmException var2) {
            log.error("The algorithm is not available in the caller\'s environment.", var2);
            return null;
        }
    }

    public static byte[] hashMD5(String str) {
        return str == null ? null : hashMD5(str.getBytes());
    }

    public static byte[] hashSHA1(String str) {
        try {
            return hash("SHA1", str);
        } catch (NoSuchAlgorithmException var2) {
            log.error("The algorithm is not available in the caller\'s environment.", var2);
            return null;
        }
    }
}
