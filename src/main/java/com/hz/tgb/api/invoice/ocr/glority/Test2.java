package com.hz.tgb.api.invoice.ocr.glority;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.aip.util.Base64Util;
import com.hz.tgb.crypto.MD5Util;
import com.hz.tgb.file.FileUtil;
import com.hz.tgb.http.util.HttpClientUtil;
import com.hz.tgb.http.util.HttpConfig;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 识别并验真 - http://fapiao.glority.cn/docs/apiInterface.html
 *
 * Created by hezhao on 2018/11/6 17:14
 */
public class Test2 {

    private static String appKey = "xxxxxx"; // 这里输入提供的app_key
    private static String appSecret = "xxxxxx"; // 这里输入提供的app_secret
    private static String host = "http://fapiao.glority.cn/v1/item/get_item_info_with_validation";

    public static void main(String[] args) throws Exception{
        long start = System.currentTimeMillis();

        int size = 2;
        String[] imagePaths = new String[size];

        String[] sources = {
                "C:/Users/Administrator/Pictures/TIM图片20180927182211.jpg",// 增值税普通发票
                "C:/Users/Administrator/Pictures/TIM图片20180925144712.png",// 增值税电子普通发票
        };
        Random rd = new Random(System.nanoTime());

        for (int i = 0; i < size; i++) {
            imagePaths[i] = sources[rd.nextInt(sources.length)];
        }

        for (int i = 0; i < imagePaths.length; i++) {
            System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            long s = System.currentTimeMillis();
            ocrAndValidation(imagePaths[i]);
            long e = System.currentTimeMillis();
            System.out.println("Time["+i+"]: " + (e - s));
        }

        long end = System.currentTimeMillis();
        System.out.println("Time Total: " + (end - start));

    }

    public static void ocrAndValidation(String imagePath) throws Exception {
        System.out.println(imagePath);
        if (StringUtils.isBlank(imagePath)) {
            System.out.println("图片路径为空");
            return;
        }

        long timestamp = System.currentTimeMillis() / 1000;
        String token = MD5Util.md5(appKey + "+" + timestamp + "+" + appSecret).toLowerCase();
        String base64 = Base64Util.encode(FileUtil.readFileByBytes(imagePath));

        Map<String, String> map = new HashMap<>();
        map.put("app_key", appKey);
        map.put("timestamp", String.valueOf(timestamp));
        map.put("token", token);
        map.put("image_data", base64);
        map.put("type", "0"); // 要识别的发票类型: 0: 自动识别(默认值) 1: 增值税发票(大票) 2: 出租车,火车票, 机打发票, 增值税卷票(小票-自动识别)
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
        String type = identifyResult.getString("type");
        System.out.println(type);

        // 发票信息
        JSONObject details = identifyResult.getJSONObject("details");
        System.out.println(details.toJSONString());

        String code = details.getString("code"); // 发票代码
        String number = details.getString("number"); // 号码
        String date = details.getString("date"); // 开票日期
        String total = details.getString("total"); // 价税合计
        String check_code = details.getString("check_code"); // 校验码
        String seller = details.getString("seller"); // 销售方名称
        String seller_tax_id = details.getString("seller_tax_id"); // 销售方纳税人识别号
        String buyer = details.getString("buyer"); // 购买方方名称
        String buyer_tax_id = details.getString("buyer_tax_id"); // 购买方纳税人识别号

        // 增值税专用发票, 增值税普通发票, 增值税普通电子发票
        if ("10100".equals(type) || "10101".equals(type) || "10102".equals(type) ) {
            String pretax_amount = details.getString("pretax_amount"); // 税前金额
            String company_seal = details.getString("company_seal"); // 是否有公司印章（0：没有； 1： 有）
            String form_type = details.getString("form_type"); // 发票是第几联

        } else if ("10103".equals(type)) {
            // 增值税普通发票（卷式）
            String category = details.getString("category"); // 种类，oil 表示是加油票

        }

        // 发票验真信息
        JSONObject validation = identifyResult.getJSONObject("validation");
        System.out.println(validation.toJSONString());

        String validationCode = validation.getString("code"); // 发票验真结果代码
        if (!"10000".equals(validationCode)) {
            String validationMessage = validation.getString("message"); // 发票查验结果详细信息
            System.out.println(validationCode + "," + validationMessage);
        } else {
            // 验真成功
            JSONArray items = details.getJSONArray("items"); //

            // 增值税专用发票, 增值税普通发票, 增值税普通电子发票
            if ("10100".equals(type) || "10101".equals(type) || "10102".equals(type) ) {
                String tax = details.getString("tax"); // 税额
                String seller_addr_tel = details.getString("seller_addr_tel"); // 销售方地址电话
                String seller_bank_account = details.getString("seller_bank_account"); // 销售方开户行及帐号
                String buyer_addr_tel = details.getString("buyer_addr_tel"); // 购买方地址电话
                String buyer_bank_account = details.getString("buyer_bank_account"); // 购买方开户行及帐号
                String remark = details.getString("remark"); // 备注
                String machine_code = details.getString("machine_code"); // 机器编号
                String invalid_mark = details.getString("invalid_mark"); // 作废标志（0：否； 1：是）
                String oil_mark = details.getString("oil_mark"); // 成品油标志（0：否； 1：是）

                if (CollectionUtils.isNotEmpty(items)) {
                    for (int i = 0; i < items.size(); i++) {
                        JSONObject item = items.getJSONObject(i);

                        String name = details.getString("name"); // 货物或应税劳务、服务名称
                        String specification = details.getString("specification"); // 规格型号
                        String unit = details.getString("unit"); // 单位
                        String quantity = details.getString("quantity"); // 数量
                        String price = details.getString("price"); // 单价
                        String item_total = details.getString("total"); // 金额
                        String tax_rate = details.getString("tax_rate"); // 税率，例如：16
                        String Tax = details.getString("tax"); // 税额

                    }

                }

            } else if ("10103".equals(type)) { // 增值税普通发票（卷式）
                String receiptor = details.getString("receiptor"); // 收货员，增值税普通发票（卷式）字段

                if (CollectionUtils.isNotEmpty(items)) {
                    for (int i = 0; i < items.size(); i++) {
                        JSONObject item = items.getJSONObject(i);

                        String item_name = details.getString("item"); // 项目
                        String quantity = details.getString("quantity"); // 数量
                        String price_with_tax = details.getString("price_with_tax"); // 含税单价
                        String total_with_tax = details.getString("total_with_tax"); // 金额

                    }

                }
            }
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
