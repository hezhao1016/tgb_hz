package com.hz.tgb.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 公用参数校验工具类。
 * 
 * @Author hezhao
 * @Date 2015年3月25日
 */
public class ValidateUtil {

	private ValidateUtil() {
		// 私用构造主法.因为此类是工具类.
	}

	/**
	 * 校验字符串是否是英文字母，不分大小写
	 * 
	 * @author yuanchangjian
	 * @return
	 */
	public static boolean isEnglish(String value) {
		Pattern pattern = Pattern.compile("^[A-Za-z]+$");
		Matcher m = pattern.matcher(value);
		return m.matches();
	}

	/**
	 * 校验数字,包括小数和负数
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNumber(String value) {
		Pattern pattern = Pattern.compile("^-?[0-9]*.?[0-9]*$");
		Matcher m = pattern.matcher(value);
		return m.matches();
	}

	/**
	 * 校验整数,0-9
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNumeric(String value) {
		Pattern pattern = Pattern.compile("^[0-9]*$");
		Matcher m = pattern.matcher(value);
		return m.matches();
	}

	/**
	 * 校验电话号码（座机）
	 * 
	 * @author yuanchangjian
	 * @return
	 */
	public static boolean checkPhone(String value) {
		Pattern pattern = Pattern
				.compile("^((\\d{3,4})\\-(\\d{8})|(\\d{4})\\-(\\d{7}))$");
		Matcher m = pattern.matcher(value);
		return m.matches();

	}

	/**
	 * 校验电话号码（手机）
	 * 
	 * @author yuanchangjian
	 * @return
	 */
	public static boolean checkMobile(String value) {
		Pattern pattern = Pattern
				.compile("^1(4[0-9]|3[0-9]|5[0-9]|8[0-9]|7[0-9])\\d{8}$");
		Matcher m = pattern.matcher(value);
		return m.matches();
	}

	/**
	 * 校验电子邮箱
	 * 
	 * @author yuanchangjian
	 * @return
	 */
	public static boolean checkEmail(String value) {
		Pattern pattern = Pattern
				.compile("^([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$");
		Matcher m = pattern.matcher(value);
		return m.matches();
	}

	/**
	 * 校验密码
	 * 
	 * @author yuanchangjian
	 * @return
	 */
	public static boolean checkPassWord(String value) {
		Pattern pattern = Pattern.compile("^[a-zA-Z0-9_-]{6,20}$");
		Matcher m = pattern.matcher(value);
		return m.matches();
	}

	/**
	 * 验证用户名
	 * 
	 * @param value
	 *            用户名
	 * @return 布尔
	 */
	public static boolean checkUserName(String value) {
		Pattern pattern = Pattern.compile("^[a-zA-Z0-9_-]{6,12}$");
		Matcher m = pattern.matcher(value);
		return m.matches();
	}

	/**
	 * 验证姓名
	 * 
	 * @param value
	 *            姓名
	 * @return 布尔
	 */
	public static boolean checkPersonName(String value) {
		Pattern pattern = Pattern.compile("^[\u4e00-\u9fa5]{2,4}$");
		Matcher m = pattern.matcher(value);
		return m.matches();
	}

	/**
	 * 获取字符长度
	 * 
	 * @param value
	 * @return 长度
	 */
	public static int getLength(String value) {
		return value.length();
	}

	/**
	 * 判断字符串是否为空或者空字符串。
	 * 
	 * @author weiguobin
	 * @param value
	 * @return 如果为空或者空字符串，返回true；否则返回false。
	 */
	public static boolean isEmptyString(String value) {
		return (value == null || "".equals(value.trim())) ? true : false;
	}

	/**
	 * 通用长度为1-50验证
	 * 
	 * @param value
	 *            校验值
	 * @return 布尔
	 */
	public static Boolean checkCommon(String value) {
		Pattern pattern = Pattern.compile("\\S{1,50}");
		Matcher m = pattern.matcher(value);
		return m.matches();
	}

	/**
	 * 验证地址
	 * 
	 * @param value
	 *            校验值
	 * @return 布尔
	 */
	public static Boolean checkAddress(String value) {
		Pattern pattern = Pattern.compile("\\S{1,100}");
		Matcher m = pattern.matcher(value);
		return m.matches();
	}

	/**
	 * 通用长度为1-255验证
	 * 
	 * @param value
	 *            校验值
	 * @return 布尔
	 */
	public static Boolean checkConstant(String value) {
		Pattern pattern = Pattern.compile("\\S{1,255}");
		Matcher m = pattern.matcher(value);
		return m.matches();
	}

	/**
	 * 验证经度
	 * 
	 * @param value
	 *            校验值
	 * @return 布尔
	 */
	public static boolean checkLongitude(String value) {
		Pattern pattern = Pattern
				.compile("^[\\-\\+]?(0?\\d{1,2}\\.\\d{1,5}|1[0-7]?\\d{1}\\.\\d{1,12}|180\\.0{1,12})$");
		Matcher m = pattern.matcher(value);
		return m.matches();
	}

	/**
	 * 验证纬度
	 * 
	 * @param value
	 *            校验值
	 * @return 布尔
	 */
	public static boolean checkLatitude(String value) {
		Pattern pattern = Pattern
				.compile("^[\\-\\+]?([0-8]?\\d{1}\\.\\d{1,12}|90\\.0{1,12})$");
		Matcher m = pattern.matcher(value);
		return m.matches();
	}

	public static void main(String[] args) {
		System.out.println(isNumber("-122.22"));
	}
}
