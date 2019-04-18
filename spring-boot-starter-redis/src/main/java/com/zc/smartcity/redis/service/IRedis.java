package com.zc.smartcity.redis.service;

import com.zc.smartcity.redis.common.Command;
import com.zc.smartcity.redis.enums.ExistEnum;
import com.zc.smartcity.redis.enums.ExpireTimeEnum;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * Redis 操作接口
 * </p>
 *
 * @author: hejianhui
 * @create: 2019-04-04 03:12
 * @see IRedis
 * @since JDK1.8
 */
@Service
public interface IRedis {

    /**
     * redis 键值（key）
     */
    // 检查key是否存在
    Boolean exists(String business, String key);

    // 检查给定 key 是否存在
    Long expire(String business, String key, int seconds);

    // 检查给定 key 是否存在，UNIX 时间戳(unix timestamp)。
    Long expireAt(String business, String key, long unixTime);

    // 以秒为单位，返回给定 key 的剩余生存时间
    Long ttl(String business, String key);

    // 返回 key 所储存的值的类型
    String type(String business, String key);

    // 该命令用于在 key 存在时删除 key
    long del(String business, String... keys);

    /**
     * 字符串
     */
    // key 所储存的值减去给定的减量值
    Long decrBy(String business, String key, long integer);

    // 将 key 中储存的数字值减一
    Long decr(String business, String key);

    // 将 key 所储存的值加上给定的增量值
    Long incrBy(String business, String key, long integer);

    // 将 key 中储存的数字值增一
    Long incr(String business, String key);

    // 设置指定 key 的值
    String set(String business, String key, String value);

    // 设置指定 key 的值
    String set(String business, byte[] key, byte[] value);

    // 获取指定 key 的值
    String get(String business, String key);

    // 获取指定 key 的值
    byte[] get(String business, byte[] key);

    // 如果 key 已经存在并且是一个字符串， APPEND 命令将指定的 value 追加到该 key 原来值（value）的末尾
    Long append(String business, String key, String value);

    // // 如果 key 已经存在并且是一个字符串， APPEND 命令将指定的 value 追加到该 key 原来值（value）的末尾
    Long append(String business, byte[] key, byte[] value);

    // 返回 key 中字符串值的子字符 GETRANGE key start end
    String substr(String business, String key, int start, int end);

    //  // 返回 key 中字符串值的子字符 GETRANGE key start end
    byte[] substr(String business, byte[] key, int start, int end);

    // 将值 value 关联到 key ，并将 key 的过期时间设为 seconds (以秒为单位)。
    String setex(String business, String key, Integer seconds, String value);

    // 将值 value 关联到 key ，并将 key 的过期时间
    String set(String business, String key, String value, ExistEnum existEnum, ExpireTimeEnum expireTimeEnum, long time);

    // 将值 value 关联到 key ，并将 key 的过期时间
    String set(String business, byte[] key, byte[] value, ExistEnum existEnum, ExpireTimeEnum expireTimeEnum, long time);

    // 将给定 key 的值设为 value ，并返回 key 的旧值(old value)
    String getSet(String business, String key, String value);

    // 将给定 key 的值设为 value ，并返回 key 的旧值(old value)
    byte[] getSet(String business, byte[] key, byte[] value);

    /**
     * 哈希表
     */
    // 将哈希表 key 中的字段 field 的值设为 value
    Long hset(String business, String key, String field, String value);

    Long hset(String business, byte[] key, byte[] field, byte[] value);

    // 只有在字段 field 不存在时，设置哈希表字段的值
    Long hsetnx(String business, String key, String field, String value);

    Long hsetnx(String business, byte[] key, byte[] field, byte[] value);

    // 同时将多个 field-value (域-值)对设置到哈希表 key 中
    String hmset(String business, String key, Map<String, String> hash);

    String hmset(String business, byte[] key, Map<byte[], byte[]> hash);

    // 获取存储在哈希表中指定字段的值
    String hget(String business, String key, String field);

    byte[] hget(String business, byte[] key, byte[] field);

    // 获取所有给定字段的值
    List<String> hmget(String business, String key, String... fields);

    List<byte[]> hmget(String business, byte[] key, byte[]... fields);

