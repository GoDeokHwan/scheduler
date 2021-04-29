package com.example.scheduler.api.domain.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class SampleController {

    @Autowired
    SampleService sampleService;

    @GetMapping("/sample/users")
    public List<SampleUser> findAll() {
        return sampleService.findAll();
    }
}
