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

import java.util.List;

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
        TimerModel info = (TimerModel) jobDataMap.get(DeleteUsersJob.class.getSimpleName());
        List<UserEntity> userEntity = userRepository.findAll();
        if (userEntity.isEmpty()) {
            log.info("Nothing to delete. Remaining fire count is '{}'", info.getRemainingFireCount());
        } else {
            userRepository.deleteAll();
            log.info("Deleted all. Remaining fire count is '{}'", info.getRemainingFireCount());
        }
//        log.info("Remaining fire count is '{}'", info.getRemainingFireCount());
    }
}
