package com.hz.tgb.api.invoice.pdf.test.model;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * <pre>
 * OCR增值税发票识别实体类
 * 数据库表名称：ocr_vat_invoice
 * </pre>
 */
@Data
public class OcrVatInvoice implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 字段名称：发票代码
     *
     * 数据库字段信息:invoice_code VARCHAR(64)
     */
    private String invoiceCode;

    /**
     * 字段名称：发票号码
     *
     * 数据库字段信息:invoice_number VARCHAR(64)
     */
    private String invoiceNumber;

    /**
     * 字段名称：开票日期
     *
     * 数据库字段信息:invoice_date DATE(10)
     */
    private Date invoiceDate;

    /**
     * 字段名称：校验码
     *
     * 数据库字段信息:check_code VARCHAR(64)
     */
    private String checkCode;

    /**
     * 字段名称：机器编号
     *
     * 数据库字段信息:machine_number VARCHAR(64)
     */
    private String machineNumber;

    /**
     * 字段名称：货物名称
     *
     * 数据库字段信息:goods_name VARCHAR(200)
     */
    private String goodsName;

    /**
     * 字段名称：税率
     *
     * 数据库字段信息:tax_rate DECIMAL(6,4)
     */
    private Double taxRate;

    /**
     * 字段名称：价总额-不含税
     *
     * 数据库字段信息:total_without_tax_amount DECIMAL(12,4)
     */
    private Double totalWithoutTaxAmount;

    /**
     * 字段名称：税总额
     *
     * 数据库字段信息:total_tax_amount DECIMAL(12,4)
     */
    private Double totalTaxAmount;

    /**
     * 字段名称：价税合计
     *
     * 数据库字段信息:total_amount DECIMAL(12,4)
     */
    private Double totalWithTaxAmount;

    /**
     * 字段名称：价税合计(大写)
     *
     * 数据库字段信息:total_with_tax_amount_cn VERCHAR(64)
     */
    private String totalWithTaxAmountCn;

    /**
     * 字段名称：发票类型 <br>
     *              01 增值税专用发票<br>
     *              02 货物运输业增值税专用发票<br>
     *              03 机动车销售统一发票<br>
     *              04 增值税普通发票<br>
     *              10 增值税电子普通发票<br>
     *              11 增值税普通发票（卷票）<br>
     * 数据库字段信息:invoice_type VARCHAR(60)
     */
    private String invoiceType;

    /**
     * 字段名称：专票/普票，(0-普票，1-专票)
     *
     * 数据库字段信息:special_flag TINYINT(4)
     */
    private Integer specialFlag;

    /**
     * 字段名称：购买方名称
     *
     * 数据库字段信息:buyer_name VARCHAR(64)
     */
    private String buyerName;

    /**
     * 字段名称：购买方纳税人识别号
     *
     * 数据库字段信息:buyer_taxpayer_code VARCHAR(64)
     */
    private String buyerTaxpayerCode;

    /**
     * 字段名称：购买方地址、电话
     *
     * 数据库字段信息:buyer_address_phone VARCHAR(64)
     */
    private String buyerAddressPhone;

    /**
     * 字段名称：购买方开户行及账号
     *
     * 数据库字段信息:buyer_bank_no VARCHAR(64)
     */
    private String buyerBankNo;

    /**
     * 字段名称：销售方名称
     *
     * 数据库字段信息:seller_name VARCHAR(64)
     */
    private String sellerName;

    /**
     * 字段名称：销售方纳税人识别号
     *
     * 数据库字段信息:seller_taxpayer_code VARCHAR(64)
     */
    private String sellerTaxpayerCode;

    /**
     * 字段名称：销售方地址、电话
     *
     * 数据库字段信息:seller_address_phone VARCHAR(64)
     */
    private String sellerAddressPhone;

    /**
     * 字段名称：销售方开户行及账号
     *
     * 数据库字段信息:seller_bank_no VARCHAR(64)
     */
    private String sellerBankNo;

    /**
     * 字段名称：收款人
     *
     * 数据库字段信息:chamberlain_name VARCHAR(64)
     */
    private String chamberlainName;

    /**
     * 字段名称：复核
     *
     * 数据库字段信息:check_name VARCHAR(64)
     */
    private String checkName;

    /**
     * 字段名称：开票人
     *
     * 数据库字段信息:drawer_name VARCHAR(64)
     */
    private String drawerName;

    /**
     * 字段名称：销售方(章)
     *
     * 数据库字段信息:sales_party VARCHAR(64)
     */
    private String salesParty;

    /**
     * 字段名称：发票备注
     *
     * 数据库字段信息:remark VARCHAR(200)
     */
    private String remark;

    /**
     * 字段名称：发票是否有效（0-作废，1-有效）
     *
     * 数据库字段信息:valid_flag TINYINT(4)
     */
    private Integer validFlag;

    /**
     * 字段名称：是否验真成功（0-未验真，1-验真成功，2-验真失败）
     *
     * 数据库字段信息:verify_flag TINYINT(4)
     */
    private Integer verifyFlag;

    /**
     * 货物明细列表
     */
    private List<OcrVatInvoiceItem> items = new ArrayList<>();

    /** 添加货物
     * @param item
     */
    public void addItem(OcrVatInvoiceItem item) {
        this.items.add(item);
    }

}