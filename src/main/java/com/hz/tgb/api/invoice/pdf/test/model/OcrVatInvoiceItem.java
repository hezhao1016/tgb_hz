package com.hz.tgb.api.invoice.pdf.test.model;

import lombok.Data;

import java.io.Serializable;


/**
 * <pre>
 * OCR增值税发票识别货物明细实体类
 * 数据库表名称：ocr_vat_invoice_item
 * </pre>
 */
@Data
public class OcrVatInvoiceItem implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 字段名称：ID
     *
     * 数据库字段信息:id BIGINT(20)
     */
    private Long id;

    /**
     * 字段名称：增值税发票ID
     *
     * 数据库字段信息:ocr_vat_invoice_id BIGINT(20)
     */
    private Long ocrVatInvoiceId;

    /**
     * 字段名称：货物或应税劳务、服务名称
     *
     * 数据库字段信息:item_name VARCHAR(64)
     */
    private String itemName;

    /**
     * 字段名称：规格型号
     *
     * 数据库字段信息:specification VARCHAR(64)
     */
    private String specification;

    /**
     * 字段名称：单位
     *
     * 数据库字段信息:unit VARCHAR(64)
     */
    private String unit;

    /**
     * 字段名称：数量
     *
     * 数据库字段信息:quantity INTEGER(11)
     */
    private Integer quantity;

    /**
     * 字段名称：单价
     *
     * 数据库字段信息:unit_price DECIMAL(12,4)
     */
    private Double unitPrice;

    /**
     * 字段名称：金额
     *
     * 数据库字段信息:amount DECIMAL(12,4)
     */
    private Double amount;

    /**
     * 字段名称：税率
     *
     * 数据库字段信息:tax_rate DECIMAL(6,4)
     */
    private Double taxRate;

    /**
     * 字段名称：税额
     *
     * 数据库字段信息:tax_amount DECIMAL(12,4)
     */
    private Double taxAmount;

    /**
     * 字段名称：行号
     *
     * 数据库字段信息:line_number INTEGER(11)
     */
    private Integer lineNumber;

}