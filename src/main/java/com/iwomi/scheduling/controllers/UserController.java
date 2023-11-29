package com.iwomi.scheduling.controllers;

import com.iwomi.scheduling.core.utils.CronValues;
import com.iwomi.scheduling.data.entities.UserEntity;
import com.iwomi.scheduling.data.repositories.UserRepository;
import com.iwomi.scheduling.jobs.DeleteUsersJob;
import com.iwomi.scheduling.models.ScheduleInfoModel;
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
    public void runHelloWorldJob(@PathVariable String id) {

        ScheduleInfoModel model = new ScheduleInfoModel();
        model.setTotalFireCount(3);
        model.setRemainingFireCount(model.getTotalFireCount());
        model.setData(Map.of("id", id));

        iSchedulerService.schedule(DeleteUsersJob.class, CronValues.EVERY_4_SECONDS, model);
    }


}
