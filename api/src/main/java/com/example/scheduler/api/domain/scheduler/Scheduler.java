package com.example.scheduler.api.domain.scheduler;

import com.example.scheduler.api.domain.BaseEntity;
import com.example.scheduler.api.domain.scheduler.model.SchedulerInfoView;
import com.example.scheduler.api.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "scheduler", schema = "schedulerdb")
public class Scheduler extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @Column
    private String date;
    @Column
    private String dateYear;
    @Column
    private String dateMonth;
    @Column
    private String dateDay;
    @Column
    private String time;
    @Column
    private String timeHour;
    @Column
    private String timeMin;
    @Column
    private boolean isAlarm;
    @Column
    @Enumerated(value = EnumType.STRING)
    private AlarmType alarmType;
    @Column
    private String alarmTime;
    @Column
    private boolean isRepeat;
    @Column
    @Enumerated(value = EnumType.STRING)
    private RepeatType repeatType;
    @Column
    private boolean isHoliday;
    @Column
    private String memo;

    @Builder
    public Scheduler(Long id, User user, String date, String dateYear, String dateMonth, String dateDay, String time, String timeHour, String timeMin, boolean isAlarm, AlarmType alarmType, String alarmTime, boolean isRepeat, RepeatType repeatType, boolean isHoliday, String memo) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.dateYear = dateYear;
        this.dateMonth = dateMonth;
        this.dateDay = dateDay;
        this.time = time;
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

    public SchedulerInfoView toSchedulerInfoView() {
        return SchedulerInfoView.builder()
                .dateYear(this.dateYear)
                .alarmType(this.alarmType)
                .dateDay(this.dateDay)
                .dateMonth(this.dateMonth)
                .isAlarm(this.isAlarm)
                .isHoliday(this.isHoliday)
                .isRepeat(this.isRepeat)
                .memo(this.memo)
                .id(this.id)
                .timeHour(this.timeHour)
                .timeMin(this.timeMin)
                .repeatType(this.repeatType)
                .userId(this.user.getId())
                .build();
    }

}
