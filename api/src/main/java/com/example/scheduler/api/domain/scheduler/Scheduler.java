package com.example.scheduler.api.domain.scheduler;

import com.example.scheduler.api.domain.BaseEntity;
import com.example.scheduler.api.domain.user.User;
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
    private String isRepeat;
    @Column
    @Enumerated(value = EnumType.STRING)
    private RepeatType repeatType;
    @Column
    private boolean isHoliday;
    @Column
    private String memo;

}
