package com.zc.smartcity.rocketmq.core;

public interface RocketMQListener<T> {
    void onMessage(T message);
}
