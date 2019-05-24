package com.zc.smartcity.rocketmq.autoconfigure;

import lombok.Data;
import org.apache.rocketmq.common.MixAll;
import org.springframework.boot.context.properties.ConfigurationProperties;

@SuppressWarnings("WeakerAccess")
@Data
@ConfigurationProperties(prefix = "spring.rocketmq")
public class RocketMQProperties {

    /**
     * The name server for rocketMQ, formats: `host:port;host:port`.
     */
    private String nameServer;

    private Producer producer;

    @Data
    public static class Producer {

        /**
         * Name of producer.
         */
        private String group;

        /**
         * Millis of send message timeout.
         */
        private int sendMessageTimeout = 3000;

        /**
         * Compress message body threshold, namely, message body larger than 4k will be compressed on default.
         */
        private int compressMessageBodyThreshold = 1024 * 4;

        /**
         * Maximum number of retry to perform internally before claiming sending failure in synchronous mode.
         * This may potentially cause message duplication which is up to application developers to resolve.
         */
        private int retryTimesWhenSendFailed = 2;

        /**
         * <p> Maximum number of retry to perform internally before claiming sending failure in asynchronous mode. </p>
         * This may potentially cause message duplication which is up to application developers to resolve.
         */
        private int retryTimesWhenSendAsyncFailed = 2;

        /**
         * Indicate whether to retry another broker on sending failure internally.
         */
        private boolean retryNextServer = false;

        /**
         * Maximum allowed message size in bytes.
         */
        private int maxMessageSize = 1024 * 1024 * 4;

        /**
         * The property of "access-key".
         */
        private String accessKey;

        /**
         * The property of "secret-key".
         */
        private String secretKey;

        /**
         * Switch flag instance for message trace.
         */
        private boolean enableMsgTrace = true;

        /**
         * The name value of message trace topic.If you don't config,you can use the default trace topic name.
         */
        private String customizedTraceTopic = MixAll.RMQ_SYS_TRACE_TOPIC;

    }
}
