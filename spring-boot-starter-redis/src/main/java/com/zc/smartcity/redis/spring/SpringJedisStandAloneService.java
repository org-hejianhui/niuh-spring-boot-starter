package com.zc.smartcity.redis.spring;

import com.google.common.base.Strings;
import com.zc.smartcity.redis.configure.SpringJedisStandAloneConfig;
import com.zc.smartcity.redis.spring.RedisCallback;
import com.zc.smartcity.redis.spring.RedisKVPO;
import com.zc.smartcity.redis.util.RedisUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @program: redis-spring-boot-starter
 * @description: 连接Redis服务(单机)
 * @author: hejianhui
 * @create: 2019-04-04 22:53
 **/
@Slf4j
public class SpringJedisStandAloneService implements DisposableBean{

    /**
     * 连接池
     **/
    @Setter
    @Getter
    private JedisPool jedisPool;

    @Setter
    @Getter
    private SpringJedisStandAloneConfig standAloneConfig;

    public SpringJedisStandAloneService(SpringJedisStandAloneConfig standAloneConfig) {
        this.jedisPool = standAloneConfig.getJedisPool();
        this.standAloneConfig = standAloneConfig;
    }

    /**
     * 模版执行,自动关闭
     *
     * @param callback 回调接口处理
     * @param args     数组参数
     * @return T 泛型
     * @author org_hejianhui@163.com
     * @date 2019/4/17
     */
    private <T> T execute(RedisCallback<T> callback, Object... args) {
        Jedis jedis = null;
        Object index = args[0];
        try {
            // 0-15号库
            if (null != index && Integer.parseInt(index.toString()) > 0 && Integer.parseInt(index.toString()) < 16) {
                jedis = getRedis(Integer.parseInt(index.toString()));
            } else {
                jedis = getRedis();
            }
            return callback.call(jedis, args);
        } catch (JedisConnectionException e) {
            log.error(e.getMessage(), e);
            if (jedis != null) {
                jedis.close();
            }
            //重新获取链接，重试一次。（网络抖动问题）
            jedis = getRedis(Integer.parseInt(index.toString()));
            return callback.call(jedis, args);
        } finally {
            if (jedis != null) {
                returnRedis(jedis);
            }
        }
    }

    /**
     * index 实例index
     *
     * @param index 数据库位置
     * @author org_hejianhui@163.com
     * @date 10:38 2019/4/8
     */
    public void select(int index) {
        execute((jedis, parms) -> {
            int index1 = Integer.parseInt(((Object[]) parms)[0].toString());
            return jedis.select(index1);
        }, index);
    }

    private String getBusiness(String business){
        if(Strings.isNullOrEmpty(standAloneConfig.getBusiness())){
            return business;
        } else {
            return standAloneConfig.getBusiness();
        }
    }

    private String getbKey(Object params){
        String business1 = ((Object[]) params)[1].toString();
        String key1 = ((Object[]) params)[2].toString();
        return RedisUtil.buildKey(getBusiness(business1), key1);
    }

    /**
     * 查看 key 中，指定的字段是否存在。
     *
     * @param index 数据库位置
     * @param key   key标识
     * @return Boolean 返回true或false
     * @author org_hejianhui@163.com
     * @date 10:26 2019/4/8
     */
    public Boolean exists(int index,String business, String key) {
        return execute((jedis, params) -> {
            return jedis.exists(getbKey(params));
        }, index, business, key);
    }

    /**
     * 查看哈希表 key 中，指定的字段是否存在。
     *
     * @param index        数据库位置
     * @param mapKey       key标识
     * @param attributeKey 成员字段
     * @return Boolean 返回true或false
     * @author org_hejianhui@163.com
     * @date 10:26 2019/4/8
     */
    public Boolean hexists(int index, String business, String mapKey, String attributeKey) {
        return execute((jedis, params) -> {
            String field = ((Object[]) params)[3].toString();
            return jedis.hexists(getbKey(params), field);
        }, index, business, mapKey, attributeKey);
    }

    /**
     * 通过Hash（哈希），获取存储在哈希表中指定字段的值。
     *
     * @param index 数据库位置
     * @param field 成员字段
     * @return String
     * @author org_hejianhui@163.com
     * @date 10:26 2019/4/8
     */
    public String hget(int index,String business, String key, String field) {
        return execute((jedis, params) -> {
            String field1 = ((Object[]) params)[3].toString();
            return jedis.hget(getbKey(params), field1);
        }, index, business, key, field);
    }

