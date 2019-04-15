package com.zc.smartcity.redis.spring;

import lombok.Data;

/**
 * @program: redis-switch
 * @description: Key-Value实体类
 * @author: hejianhui
 * @create: 2019-04-04 21:58
 **/
@Data
public class RedisKVPO {
    /**
     * key
     **/
    private String k;

    /**
     * 值
     **/
    private String v;

    public RedisKVPO() {
    }

    public RedisKVPO(String k, String v) {
        this.k = k;
        this.v = v;
    }
}