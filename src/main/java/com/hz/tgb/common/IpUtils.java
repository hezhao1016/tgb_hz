package com.hz.tgb.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * Ip地址工具类
 * 
 * @author hezhao
 * @Time 2016年5月11日 下午1:51:18
 * @Description 无
 * @Version V 1.0
 */
public class IpUtils {

	private static final Logger logger = LoggerFactory.getLogger(IpUtils.class);
	
	private static final String[] ips = {"127.0.0.1","127.0.0.1"};
	
	private IpUtils(){
		// 私用构造主法.因为此类是工具类.
	}

	/**
	 * 获取用户的IP地址
	 * 
	 * @param request
	 *        当前请求
	 * @return 用户的IP地址
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		// 多个路由时，取第一个非unknown的ip
		final String[] arr = ip.split(",");
		for (final String str : arr) {
			if (!"unknown".equalsIgnoreCase(str)) {
				ip = str;
				break;
			}
		}

		return ip;
	}

	/**
	 * 判断访问IP是否合法
	 * 
	 * @param request
	 *            请求
	 * @param ips
	 *            可允许的IP数组
	 * @return
	 */
	public static boolean checkIpAddr(HttpServletRequest request, String[] ips) {
		String ipAddr = getIpAddr(request);
		logger.debug("访问IP:::::::::::::" + ipAddr);
		boolean result = false;
		for (int i = 0; i < ips.length; i++) {
			if (ips[i].equals(ipAddr)) {
				result = true;
			}
		}
		return result;
	}
	
	/**
	 * 判断访问IP是否合法
	 * 
	 * @param request
	 *            请求
	 * @return
	 */
	public static boolean checkIpAddr(HttpServletRequest request) {
		return checkIpAddr(request, IpUtils.ips);
	}

	/**
	 * 把ip地址格式化为：000.000.000.000
	 * 
	 * @param ip
	 * @return 返回规格ip
	 */
	public static String strfullIp(String ip) {
		StringBuffer buff = new StringBuffer();
		buff.append("");
		String strzero = "000";
		int ilen = 0;
		if (ip != null) {
			String[] arrip = ip.split("\\.");
			if (arrip.length == 4) {
				for (int i = 0; i < 4; i++) {
					if (i == 0) {
						ilen = arrip[i].length();
						if (ilen < 3) {
							buff.append(strzero.substring(0, 3 - ilen)).append(
									arrip[i]);
						} else {
							buff.append(arrip[i]);
						}
					} else {
						ilen = arrip[i].length();
						if (ilen < 3) {
							buff.append(".")
									.append(strzero.substring(0, 3 - ilen))
									.append(arrip[i]);
						} else {
							buff.append(".").append(arrip[i]);
						}
					}
				}
			}
		}
		return buff.toString();
	}
	
	/**
	 * 获取本机的IP地址
	 * @return
	 * @throws UnknownHostException
	 */
	public static String getLocalIP() throws UnknownHostException {
		InetAddress addr = InetAddress.getLocalHost();
		return addr.getHostAddress();
	}

	/**
	 * 获取本机的机器名
	 * @return
	 * @throws UnknownHostException
	 */
	public static String getLocalHostName() throws UnknownHostException {
		InetAddress addr = InetAddress.getLocalHost();
		return addr.getHostName();
	}
	/**
	 * 根据域名获得主机的IP地址
	 * @param hostName	域名
	 * @return
			 * @throws UnknownHostException
	 */
	public static String getIPByName(String hostName)
			throws UnknownHostException {
		InetAddress addr = InetAddress.getByName(hostName);
		return addr.getHostAddress();
	}
	/**
	 * 根据域名获得主机所有的IP地址
	 * @param hostName	域名
	 * @return
	 * @throws UnknownHostException
	 */
	public static String[] getAllIPByName(String hostName)
			throws UnknownHostException {
		InetAddress[] addrs = InetAddress.getAllByName(hostName);
		String[] ips = new String[addrs.length];
		for (int i = 0; i < addrs.length; i++) {
			ips[i] = addrs[i].getHostAddress();
		}
		return ips;
	}
	
