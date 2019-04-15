package com.zc.smartcity.redis.core;

import com.google.common.collect.Sets;
import com.zc.smartcity.redis.configure.SpringJedisProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

@Slf4j
@Data
public class RedisClusterConnectionFactory implements IRedisClusterConnection {

    /**
     * 分隔符
     */
    private final String CONFIG_LOCATION_DELIMITERS = ":,; \t\n";

    @Autowired
    private SpringJedisProperties springJedisProperties;

    private JedisCluster jedisCluster;

    private JedisPoolConfig jedisPoolConfig;

    public RedisClusterConnectionFactory(SpringJedisProperties properties) {
       this.springJedisProperties = springJedisProperties;
    }

    @Override
    public void destroy() throws Exception {
        if(null != jedisCluster){
            jedisCluster.close();
        }
        jedisCluster = null;

        log.info("RedisClusterConnectionFactory.destroy() is running!");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Set<HostAndPort> hostAndPorts = Sets.newHashSet();
        //循环处理
        springJedisProperties.getNodes().forEach(node -> {
            //冒号分割
            String[] nodeArray = StringUtils.tokenizeToStringArray(node, CONFIG_LOCATION_DELIMITERS);
            //加入节点
            hostAndPorts.add(new HostAndPort(nodeArray[0], Integer.valueOf(nodeArray[1])));
        });
        // throw new RuntimeException("hostPorts OR hostPort is null or empty");

        if(null == jedisCluster){
            if(null == jedisPoolConfig){
                jedisPoolConfig = new JedisPoolConfig();
                //配置连接池
                // 设置池配置项值
                jedisPoolConfig.setMaxTotal(springJedisProperties.getMaxTotal());
                jedisPoolConfig.setMaxWaitMillis(springJedisProperties.getMaxWaitMillis());
                jedisPoolConfig.setMaxIdle(springJedisProperties.getMinIdle());
                jedisPoolConfig.setTestOnBorrow(springJedisProperties.isTestOnBorrow());
            }
            jedisCluster = new JedisCluster(hostAndPorts,jedisPoolConfig);
        }

        log.info("RedisClusterConnectionFactory is running!");
    }

    @Override
    public String getBusiness() {
        return springJedisProperties.getBusiness();
    }

    @Override
    public JedisCluster getJedisCluster(){
        return this.jedisCluster;
    }
}
