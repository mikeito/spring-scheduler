package com.iwomi.scheduling.listeners;

import com.iwomi.scheduling.jobs.DeleteUsersJob;
import com.iwomi.scheduling.models.TimerModel;
import com.iwomi.scheduling.services.ISchedulerService;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.listeners.TriggerListenerSupport;

/// We have implemented only methods we need. Check Quartz doc for more
/// details
public class SimpleTriggerListener extends TriggerListenerSupport {
    private final ISchedulerService schedulerService;

    public SimpleTriggerListener(ISchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @Override
    public String getName() {
        return SimpleTriggerListener.class.getSimpleName();
    }

    /// Runs on every trigger. That is if we've set number of runs in
    /// TimerModel to 3 time, it fires each time
    ///
    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        final String scheduleId = trigger.getKey().getName();

        final JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        final TimerModel model = (TimerModel) jobDataMap.get(DeleteUsersJob.class.getSimpleName());
        final Integer count = model.getRemainingFireCount();
        System.out.println("trigger fired "+model.getRemainingFireCount());

//        if (count == 0) {
//            return;
//        } else {
//            model.setRemainingFireCount(count - 1);
//        }

//            info.setRemainingFireCount(remainingFireCount - 1);

        schedulerService.updateTimer(scheduleId, model);
    }

}
