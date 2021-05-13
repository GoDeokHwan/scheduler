package com.example.scheduler.api.domain.scheduler;

import com.example.scheduler.api.domain.scheduler.model.SchedulerInfoRequest;
import com.example.scheduler.api.support.api.ApiResult;
import com.example.scheduler.api.support.api.ApiStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api")
public class SchedulerController {

    @Autowired
    SchedulerService schedulerService;

    @PostMapping("/v1/scheduler")
    public ApiResult save(@Valid @RequestBody SchedulerInfoRequest request) {
        return ApiResult.of(ApiStatus.SUCCESS, schedulerService.save(request));
    }

    @GetMapping("/v1/schedulers/{userId}")
    public ApiResult findSchedulers(@PathVariable(value = "userId") Long userId,
                                    @RequestParam(value = "year") String year,
                                    @RequestParam(value = "month") String month) {
        return ApiResult.of(ApiStatus.SUCCESS, schedulerService.findSchedulers(userId,year, month));
    }

    @PutMapping("/v1/schedulers/{id}")
    public ApiResult updateScheduler(@PathVariable(value = "id") Long id,
                                     @RequestBody SchedulerInfoRequest request) {
        return ApiResult.of(ApiStatus.SUCCESS, schedulerService.updateScheduler(id, request));
    }

    @DeleteMapping("/v1/schedulers/{id}")
    public ApiResult deleteScheduler(@PathVariable(value = "id") Long id) {
        schedulerService.deleteScheduler(id);
        return ApiResult.of(ApiStatus.SUCCESS);
    }
}
