package com.hz.tgb.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

/**
 * 最好用的HTTP请求工具类，支持JSON . Yaphis
 */
public class HttpConnectUtilV4 {

    private static final Logger logger = LoggerFactory.getLogger(HttpConnectUtilV4.class);

    private static final int HTTP_STATUS_OK = 200;

    /***
     * 系统默认编码u8
     */
    public static final String DEFAULT_ENCODING = "UTF-8";

    /***
     * get请求form表单数据
     * 
     * @param urlLink 请求url
     * @param headers
     * @param headers
     * @return
     */
    public static String getFormData(String urlLink, Map<String, String> headers) {
        String result = "";

        try {

            HttpGet get = new HttpGet(new URI(urlLink));

            if (null != headers) {
                for (String key : headers.keySet()) {
                    get.addHeader(key, headers.get(key));
                }
            }

            // http调用获取返回数据
            result = getResponseData(null, get);
        } catch (URISyntaxException e) {
            logger.error("postFormData http send form data URISyntaxException error.", e);
        } catch (UnsupportedEncodingException e) {
            logger.error("postFormData http send form data UnsupportedEncodingException error.", e);
        } catch (Exception e) {
            logger.error("postFormData http send form data Exception error.", e);
        }

        return result;
    }

    /***
     * post请求form表单数据
     * 
     * @param urlLink 请求url
     * @param headers
     * @param nvps
     * @return
     */
    public static String postFormData(String urlLink, Map<String, String> headers, List<NameValuePair> nvps) {
        String result = "";

        try {
            HttpPost post = new HttpPost(new URI(urlLink));

            if (null != headers) {
                for (String key : headers.keySet()) {
                    post.addHeader(key, headers.get(key));
                }
            }
            // 设置请求参数
            post.setEntity(new UrlEncodedFormEntity(nvps, DEFAULT_ENCODING));

            // http调用获取返回数据
            result = getResponseData(null, post);
        } catch (URISyntaxException e) {
            logger.error("postFormData http send form data URISyntaxException error.", e);
        } catch (UnsupportedEncodingException e) {
            logger.error("postFormData http send form data UnsupportedEncodingException error.", e);
        } catch (Exception e) {
            logger.error("postFormData http send form data Exception error.", e);
        }

        return result;
    }

    /***
     * post请求字符串数据(支持xml或json)
     * 
     * @param urlLink 请求url
     * @param headers
     * @param data
     * @return
     */
    public static String postStringData(String urlLink, Map<String, String> headers, String data) {
        String result = "";

        try {

            HttpPost post = new HttpPost(new URI(urlLink));

            if (null != headers) {
                for (String key : headers.keySet()) {
                    post.addHeader(key, headers.get(key));
                }
            }
            // 设置请求参数
            post.setEntity(new StringEntity(data, DEFAULT_ENCODING));

            // http调用获取返回数据
            result = getResponseData(null, post);
        } catch (URISyntaxException e) {
            logger.error("postStringData http send form data URISyntaxException error.", e);
        } catch (UnsupportedEncodingException e) {
            logger.error("postStringData http send form data UnsupportedEncodingException error.", e);
        } catch (Exception e) {
            logger.error("postStringData http send form data Exception error.", e);
        }

        return result;
    }

    /**
     * 根据指定参数获取http数据
     *
     * @param
     * @return
     */
    public static String getResponseData(HttpClient client, HttpUriRequest request) throws Exception {
        if (null == client) {
            client = HttpConnectManagerV4.getHttpClient();
        }

        try {
            // 设置返回数据处理
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                @Override
                public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    
                    // 只有200的时候才按成功处理
                    if (status == HTTP_STATUS_OK) {
                        HttpEntity entity = response.getEntity();
                        // 指定返回信息编码为UTF-8
                        return entity != null ? EntityUtils.toString(entity, DEFAULT_ENCODING) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }

                }

            };

            // http请求
            String responseBody = client.execute(request, responseHandler);

            return responseBody;

        } catch (Exception ex) {
            request.abort();
            throw ex;
        }
    }
}
