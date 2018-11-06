package com.hz.tgb.api.invoice.ocr.glority;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hz.tgb.crypto.Base64Utils;
import com.hz.tgb.crypto.MD5Util;
import com.hz.tgb.file.FileUtil;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;

import java.util.Random;

/**
 * 睿琪票据识别 - http://fapiao.glority.cn/docs/apiInterface.html
 *
 * 票小秘
 *
 * Created by hezhao on 2018/11/6 17:14
 */
public class Test1 {

    private static String appKey = "xxxxx"; // 这里输入提供的app_key
    private static String appSecret = "xxxxx"; // 这里输入提供的app_secret
    private static String host = "http://fapiao.glority.cn/v1/item/get_item_info";

    public static void main(String[] args) throws Exception{
        long start = System.currentTimeMillis();

        int size = 10;
        String[] imagePaths = new String[size];

        String[] sources = {
                "D:/qq聊天记录/1993721152/FileRecv/MobileFile/IMG_20180925_163849.jpg",
                "D:/qq聊天记录/1993721152/FileRecv/MobileFile/IMG_20180925_163741.jpg",
                "D:/qq聊天记录/1993721152/FileRecv/MobileFile/IMG_20180925_163901.jpg",
                "D:/qq聊天记录/1993721152/FileRecv/MobileFile/IMG_20180925_163942.jpg",
                "D:/qq聊天记录/1993721152/FileRecv/MobileFile/IMG_20180925_163821.jpg",
                "C:/Users/Administrator/Pictures/TIM图片20180927182211.jpg",// 专票
                "C:/Users/Administrator/Pictures/TIM图片20180925144712.png",// 普票
        };
        Random rd = new Random(System.nanoTime());

        for (int i = 0; i < size; i++) {
            imagePaths[i] = sources[rd.nextInt(sources.length)];
        }

        for (int i = 0; i < imagePaths.length; i++) {
            System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            long s = System.currentTimeMillis();
            ocr(imagePaths[i]);
            long e = System.currentTimeMillis();
            System.out.println("Time["+i+"]: " + (e - s));
        }

        long end = System.currentTimeMillis();
        System.out.println("Time Total: " + (end - start));

    }

    public static void ocr(String imagePath) throws Exception {
        System.out.println(imagePath);
        if (StringUtils.isBlank(imagePath)) {
            System.out.println("图片路径为空");
            return;
        }

        long timestamp = System.currentTimeMillis() / 1000;
        String token = MD5Util.md5(appKey + "+" + timestamp + "+" + appSecret).toLowerCase();

        String base64 = Base64Utils.encode(FileUtil.readFileByBytes(imagePath));

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(host)
                .post(new FormBody.Builder()
                        .add("app_key", appKey)
                        .add("timestamp", String.valueOf(timestamp))
                        .add("token", token)
                        .add("image_data", base64)
                        .build())
                .build();

        Response response = client.newCall(request).execute();

        String json = response.body().string();
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
        String type = identifyResult.getString("type");
        System.out.println(type);

        // 发票信息
        JSONObject details = identifyResult.getJSONObject("details");
        System.out.println(details.toJSONString());

        if ("10200".equals(type)) { // 定额发票
            String code = details.getString("code"); // 发票代码
            String number = details.getString("number"); // 号码
            String total = details.getString("total"); // 总金额

        } else if ("10507".equals(type)) { // 路桥费
            String code = details.getString("code"); // 发票代码
            String number = details.getString("number"); // 号码
            String total = details.getString("total"); // 总金额
            String date = details.getString("date"); // 日期
            String time = details.getString("time"); // 时间
            String entrance = details.getString("entrance"); // 入口
            String exit = details.getString("exit"); // 出口

        }

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
    */

}
