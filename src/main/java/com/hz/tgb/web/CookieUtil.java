package com.hz.tgb.web;

import com.hz.tgb.encrypt.MD5Util;
import com.hz.tgb.encrypt.base64.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class CookieUtil {
	/**
	 * 保存cookie时的cookieName
	 */
	private static String cookieDomainName = "ycxcLogin";
	/**
	 * 加密cookie时的网站自定码
	 */
	private final static String webKey = "ycxc";
	/**
	 * 设置cookie有效期是一个星期，根据需要自定义
	 */
	private final static long cookieMaxAge = 60 * 60 * 24 * 7 * 1; 
	
	
	public CookieUtil(String cookieDomainName)
	{
		CookieUtil.cookieDomainName = cookieDomainName;
	}
	
	
	/**
	 * 传递进来的map对象中封装了在登陆时填写的用户名与密码
	 * @param map  
	 * mapvalue  userName:登录用户名， password：登录密码，loginType 登錄類型
	 * @param response
	 */
	public static void saveCookie(Map<String, Object> map, HttpServletResponse response) {

		//cookie的有效期
		long validTime = System.currentTimeMillis() + (cookieMaxAge * 1000);
		//MD5加密用户详细信息
		String cookieValueWithMd5 = MD5Util.md5(map.get("userName").toString() + ":" + map.get("password").toString()+ ":" +map.get("loginType").toString()+ ":" + validTime + ":" + CookieUtil.webKey);
		//将要被保存的完整的Cookie值
		String cookieValue = map.get("userName").toString() + ":" +map.get("loginType").toString()+ ":"+ validTime + ":" + cookieValueWithMd5;
		//再一次对Cookie的值进行BASE64编码
		String cookieValueBase64 = new String(Base64.encode(cookieValue.getBytes()));
		//开始保存Cookie
		Cookie cookie = new Cookie(cookieDomainName, cookieValueBase64);

		//存一周(这个值应该大于或等于validTime)
		cookie.setMaxAge(60 * 60 * 24 * 7 * 2);
		//cookie有效路径是网站根目录
		cookie.setPath("/");
		//向客户端写入
		response.addCookie(cookie);

	}
	
	/**
	 * 登录用户已存在cookie  修改coolie中的值
	 * @param autoCookie 已存在的登陆cookie
	 * @param map
	 * mapvalue  userName:登录用户名， password：登录密码，loginType 登錄類型
	 * @param response
	 */
	public static void updateCookie(Cookie autoCookie, Map<String, Object> map, HttpServletResponse response){
		//cookie的有效期
		long validTime = System.currentTimeMillis() + (cookieMaxAge * 1000);
		//MD5加密用户详细信息
		String cookieValueWithMd5 = MD5Util.md5(map.get("userName").toString() + ":" + map.get("password").toString()+ ":" +map.get("loginType").toString()+ ":" + validTime + ":" + webKey);
		//将要被保存的完整的Cookie值
		String cookieValue = map.get("userName").toString() + ":" +map.get("loginType").toString()+ ":" + validTime + ":" + cookieValueWithMd5;
		//再一次对Cookie的值进行BASE64编码
		String cookieValueBase64 = new String(Base64.encode(cookieValue.getBytes()));
		//开始保存Cookie
		autoCookie.setValue(cookieValueBase64);
		//存一周(这个值应该大于或等于validTime)
		autoCookie.setMaxAge(60 * 60 * 24 * 7 * 2);
		//cookie有效路径是网站根目录
		autoCookie.setPath("/");
		//向客户端写入
		response.addCookie(autoCookie);
	}
	
	
	/**
	 * 用户注销时,清除Cookie
	 * @param response
	 */
	public static void clearCookie( HttpServletResponse response){
		Cookie cookie = new Cookie(cookieDomainName, null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	public static String getCookieValue(Cookie[] cookies, String name) {
		if (cookies == null) {
			return null;
		}
		for (Cookie c : cookies) {
			if (name.equals(c.getName())) {
				return c.getValue();
			}
		}
		return null;
	}

	/**
	 * 在取cookie值，如果获取失败，将用空字符串代替null。
	 * 同时把代码中多处重复代码都替换成这个
	 */
	public static String getCookieValueWithEmptyStrReplaceNull(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		String defaultVal = "";
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (name.equals(c.getName())) {
					defaultVal = c.getValue();
					break;
				}
			}
		}
		return defaultVal;
	}

	/**
	 * 记录当前的所有cookie
	 */
	public static String getAllCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		StringBuffer cookieSb = new StringBuffer();
		if (cookies != null) {
			for (Cookie ck : cookies) {
				cookieSb.append(ck.getName());
				cookieSb.append(",");
				cookieSb.append(ck.getValue());
				cookieSb.append(";");
			}
		}
		return cookieSb.toString();
	}

	public static String getRfcCookies(HttpServletRequest request, String domain) {
		Cookie[] cookies = request.getCookies();
		StringBuffer cookieSb = new StringBuffer();
		if (cookies != null) {
			for (Cookie ck : cookies) {
				cookieSb.append(ck.getName());
				cookieSb.append("=");
				cookieSb.append(ck.getValue());
				cookieSb.append(";");
			}
		}
		if (StringUtils.isNotEmpty(domain)) {
			cookieSb.append("domain=").append(domain).append(";");
		}
		return cookieSb.toString();
	}

	public static Cookie getCookie(HttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}
		Cookie cookie = null;
		for (Cookie ck : cookies) {
			String ckName = ck.getName();
			if (cookieName.equals(ckName)) {
				cookie = ck;
				break;
			}
		}
		return cookie;
	}

	public static void writeTouchCookie(HttpServletResponse response, HttpServletRequest request, int maxAge, String cName, String cValue, String domain) {
		Cookie cookie = CookieUtil.getCookie(request, cName);
		if (cookie == null) {
			cookie = new Cookie(cName, cValue);
		} else {
			cookie.setValue(cValue);
		}
		cookie.setMaxAge(maxAge);
		cookie.setDomain(domain);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	public static void writeTouchCookie(HttpServletResponse response, HttpServletRequest request, String cName, String cValue, String domain, int hour) {
		writeTouchCookie(response, request, hour * 3600, cName, cValue, domain);
	}

	public static void setCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value == null ? "" : value);
		cookie.setMaxAge(maxAge);
		cookie.setPath(getPath(request));
		response.addCookie(cookie);
	}

	private static String getPath(HttpServletRequest request) {
		String path = request.getContextPath();
		return (path == null || path.length() == 0) ? "/" : path;
	}
	
}
