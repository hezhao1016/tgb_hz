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
		// 私有类构造方法
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
	 * 运营商号段如下： 中国联通号码：130、131、132、145（无线上网卡）、155、156、185（iPhone5上市后开放）、186、176（4G号段）、 175（2015年9月10日正式启用，暂只对北京、上海和广东投放办理）
	 * 中国移动号码：134、135、136、137、138、139、147（无线上网卡）、150、151、152、157、158、159、182、183、187、188、178 中国电信号码：133、153、180、181、189、177、173、149 虚拟运营商：170、1718、1719 手机号前3位的数字包括： 1 :1 2 :3,4,5,7,8 3
	 * :0,1,2,3,4,5,6,7,8,9 总结： 目前java手机号码正则表达式有： a :"^1[3|4|5|7|8][0-9]\\d{4,8}$" 一般验证情况下这个就可以了 b :"^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$"
	 * Pattern和Matcher详解（字符串匹配和字节码）http://blog.csdn.net/u010700335/article/details/44616451
	 */
	public static boolean checkPhoneNo(String phoneNo) {
		String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
		boolean isMatch = false;//默认不合格
		try {
			if (phoneNo.length() != 11) {
				isMatch = false;
			} else {
				Pattern p = Pattern.compile(regex);
				Matcher m = p.matcher(phoneNo);
				isMatch = m.matches();
			}
		} catch (Exception e) {
			isMatch = false;
			e.printStackTrace();
		}
		return isMatch;
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
		
		String phone = "13123456789";
		System.out.println("---结果:"+checkPhoneNo(phone));
	}
}
