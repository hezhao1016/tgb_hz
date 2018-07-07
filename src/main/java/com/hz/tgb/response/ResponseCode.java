package com.hz.tgb.response;

/**
 * 系统返回码
 * 
 * @author Yaphis 2015年11月6日 上午10:21:31
 */
public class ResponseCode {

    // 公用返回码
    public static final ResponseCode COMMON_SUCCESS = new ResponseCode("000000", "成功");
    public static final ResponseCode COMMON_SYSTEM_ERROR = new ResponseCode("000001", "系统异常");
    public static final ResponseCode COMMON_PARAMS_MISSING = new ResponseCode("100001", "请求参数不全");
    public static final ResponseCode COMMON_PARAMS_ILLEGAL = new ResponseCode("100002", "请求参数非法");
    public static final ResponseCode COMMON_ILLEGAL_SIGN = new ResponseCode("100003", "签名错误");
    public static final ResponseCode COMMON_INVOKE_FAIL = new ResponseCode("100004", "调用失败");

    /**
     * 错误码
     */
    private String errCode;
    /**
     * 错误信息
     */
    private String errMsg;

    public ResponseCode(String errCode, String errMsg) {
        super();
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    @Override
    public String toString() {
        return "ResponseCode [" + (errCode != null ? "errCode=" + errCode + ", " : "") + (errMsg != null ? "errMsg=" + errMsg : "") + "]";
    }
}
