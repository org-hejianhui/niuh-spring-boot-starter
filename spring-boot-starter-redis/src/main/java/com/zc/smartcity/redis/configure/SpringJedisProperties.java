package com.zc.smartcity.redis.configure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;

/**
 * @program: redis-spring-boot-starter
 * @description: jedis配置
 * @author: hejianhui
 * @create: 2019-04-04 21:58
 **/
@ConfigurationProperties(prefix = SpringJedisProperties.REDIS_PREFIX)
@PropertySource("classpath:application.yml")
@Data
public class SpringJedisProperties {

    static final String REDIS_PREFIX = "spring.jedis";

    /**
     * Jedis connection pool
     **/
    private static JedisPool pool;
    /**
     * Maximum number of connections
     **/
    private int maxTotal = 10;
    /**
     * Minimum number of connections
     **/
    private int minIdle = 1;
    /**
     * Maximum waiting time
     **/
    private long maxWaitMillis = 30000;
    /**
     * Test to get the correct connection
     **/
    private boolean testOnBorrow = false;
    /**
     * Redis stand-alone address
     **/
    private String host = "localhost";

    /**
     * Redis cluster address
     */
    private List<String> nodes;
    /**
     * Redis port
     **/
    private int port = 6379;
    /**
     * overtime time
     **/
    private int timeout = 30000;
    /**
     * Authentication password
     **/
    private String auth;
    /**
     * business type
     */
    private String business;
    /**
     * zk
     */
    private String zkHostPort;
    /**
     * monitor setting
     */
    private Map<?,?> monitor;
}