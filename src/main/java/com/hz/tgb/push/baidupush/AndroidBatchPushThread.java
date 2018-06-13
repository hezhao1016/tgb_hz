package com.hz.tgb.push.baidupush;

import java.util.Map;

/**
 * 安卓的批量推送线程，防止批量推送耗时过长。
 * @author weiguobin
 * @date 2015年4月20日
 */
public class AndroidBatchPushThread extends Thread {
	private String[] channelIds;
	
	private String message;
	
	private String title;
	
	private Map<String,String> params;
	
	private AndroidPush push;

	/**
	 * 有参构造函数。
	 * @param push 安卓推送实例
	 * @param channelIds channelId字符串数组
	 * @param message 通知内容
	 * @param title 通知标题，可为空
	 * @param params 自定义参数，Map<String,String>，可为空
	 * @param params
	 */
	public AndroidBatchPushThread(AndroidPush push, String[] channelIds,String message,String title,Map<String,String> params) {
		this.channelIds = channelIds;
		this.message = message;
		this.title = title;
		this.params = params;
		this.push = push;
	}

	@Override
	public void run()
	{
		push.pushNotificationToBatchDevice(channelIds, message, title, params);
	}
}
