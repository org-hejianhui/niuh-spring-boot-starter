package com.zc.smartcity.redis.monitor.protocol;

import com.google.common.collect.Lists;
import com.zc.smartcity.redis.common.Constants;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public abstract class AbstractProtocol implements Protocol,Cloneable {
    /**
     * 时间戳(s)
     */
    private long timestamp;
    /**
     * 抽取时间间隔
     */
    private int step;
    /**
     * 实例名称
     */
    private String endpoint;
    /**
     * 指标名称
     */
    private String metric;
    /**
     * 指标值
     */
    private int value;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public AbstractProtocol clone(){
        try {
            return (AbstractProtocol) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Serializable>  buildLocalData(AbstractProtocol protocol) {
        List<Serializable> result = Lists.newArrayList();
        Set<String> keys = Constants.MONITOR_MAP.keySet();
        if (null != keys && !keys.isEmpty()) {
            for (String key : keys) {
                AbstractProtocol tmp = protocol.clone();
                tmp.setMetric(key);
                tmp.setValue(Constants.MONITOR_MAP.get(key));
                tmp.setTimestamp(System.currentTimeMillis() / 1000);
                result.add(tmp.toExport());
            }
        }
        return result;
    }

    public static void clearLocalData(){
        Constants.MONITOR_MAP.clear();
    }
}
