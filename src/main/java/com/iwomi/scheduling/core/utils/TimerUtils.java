package com.iwomi.scheduling.core.utils;

import com.iwomi.scheduling.models.TimerModel;
import org.quartz.*;

import java.util.Date;

// Helper class to build JobDetails and Triggers necessary for job
// scheduling
public class TimerUtils {
    public TimerUtils() {
    }

    public static JobDetail buildJobDetail(final Class jobClass, final TimerModel model) {
        final JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(jobClass.getSimpleName(), model);

        return JobBuilder
                .newJob(jobClass)
                .withIdentity(jobClass.getSimpleName())
                .setJobData(jobDataMap)
                .build();
    }

    public static Trigger buildTrigger(final Class jobClass, final TimerModel model) {
        SimpleScheduleBuilder builder = SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(model.getRepeatIntervalMs());

        /// Check if the timer or job is to run forever
        /// else check firedCount and deduct by 1
        if (model.isRunForever()) {
            builder = builder.repeatForever();
        } else {
            builder = builder.withRepeatCount(model.getTotalFireCount() - 1);
        }

        return TriggerBuilder
                .newTrigger()
                .withIdentity(jobClass.getSimpleName())
                .withSchedule(builder)
                .startAt(new Date(System.currentTimeMillis() + model.getInitialOffsetMs()))
                .build();
    }
}
