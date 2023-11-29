package com.iwomi.scheduling.services;

import com.iwomi.scheduling.models.ScheduleInfoModel;
import org.quartz.Job;

import java.util.List;

public interface ISchedulerService {
    <T extends Job> void schedule(final Class<T> jobClass, final String cronString, final ScheduleInfoModel model);
    List<ScheduleInfoModel> getAllRunningTimers();
    ScheduleInfoModel getRunningTimer(final String timerId);
    void updateTimer(final String timerId, final ScheduleInfoModel info);
    Boolean deleteTimer(final String timerId);
}
