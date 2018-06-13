package com.hz.tgb.crypto;

import java.nio.charset.Charset;

/**
 * 使用Byte数组进行加密解密
 * Created by hezhao on 2018-06-13 16:33
 */
public class ByteEncoder {

	/** 默认密钥 */
	private static final String DEFAULT_KEY = "FECOI()*&<MNCXZPKL";

	/** 默认编码格式 */
	private static final String DEFAULT_ENCODING = "UTF-8";
	
	/**
	 * 加密
	 * @param content 源字符串
	 * @return
	 */
	public static String encode(String content) {
		return encode(content, DEFAULT_KEY, DEFAULT_ENCODING);
	}

	/**
	 * 加密
	 * @param content 源字符串
	 * @param key 密钥
	 * @param encoding 编码格式
	 * @return
	 */
	public static String encode(String content, String key, String encoding) {
		Charset charset = Charset.forName(encoding);
		byte[] keyBytes = key.getBytes(charset);
		byte[] b = content.getBytes(charset);
		for (int i = 0, size = b.length; i < size; i++) {
			for (byte keyBytes0 : keyBytes) {
				b[i] = (byte)(b[i] ^ keyBytes0);
			}
		}
		return new String(b);
	}

	/**
	 * 解密
	 * @param decontent 加过密的字符串
	 * @return
	 */
	public static String decode(String decontent){
		return decode(decontent, DEFAULT_KEY, DEFAULT_ENCODING);
	}

	/**
	 * 解密
	 * @param decontent 加过密的字符串
	 * @param key 密钥
	 * @param encoding 编码格式
	 * @return
	 */
	public static String decode(String decontent, String key, String encoding) {
		Charset charset = Charset.forName(encoding);
		byte[] keyBytes = key.getBytes(charset);
		byte[] e = decontent.getBytes(charset);
		byte[] dee = e;
		for (int i = 0, size = e.length; i < size; i++) {
			for (byte keyBytes0 : keyBytes) {
				e[i] = (byte)(dee[i] ^ keyBytes0);
			}
		}
		return new String(e);
	}
	
	public static void main(String[] args) {
		String s = "you are right";
		String enc = encode(s);
		String dec = decode(enc);
		System.out.println(enc);
		System.out.println(dec);
	}
}
