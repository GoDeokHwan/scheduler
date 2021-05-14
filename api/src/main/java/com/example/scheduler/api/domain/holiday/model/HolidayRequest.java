package com.example.scheduler.api.domain.holiday.model;

import com.example.scheduler.api.util.DateUtils;
import com.example.scheduler.api.util.StringUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class HolidayRequest {
    private String name;
    private String memo;
    private String dateYear;
    private String dateMonth;
    private String dateDay;
    @NotNull(message = "사용자 ID는 필수 값입니다.")
    private Long userId;

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

    public String fullDate() {
        return String.format("%04d", Integer.valueOf(this.dateYear)) + String.format("%02d", Integer.valueOf(this.dateMonth)) + String.format("%02d", Integer.valueOf(this.dateDay));
    }
}
