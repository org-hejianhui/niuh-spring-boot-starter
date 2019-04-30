package com.zc.smartcity.redis.spring;

import redis.clients.jedis.Jedis;

/**
 * redis-switch
 * Redis回调模板
 * @author hejianhui
 **/
@FunctionalInterface
public interface RedisCallback<T> {

    /**
     * 回调
     *
     * @param jedis  连接源
     * @param params 参数
     * @return T 泛型
     * @author: hejianhui
     */
      T call(Jedis jedis, Object params);
}
