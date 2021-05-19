package com.example.scheduler.api.domain.holiday.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HolidayInfoView {
    private Long id;
    private String name;
    private String memo;
    private String date;
    private String dateYear;
    private String dateMonth;
    private String dateDay;
    private Long userId;
    private boolean isCommon;

    @Builder
    public HolidayInfoView(Long id, String name, String memo, String date, String dateYear, String dateMonth, String dateDay, Long userId, boolean isCommon) {
        this.id = id;
        this.name = name;
        this.memo = memo;
        this.date = date;
        this.dateYear = dateYear;
        this.dateMonth = dateMonth;
        this.dateDay = dateDay;
        this.userId = userId;
        this.isCommon = isCommon;
    }
}
