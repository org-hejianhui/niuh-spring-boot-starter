package com.zc.smartcity.redis.monitor.protocol.falcon;

import com.google.gson.Gson;
import com.zc.smartcity.redis.monitor.protocol.AbstractProtocol;

import java.io.Serializable;

public class FalconProtocol extends AbstractProtocol {
    private String counterType = "GAUGE";
    private String tag;

    public String getCounterType() {
        return counterType;
    }

    public void setCounterType(String counterType) {
        this.counterType = counterType;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public Serializable toExport() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
