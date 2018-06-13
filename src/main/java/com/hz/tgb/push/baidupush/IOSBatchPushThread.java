package com.hz.tgb.push.baidupush;
import java.util.Map;

/**
 * IOS的批量推送线程，防止批量推送耗时过长。
 * @author weiguobin
 * @date 2015年4月20日
 */
public class IOSBatchPushThread extends Thread {
	private String[] channelIds;

	private String message;
	
	private int badge;
	
	private Map<String,String> params;
	
	private IOSPush push;
	
	/**
	 * 有参构造函数。
	 * @param push IOS推送实例
	 * @param channelIds channelId字符串数组
	 * @param message 通知内容
	 * @param badge 角标
	 * @param params 自定义参数，Map<String,String>，可为空
	 * @param params
	 */
	public IOSBatchPushThread(IOSPush push, String[] channelIds,String message,int badge,Map<String,String> params) {
		this.channelIds = channelIds;
		this.message = message;
		this.badge = badge;
		this.params = params;
		this.push = push;
	}
	
	@Override
	public void run()
	{
		push.pushNotificationToBatchDevice(channelIds, message, badge, params);
	}

}
