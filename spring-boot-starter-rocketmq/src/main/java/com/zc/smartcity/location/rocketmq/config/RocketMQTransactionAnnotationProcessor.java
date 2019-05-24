package com.zc.smartcity.rocketmq.config;

import com.zc.smartcity.rocketmq.annotation.RocketMQTransactionListener;
import com.zc.smartcity.rocketmq.core.RocketMQLocalTransactionListener;
import com.zc.smartcity.rocketmq.support.RocketMQUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.RPCHook;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class RocketMQTransactionAnnotationProcessor
    implements BeanPostProcessor, Ordered, ApplicationContextAware {

    private ApplicationContext applicationContext;
    private final Set<Class<?>> nonProcessedClasses =
        Collections.newSetFromMap(new ConcurrentHashMap<Class<?>, Boolean>(64));

    private TransactionHandlerRegistry transactionHandlerRegistry;

    public RocketMQTransactionAnnotationProcessor(TransactionHandlerRegistry transactionHandlerRegistry) {
        this.transactionHandlerRegistry = transactionHandlerRegistry;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!this.nonProcessedClasses.contains(bean.getClass())) {
            Class<?> targetClass = AopUtils.getTargetClass(bean);
            RocketMQTransactionListener listener = AnnotationUtils.findAnnotation(targetClass, RocketMQTransactionListener.class);
            this.nonProcessedClasses.add(bean.getClass());
            if (listener == null) { // for quick search
                log.trace("No @RocketMQTransactionListener annotations found on bean type: {}", bean.getClass());
            } else {
                try {
                    processTransactionListenerAnnotation(listener, bean);
                } catch (MQClientException e) {
                    log.error("Failed to process annotation " + listener, e);
                    throw new BeanCreationException("Failed to process annotation " + listener, e);
                }
            }
        }

        return bean;
    }

    private void processTransactionListenerAnnotation(RocketMQTransactionListener listener, Object bean)
        throws MQClientException {
        if (transactionHandlerRegistry == null) {
            throw new MQClientException("Bad usage of @RocketMQTransactionListener, " +
                "the class must work with RocketMQTemplate", null);
        }
        if (!RocketMQLocalTransactionListener.class.isAssignableFrom(bean.getClass())) {
            throw new MQClientException("Bad usage of @RocketMQTransactionListener, " +
                "the class must implement interface RocketMQLocalTransactionListener",
                null);
        }
        TransactionHandler transactionHandler = new TransactionHandler();
        transactionHandler.setBeanFactory(this.applicationContext.getAutowireCapableBeanFactory());
        transactionHandler.setName(listener.txProducerGroup());
        transactionHandler.setBeanName(bean.getClass().getName());
        transactionHandler.setListener((RocketMQLocalTransactionListener) bean);
        transactionHandler.setCheckExecutor(listener.corePoolSize(), listener.maximumPoolSize(),
                listener.keepAliveTime(), listener.blockingQueueSize());

        RPCHook rpcHook = RocketMQUtil.getRPCHookByAkSk(applicationContext.getEnvironment(),
            listener.accessKey(), listener.secretKey());

        if (Objects.nonNull(rpcHook)) {
            transactionHandler.setRpcHook(rpcHook);
        } else {
            log.debug("Access-key or secret-key not configure in " + listener + ".");
        }

        transactionHandlerRegistry.registerTransactionHandler(transactionHandler);
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }

}
