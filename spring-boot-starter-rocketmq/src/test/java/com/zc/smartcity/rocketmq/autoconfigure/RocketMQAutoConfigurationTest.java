package com.zc.smartcity.rocketmq.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zc.smartcity.rocketmq.annotation.RocketMQMessageListener;
import com.zc.smartcity.rocketmq.core.RocketMQListener;
import com.zc.smartcity.rocketmq.support.DefaultRocketMQListenerContainer;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.junit.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

public class RocketMQAutoConfigurationTest {
    private ApplicationContextRunner runner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(RocketMQAutoConfiguration.class));


    @Test(expected = NoSuchBeanDefinitionException.class)
    public void testRocketMQAutoConfigurationNotCreatedByDefault() {
        runner.run(context -> context.getBean(RocketMQAutoConfiguration.class));
    }


    @Test(expected = NoSuchBeanDefinitionException.class)
    public void testDefaultMQProducerNotCreatedByDefault() {
        runner.run(context -> context.getBean(DefaultMQProducer.class));
    }


    @Test
    public void testDefaultMQProducerWithRelaxPropertyName() {
        runner.withPropertyValues("spring.rocketmq.nameServer=127.0.0.1:9876",
                "spring.rocketmq.producer.group=spring_rocketmq").
                run((context) -> {
                    assertThat(context).hasSingleBean(DefaultMQProducer.class);
                    assertThat(context).hasSingleBean(RocketMQProperties.class);
                });

    }

    @Test
    public void testDefaultMQProducer() {
        runner.withPropertyValues("spring.rocketmq.name-server=127.0.0.1:9876",
            "spring.rocketmq.producer.group=spring_rocketmq").
            run((context) -> {
                assertThat(context).hasSingleBean(DefaultMQProducer.class);
            });

    }

    @Test
    public void testRocketMQListenerContainer() {
        runner.withPropertyValues("spring.rocketmq.name-server=127.0.0.1:9876").
            withUserConfiguration(TestConfig.class).
            run((context) -> {
                // No producer on consume side
                assertThat(context).doesNotHaveBean(DefaultMQProducer.class);
                // Auto-create consume container if existing Bean annotated with @RocketMQMessageListener
                assertThat(context).hasSingleBean(DefaultRocketMQListenerContainer.class);
            });

    }

    @Test
    public void testRocketMQListenerWithCustomObjectMapper() {
        runner.withPropertyValues("spring.rocketmq.name-server=127.0.0.1:9876").
                withUserConfiguration(TestConfig.class, CustomObjectMapperConfig.class).
                run((context) -> {
                    assertThat(context).hasSingleBean(DefaultRocketMQListenerContainer.class);
                    assertThat(context.getBean(DefaultRocketMQListenerContainer.class).getObjectMapper())
                            .isSameAs(context.getBean(CustomObjectMapperConfig.class).testObjectMapper());
                });
    }

    @Test
    public void testRocketMQListenerWithSeveralObjectMappers() {
        runner.withPropertyValues("spring.rocketmq.name-server=127.0.0.1:9876").
                withUserConfiguration(TestConfig.class, CustomObjectMappersConfig.class).
                run((context) -> {
                    assertThat(context).hasSingleBean(DefaultRocketMQListenerContainer.class);
                    assertThat(context.getBean(DefaultRocketMQListenerContainer.class).getObjectMapper())
                            .isSameAs(context.getBean(CustomObjectMappersConfig.class).rocketMQMessageObjectMapper());
                });
    }

    @Configuration
    static class TestConfig {

        @Bean
        public Object consumeListener() {
            return new MyMessageListener();
        }
    }

    @Configuration
    static class CustomObjectMapperConfig {

        @Bean
        public ObjectMapper testObjectMapper() {
            return new ObjectMapper();
        }

    }

    @Configuration
    static class CustomObjectMappersConfig {

        @Bean
        public ObjectMapper testObjectMapper() {
            return new ObjectMapper();
        }

        @Bean
        public ObjectMapper rocketMQMessageObjectMapper() {
            return new ObjectMapper();
        }

    }

    @RocketMQMessageListener(consumerGroup = "abc", topic = "test")
    static class MyMessageListener implements RocketMQListener {

        @Override
        public void onMessage(Object message) {

        }
    }
}

