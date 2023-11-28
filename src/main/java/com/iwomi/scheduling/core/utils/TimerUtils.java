package com.iwomi.scheduling.core.utils;

import com.iwomi.scheduling.models.TimerModel;
import lombok.NoArgsConstructor;
import org.quartz.*;

import java.util.Date;

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
