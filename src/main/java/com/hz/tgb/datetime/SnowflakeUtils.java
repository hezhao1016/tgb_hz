package com.hz.tgb.datetime;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class SnowflakeUtils {

    private static final Map<String, Snowflake> snowflakeCache = new HashMap<>();

    public static Snowflake getInstance(long datacenterId, long machineId, String target) {
        if (StringUtils.isEmpty(target)) {
            String msg = "\"target\" is empty.";
            throw new IllegalArgumentException(msg);
        } else {
            target = StringUtils.normalizeSpace(target);
        }

        Snowflake snowflake = snowflakeCache.get(target);
        if (snowflake == null) {
            synchronized (snowflakeCache) {
                if (snowflakeCache.get(target) == null) {
                    snowflake = new Snowflake(datacenterId, machineId);
                    snowflakeCache.put(target, snowflake);
                } else {
                    snowflake = snowflakeCache.get(target);
                }
            }
        }

        return snowflake;
    }
}