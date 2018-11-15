package com.hz.tgb.api.invoice.ocr.hehe;

import cn.hutool.core.util.StrUtil;
import com.hz.tgb.api.invoice.ocr.hehe.util.CommonUtil;
import com.hz.tgb.api.invoice.ocr.hehe.util.HttpUtil;
import com.hz.tgb.json.FastJsonUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 发票验真
 *
 * Created by hezhao on 2018/9/25 15:43
 */
public class TestTrueTruth {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        Map<String, String> postMapSpecial = new HashMap<>();
        postMapSpecial.put("user","username");
        postMapSpecial.put("password","password");
        postMapSpecial.put("invoice_code","044031800104"); // 发票代码，非空
        postMapSpecial.put("invoice_no","04124339"); // 发票号码，非空
        postMapSpecial.put("invoice_date","20180922"); // 开票日期，非空
        postMapSpecial.put("invoice_sum","260.34"); // 总金额(不含税)，非空
        postMapSpecial.put("verify_code","73061489073796572511"); // 校验码，如果为增值税发票专用发票上没有这个信息，可以为空

        Map<String, String> postMapNormal = new HashMap<>();
        postMapNormal.put("user","username");
        postMapNormal.put("password","password");
        postMapNormal.put("invoice_code","044001633111"); // 发票代码，非空
        postMapNormal.put("invoice_no","15874692"); // 发票号码，非空
        postMapNormal.put("invoice_date","20180916"); // 开票日期，非空
        postMapNormal.put("invoice_sum","75.95"); // 总金额(不含税)，非空
        postMapNormal.put("verify_code","46869547952233124630"); // 校验码，如果为增值税发票专用发票上没有这个信息，可以为空

        int size = 10;
        Map<String, String>[] postMaps = new Map[size];
        for (int i = 0; i < size; i++) {
            if (i % 2 == 0){
                postMaps[i] = postMapSpecial; // 增值税普通发票
                continue;
            }
            postMaps[i] = postMapNormal; // 增值税电子普通发票
        }

        for (int i = 0; i < postMaps.length; i++) {
            System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            long s = System.currentTimeMillis();
            verifyVatV2(postMaps[i]);
            long e = System.currentTimeMillis();
            System.out.println("Time["+i+"]: " + (e - s));
        }

