package com.hz.tgb.exception;

import com.hz.tgb.response.ResponseCode;

/**
 * 带错误码的异常
 * 
 * @author Yaphis 2017年2月2日 上午11:26:24
 */
public class CommonException extends Exception {

    private static final long serialVersionUID = -4040912499757651507L;
    /**
     * 错误码
     */
    private String errCode;
    /**
     * 错误信息
     */
    private String errMsg;
    /**
     * 详情
     */
    private String detail;

    public CommonException(String errCode, String errMsg, String detail) {
        this.errCode = errCode;
        this.errMsg = errMsg;
        this.detail = detail;
    }

    public CommonException(String errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public CommonException(ResponseCode responseCode) {
        this.errCode = responseCode.getErrCode();
        this.errMsg = responseCode.getErrMsg();
    }

    public CommonException(ResponseCode responseCode, String detail) {
        this.errCode = responseCode.getErrCode();
        this.errMsg = responseCode.getErrMsg();
        this.detail = detail;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "CommonException [" + (errCode != null ? "errCode=" + errCode + ", " : "") + (errMsg != null ? "errMsg=" + errMsg + ", " : "") + (detail != null ? "detail=" + detail : "") + "]";
    }
}
