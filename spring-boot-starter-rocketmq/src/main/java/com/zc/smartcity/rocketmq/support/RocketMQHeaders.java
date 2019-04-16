package com.zc.smartcity.rocketmq.support;

/**
 * Represents the RocketMQ message protocol that is used during the data exchange.
 */
public class RocketMQHeaders {
    public static final String PREFIX = "rocketmq_";
    public static final String KEYS = "KEYS";
    public static final String TAGS = "TAGS";
    public static final String TOPIC = "TOPIC";
    public static final String MESSAGE_ID = "MESSAGE_ID";
    public static final String BORN_TIMESTAMP = "BORN_TIMESTAMP";
    public static final String BORN_HOST = "BORN_HOST";
    public static final String FLAG = "FLAG";
    public static final String QUEUE_ID = "QUEUE_ID";
    public static final String SYS_FLAG = "SYS_FLAG";
    public static final String TRANSACTION_ID = "TRANSACTION_ID";
}