    /**
     * 通过Hash（哈希），获取在哈希表中指定 key 的所有字段和值
     *
     * @param index 数据库位置
     * @param key   key标识
     * @return Map 获取在哈希表中指定 key 的所有字段和值
     * @author org_hejianhui@163.com
     * @date 10:26 2019/4/8
     */
    public Map<String, String> hgetAll(int index, String business, String key) {
        return execute((jedis, params) -> {
            return jedis.hgetAll(getbKey(params));
        }, index, business, key);
    }

    /**
     * 通过Hash（哈希），删除成员字段
     *
     * @param index        数据库位置
     * @param mapKey       key标识
     * @param attributeKey 成员字段
     * @return long 受影响行数
     * @author org_hejianhui@163.com
     * @date 10:26 2019/4/8
     */
    public Long hdel(int index, String business, String mapKey, String attributeKey) {
        return execute((jedis, params) -> {
            String field = ((Object[]) params)[3].toString();
            return jedis.hdel(getbKey(params), field);
        }, index, business, mapKey, attributeKey);
    }

    /**
     * 通过Hash（哈希），获取值
     *
     * @param index 数据库位置
     * @param key   key标识
     * @param field 成员字段
     * @param value 值
     * @return 返回影响行
     * @author org_hejianhui@163.com
     * @date 10:26 2019/4/8
     */
    public Long hset(int index, String business, String key, String field, String value) {
        return execute((jedis, params) -> {
            String field1 = ((Object[]) params)[3].toString();
            String value1 = ((Object[]) params)[4].toString();
            return jedis.hset(getbKey(params), field1, value1);
        }, index, business, key, field, value);
    }

    /**
     * 通过key（字符串），获取值
     *
     * @param index 数据库位置
     * @param key   key标识
     * @return 返回值
     * @author org_hejianhui@163.com
     * @date 10:26 2019/4/8
     */
    public String get(int index, String business, String key) {
        return execute((jedis, params) -> {
            return jedis.get(getbKey(params));
        }, index, business, key);
    }

    /**
     * 设置key的过期时间
     *
     * @param index   数据库位置
     * @param key     key标识
     * @param seconds 过期时间
     * @return 返回1设置 0未设置
     * @author org_hejianhui@163.com
     * @date 10:26 2019/4/8
     */
    public Long expire(int index, String business, String key, int seconds) {
        return execute((jedis, params) -> {
            String seconds1 = ((Object[]) params)[3].toString();
            return jedis.expire(getbKey(params), Integer.valueOf(seconds1));
        }, index, business, key, seconds);
    }

    /**
     * 通过二进制key，获取二进制值
     *
     * @param index 数据库位置
     * @param key   key标识
     * @return byte 二进制
     * @author org_hejianhui@163.com
     * @date 10:26 2019/4/8
     */
    public byte[] getByte(int index, String business, String key) {
        return execute((jedis, parms) -> {
            return jedis.get(getbKey(parms).getBytes(StandardCharsets.UTF_8));
        }, index, business, key);
    }

    /**
     * 设置key（字符串）赋值
     *
     * @param index 数据库位置
     * @param key   key标识
     * @param value 值
     * @return 返回状态码 OK
     * @author org_hejianhui@163.com
     * @date 10:11 2019/4/8
     */
    public String set(int index, String business, String key, String value) {
        return execute((jedis, parms) -> {
            String value1 = ((Object[]) parms)[3].toString();
            return jedis.set(getbKey(parms), value1);
        }, index, business, key, value);
    }

    /**
     * 设置key(二进制)
     *
     * @param index 数据库位置
     * @param key   key标识
     * @param value 值
     * @return 返回状态码 OK
     * @author org_hejianhui@163.com
     * @date 10:11 2019/4/8
     */
    public String set(int index, String business, String key, byte[] value) {
        return execute((jedis, parms) -> {
            byte[] value1 = (byte[]) ((Object[]) parms)[3];
            return jedis.set(getbKey(parms).getBytes(StandardCharsets.UTF_8), value1);
        }, index, business, key, value);
    }

