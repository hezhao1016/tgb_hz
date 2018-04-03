package com.hz.tgb.http.util;

import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * http连接池工具类
 * 
 * @author Yaphis 2016年8月30日 下午7:59:11
 */
public class HttpClientPoolUtil {

    private static final Logger LOG = LoggerFactory.getLogger(HttpClientPoolUtil.class);

    private static PoolingHttpClientConnectionManager connectionManagerPool = null;

    private HttpClientPoolUtil() {
    }

    static {
        try {
            SSLContext sslContext = initTrustAllContext();
            LayeredConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create().register("https", sslsf).register("http", new PlainConnectionSocketFactory())
                    .build();
            connectionManagerPool = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            connectionManagerPool.setMaxTotal(800);
            connectionManagerPool.setDefaultMaxPerRoute(300);
        } catch (Exception e) {
            LOG.error("", e);
        }
    }

    /**
     * 初始化默认SSLContext
     * 
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static SSLContext initDefaultSSLContext() throws NoSuchAlgorithmException {
        return SSLContext.getDefault();
    }

    /**
     * 初始化信任所有证书的SSLContext
     * 
     * @return
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     */
    public static SSLContext initTrustAllContext() throws KeyManagementException, NoSuchAlgorithmException {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        X509TrustManager tm = new MyX509TrustManager();
        sslContext.init(null, new TrustManager[] { tm }, null);
        return sslContext;
    }

    /**
     * 获取httpClient
     * 
     * @return
     */
    public static CloseableHttpClient getHttpClient(HttpConfig httpConfig) {
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManagerPool)
                .setDefaultRequestConfig(requestConfig(httpConfig.getConnectionTimeout(), httpConfig.getReadTimeout())).setDefaultHeaders(createHeader(httpConfig.getHeaderMap())).build();
        return httpClient;
    }
    
    /**
     * @param connectionTimeout
     * @param readTimeout
     * @return
     */
    private static RequestConfig requestConfig(int connectionTimeout, int readTimeout) {
        return RequestConfig.custom().setConnectionRequestTimeout(connectionTimeout).setConnectTimeout(connectionTimeout).setSocketTimeout(readTimeout).build();
    }

    /**
     * @param headerMap
     * @return
     */
    private static List<Header> createHeader(Map<String, String> headerMap) {
        if (null == headerMap) {
            return Collections.emptyList();
        }
        List<Header> headerList = new ArrayList<Header>();
        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            headerList.add(new BasicHeader(entry.getKey(), entry.getValue()));
        }
        return headerList;
    }

}
