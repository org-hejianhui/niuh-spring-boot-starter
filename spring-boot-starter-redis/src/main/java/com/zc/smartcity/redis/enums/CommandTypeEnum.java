package com.zc.smartcity.redis.enums;

/**
 * <p>
 *      Redis 常用指令
 * </p>
 *
 * @author: hejianhui
 * @create: 2019-04-03 17:14
 * @see CommandTypeEnum
 * @since JDK1.8
 */
public enum CommandTypeEnum {
    hget, hgetAll, expire, expireAt, ttl, //
    decrBy, decr, incrBy, incr, hset, //
    set, get, exists, type, append, //
    substr, hsetnx, hmset, hmget, hincrBy, //
    hexists, hdel, hlen, hkeys, keys, //
    dbSize, del, setex, //
    // list
    rpush, lpush, llen, lrange, //
    lindex, lpop, rpop, ltrim, //
    lrem,
    // set
    sadd, srem, scard, smembers,smember;//

}
