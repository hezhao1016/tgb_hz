package com.hz.tgb.crypto.sign;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 键值对工具类
 *
 * @author Yaphis 2015年6月26日 下午5:36:02
 */
public class KeyValueUtil {

    private KeyValueUtil() {

    }

    /**
     * 从Map中获取key=value字符串
     * <p>
     * 包含key为null或者"" 和 value为null或者"" 的属性
     *
     * @param map
     * @return
     * @throws Exception 传入map为空时抛出异常
     */
    public static String getKeyValueStr(Map<String, Object> map) throws Exception {
        if (null == map) {
            throw new Exception("map is empty!");
        }
        Iterator<String> it = map.keySet().iterator();
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        while (it.hasNext()) {
            String key = it.next();
            Object value = map.get(key);
            if (isFirst) {
                sb.append(key).append("=").append(value);
                isFirst = false;
            } else {
                sb.append("&").append(key).append("=").append(value);
            }
        }
        return sb.toString();
    }

    /**
     * 从Map中获取key=value字符串
     * <p>
     * 跳过key为null或者"" 和 value为null或者"" 的属性
     *
     * @param map
     * @return 当传入的map为null时返回""
     */
    public static String getKeyValueStrTrim(Map<String, Object> map) {
        if (null == map) {
            return "";
        }
        Iterator<String> it = map.keySet().iterator();
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        while (it.hasNext()) {
            String key = it.next();
            Object value = map.get(key);
            if (null != key && !"".equals(key) && null != value && !"".equals(value)) {
                if (isFirst) {
                    sb.append(key).append("=").append(value);
                    isFirst = false;
                } else {
                    sb.append("&").append(key).append("=").append(value);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 从Map中获取key=value字符串(key按自然序排列)
     * <p>
     * 跳过key为null或者"" 和 value为null或者"" 的属性
     *
     * @param map
     * @return 当传入的map为null时返回""
     */
    public static String getSortedKeyValueStrTrim(Map<String, Object> map) {
        if (null == map) {
            return "";
        }
        TreeMap<String, Object> paramMap = new TreeMap<String, Object>(map);
        return getKeyValueStrTrim(paramMap);
    }

    /**
     * 从Map中获取key=value字符串(跳过空值),并对value值进行urlencode编码
     *
     * @param map
     * @param encoding
     * @return
     */
    public static String getKeyValueStrEnc(Map<String, String> map, String encoding) throws Exception {
        if (null == map) {
            throw new Exception("map is empty!");
        }
        if (null == encoding || "".equals(encoding)) {
            throw new Exception("encoding is illegal!");
        }
        Iterator<String> it = map.keySet().iterator();
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        while (it.hasNext()) {
            String key = it.next();
            String value = map.get(key);
            if (null != key && !"".equals(key) && null != value && !"".equals(value)) {
                String enValue = URLEncoder.encode(value, encoding);
                enValue.replace("+", "%20");
                if (isFirst) {
                    sb.append(key).append("=").append(enValue);
                    isFirst = false;
                } else {
                    sb.append("&").append(key).append("=").append(enValue);
                }
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "jack");
        map.put("age", 22);
        map.put("sex", "");
        Map<String, String> map2 = new HashMap<>();
        map2.put("name", "jack");
        map2.put("age", "22");
        map.put("sex", "");

        try {
            System.out.println(getKeyValueStr(map));
            System.out.println(getKeyValueStrTrim(map));
            System.out.println(getSortedKeyValueStrTrim(map));
            System.out.println(getKeyValueStrEnc(map2, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
