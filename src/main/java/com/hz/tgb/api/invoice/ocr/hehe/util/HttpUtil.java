package com.hz.tgb.api.invoice.ocr.hehe.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * HTTP工具类
 *
 * Created by hezhao on 2018/9/25 17:19
 */
public class HttpUtil {

    /**
     * POST请求
     *
     * @param reqUrl 请求地址
     * @param postMap 请求参数
     * @return
     * @throws IOException
     */
    public static String doPost(String reqUrl, Map<String, Object> postMap) throws Exception {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(reqUrl);
            conn = (HttpURLConnection) url.openConnection(); // 新建连接实例
            conn.setConnectTimeout(30000); // 设置连接超时时间，单位毫秒
            conn.setReadTimeout(30000); // 设置读取数据超时时间，单位毫秒
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*;charset=UTF-8");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true); // 是否打开输出流 true|false
            conn.setDoInput(true); // 是否打开输入流true|false
            conn.setRequestMethod("POST"); // 提交方法POST|GET
            conn.setUseCaches(false); // 是否缓存true|false
            conn.connect();// 打开连接端口

            // 发送文本数据
            sendTextData(conn.getOutputStream(), postMap);

            // 响应码
            int code = conn.getResponseCode();
            // System.out.println("code="+code+ " url="+url);

            if (code == 200) {
                // 获取响应文本
                String content = readRespContent(conn.getInputStream());
                return content;
            } else {
                throw new RuntimeException("响应码错误:" + code);
            }
        } finally {
            if (conn != null) {
                conn.disconnect();// 关闭连接
            }
        }
    }

    /**
     * POST请求 - JSON
     *
     * @param reqUrl 请求地址
     * @param json 请求参数, json字符串
     * @return
     * @throws IOException
     */
    public static String doPostJSON(String reqUrl, String json) throws Exception {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(reqUrl);
            conn = (HttpURLConnection) url.openConnection(); // 新建连接实例
            conn.setConnectTimeout(30000); // 设置连接超时时间，单位毫秒
            conn.setReadTimeout(30000); // 设置读取数据超时时间，单位毫秒
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*;charset=UTF-8");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setRequestProperty("content-type", "application/json");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true); // 是否打开输出流 true|false
            conn.setDoInput(true); // 是否打开输入流true|false
            conn.setRequestMethod("POST"); // 提交方法POST|GET
            conn.setUseCaches(false); // 是否缓存true|false
            conn.connect();// 打开连接端口

            // 发送JSON数据
            sendTextData(conn.getOutputStream(), json);

            // 响应码
            int code = conn.getResponseCode();
            // System.out.println("code="+code+ " url="+url);

            if (code == 200) {
                // 获取响应文本
                String content = readRespContent(conn.getInputStream());
                return content;
            } else {
                throw new RuntimeException("响应码错误:" + code);
            }
        } finally {
            if (conn != null) {
                conn.disconnect();// 关闭连接
            }
        }
    }

    /**
     * POST发送文件
     *
     * @param reqUrl 请求地址
     * @param file 文件
     * @return
     * @throws IOException
     */
    public static String doPostFile(String reqUrl, File file) throws Exception {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(reqUrl);
            conn = (HttpURLConnection) url.openConnection(); // 新建连接实例
            conn.setConnectTimeout(30000); // 设置连接超时时间，单位毫秒
            conn.setReadTimeout(30000); // 设置读取数据超时时间，单位毫秒
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true); // 是否打开输出流 true|false
            conn.setDoInput(true); // 是否打开输入流true|false
            conn.setRequestMethod("POST"); // 提交方法POST|GET
            conn.setUseCaches(false); // 是否缓存true|false

            // 发送二进制数据
            sendByteData(conn.getOutputStream(), file);

            // 响应码
            int code = conn.getResponseCode();
            // System.out.println("code="+code+ " url="+url);

            if (code == 200) {
                // 获取响应文本
                String content = readRespContent(conn.getInputStream());
                return content;
            } else {
                throw new RuntimeException("响应码错误:" + code);
            }
        } finally {
            if (conn != null) {
                conn.disconnect();// 关闭连接
            }
        }
    }

    /**
     * 发送二进制数据
     * @param out 输出流
     * @param file 文件
     * @throws IOException
     */
    private static void sendByteData(OutputStream out, File file) throws IOException {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            byte[] data = new byte[2048];
            int len = 0;
            int sum = 0;
            while ((len = inputStream.read(data))!= -1) {
                out.write(data, 0, len);
                sum = len + sum;
            }
            // System.out.println("upload size="+sum);
            out.flush(); // 刷新
        } finally {
            if (inputStream != null) {
                inputStream.close();// 关闭输入流
            }
            if (out != null) {
                out.close();// 关闭输出流
            }
        }
    }

    /**
     * 发送文本数据
     * @param outputStream 输出流
     * @param postMap 请求参数
     * @throws IOException
     */
    private static void sendTextData(OutputStream outputStream, Map<String, Object> postMap) throws IOException {
        // 发生文本数据
        String param = "";
        for (String key : postMap.keySet()) {
            if (param.length() > 0) {
                param += "&";
            }
            param += key + "=" + postMap.get(key);
        }

        sendTextData(outputStream, param);
    }

    /**
     * 发送文本数据
     * @param outputStream 输出流
     * @param param 请求参数
     * @throws IOException
     */
    private static void sendTextData(OutputStream outputStream, String param) throws IOException {
        DataOutputStream out = null;
        try {
            out = new DataOutputStream(outputStream); // 打开输出流往对端服务器写数据
            out.writeBytes(param); // 写数据,也就是提交你的表单 name=xxx&pwd=xxx
            out.flush(); // 刷新
        } finally {
            if (out != null) {
                out.close();// 关闭输出流
            }
        }
    }

    /**
     * 获取响应文本
     * @param inputStream 输入流
     * @return
     * @throws IOException
     */
    private static String readRespContent(InputStream inputStream) throws IOException {
        BufferedReader reader = null;
        try {
            // 往对端写完数据对端服务器返回数据,以BufferedReader流来读取
            reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            final StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            return buffer.toString();
        } finally {
            if (reader != null) {
                reader.close(); // 关闭BufferedReader
            }
        }
    }

}
