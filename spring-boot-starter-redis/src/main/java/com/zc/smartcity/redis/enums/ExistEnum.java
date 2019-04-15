package com.zc.smartcity.redis.enums;

/**
 * <p>
 *      ExistEnum
 * </p>
 *
 * @author: hejianhui
 * @create: 2019-04-03 17:14
 * @see ExistEnum
 * @since JDK1.8
 */
public enum ExistEnum {
    /**
     * NX -- Only set the key if it does not already exist.
     * XX -- Only set the key if it already exist.
     * NX --有此参数时只能 set 不存在的key,如果给已经存在的key set 值则不生效，
     * XX -- 此参数只能设置已经存在的key 的值，不存在的不生效
     *
     */
    NX,XX
}
