package com.hz.tgb.datetime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * （1）校对服务器时间时，服务器时间可能会往前或往后跳跃。
 * 
 * @author 80117985
 */
public class Snowflake {
    private static final Logger LOGGER = LoggerFactory.getLogger(Snowflake.class);

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

    public Snowflake(long datacenterId, long machineId) {
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

    public Snowflake(long datacenterId, long machineId, long maxTimeDifference) {
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