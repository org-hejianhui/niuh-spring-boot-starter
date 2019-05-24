package com.zc.smartcity.rocketmq.core;

import org.springframework.messaging.Message;

public interface RocketMQLocalTransactionListener {
    RocketMQLocalTransactionState executeLocalTransaction(final Message msg, final Object arg);

    RocketMQLocalTransactionState checkLocalTransaction(final Message msg);
}
