package com.example.scheduler.api.domain.holiday;

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
@Table(name = "holiday", schema = "schedulerdb")
public class Holiday extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column
    private String name;

    @Column
    private String memo;

    @Column
    private String date;

    @Column
    private String dateYear;

    @Column
    private String dateMonth;

    @Column
    private String dateDay;

    @Column
    private boolean isCommon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
