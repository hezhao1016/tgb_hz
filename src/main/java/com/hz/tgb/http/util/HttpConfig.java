package com.hz.tgb.http.util;

import java.util.*;

/**
 * HTTP 相关配置 如不需要进行特殊配置可直接使用静态示例HttpConfig.DEFAULT
 * 
 * @author Yaphis 2015年9月28日 下午3:31:43
 */
public class HttpConfig {

    /**
     * HTTP 读超时
     */
    private int readTimeout = 6000;

    /**
     * 字符编码
     */
    private String charset = "UTF-8";

    /**
     * 连接超时
     */
    private int connectionTimeout = 6000;

    /**
     * 
     */
    private String contentType = "plain/text";

    private Map<String, String> headerMap = new HashMap<String, String>();

    public HttpConfig() {
    }

    /**
     * 获取默认配置
     * 
     * @return
     */
    public static HttpConfig getInstance() {
        return new HttpConfig();
    }

    public void addHearder(String key, String value) {
        this.headerMap.put(key, value);
    }

    public Map<String, String> getHeaderMap() {
        return Collections.unmodifiableMap(headerMap);
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        final int maxLen = 10;
        return "HttpConfig [readTimeout=" + readTimeout + ", " + (charset != null ? "charset=" + charset + ", " : "") + "connectionTimeout=" + connectionTimeout + ", "
                + (contentType != null ? "contentType=" + contentType + ", " : "") + (headerMap != null ? "headerMap=" + toString(headerMap.entrySet(), maxLen) : "") + "]";
    }

    private String toString(Collection<?> collection, int maxLen) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        int i = 0;
        for (Iterator<?> iterator = collection.iterator(); iterator.hasNext() && i < maxLen; i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(iterator.next());
        }
        builder.append("]");
        return builder.toString();
    }
}
