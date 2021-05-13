package com.example.scheduler.api.domain.holiday.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HolidayRequest {
    private String name;
    private String memo;
    private String date;
    private String dateYear;
    private String dateMonth;
    private String dateDay;
    private boolean isCommon;
    private Long userId;

}
