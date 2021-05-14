package com.example.scheduler.api.domain.holiday;

import com.example.scheduler.api.domain.holiday.model.HolidayInfoView;
import com.example.scheduler.api.domain.holiday.model.HolidayRequest;
import com.example.scheduler.api.domain.user.User;
import com.example.scheduler.api.domain.user.UserService;
import com.example.scheduler.api.support.api.ApiException;
import com.example.scheduler.api.support.api.ApiStatus;
import com.example.scheduler.api.util.DateUtils;
import com.example.scheduler.api.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class HolidayService {

    @Autowired
    private HolidayRepository holidayRepository;

    @Autowired
    private UserService userService;

    /**
     * 개인 공휴일 저장
     * @param request
     * @result HolidayInfoView
     */
    public HolidayInfoView save(HolidayRequest request) {
        if (!request.isDateValidation()) {
            throw new ApiException(ApiStatus.IS_DATETIME_ERROR);
        }

        User user = userService.getUser(request.getUserId());

        return holidayRepository.save(
                Holiday.builder()
                        .date(request.fullDate())
                        .dateDay(request.getDateDay())
                        .dateMonth(request.getDateMonth())
                        .dateYear(request.getDateYear())
                        .isCommon(false)
                        .memo(request.getMemo())
                        .name(request.getName())
                        .user(user)
                        .build()
        ).toHolidayInfoVIew();
    }

    /**
     * 공휴일 조회
     * @param userId
     * @param year
     * @param month
     * @result List<HolidayInfoView>
     */
    @Transactional(readOnly = true)
    public List<HolidayInfoView> findHolidays (Long userId, String year, String month) {
        if (!(StringUtils.isInteger(year) && StringUtils.isInteger(month) && DateUtils.isDate(Integer.valueOf(year), Integer.valueOf(month), 1))) {
            throw new ApiException(ApiStatus.IS_DATETIME_ERROR);
        }

        User user = userService.getUser(userId);

        return user.getHolidays().stream()
                .filter(f -> f.getDateYear().equals(year) && f.getDateMonth().equals(month))
                .map(Holiday::toHolidayInfoVIew)
                .collect(Collectors.toList());
    }

    /**
     * 공휴일 수정
     * @param id
     * @param request
     * @result HolidayInfoView
     */
    public HolidayInfoView updateHolidayName (Long id, HolidayRequest request) {
        Holiday holiday = holidayRepository.findById(id).orElseThrow(() -> new ApiException(ApiStatus.IS_NOT_HOLIDAY));

        if (holiday.isCommon()) {
            throw new ApiException(ApiStatus.COMMON_HOLIDAY_NOT_UPDATE);
        }

        holiday.setName(request.getName());
        holiday.setMemo(request.getMemo());

        return holidayRepository.saveAndFlush(holiday).toHolidayInfoVIew();
    }

    /**
     * 공휴일 삭제
     * @param id
     */
    public void delete (Long id) {
        Holiday holiday = holidayRepository.findById(id).orElseThrow(() -> new ApiException(ApiStatus.IS_NOT_HOLIDAY));

        if (holiday.isCommon()) {
            throw new ApiException(ApiStatus.COMMON_HOLIDAY_NOT_DELETE);
        }
        holidayRepository.delete(holiday);
        holidayRepository.flush();
    }

}
