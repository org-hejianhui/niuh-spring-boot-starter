package com.zc.smartcity.redis.monitor.job;


import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MonitorQuartzJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // if(null == context.getPreviousFireTime()){
        //     return;
        // }
        JobDataMap jobDataMap = context.getMergedJobDataMap();
        MonitorJob.doJob(jobDataMap);
    }
}
