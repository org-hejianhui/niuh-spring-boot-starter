package com.zc.smartcity.redis.monitor.scheduler;

import com.zc.smartcity.redis.monitor.job.MonitorQuartzJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import java.util.Map;
import java.util.Set;

public class ScheduledQuartz implements ScheduledService {
    private Scheduler sch = null;
    private String jobIdentity = "Quartz-Flasher-Monitor";

    @Override
    public void startJob(Map<String, Object> context,int intervalInSeconds) {
        JobDetail job = JobBuilder.newJob(MonitorQuartzJob.class)
                .withIdentity(jobIdentity)
                .usingJobData(this.mapTo(context))
                .build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(intervalInSeconds)
                        .repeatForever()
                ).build();
        //schedule the job
        SchedulerFactory schFactory = new StdSchedulerFactory();
        try {
            sch = schFactory.getScheduler();
            sch.start();
            sch.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void shutdown() {
        if(null != sch){
            try {
                sch.shutdown();
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
        sch = null;
    }

    private JobDataMap mapTo(Map<String,Object> map){
        JobDataMap jobDataMap = new JobDataMap();
        Set<String> keys = map.keySet();
        for(String k : keys){
            jobDataMap.put(k,map.get(k));
        }
        return jobDataMap;
    }
}
