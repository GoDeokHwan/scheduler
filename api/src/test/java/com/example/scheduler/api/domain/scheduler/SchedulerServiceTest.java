package com.example.scheduler.api.domain.scheduler;

import com.example.scheduler.api.domain.scheduler.model.SchedulerInfoRequest;
import com.example.scheduler.api.testsupport.ShedulerComponentTest;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ShedulerComponentTest
@ActiveProfiles("memory")
@Transactional
@SpringBootTest
@Sql(scripts = "classpath:init-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class SchedulerServiceTest {

    @Autowired
    SchedulerService schedulerService;

    @Test
    public void 스케쥴러_추가 () {
        SchedulerInfoRequest request = SchedulerInfoRequest.builder()
                .dateYear("2021")
                .dateMonth("05")
                .dateDay("10")
                .timeHour("11")
                .timeMin("45")
                .alarmTime("10")
                .alarmType(AlarmType.MINUTE)
                .isAlarm(true)
                .isHoliday(false)
                .isRepeat(false)
                .memo("점심 약속")
                .userId(1l)
                .build();

        Scheduler scheduler = schedulerService.save(request);

        assertThat(request.getMemo()).isEqualTo(scheduler.getMemo());
        assertThat(scheduler.getId()).isEqualTo(1l);
    }

}