    // 获取在哈希表中指定 key 的所有字段和值
    Map<String, String> hgetAll(String business, String key);

    Map<byte[], byte[]> hgetAll(String business, byte[] key);

    // 为哈希表 key 中的指定字段的浮点数值加上增量 increment
    Long hincrBy(String business, String key, String field, long value);

    Long hincrBy(String business, byte[] key, byte[] field, long value);

    // 查看哈希表 key 中，指定的字段是否存在
    Boolean hexists(String business, String key, String field);

    Boolean hexists(String business, byte[] key, byte[] field);

    // 删除一个或多个哈希表字段
    Long hdel(String business, String key, String... fields);

    Long hdel(String business, byte[] key, byte[]... fields);

    // 获取哈希表中字段的数量
    Long hlen(String business, String key);

    // 获取所有哈希表中的字段
    Set<String> hkeys(String business, String key);

    Set<byte[]> hkeys(String business, byte[] key);


    /**
     * 列表
     */
    // 获取列表长度
    Long llen(String business, String key);

    // 移出并获取列表的第一个元素
    String lpop(String business, String key);

    byte[] lpop(String business, byte[] key);

    // 移除列表的最后一个元素，返回值为移除的元素
    String rpop(String business, String key);

    byte[] rpop(String business, byte[] key);

    // 移除列表元素
    Long lrem(String business, String key, Long count, String value);

    Long lrem(String business, byte[] key, long count, byte[] value);

    // 获取列表指定范围内的元素
    List<String> lrange(String business, String key, Long start, Long end);

    List<byte[]> lrange(String business, byte[] key, Long start, Long end);

    // 通过索引获取列表中的元素
    String lindex(String business, String key, Long index);

    byte[] lindex(String business, byte[] key, long index);

    // 将一个或多个值插入到列表头部
    Long lpush(String business, String key, String... values);

    Long lpush(String business, byte[] key, byte[]... values);

    // 在列表中添加一个或多个值
    Long rpush(String business, String key, String... values);

    Long rpush(String business, byte[] key, byte[]... values);

    // 对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除
    String ltrim(String business, String key, Long start, Long end);

    /**
     * 集合
     */

    // 向集合添加一个或多个成员
    Long sadd(String business, String key, String... values);

    Long sadd(String business, byte[] key, byte[]... values);

    // 获取集合的成员数
    Long scard(String business, String key);

    // 返回集合中的所有成员
    Set<String> smembers(String business, String key);

    Set<byte[]> smembers(String business, byte[] key);

    // 移除集合中一个或多个成员
    Long srem(String business, String key, String... values);

    Long srem(String business, byte[] key, byte[]... values);

    // 判断 member 元素是否是集合 key 的成员
    Boolean sismember(String business, String key, String member);

    Boolean sismember(String business, byte[] key, byte[] member);


    /**
     * 有序集合
     */

    /**
     * 地理位置
     */


    /**
     * 批量执行redis服务操作
     *
     * @param business
     * @param commands Command 参见 @link Command
     * @return 注意：Command中，若采用属性set则：
     * business 可选，目前与business相同 参见 @link CommandTypeEnum
     * commandName 必需，RedisClusterImpl的方法枚举名称
     * parameters 必需，方法参数集合，此处若方法参数包括business，则business参数一定不要，即此集合包括调用方法除business的参数。
     * 若采用Command(CommandTypeEnum commandName, Object... coms)构造则：
     * commandName 必需，RedisClusterImpl的方法枚举名称
     * coms 方法参数集合，必须与所调用的方法参数个数及类型相同，此处若方法参数包括business，则business参数一定不要，即此集合包括调用方法除business的参数。
     */
    List<Object> mutliExecute(String business, List<Command> commands);


    //
    //    /**
    //     * 交集
    //     * @param keys
    //     * @return
    //     */
    //    Set<String> sinter(String business, String... keys);

    //    /**
    //     * 并集
    //     * @param keys
    //     * @return
    //     */
    //    Set<String> sunion(String business, String... keys);

    //    /**
    //     * 差集
    //     * @param keys
    //     * @return
    //     */
    //    Set<String> sdiff(String business, String... keys);


}
