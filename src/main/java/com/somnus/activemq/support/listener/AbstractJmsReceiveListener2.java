package com.somnus.activemq.support.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;

import com.somnus.activemq.support.common.Constants;
import com.somnus.activemq.support.util.SessionUtil;

public abstract class AbstractJmsReceiveListener2 extends MessageListenerAdapter {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void onReceive(Object message) {
        try {
            MDC.put(Constants.SESSION_ID, SessionUtil.getSessionId());
            execute(message);
        } catch (Throwable e) {
            logger.error("MQ消息处理异常：{}", message.toString(), e);
        } finally {
            MDC.remove(Constants.SESSION_ID);
        }
    }

    protected abstract void execute(Object message) throws Exception;
}
