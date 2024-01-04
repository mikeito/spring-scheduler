package com.iwomi.scheduling.jobs;

import com.iwomi.scheduling.data.entities.UserEntity;
import com.iwomi.scheduling.data.repositories.UserRepository;
import com.iwomi.scheduling.models.TimerModel;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class DeleteUsersJob implements Job {
    private final UserRepository userRepository;

    public DeleteUsersJob(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        TimerModel model = (TimerModel) jobDataMap.get(DeleteUsersJob.class.getSimpleName());
        String id = model.getData().get("id");

        Optional<UserEntity> userEntity = userRepository.findById(id);
        userEntity.ifPresent(userEntity1 -> {
            log.info(String.valueOf(userEntity1));
            userRepository.deleteById(id);
        });

        log.info("Remaining fire count is;;;; '{}'", model.getRemainingFireCount());
    }
}

//
