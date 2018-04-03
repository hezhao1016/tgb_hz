package com.hz.tgb.tool.BaiduPush;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.PushMsgToAllRequest;
import com.baidu.yun.push.model.PushMsgToAllResponse;
import com.baidu.yun.push.model.PushMsgToSingleDeviceRequest;
import com.baidu.yun.push.model.PushMsgToSingleDeviceResponse;

/**
 * 苹果系统百度推送。
 * @author weiguobin
 * @date 2015年4月17日
 */
public class IOSPush 
{
	private String apiKey;
	
	private String secretKey;
	
	/**
	 * IOS必须要有的 DeployStatus => 1: Developer 2: Production.
	 * 正式发布需要改成2.
	 */
	private int deployStatus = 1;
	
	public IOSPush(String apiKey, String secretKey)
	{
		this.apiKey	= apiKey;
		this.secretKey = secretKey;
	}
	
	/**
	 * 单点推送通知。
	 *
	 * @param channelId 应用的channelId
	 * @param message 通知内容
	 * @param badge 通知角标
	 * @param params 自定义参数，Map<String,String>，可为空
	 * @return true：发送成功；false：发送失败
	 */
	public boolean pushNotificationToSingleDevice(String channelId,String message,int badge,Map<String,String> params)
	{
		PushKeyPair pair = new PushKeyPair(apiKey, secretKey);

		// 2. build a BaidupushClient object to access released interfaces
		BaiduPushClient pushClient = new BaiduPushClient(pair,
				BaiduPushConstants.CHANNEL_REST_URL);

		// 3. register a YunLogHandler to get detail interacting information
		// in this request.
		pushClient.setChannelLogHandler(new YunLogHandler() 
		{
			@Override
			public void onHandle(YunLogEvent event)
			{
				System.out.println(event.getMessage());
			}
		});

		try {
			// 4. specify request arguments
			// make IOS Notification
			String notificationMsg = createNotificationMsg(message, badge, params);

			PushMsgToSingleDeviceRequest request = new PushMsgToSingleDeviceRequest()
					.addChannelId(channelId)
					.addMsgExpires(new Integer(604800))   // 设置message的有效时间
					.addMessageType(1)                    // 1：通知,0:透传消息.默认为0 注：IOS只有通知.
					.addMessage(notificationMsg)
					.addDeployStatus(deployStatus)     // IOS必须要有的 DeployStatus => 1: Developer 2: Production.
					.addDeviceType(4);                    // deviceType 3:android, 4:ios
			
			// 5. http request
			PushMsgToSingleDeviceResponse response = pushClient
					.pushMsgToSingleDevice(request);
			
			// Http请求结果解析打印
			System.out.println("msgId: " + response.getMsgId() + ",sendTime: "
					+ response.getSendTime());
			
			//走到这里没有异常，视为成功
			return true;
		} 
		catch (PushClientException e)
		{
			e.printStackTrace();
		} 
		catch (PushServerException e)
		{
			System.out.println(String.format(
					"requestId: %d, errorCode: %d, errorMessage: %s",
					e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
		}
		
		return false;
	}
	
	/**
	 * 推送通知给批量设备。
	 * 注意，本方法只是循环单播，百度云推送不支持批量IOS通知。
	 * @param channelIds channelId的字符串数组
	 * @param message 通知内容
	 * @param badge 通知角标
	 * @param params 自定义参数，Map<String,String>，可为空
	 */
	public void pushNotificationToBatchDevice(String[] channelIds,String message,int badge,Map<String,String> params)
	{
		if(channelIds != null)
		{
			int length = channelIds.length;
			
			for(int i=0; i<length;i++)
			{
				this.pushNotificationToSingleDevice(channelIds[i], message, badge, params);
			}
		}
	}
	
	/**
	 * 推送通知给所有设备。
	 *
	 * @param message 通知内容
	 * @param badge 通知角标
	 * @param params 自定义参数，Map<String,String>，可为空
	 * @return
	 */
	public boolean pushNotificationToAll(String message,int badge,Map<String,String> params)
	{
		// 1. get apiKey and secretKey from developer console
		PushKeyPair pair = new PushKeyPair(apiKey, secretKey);

		// 2. build a BaidupushClient object to access released interfaces
		BaiduPushClient pushClient = new BaiduPushClient(pair,BaiduPushConstants.CHANNEL_REST_URL);

		// 3. register a YunLogHandler to get detail interacting information
		// in this request.
		pushClient.setChannelLogHandler(new YunLogHandler()
		{
			@Override
			public void onHandle(YunLogEvent event)
			{
				System.out.println(event.getMessage());
			}
		});

		try 
		{
			// 4. specify request arguments
			// 创建IOS通知
			String notificationMsg = createNotificationMsg(message, badge, params);
			
			PushMsgToAllRequest request = new PushMsgToAllRequest()
					.addMsgExpires(new Integer(604800))  // 设置message的有效时间
					.addMessageType(1)                   // 1：通知,0:透传消息.默认为0 注：IOS只有通知.
					.addMessage(notificationMsg)
					.addDepolyStatus(deployStatus)       // IOS必须要有的 DeployStatus => 1: Developer 2: Production.
					.addDeviceType(4);                   // deviceType => 3:android, 4:ios
			
			// 5. http request
			PushMsgToAllResponse response = pushClient.pushMsgToAll(request);
			
			// Http请求结果解析打印
			System.out.println("msgId: " + response.getMsgId() + ",sendTime: "
					+ response.getSendTime() + ",timerId: "
					+ response.getTimerId());
			
			//走到这里没有异常，视为成功
			return true;
		} 
		catch (PushClientException e)
		{
			e.printStackTrace();
		} 
		catch (PushServerException e)
		{
			System.out.println(String.format(
					"requestId: %d, errorCode: %d, errorMessage: %s",
					e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
		}
		
		return false;
	}
	
	private String createNotificationMsg(String message,int badge,Map<String,String> params)
	{
		// 创建IOS通知内容
		JSONObject notification = new JSONObject();
		JSONObject jsonAPS = new JSONObject();
		jsonAPS.put("alert", message);
		jsonAPS.put("badge", badge);                   //设置角标，可选
		jsonAPS.put("sound", "default");               //提示声音
		notification.put("aps", jsonAPS);
		
		//自定义内容，key-value
		if(params != null)
		{
			Set<String> keys = params.keySet();
			
			if(keys != null)
			{
				for(String s: keys)
				{
					notification.put(s, params.get(s));
				}
			}
		}
		
		return notification.toString();
	}
	
	
	public static void main(String[] args)
	{
		String apiKey = "5mLFz4GexrzeGxkzVVB8H6uy";
		String secretKey = "VUlMnDfD9aLlYpF9vCBfoF1WwhzgDOGQ";
		
		IOSPush ip = new IOSPush(apiKey, secretKey);
		Map<String,String> params = new HashMap<String,String>();
		
		params.put("aa", "bbb");
		
//		ip.pushNotificationToAll("测试", 2, null);
		ip.pushNotificationToSingleDevice("5399853976388502227", "批量单薄测试", 0,params );
//		ip.pushNotificationToBatchDevice(new String[]{"5327487257296057820"}, "批量单薄测试", 0,params);
	}
}
