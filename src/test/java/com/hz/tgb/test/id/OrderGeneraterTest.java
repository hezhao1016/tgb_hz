package com.hz.tgb.test.id;

import java.util.ArrayList;
import java.util.List;

import com.hz.tgb.id.OrderGenerater;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 订单号生成器单元测试
 * 
 * @author Yaphis 2015年5月1日 下午3:17:51
 * 
 * 订单号生成工具类 - Yaphis的个人页面
 * https://my.oschina.net/yaphis/blog/408933
 */
public class OrderGeneraterTest {

    private static final Logger logger = LoggerFactory.getLogger(OrderGeneraterTest.class);

    /**
     * 验证订单号生成是否会重复和耗时(单线程)
     */
    @Test
    public void testGenerateOrder() {
        List<String> orderList = new ArrayList<String>();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
//            String orderId = OrderGenerater.generateOrder();
            String orderId = OrderGenerater.generateOrder("id-", 18);
            logger.debug(orderId);
            
            if (orderList.contains(orderId)) {
            	logger.info("orderId:{}", orderId);
            	logger.info("orderList:{},list:{}", new Object[] { orderList.size(), orderList });
                Assert.fail("订单号重复！");
            }
            orderList.add(orderId);
        }
        logger.info("generateOrder cost:{}", System.currentTimeMillis() - startTime);
    }

    /**
     * 验证订单号生成是否会重复和耗时(多线程)
     */
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        long startTime = System.currentTimeMillis();
        // 模拟1000个并发请求
        for (int j = 0; j < 10000; j++) {
            new Thread(new ThreadTest(list)).start();
        }
        logger.info("testGenerateOrderMultithread cost:{}", System.currentTimeMillis() - startTime);
    }

    /**
     * 测试线程
     * 
     * @author Yaphis 2015年5月1日 下午3:59:19
     */
    public static class ThreadTest implements Runnable {

        private List<String> list;

        public ThreadTest(List<String> list) {
            this.list = list;
        }

        public void run() {
            for (int i = 0; i < 1; i++) {
                String orderId = OrderGenerater.generateOrder();
                logger.debug(orderId);
                
                if (list.contains(orderId)) {
                	logger.error("订单号重复！");
                    break;
                }
                list.add(orderId);
            }
        }
    }
}