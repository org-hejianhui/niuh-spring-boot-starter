package com.zc.smartcity.rocketmq.support;

import com.zc.smartcity.rocketmq.core.RocketMQListener;
import org.springframework.beans.factory.DisposableBean;

public interface RocketMQListenerContainer extends DisposableBean {

    /**
     * Setup the message listener to use. Throws an {@link IllegalArgumentException} if that message listener type is
     * not supported.
     */
    void setupMessageListener(RocketMQListener<?> messageListener);
}
