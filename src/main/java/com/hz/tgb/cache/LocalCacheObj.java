package com.hz.tgb.cache;

/**
 * 本地缓存对象
 * 
 * @author Yaphis 2015年11月9日 下午2:40:48
 */
public class LocalCacheObj<T> {

    private T t;

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
