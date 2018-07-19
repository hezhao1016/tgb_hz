package com.hz.tgb.tool;

import java.util.ResourceBundle;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 发送短信工具类。
 * 使用的是luosimao提供的短信服务。
 * 依赖：jersey-bundle-1.19.jar json-org.jar。
 * @author WeiGuobin
 */
public class SMSUtil {
	private static final Logger logger = LoggerFactory.getLogger(SMSUtil.class);

	private static String apiKey;

	private static String smsRequestPath;

	static {
		loadConfig();
	}

	private static void loadConfig() {
		ResourceBundle bundle = ResourceBundle.getBundle("config");

		try {
			apiKey = bundle.getString("sms_apiKey");

			smsRequestPath = bundle.getString("sms_requestPath");
		} catch (Exception e) {
			logger.error("获取不到在config.properties短信网关配置: sms_apiKey、sms_requestPath！");
		}

		if(StringUtils.isBlank(apiKey)) {
			logger.error("获取不到短信网关配置: sms_apiKey！");
		}

		if(StringUtils.isBlank(smsRequestPath)) {
			logger.error("获取不到短信网关配置: sms_requestPath！");
		}
	}

	/**
	 * 使用luosimao短信服务发送短信。
	 * @param mobile 目的手机号码
	 * @param message 短信内容
	 * @return boolean true：发送成功；false：发送失败
	 * @throws Exception
	 */
	public static boolean sendSMS(String mobile,String message) {
		logger.debug("发送短信到：" + mobile);
		boolean sendResult = false;

		String httpResponse = sendMsg(mobile, message,apiKey,smsRequestPath);

		logger.debug("发送短信返回结果描述：" + httpResponse);

		try {
			JSONObject jsonObj = new JSONObject(httpResponse);
			int error_code = jsonObj.getInt("error");
			String error_msg = jsonObj.getString("msg");

			if(error_code==0) {
				//发送成功
				sendResult = true;
				logger.debug("短信发送成功。");
			} else {
				logger.error("发送短信失败，结果码："+ error_code + "，结果描述："+ error_msg);
			}
		} catch (JSONException ex) {
			logger.debug("发送短信失败，发送时出现异常！异常：" + ex.getMessage());
			logger.error(ex.getMessage());
		}
		return sendResult;
	}

	/**
	 * 使用luosimao短信服务发送短信。
	 * @param mobile 目的手机号码
	 * @param message 短信内容
	 * @param apiKey 短信服务中心的秘钥，必须是final
	 * @param smsRequestPath 短信服务中心请求路径
	 * @return boolean true：发送成功；false：发送失败
	 * @throws Exception
	 */
	public static boolean sendSMS(String mobile,String message,final String c_apiKey,String c_smsRequestPath) throws Exception {
		logger.debug("发送短信到：" + mobile);
		boolean sendResult = false;

		String httpResponse = sendMsg(mobile, message,c_apiKey,c_smsRequestPath);

		logger.debug("发送短信返回结果描述：" + httpResponse);

		try {
			JSONObject jsonObj = new JSONObject(httpResponse);
			int error_code = jsonObj.getInt("error");
			String error_msg = jsonObj.getString("msg");

			if(error_code==0) {
				//发送成功
				sendResult = true;
				logger.debug("短信发送成功。");
			} else {
				logger.error("发送短信失败，结果码："+ error_code + "，结果描述："+ error_msg);
			}
		} catch (JSONException ex) {
			logger.debug("发送短信失败，发送时出现异常！异常：" + ex.getMessage());
			logger.error(ex.getMessage());
		}
		return sendResult;
	}

	/**
	 * 获取剩余短信数量。
	 *
	 * @return int 小于0表示获取失败。
	 */
	public static int queryDepoist() {
		int leftAmount = -1;

		String httpResponse =  queryMsgAmount();

		logger.debug("查询短信剩余数量返回结果描述：" + httpResponse);

		try {
			JSONObject jsonObj = new JSONObject( httpResponse );
			int error_code = jsonObj.getInt("error");
			if( error_code == 0 ) {
				leftAmount = jsonObj.getInt("deposit");
				logger.debug("获取短信剩余数量成功，剩余短信："+ leftAmount +"条。");
			} else {
				String error_msg = jsonObj.getString("msg");
				logger.error("获取剩余短信数量失败，结果码："+ error_code + "，结果描述："+ error_msg);
			}
		} catch (JSONException ex) {
			logger.debug("获取短信剩余数量时出现异常！异常：" + ex.getMessage());
			logger.error(ex.getMessage());
		}

		return leftAmount;
	}

	/**
	 * 发送短信，返回接口信息。
	 *
	 * @param mobile
	 * @param message
	 * @param c_apiKey
	 * @param c_smsRequestPath
	 * @return
	 */
	private static String sendMsg(String mobile,String message,String c_apiKey,String c_smsRequestPath) {
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter("api",c_apiKey));
		WebResource webResource = client.resource(c_smsRequestPath);

		MultivaluedMapImpl formData = new MultivaluedMapImpl();
		formData.add("mobile", mobile);
		formData.add("message", message);

		ClientResponse response =  webResource.type( MediaType.APPLICATION_FORM_URLENCODED ).post(ClientResponse.class, formData);

		String textEntity = response.getEntity(String.class);
		//int status = response.getStatus();
		//System.out.print(textEntity);
		//System.out.print(status);
		return textEntity;
	}

	/**
	 * 获取luosimao短信平台的剩余短信数量。
	 *
	 * @param apiKey
	 * @param smsRequestPath
	 * @return
	 */
	private static String queryMsgAmount() {
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter("api",apiKey));
		WebResource webResource = client.resource(smsRequestPath);

		ClientResponse response =  webResource.get(ClientResponse.class);
		String textEntity = response.getEntity(String.class);
		//int status = response.getStatus();
		//System.out.print(status);
		//System.out.print(textEntity);
		return textEntity;
	}
}
