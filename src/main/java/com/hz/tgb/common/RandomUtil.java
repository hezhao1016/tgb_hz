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

	private RandomUtil() {
		// 私用构造主法.因为此类是工具类.
	}

	/**
	 * 获取指定位数随机数
	 * 
	 * @author hezhao
	 * @param length
	 *
	 * @return string
	 */
	public static String getRandomNumStr(int length) {
		Random random = new Random();
		String result = "";
		for (int i = 0; i < length; i++) {
			result += random.nextInt(10);
		}
		return result;
	}

	/**
	 * 获取length长度的随机数字字符串。
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomCode(final int length) {
		final char[] numbers = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
				'9' };
		final int numbersLength = numbers.length;
		final StringBuffer authCode = new StringBuffer();
		final Random random = new Random();

		for (int i = 0; i < length; i++) {
			authCode.append(numbers[random.nextInt(numbersLength)]);
		}

		return authCode.toString();
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

	private static final String[] chars = new String[] { "0", "1", "2", "3",
			"4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g",
			"h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
			"u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G",
			"H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
			"U", "V", "W", "X", "Y", "Z", "~", "!", "@", "#", "$", "%", "^",
			"&", "*", "(", ")", "-", "=", "_", "+", "[", "]", "{", "}", "\\",
			"|", ";", ":", "\"", "<", ">", "/", "?" };

	/**
	 * 返回指定长度的字符，字符取决于本类中的chars数组元素
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomAnyChars(int length) {
		Random random = new Random();
		String rand = "";
		for (int i = 0; i < length; i++) {
			rand += chars[random.nextInt(chars.length)];
		}
		return rand;
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
		System.out.println(getRandomCode(6));
		System.out.println(getRandomChars(6));
		System.out.println(getRandomAnyChars(6));
		System.out.println(getUuid());
		System.out.println(getUuidNoLine());
	}
}
