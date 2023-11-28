package com.iwomi.scheduling.jobs;

import com.iwomi.scheduling.models.TimerModel;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HelloWorldJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        TimerModel info = (TimerModel) jobDataMap.get(HelloWorldJob.class.getSimpleName());
        log.info("Remaining fire count is '{}'", info.getRemainingFireCount());
    }
}
