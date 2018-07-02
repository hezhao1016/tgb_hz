package com.hz.tgb.crypto.sign;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.hz.tgb.crypto.MD5Util;
import com.hz.tgb.reflect.ReflectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;

/**
 * HTTP接口签名工具类
 *
 * Created by hezhao on 2018-07-02 19:41
 */
public class SignatureUtil {
    private static final Logger logger = LoggerFactory.getLogger(SignatureUtil.class);
    private static final String SIGN_STR = "sign";

    public static List<String> getBaseString(Object target, Set<String> exclusiveField) {
        Preconditions.checkNotNull(target);

        Map<String, Field> fields = ReflectUtils.getBeanPropertyFields(target.getClass());
        LinkedList<String> list = Lists.newLinkedList();
        try {
            if (fields != null) {
                for (Map.Entry<String, Field> each : fields.entrySet()) {
                    String fieldName = (String)each.getKey();
                    if ((exclusiveField == null) || (!exclusiveField.contains(fieldName))) {
                        Field field = (Field)each.getValue();
                        field.setAccessible(true);
                        Object fieldValue = field.get(target);
                        if ((fieldValue != null) && (!"".equals(fieldValue))) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append(fieldName);
                            stringBuilder.append("=");
                            stringBuilder.append(fieldValue);
                            stringBuilder.append("&");
                            list.add(stringBuilder.toString());
                        }
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            logger.error("get source list IllegalArgumentException error.", e);
        } catch (IllegalAccessException e) {
            logger.error("get source list IllegalAccessException error.", e);
        }
        return list;
    }

    public static String getKeySortedBaseString(Object target, Set<String> exclusiveField, Comparator<String> comparator) {
        Preconditions.checkNotNull(target);
        Preconditions.checkNotNull(comparator);

        Map<String, Field> fields = ReflectUtils.getBeanPropertyFields(target.getClass());
        TreeMap<String, Object> map = Maps.newTreeMap(comparator);
        try {
            if (fields != null) {
                for (Map.Entry<String, Field> each : fields.entrySet()) {
                    String fieldName = (String)each.getKey();
                    if ((exclusiveField == null) || (!exclusiveField.contains(fieldName))) {
                        Field field = (Field)each.getValue();
                        field.setAccessible(true);
                        Object fieldValue = field.get(target);
                        if ((fieldValue != null) && (!"".equals(fieldValue))) {
                            map.put(fieldName, fieldValue);
                        }
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            logger.error("get source list IllegalArgumentException error.", e);
        } catch (IllegalAccessException e) {
            logger.error("get source list IllegalAccessException error.", e);
        }
        StringBuilder sb = new StringBuilder(128);
        if (!map.isEmpty()) {
            for (Map.Entry<String, Object> each : map.entrySet()) {
                sb.append((String)each.getKey());
                sb.append("=");
                sb.append(each.getValue());
                sb.append("&");
            }
        }
        return sb.toString();
    }

    public static String getSortedBaseString(Object target, Set<String> exclusiveField) {
        List<String> baseString = getBaseString(target, exclusiveField);
        if (baseString != null) {
            int size = baseString.size();
            String[] arrayToSort = (String[])baseString.toArray(new String[size]);

            // 按自然排序
            Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < size; i++) {
                sb.append(arrayToSort[i]);
            }
            String result = sb.toString();

            return result;
        }
        return null;
    }

    public static String getSortedBaseString(Object target, String signFieldName) {
        return getSortedBaseString(target, Sets.newHashSet(new String[] { signFieldName }));
    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "jack");
        map.put("age", 22);

        String APPSECRET = "123456";

        //签名
        String baseString = SignatureUtil.getSortedBaseString(map, "sign");
        baseString += "key=" + APPSECRET;
        String md5 = MD5Util.md5(baseString);

        map.put("sign", md5);

        System.out.println(md5);
    }
}
