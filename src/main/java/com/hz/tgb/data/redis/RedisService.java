package com.hz.tgb.data.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * Redis服务
 * 
 * @author Yaphis 2015年7月19日 下午10:57:47
 */
public class RedisService {

    private RedisTemplate<String, String> redisTemplate;

    /**
     * 保存对象
     * 
     * @param key
     * @param t
     * @return
     */
    public <T> boolean save(final String key, final T t) {
        return save(key, t, 0);
    }

    /**
     * 保存对象
     * 
     * @param key
     * @param t
     * @param seconds
     * @return
     */
    public <T> boolean save(final String key, final T t, final long seconds) {
        String tStr = JSON.toJSONString(t);
        return save(key, tStr, seconds);
    }

    /**
     * 获取对象
     * 
     * @param key
     * @param clazz
     * @return
     */
    public <T> T get(final String key, Class<T> clazz) {
        String tStr = get(key);
        return JSON.parseObject(tStr, clazz);
    }

    /**
     * 保存键值对
     * 
     * @param key
     * @param value
     * @return
     */
    public boolean save(final String key, final String value) {
        return save(key, value, 0);
    }

    /**
     * 保存键值对,如果存在的话，会被覆盖
     * 
     * @param key
     * @param value
     * @param seconds 过期时间(秒)
     * @return
     */
    public boolean save(final String key, final String value, final long seconds) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {

            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyByte = redisTemplate.getStringSerializer().serialize(key);
                byte[] valueByte = redisTemplate.getStringSerializer().serialize(value);
                if (seconds > 0) {
                    connection.setEx(keyByte, seconds, valueByte);
                } else {
                    connection.set(keyByte, valueByte);
                }

                return Boolean.TRUE;
            }
        });
        return result;
    }

    /**
     * 如果不存在则保存
     * 
     * @param key
     * @param value
     * @return
     */
    public boolean saveNX(final String key, final String value) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {

            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyByte = redisTemplate.getStringSerializer().serialize(key);
                byte[] valueByte = redisTemplate.getStringSerializer().serialize(value);
                return connection.setNX(keyByte, valueByte);
            }
        });
        return result;
    }

    /**
     * 获取key
     * 
     * @param key
     * @return
     */
    public String get(final String key) {
        String result = redisTemplate.execute(new RedisCallback<String>() {

            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyByte = redisTemplate.getStringSerializer().serialize(key);
                byte[] valueByte = connection.get(keyByte);
                return redisTemplate.getStringSerializer().deserialize(valueByte);
            }
        });
        return result;
    }

    /**
     * 删除key
     * 
     * @param key
     * @return
     */
    public Long delete(final String key) {
        Long count = redisTemplate.execute(new RedisCallback<Long>() {

            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyByte = redisTemplate.getStringSerializer().serialize(key);
                return connection.del(keyByte);
            }
        });
        return count;
    }

    /**
     * 检查key是否存在
     * 
     * @param key
     * @return
     */
    public boolean exist(final String key) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {

            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyByte = redisTemplate.getStringSerializer().serialize(key);
                return connection.exists(keyByte);
            }
        });
        return result;
    }

    /**
     * 设置过期时间
     * 
     * @param key
     * @param seconds 秒
     * @return key不存在时返回false
     */
    public boolean expire(final String key, final long seconds) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {

            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyByte = redisTemplate.getStringSerializer().serialize(key);
                return connection.expire(keyByte, seconds);
            }
        });
        return result;
    }

    /**
     * 返回key的剩余生存时间，key不存在则返回-2,key存在但没有设置剩余时间则返回-1
     * 
     * @param key
     * @return 返回剩余生存时间(秒)
     */
    public long ttl(final String key) {
        long timeToLive = redisTemplate.execute(new RedisCallback<Long>() {

            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyByte = redisTemplate.getStringSerializer().serialize(key);
                return connection.ttl(keyByte);
            }
        });
        return timeToLive;
    }

    public List<String> list(final String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 从set中获取值
     * 
     * @param key
     * @return
     */
    public String popFromSet(String key) {
        return redisTemplate.opsForSet().pop(key);
    }

    /**
     * 保存列表(从左至右)
     * 
     * @param key
     * @param list
     * @param seconds 超时时间
     * @return
     */
    public <T> long addToSet(String key, long seconds, String... set) {
        long result = redisTemplate.opsForSet().add(key, set);
        if (seconds > 0) {
            expire(key, seconds);
        }
        return result;
    }

    /**
     * 保存列表（从左至右）
     * 
     * @param key
     * @param set
     * @return
     */
    public long addToSet(String key, String... set) {
        return addToSet(key, -1, set);
    }

    /**
     * 是否存在
     * 
     * @param key
     * @param element
     * @return
     */
    public boolean existInSet(String key, String element) {
        return redisTemplate.opsForSet().isMember(key, element);
    }

    /**
     * 自增计数器<br>
     * 如果key不存在,则会返回1(自动设置为0)
     * 
     * @param key
     * @param delta
     * @return
     */
    public long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    public RedisTemplate<String, String> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
