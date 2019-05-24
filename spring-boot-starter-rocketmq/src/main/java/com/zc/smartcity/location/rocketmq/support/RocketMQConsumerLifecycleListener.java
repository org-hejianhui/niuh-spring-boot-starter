package com.zc.smartcity.rocketmq.support;

public interface RocketMQConsumerLifecycleListener<T> {
    void prepareStart(final T consumer);
}
