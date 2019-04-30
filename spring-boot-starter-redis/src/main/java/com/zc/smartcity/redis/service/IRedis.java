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
 * @see IRedis
 * @since JDK1.8
 */
@Service
public interface IRedis {

    //redis 键值（key）

    /**
     * 检查key是否存在
     * @param business business
     * @param key key
     * @return result
     */
    Boolean exists(String business, String key);

    /**
     * 检查给定 key 是否存在
     * @param business business
     * @param key key
     * @param seconds seconds
     * @return result
     */
    Long expire(String business, String key, int seconds);

    /**
     * 检查给定 key 是否存在，UNIX 时间戳(unix timestamp)
     * @param business business
     * @param key key
     * @param unixTime unixTime
     * @return result
     */
    Long expireAt(String business, String key, long unixTime);

    /**
     * 以秒为单位，返回给定 key 的剩余生存时间
     * @param business business
     * @param key key
     * @return result
     */
    Long ttl(String business, String key);

    /**
     * 返回 key 所储存的值的类型
     * @param business business
     * @param key key
     * @return result
     */
    String type(String business, String key);

    /**
     * 该命令用于在 key 存在时删除 key
     * @param business business
     * @param keys keys
     * @return result
     */
    long del(String business, String... keys);

    //字符串

    /**
     * key 所储存的值减去给定的减量值
     * @param business business
     * @param key key
     * @param integer  integer
     * @return result
     */
    Long decrBy(String business, String key, long integer);

    /**
     * 将 key 中储存的数字值减一
     * @param business business
     * @param key key
     * @return result
     */
    Long decr(String business, String key);

    /**
     * 将 key 所储存的值加上给定的增量值
     * @param business business
     * @param key key
     * @param integer  integer
     * @return result
     */
    Long incrBy(String business, String key, long integer);

    /**
     * 将 key 中储存的数字值增一
     * @param business business
     * @param key key
     * @return result
     */
    Long incr(String business, String key);

    /**
     * 设置指定 key 的值
     * @param business business
     * @param key key
     * @param value value
     * @return result
     */
    String set(String business, String key, String value);

    /**
     * 设置指定 key 的值
     * @param business business
     * @param key key
     * @param value value
     * @return result
     */
    String set(String business, byte[] key, byte[] value);

    /**
     * 获取指定 key 的值
     * @param business business
     * @param key key
     * @return result
     */
    String get(String business, String key);

    /**
     * 获取指定 key 的值
     * @param business business
     * @param key key
     * @return result
     */
    byte[] get(String business, byte[] key);

    /**
     * 如果 key 已经存在并且是一个字符串， APPEND 命令将指定的 value 追加到该 key 原来值（value）的末尾
     * @param business business
     * @param key key
     * @param value value
     * @return result
     */
    Long append(String business, String key, String value);

    /**
     * 如果 key 已经存在并且是一个字符串， APPEND 命令将指定的 value 追加到该 key 原来值（value）的末尾
     * @param business business
     * @param key key
     * @param value value
     * @return result
     */
    Long append(String business, byte[] key, byte[] value);

    /**
     * 返回 key 中字符串值的子字符 GETRANGE key start end
     * @param business business
     * @param key key
     * @param start start
     * @param end end
     * @return result
     */
    String substr(String business, String key, int start, int end);

    /**
     * 返回 key 中字符串值的子字符 GETRANGE key start end
     * @param business business
     * @param key key
     * @param start start
     * @param end end
     * @return result
     */
    byte[] substr(String business, byte[] key, int start, int end);

    /**
     * 将值 value 关联到 key ，并将 key 的过期时间设为 seconds (以秒为单位)
     * @param business business
     * @param key key
     * @param seconds seconds
     * @param value value
     * @return result
     */
    String setex(String business, String key, Integer seconds, String value);

    /**
     * 将值 value 关联到 key ，并将 key 的过期时间
     * @param business business
     * @param key key
     * @param value value
     * @param existEnum  existEnum
     * @param expireTimeEnum expireTimeEnum
     * @param time time
     * @return result
     */
    String set(String business, String key, String value, ExistEnum existEnum, ExpireTimeEnum expireTimeEnum, long time);

    /**
     * 将值 value 关联到 key ，并将 key 的过期时间
     * @param business business
     * @param key key
     * @param value value
     * @param existEnum  existEnum
     * @param expireTimeEnum expireTimeEnum
     * @param time time
     * @return result
     */
    String set(String business, byte[] key, byte[] value, ExistEnum existEnum, ExpireTimeEnum expireTimeEnum, long time);

    /**
     * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)
     * @param business business
     * @param key key
     * @param value value
     * @return result
     */
    String getSet(String business, String key, String value);

