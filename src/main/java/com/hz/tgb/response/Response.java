package com.hz.tgb.response;

import java.io.Serializable;

/**
 * 接口返回
 * 
 * @author Yaphis 2015年10月30日 下午3:21:28
 */
public class Response<T> implements Serializable {

    private static final long serialVersionUID = -6806276312394164937L;

    /**
     * 是否成功 true是 false否
     */
    private boolean isSuccess;

    /**
     * 返回码
     */
    private String resCode;

    /**
     * 返回信息
     */
    private String resMsg;

    /**
     * 错误详情,帮助定位请求错误原因
     */
    private String errorDetail;

    /**
     * 返回的具体内容
     */
    private T data;

    public Response() {

    }

    public Response(boolean isSuccess, String resCode, String resMsg, String errorDetail, T data) {
        super();
        this.isSuccess = isSuccess;
        this.resCode = resCode;
        this.resMsg = resMsg;
        this.errorDetail = errorDetail;
        this.data = data;
    }

    public Response(boolean isSuccess, ResponseCode responseCode, String errorDetail, T data) {
        super();
        this.isSuccess = isSuccess;
        this.resCode = responseCode.getErrCode();
        this.resMsg = responseCode.getErrMsg();
        this.errorDetail = errorDetail;
        this.data = data;
    }

    public String getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Response [isSuccess=" + isSuccess + ", " + (resCode != null ? "resCode=" + resCode + ", " : "") + (resMsg != null ? "resMsg=" + resMsg + ", " : "")
                + (errorDetail != null ? "errorDetail=" + errorDetail + ", " : "") + (data != null ? "data=" + data : "") + "]";
    }
}
