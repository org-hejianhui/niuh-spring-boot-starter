package com.zc.smartcity.rocketmq.support;

import com.zc.smartcity.rocketmq.core.RocketMQListener;
import org.apache.rocketmq.common.message.MessageExt;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultRocketMQListenerContainerTest {
    @Test
    public void testGetMessageType() throws Exception {
        DefaultRocketMQListenerContainer listenerContainer = new DefaultRocketMQListenerContainer();
        Method getMessageType = DefaultRocketMQListenerContainer.class.getDeclaredMethod("getMessageType");
        getMessageType.setAccessible(true);

        listenerContainer.setRocketMQListener(new RocketMQListener<String>() {
            @Override
            public void onMessage(String message) {
            }
        });
        Class result = (Class)getMessageType.invoke(listenerContainer);
        assertThat(result.getName().equals(String.class.getName()));

        listenerContainer.setRocketMQListener(new RocketMQListener<MessageExt>() {
            @Override
            public void onMessage(MessageExt message) {
            }
        });
        result = (Class)getMessageType.invoke(listenerContainer);
        assertThat(result.getName().equals(MessageExt.class.getName()));
    }
}


