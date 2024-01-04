package com.iwomi.scheduling.controllers;

import com.iwomi.scheduling.dto.ScheduleDto;
import com.iwomi.scheduling.jobs.DeleteUsersJob;
import com.iwomi.scheduling.models.TimerModel;
import com.iwomi.scheduling.services.ISchedulerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {
    private final ISchedulerService iSchedulerService;

    public ScheduleController(ISchedulerService iSchedulerService) {
        this.iSchedulerService = iSchedulerService;
    }

    @PostMapping()
    public void schedulerJob(@RequestBody ScheduleDto dto) {
        Map<String, String> _data = new HashMap<>();
        _data.put("id", "data");
        /// This is the model data containing all relevant data for
        /// the Job scheduling.
        /// most of this data is needed by Quartz SimpleScheduleBuilder,
        // JobBuilder & TriggerBuilder in ***TimerUtils.java
        TimerModel model = new TimerModel();
        model.setTotalFireCount(dto.fireNumber());
        model.setRemainingFireCount(model.getTotalFireCount());
        model.setInitialOffsetMs(2000); // make to set in hours. convert hours to seconds
        model.setRepeatIntervalMs(dto.repeatInterval());
        model.setData(_data); // pass data to job class.

//        iSchedulerService.schedule(dto.url(), model);
    }
}
