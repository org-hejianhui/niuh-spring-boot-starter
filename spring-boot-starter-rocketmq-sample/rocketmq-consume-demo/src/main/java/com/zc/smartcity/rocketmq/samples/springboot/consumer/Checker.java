package com.zc.smartcity.rocketmq.samples.springboot.consumer;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * Note: This is a nagitive testing. It aims to tell user the fact that
 * the @RocketMQTransactionListener can not be used on consumer side!!!
 *
 * <p>How to try it? just uncomment the annotation declaration, then compile
 * and run the consumer, it will fail to start.
 */

//@RocketMQTransactionListener
public class Checker implements TransactionListener {
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        return null;
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        return null;
    }
}
