package com.iwomi.scheduling.controllers;

import com.iwomi.scheduling.data.entities.UserEntity;
import com.iwomi.scheduling.data.repositories.UserRepository;
import com.iwomi.scheduling.jobs.DeleteUsersJob;
import com.iwomi.scheduling.models.TimerModel;
import com.iwomi.scheduling.services.ISchedulerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final ISchedulerService iSchedulerService;
    private final UserRepository userRepository;

    public UserController(ISchedulerService iSchedulerService, UserRepository userRepository) {
        this.iSchedulerService = iSchedulerService;
        this.userRepository = userRepository;
    }

    @GetMapping()
    public ResponseEntity<?> users() {
        List<UserEntity> userEntity = userRepository.findAll();
        return ResponseEntity.ok().body(userEntity);
    }

    @PostMapping("/delete/{id}")
    public void deleteJob(@PathVariable String id) {
        /// This is the model data containing all relevant data for
        /// the Job scheduling.
        /// most of this data is needed by Quartz SimpleScheduleBuilder,
        // JobBuilder & TriggerBuilder in ***TimerUtils.java
        TimerModel model = new TimerModel();
        model.setTotalFireCount(3);
        model.setRemainingFireCount(model.getTotalFireCount());
        model.setInitialOffsetMs(2000); // make to set in hours. convert hours to seconds
        model.setRepeatIntervalMs(1000);
        model.setData(Map.of("id", id));

        iSchedulerService.schedule(DeleteUsersJob.class, model);
    }


}
