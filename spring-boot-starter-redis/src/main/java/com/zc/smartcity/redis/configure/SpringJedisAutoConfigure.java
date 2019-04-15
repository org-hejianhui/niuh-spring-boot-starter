package com.zc.smartcity.redis.configure;


import com.zc.smartcity.redis.monitor.MonitorService;
import com.zc.smartcity.redis.monitor.aspect.MonitorAspect;
import com.zc.smartcity.redis.monitor.protocol.falcon.FalconProtocol;
import com.zc.smartcity.redis.spring.SpringJedisClusterService;
import com.zc.smartcity.redis.spring.SpringJedisStandAloneService;
import com.zc.smartcity.redis.service.IRedis;
import com.zc.smartcity.redis.service.impl.RedisClusterImpl;
import com.zc.smartcity.redis.service.impl.RedisImpl;
import com.zc.smartcity.redis.core.RedisClusterConnectionFactory;
import com.zc.smartcity.redis.core.RedisClusterConnectionVHFactory;
import com.zc.smartcity.redis.core.RedisConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnJava;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.system.JavaVersion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @program: redis-spring-boot-starter
 * @description: jedis自动配置
 * @author: hejianhui
 * @create: 2019-04-07 22:51
 **/
@Configuration
@ConditionalOnClass(value = {
        SpringJedisStandAloneService.class,
        SpringJedisClusterService.class,
        RedisConnectionFactory.class,
        RedisClusterConnectionFactory.class,
        RedisClusterConnectionVHFactory.class
})
@EnableConfigurationProperties(SpringJedisProperties.class)
public class SpringJedisAutoConfigure {

    /**
     * jedis资源
     */
    @Autowired
    private SpringJedisProperties properties;

    /**
     * 分隔符
     */
    private final String CONFIG_LOCATION_DELIMITERS = ":,; \t\n";


    /**
     * 注册单机实例Bean
     *
     * @return 返回单机实例
     * @author org_hejianhui@163.com
     * @date 2019/4/7
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "spring.jedis.stand.alone.enabled", havingValue = "true", matchIfMissing = true)
    @ConditionalOnJava(value = JavaVersion.EIGHT, range = ConditionalOnJava.Range.EQUAL_OR_NEWER)
    SpringJedisStandAloneService standAloneService() {
        //放入连接池
        return new SpringJedisStandAloneService(new SpringJedisStandAloneConfig(properties));
    }

    /**
     * 注册集群实例Bean
     *
     * @return 返回集群实例
     * @author org_hejianhui@163.com
     * @date 2019/4/7
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "spring.jedis.cluster.enabled", havingValue = "true")
    @ConditionalOnJava(value = JavaVersion.EIGHT, range = ConditionalOnJava.Range.EQUAL_OR_NEWER)
    SpringJedisClusterService clusterService() {
        //如果使用jedisCluster在外部处理解析
        //设置集群node
        Set<HostAndPort> nodes = new HashSet<>();
        //循环处理
        properties.getNodes().forEach(node -> {
            //冒号分割
            String[] nodeArray = StringUtils.tokenizeToStringArray(node, CONFIG_LOCATION_DELIMITERS);
            //加入节点
            nodes.add(new HostAndPort(nodeArray[0], Integer.valueOf(nodeArray[1])));
        });
        //配置连接池
        JedisPoolConfig config = new JedisPoolConfig();
        // 设置池配置项值
        config.setMaxTotal(properties.getMaxTotal());
        config.setMaxWaitMillis(properties.getMaxWaitMillis());
        config.setMaxIdle(properties.getMinIdle());
        config.setTestOnBorrow(properties.isTestOnBorrow());
        //集群配置
        return new SpringJedisClusterService(nodes, config);
    }

    /**
     * 注册单机Redis连接工厂Bean
     *
     * @return 返回单机工厂实例
     * @author org_hejianhui@163.com
     * @date 2019/4/7
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "spring.jedis.stand.alone.enabled", havingValue = "true", matchIfMissing = true)
    @ConditionalOnJava(value = JavaVersion.EIGHT, range = ConditionalOnJava.Range.EQUAL_OR_NEWER)
    RedisConnectionFactory redisConnectionFactory() {
        //放入连接池
        return new RedisConnectionFactory(new SpringJedisStandAloneConfig(properties));
    }

    /**
     * 注册集群Redis连接工厂Bean
     *
     * @return 返回集群工厂实例
     * @author org_hejianhui@163.com
     * @date 2019/4/7
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "spring.jedis.cluster.enabled", havingValue = "true")
    @ConditionalOnJava(value = JavaVersion.EIGHT, range = ConditionalOnJava.Range.EQUAL_OR_NEWER)
    RedisClusterConnectionFactory redisClusterConnectionFactory() {
        //放入连接池
        return new RedisClusterConnectionFactory(properties);
    }

    /**
     * 注册HA集群Redis连接工厂Bean
     *
     * @return 返回集群可扩容工厂实例
     * @author org_hejianhui@163.com
     * @date 2019/4/7
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "spring.jedis.haCluster.enabled", havingValue = "true")
    @ConditionalOnJava(value = JavaVersion.EIGHT, range = ConditionalOnJava.Range.EQUAL_OR_NEWER)
    RedisClusterConnectionVHFactory redisClusterConnectionVHFactory() {
        return new RedisClusterConnectionVHFactory(properties);
    }

    /**
     * 注册单机Redis服务接口Bean
     *
     * @return 返回单机实例
     * @author org_hejianhui@163.com
     * @date 2019/4/7
     */
    @Bean("iRedis")
    @ConditionalOnMissingBean
    @ConditionalOnClass({ RedisConnectionFactory.class })
    @ConditionalOnProperty(name = "spring.jedis.stand.alone.enabled", havingValue = "true", matchIfMissing = true)
    @ConditionalOnJava(value = JavaVersion.EIGHT, range = ConditionalOnJava.Range.EQUAL_OR_NEWER)
    IRedis redisImpl(){
        return new RedisImpl();
    }

