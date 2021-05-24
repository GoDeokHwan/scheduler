package com.example.scheduler.api.domain.user;

import com.example.scheduler.api.domain.BaseEntity;
import com.example.scheduler.api.domain.UseStatus;
import com.example.scheduler.api.domain.holiday.Holiday;
import com.example.scheduler.api.domain.scheduler.Scheduler;
import com.example.scheduler.api.domain.user.model.UserInfoView;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "user", schema = "schedulerdb")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column
    private String loginId;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String phoneNumber;

    @Column
    private String tokenKey;

    @Column
    @Enumerated(value = EnumType.STRING)
    private UseStatus status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Scheduler> schedulers = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Holiday> holidays = new ArrayList<>();

    @Builder
    public User(Long id, String loginId, String password, String name, String phoneNumber, String tokenKey, UseStatus status, List<Scheduler> schedulers, List<Holiday> holidays) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.tokenKey = tokenKey;
        this.status = status;
        this.schedulers = schedulers;
        this.holidays = holidays;
    }

    public UserInfoView toUserinfoView() {
        return UserInfoView.builder()
                .name(this.name)
                .loginId(this.loginId)
                .phoneNumber(this.phoneNumber)
                .status(this.status)
                .tokenKey(this.tokenKey)
                .build();
    }

    public boolean isEnable () {
        return status.isEnable();
    }

    public void clearTokenKey () {
        this.tokenKey = null;
    }
}
