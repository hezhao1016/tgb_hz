package com.hz.tgb.api.invoice.ocr.hehe.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用工具类
 *
 * Created by hezhao on 2018/9/27 17:57
 */
public class CommonUtil {

    private CommonUtil() {
        // 私有类构造方法
    }

    public static String toString(Object obj) {
        return (obj == null) ? "" : obj.toString();
    }

    public static Integer toInteger(Object obj) {
        return (obj == null) ? 0 : Integer.valueOf(toString(obj));
    }

    public static Long toLong(Object obj) {
        return (obj == null) ? 0 : Long.valueOf(toString(obj));
    }

    public static Double toDouble(Object obj) {
        return (obj == null) ? 0.0D : Double.valueOf(toString(obj));
    }

    public static Boolean toBoolean(Object obj) {
        return (obj == null) ? false : Boolean.valueOf(toString(obj));
    }

    public static Map<String, Object> toMap(Object obj) {
        return (obj == null) ? new HashMap<>() : (Map)obj;
    }

    public static List<Map<String, Object>> toList(Object obj) {
        return (obj == null) ? new ArrayList<>() : (List<Map<String, Object>>)obj;
    }

}
