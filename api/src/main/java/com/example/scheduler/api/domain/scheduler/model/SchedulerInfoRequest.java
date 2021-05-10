package com.example.scheduler.api.domain.scheduler.model;

import com.example.scheduler.api.domain.scheduler.AlarmType;
import com.example.scheduler.api.domain.scheduler.RepeatType;
import com.example.scheduler.api.util.DateUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import com.example.scheduler.api.util.StringUtils;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class SchedulerInfoRequest {
    @NotNull
    private Long userId;
    private String dateYear;
    private String dateMonth;
    private String dateDay;
    private String timeHour;
    private String timeMin;
    private boolean isAlarm;
    private AlarmType alarmType;
    private String alarmTime;
    private boolean isRepeat;
    private RepeatType repeatType;
    private boolean isHoliday;
    private String memo;

    public boolean isDateValidation() {
        if (StringUtils.isInteger(this.dateYear) &&
                StringUtils.isInteger(this.dateMonth) &&
                StringUtils.isInteger(this.dateDay)) {
            if (DateUtils.isDate(Integer.valueOf(this.dateYear), Integer.valueOf(this.dateMonth), Integer.valueOf(this.dateDay))) {
                return true;
            }
        }
        return false;
    }

    public boolean isTimeValidation() {
        if (StringUtils.isInteger(this.timeHour) &&
                StringUtils.isInteger(this.timeMin)) {
            if (DateUtils.isTime(Integer.valueOf(this.timeHour), Integer.valueOf(this.timeMin))) {
                return true;
            }
        }
        return false;
    }

    public String fullDate() {
        return this.dateYear + this.dateMonth + this.dateDay;
    }

    public String fullTime() {
        return this.timeHour + this.timeMin;
    }
}
