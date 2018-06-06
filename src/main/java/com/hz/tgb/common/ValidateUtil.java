package com.hz.tgb.common;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 公用参数校验工具类。
 * 
 * @Author hezhao
 * @Date 2015年3月25日
 */
public class ValidateUtil {

	private static final Pattern pcharanddigit = Pattern.compile("[0-9a-zA-Z]+");
	private static final Pattern pdatey_m_d = Pattern.compile("\\d{4}\\-\\d{2}\\-\\d{2}");
	private static final Pattern pip4 = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");// ip地址匹配192.168.1.3
	private static final Pattern pdomain = Pattern.compile("[a-zA-Z0-9-\\u4e00-\\u9fa5]+(.[a-zA-Z0-9-]+)+");
	private static final Pattern pinteger = Pattern.compile("[+-]?\\d+");
	private static final Pattern purl = Pattern.compile("(http|https):\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?");
	private static final Pattern pserialno = Pattern.compile("[a-z0-9-_]+");
	private static final Pattern pdateymd = Pattern.compile("\\d{8}");
	private static final String DOMAINS = ".com.cn|.net.cn|.org.cn|.gov.cn|.com|.net|.tv|.gd|.org|.cc|.vc|.mobi|.cd|.info|.name|.asia|.hk|.me|.la|.sh|.biz|.li|.kr|.in|.us|.io|.ac.cn|.bj.cn|.sh.cn|.tj.cn|.cq.cn|.he.cn|.sx.cn|.nm.cn|.ln.cn|.jl.cn|.hl.cn|.js.cn|.zj.cn|.ah.cn|.fj.cn|.jx.cn|.sd.cn|.ha.cn|.hb.cn|.hn.cn|.gd.cn|.gx.cn|.hi.cn|.sc.cn|.gz.cn|.yn.cn|.xz.cn|.sn.cn|.gs.cn|.qh.cn|.nx.cn|.xj.cn|.tw.cn|.hk.cn|.mo.cn|.cn";

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
		if (isEmptyString(value)) {
			return false;
		}
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
		if (isEmptyString(value)) {
			return false;
		}
		Pattern pattern = Pattern.compile("^-?[0-9]*.?[0-9]+$");
		Matcher m = pattern.matcher(value);
		return m.matches();
	}

	/**
	 * 是否整数,可以带正负号
	 *
	 * @param value 目标字符串
	 * @return 是否整数
	 */
	public static boolean isInteger(String value) {
		if (isEmptyString(value)) {
			return false;
		}
		Matcher m = pinteger.matcher(value);
		return m.matches();
	}

	/**
	 * 校验正整数,0-9
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isPoInteger(String value) {
		if (isEmptyString(value)) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[0-9]+$");
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
		if (isEmptyString(value)) {
			return false;
		}
		Pattern pattern = Pattern.compile("^((\\d{3,4})\\-(\\d{8})|(\\d{4})\\-(\\d{7}))$");
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
		if (isEmptyString(value)) {
			return false;
		}
		Pattern pattern = Pattern.compile("^1(4[0-9]|3[0-9]|5[0-9]|8[0-9]|7[0-9])\\d{8}$");
		Matcher m = pattern.matcher(value);
		return m.matches();
	}

	/**
	 * 运营商号段如下： 中国联通号码：130、131、132、145（无线上网卡）、155、156、185（iPhone5上市后开放）、186、176（4G号段）、 175（2015年9月10日正式启用，暂只对北京、上海和广东投放办理）
	 * 中国移动号码：134、135、136、137、138、139、147（无线上网卡）、150、151、152、157、158、159、182、183、187、188、178 中国电信号码：133、153、180、181、189、177、173、149 虚拟运营商：170、1718、1719 手机号前3位的数字包括： 1 :1 2 :3,4,5,7,8 3
	 * :0,1,2,3,4,5,6,7,8,9 总结： 目前java手机号码正则表达式有： a :"^1[3|4|5|7|8][0-9]\\d{4,8}$" 一般验证情况下这个就可以了 b :"^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$"
	 * Pattern和Matcher详解（字符串匹配和字节码）http://blog.csdn.net/u010700335/article/details/44616451
	 */
	public static boolean checkMobileNo(String mobileNo) {
		if (isEmptyString(mobileNo)) {
			return false;
		}
		String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
		boolean isMatch = false;//默认不合格
		try {
			if (mobileNo.length() != 11) {
				isMatch = false;
			} else {
				Pattern p = Pattern.compile(regex);
				Matcher m = p.matcher(mobileNo);
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
		if (isEmptyString(value)) {
			return false;
		}
		Pattern pattern = Pattern.compile("^([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$");
		Matcher m = pattern.matcher(value);
		return m.matches();
	}

	/**
	 * 是否是url地址
	 *
	 * @param str http://www.oppo.com url地址
	 * @return 是否是url地址
	 */
	public static boolean checkURL(String str) {
		if (isEmptyString(str)) {
			return false;
		}
		Matcher m = purl.matcher(str);
		return m.matches();
	}

	/**
	 * 是否是主域名如 baidu.com
	 *
	 * @param str 目标字符串
	 * @return 是否是主域名
	 */
	public static boolean checkMainDomain(String str) {
		if (isEmptyString(str)) {
			return false;
		}
		String[] domains = DOMAINS.split("\\|");
		for (String s : domains) {
			if (null != s && !s.trim().equals("") && str.indexOf(s) > 0) {
				str = str.replace(s, "");
				if (checkSerialNo(str)) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	public static boolean checkDomain(String s) {
		if (isEmptyString(s)) {
			return false;
		}
		Matcher m = pdomain.matcher(s);
		return m.matches();
	}

	public static boolean checkNotDomain(String s) {
		return !checkDomain(s);
	}

	/**
	 * 是不是IP地址
	 *
	 * @param s 目标字符串
	 * @return 是不是IP地址
	 */
	public static boolean checkIP(String s) {
		if (isEmptyString(s)) {
			return false;
		}
		Matcher m = pip4.matcher(s);
		return m.matches();
	}

	/**
	 * 校验编号, 只能是数字,字母, 下划线, 减号
	 *
	 * @param str 目标字符串 例如：http://www.oppo.com
	 * @return 是否是编号
	 */
	public static boolean checkSerialNo(String str) {
		if (isEmptyString(str)) {
			return false;
		}
		str = str.toLowerCase().trim();

		Matcher isSerialNo = pserialno.matcher(str);
		return isSerialNo.matches();
	}

	/**
	 * 是否含有非法字符
	 *
	 * @param str 目标字符串
	 * @return 是否含有非法字符
	 */
	public static boolean checkForbid(String str) {
		if (isEmptyString(str)) {
			return false;
		}
		str = str.toLowerCase().trim();
		String[] forbids = { "%", "\\", "'", "*" };
		for (String s : forbids) {
			if (str.indexOf(s) > -1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 校验密码
	 * 
	 * @author yuanchangjian
	 * @return
	 */
	public static boolean checkPassWord(String value) {
		if (isEmptyString(value)) {
			return false;
		}
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
		if (isEmptyString(value)) {
			return false;
		}
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
		if (isEmptyString(value)) {
			return false;
		}
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
	 * 字符串不为空
	 * @param s
	 * @return
	 */
	public static boolean isNotEmptyString(String s) {
		return !isEmptyString(s);
	}

	/**
	 * 判断字符串是不是都为空
	 *
	 * @param str
	 * @return 只要有不空的字符串就返回false
	 */
	public static boolean isAllEmptyString(String... str) {
		if (str == null) {
			return true;
		}
		for (String s : str) {
			if (isNotEmptyString(s)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断是不是所有字符串都不为空
	 *
	 * @param str 字符串
	 * @return 只要有为空的就返回false
	 */
	public static boolean isAllNotEmptyString(String... str) {
		if (str == null) {
			return false;
		}
		for (String s : str) {
			if (isEmptyString(s)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 移除空行、空格
	 *
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		if (StringUtils.isBlank(str)) {
			return str;
		}
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(str);
		return m.replaceAll("");
	}

	/**
	 * 通用长度为1-50验证
	 * 
	 * @param value
	 *            校验值
	 * @return 布尔
	 */
	public static Boolean checkCommon(String value) {
		if (isEmptyString(value)) {
			return false;
		}
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
		if (isEmptyString(value)) {
			return false;
		}
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
		if (isEmptyString(value)) {
			return false;
		}
		Pattern pattern = Pattern.compile("\\S{1,255}");
		Matcher m = pattern.matcher(value);
		return m.matches();
	}

	/**
	 * 判断某个字符串长度是不是在一个范围内（包含边界值）。
	 *
	 * @param str 字符串
	 * @param minLength 最小长度
	 * @param maxLength 最大长度
	 * @return
	 */
	public static boolean checkStringBetween(String str, int minLength, int maxLength) {
		if (str == null) {
			return false;
		}
		if (str.length() >= minLength && str.length() <= maxLength) {
			return true;
		}
		return false;
	}

	/**
	 * 验证经度
	 * 
	 * @param value
	 *            校验值
	 * @return 布尔
	 */
	public static boolean checkLongitude(String value) {
		if (isEmptyString(value)) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[\\-\\+]?(0?\\d{1,2}\\.\\d{1,5}|1[0-7]?\\d{1}\\.\\d{1,12}|180\\.0{1,12})$");
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
		if (isEmptyString(value)) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[\\-\\+]?([0-8]?\\d{1}\\.\\d{1,12}|90\\.0{1,12})$");
		Matcher m = pattern.matcher(value);
		return m.matches();
	}

	/**
	 * 判断日期字符串是否是yyyyMMdd的格式
	 *
	 * @param s 日期字符串
	 * @return 格式是否符合
	 */
	public static boolean checkDateYMD(String s) {
		if (isEmptyString(s)) {
			return false;
		}

		Matcher m = pdateymd.matcher(s);
		return m.matches();
	}

	/**
	 * 判断日期字符串是否是yyyy-MM-dd的格式
	 *
	 * @param s 日期字符串
	 * @return 格式是否符合
	 */
	public static boolean checkDateY_M_D(String s) {
		if (isEmptyString(s)) {
			return false;
		}
		Matcher m = pdatey_m_d.matcher(s);
		return m.matches();
	}

	/**
	 * 校验 0-9a-zA-Z
	 * @param s
	 * @return
	 */
	public static boolean checkCharAndDigit(String s) {
		if (isEmptyString(s)) {
			return false;
		}
		Matcher m = pcharanddigit.matcher(s);
		return m.matches();
	}


	public static void main(String[] args) {
		System.out.println(isNumber("-122.22"));
		System.out.println(isPoInteger("122"));
		System.out.println(isInteger("-122"));

		String phone = "13123456789";
		System.out.println("---结果:"+checkMobileNo(phone));
		System.out.println("---结果:"+checkMobile(phone));
		System.out.println("---结果:"+checkPhone("0755-12345678"));
	}
}
