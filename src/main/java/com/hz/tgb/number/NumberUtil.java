package com.hz.tgb.number;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数字工具类
 *
 * @author hezhao
 * @date 2015年7月21日 下午4:14:16
 * @version 1.0
 */
public class NumberUtil {

	// ------------------------------------------------------------------------------------------- round
	/**
	 * 保留固定位数小数<br>
	 * 采用四舍五入策略 {@link RoundingMode#HALF_UP}<br>
	 * 例如保留2位小数：123.456789 =》 123.46
	 *
	 * @param v 值
	 * @param scale 保留小数位数
	 * @return 新值
	 */
	public static BigDecimal round(double v, int scale) {
		return round(v, scale, RoundingMode.HALF_UP);
	}

	/**
	 * 保留固定位数小数<br>
	 * 采用四舍五入策略 {@link RoundingMode#HALF_UP}<br>
	 * 例如保留2位小数：123.456789 =》 123.46
	 *
	 * @param v 值
	 * @param scale 保留小数位数
	 * @return 新值
	 */
	public static String roundStr(double v, int scale) {
		return round(v, scale).toString();
	}

	/**
	 * 保留固定位数小数<br>
	 * 采用四舍五入策略 {@link RoundingMode#HALF_UP}<br>
	 * 例如保留2位小数：123.456789 =》 123.46
	 *
	 * @param numberStr 数字值的字符串表现形式
	 * @param scale 保留小数位数
	 * @return 新值
	 */
	public static BigDecimal round(String numberStr, int scale) {
		return round(numberStr, scale, RoundingMode.HALF_UP);
	}

	/**
	 * 保留固定位数小数<br>
	 * 采用四舍五入策略 {@link RoundingMode#HALF_UP}<br>
	 * 例如保留2位小数：123.456789 =》 123.46
	 *
	 * @param number 数字值
	 * @param scale 保留小数位数
	 * @return 新值
	 * @since 4.1.0
	 */
	public static BigDecimal round(BigDecimal number, int scale) {
		return round(number, scale, RoundingMode.HALF_UP);
	}

	/**
	 * 保留固定位数小数<br>
	 * 采用四舍五入策略 {@link RoundingMode#HALF_UP}<br>
	 * 例如保留2位小数：123.456789 =》 123.46
	 *
	 * @param numberStr 数字值的字符串表现形式
	 * @param scale 保留小数位数
	 * @return 新值
	 * @since 3.2.2
	 */
	public static String roundStr(String numberStr, int scale) {
		return round(numberStr, scale).toString();
	}

	/**
	 * 保留固定位数小数<br>
	 * 例如保留四位小数：123.456789 =》 123.4567
	 *
	 * @param v 值
	 * @param scale 保留小数位数
	 * @param roundingMode 保留小数的模式 {@link RoundingMode}
	 * @return 新值
	 */
	public static BigDecimal round(double v, int scale, RoundingMode roundingMode) {
		return round(Double.toString(v), scale, roundingMode);
	}

	/**
	 * 保留固定位数小数<br>
	 * 例如保留四位小数：123.456789 =》 123.4567
	 *
	 * @param v 值
	 * @param scale 保留小数位数
	 * @param roundingMode 保留小数的模式 {@link RoundingMode}
	 * @return 新值
	 * @since 3.2.2
	 */
	public static String roundStr(double v, int scale, RoundingMode roundingMode) {
		return round(v, scale, roundingMode).toString();
	}

	/**
	 * 保留固定位数小数<br>
	 * 例如保留四位小数：123.456789 =》 123.4567
	 *
	 * @param numberStr 数字值的字符串表现形式
	 * @param scale 保留小数位数，如果传入小于0，则默认0
	 * @param roundingMode 保留小数的模式 {@link RoundingMode}，如果传入null则默认四舍五入
	 * @return 新值
	 */
	public static BigDecimal round(String numberStr, int scale, RoundingMode roundingMode) {
		Assert.notBlank(numberStr);
		if (scale < 0) {
			scale = 0;
		}
		return round(toBigDecimal(numberStr), scale, roundingMode);
	}

	/**
	 * 保留固定位数小数<br>
	 * 例如保留四位小数：123.456789 =》 123.4567
	 *
	 * @param number 数字值
	 * @param scale 保留小数位数，如果传入小于0，则默认0
	 * @param roundingMode 保留小数的模式 {@link RoundingMode}，如果传入null则默认四舍五入
	 * @return 新值
	 */
	public static BigDecimal round(BigDecimal number, int scale, RoundingMode roundingMode) {
		if (null == number) {
			number = BigDecimal.ZERO;
		}
		if (scale < 0) {
			scale = 0;
		}
		if (null == roundingMode) {
			roundingMode = RoundingMode.HALF_UP;
		}

		return number.setScale(scale, roundingMode);
	}

