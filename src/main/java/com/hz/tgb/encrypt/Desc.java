package com.hz.tgb.encrypt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * DES 加密
 * @author hezhao on 2017年7月25日 下午4:07:33
 * by paybay
 */
public class Desc {
	
	private static final Logger logger = LoggerFactory.getLogger(Desc.class);
	
	private static final String deskey = "123456781234567812345678";
	private static final String Algorithm = "DESede";
	
	public static String byteArr2HexStr(byte[] bytea) throws Exception {  
        String sHex = "";  
        int iUnsigned = 0;  
        StringBuffer sbHex = new StringBuffer();  
        for (int i = 0; i < bytea.length; i++) {  
            iUnsigned = bytea[i];  
            if (iUnsigned < 0) {  
                iUnsigned += 256;  
            }  
            if (iUnsigned < 16) {  
                sbHex.append("0");  
            }  
            sbHex.append(Integer.toString(iUnsigned, 16));  
        }  
        sHex = sbHex.toString();  
        return sHex;  
    }  

	public static byte[] hexStr2ByteArr(String sHex) throws Exception {  
        
        if(sHex.length()%2!=0){  
            sHex="0"+sHex;  
        }  
        byte[] bytea = new byte[sHex.length() / 2];  
          
        String sHexSingle = "";  
        for (int i = 0; i < bytea.length; i++) {  
            sHexSingle = sHex.substring(i * 2, i * 2 + 2);  
            bytea[i] = (byte) Integer.parseInt(sHexSingle, 16);  
        }  
        return bytea;  
    }  

	/**
	 * 解密函数
	 * 
	 * @param src
	 *            密文
	 * @return
	 * @throws Exception
	 */
	public static String decryptMode(String src) throws Exception {
		 if(src.length()%2!=0){  
			 src="0"+src;  
	        }  
	        byte[] bytea = new byte[src.length() / 2];  
	          
	        String sHexSingle = "";  
	        for (int i = 0; i < bytea.length; i++) {  
	            sHexSingle = src.substring(i * 2, i * 2 + 2);  
	            bytea[i] = (byte) Integer.parseInt(sHexSingle, 16);  
	        }  
	        
		byte[] key = deskey.getBytes("UTF-8");
		SecretKey deskey = new SecretKeySpec(key, Algorithm);
		Cipher c1 = Cipher.getInstance(Algorithm);
		c1.init(Cipher.DECRYPT_MODE, deskey); // // 初始化为解密模式
		byte[] byteb = c1.doFinal(bytea);
		
        return new String(byteb);  
		
	}

	/**
	 * 加密方法
	 * 
	 * @param msg
	 *            源数据
	 * @return
	 */
	public static String encryptMode(String msg) throws Exception {
		byte[] key = deskey.getBytes("UTF-8");
		SecretKey deskey = new SecretKeySpec(key, Algorithm); // 生成密钥
		Cipher c1 = Cipher.getInstance(Algorithm); // 实例化负责加密/解密的Cipher工具类
		c1.init(Cipher.ENCRYPT_MODE, deskey); // 初始化为加密模式
		byte[] bytea = c1.doFinal(msg.getBytes());
		String sHex = "";  
        int iUnsigned = 0;  
        StringBuffer sbHex = new StringBuffer();  
        for (int i = 0; i < bytea.length; i++) {  
            iUnsigned = bytea[i];  
            if (iUnsigned < 0) {  
                iUnsigned += 256;  
            }  
            if (iUnsigned < 16) {  
                sbHex.append("0");  
            }  
            sbHex.append(Integer.toString(iUnsigned, 16));  
        }  
        sHex = sbHex.toString();  
        return sHex;
	}
	
	public static void main(String[] args){
		try {
			String msg = "6200100005258965";
			String newMsg = Desc.encryptMode(msg);//加密
			System.out.println(newMsg);
			String recMsg = newMsg;//接收银行返回报文
			String newRecMsg = Desc.decryptMode(recMsg);
			System.out.println(newRecMsg);
		} catch (Exception e) {
			logger.error(e.toString(), e);
			e.printStackTrace();
		}
	}
}