	/**
	 * 获取用户的IP归属地
	 * 
	 * @param ip
	 *        用户的IP
	 * @return IP归属地
	 * @throws Exception
	 */
	public static String getAddresses(final String ip) throws Exception {
		final String http = "http://ip.taobao.com/service/getIpInfo.php";
		final String data = getResult(http, "ip=" + ip, "utf-8");
		if (data != null) {
			final String[] temp = data.split(",");
			if (temp.length < 3) {
				return "0";
			}
			String region = (temp[5].split(":"))[1].replaceAll("\"", "");
			region = decodeUnicode(region);
			String country = "";
			String area = "";
			String city = "";
			String county = "";
			String isp = "";
			for (int i = 0; i < temp.length; i++) {
				switch (i) {
				case 1:
					country = (temp[i].split(":"))[2].replaceAll("\"", "");
					country = decodeUnicode(country);
					break;
				case 3:
					area = (temp[i].split(":"))[1].replaceAll("\"", "");
					area = decodeUnicode(area);
					break;
				case 5:
					region = (temp[i].split(":"))[1].replaceAll("\"", "");
					region = decodeUnicode(region);
					break;
				case 7:
					city = (temp[i].split(":"))[1].replaceAll("\"", "");
					city = decodeUnicode(city);
					break;
				case 9:
					county = (temp[i].split(":"))[1].replaceAll("\"", "");
					county = decodeUnicode(county);
					break;
				case 11:
					isp = (temp[i].split(":"))[1].replaceAll("\"", "");
					isp = decodeUnicode(isp);
					break;
				}
			}
			if(!ValidateUtil.isEmptyString(region)&&!ValidateUtil.isEmptyString(city))
			{
				return region + "," + city + "," + isp;
			}
			else
			{
				return null;
			}
		}
		return null;
	}
	
	/**
	 * 进行HTTP请求
	 * 
	 * @param http
	 *        请求的地址
	 * @param content
	 *        请求的参数
	 * @param encoding
	 *        服务器端请求编码
	 * @return 请求结果
	 */
	public static String getResult(final String http, final String content, final String encoding) {
		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = new URL(http);
			connection = (HttpURLConnection) url.openConnection();// 新建连接实例
			connection.setConnectTimeout(2000);// 设置连接超时时间，单位毫秒
			connection.setReadTimeout(2000);// 设置读取数据超时时间，单位毫秒
			connection.setDoOutput(true);// 是否打开输出流 true|false
			connection.setDoInput(true);// 是否打开输入流true|false
			connection.setRequestMethod("POST");// 提交方法POST|GET
			connection.setUseCaches(false);// 是否缓存true|false
			connection.connect();// 打开连接端口
			final DataOutputStream out = new DataOutputStream(
					connection.getOutputStream());// 打开输出流往对端服务器写数据
			out.writeBytes(content);// 写数据,也就是提交你的表单 name=xxx&pwd=xxx
			out.flush();// 刷新
			out.close();// 关闭输出流
			final BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), encoding));// 往对端写完数据对端服务器返回数据
			// ,以BufferedReader流来读取
			final StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
			return buffer.toString();
		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();// 关闭连接
			}
		}
		return null;
	}

	/**
	 * unicode码转换中文
	 * 
	 * @param data
	 *        unicode码
	 * @return 中文
	 */
	public static String decodeUnicode(final String data) {
		char aChar;
		final int len = data.length();
		final StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = data.charAt(x++);
			if (aChar == '\\') {
				aChar = data.charAt(x++);
				if (aChar == 'u') {
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = data.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException(
									"Malformed      encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't') {
						aChar = '\t';
					} else if (aChar == 'r') {
						aChar = '\r';
					} else if (aChar == 'n') {
						aChar = '\n';
					} else if (aChar == 'f') {
						aChar = '\f';
					}
					outBuffer.append(aChar);
				}
			} else {
				outBuffer.append(aChar);
			}
		}
		return outBuffer.toString();
	}
	
	public static void main(String[] args) throws UnknownHostException {
		// 获取本机的IP地址和机器名
		System.out.println("Local IP: " + getLocalIP());
		System.out.println("Local HostName: " + getLocalHostName());

		// 获得微软网站的IP
		String hostName = "www.microsoft.com";
		System.out.println("域名为" + hostName + "的主机的IP地址：");
		System.out.println(getIPByName(hostName));

		System.out.println("域名为" + hostName + "的主机的所有IP地址：");
		String[] ips = getAllIPByName(hostName);
		for (int i = 0; i < ips.length; i++) {
			System.out.println(ips[i]);
		}
		
		try {
			System.out.println("Ip[113.105.67.99] 归属：  "+getAddresses("113.105.67.99"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}