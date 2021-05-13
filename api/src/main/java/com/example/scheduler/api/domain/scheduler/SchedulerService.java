package com.example.scheduler.api.domain.scheduler;

import com.example.scheduler.api.domain.scheduler.model.SchedulerInfoRequest;
import com.example.scheduler.api.domain.scheduler.model.SchedulerInfoView;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SchedulerService {

    @Autowired
    private SchedulerRepository schedulerRepository;

    @Autowired
    private UserService userService;

    /**
     * 스케쥴러 저장
     * @param request
     * @return
     */
    public SchedulerInfoView save(SchedulerInfoRequest request) {
        if (!request.isDateValidation() || !request.isTimeValidation()) {
            throw new ApiException(ApiStatus.IS_DATETIME_ERROR);
        }

        User user = userService.getUser(request.getUserId());
        return Optional.of(schedulerRepository.save(Scheduler.builder()
                .user(user)
                .date(request.fullDate())
                .dateYear(request.getDateYear())
                .dateMonth(request.getDateMonth())
                .dateDay(request.getDateDay())
                .time(request.fullTime())
                .timeHour(request.getTimeHour())
                .timeMin(request.getTimeMin())
                .alarmType(request.getAlarmType())
                .isAlarm(request.isAlarm())
                .alarmTime(request.getAlarmTime())
                .isRepeat(request.isRepeat())
                .repeatType(request.getRepeatType())
                .isHoliday(request.isHoliday())
                .memo(request.getMemo())
                .build()))
                .map(Scheduler::toSchedulerInfoView)
                .get();
    }

    /**
     * 스케쥴러 조회
     * @param id
     * @param year
     * @param month
     * @return
     */
    @Transactional(readOnly = true)
    public List<SchedulerInfoView> findSchedulers(Long id, String year, String month) {
        if (!(StringUtils.isInteger(year) && StringUtils.isInteger(month) && DateUtils.isDate(Integer.valueOf(year), Integer.valueOf(month), 1))) {
            throw new ApiException(ApiStatus.IS_DATETIME_ERROR);
        }

        User user = userService.getUser(id);
        return user.getSchedulers().stream()
                .filter(f -> {
                    return f.getDateYear().equals(year) && f.getDateMonth().equals(month);
                })
                .map(Scheduler::toSchedulerInfoView)
                .collect(Collectors.toList());
    }

    /**
     * 스케쥴러 수정
     * @param id
     * @param request
     * @return
     */
    public SchedulerInfoView updateScheduler(Long id, SchedulerInfoRequest request) {
        if (!request.isDateValidation() || !request.isTimeValidation()) {
            throw new ApiException(ApiStatus.IS_DATETIME_ERROR);
        }

        Scheduler scheduler = schedulerRepository.findById(id).orElseThrow(() -> new ApiException(ApiStatus.IS_NOT_SCHEDULER));

        scheduler.setAlarm(request.isAlarm());
        scheduler.setAlarmTime(request.getAlarmTime());
        scheduler.setAlarmType(request.getAlarmType());
        scheduler.setDate(request.fullDate());
        scheduler.setDateYear(request.getDateYear());
        scheduler.setDateMonth(request.getDateMonth());
        scheduler.setDateDay(request.getDateDay());
        scheduler.setTime(request.fullTime());
        scheduler.setTimeHour(request.getTimeHour());
        scheduler.setTimeMin(request.getTimeMin());
        scheduler.setRepeat(request.isRepeat());
        scheduler.setRepeatType(request.getRepeatType());
        scheduler.setHoliday(request.isHoliday());
        scheduler.setMemo(request.getMemo());

        return schedulerRepository.saveAndFlush(scheduler).toSchedulerInfoView();
    }

    /**
     * 스케쥴러 삭제
     * @param id
     * @return
     */
    public void deleteScheduler(Long id) {
        Scheduler scheduler = schedulerRepository.findById(id).orElseThrow(() -> new ApiException(ApiStatus.IS_NOT_SCHEDULER));
        schedulerRepository.delete(scheduler);
    }

}