    /**
     * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)
     * @param business business
     * @param key key
     * @param value value
     * @return result
     */
    byte[] getSet(String business, byte[] key, byte[] value);

    //哈希表

    /**
     * 将哈希表 key 中的字段 field 的值设为 value
     * @param business business
     * @param key key
     * @param field field
     * @param value value
     * @return result
     */
    Long hset(String business, String key, String field, String value);

    /**
     * 将哈希表 key 中的字段 field 的值设为 value
     * @param business business
     * @param key key
     * @param field field
     * @param value value
     * @return result
     */
    Long hset(String business, byte[] key, byte[] field, byte[] value);

    /**
     * 只有在字段 field 不存在时，设置哈希表字段的值
     * @param business business
     * @param key key
     * @param field field
     * @param value value
     * @return result
     */
    Long hsetnx(String business, String key, String field, String value);

    /**
     * 只有在字段 field 不存在时，设置哈希表字段的值
     * @param business business
     * @param key key
     * @param field field
     * @param value value
     * @return result
     */
    Long hsetnx(String business, byte[] key, byte[] field, byte[] value);

    /**
     * 同时将多个 field-value (域-值)对设置到哈希表 key 中
     * @param business business
     * @param key key
     * @param hash hash
     * @return result
     */
    String hmset(String business, String key, Map<String, String> hash);

    /**
     * 同时将多个 field-value (域-值)对设置到哈希表 key 中
     * @param business business
     * @param key key
     * @param hash hash
     * @return result
     */
    String hmset(String business, byte[] key, Map<byte[], byte[]> hash);

    /**
     * 获取存储在哈希表中指定字段的值
     * @param business business
     * @param key key
     * @param field field
     * @return result
     */
    String hget(String business, String key, String field);

    /**
     * 获取存储在哈希表中指定字段的值
     * @param business business
     * @param key key
     * @param field field
     * @return result
     */
    byte[] hget(String business, byte[] key, byte[] field);

    /**
     * 获取所有给定字段的值
     * @param business business
     * @param key key
     * @param fields fields
     * @return result
     */
    List<String> hmget(String business, String key, String... fields);

    /**
     * 获取所有给定字段的值
     * @param business business
     * @param key key
     * @param fields fields
     * @return result
     */
    List<byte[]> hmget(String business, byte[] key, byte[]... fields);

    /**
     * 获取在哈希表中指定 key 的所有字段和值
     * @param business business
     * @param key key
     * @return result
     */
    Map<String, String> hgetAll(String business, String key);

    /**
     * 获取在哈希表中指定 key 的所有字段和值
     * @param business business
     * @param key key
     * @return result
     */
    Map<byte[], byte[]> hgetAll(String business, byte[] key);

    /**
     * 为哈希表 key 中的指定字段的浮点数值加上增量 increment
     * @param business business
     * @param key key
     * @param field field
     * @param value value
     * @return result
     */
    Long hincrBy(String business, String key, String field, long value);

    /**
     * 为哈希表 key 中的指定字段的浮点数值加上增量 increment
     * @param business business
     * @param key key
     * @param field field
     * @param value value
     * @return result
     */
    Long hincrBy(String business, byte[] key, byte[] field, long value);

    /**
     * 查看哈希表 key 中，指定的字段是否存在
     * @param business business
     * @param key key
     * @param field field
     * @return result
     */
    Boolean hexists(String business, String key, String field);

    /**
     * 查看哈希表 key 中，指定的字段是否存在
     * @param business business
     * @param key key
     * @param field field
     * @return result
     */
    Boolean hexists(String business, byte[] key, byte[] field);

    /**
     * 删除一个或多个哈希表字段
     * @param business business
     * @param key key
     * @param fields fields
     * @return result
     */
    Long hdel(String business, String key, String... fields);

    /**
     * 删除一个或多个哈希表字段
     * @param business business
     * @param key key
     * @param fields fields
     * @return result
     */
    Long hdel(String business, byte[] key, byte[]... fields);

    /**
     * 获取哈希表中字段的数量
     * @param business business
     * @param key key
     * @return result
     */
    Long hlen(String business, String key);

    /**
     * 获取所有哈希表中的字段
     * @param business business
     * @param key key
     * @return result
     */
    Set<String> hkeys(String business, String key);

    /**
     * 获取所有哈希表中的字段
     * @param business business
     * @param key key
     * @return result
     */
    Set<byte[]> hkeys(String business, byte[] key);


    // 列表

    /**
     * 获取列表长度
     * @param business business
     * @param key key
     * @return result
     */
    Long llen(String business, String key);

    /**
     * 移出并获取列表的第一个元素
     * @param business business
     * @param key key
     * @return result
     */
    String lpop(String business, String key);

