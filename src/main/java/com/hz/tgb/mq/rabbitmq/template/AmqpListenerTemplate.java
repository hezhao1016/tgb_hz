package com.hz.tgb.mq.rabbitmq.template;

import com.hz.tgb.thread.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * AMQ消息监听模板
 * 
 * @author Yaphis 2017年4月12日 下午8:04:52
 */
public abstract class AmqpListenerTemplate<T> implements MessageListener {

    protected static final Logger LOG = LoggerFactory.getLogger(AmqpListenerTemplate.class);

    @Override
    public void onMessage(Message message) {
        long startTime = System.currentTimeMillis();
        Map<String, Object> messageHeader = new HashMap<String, Object>();
        String messageBody = "";
        try {
            ThreadUtil.initThreadName();
            if (null == message) {
                LOG.warn("message:{} is null!", message);
                return;
            }
            messageHeader = getMessageHeader(message);
            messageBody = getMessageBody(message);
            T t = parseMessage2Bean(messageHeader, messageBody);
            if (!verify(t)) {
                LOG.info("messageBean:{},verify fail!", t);// 校验失败
                return;
            }
            execute(t);
        } catch (Exception e) {
            LOG.error("receiveMessage throws Exception:", e);
            handleException(messageBody, e);
        } finally {
            LOG.info("{},messageHeader:{},messageBody:{} cost:{}", new Object[] { this.getClass().getSimpleName(), messageHeader, messageBody, System.currentTimeMillis() - startTime });
            ThreadUtil.clearMDC();
        }
    }

    /**
     * 解析消息头
     * 
     * @param message
     * @return
     */
    protected Map<String, Object> getMessageHeader(Message message) {
        if (null != message.getMessageProperties()) {
            return message.getMessageProperties().getHeaders();
        }
        LOG.warn("messageProperties:{} is null!", message.getMessageProperties());
        return null;
    }

    /**
     * 解析消息体
     * 
     * @param message
     * @return
     * @throws UnsupportedEncodingException
     */
    protected String getMessageBody(Message message) throws UnsupportedEncodingException {
        byte[] bodyByte = message.getBody();
        if (null != bodyByte && bodyByte.length > 0) {
            return new String(bodyByte, "UTF-8");
        } else {
            LOG.error("bodyByte:{} is null or empty!", new Object[] { Arrays.toString(bodyByte) });
            return "";
        }
    }

    /**
     * 校验消息 如果返回false 会抛弃该消息
     * 
     * @param messageBean
     */
    public abstract boolean verify(T messageBean);

    /**
     * 将来源消息转换为实体
     * 
     * @param messageHeader
     * @param messageBody
     * @return
     */
    public abstract T parseMessage2Bean(Map<String, Object> messageHeader, String messageBody);

    /**
     * 处理消息实体
     * 
     * @param messageBean
     */
    public abstract void execute(T messageBean);

    /**
     * 异常处理
     * 
     * @param messageBody
     * @param e
     */
    protected void handleException(String messageBody, Exception e) {
        LOG.warn("handleException,messageBody:{}", messageBody);
        LOG.error("", e);
    }

    /**
     * 从消息头中取值
     * 
     * @param messageHeader 消息头
     * @param headerKey 头索引
     * @param defaultValue 默认值,当消息头为空或者消息头中包含头索引时返回该值
     * @return
     */
    protected Object getMessageHeaderValue(Map<String, Object> messageHeader, String headerKey, Object defaultValue) {
        if (CollectionUtils.isEmpty(messageHeader)) {
            LOG.info("messageHeader:{} is empty! return defaultValue:{}", new Object[] { messageHeader, defaultValue });
            return defaultValue;
        }
        if (StringUtils.isEmpty(headerKey)) {
            LOG.info("headerKey:{} is blank!return defaultValue:{}", new Object[] { headerKey, defaultValue });
            return defaultValue;
        }
        if (messageHeader.containsKey(headerKey) && null != messageHeader.get(headerKey)) {
            return messageHeader.get(headerKey);
        } else {
            LOG.info("messageHeader:{} does not contains headerKey:{} return defaultValue:{}", new Object[] { messageHeader, headerKey, defaultValue });
            return defaultValue;
        }
    }
}