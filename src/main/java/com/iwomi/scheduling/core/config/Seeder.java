package com.iwomi.scheduling.core.config;

import com.iwomi.scheduling.data.entities.UserEntity;
import com.iwomi.scheduling.data.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class Seeder {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository) {

        UserEntity user1 = userRepository.save(UserEntity.builder()
                .name("Thierry Henry")
                .email("henry@gmail.com").build());

        UserEntity user2 = userRepository.save(UserEntity.builder()
                .name("Luda Rashford")
                .email("ford@gmail.com").build());


        return args -> {
            log.info("Preloading " + user1);
            log.info("Preloading " + user2);

        };
    }
}