    /**
     * 设置key(字符串)过期时间（单位：秒）
     *
     * @param index   数据库位置
     * @param key     key标识
     * @param value   值
     * @param seconds 过期时间
     * @return 返回状态码 OK
     * @author org_hejianhui@163.com
     * @date 10:11 2019/4/8
     */
    public String setex(int index, String business, String key, String value, int seconds) {
        return execute((jedis, parms) -> {
            String value1 = ((Object[]) parms)[3].toString();
            String seconds1 = ((Object[]) parms)[4].toString();
            return jedis.setex(getbKey(parms), Integer.parseInt(seconds1), value1);
        }, index, business, key, value, seconds);
    }

    /**
     * 设置key(二进制)过期时间（单位：秒）
     *
     * @param index   数据库位置
     * @param key     key标识
     * @param value   值
     * @param seconds 过期时间
     * @author org_hejianhui@163.com
     * @date 10:11 2019/4/8
     */
    public String setex(int index, String business, String key, byte[] value, int seconds) {
        return execute((jedis, parms) -> {
            byte[] value1 = (byte[]) ((Object[]) parms)[3];
            String seconds1 = ((Object[]) parms)[4].toString();
            return jedis.setex(getbKey(parms).getBytes(StandardCharsets.UTF_8), Integer.parseInt(seconds1), value1);
        }, index, business, key, value, seconds);
    }

    /**
     * 批量set新增
     *
     * @param index 数据库位置
     * @author org_hejianhui@163.com
     * @date 10:11 2019/4/8
     */
    public void setPipeLine(int index, String business, List<RedisKVPO> list) {
        execute((jedis, params) -> {
            Pipeline p = jedis.pipelined();
            String business1 = ((Object[]) params)[1].toString();
            @SuppressWarnings("unchecked")
            List<RedisKVPO> values = (List<RedisKVPO>) ((Object[]) params)[2];
            values.forEach(v ->
                    p.set(RedisUtil.buildKey(getBusiness(business1), v.getK()), v.getV())
            );
            p.sync();
            return null;
        }, index, business, list);
    }

    /**
     * 根据key删除
     *
     * @param index 数据库位置
     * @param key   key标识
     * @return 返回删除影响行数
     * @author org_hejianhui@163.com
     * @date 10:11 2019/4/8
     */
    public Long del(int index, String business, String key) {
        return execute((jedis, parms) -> {
            return jedis.del(getbKey(parms));
        }, index, business, key);
    }

    /**
     * 获取列表长度
     *
     * @param index 数据库位置
     * @param key   key标识
     * @return 返回集合长度
     * @author org_hejianhui@163.com
     * @date 10:11 2019/4/8
     */
    public Long llen(int index, String business, String key) {
        return execute((jedis, parms) -> {
            return jedis.llen(getbKey(parms));
        }, index, business, key);
    }

    /**
     * 将一个或多个值插入到列表头部
     *
     * @param index 数据库位置
     * @param key   key标识
     * @param value 值
     * @return 返回整个元素的数量
     * @author org_hejianhui@163.com
     * @date 10:11 2019/4/8
     */
    public Long lpush(int index, String business, String key, String value) {
        return execute((jedis, parms) -> {
            String value1 = ((Object[]) parms)[3].toString();
            return jedis.lpush(getbKey(parms), value1);
        }, index, business, key, value);
    }

    /**
     * List列表key，一次命令新增
     *
     * @param index 数据库位置
     * @param key   key标识
     * @author org_hejianhui@163.com
     * @date 10:11 2019/4/8
     */
    public void lpushPipeLine(int index, String business, String key, List<String> values) {
        execute((RedisCallback<String>) (jedis, parms) -> {
            @SuppressWarnings("unchecked")
            List<String> values1 = (List<String>) ((Object[]) parms)[3];
            Pipeline p = jedis.pipelined();
            values1.forEach(value -> p.lpush(getbKey(parms), value));
            p.sync();
            return null;
        }, index, business, key, values);
    }

    /**
     * 获取列表指定范围内的元素
     *
     * @param index 数据库位置
     * @param key   key标识
     * @param start 开始
     * @param end   截至
     * @return List集合
     * @author org_hejianhui@163.com
     * @date 10:11 2019/4/8
     */
    public List<String> lrange(int index, String business, String key, long start, long end) {
        return execute((jedis, parms) -> {
            Object[] ps = ((Object[]) parms);
            long start1 = Long.parseLong(ps[3].toString());
            long end1 = Long.parseLong(ps[4].toString());
            return jedis.lrange(getbKey(parms), start1, end1);
        }, index, business, key, start, end);
    }

