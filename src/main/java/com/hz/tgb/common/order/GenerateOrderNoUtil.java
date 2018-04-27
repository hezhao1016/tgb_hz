/**
 * 
 */
package com.hz.tgb.common.order;

import com.hz.tgb.common.RandomUtil;
import com.hz.tgb.common.StringUtil;
import com.hz.tgb.common.datetime.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 80114515 获取生成随机的订单号
 */
public class GenerateOrderNoUtil {

    /***
     * 获取指定长度的订单号(推荐订单号27位)
     * @param length
     * @return
     */
    public static String getOrderNo(int length) {
        String result = "";
        if (length <= 0) {
            return result;
        }
        SnowFlake snowFlake = SnowFlakeUtils.getInstance(0, length, "LOAN");
        // 雪花算法生成的随机id
        long snowRandowmId = snowFlake.nextId();
        String snowRandowmIdStr = String.valueOf(snowRandowmId);
        int randomLength = snowRandowmIdStr.length();
        if (length < randomLength) {
            result = RandomUtil.getRandomUuidStr(length);
        } else {
            int paddingLen = length - randomLength;
            result = paddingStr(snowRandowmIdStr, '0', paddingLen);
        }

        return result;
    }
    
    /***
     * 获取指定长度含日期的订单号(推荐订单号27位)
     * @param length
     * @return
     */
    public static String getOrderNoWithDate(int length){
        String dateStr = DateUtil.format(new Date(), "yyyyMMdd");
        String randomStr = getOrderNo(length - 8);
        return dateStr + randomStr;
    }

    /***
     * 填充指定长度的字符串,右填充
     * 
     * @param source
     * @param c
     * @param paddingLen
     * @return
     */
    private static String paddingStr(String source, char c, int paddingLen) {
        StringBuffer sb = new StringBuffer(source);
        for (int i = 0; i < paddingLen; i++) {
            sb.append(c);
        }
        return sb.toString();
    }
}

class SnowFlakeUtils {
    private static final Map<String, SnowFlake> snowFlakeCache = new HashMap<>();

    public static SnowFlake getInstance(long datacenterId, long machineId, String target) {
        if (StringUtil.isEmpty(target)) {
            String msg = "\"target\" is empty.";
            throw new IllegalArgumentException(msg);
        } else {
            target = StringUtil.trimToEmpty(target);
        }

        SnowFlake snowFlake = snowFlakeCache.get(target);
        if (snowFlake == null) {
            synchronized (snowFlakeCache) {
                if (snowFlakeCache.get(target) == null) {
                    snowFlake = new SnowFlake(datacenterId, machineId);
                    snowFlakeCache.put(target, snowFlake);
                } else {
                    snowFlake = snowFlakeCache.get(target);
                }
            }
        }

        return snowFlake;
    }
}


/**
 * （1）校对服务器时间时，服务器时间可能会往前或往后跳跃。
 *
 * @author 80117985
 */
class SnowFlake {
    private static final Logger LOGGER = LoggerFactory.getLogger(SnowFlake.class);

    /**
     * 起始的时间戳。
     */
    private final static long START_TIMESTAMP = 1511435554743L;

    /**
     * 序列号占用的位数。
     */
    private final static long SEQUENCE_BITS = 12;

    /**
     * 服务器标识占用的位数。
     */
    private final static long MACHINE_ID_BITS = 5;

    /**
     * 数据中心标识占用的位数。
     */
    private final static long DATACENTER_ID_BITS = 5;

    /**
     * 数据中心标识最大值。
     */
    private final static long MAX_DATACENTER_ID = -1L ^ (-1L << DATACENTER_ID_BITS);

    /**
     * 服务器标识最大值。
     */
    private final static long MAX_MACHINE_ID = -1L ^ (-1L << MACHINE_ID_BITS);

    /**
     * 序列号最大值。
     */
    private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BITS);

    /**
     * 每一部分向左的位移。
     */
    private final static long MACHINE_LEFT = SEQUENCE_BITS;

    private final static long DATACENTER_LEFT = SEQUENCE_BITS + MACHINE_ID_BITS;

    private final static long TIMESTAMP_LEFT = DATACENTER_LEFT + DATACENTER_ID_BITS;

    /**
     * 数据中心标识。
     */
    private long datacenterId;

    /**
     * 服务器标识。
     */
    private long machineId;

    /**
     * 序列号。
     */
    private long sequence = 0L;

    /**
     * 上一次时间戳。
     */
    private long lastTimestamp = -1L;

    /**
     * Measured in milliseconds.
     */
    private long maxTimeDifference = 5000L;

    public SnowFlake(long datacenterId, long machineId) {
        // Normalize parameters.

        if (datacenterId > MAX_DATACENTER_ID || datacenterId < 0) {
            String msg = String.format("datacenterId can't be greater than %d or less than 0", MAX_DATACENTER_ID);
            throw new IllegalArgumentException(msg);
        }

        if (machineId > MAX_MACHINE_ID || machineId < 0) {
            String msg = String.format("machineId can't be greater than %d or less than 0", MAX_MACHINE_ID);
            throw new IllegalArgumentException(msg);
        }

        // Set fields.

        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    public SnowFlake(long datacenterId, long machineId, long maxTimeDifference) {
        this(datacenterId, machineId);

        this.maxTimeDifference = maxTimeDifference;
    }

    /**
     * 产生下一个ID。
     *
     * @return
     */
    public synchronized long nextId() {
        long currentTimestamp = this.getTimestamp();
        if (currentTimestamp < this.lastTimestamp) {
            String msg = "Clock moved backwards.";
            LOGGER.warn(msg);

            currentTimestamp = this.lastTimestamp;
        }

        if (currentTimestamp == this.lastTimestamp) {
            // 相同毫秒内，序列号自增。
            sequence = (sequence + 1) & MAX_SEQUENCE;
            // 同一毫秒的序列数已经达到最大。
            if (sequence == 0L) {
                currentTimestamp = getNextMillis();
            }
        } else {
            // 不同毫秒内，序列号置为0。
            sequence = 0L;
        }

        this.lastTimestamp = currentTimestamp;

        return (currentTimestamp - START_TIMESTAMP) << TIMESTAMP_LEFT // 时间戳。
                | datacenterId << DATACENTER_LEFT // 数据中心标识。
                | machineId << MACHINE_LEFT // 机器标识。
                | sequence; // 序列号。
    }

    private long getNextMillis() {
        long timestamp = this.getTimestamp();
        while ((this.lastTimestamp - timestamp) >= this.maxTimeDifference) {
            String msg = "The difference between last timestamp and current timestamp is great or equal to "
                    + this.maxTimeDifference + " millis.";
            LOGGER.warn(msg);

            timestamp = this.getTimestamp();
        }

        if ((this.lastTimestamp - timestamp) >= 0) {
            timestamp = this.lastTimestamp + 1;
        }

        return timestamp;
    }

    private long getTimestamp() {
        return System.currentTimeMillis();
    }
}