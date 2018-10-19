package com.hz.tgb.crypto.sign;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.hz.tgb.crypto.MD5Util;
import com.hz.tgb.entity.User;
import com.hz.tgb.reflect.ReflectUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * HTTP接口签名工具类
 *
 * Created by hezhao on 2018-07-02 19:41
 */
public class SignatureUtil {
    private static final Logger logger = LoggerFactory.getLogger(SignatureUtil.class);
    private static final String SIGN_STR = "sign";

    /**
     * 获取拼接字符串<br>
     * 跳过key为null或者"" 和 value为null或者"" 的属性
     *
     * @param target 对象 或 Map
     * @param exclusiveField 排除的属性列表,可空
     * @return
     */
    public static String getBaseString(Object target, String... exclusiveField) {
        Preconditions.checkNotNull(target);

        Map<String, Object> paramMap = getStringObjectTreeMap(target, null, false);
        return getStringByMap(paramMap, exclusiveField);
    }

    /**
     * 获取按照自然顺序升序后的字符串<br>
     * 跳过key为null或者"" 和 value为null或者"" 的属性
     *
     * @param target 对象 或 Map
     * @param exclusiveField 排除的属性列表,可空
     * @return
     */
    public static String getSortedBaseString(Object target, String... exclusiveField) {
        Preconditions.checkNotNull(target);

        Map<String, Object> paramMap = getStringObjectTreeMap(target, null, true);
        return getStringByMap(paramMap, exclusiveField);
    }

    /**
     * 获取拼接字符串,自定义排序<br>
     * 跳过key为null或者"" 和 value为null或者"" 的属性
     *
     * @param target 对象 或 Map
     * @param exclusiveField 排除的属性列表,可空
     * @param comparator 自定义排序比较器
     * @return
     */
    public static String getKeySortedBaseString(Object target, Comparator<String> comparator, String... exclusiveField) {
        Preconditions.checkNotNull(target);
        Preconditions.checkNotNull(comparator);

        Map<String, Object> paramMap = getStringObjectTreeMap(target, comparator, null);
        return getStringByMap(paramMap, exclusiveField);
    }

    /**
     * 把对象转为Map
     * @param target 对象 或 Map
     * @param comparator 自定义排序比较器
     * @return
     */
    private static Map<String, Object> getStringObjectTreeMap(Object target, Comparator<String> comparator, Boolean isSort) {
        Map<String, Object> paramMap;
        if (comparator != null) {
            paramMap = Maps.newTreeMap(comparator);
        } else {
            // 按照自然顺序排列
            if (isSort != null && isSort) {
                paramMap = new TreeMap<>();
            } else {
                // 不排序
                paramMap = new HashMap<>();
            }
        }

        // Map
        if (target instanceof Map) {
            paramMap.putAll((Map<String, Object>) target);
        } else {
            // 不是Map
            Map<String, Field> fields = ReflectUtils.getBeanPropertyFields(target.getClass());
            try {
                if (fields != null) {
                    for (Map.Entry<String, Field> each : fields.entrySet()) {
                        String fieldName = each.getKey();
                        Field field = each.getValue();
                        field.setAccessible(true);
                        Object fieldValue = field.get(target);
                        paramMap.put(fieldName, fieldValue);
                    }
                }
            } catch (IllegalArgumentException e) {
                logger.error("get source list IllegalArgumentException error.", e);
            } catch (IllegalAccessException e) {
                logger.error("get source list IllegalAccessException error.", e);
            }
        }
        return paramMap;
    }

    /**
     * 把Map转为String
     * @param exclusiveField 排除的属性
     * @param paramMap map
     * @return
     */
    private static String getStringByMap(Map<String, Object> paramMap, String... exclusiveField) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;

        if (paramMap != null && !paramMap.isEmpty()) {
            for (String key : paramMap.keySet()) {
                if (exclusiveField == null || !ArrayUtils.contains(exclusiveField, key)) {
                    Object value = paramMap.get(key);
                    if (null != key && !"".equals(key.trim()) && null != value && !"".equals(value)) {
                        if (isFirst) {
                            sb.append(key).append("=").append(value);
                            isFirst = false;
                        } else {
                            sb.append("&").append(key).append("=").append(value);
                        }
                    }
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

        // 签名
        String APPSECRET = "123456";
        // 自然顺序升序排列
        String baseString = SignatureUtil.getSortedBaseString(map, "sign");
        baseString += "&key=" + APPSECRET;
        System.out.println(baseString);
        String md5 = MD5Util.md5(baseString);
        System.out.println(md5);
        // map.put("sign", md5);

        // 自然顺序升序排列
        System.out.println(SignatureUtil.getSortedBaseString(map));
        // 不排序
        System.out.println(SignatureUtil.getBaseString(map));

        // 对象拼接
        User jack = User.builder().name("jack").age(22).build();
        System.out.println(SignatureUtil.getBaseString(jack));
        System.out.println(SignatureUtil.getSortedBaseString(jack));
    }
}
