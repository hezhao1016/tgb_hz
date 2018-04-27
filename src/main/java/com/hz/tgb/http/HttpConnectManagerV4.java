package com.hz.tgb.http;

import com.hz.tgb.common.number.NumberUtil;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/** HTTP连接池管理工具类
 * @author hezhao
 */
public class HttpConnectManagerV4 {

    private static final Logger logger = LoggerFactory.getLogger(HttpConnectManagerV4.class);

    private static final PoolingHttpClientConnectionManager CONNECT_MANAGER;
    private static final CloseableHttpClient DEFAULT_HTTP_CLIENT;
    private static SSLConnectionSocketFactory sslSocketFactory = null;
    public final static String HTTP_MAX_TOTAL_CONNECTIONS = "http.max_total_connections";
    public final static String HTTP_MAX_ROUTE_CONNECTIONS = "http.max_route_connections";
    public final static String HTTP_CONNECT_TIMEOUT = "http.connect_timeout";
    public final static String HTTP_READ_TIMEOUT = "http.read_timeout";

    /**
     * 最大连接数
     */
    public final static int MAX_TOTAL_CONNECTIONS = 1024;

    /**
     * 每个路由最大连接数
     */
    public final static int MAX_ROUTE_CONNECTIONS = 512;
    /**
     * 连接超时时间
     */
    public final static int CONNECT_TIMEOUT = 2500;
    /**
     * 读取超时时间
     */
    public final static int READ_TIMEOUT = 2500;

    static {

        try {
            SSLContext sslcontext = SSLContexts.custom().build();
            sslSocketFactory = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null, SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        } catch (KeyManagementException e) {
            logger.error("HttpConnectManager init KeyManagementException error.", e);
        } catch (NoSuchAlgorithmException e) {
            logger.error("HttpConnectManager init NoSuchAlgorithmException error.", e);
        }

        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create().register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", sslSocketFactory).build();
        // 设置连接管理器
        CONNECT_MANAGER = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

        int maxTotal = NumberUtil.parseInt(System.getProperty(HTTP_MAX_TOTAL_CONNECTIONS), MAX_TOTAL_CONNECTIONS);
        CONNECT_MANAGER.setMaxTotal(maxTotal);
        // 设置每个路由的最大连接数
        int maxRoute = NumberUtil.parseInt(System.getProperty(HTTP_MAX_ROUTE_CONNECTIONS), MAX_ROUTE_CONNECTIONS);
        CONNECT_MANAGER.setDefaultMaxPerRoute(maxRoute);

        // 设置连接超时时间
        int connectTimeout = NumberUtil.parseInt(System.getProperty(HTTP_CONNECT_TIMEOUT), CONNECT_TIMEOUT);
        // 设置读取超时时间
        int readTimeout = NumberUtil.parseInt(System.getProperty(HTTP_READ_TIMEOUT), READ_TIMEOUT);

        logger.debug("maxTotal:{},maxRoute:{},connectTimeout:{},readTimeout:{}", new Object[] { maxTotal, maxRoute, connectTimeout, readTimeout });

        // 设置超时时间
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectTimeout).setConnectTimeout(connectTimeout).setSocketTimeout(readTimeout)
                .setCookieSpec(CookieSpecs.IGNORE_COOKIES).build();

        DEFAULT_HTTP_CLIENT = HttpClients.custom().setConnectionManager(CONNECT_MANAGER).setDefaultRequestConfig(requestConfig).build();
    }

    /**
     * 获取客户端连接
     */
    public static CloseableHttpClient getHttpClient() {
        return DEFAULT_HTTP_CLIENT;
    }

    public static void main(String[] args) {
        HttpConnectManagerV4.getHttpClient();
    }
}
