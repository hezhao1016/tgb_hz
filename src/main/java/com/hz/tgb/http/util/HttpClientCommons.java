package com.hz.tgb.http.util;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Http工具类commons实现
 * 
 * @author Yaphis 2015年6月26日 下午5:35:52
 */
public class HttpClientCommons {

    private HttpClientCommons() {

    }


    private static final Logger LOG = LoggerFactory.getLogger(HttpClientCommons.class);
    /**
     * Http Post
     * 
     * @param reqUrl 请求地址
     * @param postMap 请求参数
     * @param charset 字符编码
     * @return 接口返回
     * @throws HttpException
     * @throws IOException
     */
    public static String doPost(String reqUrl, Map<String, String> postMap, String charset) throws HttpException, IOException {
        PostMethod postMethod = new PostMethod(reqUrl);
        if (null != postMap && !postMap.isEmpty()) {
            Iterator<String> postMapIt = postMap.keySet().iterator();
            NameValuePair[] params = new NameValuePair[postMap.size()];
            List<NameValuePair> paramsList = new LinkedList<NameValuePair>();
            while (postMapIt.hasNext()) {
                String key = postMapIt.next();
                String value = postMap.get(key);
                paramsList.add(new NameValuePair(key, value));
            }
            postMethod.setRequestBody(paramsList.toArray(params));
        }
        LOG.info("reqUrl:{},postMap:{}", new Object[] { reqUrl, postMap });
        String response = execute(postMethod, charset);
        LOG.info("response:{}", response);
        return response;
    }

    /**
     * Http Get
     * 
     * @param reqUrl 请求地址
     * @param getMap 请求参数
     * @param charset 字符编码
     * @return 接口返回
     * @throws HttpException
     * @throws IOException
     */
    public static String doGet(String reqUrl, Map<String, String> getMap, String charset) throws HttpException, IOException {
        StringBuilder builder = new StringBuilder(reqUrl);
        if (null != getMap && !getMap.isEmpty()) {
            if (!reqUrl.contains("?")) {
                builder.append("?");
            }
            Iterator<String> getMapIt = getMap.keySet().iterator();
            boolean isFirst = true;
            while (getMapIt.hasNext()) {
                String key = getMapIt.next();
                String value = getMap.get(key);
                if (isFirst) {
                    builder.append(key).append("=").append(value);
                    isFirst = false;
                } else {
                    builder.append("&").append(key).append("=").append(value);
                }
            }
        }
        String fullUrl = builder.toString();
        GetMethod getMethod = new GetMethod(fullUrl);
        LOG.info("reqUrl:{}", new Object[] { fullUrl });
        String response = execute(getMethod, charset);
        LOG.info("response:{}", response);
        return response;
    }

    /**
     * 执行http请求
     * 
     * @param httpMethod
     * @param charset
     * @return
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    private static String execute(HttpMethod httpMethod, String charset) throws UnsupportedEncodingException, IOException {
        HttpClient httpClient = new HttpClient();
        int statusCode = httpClient.executeMethod(httpMethod);
        if (statusCode != HttpStatus.SC_OK) {
            LOG.error("executeMethod statusCode:{},does not equals 200!", statusCode);
            throw new HttpException("doPost fail");
        } else {
            String responseBody = new String(httpMethod.getResponseBody(), charset);// 响应体信息
            if (StringUtils.isNotBlank(responseBody)) {
                LOG.info("responseBody:{}", responseBody);
                return responseBody;
            } else {
                LOG.warn("executeMethod responseBody is empty!");
                // throw new HttpException("executeMethod responseBody is empty!");
                return "responseBody is empty!";
            }
        }
    }
}
