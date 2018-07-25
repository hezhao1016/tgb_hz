package com.hz.tgb.api.express;

import java.io.Serializable;

/**
 * 物流轨迹信息
 *
 * Created by hezhao on 2018-07-25 17:52
 */
public class ExpressTrace implements Serializable {

    private static final long serialVersionUID = -7476475935778210958L;

    /** 时间 */
    private String acceptTime;
    /** 描述 */
    private String acceptStation;
    /** 备注 */
    private String remark;

    public ExpressTrace() {
    }

    public ExpressTrace(String acceptTime, String acceptStation, String remark) {
        this.acceptTime = acceptTime;
        this.acceptStation = acceptStation;
        this.remark = remark;
    }

    public String getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getAcceptStation() {
        return acceptStation;
    }

    public void setAcceptStation(String acceptStation) {
        this.acceptStation = acceptStation;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "ExpressTrace{" +
                "acceptTime='" + acceptTime + '\'' +
                ", acceptStation='" + acceptStation + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
