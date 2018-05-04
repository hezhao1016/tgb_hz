package com.hz.tgb.test.doc;

import java.io.Serializable;

/**
 * Created by hezhao on 2017-08-08.
 */
public class SettleFileDto implements Serializable {

    //结算月
    private String settleMonth;

    //订单类型 0下载付费 1游戏中心 2内嵌支付 3主题商店 4直充
    private Byte orderType;

    //开发者id
    private int userId;

    //商品名称
    private String productName;

    //销售流水
    private Long price;

    //销量 商品数量
    private int buyCount;

    //渠道成本
    private Long channelCost;

    //分成比例
    private double shareRatio;

    //平台所得
    private Long platformCost;
    //开发者所得
    private Long userCost;

    //应用名称
    private String appName;

    //不分成金额
    private Long notClear;
    //分成基数
    private Long shareBaseCost;

    //税费
    private Long ratioCost;

    //合作方分成金额
    private Long realIncome;

    //包名
    private String packageName;

    //单价
    private double unitPrice;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getSettleMonth() {
        return settleMonth;
    }

    public void setSettleMonth(String settleMonth) {
        this.settleMonth = settleMonth;
    }

    public Byte getOrderType() {
        return orderType;
    }

    public void setOrderType(Byte orderType) {
        this.orderType = orderType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public int getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(int buyCount) {
        this.buyCount = buyCount;
    }

    public Long getChannelCost() {
        return channelCost;
    }

    public void setChannelCost(Long channelCost) {
        this.channelCost = channelCost;
    }

    public double getShareRatio() {
        return shareRatio;
    }

    public void setShareRatio(double shareRatio) {
        this.shareRatio = shareRatio;
    }

    public Long getPlatformCost() {
        return platformCost;
    }

    public void setPlatformCost(Long platformCost) {
        this.platformCost = platformCost;
    }

    public Long getUserCost() {
        return userCost;
    }

    public void setUserCost(Long userCost) {
        this.userCost = userCost;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Long getNotClear() {
        return notClear;
    }

    public void setNotClear(Long notClear) {
        this.notClear = notClear;
    }

    public Long getShareBaseCost() {
        return shareBaseCost;
    }

    public void setShareBaseCost(Long shareBaseCost) {
        this.shareBaseCost = shareBaseCost;
    }

    public Long getRatioCost() {
        return ratioCost;
    }

    public void setRatioCost(Long ratioCost) {
        this.ratioCost = ratioCost;
    }

    public Long getRealIncome() {
        return realIncome;
    }

    public void setRealIncome(Long realIncome) {
        this.realIncome = realIncome;
    }
}