    /**
     * 移出并获取列表的第一个元素
     * @param business business
     * @param key key
     * @return result
     */
    byte[] lpop(String business, byte[] key);

    /**
     * 移除列表的最后一个元素，返回值为移除的元素
     * @param business business
     * @param key key
     * @return result
     */
    String rpop(String business, String key);

    /**
     * 移除列表的最后一个元素，返回值为移除的元素
     * @param business business
     * @param key key
     * @return result
     */
    byte[] rpop(String business, byte[] key);

    /**
     * 移除列表元素
     * @param business business
     * @param key key
     * @param count count
     * @param value value
     * @return result
     */
    Long lrem(String business, String key, Long count, String value);

    /**
     * 移除列表元素
     * @param business business
     * @param key key
     * @param count count
     * @param value value
     * @return result
     */
    Long lrem(String business, byte[] key, long count, byte[] value);

    /**
     * 获取列表指定范围内的元素
     * @param business business
     * @param key key
     * @param start start
     * @param end end
     * @return result
     */
    List<String> lrange(String business, String key, Long start, Long end);

    /**
     * 获取列表指定范围内的元素
     * @param business business
     * @param key key
     * @param start start
     * @param end end
     * @return result
     */
    List<byte[]> lrange(String business, byte[] key, Long start, Long end);

    /**
     * 通过索引获取列表中的元素
     * @param business business
     * @param key key
     * @param index index
     * @return result
     */
    String lindex(String business, String key, Long index);

    /**
     * 通过索引获取列表中的元素
     * @param business business
     * @param key key
     * @param index index
     * @return result
     */
    byte[] lindex(String business, byte[] key, long index);

    /**
     * 将一个或多个值插入到列表头部
     * @param business business
     * @param key key
     * @param values values
     * @return result
     */
    Long lpush(String business, String key, String... values);

    /**
     * 将一个或多个值插入到列表头部
     * @param business business
     * @param key key
     * @param values values
     * @return result
     */
    Long lpush(String business, byte[] key, byte[]... values);

    /**
     * 在列表中添加一个或多个值
     * @param business business
     * @param key key
     * @param values values
     * @return result
     */
    Long rpush(String business, String key, String... values);

    /**
     * 在列表中添加一个或多个值
     * @param business business
     * @param key key
     * @param values values
     * @return result
     */
    Long rpush(String business, byte[] key, byte[]... values);

    /**
     * 对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除
     * @param business business
     * @param key key
     * @param start start
     * @param end end
     * @return result
     */
    String ltrim(String business, String key, Long start, Long end);

    // 集合

    /**
     * 向集合添加一个或多个成员
     * @param business business
     * @param key key
     * @param values values
     * @return result
     */
    Long sadd(String business, String key, String... values);

    /**
     * 向集合添加一个或多个成员
     * @param business business
     * @param key key
     * @param values values
     * @return result
     */
    Long sadd(String business, byte[] key, byte[]... values);

    /**
     * 获取集合的成员数
     * @param business business
     * @param key key
     * @return result
     */
    Long scard(String business, String key);

    /**
     * 返回集合中的所有成员
     * @param business business
     * @param key key
     * @return result
     */
    Set<String> smembers(String business, String key);

    /**
     * 返回集合中的所有成员
     * @param business business
     * @param key key
     * @return result
     */
    Set<byte[]> smembers(String business, byte[] key);

    /**
     * 移除集合中一个或多个成员
     * @param business business
     * @param key key
     * @param values values
     * @return result
     */
    Long srem(String business, String key, String... values);

    /**
     * 移除集合中一个或多个成员
     * @param business business
     * @param key key
     * @param values values
     * @return result
     */
    Long srem(String business, byte[] key, byte[]... values);

    /**
     * 判断 member 元素是否是集合 key 的成员
     * @param business business
     * @param key key
     * @param member member
     * @return result
     */
    Boolean sismember(String business, String key, String member);

    /**
     * 判断 member 元素是否是集合 key 的成员
     * @param business business
     * @param key key
     * @param member member
     * @return result
     */
    Boolean sismember(String business, byte[] key, byte[] member);


    // 有序集合

    // 地理位置


    /**
     * 批量执行redis服务操作
     *
     * @param business business
     * @param commands Command 参见 @link Command
     * @return result 注意：Command中，若采用属性set则：
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
    //     * @param key keys
    //     * @return result
    //     */
    //    Set<String> sinter(String business, String... keys);

    //    /**
    //     * 并集
    //     * @param key keys
    //     * @return result
    //     */
    //    Set<String> sunion(String business, String... keys);

    //    /**
    //     * 差集
    //     * @param key keys
    //     * @return result
    //     */
    //    Set<String> sdiff(String business, String... keys);


}
