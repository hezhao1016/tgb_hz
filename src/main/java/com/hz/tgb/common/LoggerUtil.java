package com.hz.tgb.common;


/**
 * 日志管理类
 * @author hezhao
 * @Time   2016年10月28日 上午10:21:48
 * @Description 无
 * @Version V 1.0
 */
public class LoggerUtil {

	/**
	 * 进入方法打印日志
	 * @param clazz
	 * @return
	 */
	public static String intoMethod(Class clazz){
		String classStr = clazz.getSimpleName();  //获取类名
		StackTraceElement[] stacks = new Throwable().getStackTrace();  //运行数组
		String methodStr = stacks[1].getMethodName();  //获取运行的方法名
		
		//返回拼接字符串
		return "=============go into "+classStr+"::"+methodStr;
	}
	
	/**
	 * 出方法打印日志
	 * @param clazz
	 * @return
	 */
	public static String outMethod(Class clazz){
		String classStr = clazz.getSimpleName();  //获取类名
		StackTraceElement[] stacks = new Throwable().getStackTrace();  //运行数组
		String methodStr = stacks[1].getMethodName();  //获取运行的方法名
		
		//返回拼接字符串
		return "=============go out "+classStr+"::"+methodStr;
	}

	public static void main(String[] args) {
		System.out.println(intoMethod(LoggerUtil.class));
	}

}
