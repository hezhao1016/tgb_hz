package com.hz.tgb.http;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class HttpSMS {
	private static final Logger logger = LoggerFactory.getLogger(HttpSMS.class);
	
	private static final String SMS_URL = "";
	
	public static boolean sendSMS(String phone, String code)  
    {  
        // 创建HttpClient实例     
        HttpClient httpclient = new DefaultHttpClient();
        // 创建Get方法实例     
        String content = "您的验证码是：【"+code+"】。请不要把验证码泄露给其他人。";
        logger.info("phone=" + phone + ";code=" + code);
        HttpGet httpgets = new HttpGet(SMS_URL+"?phone="+phone+"&content="+content);
        logger.info(SMS_URL);
        HttpResponse response = null;
        String str = null;
        boolean flag = false;
		try {
			response = httpclient.execute(httpgets);
			 HttpEntity entity =  response.getEntity();
		        if (entity != null) {    
		            InputStream instreams =  entity.getContent();
		            str = convertStreamToString(instreams);  
		            System.out.println("Do something");   
		            System.out.println(str);  
		            // Do not need the rest    
		            httpgets.abort();   
		            JSONObject json = JSONObject.fromObject(str);
		            String code1 = json.get("code").toString();
		            if(code1.equals("200")) flag = true;
		        }  
		} catch (IOException e) {
			logger.error(e.toString(),e);
			e.printStackTrace();
		}   
		return flag;
    }  
	
	/**
	 * 发送优惠券信息
	 * @param phone
	 * @param code
	 * @return
	 */
	public static boolean sendCouponSMS(String phone, String code, String mchtName, String productName)  
    {  
        // 创建HttpClient实例     
        HttpClient httpclient = new DefaultHttpClient();  
        // 创建Get方法实例     
        //尊敬的顾客您好，您已成功购买了一张【变量】，请凭验证码【变量】到指定商家消费使用。
        String content = "尊敬的顾客您好，您已成功购买了一张【" + productName + "】，请凭验证码【"+code+"】到指定商家消费使用。";
        logger.info("phone=" + phone + ";SMS content = " + content);
        HttpResponse response = null;
        String str = null;
        boolean flag = false;
		try {
			URL url = new URL(SMS_URL+"?phone="+phone+"&content="+content);
			URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);
			HttpGet httpgets = new HttpGet(uri);
//	        HttpGet httpgets = new HttpGet(CommonUtil.SMS_URL+"?phone="+phone+"&content="+content);    
			response = httpclient.execute(httpgets);
			 HttpEntity entity =  response.getEntity();    
		        if (entity != null) {    
		            InputStream instreams =  entity.getContent();    
		            str = convertStreamToString(instreams);  
		            System.out.println("Do something");   
		            System.out.println(str);  
		            // Do not need the rest    
		            httpgets.abort();   
		            JSONObject json = JSONObject.fromObject(str);
		            String code1 = json.get("code").toString();
		            if(code1.equals("200")) flag = true;
		        }  
		} catch (IOException e) {
			logger.error(e.toString(), e);
			e.printStackTrace();
		} catch (URISyntaxException e) {
			logger.error(e.toString(), e);
			e.printStackTrace();
		}   
		return flag;
    }  

	/**
	 * 发送退款信息
	 * @param phone
	 * @param code
	 * @return
	 */
	public static boolean sendRefundSMS(String phone, String orderId, String money, String finishTime)  
	{  
		// 创建HttpClient实例     
		HttpClient httpclient = new DefaultHttpClient();  
		String content = "尊敬的顾客您好，您申请的退款已处理成功，订单号【"+orderId+"】，退款金额【" +money+ "】，退款时间【"+finishTime+"】。请注意查看退款是否到账。";
		logger.info("phone=" + phone + ";SMS content = " + content);
		HttpResponse response = null;
		String str = null;
		boolean flag = false;
		try {
			URL url = new URL(SMS_URL+"?phone="+phone+"&content="+content);
			URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);
			// 创建Get方法实例     
			HttpGet httpgets = new HttpGet(uri);
			response = httpclient.execute(httpgets);
			HttpEntity entity =  response.getEntity();    
			if (entity != null) {    
				InputStream instreams =  entity.getContent();    
				str = convertStreamToString(instreams);  
				System.out.println("Do something");   
				System.out.println(str);  
				// Do not need the rest    
				httpgets.abort();   
				JSONObject json = JSONObject.fromObject(str);
				String code1 = json.get("code").toString();
	            if(code1.equals("200")) flag = true;
			}  
		} catch (IOException e) {
			logger.error(e.toString(), e);
			e.printStackTrace();
		} catch (URISyntaxException e) {
			logger.error(e.toString(), e);
			e.printStackTrace();
		}   
		return flag;
	}  
      
    public static String convertStreamToString(InputStream is) {      
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();      
       
        String line = null;      
        try {      
            while ((line = reader.readLine()) != null) {  
                sb.append(line + "\n");      
            }      
        } catch (IOException e) {      
			logger.error(e.toString(), e);  
            e.printStackTrace();      
        } finally {      
            try {      
                is.close();      
            } catch (IOException e) {      
    			logger.error(e.toString(), e);    
               e.printStackTrace();      
            }      
        }      
        return sb.toString();      
    } 
}
