package com.example.scheduler.api.domain.sample;

import com.example.scheduler.api.domain.sample.model.UserRequest;
import com.example.scheduler.api.support.api.ApiResult;
import com.example.scheduler.api.support.api.ApiStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class SampleController {

    @Autowired
    SampleService sampleService;

    @GetMapping("/sample/users")
    public ApiResult findAll() {
        return ApiResult.of(ApiStatus.SUCCESS, sampleService.findAll());
    }

    @PostMapping("/sample/user")
    public ApiResult save(@RequestBody UserRequest request) {
        return ApiResult.of(ApiStatus.SUCCESS, sampleService.save(request));
    }

    @PostMapping("/sample/users")
    public ApiResult saveAll(@RequestBody List<UserRequest> requests) {
        sampleService.saveAll(requests);
        return ApiResult.of(ApiStatus.SUCCESS);
    }

}
