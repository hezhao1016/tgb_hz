package com.hz.tgb.thread;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import java.util.UUID;

/**
 * 线程工具类
 * 
 * @author Yaphis 2017年4月11日 上午10:29:08
 */
public class ThreadUtil {

    public static final String THREAD_NAME = "ThreadName";

    private ThreadUtil() {
    }

    /**
     * 初始化线程名称(生成全局唯一的线程ID)
     */
    public static void initThreadName(String threadName) {
        if (StringUtils.isNotBlank(threadName)) {
            setMDC(THREAD_NAME, threadName);
        } else {
            initThreadName();
        }
    }

    /**
     * 初始化线程名称(生成全局唯一的线程ID)
     */
    public static void initThreadName() {
        setMDC(THREAD_NAME, UUID.randomUUID().toString().replace("-", ""));
    }

    /**
     * 设置MDC
     * 
     * @param key
     * @param value
     */
    public static void setMDC(String key, String value) {
        MDC.put(key, value);
    }

    /**
     * 清除MDC
     */
    public static void clearMDC() {
        MDC.clear();
    }

    /**
     * 获取当前线程名
     * 
     * @return
     */
    public static String getThreadName() {
        String threadName = MDC.get(THREAD_NAME);
        if (null == threadName || threadName.equals("")) {
            threadName = UUID.randomUUID().toString().replace("-", "");
        }
        return threadName;
    }
}
