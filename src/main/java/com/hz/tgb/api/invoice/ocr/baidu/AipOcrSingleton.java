package com.hz.tgb.api.invoice.ocr.baidu;

import com.baidu.aip.ocr.AipOcr;

/**
 * AipOcr单例类, 避免重复获取access_token
 *
 * Created by hezhao on 2018/9/26 10:10
 */
public class AipOcrSingleton {

    /** 让构造函数为private，这样该类就不会被实例化 */
    private AipOcrSingleton() {}

    private static volatile AipOcr instance;

    /** 设置APPID/AK/SK，注意不要有空格 */
    public static final String APP_ID = "xxx";
    public static final String API_KEY = "xxx";
    public static final String SECRET_KEY = "xxx";

    /**
     * 获取实例, 双检锁/双重校验锁
     * @return AipOcr
     */
    public static AipOcr getInstance(){
        if(instance == null) {
            synchronized (AipOcr.class){
                if (instance == null){
                    // 初始化一个AipOcr
                    instance = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

                    // 可选：设置网络连接参数
                    instance.setConnectionTimeoutInMillis(2000);
                    instance.setSocketTimeoutInMillis(60000);

                    // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
                    // instance.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
                    // instance.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

                    // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
                    // 也可以直接通过jvm启动参数设置此环境变量
                    // System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");
                }
            }
        }
        return instance;
    }

}
