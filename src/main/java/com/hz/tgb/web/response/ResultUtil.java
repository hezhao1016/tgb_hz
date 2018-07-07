package com.hz.tgb.web.response;

/**
 * 系统返回工具类
 * 
 * @author Yaphis 2015年9月7日 下午3:34:30
 */
public class ResultUtil {

    /**
     * 成功返回
     * 
     * @return
     */
    public static ResponseResult<Object> success() {
        ResponseResult<Object> result = new ResponseResult<Object>();
        return result;
    }

    /**
     * 成功返回
     *
     * @return
     */
    public static ResponseResult<Object> success(Object o) {
        ResponseResult<Object> result = new ResponseResult<Object>();
        result.success(o);
        return result;
    }

    /**
     * 失败返回
     * 
     * @param responseCode
     * @return
     */
    public static ResponseResult<Object> fail(ResponseCode responseCode) {
        ResponseResult<Object> result = new ResponseResult<Object>();
        result.fail(responseCode);
        return result;
    }

    /**
     * 失败返回
     * 
     * @param responseCode
     * @param errMsg
     * @return
     */
    public static ResponseResult<Object> fail(ResponseCode responseCode, String errMsg) {
        ResponseResult<Object> result = new ResponseResult<Object>();
        result.createResponseResult(responseCode.getErrCode(), errMsg);
        return result;
    }

    /**
     * 无权限
     * 
     * @return
     */
    public static ResponseResult<Object> authFail() {
        return fail(ResponseCode.COMMON_AUTHORITY_ERROR);
    }

    /**
     * 异常返回
     * 
     * @param exception
     * @return
     */
    public static ResponseResult<Object> exception(Throwable exception) {
        ResponseResult<Object> result = new ResponseResult<Object>();
        result.exception(exception);
        return result;
    }
}
