package com.zc.smartcity.redis.spring;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

/**
 * redis-spring-boot-starter
 * 连接Redis服务(集群)
 * @author hejianhui
 **/
@Slf4j
public class SpringJedisClusterService extends JedisCluster {


    public SpringJedisClusterService(Set<HostAndPort> nodes, JedisPoolConfig config) {
        super(nodes, config);
    }


    public SpringJedisClusterService(Set<HostAndPort> nodes, int connectionTimeout, int soTimeout, int maxAttempts, String auth, JedisPoolConfig config) {
        super(nodes,connectionTimeout,soTimeout,maxAttempts,auth,config);
    }
}
