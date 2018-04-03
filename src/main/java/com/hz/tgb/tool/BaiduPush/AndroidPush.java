package com.hz.tgb.tool.BaiduPush;

import java.util.ArrayList;
import java.util.List;
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
import com.baidu.yun.push.model.PushBatchUniMsgRequest;
import com.baidu.yun.push.model.PushBatchUniMsgResponse;
import com.baidu.yun.push.model.PushMsgToAllRequest;
import com.baidu.yun.push.model.PushMsgToAllResponse;
import com.baidu.yun.push.model.PushMsgToSingleDeviceRequest;
import com.baidu.yun.push.model.PushMsgToSingleDeviceResponse;

/**
 * 安卓百度云推送。
 * @author weiguobin
 * @date 2015年4月17日
 */
public class AndroidPush
{
	private String apiKey;
	
	private String secretKey;
	
	public AndroidPush(String apiKey, String secretKey)
	{
		this.apiKey	= apiKey;
		this.secretKey = secretKey;
	}
	
	/**
	 * 单点通知推送。
	 *
	 * @param channelId 应用的channelId 不可为空
	 * @param message 通知内容 不可为空
	 * @param title 通知标题，可为空
	 * @param params 自定义参数，Map<String,String>，可为空
	 * @return true：发送成功；false：发送失败
	 */
	public boolean pushMsgToSingleDevice(String channelId,String message,String title,Map<String,String> params)
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
			String notificationMsg = createNotificationMsg(title, message, params);
		
			PushMsgToSingleDeviceRequest request = new PushMsgToSingleDeviceRequest()
					.addChannelId(channelId)
					.addMsgExpires(new Integer(604800))  // message有效时间，单位秒，最大保留7天，可选
					.addMessageType(1)                   // 1：通知,0:透传消息. 默认为0 注：IOS只有通知.
					.addMessage(notificationMsg)
					.addDeviceType(3);                   // deviceType => 3:android, 4:ios
			
			// 5. http request
			PushMsgToSingleDeviceResponse response = pushClient.pushMsgToSingleDevice(request);
			
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
	 *
	 * @param channelIds channelId字符串数组,可以为空（不发送）
	 * @param message 通知内容，不可为空
	 * @param title 通知标题，可为空
	 * @param params 自定义参数，Map<String,String>，可为空
	 * @return true：发送成功；false：发送失败
	 */
	public boolean pushNotificationToBatchDevice(String[] channelIds,String message,String title,Map<String,String> params)
	{
		if(channelIds != null && channelIds.length>0)
		{
			// 1. get apiKey and secretKey from developer console
			PushKeyPair pair = new PushKeyPair(apiKey, secretKey);
			
			// 2. build a BaidupushClient object to access released interfaces
			BaiduPushClient pushClient = new BaiduPushClient(pair,
					BaiduPushConstants.CHANNEL_REST_URL);
			
			// 3. register a YunLogHandler to get detail interacting information
			// in this request.
			pushClient.setChannelLogHandler(new YunLogHandler() 
			{
				@Override
				public void onHandle(YunLogEvent event) {
					System.out.println(event.getMessage());
				}
			});
			
			try 
			{
				//批量推送，一次最多一万个channelId
				List<String[]> channelList = new ArrayList<String[]>();
				int length = channelIds.length;
				
				if(length>10000)
				{
					String[] temp = null;
					int lastCircleLength = length%10000;
					int circle = length/10000;
					
					for(int i = 0;i<=circle;i++)
					{
						temp = new String[10000];
						System.arraycopy(channelIds, (i-1)*10000, temp, 0, 10000);
						channelList.add(temp);
					}
					
					//不能让channel数组有空值，需要对非10000倍数的最后一轮做特殊处理
					if(lastCircleLength>0)
					{
						temp = new String[lastCircleLength];
						System.arraycopy(channelIds, circle*10000, temp, 0, lastCircleLength);
						channelList.add(temp);
					}
				}
				else
				{
					channelList.add(channelIds);
				}
				
				for(String[] s: channelList)
				{
					// 4. specify request arguments
					String notificationMsg = createNotificationMsg(title, message, params);
				
					PushBatchUniMsgRequest request = new PushBatchUniMsgRequest()
						.addChannelIds(s)
						.addMsgExpires(new Integer(86400))
						.addMessageType(1)
						.addMessage(notificationMsg)
						.addDeviceType(3);
					
					// 5. http request
					PushBatchUniMsgResponse response = pushClient.pushBatchUniMsg(request);
					
					// Http请求结果解析打印
					System.out.println("msgId: " + response.getMsgId() + ",sendTime: "
							+ response.getSendTime());
				}
				
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
		}
		else
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * 推送通知给所有安卓设备。
	 *
	 * @param message 通知内容 不可为空
	 * @param title 通知标题，可为空
	 * @param params 自定义参数，Map<String,String>，可为空
	 * @return true：发送成功；false：发送失败
	 */
	public boolean pushMsgToAll(String message,String title,Map<String,String> params) 
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
			//创建 Android的通知
			String notificationMsg = createNotificationMsg(title, message, params);
			
			// 4. specify request arguments
			PushMsgToAllRequest request = new PushMsgToAllRequest()
					.addMsgExpires(new Integer(604800))
					.addMessage(notificationMsg) //添加透传消息
					.addDeviceType(3)
					.addMessageType(1);
			
			// 5. http request
			PushMsgToAllResponse response = pushClient.pushMsgToAll(request);
			
			// Http请求结果解析打印
			System.out.println("msgId: " + response.getMsgId() + ",sendTime: "
					+ response.getSendTime() + ",timerId: "
					+ response.getTimerId());
			
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
	
	private String createNotificationMsg(String title, String message, Map<String,String> params)
	{
		//创建 Android的通知
		JSONObject notification = new JSONObject();
		if(title != null){notification.put("title", title);} //可选，如果为空则设为appid对应的应用名
		notification.put("description",message);             //必选，通知的文本内容
		notification.put("open_type", 3);
		
		//自定义内容，key-value
		if(params != null)
		{
			Set<String> keys = params.keySet();
			
			if(keys != null)
			{
				JSONObject jsonCustormCont = new JSONObject();
				
				for(String s: keys)
				{
					jsonCustormCont.put(s, params.get(s)); 
				}
				
				notification.put("custom_content", jsonCustormCont);
			}
		}
		
		return notification.toString();
	}
	
	
	
//	public static void main(String[] args)
//	{
//		String apiKey = "g7ETW2pEq32iGGDQjzOBUUg4";
//		String secretKey = "jAVORVjnk0GzsdoXLZFPuOGEas8fZwnY";
//		
//		AndroidPush ap = new AndroidPush(apiKey, secretKey);
//		String[] channelIds = new String[]{"3970302060691219684","4394195520088827814"};
//		
//		System.out.println(ap.pushMsgToSingleDevice("4394195520088827814", "测试", "test", null));
//		System.out.println(ap.pushNotificationToBatchDevice(channelIds, "测试", "test", null));
//		System.out.println(ap.pushMsgToAll("哈哈哈哈", null, null));
//	}
}
