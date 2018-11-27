package com.hz.tgb.datetime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期、时间处理工具类
 *
 * @author hezhao
 * @Time   2017年7月31日 上午11:50:45
 *
 */
public class DateUtil {

	public static void main(String[] args) {

		Date now = new Date();
		int month = getIntMonthByDate(now);
		System.out.println(month);

		System.out.println(format(now, "yyyyMMdd"));
		System.out.println(format(now, "yyyy-MM-dd"));
		System.out.println(format(now, DateEnums.DateStyle.yyyyMMddHHmmss));
		System.out.println(format(now, "yyyy-MM-dd HH:mm:ss"));
		System.out.println(format(now, "yyyy/MM/dd E HH:mm:ss:S"));
		System.out.println(format(now, "yyMMddHHmmss"));
		System.out.println("=========================1===========================");

		String format = format(now, "yyyy-MM-dd HH:mm:ss");
		System.out.println(parse(format, "yyyy-MM-dd HH:mm:ss"));

		System.out.println(getCurrentTimeMillis());
		System.out.println(getNanoTime());

		System.out.println(formatDateTime(getNow()));

		System.out.println(format(parse(formatDateTime(getNow())), "yyyy-MM-dd HH:mm:ss"));
		System.out.println(format(parse("2018-10-10 22:22:22:333"), "yyyy-MM-dd HH:mm:ss:S"));
		System.out.println(format(parse("2017年07月27日 星期四"), "yyyy年MM月dd日 E"));
		System.out.println(format(parse("20170727133253356"), "yyyyMMddHHmmssS"));

		System.out.println(getDateStyle("06:45").getValue());
		System.out.println(addYear("2017", 5));

		System.out.println(daysBetween(getNow(), parse("2017-06-22")));
		System.out.println(daysBetween("2017-01-12", "2017-08-22"));
		System.out.println("===========================2=========================");

		Date addDay = addDay(getNow(), -39);
		System.out.println(formatDate(addDay));

		System.out.println(check("2017-12-12"));
		System.out.println(check("12-12"));
		System.out.println("==========================3==========================");

		System.out.println("First day of week is : "
				+ formatDateTime(getFirstDateByWeek(new Date())));
		System.out.println("Last day of week is : "
				+ formatDateTime(getLastDateByWeek(new Date())));
		System.out.println("First day of week by sunday is : "
				+ formatDateTime(getFirstDateByWeekBySunDay(new Date())));
		System.out.println("Last day of week by sunday is : "
				+ formatDateTime(getLastDateByWeekBySunDay(new Date())));
		System.out.println("===========================4=========================");

		System.out.println("First day of month is : "
				+ formatDateTime(getFirstDateByMonth(new Date())));
		System.out.println("First day of month is : " + getFirstDateByMonth());
		System.out.println("First day of month is : "
				+ getFirstDateByMonth(2017, 8));

		System.out.println("Last day of month is : "
				+ formatDateTime(getLastDateByMonth(new Date())));
		System.out.println("Last day of month is : " + getLastDateByMonth());
		System.out.println("Last day of month is : "
				+ getLastDateByMonth(2017, 8));
		System.out.println("==========================5==========================");

		System.out.println("First day of year is : "
				+ formatDateTime(getFirstDateByYear()));
		System.out.println("First day of year is : "
				+ formatDateTime(getFirstDateByYear(2017)));
		System.out.println("First day of year is : "
				+ formatDateTime(getFirstDateByYear(getNow())));

		System.out.println("Last day of year is : "
				+ formatDateTime(getLastDateByYear(getNow())));
		System.out.println("Last day of year is : "
				+ formatDateTime(getLastDateByYear()));
		System.out.println("Last day of year is : "
				+ formatDateTime(getLastDateByYear(2017)));
		System.out.println("==========================6==========================");

		try {
			System.out.println("calculateTime is : " + calculateTime(new Date(),"D",-1));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		System.out.println(changeFormat("20181009", "yyyy-MM-dd"));
		System.out.println(changeFormat("2018年10月09日", DateEnums.DateStyle.yyyy_MM_dd.getValue()));
		System.out.println(changeFormat("2018-10-09", DateEnums.DateStyle.yyyyMMdd.getValue()));

		long time = DateUtil.dateDiffer("2018-09-18 23:45:11", "2018-09-19 02:45:12", DateEnums.DateStyle.yyyy_MM_dd_HH_mm_ss.getValue());
		double hour = time / 1000.0 / 60 / 60;
		System.out.println(hour);
	}

	private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

	/**
	 * 缺省的日期格式 yyyy-MM-dd
	 */
	private static final String DAFAULT_DATE_FORMAT = "yyyy-MM-dd";

	/**
	 * 默认日期类型格试.
	 *
	 * @see DAFAULT_DATE_FORMAT
	 */
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			DAFAULT_DATE_FORMAT);

	/**
	 * 缺省的日期时间格式 yyyy-MM-dd HH:mm:ss
	 */
	private static final String DAFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private static final SimpleDateFormat datetimeFormat = new SimpleDateFormat(
			DAFAULT_DATETIME_FORMAT);

	/**
	 * 缺省的时间格式 HH:mm:ss
	 */
	private static final String DAFAULT_TIME_FORMAT = "HH:mm:ss";

	private static final SimpleDateFormat timeFormat = new SimpleDateFormat(
			DAFAULT_TIME_FORMAT);

	private DateUtil() {
		// 私有类构造方法
	}

	private static boolean isEmpty(String str) {
		if (str == null || str.trim().length() == 0)
			return true;
		return false;
	}

	private static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 获取某个时间点前N天的时间点。
	 *
	 * @param currentDate
	 * @param days
	 * @return
	 */
	public static Date getDateBefore(Date currentDate, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		cal.add(Calendar.DAY_OF_YEAR, -days);

		return cal.getTime();
	}

