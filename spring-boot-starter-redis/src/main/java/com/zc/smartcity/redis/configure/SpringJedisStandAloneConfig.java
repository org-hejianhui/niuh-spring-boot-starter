package com.zc.smartcity.redis.configure;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @program: redis-spring-boot-starter
 * @description: jedis连接池(单机)
 * @author: hejianhui
 * @create: 2019-04-07 23:30
 **/
@Data
@Slf4j
public class SpringJedisStandAloneConfig extends JedisPoolConfig {

    /**
     * 默认数据库
     */
    final int DEFAULT_DATABASE = 0;

    String CONFIG_LOCATION_DELIMITERS = ",; \t\n";

    /**
     * 连接池
     **/
    private JedisPool jedisPool;

    private String business;

    /**
     * 构造处理
     *
     * @param properties 配置信息
     * @author org_hejianhui@163.com
     * @date 2019/4/7
     */
    public SpringJedisStandAloneConfig(SpringJedisProperties properties) {
        super();
        //初始化
        init(properties);
    }

    /**
     * 初始化
     *
     * @param properties 配置信息
     * @author org_hejianhui@163.com
     * @date 2019/4/7
     */
    private void init(SpringJedisProperties properties) {
        business = properties.getBusiness();
        //防止单机和集群容错
        //截取多个主机地址
        String[] hostArray = StringUtils.tokenizeToStringArray(properties.getHost(), CONFIG_LOCATION_DELIMITERS);
        try {
            log.info("------------- redis pool init start------------- ");
            JedisPoolConfig config = new JedisPoolConfig();
            // 设置池配置项值
            config.setMaxTotal(properties.getMaxTotal());
            config.setMaxWaitMillis(properties.getMaxWaitMillis());
            config.setMaxIdle(properties.getMinIdle());
            config.setTestOnBorrow(properties.isTestOnBorrow());
            //获取连接池 判断是否存在鉴权
            if (StringUtils.hasLength(properties.getAuth())) {
                jedisPool = new JedisPool(config, hostArray[0], properties.getPort(), properties.getTimeout(), properties.getAuth());
            } else {
                jedisPool = new JedisPool(config, hostArray[0], properties.getPort(), properties.getTimeout());
            }
            boolean connected = isConnected();
            if (!connected) {
                log.error("redis 初始化出错 缓存服务器连接不上！");
                throw new Exception("IP:" + hostArray[0] + ", redis服务器不可以连接~~~，请检查配置redis服务器");
            }
            log.info("------------- redis pool init end------------- ");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new Error("IP:" + hostArray[0] + ",redis服务器不可以连接~~~，请检查配置redis服务器", e);
        }
    }


    /**
     * 验证是否连接正常
     *
     * @return boolean
     * @author org_hejianhui@163.com
     * @date 2019/4/7
     */
    private boolean isConnected() {
        Jedis jedis = jedisPool.getResource();
        jedis.select(DEFAULT_DATABASE);
        return jedis.isConnected();
    }
}
