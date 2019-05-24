package com.zc.smartcity.rocketmq.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnMissingBean(ObjectMapper.class)
class JacksonFallbackConfiguration {

    @Bean
    public ObjectMapper rocketMQMessageObjectMapper() {
        return new ObjectMapper();
    }

}