	/**
	 * 保留固定位数小数<br>
	 * 例如保留四位小数：123.456789 =》 123.4567
	 *
	 * @param numberStr 数字值的字符串表现形式
	 * @param scale 保留小数位数
	 * @param roundingMode 保留小数的模式 {@link RoundingMode}
	 * @return 新值
	 * @since 3.2.2
	 */
	public static String roundStr(String numberStr, int scale, RoundingMode roundingMode) {
		return round(numberStr, scale, roundingMode).toString();
	}

	/**
	 * 数字转字符串<br>
	 * 调用{@link Number#toString()}，并去除尾小数点儿后多余的0
	 *
	 * @param number A Number
	 * @param defaultValue 如果number参数为{@code null}，返回此默认值
	 * @return A String.
	 * @since 3.0.9
	 */
	public static String toStr(Number number, String defaultValue) {
		return (null == number) ? defaultValue : toStr(number);
	}

	/**
	 * 数字转字符串<br>
	 * 调用{@link Number#toString()}，并去除尾小数点儿后多余的0
	 *
	 * @param number A Number
	 * @return A String.
	 */
	public static String toStr(Number number) {
		if (null == number) {
			throw new NullPointerException("Number is null !");
		}

		if (false == ObjectUtil.isValidIfNumber(number)) {
			throw new IllegalArgumentException("Number is non-finite!");
		}

		// 去掉小数点儿后多余的0
		String string = number.toString();
		if (string.indexOf('.') > 0 && string.indexOf('e') < 0 && string.indexOf('E') < 0) {
			while (string.endsWith("0")) {
				string = string.substring(0, string.length() - 1);
			}
			if (string.endsWith(".")) {
				string = string.substring(0, string.length() - 1);
			}
		}
		return string;
	}

	/**
	 * 数字转{@link BigDecimal}
	 *
	 * @param number 数字
	 * @return {@link BigDecimal}
	 * @since 4.0.9
	 */
	public static BigDecimal toBigDecimal(Number number) {
		if(null == number) {
			return BigDecimal.ZERO;
		}
		return toBigDecimal(number.toString());
	}

	/**
	 * 数字转{@link BigDecimal}
	 *
	 * @param number 数字
	 * @return {@link BigDecimal}
	 * @since 4.0.9
	 */
	public static BigDecimal toBigDecimal(String number) {
		return (null == number) ? BigDecimal.ZERO : new BigDecimal(number);
	}

	// ------------------------------------------------------------------------------------------- range
	/**
	 * 从0开始给定范围内的整数列表，步进为1
	 *
	 * @param stop 结束（包含）
	 * @return 整数列表
	 * @since 3.3.1
	 */
	public static int[] range(int stop) {
		return range(0, stop);
	}

	/**
	 * 给定范围内的整数列表，步进为1
	 *
	 * @param start 开始（包含）
	 * @param stop 结束（包含）
	 * @return 整数列表
	 */
	public static int[] range(int start, int stop) {
		return range(start, stop, 1);
	}

	/**
	 * 给定范围内的整数列表
	 *
	 * @param start 开始（包含）
	 * @param stop 结束（包含）
	 * @param step 步进
	 * @return 整数列表
	 */
	public static int[] range(int start, int stop, int step) {
		if (start < stop) {
			step = Math.abs(step);
		} else if (start > stop) {
			step = -Math.abs(step);
		} else {// start == end
			return new int[] { start };
		}

		int size = Math.abs((stop - start) / step) + 1;
		int[] values = new int[size];
		int index = 0;
		for (int i = start; (step > 0) ? i <= stop : i >= stop; i += step) {
			values[index] = i;
			index++;
		}
		return values;
	}

	// ------------------------------------------------------------------------------------------- others
	/**
	 * 计算阶乘
	 * <p>
	 * n! = n * (n-1) * ... * end
	 * </p>
	 *
	 * @param start 阶乘起始
	 * @param end 阶乘结束
	 * @return 结果
	 * @since 4.1.0
	 */
	public static long factorial(long start, long end) {
		if (start < end) {
			return 0L;
		}
		if (start == end) {
			return 1L;
		}
		return start * factorial(start - 1, end);
	}