        long end = System.currentTimeMillis();
        System.out.println("Time Total: " + (end - start));
    }

    // 验真
    // 增值税发票的验真过程，每次需要约 10 秒时间；建议使用过程中，每张增值税发票请求验后， 10 秒后再次请求验真，可以获取验证结果。
    private static void verifyVatV2(Map<String, String> postMap){
        System.out.println(postMap);
        if (postMap == null || postMap.size() == 0) {
            System.out.println("参数Map为空");
            return;
        }

        try {
            String urlString = "https://imgs-sandbox.intsig.net/icr/verify_vat_v2";

            String json = FastJsonUtil.serialize(postMap);
            String content = HttpUtil.doPostJSON(urlString, json);
            System.out.println(content + "\n");

            if (StringUtils.isBlank(content)) {
                System.out.println("返回为空");
            } else {
                // 解析内容
                Map<String, Object> map = FastJsonUtil.jsonToMap(content);

                if (map == null) {
                    System.out.println("map为空");
                } else {
                    // 返回码
                    String errorNo = CommonUtil.toString(map.get("error_no"));
                    if (!"0".equals(errorNo)) {
                        // 返回信息
                        String errorMessage = CommonUtil.toString(map.get("error_message"));
                        System.out.println("Error: " + errorNo + " - " + errorMessage);
                    } else {

                        // 发票类型,例如：01
                        String vatInvoiceType = CommonUtil.toString(map.get("invoice_type"));
                        // 作废标志， 返回“false”时表示该票验真成功但是已经作废
                        Boolean invoiceValid = CommonUtil.toBoolean(map.get("invoice_valid"));
                        // 验真成功标志，返回“false”时表示该票验真结果为“不一致”
                        Boolean invoiceVerifyResult = CommonUtil.toBoolean(map.get("invoice_verify_result"));

                        if (!invoiceValid || !invoiceVerifyResult) {
                            System.out.println("发票已作废或验真结果不一致");
                        } else {
                            // success

                            // 发票内容数据
                            Map<String, Object> vatInvoiceData = CommonUtil.toMap(map.get("vat_invoice_data"));

                            // 发票代码
                            String invoiceCode = CommonUtil.toString(vatInvoiceData.get("invoice_code"));
                            // 发票号码
                            String invoiceNo = CommonUtil.toString(vatInvoiceData.get("invoice_no"));
                            // 开票日期
                            String invoiceDate = CommonUtil.toString(vatInvoiceData.get("invoice_date"));
                            // 不含税金额
                            String invoiceSumWithoutTax = CommonUtil.toString(vatInvoiceData.get("invoice_sum_without_tax"));
                            // 校验码
                            String verifyCode = CommonUtil.toString(vatInvoiceData.get("verify_code"));
                            // 价税合计
                            String invoiceSumWithTax = CommonUtil.toString(vatInvoiceData.get("invoice_sum_with_tax"));
                            // 税额总计
                            String invoiceTax = CommonUtil.toString(vatInvoiceData.get("invoice_tax"));
                            // 机器编号
                            String machineNo = CommonUtil.toString(vatInvoiceData.get("machine_no"));
                            // 备注区域信息
                            String comment = CommonUtil.toString(vatInvoiceData.get("comment"));

                            // 货物明细信息
                            List<Map<String, Object>> goods = CommonUtil.toList(map.get("goods"));
                            for (Map<String, Object> good : goods) {
                                // 货物或应税劳务、服务名称
                                String unitName = CommonUtil.toString(good.get("unit_name"));
                                // 数量
                                String quantity = CommonUtil.toString(good.get("quantity"));
                                // 金额
                                String unitSum = CommonUtil.toString(good.get("unit_sum"));
                                // 税额
                                String unitTax = CommonUtil.toString(good.get("unit_tax"));
                                // 税率
                                String taxRate = CommonUtil.toString(good.get("tax_rate"));
                                // 规格型号
                                String type = CommonUtil.toString(good.get("type"));
                                // 单位
                                String unit = CommonUtil.toString(good.get("unit"));
                                // 单价
                                String unitPrice = CommonUtil.toString(good.get("unit_price"));


                            }

                            // 购买方信息
                            Map<String, Object> payer = CommonUtil.toMap(map.get("payer"));
                            // 购买方地址、 电话
                            String payerAddress = CommonUtil.toString(payer.get("address"));
                            // 购买方名称
                            String payerName = CommonUtil.toString(payer.get("name"));
                            // 购买方纳税人识别号
                            String payerTaxNumber = CommonUtil.toString(payer.get("tax_number"));
                            // 购买方开户行及账号
                            String payerAccountInfo = CommonUtil.toString(payer.get("account_info"));

                            // 销售方信息
                            Map<String, Object> seller = CommonUtil.toMap(map.get("seller"));
                            // 销售方地址、 电话
                            String sellerAddress = CommonUtil.toString(seller.get("address"));
                            // 销售方名称
                            String sellerName = CommonUtil.toString(seller.get("name"));
                            // 销售方纳税人识别号
                            String sellerTaxNumber = CommonUtil.toString(seller.get("tax_number"));
                            // 销售方开户行及账号
                            String sellerAccountInfo = CommonUtil.toString(seller.get("account_info"));

                        }
                    }
                }


                for (String key : map.keySet()) {
                    System.out.println(key + " - " + map.get(key));
                }
                System.out.println("\n");
                for (String key : map.keySet()) {
                    System.out.println("String " + StrUtil.toCamelCase(key)+" = CommonUtil.toString(map.get(\""+key+"\"));");
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Invoice_type 发票类型返回说明
    01      增值税专用发票
    02      货物运输业增值税专用发票
    03      机动车销售统一发票
    04      增值税普通发票
    10      增值税电子普通发票
    11      增值税普通发票（卷票）

    Error_no 错误码返回说明
    0       验真成功
    40110   超过本日查验次数
    40120   国税接口维护中，暂未获取数据
    40130   其他错误， 请联系技术支持
    */

    /*

    {"error_message":"","error_no":"0","invoice_type":"10","invoice_valid":true,"invoice_verify_result":true,"vat_invoice_data":{"goods":[{"quantity":"1","tax_rate":"16","type":"无","unit":"盒","unit_name":"*果类加工品*百草味 坚果大礼包喜团圆1480g\/盒 中秋礼盒9袋装零食干果组合夏威夷果碧根果","unit_price":"186.207","unit_sum":"186.21","unit_tax":"29.79"},{"quantity":"0","tax_rate":"16","type":"","unit":"","unit_name":"*果类加工品*百草味 坚果大礼包喜团圆1480g\/盒 中秋礼盒9袋装零食干果组合夏威夷果碧根果","unit_price":"0","unit_sum":"-110.26","unit_tax":"-17.64"}],"invoice_code":"044001633111","invoice_date":"20180916","invoice_no":"15874692","invoice_sum_with_tax":"88.1","invoice_sum_without_tax":"75.95","invoice_tax":"12.15","payer":{"account_info":"","address":"","name":"个人"},"seller":{"account_info":"中国银行股份有限公司肇庆鼎湖支行 695170410217","address":"肇庆市鼎湖区桂城新城北八区肇庆新区投资发展有限公司厂房（B幢）138室 020-66215500","name":"肇庆京东盛甲贸易有限公司","tax_number":"91441203MA51UJEM11"},"verify_code":"46869547952233124630"}}

    {"error_message":"","error_no":"0","invoice_type":"04","invoice_valid":true,"invoice_verify_result":true,"vat_invoice_data":{"goods":[{"quantity":"40.37433155080214","tax_rate":"16","type":"92#","unit":"升","unit_name":"*汽油*车用汽油（V）","unit_price":"6.448","unit_sum":"260.34","unit_tax":"41.66"}],"invoice_code":"044031800104","invoice_date":"20180922","invoice_no":"04124339","invoice_sum_with_tax":"302","invoice_sum_without_tax":"260.34","invoice_tax":"41.66","machine_no":"661701415389","payer":{"account_info":"","address":"","name":"跨越速运集团有限公司","tax_number":"91440300665859389F"},"seller":{"account_info":"中行深圳坑梓支行762757953107","address":"深圳市坪山区坑梓街道坪山大道6414号 0755-84127078","name":"深圳市南山石油有限公司沙田加油站","tax_number":"91440300892413870L"},"verify_code":"73061489073796572511"}}

    */

}
