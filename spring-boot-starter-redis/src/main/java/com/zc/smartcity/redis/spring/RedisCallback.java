package com.zc.smartcity.redis.spring;

import redis.clients.jedis.Jedis;

/**
 * @program: redis-switch
 * @description: Redis回调模板
 * @author: hejianhui
 * @create: 2019-04-04 18:20
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
     * @date 18:25 2018/4/4
     * @version V1.0.0
     */
      T call(Jedis jedis, Object params);
}
