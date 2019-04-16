package com.zc.smartcity.rocketmq.config;

import com.zc.smartcity.rocketmq.core.RocketMQTemplate;
import io.netty.util.internal.ConcurrentSet;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.DisposableBean;

import java.util.Set;

public class TransactionHandlerRegistry implements DisposableBean {
    private RocketMQTemplate rocketMQTemplate;

    private final Set<String> listenerContainers = new ConcurrentSet<>();

    public TransactionHandlerRegistry(RocketMQTemplate template) {
        this.rocketMQTemplate = template;
    }

    @Override
    public void destroy() throws Exception {
        listenerContainers.clear();
    }

    public void registerTransactionHandler(TransactionHandler handler) throws MQClientException {
        if (listenerContainers.contains(handler.getName())) {
            throw new MQClientException(-1,
                String
                    .format("The transaction name [%s] has been defined in TransactionListener [%s]", handler.getName(),
                        handler.getBeanName()));
        }
        listenerContainers.add(handler.getName());

        rocketMQTemplate.createAndStartTransactionMQProducer(handler.getName(), handler.getListener(), handler.getCheckExecutor(), handler.getRpcHook());
    }
}
