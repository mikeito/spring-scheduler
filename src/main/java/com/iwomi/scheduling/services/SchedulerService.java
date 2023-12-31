package com.iwomi.scheduling.services;

import com.iwomi.scheduling.core.utils.TimerUtils;
//import com.iwomi.scheduling.listeners.SimpleTriggerListener;
import com.iwomi.scheduling.listeners.SimpleTriggerListener;
import com.iwomi.scheduling.models.TimerModel;
import com.iwomi.scheduling.services.ISchedulerService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SchedulerService implements ISchedulerService {
    private final Scheduler scheduler;

    public SchedulerService(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public <T extends Job> void schedule(final Class<T> jobClass, final TimerModel model) {
        // build necessary data for scheduling.
        // This is all needed for SimpleScheduleBuilder as per Quartz docs
        final JobDetail jobDetail = TimerUtils.buildJobDetail(jobClass, model);
        final Trigger trigger = TimerUtils.buildTrigger(jobClass, model);

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
        }
    }

    // This queries jobs and return all
    @Override
    public List<TimerModel> getAllRunningTimers() {
        try {
            return scheduler.getJobKeys(GroupMatcher.anyGroup())
                    .stream()
                    .map(jobKey -> {
                        try {
                            final JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                            return (TimerModel) jobDetail.getJobDataMap().get(jobKey.getName());
                        } catch (final SchedulerException e) {
                            log.error(e.getMessage(), e);
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (final SchedulerException e) {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    // return specific running job or timer
    @Override
    public TimerModel getRunningTimer(final String timerId) {
        try {
            final JobDetail jobDetail = scheduler.getJobDetail(new JobKey(timerId));
            if (jobDetail == null) {
                log.error("Failed to find timer with ID '{}'", timerId);
                return null;
            }

            return (TimerModel) jobDetail.getJobDataMap().get(timerId);
        } catch (final SchedulerException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public void updateTimer(final String timerId, final TimerModel info) {
        try {
            final JobDetail jobDetail = scheduler.getJobDetail(new JobKey(timerId));
            if (jobDetail == null) {
                log.error("Failed to find timer with ID '{}'", timerId);
                return;
            }

            jobDetail.getJobDataMap().put(timerId, info);

            scheduler.addJob(jobDetail, true, true);
        } catch (final SchedulerException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public Boolean deleteTimer(final String timerId) {
        try {
            return scheduler.deleteJob(new JobKey(timerId));
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    // This starts the scheduler and registers our listener
    // with this service. See **SimpleTriggerListener class.
    @PostConstruct
    public void init() {
        try {
            scheduler.start();
            scheduler.getListenerManager().addTriggerListener(new SimpleTriggerListener(this));
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
        }
    }

    @PreDestroy
    public void preDestroy() {
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
        }
    }
}
