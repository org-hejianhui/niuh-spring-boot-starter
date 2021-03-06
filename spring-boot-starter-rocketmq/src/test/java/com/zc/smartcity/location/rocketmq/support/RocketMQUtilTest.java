package com.zc.smartcity.rocketmq.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Arrays;

import static org.junit.Assert.*;

public class RocketMQUtilTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testMessageBuilder() {
        Message msg = MessageBuilder.withPayload("test").
            setHeader("A", "test1").
            setHeader("B", "test2").
            build();
        System.out.printf("header size=%d  %s %n", msg.getHeaders().size(), msg.getHeaders().toString());
        assertTrue(msg.getHeaders().get("A") != null);
        assertTrue(msg.getHeaders().get("B") != null);
    }

    @Test
    public void testPayload() {
        String charset = "UTF-8";
        String destination = "test-topic";
        Message msgWithStringPayload = MessageBuilder.withPayload("test").build();
        org.apache.rocketmq.common.message.Message rocketMsg1 = RocketMQUtil.convertToRocketMessage(objectMapper,
            charset, destination, msgWithStringPayload);

        Message msgWithBytePayload = MessageBuilder.withPayload("test".getBytes()).build();
        org.apache.rocketmq.common.message.Message rocketMsg2 = RocketMQUtil.convertToRocketMessage(objectMapper,
            charset, destination, msgWithBytePayload);

        assertTrue(Arrays.equals(((String)msgWithStringPayload.getPayload()).getBytes(), rocketMsg1.getBody()));
        assertTrue(Arrays.equals((byte[])msgWithBytePayload.getPayload(), rocketMsg2.getBody()));
    }

    @Test
    public void testHeaderConvertToRMQMsg() {
        Message msgWithStringPayload = MessageBuilder.withPayload("test body")
            .setHeader("test", 1)
            .setHeader(RocketMQHeaders.TAGS, "tags")
            .setHeader(RocketMQHeaders.KEYS, "my_keys")
            .build();
        org.apache.rocketmq.common.message.Message rocketMsg = RocketMQUtil.convertToRocketMessage(objectMapper,
            "UTF-8", "test-topic", msgWithStringPayload);
        assertEquals(String.valueOf("1"), rocketMsg.getProperty("test"));
        assertNull(rocketMsg.getProperty(RocketMQHeaders.TAGS));
        assertEquals("my_keys", rocketMsg.getProperty(RocketMQHeaders.KEYS));
    }

    @Test
    public void testHeaderConvertToSpringMsg() {
        org.apache.rocketmq.common.message.Message rmqMsg = new org.apache.rocketmq.common.message.Message();
        rmqMsg.setBody("test body".getBytes());
        rmqMsg.setTopic("test-topic");
        rmqMsg.putUserProperty("test", "1");
        rmqMsg.setTags("tags");
        Message springMsg = RocketMQUtil.convertToSpringMessage(rmqMsg);
        assertEquals(String.valueOf("1"), springMsg.getHeaders().get("test"));
        assertEquals("tags", springMsg.getHeaders().get(RocketMQHeaders.PREFIX + RocketMQHeaders.TAGS));

        org.apache.rocketmq.common.message.Message rocketMsg = RocketMQUtil.convertToRocketMessage(objectMapper,
            "UTF-8", "test-topic", springMsg);
        assertEquals(String.valueOf("1"), rocketMsg.getProperty("test"));
        assertEquals(String.valueOf("tags"), rocketMsg.getProperty(RocketMQHeaders.PREFIX + RocketMQHeaders.TAGS));
        assertNull(rocketMsg.getTags());
    }

}