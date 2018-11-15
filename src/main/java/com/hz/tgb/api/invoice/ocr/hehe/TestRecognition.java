package com.hz.tgb.api.invoice.ocr.hehe;

import cn.hutool.core.util.StrUtil;
import com.hz.tgb.api.invoice.ocr.hehe.util.CommonUtil;
import com.hz.tgb.api.invoice.ocr.hehe.util.HttpUtil;
import com.hz.tgb.json.FastJsonUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Map;
import java.util.Random;

/**
 * 发票识别
 *
 * 旧
 * https://imgs-sandbox.intsig.net/icr/demo/test_vat_invoice.htm
 * https://imgs-sandbox.intsig.net/icr/demo/test_vehicle_invoice.htm
 *
 * 新
 * https://sh-imgs-sandbox.intsig.net/icr/demo/test_vat_invoice.htm
 * https://sh-imgs-sandbox.intsig.net/icr/demo/test_vehicle_invoice.htm
 *
 * Created by hezhao on 2018/9/25 15:43
 */
public class TestRecognition {

    // 10 次 总用时 26秒
    public static void main(String[] args) {
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
            recognizeVatInvoice(imagePaths[i]);
            long e = System.currentTimeMillis();
            System.out.println("Time["+i+"]: " + (e - s));
        }

