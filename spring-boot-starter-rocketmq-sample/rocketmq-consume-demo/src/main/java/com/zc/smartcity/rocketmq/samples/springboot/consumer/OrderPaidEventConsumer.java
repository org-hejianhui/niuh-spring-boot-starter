package com.zc.smartcity.rocketmq.samples.springboot.consumer;

import com.zc.smartcity.rocketmq.annotation.RocketMQMessageListener;
import com.zc.smartcity.rocketmq.core.RocketMQListener;
import com.zc.smartcity.rocketmq.samples.springboot.domain.OrderPaidEvent;
import org.springframework.stereotype.Service;

/**
 * OrderPaidEventConsumer
 */
@Service
@RocketMQMessageListener(topic = "${demo.rocketmq.orderTopic}", consumerGroup = "order-paid-consumer")
public class OrderPaidEventConsumer implements RocketMQListener<OrderPaidEvent> {

    @Override
    public void onMessage(OrderPaidEvent orderPaidEvent) {
        System.out.printf("------- OrderPaidEventConsumer received: %s \n", orderPaidEvent);
    }
}
