package com.iwomi.scheduling.jobs;

import com.iwomi.scheduling.data.entities.UserEntity;
import com.iwomi.scheduling.data.repositories.UserRepository;
import com.iwomi.scheduling.models.ScheduleInfoModel;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        ScheduleInfoModel model = (ScheduleInfoModel) jobDataMap.get(DeleteUsersJob.class.getSimpleName());
        String id = model.getData().get("id");

        Optional<UserEntity> userEntity = userRepository.findById(id);
        userEntity.ifPresent(userEntity1 -> {
            log.info(String.valueOf(userEntity1));
        });

        log.info("Remaining fire count is '{}'", model.getRemainingFireCount());
    }
}
