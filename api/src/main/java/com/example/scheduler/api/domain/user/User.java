package com.example.scheduler.api.domain.user;

import com.example.scheduler.api.domain.BaseEntity;
import com.example.scheduler.api.domain.UseStatus;
import com.example.scheduler.api.domain.scheduler.Scheduler;
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

    @Builder
    public User(Long id, String loginId, String password, String name, String phoneNumber, String tokenKey, UseStatus status, List<Scheduler> schedulers) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.phoneNumber = StringUtils.isNotBlank(phoneNumber) ? phoneNumber.replace("-", "") : phoneNumber;
        this.tokenKey = tokenKey;
        this.status = status;
        this.schedulers = schedulers;
    }
}
