package com.iwomi.scheduling.listeners;

import com.iwomi.scheduling.jobs.DeleteUsersJob;
import com.iwomi.scheduling.models.ScheduleInfoModel;
import com.iwomi.scheduling.services.ISchedulerService;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;
import org.quartz.listeners.JobListenerSupport;
import org.quartz.listeners.TriggerListenerSupport;

import java.util.HashMap;

public class SimpleTriggerListener extends TriggerListenerSupport {
    private final ISchedulerService schedulerService;

    public SimpleTriggerListener(ISchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @Override
    public String getName() {
        return SimpleTriggerListener.class.getSimpleName();
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        final String scheduleId = trigger.getKey().getName();

        final JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        final ScheduleInfoModel model = (ScheduleInfoModel) jobDataMap.get(DeleteUsersJob.class.getSimpleName());
        final Integer count = model.getRemainingFireCount();

//        if (count == 0) {
//            return;
//        } else {
//            model.setRemainingFireCount(count - 1);
//        }

//            info.setRemainingFireCount(remainingFireCount - 1);

        schedulerService.updateTimer(scheduleId, model);
    }

}