	/**
	 * 计算阶乘
	 * <p>
	 * n! = n * (n-1) * ... * 2 * 1
	 * </p>
	 *
	 * @param n 阶乘起始
	 * @return 结果
	 */
	public static long factorial(long n) {
		return factorial(n, 1);
	}

	/**
	 * 平方根算法<br>
	 * 推荐使用 {@link Math#sqrt(double)}
	 *
	 * @param x 值
	 * @return 平方根
	 */
	public static long sqrt(long x) {
		long y = 0;
		long b = (~Long.MAX_VALUE) >>> 1;
		while (b > 0) {
			if (x >= y + b) {
				x -= y + b;
				y >>= 1;
				y += b;
			} else {
				y >>= 1;
			}
			b >>= 2;
		}
		return y;
	}

	/**
	 * 可以用于计算双色球、大乐透注数的方法<br>
	 * 比如大乐透35选5可以这样调用processMultiple(7,5); 就是数学中的：C75=7*6/2*1
	 *
	 * @param selectNum 选中小球个数
	 * @param minNum 最少要选中多少个小球
	 * @return 注数
	 */
	public static int processMultiple(int selectNum, int minNum) {
		int result;
		result = mathSubnode(selectNum, minNum) / mathNode(selectNum - minNum);
		return result;
	}

	/**
	 * 最大公约数
	 *
	 * @param m 第一个值
	 * @param n 第二个值
	 * @return 最大公约数
	 */
	public static int divisor(int m, int n) {
		while (m % n != 0) {
			int temp = m % n;
			m = n;
			n = temp;
		}
		return n;
	}

	/**
	 * 最小公倍数
	 *
	 * @param m 第一个值
	 * @param n 第二个值
	 * @return 最小公倍数
	 */
	public static int multiple(int m, int n) {
		return m * n / divisor(m, n);
	}

	/**
	 * 获得数字对应的二进制字符串
	 *
	 * @param number 数字
	 * @return 二进制字符串
	 */
	public static String getBinaryStr(Number number) {
		if (number instanceof Long) {
			return Long.toBinaryString((Long) number);
		} else if (number instanceof Integer) {
			return Integer.toBinaryString((Integer) number);
		} else {
			return Long.toBinaryString(number.longValue());
		}
	}

	/**
	 * 二进制转int
	 *
	 * @param binaryStr 二进制字符串
	 * @return int
	 */
	public static int binaryToInt(String binaryStr) {
		return Integer.parseInt(binaryStr, 2);
	}

	/**
	 * 二进制转long
	 *
	 * @param binaryStr 二进制字符串
	 * @return long
	 */
	public static long binaryToLong(String binaryStr) {
		return Long.parseLong(binaryStr, 2);
	}

	/**
	 * 判断一个字符串是否都为数字
	 * @param strNum
	 * @return
	 */
	public static  boolean isDigit(String strNum) {
		Pattern pattern = Pattern.compile("[0-9]{1,}");
		Matcher matcher = pattern.matcher((CharSequence) strNum);
		return matcher.matches();
	}

	/***
	 * char转换为int数字,不做ascii码转换
	 *
	 * @param c 字符
	 * @return
	 */
	public static int charToInt(char c) {
		return Integer.valueOf(String.valueOf(c));
	}

	/***
	 * 字符串转int类型
	 *
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static int parseInt(String key, int defaultValue) {
		int value = defaultValue;
		if (key != null && !"".equals(key)){
			try {
				value = Integer.parseInt(key);
			} catch (Exception localException) {
			}
		}

		return value;
	}

	/***
	 * 判断是否为连续数字或相同数字
	 *
	 * @param strings 待判定字符串
	 * @return true为是false为不是
	 */
	public static boolean isSerialNumber(String strings) {
		boolean result = true;

		// 判断是否为数字,如果不是直接返回不是连续数字
		if (!StringUtils.isNumeric(strings)) {
			return false;
		}

		// 长度只有一位返回不为连续数字
		if (strings.length() < 2) {
			return false;
		}

		// 是否为相同数字
		if (!isSameNumber(strings)) {
			// 是否为升序连续
			if (!isAscSerialNumer(strings)) {
				// 是否为降序连续
				if (!isDescSerialNumber(strings)) {
					result = false;
				}
			}
		}

		return result;
	}

	/***
	 * 检查是否为相同数字
	 *
	 * @param strings 待判定字符串
	 * @return true为是false为不是
	 */
	public static boolean isSameNumber(String strings) {
		boolean isSame = true;
		int numer;
		numer = charToInt(strings.charAt(0));
		// 遍历密保答案检查是否为相同的数字
		for (int i = 1; i < strings.length(); i++) {
			int tmp = charToInt(strings.charAt(i));
			if (numer != tmp) {
				// 若数字相同的情况下继续遍历
				isSame = false;
				break;
			}
		}

		return isSame;
	}