    /**
     * 注册集群Redis服务接口Bean
     *
     * @return 返回集群实例
     * @author org_hejianhui@163.com
     * @date 2019/4/7
     */
    @Bean("iRedisCluster")
    @ConditionalOnMissingBean
    @ConditionalOnClass({ RedisClusterConnectionFactory.class })
    @ConditionalOnProperty(name = "spring.jedis.cluster.enabled", havingValue = "true")
    @ConditionalOnJava(value = JavaVersion.EIGHT, range = ConditionalOnJava.Range.EQUAL_OR_NEWER)
    IRedis redisClusterImpl(){
        return new RedisClusterImpl();
    }

    /**
     * 注册HA集群Redis服务接口Bean
     *
     * @return 返回HA集群实例
     * @author org_hejianhui@163.com
     * @date 2019/4/7
     */
    @Bean("iRedisClusterHA")
    @ConditionalOnMissingBean
    @ConditionalOnClass({ RedisClusterConnectionVHFactory.class })
    @ConditionalOnProperty(name = "spring.jedis.haCluster.enabled", havingValue = "true")
    @ConditionalOnJava(value = JavaVersion.EIGHT, range = ConditionalOnJava.Range.EQUAL_OR_NEWER)
    IRedis redisClusterHAImpl(){
        return new RedisClusterImpl();
    }

    /**
     * 注册监控AOP切面Bean
     *
     * @return 返回监控AOP切面实例
     * @author org_hejianhui@163.com
     * @date 2019/4/7
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(value = { RedisImpl.class,RedisClusterImpl.class })
    @ConditionalOnProperty(name = "spring.jedis.monitor.enabled", havingValue = "true")
    @ConditionalOnJava(value = JavaVersion.EIGHT, range = ConditionalOnJava.Range.EQUAL_OR_NEWER)
    MonitorAspect monitorAspect(){
        return new MonitorAspect();
    }

    /**
     * 注册监控服务Bean
     *
     * @return 返回监控服务实例
     * @author org_hejianhui@163.com
     * @date 2019/4/7
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(value = { RedisImpl.class,RedisClusterImpl.class })
    @ConditionalOnProperty(name = "spring.jedis.monitor.enabled", havingValue = "true")
    @ConditionalOnJava(value = JavaVersion.EIGHT, range = ConditionalOnJava.Range.EQUAL_OR_NEWER)
    MonitorService monitorService(){
        FalconProtocol protocol = new FalconProtocol();
        protocol.setEndpoint("redis-cluster");
        Map<String,String> map = (Map<String, String>) properties.getMonitor();
        return new MonitorService(protocol,map.get("url"));
    }

}
