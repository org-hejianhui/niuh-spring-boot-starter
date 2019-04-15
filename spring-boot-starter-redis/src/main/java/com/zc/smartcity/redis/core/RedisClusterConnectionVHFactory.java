package com.zc.smartcity.redis.core;

import com.google.common.collect.Sets;

import com.zc.smartcity.redis.configure.SpringJedisProperties;
import com.zc.smartcity.redis.zookeeper.GedisGroups;
import com.zc.smartcity.redis.zookeeper.ZkListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.springframework.util.Assert;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Set;

@Slf4j
public class RedisClusterConnectionVHFactory implements IRedisClusterConnection {

    /**
     * 分隔符
     */
    private final String CONFIG_LOCATION_DELIMITERS = ":";

    private SpringJedisProperties springJedisProperties;

    private JedisCluster jedisCluster;

    private JedisPoolConfig jedisPoolConfig;

    private GedisGroups hostGroups;


    public RedisClusterConnectionVHFactory(SpringJedisProperties properties) {
        this.springJedisProperties = properties;
    }

    @Override
    public void destroy() throws Exception {
        if(null != jedisCluster){
            jedisCluster.close();
        }
        jedisCluster = null;

        log.info("RedisClusterConnectionVHFactory.destroy() is running!");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
       Assert.hasText(springJedisProperties.getZkHostPort());
       Assert.hasText(springJedisProperties.getBusiness());
       
       hostGroups = new GedisGroups(springJedisProperties.getZkHostPort(),springJedisProperties.getBusiness());
       hostGroups.addChangeListner(new DataChangeListener());
       List<String> hostPs = hostGroups.getValues();

       Set<HostAndPort> hostAndPorts = Sets.newHashSet();
       for(String s : hostPs){
           String[] ss = s.split(CONFIG_LOCATION_DELIMITERS);
           hostAndPorts.add(new HostAndPort(ss[0], Integer.valueOf(ss[1])));
       }

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

        log.info("RedisClusterConnectionVHFactory is running!");
    }

    private void refresh(){
    	List<String> hostPs = hostGroups.getValues();
        Set<HostAndPort> hostAndPorts = Sets.newHashSet();
        for(String s : hostPs){
            String[] ss = s.split(":");
            hostAndPorts.add(new HostAndPort(ss[0], Integer.valueOf(ss[1])));
        }

        JedisCluster newjedisCluster = new JedisCluster(hostAndPorts,jedisPoolConfig);
        jedisCluster = newjedisCluster;

        log.info("RedisClusterConnectionVHFactory.refresh() running!");
    }

    public JedisCluster getJedisCluster() {
        return jedisCluster;
    }

    @Override
    public String getBusiness() {
        return null;
    }

    private class DataChangeListener implements ZkListener {

		@Override
		public void dataEvent(WatchedEvent event) {
			// TODO Auto-generated method stub
			if(event.getType() == EventType.NodeChildrenChanged || event.getType() == EventType.NodeDataChanged){
				refresh();
			}
			
		}
    }
    
}
