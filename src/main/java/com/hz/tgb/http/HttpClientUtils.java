package com.hz.tgb.http;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

/** HTTP请求工具类 . 中车
 * @author hezhao
 * 
 */
public class HttpClientUtils {

	private static Logger log = Logger.getLogger(HttpClientUtils.class);

	@Deprecated
	public static String request(final Map<String, String> reqParams, final String paramName, final String url) {
		return request(reqParams, url);
	}

	/**
	 * 
	 * @author luo bohui
	 * @date 2015年8月7日
	 * @param reqParams
	 * @param url
	 * @return
	 */
	public static String request(final Map<String, String> reqParams, final String url) {
		final HttpClient client = new HttpClient();
		final PostMethod method = new PostMethod(url);
		method.setRequestHeader("Connection", "close");  
		if (MapUtils.isNotEmpty(reqParams)) {
			final NameValuePair[] params = new NameValuePair[reqParams.size()];
			int i = 0;
			for (final Entry<String, String> entry : reqParams.entrySet()) {
				params[i++] = new NameValuePair(entry.getKey(), entry.getValue());
			}
			method.addParameters(params);
		}
		method.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; text/html; charset=UTF-8");
		try {
			final int statusCode = client.executeMethod(method);
			// System.out.println("请求statusCode为:" + statusCode);
			if (statusCode == 200) {
				return new String(method.getResponseBody(), "utf-8");
			} else {
				if (method.getResponseBody() != null) {
					log.error("请求[" + url + "]返回状态代码[" + statusCode + "],响应信息为:"
							+ new String(method.getResponseBody(), "utf-8"));
				} else {
					log.error("请求[" + url + "]返回状态代码[" + statusCode + "]");
				}
			}
		} catch (final HttpException e) {
			log.error("请求[" + url + "]发生异常:" + e.getMessage());
		} catch (final IOException e) {
			log.error("请求[" + url + "]发生异常:" + e.getMessage());
		} finally {
			method.releaseConnection();
			client.getHttpConnectionManager().closeIdleConnections(0);
		}
		return "";
	}

	public static void main(final String[] args) {
		System.out.println(request(null,"http://www.ycxc.com"));
	}
}
