package com.hz.tgb.id;

import com.hz.tgb.common.RandomUtil;
import com.hz.tgb.common.ValidateUtil;
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
     * 生成订单号，生成规则：时间戳 + 机器IP最后两位 + 2位随机数 + 两位自增序列 <br>
     * 采用可重入锁减小锁持有的粒度，提高系统在高并发情况下的性能
     * 
     * @return
     */
    public static String generateOrder() {
        StringBuilder builder = new StringBuilder();
        builder.append(getDateTime(FORMATSTRING))
               .append(getNumberFromMechine())
               .append(getRandomNum())
               .append(getIncrement());
        return builder.toString();
    }

    /**
     * 生成订单号，生成规则：前缀 + 剩余长度(UUID)
     *
     * @param prefix 前缀
     * @param len 订单号总长度
     * @return
     */
    public static String generateOrder(String prefix, int len) {
        StringBuilder builder = new StringBuilder(prefix);
        builder.append(RandomUtil.getRandomUuidStr(len - prefix.length()));
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
    private static String getNumberFromMechine() {
        String hostName = "";
        try {
            hostName = getHostName();
        } catch (Exception e) {
            LOG.error("getHostName throws Exception:", e);
        }
        if (StringUtils.isBlank(hostName) || hostName.length() < 2) {
            // 机器名为空
            LOG.warn("hostName:{} is illegal!return randomNum!", hostName);
            return getRandomNum();
        } else {
            // 得到了机器名
            String number = hostName.substring(hostName.length() - 2, hostName.length());
            if (ValidateUtil.isPoInteger(number)) {
                return number;
            } else {
                LOG.warn("number:{} is not number!return randomNum!", number);
                return getRandomNum();
            }
        }
    }

    /**
     * 获取本机IP
     *
     * @return
     */
    private static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            LOG.error("getLocalHost throws UnknownHostException:", e);
        }
        return "";
    }
}