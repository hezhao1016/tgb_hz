package com.hz.tgb.encryption;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import Decoder.BASE64Encoder;

/**
 * 利用 Decoder.BASE64Encoder 进行 MD5加密
 * 
 * @author hezhao
 * @Time 2017年7月31日 下午4:29:21
 */
public class BASE64EncoderUtil {

	/**
	 * 加密
	 * 
	 * @author hezhao
	 * @Time 2017年7月31日 下午4:29:15
	 * @param str
	 * @return
	 */
	public final static String md5(String str) {
		try {
			MessageDigest md5;
			md5 = MessageDigest.getInstance("MD5");
			BASE64Encoder base64en = new BASE64Encoder();
			// 加密后的字符串
			String mStr = base64en.encode(md5.digest(str.getBytes("utf-8")));
			return mStr;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 使用MD5 对两端加密后的密文进行比较
	 * 
	 * @author hezhao
	 * @Time 2017年7月31日 下午4:30:11
	 * @param strOne
	 *            未加密的字符串
	 * @param strTwo
	 *            已加密的字符串
	 * @return boolean
	 */
	public static boolean check(String strOne, String strTwo) {
		if (md5(strOne).equals(strTwo))
			return true;
		else
			return false;
	}

}
