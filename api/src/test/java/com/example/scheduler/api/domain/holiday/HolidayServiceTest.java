package com.example.scheduler.api.domain.holiday;

import com.example.scheduler.api.domain.holiday.model.HolidayInfoView;
import com.example.scheduler.api.domain.holiday.model.HolidayRequest;
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
class HolidayServiceTest {

    @Autowired
    HolidayService holidayService;

    @Test
    public void 개인_공휴일_저장 () {
        HolidayRequest request = HolidayRequest.builder()
                .userId(1l)
                .dateYear("2021")
                .dateMonth("05")
                .dateDay("14")
                .name("테스트 탄생일")
                .memo("API 완성에 날")
                .build();

        HolidayInfoView holidayInfoView = holidayService.save(request);

        assertThat(holidayInfoView.getDate()).isEqualTo(request.fullDate());
        assertThat(holidayInfoView.getName()).isEqualTo(request.getName());
    }

    @Test
    public void 개인_공휴일_닐찌_에러 () {
        HolidayRequest request = HolidayRequest.builder()
                .userId(1l)
                .dateYear("2021")
                .dateMonth("51")
                .dateDay("14")
                .name("테스트 탄생일")
                .memo("API 완성에 날")
                .build();

        assertThatThrownBy(() -> holidayService.save(request))
                .isInstanceOf(ApiException.class)
                .hasMessage(ApiStatus.IS_DATETIME_ERROR.getMessage());
    }

    @Test
    public void 개인_공휴일_사용자_에러 () {
        HolidayRequest request = HolidayRequest.builder()
                .userId(5l)
                .dateYear("2021")
                .dateMonth("12")
                .dateDay("14")
                .name("테스트 탄생일")
                .memo("API 완성에 날")
                .build();

        assertThatThrownBy(() -> holidayService.save(request))
                .isInstanceOf(ApiException.class)
                .hasMessage(ApiStatus.IS_NOT_USER.getMessage());
    }

    @Test
    public void 공휴일_조회 () {
        List<HolidayInfoView> holidayInfoViews = holidayService.findHolidays(1l, "2021", "05");
        assertThat(holidayInfoViews.size()).isEqualTo(2);
    }

    @Test
    public void 공휴일_조회_날짜_에러 () {
        assertThatThrownBy(() -> holidayService.findHolidays(1l, "2021", "13"))
                .isInstanceOf(ApiException.class)
                .hasMessage(ApiStatus.IS_DATETIME_ERROR.getMessage());
    }

    @Test
    public void 공휴일_조회_사용자_에러 () {
        assertThatThrownBy(() -> holidayService.findHolidays(4l, "2021", "12"))
                .isInstanceOf(ApiException.class)
                .hasMessage(ApiStatus.IS_NOT_USER.getMessage());
    }

    @Test
    public void 공휴일_수정 () {
        HolidayRequest request = HolidayRequest.builder()
                .name("테스트 탄생일")
                .memo("API 완성에 날")
                .build();

        HolidayInfoView holidayInfoView = holidayService.updateHolidayName(2l, request);

        assertThat(holidayInfoView.getName()).isEqualTo(request.getName());
        assertThat(holidayInfoView.getMemo()).isEqualTo(request.getMemo());
    }

    @Test
    public void 공휴일_수정_공통_공휴일_에러 () {
        HolidayRequest request = HolidayRequest.builder()
                .name("테스트 탄생일")
                .memo("API 완성에 날")
                .build();

        assertThatThrownBy(() -> holidayService.updateHolidayName(1l, request))
                .isInstanceOf(ApiException.class)
                .hasMessage(ApiStatus.COMMON_HOLIDAY_NOT_UPDATE.getMessage());
    }

    @Test
    public void 공휴일_수정_공휴일_찾기_에러 () {
        HolidayRequest request = HolidayRequest.builder()
                .name("테스트 탄생일")
                .memo("API 완성에 날")
                .build();

        assertThatThrownBy(() -> holidayService.updateHolidayName(4l, request))
                .isInstanceOf(ApiException.class)
                .hasMessage(ApiStatus.IS_NOT_HOLIDAY.getMessage());
    }

    @Test
    public void 공휴일_삭제 () {
        holidayService.delete(2l);
        assertThat(holidayService.findHolidays(1l, "2021", "05").size()).isEqualTo(1);
    }

    @Test
    public void 공휴일_삭제_공휴일_찾기_에러 () {
        assertThatThrownBy(() -> holidayService.delete(4l))
                .isInstanceOf(ApiException.class)
                .hasMessage(ApiStatus.IS_NOT_HOLIDAY.getMessage());
    }

    @Test
    public void 공휴일_삭제_공통_공휴일_에러 () {
        assertThatThrownBy(() -> holidayService.delete(1l))
                .isInstanceOf(ApiException.class)
                .hasMessage(ApiStatus.COMMON_HOLIDAY_NOT_DELETE.getMessage());
    }

}