package com.example.scheduler.api.domain.scheduler;

import com.example.scheduler.api.domain.scheduler.model.SchedulerInfoRequest;
import com.example.scheduler.api.domain.user.User;
import com.example.scheduler.api.domain.user.UserService;
import com.example.scheduler.api.support.api.ApiException;
import com.example.scheduler.api.support.api.ApiStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SchedulerService {

    @Autowired
    private SchedulerRepository schedulerRepository;

    @Autowired
    private UserService userService;

    public Scheduler save(SchedulerInfoRequest request) {
        if (!request.isDateValidation() || !request.isTimeValidation()) {
            throw new ApiException(ApiStatus.IS_DATETIME_ERROR);
        }

        User user = userService.getUser(request.getUserId());
        return schedulerRepository.save(Scheduler.builder()
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
                .build());
    }

//    public List<SchedulerInfoView> findScheduler(Long id, String year, String month) {
//        Optional<User> user = Optional.ofNullable(userRepository.findById(id)).orElseThrow(() -> new ApiException(ApiStatus.IS_NOT_USER));
//        List<Scheduler> schedulers = user.get().getSchedulers();
//        if (schedulers.size() > 0) {
//
//        } else {
//            return null;
//        }
//    }
}
