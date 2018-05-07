package com.hz.tgb.data.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.Resource;

/**
 * Created by hezhao on 16/6/24.
 */
@Service("redisPool")
public class RedisPool {

    private static Logger logger = LoggerFactory.getLogger(RedisPool.class);

    /**
     * if use redis, should check these files:
     * (close injecting code, for no need to start redis-server every time)
     *
     * applicationContext.xml
     * RedisPool.java
     * SysCacheService.java
     */
    @Resource(name = "shardedJedisPool")
    private ShardedJedisPool shardedJedisPool;

    public ShardedJedis instance() {
        return shardedJedisPool.getResource();
    }

    public void safeClose(ShardedJedis shardedJedis) {
        try {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        } catch (Exception e) {
            logger.error("return redis resource exception", e);
        }
    }
}