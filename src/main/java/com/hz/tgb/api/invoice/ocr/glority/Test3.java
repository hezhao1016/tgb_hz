package com.hz.tgb.api.invoice.ocr.glority;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hz.tgb.crypto.MD5Util;
import com.hz.tgb.http.util.HttpClientUtil;
import com.hz.tgb.http.util.HttpConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * 验真 - http://fapiao.glority.cn/docs/apiInterface.html
 *
 * Created by hezhao on 2018/11/6 17:14
 */
public class Test3 {

    private static String appKey = "xxxxxx"; // 这里输入提供的app_key
    private static String appSecret = "xxxxxx"; // 这里输入提供的app_secret
    private static String host = "http://fapiao.glority.cn/v1/item/fapiao_validation";

    public static void main(String[] args) throws Exception{
        long start = System.currentTimeMillis();

        Map<String, String> postMapSpecial = new HashMap<>();
        postMapSpecial.put("code","044031800104"); // 发票代码，非空
        postMapSpecial.put("number","04124339"); // 发票号码，非空
        postMapSpecial.put("date","2018年09月22日"); // 开票日期，非空
        postMapSpecial.put("pretax_amount","260.34"); // 总金额(不含税)，专票不为空，普票可为空
        postMapSpecial.put("check_code","73061489073796572511"); // 校验码，专票可为空，普票不为空
        postMapSpecial.put("type","10101"); // 发票类型

        Map<String, String> postMapNormal = new HashMap<>();
        postMapNormal.put("code","044001633111"); // 发票代码，非空
        postMapNormal.put("number","15874692"); // 发票号码，非空
        postMapNormal.put("date","2018年09月16日"); // 开票日期，非空
        postMapNormal.put("pretax_amount","75.95"); // 总金额(不含税)，专票不为空，普票可为空
        postMapNormal.put("check_code","46869547952233124630"); // 校验码，专票可为空，普票不为空
        postMapNormal.put("type","10102"); // 发票类型

        int size = 2;
        Map<String, String>[] postMaps = new Map[size];
        for (int i = 0; i < size; i++) {
            if (i % 2 == 0){
                postMaps[i] = postMapSpecial; // 专票
                continue;
            }
            postMaps[i] = postMapNormal; // 普票
        }

        for (int i = 0; i < postMaps.length; i++) {
            System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            long s = System.currentTimeMillis();
            validation(postMaps[i]);
            long e = System.currentTimeMillis();
            System.out.println("Time["+i+"]: " + (e - s));
        }

        long end = System.currentTimeMillis();
        System.out.println("Time Total: " + (end - start));

    }

    public static void validation(Map<String, String> postMap) throws Exception {
        System.out.println(postMap);
        if (postMap == null || postMap.size() == 0) {
            System.out.println("参数Map为空");
            return;
        }

        long timestamp = System.currentTimeMillis() / 1000;
        String token = MD5Util.md5(appKey + "+" + timestamp + "+" + appSecret).toLowerCase();

        Map<String, String> map = new HashMap<>();
        map.put("app_key", appKey);
        map.put("timestamp", String.valueOf(timestamp));
        map.put("token", token);
        map.put("code", postMap.get("code"));
        map.put("number", postMap.get("number"));
        map.put("check_code", postMap.get("check_code"));
        map.put("pretax_amount", postMap.get("pretax_amount"));
        map.put("date", postMap.get("date"));
        map.put("type", postMap.get("type"));
        HttpConfig httpConfig = new HttpConfig();
        httpConfig.setCharset("utf-8");
        httpConfig.setContentType("application/x-www-form-urlencoded");
        String json = HttpClientUtil.doPost(host, map, httpConfig);
        System.out.println(json);

        JSONObject jsonObject = JSON.parseObject(json);
        Integer result = jsonObject.getInteger("result");
        if (result != 1) {
            System.out.println(jsonObject.getInteger("error") + "," + jsonObject.getString("message"));
            return;
        }

        JSONObject data = jsonObject.getJSONObject("response").getJSONObject("data");

        JSONArray identifyResults = data.getJSONArray("identify_results");
        JSONObject identifyResult = identifyResults.getJSONObject(0);

        // 发票验真信息
        JSONObject validation = identifyResult.getJSONObject("validation");
        System.out.println(validation.toJSONString());

        String code = validation.getString("code"); // 发票验真结果代码
        String message = validation.getString("message"); // 发票查验结果详细信息

    }

    /*
    错误码定义
    10000	Internal error	            服务器内部错误	                请联系管理员
    10002	Authenticate failed	        授权错误	                        请确认已使用有效的appkey以及appsecret获取token，获取方法上文已述
    10003	Invalid parameter	        无效参数	                        补充相关必要参数
    10004	Not support type	        不支持的发票类型	                该种类型发票尚不支持
    10005	Invalid type	            发票类型不对	                    1, 识别的发票类型 与 输入的“type”不一致, 检查输入的参数与要识别的图片. 2, 当前账号没有识别该类型发票的权限. 购买相应权限或者联系管理员.
    10006	Invalid image	            无效图片	                        请检查以下几点: 1, 是否是损坏的图片 2, 是否是不支持的图片格式 3, 是否图片大小超过限制
    10007	No available packet	        无可用的流量包	                购买新流量包或联系管理员
    10008	Image cache disabled	    图片缓存功能未开启	            调用/feedback api时使用了id字段，但是图片缓存功能未开启，请联系管理员
    10025	Invalid app key	            无效App Key	                    检查appkey，确保有效
    10026	Expired token	            token过期	                    重新生成token
    10300	pdf convert failed	        pdf文件处理异常	                请联系管理员
    10400	download image file failed	使用image_url参数时图片下载失败	1,检查图片url是否正确 2,检查图片能否正常下载,图片下载timeout为20秒

    发票类型
    10100	增值税专用发票
    10101	增值税普通发票
    10102	增值税电子普通发票
    10103	增值税普通发票(卷票)
    10104	机动车销售统一发票
    10105	二手车销售统一发票
    10200	定额发票
    10400	机打发票
    10500	出租车发票
    10503	火车票
    10505	客运汽车
    10506	航空运输电子客票行程单
    10507	过路费发票
    10900	可报销其他发票
    00000	其他
    20100	国际小票

    发票验真结果代码
    10000	查验成功
    10001	查无此票
    10002	查验信息不一致(一般是专票未税金额不正确)
    10003	验真次数超过限制，同一张票一天最多可以查验5次
    10004	不支持验真发票类型
    10005	无效参数（发票代码，号码，开票日期等为空或者格式错误，普票验证码为空，专票税前金额为空等不合法参数错误）
    10006	其他错误
    说明：其中验真结果代码为 10000,10001,10002,10003时，视为一次有效查验，计算验真费用，其他值不计费

    */

}