    /**
     * 原子性自增
     *
     * @param index 数据库位置
     * @param key   key标识
     * @return 返回自增值
     * @author org_hejianhui@163.com
     * @date 10:11 2019/4/8
     */
    public Long incr(int index, String business, String key) {
        return execute((jedis, parms) -> {
            return jedis.incr(getbKey(parms));
        }, index, business, key);
    }

    /**
     * 向集合添加一个(多个成员不支持)
     *
     * @param index 数据库位置
     * @param key   key标识
     * @param value 值
     * @return 返回插入影响行数
     * @author org_hejianhui@163.com
     * @date 10:11 2019/4/8
     */
    public Long sadd(int index, String business, String key, String value) {
        return execute((jedis, parms) -> {
            String value1 = ((Object[]) parms)[3].toString();
            return jedis.sadd(getbKey(parms), value1);
        }, index, business, key, value);
    }

    /**
     * 返回集合中的所有成员
     *
     * @param index 数据库位置
     * @param key   key标识
     * @return Set集合
     * @author org_hejianhui@163.com
     * @date 10:11 2019/4/8
     */
    public Set<String> smembers(int index, String business, String key) {
        return execute((jedis, parms) -> {
            return jedis.smembers(getbKey(parms));
        }, index, business, key);
    }

    /**
     * 移出并获取列表的最后一个元素
     *
     * @param index 数据库位置
     * @param key   key标识
     * @return List列表
     * @author org_hejianhui@163.com
     * @date 10:11 2019/4/8
     */
    public List<String> brpop(int index, String business, String key) {
        return execute((jedis, parms) -> {
            return jedis.brpop(0, getbKey(parms));
        }, index, business, key);
    }

    /**
     * 对hash里面的value进行自增
     *
     * @param index 数据库位置
     * @param key   key标识
     * @param field 成员字段
     * @param value 自增步长
     * @author org_hejianhui@163.com
     * @date 10:26 2019/4/8
     */
    public Long hincrby(int index, String business, String key, String field, long value) {
        return execute((jedis, parms) -> {
            String field1 = ((Object[]) parms)[3].toString();
            long value1 = Long.valueOf(((Object[]) parms)[4].toString());
            return jedis.hincrBy(getbKey(parms), field1, value1);
        }, index, business, key, field, value);
    }

    /**
     * 将 key 中储存的数字加上指定的增量值。
     *
     * @param index 数据库位置
     * @param key   key标识
     * @param value 自增步长
     * @return String 返回旧值
     * @author org_hejianhui@163.com
     * @date 10:26 2019/4/8
     */
    public Long incrBy(int index, String business, String key, long value) {
        return execute((jedis, parms) -> {
            long value1 = Long.valueOf(((Object[]) parms)[3].toString());
            return jedis.incrBy(getbKey(parms), value1);
        }, index, business, key, value);
    }

    /**
     * 将 key 中储存的数字减去指定的增量值。
     *
     * @param index 数据库位置
     * @param key   key标识
     * @param value 自减步长
     * @return String 返回旧值
     * @author org_hejianhui@163.com
     * @date 10:26 2019/4/8
     */
    public Long decrBy(int index, String business, String key, long value) {
        return execute((jedis, parms) -> {
            long value1 = Long.valueOf(((Object[]) parms)[3].toString());
            return jedis.decrBy(getbKey(parms), value1);
        }, index, business, key, value);
    }


    /**
     * 关闭服务时，自动销毁
     *
     * @author org_hejianhui@163.com
     * @date 2019/4/17
     */
    @Override
    public void destroy() {
        jedisPool.destroy();
    }

    /**
     * 默认选择db0库
     *
     * @return Jedis
     * @author org_hejianhui@163.com
     * @date 2019/4/17
     */
    private Jedis getRedis() {
        return getRedis(0);
    }

    /**
     * 选择db库
     *
     * @param index db库标记
     * @return Jedis
     * @author org_hejianhui@163.com
     * @date 2019/4/17
     */
    private Jedis getRedis(int index) {
        Jedis jedis = jedisPool.getResource();
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
    private void returnRedis(Jedis jedis) {
        jedis.close();
    }
}
