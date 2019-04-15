package com.zc.smartcity.redis.spring;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

/**
 * @program: redis-spring-boot-starter
 * @description: 连接Redis服务(集群)
 * @author: hejianhui
 * @create: 2019-04-04 00:33
 **/
@Slf4j
public class SpringJedisClusterService extends JedisCluster {


    public SpringJedisClusterService(Set<HostAndPort> nodes, JedisPoolConfig config) {
        super(nodes, config);
    }
}