	/**
	 * 获取某个时间点后N天的时间点。
	 *
	 * @param currentDate
	 * @param days
	 * @return
	 */
	public static Date getDateAfter(Date currentDate, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		cal.add(Calendar.DAY_OF_YEAR, days);

		return cal.getTime();
	}

	/**
	 * 根据生日计算年龄。
	 *
	 * @param birthday
	 * @return int
	 */
	public static int getAgeByBirthDay(Date birthday) {
		Calendar cal = Calendar.getInstance();

		if (cal.before(birthday)) {
			return 0;
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

		cal.setTime(birthday);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				// monthNow>monthBirth
				age--;
			}
		}
		return age;
	}

	/**
	 * 检查字符串是否是日期
	 *
	 * @param dateString
	 *            字符串
	 * @return 任意格式[DateStyle] 只要有一个转换成功就返回true，否则返回false
	 */
	public static boolean check(String dateString) {
		if (isEmpty(dateString))
			return false;

		for (DateEnums.DateStyle style : DateEnums.DateStyle.values()) {
			Date date = null;
			SimpleDateFormat sdf = new SimpleDateFormat(style.getValue());
			try {
				date = (Date) sdf.parse(dateString);
				if (date != null && dateString.equals(sdf.format(date))) {
					return true;
				}
			} catch (ParseException e) {

			}
		}
		return false;
	}

	/**
	 * 返回两个时间之间的差(毫秒数)
	 *
	 * @param time1 日期一
	 * @param time2 日期二
	 * @return 毫秒数[绝对值]
	 */
	public static long dateDiffer(Date time1, Date time2) {
		if (time1 == null || time2 == null) {
			return 0;
		}

		long differ = Math.abs(time1.getTime() - time2.getTime());
		return differ;
	}

	/**
	 * 返回两个时间之间的差(毫秒数)
	 *
	 * @param time1 日期一
	 * @param time2 日期二
	 * @param formatStr 格式字符串
	 * @return 毫秒数[绝对值]
	 */
	public static long dateDiffer(String time1, String time2, String formatStr) {
		if (isEmpty(time1) || isEmpty(time2)) {
			return 0L;
		}

		Date date1 = parse(time1, formatStr);
		Date date2 = parse(time2, formatStr);
		return dateDiffer(date1, date2);
	}

	/**
	 * 返回两个时间之间的差(毫秒数)
	 *
	 * @param time1 日期一
	 * @param time2 日期二
	 * @return 毫秒数[绝对值]
	 */
	public static long dateDiffer(String time1, String time2) {
		return dateDiffer(time1, time2, "");
	}

	/**
	 * 计算日期差
	 *
	 * @author hezhao
	 * @Time 2016年8月26日 上午9:48:50
	 * @param time1
	 *            日期一
	 * @param time2
	 *            日期二
	 * @return 日期差[绝对值]
	 */
	public static int daysBetween(Date time1, Date time2) {
		if (time1 == null || time2 == null) {
			return 0;
		}

		Calendar cDate1 = Calendar.getInstance();
		Calendar cDate2 = Calendar.getInstance();
		cDate1.setTime(time1);
		cDate2.setTime(time2);
		setTimeToMidnight(cDate1);
		setTimeToMidnight(cDate2);
		long todayMs = cDate1.getTimeInMillis();
		long returnMs = cDate2.getTimeInMillis();
		long intervalMs = returnMs - todayMs;
		return Math.abs(millisecondsToDays(intervalMs));
	}

	/**
	 * 计算日期差
	 *
	 * @author hezhao
	 * @Time 2016年8月26日 上午9:48:50
	 * @param time1
	 *            日期一
	 * @param time2
	 *            日期二
	 * @param formatStr
	 *            格式字符串
	 * @return 日期差[绝对值]
	 */
	public static int daysBetween(String time1, String time2, String formatStr) {
		if (isEmpty(time1) || isEmpty(time2)) {
			return 0;
		}

		Date date1 = parse(time1, formatStr);
		Date date2 = parse(time2, formatStr);
		return daysBetween(date1, date2);
	}

	/**
	 * 计算日期差
	 *
	 * @author hezhao
	 * @Time 2016年8月26日 上午9:48:50
	 * @param time1
	 *            日期一
	 * @param time2
	 *            日期二
	 * @return 日期差[绝对值]
	 */
	public static int daysBetween(String time1, String time2) {
		return daysBetween(time1, time2, "");
	}

	private static int millisecondsToDays(long intervalMs) {
		return (int) (intervalMs / (1000 * 86400));
	}

	private static void setTimeToMidnight(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
	}

	/**
	 * 计算月份差
	 *
	 * @author hezhao
	 * @Time 2018年02月24日 上午9:48:50
	 * @param time1
	 *            日期一
	 * @param time2
	 *            日期二
	 * @return 月份差[绝对值]
	 */
	public static int monthsBetween(Date time1, Date time2) {
		if (time1 == null || time2 == null) {
			return 0;
		}

		Calendar bef = Calendar.getInstance();
		Calendar aft = Calendar.getInstance();
		bef.setTime(time1);
		aft.setTime(time2);
		int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
		int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
		return Math.abs(month + result);
	}

	/**
	 * 计算月份差
	 *
	 * @author hezhao
	 * @Time 2018年02月24日 上午9:48:50
	 * @param time1
	 *            日期一
	 * @param time2
	 *            日期二
	 * @param formatStr
	 *            格式字符串
	 * @return 月份差[绝对值]
	 */
	public static int monthsBetween(String time1, String time2, String formatStr) {
		if (isEmpty(time1) || isEmpty(time2)) {
			return 0;
		}

		Date date1 = parse(time1, formatStr);
		Date date2 = parse(time2, formatStr);
		return monthsBetween(date1, date2);
	}

	/**
	 * 计算月份差
	 *
	 * @author hezhao
	 * @Time 2018年02月24日 上午9:48:50
	 * @param time1
	 *            日期一
	 * @param time2
	 *            日期二
	 * @return 月份差[绝对值]
	 */
	public static int monthsBetween(String time1, String time2) {
		return monthsBetween(time1, time2, "");
	}

	/**
	 * 时间戳转化为时间格式
	 *
	 * @param timeStamp
	 *            时间戳
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String timeStampToDateTime(long timeStamp) {
		String date = datetimeFormat.format(timeStamp);
		return date;
	}

	/**
	 * 时间戳转化为时间格式
	 *
	 * @param timeStamp
	 *            时间戳
	 * @return yyyy-MM-dd
	 */
	public static String timeStampToDate(long timeStamp) {
		String date = dateFormat.format(timeStamp * 1000);
		return date;
	}

	/**
	 * 时间戳转化为时间格式
	 *
	 * @param timeStamp
	 *            时间戳
	 * @return HH:mm:ss
	 */
	public static String timeStampToTime(long timeStamp) {
		String time = null;
		String date = timeFormat.format(timeStamp * 1000);
		String[] split = date.split("\\s");
		if (split.length > 1) {
			time = split[1];
		}
		return time;
	}

	/**
	 * 将一个时间戳转换成提示性时间字符串，如刚刚，1秒前
	 *
	 * @param timeStamp
	 * @return
	 */
	public static String convertTimeToFormat(long timeStamp) {
		long curTime = System.currentTimeMillis() / (long) 1000;
		long time = curTime - timeStamp;

		if (time < 60 && time >= 0) {
			return "刚刚";
		} else if (time >= 60 && time < 3600) {
			return time / 60 + "分钟前";
		} else if (time >= 3600 && time < 3600 * 24) {
			return time / 3600 + "小时前";
		} else if (time >= 3600 * 24 && time < 3600 * 24 * 30) {
			return time / 3600 / 24 + "天前";
		} else if (time >= 3600 * 24 * 30 && time < 3600 * 24 * 30 * 12) {
			return time / 3600 / 24 / 30 + "个月前";
		} else if (time >= 3600 * 24 * 30 * 12) {
			return time / 3600 / 24 / 30 / 12 + "年前";
		} else {
			return "刚刚";
		}
	}

	/**
	 * 将一个时间戳转换成提示性时间字符串，(多少分钟)
	 *
	 * @param timeStamp
	 * @return
	 */
	public static String timeStampToFormat(long timeStamp) {
		long curTime = System.currentTimeMillis() / (long) 1000;
		long time = curTime - timeStamp;
		return time / 60 + "";
	}

	/**
	 * 日期/时间 计算
	 *
	 * @param time 待计算时间
	 * @param addpart 可选 Y M D H F S
	 * @param num 增加或者减少量(整数)
	 * @return yyyy-MM-dd HH:mm:ss
	 * @throws Exception
	 */
	public static String calculateTime(String time, String addpart, int num) throws ParseException {
		return calculateTime(parseTime(time,DAFAULT_DATETIME_FORMAT),addpart,num);
	}

	/**
	 * 日期/时间 计算
	 *
	 * @param time 待计算时间
	 * @param addpart 可选 Y M D H F S
	 * @param num 增加或者减少量(整数)
	 * @return yyyy-MM-dd HH:mm:ss
	 * @throws Exception
	 */
	public static String calculateTime(Date time, String addpart, int num) throws ParseException {
		DateFormat yyyyMMddHHmmssDateFormat = new SimpleDateFormat(DAFAULT_DATETIME_FORMAT);
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(time);
		if (addpart.equalsIgnoreCase("Y")) {
			cal.add(Calendar.YEAR, num);
		} else if (addpart.equalsIgnoreCase("M")) {
			cal.add(Calendar.MONTH, num);
		} else if (addpart.equalsIgnoreCase("D")) {
			cal.add(Calendar.DATE, num);
		} else if (addpart.equalsIgnoreCase("H")) {
			cal.add(Calendar.HOUR_OF_DAY, num);
		} else if (addpart.equalsIgnoreCase("F")) {
			cal.add(Calendar.MINUTE, num);
		} else if (addpart.equalsIgnoreCase("S")) {
			cal.add(Calendar.SECOND, num);
		}
		return yyyyMMddHHmmssDateFormat.format(cal.getTime());
	}

	/**
	 * 格式化Calendar
	 *
	 * @param calendar
	 * @return
	 */
	public static String formatCalendar(Calendar calendar) {
		if (calendar == null) {
			return "";
		}
		return dateFormat.format(calendar.getTime());
	}

	/**
	 * 格式化日期时间，默认日期格式yyyy-MM-dd HH:mm:ss
	 *
	 * @author hezhao
	 * @Time 2017年7月27日 下午7:12:17
	 * @Description 无
	 * @Version V 1.0
	 * @param datetime
	 * @return
	 */
	public static String formatDateTime(Date datetime) {
		if (datetime == null) {
			return "";
		}
		return datetimeFormat.format(datetime);
	}

	/**
	 * 日期格式化，默认日期格式yyyy-MM-dd
	 *
	 * @author hezhao
	 * @Time 2017年7月27日 下午7:08:49
	 * @Description 无
	 * @Version V 1.0
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		if (date == null) {
			return "";
		}
		return dateFormat.format(date);
	}

	/**
	 * 格式化时间，默认日期格式HH:mm:ss
	 *
	 * @param time
	 * @return
	 */
	public static String formatTime(Date time) {
		if (time == null) {
			return "";
		}
		return timeFormat.format(time);
	}

	/**
	 * 格式化整数型日期
	 *
	 * @param intDate
	 * @return
	 */
	public static String formatIntDate(Integer intDate) {
		if (intDate == null) {
			return "";
		}
		Calendar c = newCalendar(intDate);
		return formatCalendar(c);
	}

	/**
	 * 根据指定格式化来格式日期.
	 *
	 * @param date
	 *            待格式化的日期.
	 * @param style
	 *            日期风格
	 * @return 字符串型日期.
	 */
	public static String format(Date date, DateEnums.DateStyle style) {
		return format(date, style.getValue());
	}

	/**
	 * 根据指定格式化来格式日期.
	 *
	 * @param date
	 *            待格式化的日期.
	 * @param pattern
	 *            格式化样式或分格,如yyMMddHHmmss
	 * @return 字符串型日期.
	 */
	public static String format(Date date, String pattern) {
		if (date == null) {
			return "";
		}
		if (isEmpty(pattern)) {
			return formatDate(date);
		}
		SimpleDateFormat simpleDateFormat = null;
		try {
			simpleDateFormat = new SimpleDateFormat(pattern);
		} catch (Exception e) {
			logger.error(e.toString(), e);
			return formatDate(date);
		}
		return simpleDateFormat.format(date);
	}

	/**
	 * 将日期字符串转化为另一日期字符串。失败返回null。
	 *
	 * @param date
	 *            旧日期字符串
	 * @param pattern
	 *            新日期格式
	 * @return 新日期字符串
	 */
	public static String changeFormat(String date, String pattern) {
		String dateString = null;
		Date myDate = parse(date);
		dateString = format(myDate, pattern);
		return dateString;
	}

	/**
	 * 将日期字符串转化为另一日期字符串。失败返回null。
	 *
	 * @param date
	 *            旧日期字符串
	 * @param olddPattern
	 *            旧日期格式
	 * @param newPattern
	 *            新日期格式
	 * @return 新日期字符串
	 */
	public static String changeFormat(String date, String olddPattern,
									  String newPattern) {
		String dateString = null;
		Date myDate = parse(date, olddPattern);
		dateString = format(myDate, newPattern);
		return dateString;
	}

	/**
	 * 将日期字符串转化为另一日期字符串。失败返回null。
	 *
	 * @param date
	 *            旧日期字符串
	 * @param olddDteStyle
	 *            旧日期风格
	 * @param newDateStyle
	 *            新日期风格
	 * @return 新日期字符串
	 */
	public static String changeFormat(String date, DateEnums.DateStyle olddDteStyle,
									  DateEnums.DateStyle newDateStyle) {
		String dateString = null;
		dateString = changeFormat(date, olddDteStyle.getValue(),
				newDateStyle.getValue());
		return dateString;
	}

	/**
	 * 把字符串转换为日期
	 *
	 * @param dateString
	 *            字符串
	 * @return yyyyMMdd
	 */
	public static Date parseDateChar(String dateString) {
		return parse(dateString, "yyyyMMdd");
	}

	/**
	 * 把字符串转换为日期
	 *
	 * @param dateString
	 *            字符串
	 * @return yyyy-MM-dd
	 */
	public static Date parseDate(String dateString) {
		return parse(dateString, "yyyy-MM-dd");
	}

	/**
	 * 把字符串转换为日期
	 *
	 * @param dateString
	 *            字符串
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static Date parseDateTime(String dateString) {
		return parse(dateString, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 把字符串转换为日期
	 *
	 * @param dateString
	 *            字符串
	 * @return 任意格式[DateStyle] 只要有一个转换成功就返回转换结果，否则返回null
	 */
	public static Date parse(String dateString) {
		if (isEmpty(dateString))
			return null;

		for (DateEnums.DateStyle style : DateEnums.DateStyle.values()) {
			Date date = null;
			SimpleDateFormat sdf = new SimpleDateFormat(style.getValue());
			try {
				date = (Date) sdf.parse(dateString);
				if (date != null && dateString.equals(sdf.format(date))) {
					return new Date(date.getTime());
				}
			} catch (ParseException e) {

			}
		}
		return null;
	}

	/**
	 * 把字符串转换为日期
	 *
	 * @param dateString
	 *            字符串
	 * @param pattern
	 *            格式
	 * @return
	 */
	public static Date parse(String dateString, String pattern) {
		if (isEmpty(dateString))
			return null;
		if (isEmpty(pattern))
			return parse(dateString);

		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			date = (Date) sdf.parse(dateString);
			if (date == null || !dateString.equals(sdf.format(date))) {
				logger.error("日期格式错误");
				return null;
			}
		} catch (ParseException e) {
			logger.error(e.toString(), e);
		}
		return new Date(date.getTime());
	}

	/**
	 * 把字符串转换为日期
	 *
	 * @param dateString
	 *            字符串
	 * @param style
	 *            格式
	 * @return
	 */
	public static Date parse(String dateString, DateEnums.DateStyle style) {
		return parse(dateString, style.getValue());
	}

	/**
	 * 把字符串转换为时间
	 *
	 * @param dateString
	 *            字符串
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static Timestamp parseTime(String dateString) {
		return parseTime(dateString, DAFAULT_DATETIME_FORMAT);
	}

	/**
	 * 把字符串转换为时间
	 *
	 * @param dateString
	 *            字符串
	 * @param pattern
	 *            格式
	 * @return
	 */
	public static Timestamp parseTime(String dateString, String pattern) {
		if ("".equals(dateString))
			return null;
		if (pattern == null)
			pattern = DAFAULT_DATETIME_FORMAT;

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = (Date) simpleDateFormat.parse(dateString);
			if (date == null
					|| !dateString.equals(simpleDateFormat.format(date))) {
				logger.error("日期时间格式错误");
				return null;
			}
		} catch (ParseException e) {
			logger.error(e.toString(), e);
		}
		return new Timestamp(date.getTime());
	}

	/**
	 * 当前时间与协调世界时 1970 年 1 月 1 日午夜之间的时间差（以毫秒为单位测量）。
	 *
	 * @author hezhao
	 * @Time 2017年8月1日 上午10:56:21
	 * @return
	 */
	public static long getCurrentTimeMillis() {
		return System.currentTimeMillis();
	}

	/**
	 * 系统计时器的当前值，以毫微秒为单位。
	 *
	 * @author hezhao
	 * @Time 2017年8月1日 上午10:56:12
	 * @return
	 */
	public static long getNanoTime() {
		return System.nanoTime();
	}

	/**
	 * 返回unix时间戳 (1970年至今的秒数)
	 *
	 * @return
	 */
	public static long getUnixStamp() {
		return System.currentTimeMillis() / 1000;
	}

	/**
	 * 得到昨天的日期，时间格式yyyy-MM-dd
	 *
	 * @return
	 */
	public static String getYestoryDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		String yestoday = dateFormat.format(calendar.getTime());
		return yestoday;
	}

	/**
	 * 得到明天的日期，时间格式yyyy-MM-dd
	 *
	 * @return
	 */
	public static String getTomorrowDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 1);
		String yestoday = dateFormat.format(calendar.getTime());
		return yestoday;
	}

	/**
	 * 得到当前的时间，时间格式yyyy-MM-dd
	 *
	 * @return
	 */
	public static String getCurrentDate() {
		return dateFormat.format(getNow());
	}

	/**
	 * 得到当前的时间,自定义时间格式
	 *
	 * @param pattern
	 *            输出显示的时间格式
	 * @return
	 */
	public static String getCurrentDate(String pattern) {
		SimpleDateFormat sdf = getFormatInstance(pattern);
		return sdf.format(getNow());
	}

	/**
	 * 取得Date型的当前日期
	 *
	 * @return
	 */
	public static Date getNow() {
		return new Date();
	}

	/**
	 * 取得Integer型的当前日期
	 *
	 * @return
	 */
	public static Integer getIntNow() {
		return getIntDate(getNow());
	}

	/**
	 * 取得Integer型的当前年份
	 *
	 * @return
	 */
	public static Integer getIntYearNow() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		return year;
	}

	/**
	 * 取得Integer型的当前月份
	 *
	 * @return
	 */
	public static Integer getIntMonthNow() {
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH) + 1;
		return month;
	}

	/**
	 * 取得Integer型的指定时间的年份
	 *
	 * @return
	 */
	public static Integer getIntYearByDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int year = c.get(Calendar.YEAR);
		return year;
	}

	/**
	 * 取得Integer型的指定时间的月份
	 *
	 * @return
	 */
	public static Integer getIntMonthByDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int month = c.get(Calendar.MONTH) + 1;
		return month;
	}


	public static String getStringToday() {
		return getIntDate(getNow()) + "";
	}

	/**
	 * 根据年月日获取整型日期
	 *
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Integer getIntDate(int year, int month, int day) {
		return getIntDate(newCalendar(year, month, day));
	}

	/**
	 * 根据Calendar获取整型年份
	 *
	 * @param c
	 * @return
	 */
	public static Integer getIntYear(Calendar c) {
		int year = c.get(Calendar.YEAR);
		return year;
	}

	/**
	 * 根据Calendar获取整型日期
	 *
	 * @param c
	 * @return
	 */
	public static Integer getIntDate(Calendar c) {
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		return year * 10000 + month * 100 + day;
	}

	/**
	 * 根据Date获取整型年份
	 *
	 * @param d
	 * @return
	 */
	public static Integer getIntYear(Date d) {
		if (d == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return getIntYear(c);
	}

	/**
	 * 根据Date获取整型日期
	 *
	 * @param d
	 * @return
	 */
	public static Integer getIntDate(Date d) {
		if (d == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return getIntDate(c);
	}

	/**
	 * 根据Integer获取Date日期
	 *
	 * @param n
	 * @return
	 */
	public static Date getDate(Integer n) {
		if (n == null) {
			return null;
		}
		// Calendar c = Calendar.getInstance();
		// c.set(n / 10000, n / 100 % 100 - 1, n % 100);
		// return c.getTime();
		return parse(String.valueOf(n));
	}

	/**
	 * 根据年月日生成Calendar
	 *
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Calendar newCalendar(int year, int month, int day) {
		Calendar ret = Calendar.getInstance();
		if (year < 100) {
			year = 2000 + year;
		}
		ret.set(year, month - 1, day);
		return ret;
	}

	/**
	 * 根据整型日期生成Calendar
	 *
	 * @param date
	 * @return
	 */
	public static Calendar newCalendar(int date) {
		int year = date / 10000;
		int month = (date % 10000) / 100;
		int day = date % 100;

		Calendar ret = Calendar.getInstance();
		ret.set(year, month - 1, day);
		return ret;
	}

	/**
	 * 整数型日期的加法
	 *
	 * @param date
	 * @param days
	 * @return
	 */
	public static Integer intDateAdd(int date, int days) {
		int year = date / 10000;
		int month = (date % 10000) / 100;
		int day = date % 100;

		day += days;

		return getIntDate(year, month, day);
	}

	/**
	 * 整数型日期的减法
	 *
	 * @param date
	 * @param days
	 * @return
	 */
	public static Integer intDateSub(int date, int days) {
		return intDateAdd(date, -days);
	}

	/**
	 * 获取某年第一天
	 *
	 * @param year
	 * @return
	 */
	public static Date getFirstDateByYear(Integer year) {
		if (year == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.set(year, 0, 1, 0, 0, 0);
		return c.getTime();
	}

	/**
	 * 获取某年第一天
	 *
	 * @param date
	 * @return
	 */
	public static Date getFirstDateByYear(Date date) {
		return getFirstDateByYear(getYear(date));
	}

	/**
	 * 获取今年第一天
	 *
	 * @return
	 */
	public static Date getFirstDateByYear() {
		return getFirstDateByYear(getNow());
	}

	/**
	 * 获取某年最后一天
	 *
	 * @param date
	 * @return 年份的最后一天
	 */
	public static Date getLastDateByYear(Date date) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.YEAR, now.get(Calendar.YEAR) + 1);
		now.set(Calendar.MONTH, 0);
		now.set(Calendar.DATE, 0);
		now.set(Calendar.HOUR_OF_DAY, 23);
		now.set(Calendar.MINUTE, 59);
		now.set(Calendar.SECOND, 59);
		return now.getTime();
	}

	/**
	 * 获取今年最后一天
	 *
	 * @return 年份的最后一天
	 */
	public static Date getLastDateByYear() {
		return getLastDateByYear(getNow());
	}

	/**
	 * 获取某年最后一天
	 *
	 * @param year
	 * @return 年份的最后一天
	 */
	public static Date getLastDateByYear(int year) {
		return getLastDateByYear(getDate(year));
	}

	/**
	 * 某年月的第一天
	 *
	 * @param year
	 * @param month
	 * @return
	 */
	public static Integer getFirstDateByMonth(int year, int month) {
		return getIntDate(newCalendar(year, month, 1));
	}

	/**
	 * 本月第一天
	 *
	 * @return
	 */
	public static Integer getFirstDateByMonth() {
		Integer year = getIntYearNow();
		Integer month = getIntMonthNow();
		return getIntDate(newCalendar(year, month, 1));
	}

	/**
	 * 获得所在月份的第一天
	 *
	 * @param date 当前月份所在的时间
	 * @return 月份的第一天
	 */
	public static Date getFirstDateByMonth(Date date) {

		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.DATE, 0);
		now.set(Calendar.HOUR_OF_DAY, 24);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		return now.getTime();
	}

	/**
	 * 获得所在月份的最后一天
	 *
	 * @param date 当前月份所在的时间
	 * @return 月份的最后一天
	 */
	public static Date getLastDateByMonth(Date date) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.MONTH, now.get(Calendar.MONTH) + 1);
		now.set(Calendar.DATE, 1);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - 1);
		now.set(Calendar.HOUR_OF_DAY, 23);
		now.set(Calendar.MINUTE, 59);
		now.set(Calendar.SECOND, 59);
		return now.getTime();
	}

	/**
	 * 本月最后一天
	 *
	 * @return
	 */
	public static Integer getLastDateByMonth() {
		Integer year = getIntYearNow();
		Integer month = getIntMonthNow();
		return intDateSub(getIntDate(newCalendar(year, month + 1, 1)), 1);
	}

	/**
	 * 某年月的最后一天
	 *
	 * @param year
	 * @param month
	 * @return
	 */
	public static Integer getLastDateByMonth(int year, int month) {
		return intDateSub(getIntDate(newCalendar(year, month + 1, 1)), 1);
	}

	/**
	 * 获得所在星期的第一天，(星期一为一周开始)
	 */
	public static Date getFirstDateByWeek(Date date) {

		Calendar now = Calendar.getInstance();
		now.setTime(date);
		int today = now.get(Calendar.DAY_OF_WEEK);
		int first_day_of_week = now.get(Calendar.DATE) + 2 - today; // 星期一
		now.set(now.DATE, first_day_of_week);
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		return now.getTime();
	}

	/**
	 * 获得所在星期的最后一天，(星期一为一周开始)
	 */
	public static Date getLastDateByWeek(Date date) {

		Calendar now = Calendar.getInstance();
		now.setTime(date);
		int today = now.get(Calendar.DAY_OF_WEEK);
		int first_day_of_week = now.get(Calendar.DATE) + 2 - today; // 星期一
		int last_day_of_week = first_day_of_week + 6; // 星期日
		now.set(now.DATE, last_day_of_week);
		now.set(Calendar.HOUR_OF_DAY, 23);
		now.set(Calendar.MINUTE, 59);
		now.set(Calendar.SECOND, 59);
		return now.getTime();
	}

	/**
	 * 获得所在星期的第一天，(星期日为一周开始)
	 */
	public static Date getFirstDateByWeekBySunDay(Date date) {

		Calendar now = Calendar.getInstance();
		now.setTime(date);
		int today = now.get(Calendar.DAY_OF_WEEK);
		int first_day_of_week = now.get(Calendar.DATE) + 1 - today; // 星期日
		now.set(now.DATE, first_day_of_week);
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		return now.getTime();
	}

	/**
	 * 获得所在星期的最后一天，(星期日为一周开始)
	 */
	public static Date getLastDateByWeekBySunDay(Date date) {

		Calendar now = Calendar.getInstance();
		now.setTime(date);
		int today = now.get(Calendar.DAY_OF_WEEK);
		int first_day_of_week = now.get(Calendar.DATE) + 1 - today; // 星期日
		int last_day_of_week = first_day_of_week + 6; // 星期六
		now.set(now.DATE, last_day_of_week);
		now.set(Calendar.HOUR_OF_DAY, 23);
		now.set(Calendar.MINUTE, 59);
		now.set(Calendar.SECOND, 59);
		return now.getTime();
	}

	/**
	 * 获取上个月
	 * @return
	 */
	public static String getLastMonth(){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		SimpleDateFormat format =  new SimpleDateFormat("yyyyMM");
		String time = format.format(c.getTime());
		return time;
	}

	/**
	 * 返回一天的开始时间，有误差
	 *
	 * @param day
	 * @return
	 */
	@Deprecated
	public static Date getDayStart(Date day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	/**
	 * 返回一天的结束时间,有误差
	 *
	 * @param day
	 * @return
	 */
	@Deprecated
	public static Date getDayEnd(Date day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	/**
	 * 返回当天的开始时间 00:00:00
	 *
	 * @return
	 */
	public static Date getDateStart() {
		return getDateStart(getNow());
	}

	/**
	 * 返回一天的开始时间 00:00:00
	 *
	 * @param day
	 * @return
	 */
	public static Date getDateStart(Date day) {
		Date date = DateUtil.parse(DateUtil.format(day, "yyyy-MM-dd 00:00:00"), DAFAULT_DATETIME_FORMAT);
		return date;
	}

	/**
	 * 返回当天的结束时间 23:59:59
	 *
	 * @return
	 */
	public static Date getDateEnd() {
		return getDateEnd(getNow());
	}

	/**
	 * 返回一天的结束时间 23:59:59
	 *
	 * @param day
	 * @return
	 */
	public static Date getDateEnd(Date day) {
		Date date = DateUtil.parse(DateUtil.format(day, "yyyy-MM-dd 23:59:59"), DAFAULT_DATETIME_FORMAT);
		return date;
	}

	// /**
	// * 国际化。
	// */
	// public static String formatI18nDate(Date date){
	// if(date == null){
	// return "";
	// }
	// actionSupport actionSupport = new ActionSupport();
	// SimpleDateFormat sdf = new
	// SimpleDateFormat(actionSupport.getText("date.i18n.format"));
	// return sdf.format(date);
	// }

	/**
	 * 获取SimpleDateFormat实例.
	 *
	 * @param pattern
	 *            日期格式 如果为空使用DAFAULT_DATE_FORMAT
	 * @return
	 */
	public static SimpleDateFormat getFormatInstance(String pattern) {
		if (isEmpty(pattern)) {
			pattern = DAFAULT_DATE_FORMAT;
		}
		return new SimpleDateFormat(pattern);
	}

	/**
	 * 获取日期字符串的日期风格。失敗返回null。
	 *
	 * @param date
	 *            日期字符串
	 * @return 日期风格
	 */
	public static DateEnums.DateStyle getDateStyle(String date) {
		if (isEmpty(date))
			return null;

		for (DateEnums.DateStyle style : DateEnums.DateStyle.values()) {
			Date dateTmp = null;
			SimpleDateFormat sdf = new SimpleDateFormat(style.getValue());
			try {
				dateTmp = (Date) sdf.parse(date);
				if (dateTmp != null && date.equals(sdf.format(dateTmp))) {
					return style;
				}
			} catch (ParseException e) {
			}
		}
		return null;
	}

	/**
	 * 获取日期中的某数值。如获取月份
	 *
	 * @param date
	 *            日期
	 * @param dateType
	 *            日期格式
	 * @return 数值
	 */
	private static int getInteger(Date date, int dateType) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(dateType);
	}

	/**
	 * 增加日期中某类型的某数值。如增加日期
	 *
	 * @param date
	 *            日期字符串
	 * @param dateType
	 *            类型
	 * @param amount
	 *            数值
	 * @return 计算后日期字符串
	 */
	private static String addInteger(String date, int dateType, int amount) {
		String dateString = null;
		DateEnums.DateStyle dateStyle = getDateStyle(date);
		if (dateStyle != null) {
			Date myDate = parse(date, dateStyle);
			myDate = addInteger(myDate, dateType, amount);
			dateString = format(myDate, dateStyle);
		}
		return dateString;
	}

	/**
	 * 增加日期中某类型的某数值。如增加日期
	 *
	 * @param date
	 *            日期
	 * @param dateType
	 *            类型
	 * @param amount
	 *            数值
	 * @return 计算后日期
	 */
	private static Date addInteger(Date date, int dateType, int amount) {
		Date myDate = null;
		if (date != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(dateType, amount);
			myDate = calendar.getTime();
		}
		return myDate;
	}

	/**
	 * 判断字符串是否为日期字符串
	 *
	 * @param date
	 *            日期字符串
	 * @return true or false
	 */
	public static boolean isDate(String date) {
		boolean isDate = false;
		if (date != null) {
			if (parse(date) != null) {
				isDate = true;
			}
		}
		return isDate;
	}

	/**
	 * 增加日期的年份。失败返回null。
	 *
	 * @param date
	 *            日期
	 * @param yearAmount
	 *            增加数量。可为负数
	 * @return 增加年份后的日期字符串
	 */
	public static String addYear(String date, int yearAmount) {
		return addInteger(date, Calendar.YEAR, yearAmount);
	}

	/**
	 * 增加日期的年份。失败返回null。
	 *
	 * @param date
	 *            日期
	 * @param yearAmount
	 *            增加数量。可为负数
	 * @return 增加年份后的日期
	 */
	public static Date addYear(Date date, int yearAmount) {
		return addInteger(date, Calendar.YEAR, yearAmount);
	}

	/**
	 * 增加日期的月份。失败返回null。
	 *
	 * @param date
	 *            日期
	 * @param monthAmount
	 *            增加数量。可为负数
	 * @return 增加月份后的日期字符串
	 */
	public static String addMonth(String date, int monthAmount) {
		return addInteger(date, Calendar.MONTH, monthAmount);
	}

	/**
	 * 增加日期的月份。失败返回null。
	 *
	 * @param date
	 *            日期
	 * @param monthAmount
	 *            增加数量。可为负数
	 * @return 增加月份后的日期
	 */
	public static Date addMonth(Date date, int monthAmount) {
		return addInteger(date, Calendar.MONTH, monthAmount);
	}

	/**
	 * 增加日期的天数。失败返回null。
	 *
	 * @param date
	 *            日期字符串
	 * @param dayAmount
	 *            增加数量。可为负数
	 * @return 增加天数后的日期字符串
	 */
	public static String addDay(String date, int dayAmount) {
		return addInteger(date, Calendar.DATE, dayAmount);
	}

	/**
	 * 增加日期的天数。失败返回null。
	 *
	 * @param date
	 *            日期
	 * @param dayAmount
	 *            增加数量。可为负数
	 * @return 增加天数后的日期
	 */
	public static Date addDay(Date date, int dayAmount) {
		return addInteger(date, Calendar.DATE, dayAmount);
	}

	/**
	 * 增加日期的小时。失败返回null。
	 *
	 * @param date
	 *            日期字符串
	 * @param hourAmount
	 *            增加数量。可为负数
	 * @return 增加小时后的日期字符串
	 */
	public static String addHour(String date, int hourAmount) {
		return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);
	}

	/**
	 * 增加日期的小时。失败返回null。
	 *
	 * @param date
	 *            日期
	 * @param hourAmount
	 *            增加数量。可为负数
	 * @return 增加小时后的日期
	 */
	public static Date addHour(Date date, int hourAmount) {
		return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);
	}

	/**
	 * 增加日期的分钟。失败返回null。
	 *
	 * @param date
	 *            日期字符串
	 * @param hourAmount
	 *            增加数量。可为负数
	 * @return 增加分钟后的日期字符串
	 */
	public static String addMinute(String date, int hourAmount) {
		return addInteger(date, Calendar.MINUTE, hourAmount);
	}

	/**
	 * 增加日期的分钟。失败返回null。
	 *
	 * @param date
	 *            日期
	 * @param hourAmount
	 *            增加数量。可为负数
	 * @return 增加分钟后的日期
	 */
	public static Date addMinute(Date date, int hourAmount) {
		return addInteger(date, Calendar.MINUTE, hourAmount);
	}

	/**
	 * 增加日期的秒钟。失败返回null。
	 *
	 * @param date
	 *            日期字符串
	 * @param hourAmount
	 *            增加数量。可为负数
	 * @return 增加秒钟后的日期字符串
	 */
	public static String addSecond(String date, int hourAmount) {
		return addInteger(date, Calendar.SECOND, hourAmount);
	}

	/**
	 * 增加日期的秒钟。失败返回null。
	 *
	 * @param date
	 *            日期
	 * @param hourAmount
	 *            增加数量。可为负数
	 * @return 增加秒钟后的日期
	 */
	public static Date addSecond(Date date, int hourAmount) {
		return addInteger(date, Calendar.SECOND, hourAmount);
	}

	/**
	 * 获取日期的年份。失败返回0。
	 *
	 * @param date
	 *            日期字符串
	 * @return 年份
	 */
	public static int getYear(String date) {
		return getYear(parse(date));
	}

	/**
	 * 获取日期的年份。失败返回0。
	 *
	 * @param date
	 *            日期
	 * @return 年份
	 */
	public static int getYear(Date date) {
		return getInteger(date, Calendar.YEAR);
	}

	/**
	 * 获取日期的月份。失败返回0。
	 *
	 * @param date
	 *            日期字符串
	 * @return 月份
	 */
	public static int getMonth(String date) {
		return getMonth(parse(date));
	}

	/**
	 * 获取日期的月份。失败返回0。
	 *
	 * @param date
	 *            日期
	 * @return 月份
	 */
	public static int getMonth(Date date) {
		return getInteger(date, Calendar.MONTH);
	}

	/**
	 * 获取日期的天数。失败返回0。
	 *
	 * @param date
	 *            日期字符串
	 * @return 天
	 */
	public static int getDay(String date) {
		return getDay(parse(date));
	}

	/**
	 * 获取日期的天数。失败返回0。
	 *
	 * @param date
	 *            日期
	 * @return 天
	 */
	public static int getDay(Date date) {
		return getInteger(date, Calendar.DATE);
	}

	/**
	 * 获取日期的小时。失败返回0。
	 *
	 * @param date
	 *            日期字符串
	 * @return 小时
	 */
	public static int getHour(String date) {
		return getHour(parse(date));
	}

	/**
	 * 获取日期的小时。失败返回0。
	 *
	 * @param date
	 *            日期
	 * @return 小时
	 */
	public static int getHour(Date date) {
		return getInteger(date, Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取日期的分钟。失败返回0。
	 *
	 * @param date
	 *            日期字符串
	 * @return 分钟
	 */
	public static int getMinute(String date) {
		return getMinute(parse(date));
	}

	/**
	 * 获取日期的分钟。失败返回0。
	 *
	 * @param date
	 *            日期
	 * @return 分钟
	 */
	public static int getMinute(Date date) {
		return getInteger(date, Calendar.MINUTE);
	}

	/**
	 * 获取日期的秒钟。失败返回0。
	 *
	 * @param date
	 *            日期字符串
	 * @return 秒钟
	 */
	public static int getSecond(String date) {
		return getSecond(parse(date));
	}

	/**
	 * 获取日期的秒钟。失败返回0。
	 *
	 * @param date
	 *            日期
	 * @return 秒钟
	 */
	public static int getSecond(Date date) {
		return getInteger(date, Calendar.SECOND);
	}

	/**
	 * 获取日期。默认yyyy-MM-dd格式。失败返回null。
	 *
	 * @param date
	 *            日期
	 * @return 日期
	 */
	public static String getDate(Date date) {
		return format(date, DateEnums.DateStyle.yyyy_MM_dd);
	}

	/**
	 * 获取日期的时间。默认HH:mm:ss格式。失败返回null。
	 *
	 * @param date
	 *            日期字符串
	 * @return 时间
	 */
	public static String getTime(String date) {
		return getTime(parse(date));
	}

	/**
	 * 获取日期的时间。默认HH:mm:ss格式。失败返回null。
	 *
	 * @param date
	 *            日期
	 * @return 时间
	 */
	public static String getTime(Date date) {
		return format(date, DateEnums.DateStyle.HH_mm_ss);
	}

	/**
	 * 获取日期的星期。失败返回null。
	 *
	 * @param date
	 *            日期字符串
	 * @return 星期
	 */
	public static DateEnums.Week getWeek(String date) {
		DateEnums.Week week = null;
		DateEnums.DateStyle dateStyle = getDateStyle(date);
		if (dateStyle != null) {
			Date myDate = parse(date, dateStyle);
			week = getWeek(myDate);
		}
		return week;
	}

	/**
	 * 获取日期的星期。失败返回null。
	 *
	 * @param date
	 *            日期
	 * @return 星期
	 */
	public static DateEnums.Week getWeek(Date date) {
		DateEnums.Week week = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int weekNumber = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		switch (weekNumber) {
			case 0:
				week = DateEnums.Week.SUNDAY;
				break;
			case 1:
				week = DateEnums.Week.MONDAY;
				break;
			case 2:
				week = DateEnums.Week.TUESDAY;
				break;
			case 3:
				week = DateEnums.Week.WEDNESDAY;
				break;
			case 4:
				week = DateEnums.Week.THURSDAY;
				break;
			case 5:
				week = DateEnums.Week.FRIDAY;
				break;
			case 6:
				week = DateEnums.Week.SATURDAY;
				break;
		}
		return week;
	}

}
