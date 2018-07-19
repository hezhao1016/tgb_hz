package com.hz.tgb.http.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 接口调用类
 * @author hezhao
 */
public class ConnectionHelp {
	//------ 实例化一个日志类 ------
	private static Logger logger = LoggerFactory.getLogger(ConnectionHelp.class);
	//------ 密钥 ------
	public static final String PDK = "lmq";
	//========== 调用接口路径 start ==========

	//========== 调用接口路径 end ==========

	/**
	 * 连接指定指定地址并传递参数
	 * @param url		地址
	 * @param value		值
	 * @return	返回响应值
	 */
	public static String connectionForUrl(String url,String value){
		//JSONArray array = new JSONArray();
		//JSONObject jsonObject = new JSONObject();
		//========== 创建变量 start ==========
		//------ 保存返回结果 ------
		String result = "";
		//------ 输出流 ------
		OutputStreamWriter out = null;
		//------ 输入流 ------
		BufferedReader in = null;
		//========== 插件变量 end ==========

		//========== 调用接口 start ==========
		try{
			//========== 调用接口传输参数 satrt ==========
			//------ 与指定地址进行连接并交互 ------
			URL realUrl = new URL(url);
			//------ 打开和URL之间的连接 ------
			URLConnection conn = realUrl.openConnection();
			//------ 设置通用的请求属性(模拟浏览器连接并设置超时时间) ------
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setConnectTimeout(1000*60*1);
			//------ 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);

			//------设置为GET方式请求


			//------ 获取URLConnection对象对应的输入流 ------
			out = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
			//------ 发送请求属性 ------
			out.write(value);
			//------ 清除输出流的缓存 ------
			out.flush();
			//========== 调用接口传输参数 end ==========

			//========== 获取返回结果并执行相应操作  start ==========
			//------ 获取输入流 ------
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
			//------ 将输入流转为字符串 ------
			String line ;
			while((line = in.readLine()) != null) {
				result += line;
			}
			//========== 获取返回结果并执行相应操作 end ==========
		}catch(Exception e){
			//------ 日志 ------
			logger.error("error:{}", e);
			e.printStackTrace();
		}finally{
			try{
				if(out != null) out.close();
				if(in != null) in.close();
			}catch(IOException ioe){
				ioe.printStackTrace();
			}
		}
		//========== 调用接口 end ==========
		return result;
	}

	/**
	 * 格式化时间格式为 yyyy-MM-dd HH:mm:ss
	 * @param date 时间
	 * @return
	 */
	public static String funFormatDate(Date date){
		if(date != null) return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		else return null;
	}

	/**
	 * 将null值转为""
	 * @param str
	 * @return
	 */
	public static String funChangeNull(Object str){
		return str == null ? "" : str.toString();
	}

	/**
	 * 参数为空或null转为2，否则转为int类型返回
	 * @param str
	 * @return
	 */
	public static int funChangeNullForInt(Object str){
		//------ 如果整体处理结果为空或者为null，则表示处理失败，返回2 ------
		return str == null || "".equals(str+"")? 2 : Integer.parseInt(str+"");
	}

	/**
	 * 将字符串转为json
	 * @param str
	 * @return
	 */
	public static String funChangeObjectForJSON(Object str){
		//------ 如果整体处理结果为空或者为null，则表示处理失败，返回2 ------
		return str == null ? "[{}]" : str+"";
	}
}
