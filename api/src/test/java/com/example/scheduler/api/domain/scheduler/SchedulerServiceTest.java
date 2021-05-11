package com.example.scheduler.api.domain.scheduler;

import com.example.scheduler.api.domain.scheduler.model.SchedulerInfoRequest;
import com.example.scheduler.api.domain.scheduler.model.SchedulerInfoView;
import com.example.scheduler.api.support.api.ApiException;
import com.example.scheduler.api.support.api.ApiStatus;
import com.example.scheduler.api.testsupport.ShedulerComponentTest;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@ShedulerComponentTest
@ActiveProfiles("memory")
@Transactional
@SpringBootTest
@Sql(scripts = "classpath:init-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class SchedulerServiceTest {

    @Autowired
    SchedulerService schedulerService;

    @Autowired
    SchedulerRepository schedulerRepository;

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

        SchedulerInfoView scheduler = schedulerService.save(request);

        assertThat(request.getMemo()).isEqualTo(scheduler.getMemo());
    }

    @Test
    public void 스케줄러_추가시_날짜_장애 () {
        SchedulerInfoRequest request = SchedulerInfoRequest.builder()
                .dateYear("2021")
                .dateMonth("13")
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

        assertThatThrownBy(() -> { schedulerService.save(request); })
        .isInstanceOf(ApiException.class)
        .hasMessage(ApiStatus.IS_DATETIME_ERROR.getMessage());
    }

    @Test
    public void 스케쥴러_추가시_사용자ID_장애 () {
        SchedulerInfoRequest request = SchedulerInfoRequest.builder()
                .dateYear("2021")
                .dateMonth("11")
                .dateDay("10")
                .timeHour("11")
                .timeMin("45")
                .alarmTime("10")
                .alarmType(AlarmType.MINUTE)
                .isAlarm(true)
                .isHoliday(false)
                .isRepeat(false)
                .memo("점심 약속")
                .userId(3l)
                .build();

        assertThatThrownBy(() -> { schedulerService.save(request); })
                .isInstanceOf(ApiException.class)
                .hasMessage(ApiStatus.IS_NOT_USER.getMessage());
    }

    @Test
    public void 스케쥴러_조회 () {
        List<SchedulerInfoView> schedulers = schedulerService.findSchedulers(1l, "2021", "05");
        assertThat(schedulers.size()).isEqualTo(2);
    }

    @Test
    public void 스케쥴러_조회_날짜_에러 () {
        assertThatThrownBy(() -> { schedulerService.findSchedulers(1l, "년", "13"); })
                .isInstanceOf(ApiException.class)
                .hasMessage(ApiStatus.IS_DATETIME_ERROR.getMessage());
    }

    @Test
    public void 스케쥴러_조회_사용자_에러 () {
        assertThatThrownBy(() -> { schedulerService.findSchedulers(3l, "2021", "05"); })
                .isInstanceOf(ApiException.class)
                .hasMessage(ApiStatus.IS_NOT_USER.getMessage());
    }

    @Test
    public void 스케쥴러_수정 () {
        SchedulerInfoRequest request = SchedulerInfoRequest.builder()
                .dateYear("2021")
                .dateMonth("11")
                .dateDay("21")
                .timeHour("11")
                .timeMin("45")
                .alarmTime("10")
                .alarmType(AlarmType.MINUTE)
                .isAlarm(true)
                .isHoliday(false)
                .isRepeat(false)
                .memo("점심 약속")
                .userId(3l)
                .build();

        SchedulerInfoView schedulerInfoView = schedulerService.updateScheduler(1l, request);
        assertThat(schedulerInfoView.getId()).isEqualTo(1l);
        assertThat(schedulerInfoView.getMemo()).isEqualTo("점심 약속");
    }

    @Test
    public void 스케쥴러_수정_날짜_에러 () {
        SchedulerInfoRequest request = SchedulerInfoRequest.builder()
                .dateYear("2021")
                .dateMonth("11")
                .dateDay("날짜")
                .timeHour("11")
                .timeMin("45")
                .alarmTime("10")
                .alarmType(AlarmType.MINUTE)
                .isAlarm(true)
                .isHoliday(false)
                .isRepeat(false)
                .memo("점심 약속")
                .userId(3l)
                .build();

        assertThatThrownBy(() -> { schedulerService.updateScheduler(1l, request); })
                .isInstanceOf(ApiException.class)
                .hasMessage(ApiStatus.IS_DATETIME_ERROR.getMessage());
    }

    @Test
    public void 스케쥴러_수정_스케쥴러_찾기_에러 () {
        assertThatThrownBy(() -> { schedulerService.updateScheduler(3l, SchedulerInfoRequest.builder()
                .dateYear("2021")
                .dateMonth("11")
                .dateDay("24")
                .timeHour("11")
                .timeMin("45")
                .build());
        }).isInstanceOf(ApiException.class).hasMessage(ApiStatus.IS_NOT_SCHEDULER.getMessage());
    }

    @Test
    public void 스케쥴러_삭제 () {
        schedulerService.deleteScheduler(1l);

        assertThatThrownBy(() -> { schedulerRepository.findById(1l).orElseThrow(() -> new ApiException(ApiStatus.IS_NOT_SCHEDULER)); })
                .isInstanceOf(ApiException.class)
                .hasMessage(ApiStatus.IS_NOT_SCHEDULER.getMessage());
    }

    @Test
    public void 스케쥴러_삭제_찾기_에러 () {
        assertThatThrownBy(() -> { schedulerService.deleteScheduler(5l); })
                .isInstanceOf(ApiException.class)
                .hasMessage(ApiStatus.IS_NOT_SCHEDULER.getMessage());
    }
}