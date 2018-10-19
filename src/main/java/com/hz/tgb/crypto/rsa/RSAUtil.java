package com.hz.tgb.crypto.rsa;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA加密算法生成公钥和私钥
 *
 * Created by hezhao on 2018-10-19 16:41
 */
public class RSAUtil {
    private static final Logger LOG = LoggerFactory.getLogger(RSAUtil.class);

    public static final String CHARSET = "UTF-8";
    public static final String RSA_ALGORITHM = "RSA";
    private static final String PUBLIC_KEY = "publicKey";
    private static final String PRIVATE_KEY = "privateKey";

    /**
     * 生成公钥私钥
     *
     * @param keySize
     * @return
     */
    public static Map<String, String> createKeys(int keySize){
        //为RSA算法创建一个KeyPairGenerator对象
//        KeyPairGenerator kpg;
//        try{
//            kpg = KeyPairGenerator.getInstance(RSA_ALGORITHM);
//        }catch(NoSuchAlgorithmException e){
//            throw new IllegalArgumentException("No such algorithm-->[" + RSA_ALGORITHM + "]");
//        }
//
//        //初始化KeyPairGenerator对象,密钥长度
//        kpg.initialize(keySize);
//        //生成密匙对
//        KeyPair keyPair = kpg.generateKeyPair();
//        //得到公钥
//        Key publicKey = keyPair.getPublic();
//        String publicKeyStr = Base64.encodeBase64URLSafeString(publicKey.getEncoded());
//        //得到私钥
//        Key privateKey = keyPair.getPrivate();
//        String privateKeyStr = Base64.encodeBase64URLSafeString(privateKey.getEncoded());
//        Map<String, String> keyPairMap = new HashMap<String, String>();
//        keyPairMap.put("publicKey", publicKeyStr);
//        keyPairMap.put("privateKey", privateKeyStr);
//
//        return keyPairMap;

        try {
            Map<String, String> keyPairMap = new HashMap<String, String>();
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");
            generator.initialize(keySize, new SecureRandom());
            KeyPair pair = generator.generateKeyPair();
            PublicKey pubKey = pair.getPublic();
            PrivateKey privKey = pair.getPrivate();
            byte[] pk = pubKey.getEncoded();
            byte[] privk = privKey.getEncoded();
            String publicKeyStr = new String(Base64.encodeBase64(pk));
            String privateKeyStr = new String(Base64.encodeBase64(privk));

            LOG.debug("公钥Base64编码:" + publicKeyStr);
            LOG.debug("私钥Base64编码:" + privateKeyStr);

            keyPairMap.put(PUBLIC_KEY, publicKeyStr);
            keyPairMap.put(PRIVATE_KEY, privateKeyStr);
            return keyPairMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 得到公钥
     * @param publicKey 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static RSAPublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //通过X509编码的Key指令获得公钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
        RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
        return key;
    }

    /**
     * 得到私钥
     * @param privateKey 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static RSAPrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //通过PKCS#8编码的Key指令获得私钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
        RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
        return key;
    }

    /**
     * 公钥加密
     * @param data
     * @param publicKey
     * @return
     */
    public static String publicEncrypt(String data, RSAPublicKey publicKey){
        try{
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), publicKey.getModulus().bitLength()));
        }catch(Exception e){
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 私钥解密
     * @param data
     * @param privateKey
     * @return
     */
    public static String privateDecrypt(String data, RSAPrivateKey privateKey){
        try{
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), privateKey.getModulus().bitLength()), CHARSET);
        }catch(Exception e){
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 私钥加密
     * @param data
     * @param privateKey
     * @return
     */
    public static String privateEncrypt(String data, RSAPrivateKey privateKey){
        try{
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), privateKey.getModulus().bitLength()));
        }catch(Exception e){
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 公钥解密
     * @param data
     * @param publicKey
     * @return
     */
    public static String publicDecrypt(String data, RSAPublicKey publicKey){
        try{
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), publicKey.getModulus().bitLength()), CHARSET);
        }catch(Exception e){
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }

    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize){
        int maxBlock = 0;
        if(opmode == Cipher.DECRYPT_MODE){
            maxBlock = keySize / 8;
        }else{
            maxBlock = keySize / 8 - 11;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] buff;
        int i = 0;
        try{
            while(datas.length > offSet){
                if(datas.length-offSet > maxBlock){
                    buff = cipher.doFinal(datas, offSet, maxBlock);
                }else{
                    buff = cipher.doFinal(datas, offSet, datas.length-offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
        }catch(Exception e){
            throw new RuntimeException("加解密阀值为["+maxBlock+"]的数据时发生异常", e);
        }
        byte[] resultDatas = out.toByteArray();
        IOUtils.closeQuietly(out);
        return resultDatas;
    }


    public static void main (String[] args) throws Exception {
      /*  Map<String, String> keyMap = RSAUtil.createKeys(1024);
        String  publicKey = keyMap.get("publicKey");
        String  privateKey = keyMap.get("privateKey");
        System.out.println("公钥: \n\r" + publicKey);
        System.out.println("私钥： \n\r" + privateKey);*/

        System.out.println("公钥加密——私钥解密");
       /* String str = "站在大明门前守卫的禁卫军，事先没有接到\n" +
                "有关的命令，但看到大批盛装的官员来临，也就\n" +
                "以为确系举行大典，因而未加询问。进大明门即\n" +
                "为皇城。文武百官看到端门午门之前气氛平静，\n" +
                "城楼上下也无朝会的迹象，既无几案，站队点名\n" +
                "的御史和御前侍卫“大汉将军”也不见踪影，不免\n" +
                "心中揣测，互相询问：所谓午朝是否讹传？";*/

        String approveStr=" {\n" +
                "\t\t\"expenseAmount\": 80.00,\n" +
                "\t\t\"expenseReason\": \"内部转寄\",\n" +
                "\t\t\"expenseType\": \"3\",\n" +
                "\t\t\"remark\": \"报销快递费\",\n" +
                "\t\t\"expenseDetailWholeList\": [{\n" +
                "\t\t    \"businessActivityNumber\": \"GX20180927-00000001\",\n" +
                "                    \"businessActivityDate\": \"2018-10-12\",\n" +
                "                    \"detailAmount\": 20.00,\n" +
                "                    \"referenceNumber\":\"1234567890\",\n" +
                "                    \"agentPaymentName\":\"代理付款名称1\"\n" +
                "\n" +
                "\t\t},{\n" +
                "\t\t    \"businessActivityNumber\": \"GX20180927-00000002\",\n" +
                "                    \"businessActivityDate\": \"2018-10-11\",\n" +
                "                    \"detailAmount\": 60.00,\n" +
                "                    \"referenceNumber\":\"2234567892\",\n" +
                "                    \"agentPaymentName\":\"代理付款名称2\"\n" +
                "\n" +
                "\t\t}]\n" +
                "}";


        System.out.println("\r明文：\r\n" + approveStr);
        System.out.println("\r明文大小：\r\n" + approveStr.getBytes().length);
        String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCMiqkyjORz_MGEbY6z4BK_tVETm8AEhNfXwSJSwScGpV73MvxSrezbxFLPUoRVTdIPXyuIb3fpnbnL4TLoxqHYUDs7M2xEV9FTgDIYXC_XL4oQXxp_iTRhi8b1OVYJBp6LlUg54ohSXYmh2mz9oz_vrqHN61suG1479FgL1ryt8QIDAQAB";
        String privateKey="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIyKqTKM5HP8wYRtjrPgEr-1URObwASE19fBIlLBJwalXvcy_FKt7NvEUs9ShFVN0g9fK4hvd-mducvhMujGodhQOzszbERX0VOAMhhcL9cvihBfGn-JNGGLxvU5VgkGnouVSDniiFJdiaHabP2jP--uoc3rWy4bXjv0WAvWvK3xAgMBAAECgYApO0j2XqVrbdzsWCQEm5mEitJVeV01NjAIyU2jj60-YufgiCHEs-gIdamBz4ipaiJVhitEZMd-T1rB-Lix2AyE9WUTnUV44slmkSrBhu0w9bECLmcZiu_b-MXPeJtgIv-u8gNfjLqmSjqSGiPFcxaXzei6D6cYwlRc1Xz7skOUPQJBAMY7W31XacsgQgptD7cWDGEBC7uADj2kvtyqQrnJuq2ETjbNL7iM7Y15k7iEwH62XP7kxdkoUDXt0Mcx8rFyVcsCQQC1f3RPnG8tV4k7D632ndWvtlsm5RkqTyQG7MLsQ471eL9ggA1D0rDXPbu0n1-aUV-a3gnxglzipDXXC7FhsfOzAkAMRwLcmXxg60eL08NkaIYtv93VKBI5uSllC1qEnTzloEsj08N_36Ka34vJotc1QeAynUKvt3J4VJlYrPxGBznPAkBTKBlXXHpH0QKML-vpwXklb6ylznPQQcBk99d7PpzidTdh9GmfeIoTpDI7WIYKA1yFbVQq9iquoMQWSkI0W78nAkEAtU-ws7sXmEdoyOs_G7PEUVtvTcLxTZS_pNV5FeW_iVZ2yFa_onwCDcysj-YDDHMBDi7ZvCHVNrttdZabGk5a4Q";


        String encodedData = RSAUtil.publicEncrypt(approveStr, RSAUtil.getPublicKey(publicKey));
        System.out.println("密文：\r\n" + encodedData);
        //privateKey="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKOgFPnfZ4e76SPJwEwYDtNE0pE4V3VnuTtPFmZEn8DQT8U_xtSszINnNBdiikGgMrJHZZaOoas5TZ_Vna1uiuazSNQ3cLkhPB8XCge-T_jqKVua6ZfNLekTpBLSq949XfKD64ex28ASSbzk-ZAKxgT0ALVJdoDTplJRMECVKThTAgMBAAECgYBn7LAF-LAQFPjCA01Ax3L7GtZG6CscZbHPiq7ma6Wv2F8iIBzQTaRjvXPnem3nurEYbKKIWrUyRGYaI5pPYkuTsO7cQ0-ZIoBVznRiF2jEOSHBNrX1t5eZ3I7rlhZCMLo1BpFRuCsPXtB8S3a6qKJqbmMoXvsq6Dh7NfMXUu8OOQJBAN3DZl7dL_xrPsUKE6dZ2qXc7FIM1INoyhhAx1Po9QsoqFhKe9Ry6mDFNEGORPuwR-yTZcrvsedX81ZDlOYy7tUCQQC84u9MqUeoCe3msNpgj6WNYYV9bTcGQgJjkqAlUhjV1Jk89thnUNCp0yNdOJ1-SQrkIK5fK3kdITnsQSjjVy6HAkEAsGIgZEeNSTmLvXDcXPks3UuwczpdNP_97fhoEidx76Qw8biccUfNf5aaj6G24HE_Y72A8z5ew-DCXNR38_iC2QJAZk3jnnnxPapjGBehr7sdx72SN6T3sIQAFz5LTutwWCCV_tcTzqGO4uxgsxT--G9HXtf0mcdKCeT9ImEIbFnD8QJBAMNhYsML3hsmodi6u1viLHxzNhSV3TXg_JuOkxCpVJrI4EqnQJybsLbLVfHAb2U43quGdn7h6obN9D77wW_jPm4";
        String decodedData = RSAUtil.privateDecrypt(encodedData, RSAUtil.getPrivateKey(privateKey));
        System.out.println("解密后文字: \r\n" + decodedData);

    }

}
