/*
 * Copyright erong software, Inc. All rights reserved.
 * SHENZHEN ERONG SOFTWARE CO.,LTD. WWW.ERONGSOFT.COM
 */

package com.hz.tgb.tool;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;



/**
 * @author joshuaxu
 *
 */
public class DataUtil {
	
	/**
	 * url编码
	 * @author hezhao
	 * @Time   2017年8月2日 上午11:34:57
	 * @param urlString
	 * @return
	 * @throws Exception
	 */
	public static String encodeUrl(String urlString) throws Exception {
		return URLEncoder.encode(urlString,"UTF-8");
	}
	
	/**
	 * 后补空格
	 * @param str
	 * @param len
	 * @return
	 * @throws Exception
	 */
	public static String  fixLenAppendSpace(String str, int len) throws Exception {
		byte bytes[]=str.getBytes("GBK");
		byte fixLenBytes[]=new byte[len];
		if (bytes.length>=fixLenBytes.length)
			return str;
		
		int i=0;
		for (;i<bytes.length;i++)
			fixLenBytes[i]=bytes[i];
		
		for (;i<fixLenBytes.length;i++)
			fixLenBytes[i]=' ';
		
		return new String(fixLenBytes,"GBK");
	}
	
	/**
	 * 前填充0
	 * @param str
	 * @param len
	 * @return
	 * @throws Exception
	 */
	public static String  fixLenInsertZero(String str, int len) throws Exception {
		byte bytes[]=str.getBytes("GBK");
		byte fixLenBytes[]=new byte[len];
		if (bytes.length>=fixLenBytes.length)
			return str;
		
		int i=0;
		for (;i<bytes.length;i++)
			fixLenBytes[len-i-1]=bytes[bytes.length-i-1];
		
		for (;i<fixLenBytes.length;i++)
			fixLenBytes[len-i-1]='0';
		
		return new String(fixLenBytes,"GBK");
	}
	
	/**
	 * 获取正确的where语句
	 * @param where
	 * @return
	 */
	public static String  getWhereString(String where) {
		if (where.equals("where "))
			return "";
		
		String end=where.substring(where.length()-4, where.length());
		if (end.equals("and "))
			return where.substring(0, where.length()-4);
		else 
			return where;
	}
	
	
	/**
	 * 获得字符真实长度，以一个中文字符2个字节来算
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static int getLength(String str) throws Exception
	{
		if(str == null)
			return 0;
		if(str == "")
			return 0;
		
//		char[] chars=str.toCharArray();
//		for (int i=0;i<chars.length;i++){
//			System.out.println(chars[i]);
//		}

		return str.getBytes("GBK").length;
	}
	
	public static int byte2Int(byte b) {
		return b <0?(int)(b+256):(int)b;
	}
	
	/**
	 * 截取
	 * @param str
	 * @param beginIndex
	 * @param length
	 * @return
	 * @throws Exception
	 */
	public static String substring(String str, int beginIndex, int length) throws Exception
	{
		if (str==null)
			return "";
		
		int strLen=getLength(str);
		int countLen=beginIndex+length;
		
		if (beginIndex<0) return "";
		if (beginIndex>strLen) return "";
		if (length<=0) return "";
		if (countLen>strLen) return "";
		
		boolean isBeginChecked=false;
		int subIndex=0;
		byte subbytes[]=new byte[length];
		
		byte bytes[]=str.getBytes("GBK");
		for (int i = 0; i < bytes.length; ) {
			if (!isBeginChecked){
				if (i==beginIndex)
					isBeginChecked=true;
				
				if (i>beginIndex)
					throw new Exception("第"+i+"个字符不符合规格H["+str+"]");
			}
			
			if (i>=beginIndex){
				subbytes[subIndex]=bytes[i];
				int a=byte2Int(bytes[i]);
				if (a>=128){
					subbytes[subIndex+1]=bytes[i+1];
					subIndex++;
					i++;
				}
				subIndex++;
				i++;
			}
			else {
				i++;
			}
			
			if (i>countLen) 
				throw new Exception("第"+i+"个字符不符合规格H["+str+"]");
			if (i==countLen)
				break;
		}
		
		return new String(subbytes,"GBK");
	}
	
	
	
	 public static void main(String[] args)   throws Exception
	    {   
		 StringBuffer stringBuffer=new StringBuffer();
		 stringBuffer.append('c');
		 stringBuffer.append('日');
		 System.out.println(stringBuffer.toString());
	    	String str="啊asdf中b国cr";
	    	byte[] b= str.getBytes("GBK");
	    	System.out.println("b.length=["+b.length+"]");
	    	System.out.println("str.length=["+str.length()+"]");
	    	System.out.println("str.substring(3,7)=["+str.substring(3,7)+"]");
//	        System.out.println(getLength(str));
	        System.out.println("["+substring(str,3,5)+"]");
	        System.out.println("["+getWhereString("where 1=1 and ")+"]");
	        System.out.println("["+fixLenInsertZero("哈哈",10)+"]");
	    }   
	
}
