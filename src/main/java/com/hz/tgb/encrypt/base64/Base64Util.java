package com.hz.tgb.encrypt.base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

import Decoder.BASE64Encoder;

/**
 * 统一项目中base64算法的使用。可避免项目中引用不同三方库去实现相同代码<br>
 *
 * 利用 Decoder.BASE64Encoder 进行 MD5加密
 * 
 * @author hezhao
 * @Time 2017年7月31日 下午4:29:21
 */
public class Base64Util {

	/**
	 * encode for
	 * byte - > byte using base64
	 *
	 * @param binaryData
	 * @return
	 */
	public static byte[] encodeByte(final byte[] binaryData) {
		return Base64.encodeBase64(binaryData, false);
	}

	/**
	 * encode for
	 * byte - > string using base64
	 *
	 * @param binaryData
	 * @return
	 */
	public static String encode(final byte[] binaryData) {
		return Base64.encodeBase64String(binaryData);
	}

	/**
	 * decode for
	 * string -> byte using base64
	 *
	 * @param base64String
	 * @return
	 */
	public static byte[] decode(final String base64String) {
		return new Base64().decode(base64String);
	}

	/**
	 * decode for
	 * byte -> byte using base64
	 *
	 * @param base64Data
	 * @return
	 */
	public static byte[] decode(final byte[] base64Data) {
		return new Base64().decode(base64Data);
	}

	public static String decodeStr(final String base64String) {
		return new String(decode(base64String));
	}

	/**
	 * 使用Base64 进行 MD5 加密
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

	public static void main(String[] args) {
		System.out.println(Base64Util.md5("admin"));
		System.out.println(Base64Util.check("admin","ISMvKXpXpadDiUoOSoAfww=="));
	}

}
