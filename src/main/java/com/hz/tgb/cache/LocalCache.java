package com.hz.tgb.cache;

import com.hz.tgb.entity.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义本地缓存，可应用于简单的缓存场景。<br>
 * 注意事项：1.多节点部署时，该缓存不共享，建议使用集中式缓存 2.数据刷新时，需要主动设置该缓存过期
 *
 * @author Yaphis 2015年11月9日 下午2:13:53
 */
public class LocalCache {

    private static final Map<String, LocalCacheObj<Object>> cacheMap = new HashMap<String, LocalCacheObj<Object>>();

    private static final Logger LOG = LoggerFactory.getLogger(LocalCache.class);

    /** 默认的缓存时间[7天],单位毫秒 */
    private static final long DEFAULT_EXPIRE = 7 * 24 * 60 * 60 * 1000;

    /** 默认的最大缓存个数[20万个] */
    private static final long MAX_COUNT = 200000;

    private LocalCache() {
        // 私有类构造方法
    }

    /**
     * 插入缓存
     *
     * @param key
     * @param t
     */
    public static <T> void insertCache(String key, T t) {
        // 这里简单设计 防止缓存过大导致内存溢出
        if (cacheMap.size() >= MAX_COUNT) {
            LOG.error("current cacheMap size:{},has up to limit!", cacheMap.size());
            return;
        }
        long expireTime = System.currentTimeMillis() + DEFAULT_EXPIRE;
        LocalCacheObj<Object> localCache = new LocalCacheObj<Object>(t, expireTime);
        cacheMap.put(key, localCache);
    }

    /**
     * 删除缓存
     *
     * @param key
     */
    public static void deleteCache(String key) {
        cacheMap.remove(key);
    }

    /**
     * 查询缓存
     *
     * @param key
     * @return
     */
    public static LocalCacheObj<Object> queryCache(String key) {
        LocalCacheObj<Object> localCache = cacheMap.get(key);
        if (null != localCache) {
            long nowTime = System.currentTimeMillis();
            if (nowTime > localCache.getExpireTime()) {
                LOG.info("key:{} has been expired!", key);
                cacheMap.remove(key);
                return null; // 缓存已经过期
            } else {
                return localCache;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        LocalCache.insertCache("name", "jack");
        Student student = new Student();
        student.setName("bob");
        LocalCache.insertCache("student", student);

        System.out.println(LocalCache.queryCache("name"));
        System.out.println(LocalCache.queryCache("student"));

        LocalCache.deleteCache("name");

        System.out.println(LocalCache.queryCache("name"));
        System.out.println(LocalCache.queryCache("student"));
    }
}
