package com.zc.smartcity.redis.monitor.scheduler;

import java.util.Map;

public interface ScheduledService {
    void startJob(Map<String, Object> context, int intervalInSeconds);
    void shutdown();
}
