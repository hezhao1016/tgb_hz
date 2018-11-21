package com.hz.tgb.api.invoice.pdf.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by hezhao on 2018/9/18 16:16
 */
public class PDFStringUtil {

    private PDFStringUtil() {
        // 私有类构造方法
    }

    private static final Random random = new SecureRandom();
    private static final char[] randomArray = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private static final int randomArrayLength;

    static {
        randomArrayLength = randomArray.length;
    }

    public static String string(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("The length cannot be less than 0.");
        } else {
            String result = "";

            for(int i = 0; i < length; ++i) {
                result = result + randomArray[Math.abs(random.nextInt()) % (randomArrayLength - 10) + 10];
            }

            return result;
        }
    }

    public static String numeric(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("The length cannot be less than 0.");
        } else {
            String result = "";

            for(int i = 0; i < length; ++i) {
                result = result + randomArray[Math.abs(random.nextInt()) % 10];
            }

            return result;
        }
    }

    public static String fixed(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("The length cannot be less than 0.");
        } else {
            String result = "";

            for(int i = 0; i < length; ++i) {
                result = result + randomArray[Math.abs(random.nextInt()) % randomArrayLength];
            }

            return result;
        }
    }

    public static int parseInt(String numeric) {
        if (isInteger(numeric)) {
            return StringUtils.isBlank(numeric) ? 0 : Integer.parseInt(numeric.trim());
        }

        return 0;
    }

    public static long parseLong(String numeric) {
        return StringUtils.isBlank(numeric) ? 0L : Long.parseLong(numeric.trim());
    }

    public static float parseFloat(String numeric) {
        return StringUtils.isBlank(numeric) ? 0.0F : Float.parseFloat(numeric.trim());
    }

    public static double parseDouble(String numeric) {
        return StringUtils.isBlank(numeric) ? 0.0D : Double.parseDouble(numeric.trim());
    }

    public static BigDecimal parseDecimal(String numeric) {
        if (StringUtils.isBlank(numeric)) {
            return new BigDecimal(0);
        } else {
            numeric = numeric.replaceAll(",", "");
            return (new BigDecimal(numeric.trim())).setScale(2, 4);
        }
    }

    public static <T> String getString(T value) {
        return value == null ? "" : value.toString();
    }

    public static String getChinese(int number) {
        return null;
    }

    public static <T> String join(String split, T... array) {
        if (array != null && array.length != 0) {
            if (split == null) {
                split = "";
            }

            StringBuilder result = new StringBuilder(getString(array[0]));

            for(int i = 1; i < array.length; ++i) {
                result.append(split).append(array[i]);
            }

            return result.toString();
        } else {
            return "";
        }
    }

    public static Map<String, String> convertMap(Map<String, Object> param) {
        Map<String, String> map = new HashMap();
        if (param != null && param.size() != 0) {
            Iterator var3 = param.keySet().iterator();

            while(var3.hasNext()) {
                String key = (String)var3.next();
                Object value = param.get(key);
                if (value == null) {
                    map.put(key, "");
                } else {
                    map.put(key, value.toString());
                }
            }

            return map;
        } else {
            return map;
        }
    }

    public static List<Map<String, String>> convertMap(List<Map<String, Object>> maps) {
        List<Map<String, String>> tempList = new ArrayList();
        if (maps != null && maps.size() != 0) {
            Iterator var3 = maps.iterator();

            while(var3.hasNext()) {
                Map<String, Object> param = (Map)var3.next();
                Map<String, String> map = new HashMap();
                Iterator var6 = param.keySet().iterator();

                while(var6.hasNext()) {
                    String key = (String)var6.next();
                    Object value = param.get(key);
                    if (value == null) {
                        map.put(key, "");
                    } else {
                        map.put(key, value.toString());
                    }
                }

                tempList.add(map);
            }

            return tempList;
        } else {
            return tempList;
        }
    }

    public static List<Map<String, Object>> convertMap2(List<Map<String, Object>> maps) {
        List<Map<String, Object>> tempList = new ArrayList();
        if (maps != null && maps.size() != 0) {
            Iterator var3 = maps.iterator();

            while(var3.hasNext()) {
                Map<String, Object> param = (Map)var3.next();
                Map<String, Object> map = new HashMap();
                Iterator var6 = param.keySet().iterator();

                while(var6.hasNext()) {
                    String key = (String)var6.next();
                    Object value = param.get(key);
                    if (value == null) {
                        map.put(key, "");
                    } else if (value instanceof JSONArray) {
                        map.put(key, value);
                    } else {
                        map.put(key, value.toString());
                    }
                }

                tempList.add(map);
            }

            return tempList;
        } else {
            return tempList;
        }
    }

    public static String replaceSubString(String source, int start, int end) {
        if (StringUtils.isBlank(source)) {
            return "";
        } else {
            String sub = "";
            String startSub = "";
            String endSub = "";

            try {
                startSub = source.substring(0, start);
                endSub = source.substring(end, source.length());
                source.substring(start, end);
                StringBuffer sb = new StringBuffer();

                for(int i = 0; i < end - start; ++i) {
                    sb = sb.append("*");
                }

                sub = startSub + sb.toString() + endSub;
            } catch (Exception var8) {
                var8.printStackTrace();
            }

            return sub;
        }
    }

    public static String replaceSubString(String str, int begin) {
        String sub = "";

        try {
            sub = str.substring(0, begin);
            StringBuffer sb = new StringBuffer();

            for(int i = 0; i < str.length() - begin; ++i) {
                sb = sb.append("*");
            }

            sub = sub + sb.toString();
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return sub;
    }

    public static String replaceIdNumber(String source) {
        if (StringUtils.isBlank(source)) {
            return "";
        } else {
            int len = source.length();
            StringBuffer sb = new StringBuffer();

            try {
                if (len == 15) {
                    sb.append(source.substring(0, 8));
                    sb.append("***");
                    sb.append(source.substring(12, 15));
                } else if (len == 18) {
                    sb.append(source.substring(0, 10));
                    sb.append("***");
                    sb.append(source.substring(14, 18));
                } else {
                    sb.append(source.substring(0, len > 8 ? len - 8 : 1));
                    sb.append("***");
                    sb.append(source.substring(len > 4 ? len - 4 : 0, len));
                }
            } catch (Exception var4) {
                var4.printStackTrace();
            }

            return sb.toString();
        }
    }

    public static String getLastestStr(String source, int count) {
        if (StringUtils.isBlank(source)) {
            return "";
        } else {
            return source.length() <= count ? source : source.substring(source.length() - count);
        }
    }

    public static String replacePhone(String source) {
        if (StringUtils.isBlank(source)) {
            return "";
        } else {
            StringBuffer sb = new StringBuffer();

            try {
                String subTemp = source.substring(0, 4);
                sb.append(subTemp);
                sb.append("****");
                if (source.length() >= 11) {
                    subTemp = source.substring(8, 11);
                    sb.append(subTemp);
                }
            } catch (Exception var3) {
                var3.printStackTrace();
            }

            return sb.toString();
        }
    }

    public static String getPrice(String priceStr) {
        if (priceStr != null && !"0".equals(priceStr)) {
            priceStr = priceStr.trim();
            if (priceStr.indexOf(".") > -1) {
                if (priceStr.length() < 7) {
                    return priceStr + "元";
                }

                priceStr = priceStr.substring(0, priceStr.indexOf("."));
            }

            int len = priceStr.length();
            StringBuffer priceBuffer = new StringBuffer();
            if (len > 8) {
                priceBuffer.append(priceStr.substring(0, len - 8));
                if (len % 4 > 0) {
                    priceBuffer.append(".");
                    priceBuffer.append(priceStr.substring(0, len % 4));
                }

                priceBuffer.append("亿");
            } else if (len > 4) {
                priceBuffer.append(priceStr.substring(0, len - 4));
                if (len % 4 > 0) {
                    priceBuffer.append(".");
                    priceBuffer.append(priceStr.substring(len - 4, 4));
                }

                priceBuffer.append("万");
            } else {
                priceBuffer.append(priceStr);
                priceBuffer.append("元");
            }

            return priceBuffer.toString();
        } else {
            return null;
        }
    }

    public static String getNickFromPhone(String phone) {
        if (StringUtils.isBlank(phone)) {
            return "未知名称";
        } else {
            StringBuffer nickBuffer = new StringBuffer();
            if (phone.length() >= 3) {
                nickBuffer.append(phone.substring(0, 3));
            }

            if (phone.length() >= 7) {
                nickBuffer.append("***");
            }

            if (phone.length() >= 11) {
                nickBuffer.append(phone.substring(7, 11));
            }

            return nickBuffer.toString();
        }
    }

    public static boolean isMobile(String userAgent, String type) {
        boolean flag = false;
        if (userAgent != null && type != null) {
            if (type != null && "mobile".equals(type.toLowerCase())) {
                flag = userAgent.toLowerCase().matches("/applewebkit.*mobile.*/");
            } else if (type != null && "android".equals(type.toLowerCase())) {
                flag = userAgent.toLowerCase().indexOf("android") > -1;
            } else if (type != null && "iphone".equals(type.toLowerCase())) {
                flag = userAgent.toLowerCase().indexOf("iphone") > -1;
            } else if (type != null && "ipad".equals(type.toLowerCase())) {
                flag = userAgent.toLowerCase().indexOf("ipad") > -1;
            }

            return flag;
        } else {
            return false;
        }
    }

    public static String removeComma(String str) {
        return str == null ? null : str.trim().replaceAll(",", "");
    }

    public static long getLongValueFromJson(JSONObject jsonObject, String key) {
        try {
            return jsonObject.getLongValue(key);
        } catch (Exception var3) {
            return 0L;
        }
    }

    public static int getIntValueFromJson(JSONObject jsonObject, String key) {
        try {
            return jsonObject.getIntValue(key);
        } catch (Exception var3) {
            return 0;
        }
    }

    public static BigDecimal getBigDecimalFromJson(JSONObject jsonObject, String key) {
        try {
            return jsonObject.getBigDecimal(key);
        } catch (Exception var3) {
            return BigDecimal.ZERO;
        }
    }

    public static boolean isInteger(String str) {
        if (isNumeric(str)) {
            Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
            return pattern.matcher(str).matches();
        } else {
            return isNumeric(str);
        }
    }

    public static final boolean isNumeric(String s) {
        return s != null && !"".equals(s.trim()) ? s.matches("^[0-9]*$") : false;
    }

    public static String filtration(String str) {
        String result = str;
        String regEx = "[`~!@#$%^&*()+=|{}:;\\\\[\\\\]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

        try {
            result = Pattern.compile(regEx).matcher(str).replaceAll("").trim();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return result;
    }

    public static String removeSpace(String str) {
        String result = str;
        String regEx = "[ 　]";

        try {
            result = Pattern.compile(regEx).matcher(str).replaceAll("").trim();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return result;
    }

    public static void main(String[] args) {
        System.out.println(removeSpace("校 验 码:"));
        System.out.println(getLastestStr("123fee23423", 4));
        System.out.println(getLastestStr((String)null, 4));
        System.out.println(getLastestStr("123", 4));
        System.out.println(getLastestStr("1234", 4));
        System.out.println(getLastestStr("12345", 4));
        System.out.println(isInteger((String)null));

    }

}
