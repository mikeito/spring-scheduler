package com.iwomi.scheduling.controllers;

import com.iwomi.scheduling.models.TimerModel;
import com.iwomi.scheduling.services.PlaygroundService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timer")
public class PlaygroundController {
    private final PlaygroundService service;

    public PlaygroundController(PlaygroundService service) {
        this.service = service;
    }

    @PostMapping("/test")
    public void runHelloWorldJob() {
        service.runHelloWorldJob();
    }

    @GetMapping
    public List<TimerModel> getAllRunningTimers() {
        return service.getAllRunningTimers();
    }

    @GetMapping("/{timerId}")
    public TimerModel getRunningTimer(@PathVariable String timerId) {
        return service.getRunningTimer(timerId);
    }

    @DeleteMapping("/{timerId}")
    public Boolean deleteTimer(@PathVariable String timerId) {
        return service.deleteTimer(timerId);
    }
}
