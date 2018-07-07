package com.hz.tgb.exception;

import com.hz.tgb.response.ResponseCode;

/**
 * 带错误码的运行异常
 * 
 * @author Yaphis 2017年2月2日 上午11:30:25
 */
public class CommonRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 8573859443181413985L;
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

    /**
     * 异常数据
     */
    private Object data;

    public CommonRuntimeException(String errCode, String errMsg, String detail, Object data) {
        super();
        this.errCode = errCode;
        this.errMsg = errMsg;
        this.detail = detail;
        this.data = data;
    }

    public CommonRuntimeException(String errCode, String errMsg, String detail) {
        this.errCode = errCode;
        this.errMsg = errMsg;
        this.detail = detail;
    }

    public CommonRuntimeException(String errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public CommonRuntimeException(ResponseCode responseCode) {
        this.errCode = responseCode.getErrCode();
        this.errMsg = responseCode.getErrMsg();
    }

    public CommonRuntimeException(ResponseCode responseCode, String detail) {
        this.errCode = responseCode.getErrCode();
        this.errMsg = responseCode.getErrMsg();
        this.detail = detail;
    }

    public CommonRuntimeException(ResponseCode responseCode, String detail, Object data) {
        this.errCode = responseCode.getErrCode();
        this.errMsg = responseCode.getErrMsg();
        this.detail = detail;
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
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
        return "CommonRuntimeException [" + (errCode != null ? "errCode=" + errCode + ", " : "") + (errMsg != null ? "errMsg=" + errMsg + ", " : "") + (detail != null ? "detail=" + detail + ", " : "")
                + (data != null ? "data=" + data : "") + "]";
    }

}
