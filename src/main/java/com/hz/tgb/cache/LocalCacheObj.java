package com.hz.tgb.cache;

/**
 * 本地缓存对象
 *
 * @author Yaphis 2015年11月9日 下午2:40:48
 */
public class LocalCacheObj<T> {

    /** 缓存对象 */
    private T t;

    /** 过期时间,单位毫秒 */
    private long expireTime;

    public LocalCacheObj(T t, long expireTime) {
        this.t = t;
        this.expireTime = expireTime;
    }

    public T getT() {
        return t;
    }

    public long getExpireTime() {
        return expireTime;
    }

    @Override
    public String toString() {
        return "LocalCacheObj [t=" + t + ", expireTime=" + expireTime + "]";
    }
}
