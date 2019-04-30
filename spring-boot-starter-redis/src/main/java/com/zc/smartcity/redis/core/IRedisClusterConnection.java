package com.zc.smartcity.redis.core;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import redis.clients.jedis.JedisCluster;

/**
 * <p>
 *      IRedisClusterConnection
 * </p>
 *
 * @author: hejianhui
 * @see IRedisClusterConnection
 * @since JDK1.8
 */
public interface IRedisClusterConnection extends InitializingBean, DisposableBean {
	
	JedisCluster getJedisCluster();
	
	String getBusiness();

}
