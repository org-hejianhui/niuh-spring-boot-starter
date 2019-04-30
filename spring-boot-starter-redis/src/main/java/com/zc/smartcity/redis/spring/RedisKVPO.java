package com.zc.smartcity.redis.spring;

import lombok.Data;

/**
 * redis-switch
 * Key-Value实体类
 * @author hejianhui
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