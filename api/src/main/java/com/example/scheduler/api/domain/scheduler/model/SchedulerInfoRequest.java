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
public class SchedulerInfoRequest {
    @NotNull(message = "사용자 ID는 필수 값입니다.")
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

    @Builder
    public SchedulerInfoRequest(@NotNull Long userId, String dateYear, String dateMonth, String dateDay, String timeHour, String timeMin, boolean isAlarm, AlarmType alarmType, String alarmTime, boolean isRepeat, RepeatType repeatType, boolean isHoliday, String memo) {
        this.userId = userId;
        this.dateYear = dateYear;
        this.dateMonth = dateMonth;
        this.dateDay = dateDay;
        this.timeHour = timeHour;
        this.timeMin = timeMin;
        this.isAlarm = isAlarm;
        this.alarmType = alarmType;
        this.alarmTime = alarmTime;
        this.isRepeat = isRepeat;
        this.repeatType = repeatType;
        this.isHoliday = isHoliday;
        this.memo = memo;
    }

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
        return String.format("%04d", Integer.valueOf(this.dateYear)) + String.format("%02d", Integer.valueOf(this.dateMonth)) + String.format("%02d", Integer.valueOf(this.dateDay));
    }

    public String fullTime() {
        return String.format("%02d", Integer.valueOf(this.timeHour)) + String.format("%02d", Integer.valueOf(this.timeMin));
    }
}
