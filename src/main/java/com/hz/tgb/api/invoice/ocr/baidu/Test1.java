package com.hz.tgb.api.invoice.ocr.baidu;

import com.baidu.aip.ocr.AipOcr;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Baidu AIOCR 自定义模板文字识别
 *
 * https://ai.baidu.com/iocr/
 *
 * Created by hezhao on 2018/9/26 10:09
 */
public class Test1 {
    // 100 次 总用时 195秒

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        int size = 15;
        String[] imagePaths = new String[size];
        for (int i = 0; i < size; i++) {

            Random rd = new Random(System.nanoTime());
            int temp = rd.nextInt(5);

            switch (temp) {
                case 0:
                    imagePaths[i] = "D:/qq聊天记录/1993721152/FileRecv/MobileFile/IMG_20180925_163849.jpg";
                    break;
                case 1:
                    imagePaths[i] = "D:/qq聊天记录/1993721152/FileRecv/MobileFile/IMG_20180925_163741.jpg";
                    break;
                case 2:
                    imagePaths[i] = "D:/qq聊天记录/1993721152/FileRecv/MobileFile/IMG_20180925_163901.jpg";
                    break;
                case 3:
                    imagePaths[i] = "D:/qq聊天记录/1993721152/FileRecv/MobileFile/IMG_20180925_163942.jpg";
                    break;
                case 4:
                    imagePaths[i] = "D:/qq聊天记录/1993721152/FileRecv/MobileFile/IMG_20180925_163821.jpg";
                    break;
            }

        }

        for (int i = 0; i < imagePaths.length; i++) {
            System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            long s = System.currentTimeMillis();
            baiduAIOCR(imagePaths[i], "1", "");
            long e = System.currentTimeMillis();
            System.out.println("Time["+i+"]: " + (e - s));
        }

        long end = System.currentTimeMillis();
        System.out.println("Time Total: " + (end - start));

    }

    // 图像数据，base64编码后进行urlencode，要求base64编码和urlencode后大小不超过4M，最短边至少15px，最长边最大4096px,支持jpg/png/bmp格式
    private static void baiduAIOCR(String imagePath, String classifierId, String template){
        System.out.println(imagePath);
        if (StringUtils.isBlank(imagePath)) {
            System.out.println("图片路径为空");
            return;
        }

        AipOcr client = AipOcrSingleton.getInstance();

        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<>();

        // 模板ID
        String templateSign = "";
        if (StringUtils.isNotBlank(templateSign)) {
            templateSign = template;
        }

        // 分类器ID，这个参数和templateSign至少存在一个，优先使用templateSign。
        // 存在templateSign时，表示使用指定模板；如果没有templateSign而有classifierId，表示使用分类器去判断使用哪个模板
        if (StringUtils.isNotBlank(classifierId)){
            options.put("classifierId", classifierId);
        }

        if (StringUtils.isBlank(templateSign) && StringUtils.isBlank(classifierId)) {
            System.out.println("classifierId和templateSign至少存在一个");
            return;
        }

        // 方式1：参数为本地图片路径
        JSONObject res = client.custom(imagePath, templateSign, options);

        // 方式2：参数为本地图片二进制数组
//        try {
//            byte[] file = FileUtil.readFileByBytes(imagePath);
//            res = client.custom(file, templateSign, options);
//            System.out.println(res.toString(2));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        // 解析内容
        Map<String, String> respData = new HashMap<>();

        String errorCode = res.getString("error_code");
        if (!"0".equals(errorCode)) {
            String errorMsg = res.getString("error_msg");
            System.out.println("Error: " + errorCode + " - " + errorMsg);

            // SDK本地检测参数返回的错误码
//            SDK100	image size error	图片大小超限
//            SDK101	image length error	图片边长不符合要求
//            SDK102	read image file error	读取图片文件错误
//            SDK108	connection or read data time out	连接超时或读取数据超时
//            SDK109	unsupported image format	不支持的图片格式

            // 服务端返回的错误码
//            14	IAM Certification failed	IAM鉴权失败，建议用户参照文档自查生成sign的方式是否正确，或换用控制台中ak sk的方式调用
//            17	Open api daily request limit reached	每天流量超限额
//            18	Open api qps request limit reached	QPS超限额
//            19	Open api total request limit reached	请求总量超限额
//            100	Invalid parameter	无效参数
//            110	Access token invalid or no longer valid	Access Token失效
//            111	Access token expired	Access token过期

//            216100	invalid param	请求中包含非法参数，请检查后重新尝试
//            216101	not enough param	缺少必须的参数，请检查参数是否有遗漏
//            216110	appid not exist	appid不存在，请重新核对信息是否为后台应用列表中的appid
//            216200	empty image	图片为空，请检查后重新尝试
//            216201	image format error	上传的图片格式错误，现阶段我们支持的图片格式为：PNG、JPG、JPEG、BMP，请进行转码或更换图片
//            216202	image size error	上传的图片大小错误，现阶段我们支持的图片大小为：base64编码后小于4M，分辨率不高于4096*4096，请重新上传图片
//            272000	structure failed	未能匹配模板，请检查参照字段的设置是否符合规范，并重新选取或增加更多的参照字段
//            272001	classify failed	未能成功分类
//            282003	missing parameters: {参数名}	请求参数缺失
//            282004	invalid parameter, appId doesn't own this template nor not launch	您指定的模板暂未发布，请先保存发布该模板，再调用
//            282005	batch  processing error	处理批量任务时发生部分或全部错误，请根据具体错误码排查
//            282006	batch task  limit reached	批量任务处理数量超出限制，请将任务数量减少到10或10以下

        } else {
            // success
            JSONObject data = res.getJSONObject("data");

            // 表示是否结构话成功，true为成功，false为失败；成功时候，返回结构化的识别结果；失败时，如果能识别，返回类似通用文字识别的结果，如果不能识别，返回空
            boolean isStructured = data.getBoolean("isStructured");
            // 使用的模板ID
            String templateSignRecord = data.getString("templateSign");
            // 分类器的得分，直接选择时为1.0F
            double scores = data.getDouble("scores");

            // 匹配失败
            if (!isStructured) {
                System.out.println("匹配失败");
            } else {
                JSONArray ret = data.getJSONArray("ret");
                for (int i = 0; i < ret.length(); i++) {
                    JSONObject retObj = ret.getJSONObject(i);
                    String wordName = retObj.getString("word_name");
                    String word = retObj.getString("word");
                    respData.put(wordName, word);
                }
            }
        }

        for (String key : respData.keySet()) {
            System.out.println(key + " - " + respData.get(key));
        }
    }

}
