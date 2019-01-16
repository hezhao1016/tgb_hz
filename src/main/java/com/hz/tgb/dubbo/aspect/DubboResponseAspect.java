package com.hz.tgb.dubbo.aspect;

import com.hz.tgb.exception.CommonRuntimeException;
import com.hz.tgb.response.Response;
import com.hz.tgb.response.ResponseBuilder;
import com.hz.tgb.response.ResponseCode;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dubbo接口响应切面<br>
 * 适用于接口返回com.oppo.pay.api.response.Response<T> 的情况
 * 
 * @author Yaphis 2017年8月15日 上午10:35:16
 */
public class DubboResponseAspect {

    protected static final Logger LOG = LoggerFactory.getLogger(DubboResponseAspect.class);

    /**
     * 切点
     * 
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    public Object aspect(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed(joinPoint.getArgs());
        } catch (CommonRuntimeException e) {
            LOG.info("bussiness exception:{}", e.toString());
            if (returnResponse(joinPoint)) {
                return ResponseBuilder.createExceptionRes(e);
            }
            throw e;
        } catch (Throwable e) {
            LOG.error("", e);
            if (returnResponse(joinPoint)) {
                return ResponseBuilder.createFailRes(ResponseCode.COMMON_SYSTEM_ERROR, "系统异常");
            }
            throw e;
        }
    }

    /**
     * 判断返回类型是否为指定类型
     * 
     * @param joinPoint
     * @return
     */
    private boolean returnResponse(ProceedingJoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        Class<?> returnType = ((MethodSignature) signature).getReturnType();
        if (returnType.getName().equals(Response.class.getName())) {
            // 如果返回类型是指定类型
            return true;
        }
        return false;
    }
}
