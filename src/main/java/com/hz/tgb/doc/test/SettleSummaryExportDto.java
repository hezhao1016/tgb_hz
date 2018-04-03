package com.hz.tgb.doc.test;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 结算汇总导出 excel dto
 * by hezhao on 2017-08-10
 */
public class SettleSummaryExportDto implements Serializable {

    public SettleSummaryExportDto(String settleMonth, String orderTypeName, BigDecimal price, BigDecimal traleTotal, BigDecimal refundTotal, BigDecimal salesVolume, BigDecimal clearAmount, BigDecimal notClear, BigDecimal dividedProportion, BigDecimal channelCost, BigDecimal taxCost, BigDecimal settleAmount, BigDecimal allIncome, BigDecimal allPlayMoney, String status) {
        this.settleMonth = settleMonth;
        this.orderTypeName = orderTypeName;
        this.price = price;
        this.traleTotal = traleTotal;
        this.refundTotal = refundTotal;
        this.salesVolume = salesVolume;
        this.clearAmount = clearAmount;
        this.notClear = notClear;
        this.dividedProportion = dividedProportion;
        this.channelCost = channelCost;
        this.taxCost = taxCost;
        this.settleAmount = settleAmount;
        this.allIncome = allIncome;
        this.allPlayMoney = allPlayMoney;
        this.status = status;
    }

    public SettleSummaryExportDto() {
        super();
    }

    /**
     * 月份
     */
    private String settleMonth;
    /**
     * 业务类型
     */
    private String orderTypeName;
    /**
     * 订单流水（元）
     */
    private BigDecimal price;
    /**
     * 交易总金额（元）
     */
    private BigDecimal traleTotal;
    /**
     * 退款总金额（元）
     */
    private BigDecimal refundTotal;
    /**
     * 销售额
     */
    private BigDecimal salesVolume;
    /**
     * 分成可币券流水（元）
     */
    private BigDecimal clearAmount;

    /**
     * 不分成可币券流水（元）
     */
    private BigDecimal notClear;

    /**
     * 总分成基数（元）
     */
    private BigDecimal dividedProportion;
    /**
     * 渠道费用（元）
     */
    private BigDecimal channelCost;

    /**
     * 总税费（元）
     */
    private BigDecimal taxCost;
    /**
     * 结算总金额（元）
     */
    private BigDecimal settleAmount;

    /**
     * 总利润（元）
     */
    private BigDecimal allIncome;

    /**
     * 打款总金额（元）
     */
    private BigDecimal allPlayMoney;
    /**
     * 状态
     */
    private String status;

    public String getSettleMonth() {
        return settleMonth;
    }

    public void setSettleMonth(String settleMonth) {
        this.settleMonth = settleMonth;
    }

    public String getOrderTypeName() {
        return orderTypeName;
    }

    public void setOrderTypeName(String orderTypeName) {
        this.orderTypeName = orderTypeName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTraleTotal() {
        return traleTotal;
    }

    public void setTraleTotal(BigDecimal traleTotal) {
        this.traleTotal = traleTotal;
    }

    public BigDecimal getRefundTotal() {
        return refundTotal;
    }

    public void setRefundTotal(BigDecimal refundTotal) {
        this.refundTotal = refundTotal;
    }

    public BigDecimal getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(BigDecimal salesVolume) {
        this.salesVolume = salesVolume;
    }

    public BigDecimal getClearAmount() {
        return clearAmount;
    }

    public void setClearAmount(BigDecimal clearAmount) {
        this.clearAmount = clearAmount;
    }

    public BigDecimal getNotClear() {
        return notClear;
    }

    public void setNotClear(BigDecimal notClear) {
        this.notClear = notClear;
    }

    public BigDecimal getDividedProportion() {
        return dividedProportion;
    }

    public void setDividedProportion(BigDecimal dividedProportion) {
        this.dividedProportion = dividedProportion;
    }

    public BigDecimal getChannelCost() {
        return channelCost;
    }

    public void setChannelCost(BigDecimal channelCost) {
        this.channelCost = channelCost;
    }

    public BigDecimal getTaxCost() {
        return taxCost;
    }

    public void setTaxCost(BigDecimal taxCost) {
        this.taxCost = taxCost;
    }

    public BigDecimal getSettleAmount() {
        return settleAmount;
    }

    public void setSettleAmount(BigDecimal settleAmount) {
        this.settleAmount = settleAmount;
    }

    public BigDecimal getAllIncome() {
        return allIncome;
    }

    public void setAllIncome(BigDecimal allIncome) {
        this.allIncome = allIncome;
    }

    public BigDecimal getAllPlayMoney() {
        return allPlayMoney;
    }

    public void setAllPlayMoney(BigDecimal allPlayMoney) {
        this.allPlayMoney = allPlayMoney;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
