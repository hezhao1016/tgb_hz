package com.hz.tgb.data.redis.serializer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.util.Assert;

/**
 * redis序列化器
 * 
 * @author Yaphis 2017年5月23日 下午2:17:39
 */
public class JacksonRedisSerializer implements RedisSerializer<Object> {

    static final byte[] EMPTY_ARRAY = new byte[0];

    private final ObjectMapper mapper;

    public JacksonRedisSerializer() {
        this((String) null);
    }

    public JacksonRedisSerializer(String classPropertyTypeName) {
        this(new ObjectMapper());
        if (StringUtils.isNotBlank(classPropertyTypeName)) {
            mapper.enableDefaultTypingAsProperty(DefaultTyping.NON_FINAL, classPropertyTypeName);
        } else {
            mapper.enableDefaultTyping(DefaultTyping.NON_FINAL, As.PROPERTY);
        }
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public JacksonRedisSerializer(ObjectMapper mapper) {
        Assert.notNull(mapper, "ObjectMapper must not be null!");
        this.mapper = mapper;
    }

    @Override
    public byte[] serialize(Object source) throws SerializationException {
        if (source == null) {
            return EMPTY_ARRAY;
        }
        try {
            return mapper.writeValueAsBytes(source);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Could not write JSON: " + e.getMessage(), e);
        }
    }

    @Override
    public Object deserialize(byte[] source) throws SerializationException {
        return deserialize(source, Object.class);
    }

    public <T> T deserialize(byte[] source, Class<T> type) throws SerializationException {
        Assert.notNull(type, "Deserialization type must not be null! Pleaes provide Object.class to make use of Jackson2 default typing.");
        if (isEmpty(source)) {
            return null;
        }
        try {
            return mapper.readValue(source, type);
        } catch (Exception ex) {
            throw new SerializationException("Could not read JSON: " + ex.getMessage(), ex);
        }
    }

    static boolean isEmpty(byte[] data) {
        return (data == null || data.length == 0);
    }

}
