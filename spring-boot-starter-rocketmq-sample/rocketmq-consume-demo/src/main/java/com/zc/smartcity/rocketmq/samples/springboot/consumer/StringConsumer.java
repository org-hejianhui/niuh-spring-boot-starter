package com.zc.smartcity.rocketmq.samples.springboot.consumer;

import com.zc.smartcity.rocketmq.annotation.RocketMQMessageListener;
import com.zc.smartcity.rocketmq.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * RocketMQMessageListener
 */
@Service
@RocketMQMessageListener(topic = "${demo.rocketmq.topic}", consumerGroup = "string_consumer")
public class StringConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        System.out.printf("------- StringConsumer received: %s \n", message);
    }
}
