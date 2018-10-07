package com.hz.tgb.api.invoice.pdf.test;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hz.tgb.datetime.DateUtil;
import org.apache.commons.lang3.StringUtils;
import com.hz.tgb.api.invoice.pdf.constants.ReceiptTaxFlg;
import com.hz.tgb.api.invoice.pdf.parse.PDFReaderUtil;
import com.hz.tgb.api.invoice.pdf.test.model.OcrVatInvoice;
import com.hz.tgb.api.invoice.pdf.test.model.OcrVatInvoiceItem;

import java.util.Date;
import java.util.Random;

/**
 * PDF电子发票解析归档
 *
 * Created by hezhao on 2018/9/18 14:46
 */
public class TestEInvoiceAnalysis {

    // 100 次 总用时 5.5秒
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        int size = 100;
        String[] pdfPaths = new String[size];
        for (int i = 0; i < size; i++) {
            Random rd = new Random(System.nanoTime());
            int temp = rd.nextInt(5);

            switch (temp) {
                case 0:
                    pdfPaths[i] = "D:/360极速浏览器下载/PDF发票解析/发票/京东企业电子发票.pdf";
                    break;
                case 1:
                    pdfPaths[i] = "D:/360极速浏览器下载/PDF发票解析/发票/京东个人电子发票.pdf";
                    break;
                case 2:
                    pdfPaths[i] = "D:/360极速浏览器下载/PDF发票解析/发票/网易严选电子发票.pdf";
                    break;
                case 3:
                    pdfPaths[i] = "D:/360极速浏览器下载/PDF发票解析/发票/小米电子发票.pdf";
                    break;
                case 4:
                    pdfPaths[i] = "D:/360极速浏览器下载/PDF发票解析/发票/中国电信电子发票.pdf";
                    break;
            }
        }

        for (int i = 0; i < pdfPaths.length; i++) {
            System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            long s = System.currentTimeMillis();
            recognizeVatInvoice(pdfPaths[i]);
            long e = System.currentTimeMillis();
            System.out.println("Time["+i+"]: " + (e - s));
        }

        long end = System.currentTimeMillis();
        System.out.println("Time Total: " + (end - start));
    }

    /**
     * 解析发票内容
     */
    private static void recognizeVatInvoice(String pdfPath){
        System.out.println(pdfPath);
        if (StringUtils.isBlank(pdfPath)) {
            System.out.println("PDF路径为空");
            return;
        }

        try {
            JSONObject json = PDFReaderUtil.readReceiptPdf(pdfPath);
            System.out.println(json.toJSONString());

            OcrVatInvoice ocrVatInvoice = new OcrVatInvoice();
            ocrVatInvoice.setId(RandomUtil.randomLong(1000, 10000));
            // 发票代码
            ocrVatInvoice.setInvoiceCode(json.getString("fpdm"));
            // 发票号码
            ocrVatInvoice.setInvoiceNumber(json.getString("fphm"));
            // 开票日期
            String kprq = json.getString("kprq");
            Date invoiceDate = DateUtil.parse(kprq);
            ocrVatInvoice.setInvoiceDate(invoiceDate);
            // 校验码
            ocrVatInvoice.setCheckCode(json.getString("jym"));
            // 机器编号
            ocrVatInvoice.setMachineNumber(json.getString("jqbh"));
            // 货物名称
            ocrVatInvoice.setGoodsName(json.getString("hwmc"));
            // 税率
            ocrVatInvoice.setTaxRate(json.getDouble("slv") / 100.0);
            // 价总额-不含税
            ocrVatInvoice.setTotalWithoutTaxAmount(json.getDouble("hjse"));
            // 税总额
            ocrVatInvoice.setTotalTaxAmount(json.getDouble("hjje"));
            // 价税合计
            ocrVatInvoice.setTotalWithTaxAmount(json.getDouble("kpje"));
            // 价税合计(大写)
            ocrVatInvoice.setTotalWithTaxAmountCn(json.getString("kpjecn"));
            // 发票类型
            ocrVatInvoice.setInvoiceType(json.getString("fplx"));
            // 专票/普票，(0-普票，1-专票)
            ocrVatInvoice.setSpecialFlag(ReceiptTaxFlg.getSpecialFlag(ocrVatInvoice.getInvoiceType()));
            // 购买方名称
            ocrVatInvoice.setBuyerName(json.getString("spfmc"));
            // 购买方纳税人识别号
            ocrVatInvoice.setBuyerTaxpayerCode(json.getString("spfsbh"));
            // 购买方地址、电话
            ocrVatInvoice.setBuyerAddressPhone(json.getString("spfdzdh"));
            // 购买方开户行及账号
            ocrVatInvoice.setBuyerBankNo(json.getString("spfyhzh"));
            // 销售方名称
            ocrVatInvoice.setSellerName(json.getString("kpfmc"));
            // 销售方纳税人识别号
            ocrVatInvoice.setSellerTaxpayerCode(json.getString("kpfsbh"));
            // 销售方地址、电话
            ocrVatInvoice.setSellerAddressPhone(json.getString("kpfdzdh"));
            // 销售方开户行及账号
            ocrVatInvoice.setSellerBankNo(json.getString("kpfyhzh"));
            // 收款人
            ocrVatInvoice.setChamberlainName(json.getString("skr"));
            // 复核
            ocrVatInvoice.setCheckName(json.getString("fh"));
            // 开票人
            ocrVatInvoice.setDrawerName(json.getString("kpr"));
            // 销售方(章)
            ocrVatInvoice.setSalesParty(json.getString("xsf"));
            // 发票备注
            ocrVatInvoice.setRemark("");
            // 发票是否有效（0-作废，1-有效）
            ocrVatInvoice.setValidFlag(1);
            // 是否验真成功（0-未验真，1-验真成功，2-验真失败）
            ocrVatInvoice.setVerifyFlag(0);

            // 货物明细
            JSONArray items = json.getJSONArray("hwmxs");
            if (items != null) {
                for (int i = 0; i < items.size(); i++) {
                    JSONObject item = items.getJSONObject(i);
                    OcrVatInvoiceItem ocrVatInvoiceItem = new OcrVatInvoiceItem();
                    ocrVatInvoiceItem.setId(RandomUtil.randomLong(1000, 10000));
                    ocrVatInvoiceItem.setOcrVatInvoiceId(ocrVatInvoice.getId());
                    ocrVatInvoiceItem.setItemName(item.getString("hwmc"));
                    ocrVatInvoiceItem.setSpecification(item.getString("ggxh"));
                    ocrVatInvoiceItem.setUnit(item.getString("dw"));
                    ocrVatInvoiceItem.setQuantity(item.getInteger("sl"));
                    ocrVatInvoiceItem.setUnitPrice(item.getDouble("dj"));
                    ocrVatInvoiceItem.setAmount(item.getDouble("je"));
                    ocrVatInvoiceItem.setTaxRate(item.getDouble("slv"));
                    ocrVatInvoiceItem.setTaxAmount(item.getDouble("se") / 100.0);
                    ocrVatInvoiceItem.setLineNumber(item.getInteger("hh"));

                    ocrVatInvoice.addItem(ocrVatInvoiceItem);
                }
            }

            System.out.println(ocrVatInvoice);
            for (OcrVatInvoiceItem ocrVatInvoiceItem : ocrVatInvoice.getItems()) {
                System.out.println("=================================================");
                System.out.println(ocrVatInvoiceItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
