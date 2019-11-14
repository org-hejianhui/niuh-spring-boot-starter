package com.zc.smartcity.redis.configure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;

/**
 * redis-spring-boot-starter
 * jedis配置
 * @author hejianhui
 **/
@ConfigurationProperties(prefix = SpringJedisProperties.REDIS_PREFIX)
@PropertySource("classpath:application.yml")
@Data
public class SpringJedisProperties {

    /**
     * jedis prefix
     */
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
    private String auth = "sagis@123";
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

    /**
     * 超时
     */
    private int connectionTimeout = 60000;

    /**
     * 访问数据时间
     */
    private int soTimeout = 30000;

    /**
     * 访问数据时间
     */
    private int maxAttempts = 3;
}