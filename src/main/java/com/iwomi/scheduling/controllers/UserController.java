package com.iwomi.scheduling.controllers;

import com.iwomi.scheduling.data.entities.UserEntity;
import com.iwomi.scheduling.data.repositories.UserRepository;
import com.iwomi.scheduling.services.PlaygroundService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final PlaygroundService service;
    private final UserRepository userRepository;

    public UserController(PlaygroundService service, UserRepository userRepository) {
        this.service = service;
        this.userRepository = userRepository;
    }

    @GetMapping()
    public ResponseEntity<?> users() {
        List<UserEntity> userEntity = userRepository.findAll();
        return ResponseEntity.ok().body(userEntity);
    }

    @PostMapping("/delete")
    public void runHelloWorldJob() {
        service.removeUserAllUsers();
    }


}
