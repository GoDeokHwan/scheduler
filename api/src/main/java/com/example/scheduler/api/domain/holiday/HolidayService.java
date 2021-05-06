package com.example.scheduler.api.domain.holiday;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HolidayService {

    @Autowired
    private HolidayRepository holidayRepository;


}
