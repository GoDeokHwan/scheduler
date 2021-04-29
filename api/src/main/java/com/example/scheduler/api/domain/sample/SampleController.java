package com.example.scheduler.api.domain.sample;

import com.example.scheduler.api.domain.sample.model.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/sample/user")
    public SampleUser save(@RequestBody UserRequest request) {
        return sampleService.save(request);
    }
}