	/***
	 * 是否为降序数字
	 *
	 * @param strings 待判定字符串
	 * @return true为是false为不是
	 */
	public static boolean isDescSerialNumber(String strings) {
		boolean isDesc = true;
		int numer;
		numer = charToInt(strings.charAt(0));
		// 遍历密保答案检查是否为降序连续数字
		for (int i = 1; i < strings.length(); i++) {
			int tmp = charToInt(strings.charAt(i));
			if (numer <= tmp) {
				// 数字相同非降序退出循环
				isDesc = false;
				break;
			} else {
				numer--;
				if (numer == tmp) {
					// 可能为降序连续
				} else {
					isDesc = false;
					break;
				}
			}
		}

		return isDesc;
	}

	/***
	 * 判断是否为升序数字
	 *
	 * @param strings 待判定字符串
	 * @return true为是false为不是
	 */
	public static boolean isAscSerialNumer(String strings) {
		boolean isAsc = true;
		;
		int numer = charToInt(strings.charAt(0));
		// 遍历密保答案检查是否为升序连续数字
		for (int i = 1; i < strings.length(); i++) {
			int tmp = charToInt(strings.charAt(i));
			if (numer == tmp) {
				// 若相同非升序,退出
				isAsc = false;
				break;
			} else if (numer < tmp) {
				numer++;
				if (numer == tmp) {
					// 可能为升序连续
				} else {
					isAsc = false;
					break;
				}
			} else {
				isAsc = false;
				break;
			}
		}

		return isAsc;
	}

	/**
	 * 当浮点型数据位数超过10位之后，数据变成科学计数法显示。用此方法可以使其正常显示。
	 * @param value
	 * @return Sting
	 */
	public static String formatFloatNumber(double value) {
		if(value != 0.00){
			if(value < 1 && value >-1){
				return String.format("%.2f",value);
			}

			java.text.DecimalFormat df = new java.text.DecimalFormat("########.00");
			return df.format(value);
		}else{
			return "0.00";
		}

	}

	/**
	 * 当浮点型数据位数超过10位之后，数据变成科学计数法显示。用此方法可以使其正常显示。
	 * @param value
	 * @return Sting
	 */
	public static String formatFloatNumber(Double value) {
		if(value != null){
			if(value.doubleValue() != 0.00){
				if(value.doubleValue() < 1 && value.doubleValue() >-1){
					return String.format("%.2f",value);
				}

				java.text.DecimalFormat df = new java.text.DecimalFormat("########.00");
				return df.format(value.doubleValue());
			}else{
				return "0.00";
			}
		}
		return "";
	}

	/**
	 * 当浮点型数据位数超过10位之后，数据变成科学计数法显示。用此方法可以使其正常显示。
	 * @param valuestr
	 * @return Sting
	 */
	public static String formatFloatNumber(String valuestr) {
		Double value = Double.valueOf(valuestr);

		if(value != null){

			if(value.doubleValue() < 1 && value.doubleValue() >-1){
				return String.format("%.2f",value);
			}

			if(value.doubleValue() != 0.00){
				java.text.DecimalFormat df = new java.text.DecimalFormat("########.00");
				return df.format(value.doubleValue());
			}else{
				return "0.00";
			}
		}
		return "";
	}

	// ------------------------------------------------------------------------------------------- Private method start
	private static int mathSubnode(int selectNum, int minNum) {
		if (selectNum == minNum) {
			return 1;
		} else {
			return selectNum * mathSubnode(selectNum - 1, minNum);
		}
	}

	private static int mathNode(int selectNum) {
		if (selectNum == 0) {
			return 1;
		} else {
			return selectNum * mathNode(selectNum - 1);
		}
	}
	// ------------------------------------------------------------------------------------------- Private method end

	public static void main(String[] args) {
		BigDecimal round = round(324.2513, 2);
		System.out.println(round);

		int[] range = range(1, 10, 2);
		System.out.println(ArrayUtils.toString(range));

		String binaryStr = getBinaryStr(10);
		System.out.println(binaryStr);

		long factorial = factorial(5);
		System.out.println(factorial);

		double d = 465215654849894890346478.506756354;
		System.out.println(d);
		System.out.println(NumberUtil.formatFloatNumber(d));
	}

}