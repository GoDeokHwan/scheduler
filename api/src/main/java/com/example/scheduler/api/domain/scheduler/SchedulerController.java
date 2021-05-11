package com.example.scheduler.api.domain.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class SchedulerController {

    @Autowired
    SchedulerService schedulerService;

    
}
