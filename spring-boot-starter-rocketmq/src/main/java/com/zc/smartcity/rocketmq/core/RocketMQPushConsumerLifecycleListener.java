package com.zc.smartcity.rocketmq.core;

import com.zc.smartcity.rocketmq.support.RocketMQConsumerLifecycleListener;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;

public interface RocketMQPushConsumerLifecycleListener extends RocketMQConsumerLifecycleListener<DefaultMQPushConsumer> {
}
