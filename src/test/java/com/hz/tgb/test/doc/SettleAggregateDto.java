package com.hz.tgb.test.doc;

import java.io.Serializable;
import java.math.BigDecimal;

public class SettleAggregateDto implements Serializable {
    private String startTime;
    private String endTime;
    private Integer userId;
    private String userName;
    private Byte status;
    private Integer isFreeze;
    private Integer userType;
    private Byte orderType;
    private String settleMonth;
    private String applyTime;
    private String updateTime;
    private Long price;
    private BigDecimal channelCost;
    private BigDecimal taxCost;
    private BigDecimal realIncome;
    private String checkTime;
    private String detailUrl;
    private BigDecimal notClear;
    private BigDecimal clearAmount;
    private Long refundTotal;
    private int pageNum;
    private int pageSize;

    public SettleAggregateDto() {
    }

    public int getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Byte getStatus() {
        return this.status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getIsFreeze() {
        return this.isFreeze;
    }

    public void setIsFreeze(Integer isFreeze) {
        this.isFreeze = isFreeze;
    }

    public Integer getUserType() {
        return this.userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Byte getOrderType() {
        return this.orderType;
    }

    public void setOrderType(Byte orderType) {
        this.orderType = orderType;
    }

    public String getSettleMonth() {
        return this.settleMonth;
    }

    public void setSettleMonth(String settleMonth) {
        this.settleMonth = settleMonth;
    }

    public String getApplyTime() {
        return this.applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Long getPrice() {
        return this.price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getCheckTime() {
        return this.checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getDetailUrl() {
        return this.detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public Long getRefundTotal() {
        return this.refundTotal;
    }

    public void setRefundTotal(Long refundTotal) {
        this.refundTotal = refundTotal;
    }

    public BigDecimal getChannelCost() {
        return this.channelCost;
    }

    public void setChannelCost(BigDecimal channelCost) {
        this.channelCost = channelCost;
    }

    public BigDecimal getTaxCost() {
        return this.taxCost;
    }

    public void setTaxCost(BigDecimal taxCost) {
        this.taxCost = taxCost;
    }

    public BigDecimal getRealIncome() {
        return this.realIncome;
    }

    public void setRealIncome(BigDecimal realIncome) {
        this.realIncome = realIncome;
    }

    public BigDecimal getNotClear() {
        return this.notClear;
    }

    public void setNotClear(BigDecimal notClear) {
        this.notClear = notClear;
    }

    public BigDecimal getClearAmount() {
        return this.clearAmount;
    }

    public void setClearAmount(BigDecimal clearAmount) {
        this.clearAmount = clearAmount;
    }
}
