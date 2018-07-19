package com.hz.tgb.http.demo;

import com.hz.tgb.json.FastJsonUtil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * 同步注册会员到b2b平台
 * @author hezhao
 *
 */
public class DataSynchronization {

	protected static final Logger LOG = LoggerFactory.getLogger(DataSynchronization.class);

	/**
	 * 注册同步数据到B2B平台--会员，会员用户
	 * @param map  //同步数据的参数
	 * @param map  //同步到B2B平台的url
	 */
	public static void sendRegister(Map<String, Object> map, String url){
		HttpClient client = new HttpClient();
		// 创建httppost    url暂时使用
		PostMethod method = new PostMethod(url);
		NameValuePair nameValuePair = new NameValuePair("json", FastJsonUtil.serialize(map));
		method.addParameters(new NameValuePair[]{nameValuePair});
		method.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; text/html; charset=UTF-8");
		try {
			LOG.info("开始同步数据...........................");
			client.executeMethod(method);
		} catch (HttpException e) {
			LOG.info("HttpException----------同步失败");
			e.printStackTrace();
		} catch (IOException e) {
			LOG.info("IOException------------------同步失败");
			e.printStackTrace();
		}
	}
}
