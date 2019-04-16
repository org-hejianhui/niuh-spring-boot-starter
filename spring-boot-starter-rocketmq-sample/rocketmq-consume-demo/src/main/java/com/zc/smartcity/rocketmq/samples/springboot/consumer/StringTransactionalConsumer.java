package com.zc.smartcity.rocketmq.samples.springboot.consumer;

import com.zc.smartcity.rocketmq.annotation.RocketMQMessageListener;
import com.zc.smartcity.rocketmq.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * StringTransactionalConsumer
 */
@Service
@RocketMQMessageListener(topic = "${demo.rocketmq.transTopic}", consumerGroup = "string_trans_consumer")
public class StringTransactionalConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        System.out.printf("------- StringTransactionalConsumer received: %s \n", message);
    }
}
