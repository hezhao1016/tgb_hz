package com.hz.tgb.common;

import java.util.Random;
import java.util.UUID;

/**
 * 随机数工具类
 * 
 * @author hezhao
 * @version v01.00.00 $Revision$
 * @date 2015年4月7日
 * @time 下午7:58:49
 */
public class RandomUtil {

	private final static String[] DIGITS = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

	private final static String[] HEXSTR_DIGITS = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	private final static String[] ALLSTR_DIGITS = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
			"t", "u", "v", "w", "x", "y", "z" };

	private RandomUtil() {
		// 私有类构造方法
	}

	/***
	 * 获取指定长度数字字符串
	 *
	 * @param len 指定长度
	 * @return
	 */
	public static String getRandomNumStr(int len) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < len; i++) {
			String temp = DIGITS[random.nextInt(DIGITS.length)];

			sb.append(temp);
		}
		return sb.toString();
	}

	/***
	 * 获取指定长度随机字符串(数字+字母)
	 *
	 * @param len 指定长度
	 * @return
	 */
	public static String getRandomNumWordStr(int len) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < len; i++) {
			String temp = ALLSTR_DIGITS[random.nextInt(ALLSTR_DIGITS.length)];

			sb.append(temp);
		}
		return sb.toString();
	}

	/**
	 * 返回指定长度的字母加数字随机数
	 *
	 * @param length
	 * @return String
	 */
	public static String getRandomChars(int length) {
		String val = "";

		Random random = new Random();
		for (int i = 0; i < length; i++) {
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字

			if ("char".equalsIgnoreCase(charOrNum)) // 字符串
			{
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
				val += (char) (choice + random.nextInt(26));
			} else if ("num".equalsIgnoreCase(charOrNum)) // 数字
			{
				val += String.valueOf(random.nextInt(10));
			}
		}

		return val;
	}

	/***
	 * 获取指定长度16进制字符串
	 *
	 * @param len 指定长度
	 * @return
	 */
	public static String getRandomHexStr(int len) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < len; i++) {
			String temp = HEXSTR_DIGITS[random.nextInt(HEXSTR_DIGITS.length)];

			sb.append(temp);
		}
		return sb.toString();
	}

	/***
	 * 根据UUID作为引子的随机数字符串
	 *
	 * @param len
	 * @return
	 */
	public static String getRandomUuidStr(int len) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random(UUID.randomUUID().hashCode());
		for (int i = 0; i < len; i++) {
			sb.append(random.nextInt(9));
		}

		return sb.toString();
	}

	/**
	 * 生产UUID
	 *
	 * @return
	 */
	public static String getUuid() {
		String uuid = UUID.randomUUID().toString();
		return uuid;
	}

	/**
	 * 生产不带横线的UUID
	 *
	 * @return
	 */
	public static String getUuidNoLine() {
		String uuid = UUID.randomUUID().toString().replace("-", "");
		return uuid;
	}


	public static void main(String[] args) {
		System.out.println(getRandomNumStr(6));
		System.out.println(getRandomNumWordStr(6));
		System.out.println(getRandomChars(6));
		System.out.println(getRandomHexStr(6));
		System.out.println(getRandomUuidStr(6));
		System.out.println(getUuid());
		System.out.println(getUuidNoLine());
	}
}
