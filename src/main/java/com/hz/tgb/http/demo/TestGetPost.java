package com.hz.tgb.http.demo;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class TestGetPost {
	
	private static final Logger logger = LoggerFactory.getLogger(TestGetPost.class);
	
	/** 
     * 向指定URL发送GET方法的请求 
     *  
     * @param url 
     *            发送请求的URL 
     * @param param 
     *            请求参数，请求参数应该是name1=value1&name2=value2的形式。 
     * @return URL所代表远程资源的响应 
     */  
    public static String sendGet(String url, String param) {  
        String result = "";  
        BufferedReader in = null;
        try {  
            String urlName = url + "?" + param;  
            URL realUrl = new URL(urlName);
            // 打开和URL之间的连接  
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性  
            conn.setRequestProperty("accept", "*/*");  
            conn.setRequestProperty("connection", "Keep-Alive");  
            conn.setRequestProperty("user-agent",  
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");  
            // 建立实际的连接  
            conn.connect();  
            // 获取所有响应头字段  
            Map<String, List<String>> map = conn.getHeaderFields();
            // 遍历所有的响应头字段  
            for (String key : map.keySet()) {  
                System.out.println(key + "--->" + map.get(key));  
            }  
            // 定义BufferedReader输入流来读取URL的响应  
            in = new BufferedReader(  
                    new InputStreamReader(conn.getInputStream()));
            String line;  
            while ((line = in.readLine()) != null) {  
                result += "/n" + line;  
            }  
        } catch (Exception e) {  
            logger.error("发送GET请求出现异常！" + e); 
			logger.error(e.toString(), e); 
            e.printStackTrace();  
        }  
        // 使用finally块来关闭输入流  
        finally {  
            try {  
                if (in != null) {  
                    in.close();  
                }  
            } catch (IOException ex) {
    			logger.error(ex.toString(), ex);
                ex.printStackTrace();  
            }  
        }  
        return result;  
    }  
    /**  
     * 向指定URL发送POST方法的请求  
     *   
     * @param url  
     *            发送请求的URL  
     * @param param  
     *            请求参数，请求参数应该是name1=value1&name2=value2的形式。  
     * @return URL所代表远程资源的响应  
     */  
    public static String sendPost(String url, String param) {  
        PrintWriter out = null;
        BufferedReader in = null;  
        String result = "";  
        try {  
            URL realUrl = new URL(url);  
            // 打开和URL之间的连接  
            URLConnection conn = realUrl.openConnection(); 
           // conn.setRequestProperty("Content-Type", "*/*;charset=UTF-8");  
            // 设置通用的请求属性  
           conn.setRequestProperty("accept", "*/*;charset=UTF-8");  
            conn.setRequestProperty("connection", "Keep-Alive");  
            conn.setRequestProperty("user-agent",  
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");  
            // 发送POST请求必须设置如下两行  
            conn.setDoOutput(true);  
            conn.setDoInput(true);  
            // 获取URLConnection对象对应的输出流  
            out = new PrintWriter(conn.getOutputStream());  
            // 发送请求参数  
            out.print(param);  
            // flush输出流的缓冲   
            out.flush();  
            // 定义BufferedReader输入流来读取URL的响应  
            in = new BufferedReader(  
                    new InputStreamReader(conn.getInputStream()));  
            String line;  
            while ((line = in.readLine()) != null) {  
                result += line;  
            }  
        } catch (Exception e) {  
            logger.error("发送POST请求出现异常！" + e);  
			logger.error(e.toString(), e);
            e.printStackTrace();  
        }  
        // 使用finally块来关闭输出流、输入流  
        finally {  
            try {  
                if (out != null) {  
                    out.close();  
                }  
                if (in != null) {  
                    in.close();  
                }  
            } catch (IOException ex) {  
    			logger.error(ex.toString(), ex);
                ex.printStackTrace();  
            }  
        }  
        return result;  
    }  
    
    // 提供主方法，测试发送GET请求和POST请求  
    public static void main(String args[])  {  
		//发送POST请求  121.40.54.146
	    //String s1 = TestGetPost.sendPost("http://192.168.10.235:8080/bishop/activity/inf/v1.0.1/findStockByGrouponId" ,"grouponId=0001");  //"memberId=1450424922718114997&addrId=654321&addrCode=123456&address=123456&isDefault=0&name=Mr.Liu&phone=18212345678&status=1"
	    //String s1 = TestGetPost.sendPost("http://121.40.240.16:8080/bishop/activity/inf/v1.0.1/findSeckillBySeckillId", "storeId=0001&seckillId=202");//&orderId=1451890582879972267
	    String s1 = TestGetPost.sendGet("http://192.168.10.235:8080/bishop/product/inf/v1.0.1/createOrder" ,"memberId=1460530269303476739&token=336f104dc70e318c76f9826de0494d8f&artNo=14617427330005989-0&count=1");//memberId=1450424922718114997&addrCode=123456&address=123456&isDefault=1&name=Mr.Liu&phone=18212345678&postCode=&tel=
		//String s1 = TestGetPost.sendGet("http://192.168.10.235:8080/bishop/product/inf/v1.0.1/findSpecByProductId", "productId=10000");//&artNo=123456&count=2
	    System.out.println(s1); 
		ObjectMapper om = new ObjectMapper();
	}  
}