package com.hz.tgb.dubbo.filter;

import ch.qos.logback.classic.Level;
import com.alibaba.dubbo.rpc.*;
import com.hz.tgb.exception.CommonRuntimeException;
import com.hz.tgb.response.Response;
import com.hz.tgb.response.ResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

/**
 * 日志过滤器-toString()格式打印<br>
 * 该格式打印的方便调用者自己决定哪些属性需要打印处理,主要应用在返回有大量集合的情况下。基于性能考虑可能只需要打印size就可以了.
 * 
 * @author Yaphis 2018年1月4日 下午2:59:41
 */
public class LogbackToStringLogFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(LogbackToStringLogFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        long startTime = System.currentTimeMillis();
        Result result = null;
        boolean needClearFlag = true;
        Result newResult = null;
        boolean isCommonResponse = false;
        try {
            needClearFlag = initThreadName();
            isCommonResponse = returnResponse(invocation);
            result = invoker.invoke(invocation);
            return result;
        } catch (Throwable e) {
            if (e instanceof CommonRuntimeException) {
                LOG.info("{},{}", e.toString(), e.getStackTrace()[0]);// 自定义异常 不打印堆栈信息
            } else {
                LOG.error("{}", e);// 其他异常 打印堆栈信息
            }
            if (isCommonResponse) {
                // 如果是通用返回
                newResult = createResult(result, e);
                return newResult;
            }
            throw e;// 其他情况直接抛出异常
        } finally {
            try {
                Throwable throwable = (result == null) ? null : result.getException();
                Object resultObj = null;
                if (null != newResult) {
                    resultObj = newResult.getValue();
                } else {
                    resultObj = (result == null) ? null : result.getValue();
                }
                String resultStr = resultObj == null ? null : resultObj.toString().trim().replace("\n", "").replace("\r", "").replace("\t", "");
                String paramStr = Arrays.toString(invocation.getArguments()).trim().replace("\n", "").replace("\r", "").replace("\t", "");
                long costTime = System.currentTimeMillis() - startTime;
                if (null == throwable) {
                    // 没有抛出异常的情况 判断需要打印的日志级别
                    Level level = getLogLevel(resultObj);
                    if (level.equals(Level.WARN)) {
                        LOG.warn("{}.{}() param:{}, return:{}, cost:{} ms!", invoker.getInterface(), invocation.getMethodName(), paramStr, resultStr, costTime);
                    } else {
                        LOG.info("{}.{}() param:{}, return:{}, cost:{} ms!", invoker.getInterface(), invocation.getMethodName(), paramStr, resultStr, costTime);
                    }
                } else {
                    // 抛出异常的情况 打印警告
                    LOG.warn("{}.{}() param:{}, return:{}, exception:{}, cost:{} ms!", invoker.getInterface(), invocation.getMethodName(), paramStr, resultStr, throwable, costTime);
                }
            } catch (Exception e) {
                LOG.error("", e);
            }
            if (needClearFlag) {
                clearMDC();
            }
        }
    }

    /**
     * 获取日志级别
     * 
     * @param resultObj
     * @return
     */
    private Level getLogLevel(Object resultObj) {
        if (null == resultObj) {
            // 如果返回结果是空 那么打印INFO级别日志,(可能是一个void返回的方法)
            return Level.INFO;
        }
        if (!(resultObj instanceof Response)) {
            // 如果是不是通用返回 那么打印INFO级别日志
            return Level.INFO;
        }
        Response<?> response = (Response<?>) resultObj;
        if (!response.isSuccess()) {
            // 如果接口调用失败 那么打印警告日志
            return Level.WARN;
        } else {
            // 如果接口调用成功 那么打印INFO级别日志
            return Level.INFO;
        }
    }

    /**
     * 生成异常返回
     * 
     * @param oldResult
     * @param throwable
     * @return
     */
    private Result createResult(final Result oldResult, final Throwable throwable) {
        return new Result() {

            @Override
            public Object recreate() throws Throwable {
                return getValue();
            }

            @Override
            public boolean hasException() {
                return false;
            }

            @Override
            public Object getValue() {
                return ResponseBuilder.createExceptionRes(throwable);
            }

            @Override
            public Object getResult() {
                return getValue();
            }

            @Override
            public Throwable getException() {
                return null;
            }

            @Override
            public Map<String, String> getAttachments() {
                return null != oldResult ? oldResult.getAttachments() : Collections.<String, String> emptyMap();
            }

            @Override
            public String getAttachment(String key, String defaultValue) {
                return null != oldResult ? oldResult.getAttachment(key, defaultValue) : defaultValue;
            }

            @Override
            public String getAttachment(String key) {
                return null != oldResult ? oldResult.getAttachment(key) : null;
            }
        };
    }

    /**
     * 判断返回类型是否为指定类型
     * 
     * @param invocation
     * @return
     */
    private boolean returnResponse(Invocation invocation) {
        try {
            Class<?> returnType = invocation.getInvoker().getInterface().getMethod(invocation.getMethodName(), invocation.getParameterTypes()).getReturnType();
            if (returnType.getName().equals(Response.class.getName())) {
                return true;// 如果返回类型是指定类型
            }
        } catch (Exception e) {
            LOG.error("", e);
        }
        return false;
    }

    /**
     * 初始化线程名称(生成全局唯一的线程ID)
     */
    private boolean initThreadName() {
        String threadName = MDC.get("ThreadName");
        if (null == threadName || "".equals(threadName)) {
            // 不存在线程名
            MDC.put("ThreadName", UUID.randomUUID().toString().replace("-", ""));// 设置日志线程名称
            return true;
        } else {
            // 如果已经存在线程名 说明这个方法是在调用链中 不需要设置
            return false;
        }
    }

    /**
     * 清除MDC
     */
    public static void clearMDC() {
        MDC.clear();
    }
}
