package com.zc.smartcity.redis.monitor.job;

import java.util.Map;

public class MonitorExecutorJob implements Runnable {
    private Map<String,Object> context;

    private MonitorExecutorJob(){}
    public MonitorExecutorJob(Map<String,Object> context){
        this.context = context;
    }
    @Override
    public void run() {
        MonitorJob.doJob(context);
    }
}
