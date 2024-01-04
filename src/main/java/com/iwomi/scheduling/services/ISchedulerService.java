package com.iwomi.scheduling.services;

import com.iwomi.scheduling.models.TimerModel;
import org.quartz.Job;

import java.util.List;

public interface ISchedulerService {
    <T extends Job> void schedule(final Class<T> jobClass, final TimerModel model);
    List<TimerModel> getAllRunningTimers();
    TimerModel getRunningTimer(final String timerId);
    void updateTimer(final String timerId, final TimerModel info);
    Boolean deleteTimer(final String timerId);
}
