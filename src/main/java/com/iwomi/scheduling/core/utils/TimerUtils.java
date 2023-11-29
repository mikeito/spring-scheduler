package com.iwomi.scheduling.core.utils;

import com.iwomi.scheduling.models.ScheduleInfoModel;
import lombok.NoArgsConstructor;
import org.quartz.*;

import java.util.Date;
import java.util.HashMap;

public class TimerUtils {
    public TimerUtils() {
    }

    public static JobDetail buildJobDetail(final Class jobClass, final ScheduleInfoModel model) {
        final JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(jobClass.getSimpleName(), model);

        return JobBuilder
                .newJob(jobClass)
                .withIdentity(jobClass.getSimpleName())
                .setJobData(jobDataMap)
                .build();
    }

    public static Trigger buildTrigger(final Class jobClass, final String cronString) {
        CronScheduleBuilder cronSchedule = CronScheduleBuilder.cronSchedule(cronString);
//        Date end = TriggerUtils.computeFireTimes(cronSchedule,)

        return TriggerBuilder
                .newTrigger()
                .withIdentity(jobClass.getSimpleName())
                .withSchedule(cronSchedule)
                .build();
    }
}
