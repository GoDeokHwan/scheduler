package com.example.scheduler.api.domain.holiday;

import com.example.scheduler.api.domain.holiday.model.HolidayRequest;
import com.example.scheduler.api.support.api.ApiResult;
import com.example.scheduler.api.support.api.ApiStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api")
public class HolidayController {

    @Autowired
    HolidayService holidayService;

    @PostMapping("/v1/holidays")
    public ApiResult save (@Valid @RequestBody  HolidayRequest request) {
        return ApiResult.of(ApiStatus.SUCCESS, holidayService.save(request));
    }

    @GetMapping("/v1/holidays/{userId}")
    public ApiResult findAll (@PathVariable(name = "userId") Long userId,
                              @RequestParam(name = "year") String year,
                              @RequestParam(name = "month") String month) {
        return ApiResult.of(ApiStatus.SUCCESS, holidayService.findHolidays(userId, year, month));
    }

    @PutMapping("/v1/holidays/{id}")
    public ApiResult update(@PathVariable(name = "id") Long id,
                            @RequestBody  HolidayRequest request) {
        return ApiResult.of(ApiStatus.SUCCESS, holidayService.updateHolidayName(id, request));
    }

    @DeleteMapping("/v1/holidays/{id}")
    public ApiResult delete(@PathVariable(name = "id") Long id) {
        holidayService.delete(id);
        return ApiResult.of(ApiStatus.SUCCESS);
    }

}
