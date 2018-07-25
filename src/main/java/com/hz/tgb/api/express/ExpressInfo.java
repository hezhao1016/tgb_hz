package com.hz.tgb.api.express;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 物流信息封装
 *
 * Created by hezhao on 2018-07-25 17:46
 */
public class ExpressInfo implements Serializable {

    private static final long serialVersionUID = 599536270980881827L;

    /** 用户ID */
    private String businessId;
    /** 订单编号 */
    private String orderCode;
    /** 快递公司编码 */
    private String shipperCode;
    /** 快递公司名称 */
    private String shipperName;
    /** 物流运单号 */
    private String logisticCode;
    /** 成功与否 */
    private Boolean success;
    /** 失败原因 */
    private String reason;
    /** 物流状态码 */
    private Integer state;
    /** 物流状态描述 */
    private String stateStr;
    /** 物流轨迹列表 */
    private List<ExpressTrace> expressTraceList = new ArrayList<>();

    public ExpressInfo() {
    }

    public ExpressInfo(String businessId, String orderCode, String shipperCode, String shipperName, String logisticCode, Boolean success, String reason, Integer state, String stateStr, List<ExpressTrace> expressTraceList) {
        this.businessId = businessId;
        this.orderCode = orderCode;
        this.shipperCode = shipperCode;
        this.shipperName = shipperName;
        this.logisticCode = logisticCode;
        this.success = success;
        this.reason = reason;
        this.state = state;
        this.stateStr = stateStr;
        this.expressTraceList = expressTraceList;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getShipperCode() {
        return shipperCode;
    }

    public void setShipperCode(String shipperCode) {
        this.shipperCode = shipperCode;
        this.shipperName = ExpressCompanyUtil.getExpressCompanyName(shipperCode);
    }

    public String getLogisticCode() {
        return logisticCode;
    }

    public void setLogisticCode(String logisticCode) {
        this.logisticCode = logisticCode;
    }

    public String getShipperName() {
        return shipperName;
    }

    public void setShipperName(String shipperName) {
        this.shipperName = shipperName;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getStateStr() {
        return stateStr;
    }

    public void setStateStr(String stateStr) {
        this.stateStr = stateStr;
    }

    public List<ExpressTrace> getExpressTraceList() {
        return expressTraceList;
    }

    public void setExpressTraceList(List<ExpressTrace> expressTraceList) {
        this.expressTraceList = expressTraceList;
    }

    public void addExpressTrace(ExpressTrace expressTrace) {
        if (this.expressTraceList == null) {
            this.expressTraceList = new ArrayList<>();
        }
        this.expressTraceList.add(expressTrace);
    }

    /**
     * 带轨迹信息
     * @return
     */
    @Override
    public String toString() {
        return "ExpressInfo{" +
                "businessId='" + businessId + '\'' +
                ", orderCode='" + orderCode + '\'' +
                ", shipperCode='" + shipperCode + '\'' +
                ", shipperName='" + shipperName + '\'' +
                ", logisticCode='" + logisticCode + '\'' +
                ", success=" + success +
                ", reason='" + reason + '\'' +
                ", state=" + state +
                ", stateStr='" + stateStr + '\'' +
                ", expressTraceList=" + JSON.toJSONString(expressTraceList) +
                '}';
    }

    /**
     * 不带轨迹信息
     * @return
     */
    public String toStringSimple() {
        return "ExpressInfo{" +
                "businessId='" + businessId + '\'' +
                ", orderCode='" + orderCode + '\'' +
                ", shipperCode='" + shipperCode + '\'' +
                ", shipperName='" + shipperName + '\'' +
                ", logisticCode='" + logisticCode + '\'' +
                ", success=" + success +
                ", reason='" + reason + '\'' +
                ", state=" + state +
                ", stateStr='" + stateStr + '\'' +
                '}';
    }
}
