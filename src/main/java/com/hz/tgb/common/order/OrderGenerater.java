package com.hz.tgb.common.order;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 订单号生成器
 * 
 * @author Yaphis 2015年4月29日 下午7:12:44
 * 
 * 订单号生成工具类 - Yaphis的个人页面
 * https://my.oschina.net/yaphis/blog/408933
 */
public class OrderGenerater {

    private static final Logger LOG = LoggerFactory.getLogger(OrderGenerater.class);

    private volatile static int serialNo = 0;

    private static final String FORMATSTRING = "yyMMddHHmmssSSS";

    /**
     * 使用公平锁防止饥饿
     */
    private static final Lock lock = new ReentrantLock(true);

    private static final int TIMEOUTSECODES = 3;
    
    private OrderGenerater() {
		// 私有类构造方法
	}

    /**
     * 生成订单号，生成规则 时间戳+机器IP最后两位+2位随机数+两位自增序列 <br>
     * 采用可重入锁减小锁持有的粒度，提高系统在高并发情况下的性能
     * 
     * @return
     */
    public static String generateOrder() {
        StringBuilder builder = new StringBuilder();
        builder.append(getDateTime(FORMATSTRING)).append(getLastNumOfIP());
        builder.append(getRandomNum()).append(getIncrement());
        return builder.toString();
    }

    /**
     * 获取系统当前时间
     * 
     * @param formatStr
     * @return
     */
    private static String getDateTime(String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return format.format(new Date());
    }

    /**
     * 获取自增序列
     * 
     * @return
     */
    private static String getIncrement() {
        int tempSerialNo = 0;
        try {
            if (lock.tryLock(TIMEOUTSECODES, TimeUnit.SECONDS)) {
                if (serialNo >= 99) {
                    serialNo = 0;
                } else {
                    serialNo = serialNo + 1;
                }
                tempSerialNo = serialNo;
            } else {
                // 指定时间内没有获取到锁，存在激烈的锁竞争或者性能问题，直接报错
                LOG.error("can not get lock in:{} seconds!", TIMEOUTSECODES);
                throw new RuntimeException("generateOrder can not get lock!");
            }
        } catch (Exception e) {
            LOG.error("tryLock throws Exception:", e);
            throw new RuntimeException("tryLock throws Exception!");
        } finally {
            lock.unlock();
        }
        if (tempSerialNo < 10) {
            return "0" + tempSerialNo;
        } else {
            return "" + tempSerialNo;
        }
    }

    /**
     * 返回两位随机整数
     * 
     * @return
     */
    private static String getRandomNum() {
        int num = new Random(System.nanoTime()).nextInt(100);
        if (num < 10) {
            return "0" + num;
        } else {
            return num + "";
        }
    }

    /**
     * 获取IP的最后两位数字
     * 
     * @return
     */
    private static String getLastNumOfIP() {
        String ip = getCurrentIP();
        return ip.substring(ip.length() - 2);
    }

    /**
     * 获取本机IP
     * 
     * @return
     */
    private static String getCurrentIP() {
        String ip = "";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            LOG.error("getLocalHost throws UnknownHostException:", e);
            throw new RuntimeException("can not get ip!");
        }
        if (StringUtils.isBlank(ip)) {
            LOG.error("ip is blank!");
            throw new RuntimeException("ip is blank!");
        }
        return ip;
    }
}