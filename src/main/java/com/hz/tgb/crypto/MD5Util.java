package com.hz.tgb.crypto;

import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;

/**
 * MD5加密工具类
 *
 * @author hezhao
 * @version v1.0
 * @date 2015年4月17日
 * @time 下午5:15:50
 */
public class MD5Util {

	private static final char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	/**此字符串相当于加密用的串，数据校验时结果不会被猜测。*/
	private static final String default_key = "dAA%D#V*2a9r4I!V";

	private MD5Util(){
		// 私有类构造方法
	}

	/**
	 * 对字符串进行加密
	 *
	 * @author hezhao
	 * @param str 字符串
	 * @param algorithm 加密算法，例如：MD2、MD5、SHA-1、SHA-256、SHA-384、SHA-512
	 * @return
	 */
	public static String digest(String str, String algorithm) {
		try {
			byte[] strTemp = str.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance(algorithm);
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char[] chars = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
                chars[k++] = hexDigits[byte0 >>> 4 & 0xf];
                chars[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(chars);
		} catch (Exception e) {
			return null;
		}
	}

    /**
     * 对字符串进行MD2加密
     *
     * @author hezhao
     * @param str 字符串
     * @return
     */
    public static String md2(String str) {
        return digest(str, "MD2");
    }

    /**
     * 对字符串进行SHA-1加密
     *
     * @author hezhao
     * @param str 字符串
     * @return
     */
    public static String sha1(String str) {
        return digest(str, "SHA-1");
    }

    /**
     * 对字符串进行SHA-256加密
     *
     * @author hezhao
     * @param str 字符串
     * @return
     */
    public static String sha256(String str) {
        return digest(str, "SHA-256");
    }

    /**
     * 对字符串进行SHA-384加密
     *
     * @author hezhao
     * @param str 字符串
     * @return
     */
    public static String sha384(String str) {
        return digest(str, "SHA-384");
    }

    /**
     * 对字符串进行SHA-512加密
     *
     * @author hezhao
     * @param str 字符串
     * @return
     */
    public static String sha512(String str) {
        return digest(str, "SHA-512");
    }

    //////////////////////////////////////// MD5 //////////////////////////////////////
    /**
     * 对字符串进行MD5加密
     *
     * @author hezhao
     * @param str 字符串
     * @return
     */
    public static String md5(String str) {
        return digest(str, "MD5");
    }

	/**
	 * 对字符串自行2次MD5加密MD5(MD5(s))
	 *
	 * @author hezhao
	 * @param str 字符串
	 * @return
	 */
	public static String md5x2(String str) {
		return md5(md5(str));
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
		if (md5(strOne).equals(strTwo)) {
			return true;
		} else {
			return false;
		}
	}

	/////////////////////////////////////// Hash ///////////////////////////////////////
	/**
	 * 对字符串进行MD5加密,需要提供秘钥
	 *
	 * @param str 源字符串
	 * @param key 加密密钥
	 * @return 经过编码后的字符串
	 */
	public static String hash(String str, String key) {
		String input = str + key;
		return md5(input);
	}

	/**
	 * 对字符串进行MD5加密，使用默认秘钥
	 *
	 * @param str 源字符串
	 * @return hash值
	 */
	public static String hash(String str) {
		return hash(str, default_key);
	}

	/**
	 * 使用MD5 对两端hash加密后的密文进行比较
	 *
	 * @author hezhao
	 * @Time 2018年5月11日 下午4:30:11
	 * @param strOne
	 *            未加密的字符串
	 * @param key
	 *            加密秘钥
	 * @param strTwo
	 *            已加密的字符串
	 * @return boolean
	 */
	public static boolean checkHash(String strOne, String key, String strTwo) {
		if (hash(strOne,key).equals(strTwo)) {
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) {
        System.out.println("===================md5====================");
		System.out.println(MD5Util.md5("admin"));
		System.out.println(MD5Util.md5("加密"));
		System.out.println(MD5Util.md5("20121lkkfaoisdfO$^#@!221"));
		System.out.println(MD5Util.md5("liangan0923A"));
		System.out.println(MD5Util.check("admin","21232F297A57A5A743894A0E4A801FC3"));


		System.out.println("\n==================各种加密=====================");
        System.out.println(MD5Util.md5x2("admin"));
        System.out.println(MD5Util.md2("admin"));
        System.out.println(MD5Util.sha1("admin"));
        System.out.println(MD5Util.sha256("admin"));
        System.out.println(MD5Util.sha384("admin"));
        System.out.println(MD5Util.sha512("admin"));


		System.out.println("\n==================hash=====================");
		System.out.println(hash("admin","123"));
		System.out.println(checkHash("admin","123","0192023A7BBD73250516F069DF18B500"));


		// 推荐使用 Apache加密解密工具类 -> DigestUtils
		System.out.println("\n===================DigestUtils====================");
		System.out.println(DigestUtils.md5Hex("admin"));
		System.out.println(DigestUtils.md2Hex("admin"));
		System.out.println(DigestUtils.sha1Hex("admin"));
		System.out.println(DigestUtils.sha256Hex("admin"));
		System.out.println(DigestUtils.sha384Hex("admin"));
		System.out.println(DigestUtils.sha512Hex("admin"));
	}

}
