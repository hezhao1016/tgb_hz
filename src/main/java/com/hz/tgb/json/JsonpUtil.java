package com.hz.tgb.json;

import com.alibaba.fastjson.JSON;

/**
 * JsonP工具类
 *
 * @Author hezhao
 * @Time 2018-07-07 23:52
 */
public class JsonpUtil {

    /**
     * 用于做JSONP回调函数拼接
     * @param back 回调函数
     * @param result 返回结果
     * @return 返回拼接后的字符串
     */
    public static String callbackStr(String back, String result){
        // 返回字符串拼接
        return back+"("+result+");";
    }

    /**
     * 用于做JSONP回调函数拼接，回调函数默认为callback
     * @param result 返回结果
     * @return 返回拼接后的字符串
     */
    public static String callbackStr(String result){
        String back = "callback";
        return callbackStr(back, result);
    }

    /**
     * 用于做JSONP回调函数拼接
     * @param back 回调函数
     * @param result 返回结果对象，将会转换为JSON字符串
     * @return 返回拼接后的字符串
     */
    public static String callbackStr(String back, Object result){
        String resultStr = JSON.toJSONString(result);
        return callbackStr(back, resultStr);
    }

    /**
     * 用于做JSONP回调函数拼接，回调函数默认为callback
     * @param result 返回结果对象，将会转换为JSON字符串
     * @return 返回拼接后的字符串
     */
    public static String callbackStr(Object result){
        String back = "callback";
        return callbackStr(back, result);
    }

}