        long end = System.currentTimeMillis();
        System.out.println("Time Total: " + (end - start));
    }

    // 发票识别
    // 推荐 jpg 文件设置为： 增值税发票的最短边不低于 1200，图像质量 75 以上，位深度 24。 保证图片质量可以获得更好的识别体验
    // 图片大小不能超过4M
    private static void recognizeVatInvoice(String imagePath){
        System.out.println(imagePath);
        if (StringUtils.isBlank(imagePath)) {
            System.out.println("图片路径为空");
            return;
        }

        try {
            File file = new File(imagePath);
//                String urlString = "https://imgs-sandbox.intsig.net/icr/recognize_vat_invoice?user=username&password=password";
            String urlString = "https://sh-imgs-sandbox.intsig.net/icr/recognize_vat_invoice";

            String content = HttpUtil.doPostFile(urlString, file);
            System.out.println(content + "\n");

            if (StringUtils.isBlank(content)) {
                System.out.println("返回为空");
            } else {
                // 解析内容
                Map<String, Object> map = FastJsonUtil.jsonToMap(content);

                if (map == null) {
                    System.out.println("map为空");
                } else {
                    // 错误码
                    String errorCode = CommonUtil.toString(map.get("error_code"));
                    if (!"0".equals(errorCode)) {
                        // 错误描述
                        String errorMsg = CommonUtil.toString(map.get("error_msg"));
                        System.out.println("Error: " + errorCode + " - " + errorMsg);
                    } else {
                        // success

                        // 校验码
                        String vatInvoiceCorrectCode = CommonUtil.toString(map.get("vat_invoice_correct_code"));
                        // 发票代码
                        String vatInvoiceDaima = CommonUtil.toString(map.get("vat_invoice_daima"));
                        // 发票号码
                        String vatInvoiceHaoma = CommonUtil.toString(map.get("vat_invoice_haoma"));
                        // 开票日期
                        String vatInvoiceIssueDate = CommonUtil.toString(map.get("vat_invoice_issue_date"));

                        // 价合计（不含税）,例如：¥75.95
                        String vatInvoiceTotal = CommonUtil.toString(map.get("vat_invoice_total"));
                        // 税率,例如：16%
                        String vatInvoiceTaxRate = CommonUtil.toString(map.get("vat_invoice_tax_rate"));
                        // 价税合计大写,例如：捌拾捌圆壹角
                        String vatInvoiceTotalCoverTax = CommonUtil.toString(map.get("vat_invoice_total_cover_tax"));
                        // 价税合计小写,例如：¥88.10
                        String vatInvoiceTotalCoverTaxDigits = CommonUtil.toString(map.get("vat_invoice_total_cover_tax_digits"));
                        // 税额合计,例如：¥12.15
                        String vatInvoiceTaxTotal = CommonUtil.toString(map.get("vat_invoice_tax_total"));

                        // 证件类型,例如：增值税发票
                        String type = CommonUtil.toString(map.get("type"));
                        // 发票类型,例如：电子普通发票
                        String vatInvoiceType = CommonUtil.toString(map.get("vat_invoice_type"));
                        // 专票/普票,例如：普票
                        String vatInvoiceZhuanYongFlag = CommonUtil.toString(map.get("vat_invoice_zhuan_yong_flag"));
                        // 备注
                        String vatInvoiceNote = CommonUtil.toString(map.get("vat_invoice_note"));

                        // 购买方名称
                        String vatInvoicePayerName = CommonUtil.toString(map.get("vat_invoice_payer_name"));
                        // 购买方纳税识别号
                        String vatInvoiceRatePayerId = CommonUtil.toString(map.get("vat_invoice_rate_payer_id"));
                        // 购买方地址、电话
                        String vatInvoicePayerAddrTell = CommonUtil.toString(map.get("vat_invoice_payer_addr_tell"));
                        // 购买方开户行及账号
                        String vatInvoicePayerBankAccount = CommonUtil.toString(map.get("vat_invoice_payer_bank_account"));

                        // 销售方名称
                        String vatInvoiceSellerName = CommonUtil.toString(map.get("vat_invoice_seller_name"));
                        // 销售方纳税识别号
                        String vatInvoiceSellerId = CommonUtil.toString(map.get("vat_invoice_seller_id"));
                        // 销售方地址、电话
                        String vatInvoiceSellerAddrTell = CommonUtil.toString(map.get("vat_invoice_seller_addr_tell"));
                        // 销售方开户行及账号
                        String vatInvoiceSellerBankAccount = CommonUtil.toString(map.get("vat_invoice_seller_bank_account"));

                        // 货物或服务名称
                        String vatInvoiceGoodsList = CommonUtil.toString(map.get("vat_invoice_goods_list"));
                        // 金额明细
                        String vatInvoicePriceList = CommonUtil.toString(map.get("vat_invoice_price_list"));
                        // 税率明细
                        String vatInvoiceTaxList = CommonUtil.toString(map.get("vat_invoice_tax_list"));
                        // 税额明细
                        String vatInvoiceTaxRateList = CommonUtil.toString(map.get("vat_invoice_tax_rate_list"));

                        for (String key : map.keySet()) {
                            System.out.println(key + " - " + map.get(key));
                        }
                        System.out.println("\n");
                        for (String key : map.keySet()) {
                            System.out.println("String "+ StrUtil.toCamelCase(key)+" = CommonUtil.toString(map.get(\""+key+"\"));");
                        }

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Http(s) Response Code 值 说明
    200     识别成功。对应返回的 http(s) body 里面 error_code 为 0
    403     识别失败。对应返回的 http(s) body 里面 error_code 可包含三种情况： 40003。
    404     找不到请求识别的服务器，请检查请求识别的服务器 URL 是否正确。
    406     识别失败。对应返回的 http(s) body 里面 error_code 可包含三种情况： 40004， 40005 和 40007。
    500     服务器内部错误，无法正常接收和处理请求。对应返回的http(s) body 里面 error_code 为 90099

    HTTP(s) body 错误码说明
    0       ok 正常返回
    40001   invalid parameter           参数不对
    40002   missing parameter           缺少参数
    40003   invalid user or password    帐号或密码不对
    40004   missing request body        没有 HTTP body
    40005   invalid image format HTTP body 不是图像或者不支持该格式
    40006   invalid image size          图像太大或太小
    40007   fail to recognize           识别失败
    40008   invalid content type        通过 HTTP form 上传图片时，ContentType 无效
    40009   corrupted request body      请求 body 损坏
    40010   fail to extract image       提取图像裸数据失败
    50001   backend down                后台服务器宕机
    50004   timeout                     识别超时
    90099   unknown                     未知错误
    */

    /*
        {
        "vat_invoice_note":"订单号了7918406300\r\n籬甲”矿為\r\n削討+1n3MA5UlEM1LE",
        "vat_invoice_haoma_right_side":"月16日",
        "vat_invoice_haoma":"15874692",
        "vat_invoice_issue_date":"2018年09月16日",
        "vat_invoice_daima":"044001633111",
        "error_code":0,
        "vat_invoice_tax_list":"12.15",
        "vat_invoice_tax_rate":"17%",
        "vat_invoice_daima_right_side":"月16日",
        "vehicle_invoice_total_price":"捌拾捌圆壹角",
        "vat_invoice_payer_name":"纳税人识别号:",
        "vat_invoice_zhuan_yong_flag":"普票",
        "vat_invoice_total_cover_tax_digits":"¥88.10",
        "vat_invoice_correct_code":"46869547952233124630",
        "vat_invoice_total_cover_tax":"捌拾捌圆壹角",
        "vat_invoice_total":"¥75.95",
        "vat_invoice_goods_list":"*果类加工品*百草味坚果大礼包喜\r\n团圆1480吕幢中秋礼盒9袋装零食十\r\n果组合夏威夷果碧根果\r\n*果类加工品*百草味坚果大礼包喜\r\n团圆14800盒中秋礼盒9袋装零食十\r\n果组合夏威夷果碧根果",
        "vat_invoice_tax_rate_list":"17%","type":"增值税发票",
        "vat_invoice_seller_name":"肇庆京东盛甲贸易有限公司",
        "vat_invoice_tax_total":"¥12.15",
        "rotate_angle":0,
        "vat_invoice_price_list":"75.95",
        "vat_invoice_seller_id":"ARKRN991441203MA51UJEM11"
        }
    */

}
