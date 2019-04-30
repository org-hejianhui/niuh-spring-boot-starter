package com.zc.smartcity.redis.enums;


/**
 * <p>
 *      ExpireTimeEnum
 * </p>
 *
 * @author: hejianhui
 * @see ExpireTimeEnum
 * @since JDK1.8
 */
public enum ExpireTimeEnum {
    /**
     * expire time units: EX = seconds; PX = milliseconds
     * key 的存在时间: EX = seconds; PX = milliseconds
     */
    EX,PX
}
