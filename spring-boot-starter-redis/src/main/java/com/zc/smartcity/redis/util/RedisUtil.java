package com.zc.smartcity.redis.util;

import com.zc.smartcity.redis.common.Constants;

public class RedisUtil {

    public static String buildKey(String business,String key){
        return new StringBuilder(business).append(Constants.DEFAULT_SEPARATOR).append(key).toString();
    }

    public static String buildKeys(String business,String ... keys){
        StringBuilder sb = new StringBuilder(business).append(Constants.DEFAULT_SEPARATOR);
        for(String key : keys){
            sb.append(key);
        }
        return sb.toString();
    }

    public static byte[] buildKey(String business,byte[] key){
        byte[] busi = (business + Constants.DEFAULT_SEPARATOR).getBytes();
        byte[] bytes = new byte[busi.length + key.length];
        System.arraycopy(busi, 0, bytes, 0, busi.length);
        System.arraycopy(key, 0, bytes, busi.length, key.length);
        return bytes;
    }


}
