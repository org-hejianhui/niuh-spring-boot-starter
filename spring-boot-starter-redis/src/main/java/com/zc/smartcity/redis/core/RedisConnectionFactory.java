package com.zc.smartcity.redis.core;

import com.zc.smartcity.redis.configure.SpringJedisStandAloneConfig;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

@Slf4j
public class RedisConnectionFactory implements IRedisConnection {

    @Setter
    @Getter
    private SpringJedisStandAloneConfig standAloneConfig;

    public RedisConnectionFactory(SpringJedisStandAloneConfig standAloneConfig) {
        this.standAloneConfig = standAloneConfig;
    }

    /**
     * 关闭服务时，自动销毁
     *
     * @author org_hejianhui@163.com
     * @date 2019/4/17
     */
    @Override
    public void destroy() {
        standAloneConfig.getJedisPool().destroy();
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("RedisConnectionFactory is running!");
    }

    /**
     * 默认选择db0库
     *
     * @return Jedis
     * @author org_hejianhui@163.com
     * @date 2019/4/17
     */
    public Jedis getJedis() {
        return getJedis(0);
    }

    /**
     * 选择db库
     *
     * @param index db库标记
     * @return Jedis
     * @author org_hejianhui@163.com
     * @date 2019/4/17
     */
    public Jedis getJedis(int index) {
        Jedis jedis = standAloneConfig.getJedisPool().getResource();
        jedis.select(index);
        return jedis;
    }

    /**
     * 返回连接池
     *
     * @param jedis 连接
     * @author org_hejianhui@163.com
     * @date 2019/4/17
     */
    public void returnRedis(Jedis jedis) {
        jedis.close();
    }

    @Override
    public String getBusiness() {
        return standAloneConfig.getBusiness();
    }

}
