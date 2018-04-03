package com.hz.tgb.http.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HttpClient工具类 - 兼容Https请求
 *
 * @author Yaphis 2015年9月28日 下午2:57:46
 */
public class HttpClientUtil {

    private static final Logger LOG = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * 生成GET请求完整url
     * 
     * @param reqUrl
     * @param paramMap
     * @param charset
     * @return
     * @throws ParseException
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    public static String createHttpGetUrl(String reqUrl, Map<String, String> paramMap, String charset) throws ParseException, UnsupportedEncodingException, IOException {
        if (null == paramMap || paramMap.isEmpty()) {
            LOG.debug("paramMap is empty!");
            return reqUrl;
        }
        StringBuilder builder = new StringBuilder(reqUrl);
        if (!reqUrl.contains("?")) {
            builder.append("?");
        }
        List<NameValuePair> pairs = new ArrayList<NameValuePair>(paramMap.size());
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        String nameValuePairStr = EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
        LOG.debug("nameValuePairStr:{}", nameValuePairStr);
        builder.append(nameValuePairStr);
        return builder.toString();
    }

    /**
     * 发送 GET 请求
     * 
     * @param reqUrl
     * @param getMap
     * @param httpConfig
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String doGet(String reqUrl, Map<String, String> getMap, HttpConfig httpConfig) throws ClientProtocolException, IOException {
        String fullUrl = createHttpGetUrl(reqUrl, getMap, httpConfig.getCharset());
        HttpGet httpget = new HttpGet(fullUrl);
        long startTime = System.currentTimeMillis();
        String response = execute(httpget, httpConfig);
        LOG.info("reqMethod[GET],reqUrl:{},response:{},cost:{} ms!", new Object[] { fullUrl, response, System.currentTimeMillis() - startTime });
        return response;
    }

    /**
     * 执行HTTP请求
     * 
     * @param httpRequestBase
     * @param httpConfig
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    private static String execute(HttpRequestBase httpRequestBase, HttpConfig httpConfig) throws ClientProtocolException, IOException {
        CloseableHttpClient httpclient = HttpClientPoolUtil.getHttpClient(httpConfig);
        return httpclient.execute(httpRequestBase, HttpClientUtil.stringResponseHandler());
    }

    /**
     * POST 请求 - 兼容Https请求
     *
     * @param reqUrl 请求地址
     * @param postContent POST数据
     * @param httpConfig http配置
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String doPost(String reqUrl, String postContent, HttpConfig httpConfig) throws ClientProtocolException, IOException {
        HttpPost httpPost = new HttpPost(reqUrl);
        StringEntity stringEntity = new StringEntity(postContent, ContentType.create(httpConfig.getContentType(), Consts.UTF_8));
        httpPost.setEntity(stringEntity);
        long startTime = System.currentTimeMillis();
        String response = execute(httpPost, httpConfig);
        LOG.info("reqMethod[POST],reqUrl:{},postContent:{},response:{},cost:{}", new Object[] { reqUrl, postContent, response, System.currentTimeMillis() - startTime });
        return response;
    }

    /**
     * POST 请求
     * 
     * @param reqUrl 请求地址
     * @param postMap POST参数
     * @param httpConfig http配置
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String doPost(String reqUrl, Map<String, String> postMap, HttpConfig httpConfig) throws ClientProtocolException, IOException {
        HttpPost httpPost = new HttpPost(reqUrl);
        if (null != postMap && !postMap.isEmpty()) {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>(postMap.size());
            for (Map.Entry<String, String> entry : postMap.entrySet()) {
                pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairs, httpConfig.getCharset()));
        }
        long startTime = System.currentTimeMillis();
        String response = execute(httpPost, httpConfig);
        LOG.info("reqMethod[POST],reqUrl:{},postMap:{},response:{},cost:{}", new Object[] { reqUrl, postMap, response, System.currentTimeMillis() - startTime });
        return response;
    }

    /**
     * POST发送文件
     * 
     * @param reqUrl 请求地址
     * @param file 文件
     * @param formParamName 表单名称
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String postFile(String reqUrl, File file, String formParamName) throws ClientProtocolException, IOException {
        HttpPost httpPost = new HttpPost(reqUrl);
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        multipartEntityBuilder.setCharset(Charset.forName("UTF8"));
        multipartEntityBuilder.addBinaryBody(formParamName, file);
        multipartEntityBuilder.addTextBody("fileName", file.getName());
        httpPost.setEntity(multipartEntityBuilder.build());
        HttpConfig httpConfig = new HttpConfig();
        httpConfig.setReadTimeout(30000);// 30秒超时
        long startTime = System.currentTimeMillis();
        String response = execute(httpPost, httpConfig);
        LOG.info("reqMethod[POST],reqUrl:{},file:{},formParamName:{},response:{},cost:{}", new Object[] { reqUrl, file.getAbsolutePath(), formParamName, response,
                System.currentTimeMillis() - startTime });
        return response;
    }

    /**
     * Post文件接口
     * 
     * @param reqUrl
     * @param file
     * @param formParamName
     * @param postMap
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    @Deprecated
    public static String postFile(String reqUrl, File file, String formParamName, Map<String, String> postMap) throws ClientProtocolException, IOException {
        HttpPost httpPost = new HttpPost(reqUrl);
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        multipartEntityBuilder.setCharset(Charset.forName("UTF8"));
        multipartEntityBuilder.addBinaryBody(formParamName, file);
        ContentType contentType = ContentType.create(ContentType.DEFAULT_TEXT.getMimeType(), Consts.UTF_8);
        for (Map.Entry<String, String> entry : postMap.entrySet()) {
            StringBody stringBody = new StringBody(entry.getValue(), contentType);
            multipartEntityBuilder.addPart(entry.getKey(), stringBody);
        }
        httpPost.setEntity(multipartEntityBuilder.build());
        HttpConfig httpConfig = new HttpConfig();
        httpConfig.setReadTimeout(30000);// 30秒超时
        long startTime = System.currentTimeMillis();
        String response = execute(httpPost, httpConfig);
        LOG.info("reqMethod[POST],reqUrl:{},file:{},formParamName:{},response:{},cost:{}", new Object[] { reqUrl, file.getAbsolutePath(), formParamName, response,
                System.currentTimeMillis() - startTime });
        return response;
    }

    /**
     * @param reqUrl
     * @param file
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String postFile(String reqUrl, File file) throws ClientProtocolException, IOException {
        HttpPost httpPost = new HttpPost(reqUrl);
        FileEntity entity = new FileEntity(file);
        httpPost.setEntity(entity);
        httpPost.setHeader("Content-type", "application/octet-stream");
        HttpConfig httpConfig = new HttpConfig();
        httpConfig.setReadTimeout(30000);// 30秒超时
        String response = execute(httpPost, httpConfig);
        return response;
    }

    /**
     * @return
     */
    private static ResponseHandler<String> stringResponseHandler() {

        return new ResponseHandler<String>() {

            @Override
            public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    if (null == entity) {
                        LOG.info("entity is null!");
                        return "entity is null";
                    }
                    String entityStr = EntityUtils.toString(entity, "UTF-8");
                    if (null == entityStr || "".equals(entityStr)) {
                        LOG.info("entityStr is empty!");
                        return "entity is empty!";
                    }
                    return entityStr;
                } else {
                    return "status:" + status;
                }
            }
        };
    }

    private HttpClientUtil() {
    }




    public static void main(String[] args) {
        String refundGameSyncUrl = "https://183.131.22.101:8005/payadmin/pay/refund";
        String orderId = "GC201612211103228151622813363681918976";
        String refundOrderId = "GC201612211103228151622813363681918976_1";
        BigDecimal refundAmount = new BigDecimal(230);
        String refundDate = "20171001";
        LOG.info("开始同步游戏退款::::::[orderId]:{"+orderId+"},[refundOrderId]:{"+refundOrderId+"},[refundAmount]:{"+refundAmount+"},[refundDate]:{"+refundDate+"},[refundGameSyncUrl]:{"+refundGameSyncUrl+"},[type]:{1}");

        //同步结果
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("result",false);
        resultMap.put("msg","调用游戏退款同步接口失败");

        try {

            Map<String,Object> createMap = new HashMap<>();
            createMap.put("orderId",orderId);
            createMap.put("refundOrderId",refundOrderId);
            createMap.put("type",1);
            createMap.put("refundAmount",refundAmount.movePointRight(2).longValue());
            createMap.put("refundDate",refundDate);
            HttpConfig httpConfig = new HttpConfig();
            httpConfig.setCharset("utf-8");
            httpConfig.setContentType("application/json");
            String data = HttpClientUtil.doPost(refundGameSyncUrl, JSON.toJSONString(createMap),httpConfig);

            LOG.debug("syncRefundGame 接口返回参数:["+data+"]");

            if(StringUtils.isNotBlank(data)){
                data = data.replaceAll("/n","");
                JSONObject json = JSONObject.parseObject(data);

                if(json != null){

                    String code = json.getString("code");
                    String msg = json.getString("msg");

                    if("200".equals(code)){
                        resultMap.put("result",true);
                    }

                    resultMap.put("msg",msg);
                }
            }
        } catch (Exception e) {
            LOG.error(e.toString());
        }

        LOG.info("同步游戏退款结果："+JSONObject.toJSON(resultMap));
    }
}
