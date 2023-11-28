package com.iwomi.scheduling.services;

import com.iwomi.scheduling.jobs.DeleteUsersJob;
import com.iwomi.scheduling.jobs.HelloWorldJob;
import com.iwomi.scheduling.models.TimerModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaygroundService {
    private final SchedulerService scheduler;

    public PlaygroundService(SchedulerService schedulerService) {
        this.scheduler = schedulerService;
    }

    public void runHelloWorldJob() {
        final TimerModel model = new TimerModel();
        model.setTotalFireCount(5);
        model.setRemainingFireCount(model.getTotalFireCount());
        model.setRepeatIntervalMs(5000);
        model.setInitialOffsetMs(1000);
        model.setCallbackData("My callback data");

        scheduler.schedule(HelloWorldJob.class, model);
    }

    public void removeUserAllUsers() {
        final TimerModel model = new TimerModel();
        model.setTotalFireCount(5);
        model.setRemainingFireCount(model.getTotalFireCount());
        model.setRepeatIntervalMs(5000);
        model.setInitialOffsetMs(1000);
        model.setCallbackData("My callback data");

        scheduler.schedule(DeleteUsersJob.class, model);
    }

    public Boolean deleteTimer(final String timerId) {
        return scheduler.deleteTimer(timerId);
    }

    public List<TimerModel> getAllRunningTimers() {
        return scheduler.getAllRunningTimers();
    }

    public TimerModel getRunningTimer(final String timerId) {
        return scheduler.getRunningTimer(timerId);
    }
}
