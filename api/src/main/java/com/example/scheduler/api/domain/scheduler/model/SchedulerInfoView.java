package com.example.scheduler.api.domain.scheduler.model;

import com.example.scheduler.api.domain.scheduler.AlarmType;
import com.example.scheduler.api.domain.scheduler.RepeatType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SchedulerInfoView {

    private Long id;
    private Long userId;
    private String dateYear;
    private String dateMonth;
    private String dateDay;
    private String timeHour;
    private String timeMin;
    private boolean isAlarm;
    private AlarmType alarmType;
    private boolean isRepeat;
    private RepeatType repeatType;
    private boolean isHoliday;
    private String memo;

}
