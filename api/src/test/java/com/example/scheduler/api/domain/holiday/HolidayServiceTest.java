package com.example.scheduler.api.domain.holiday;

import com.example.scheduler.api.testsupport.ShedulerComponentTest;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@ShedulerComponentTest
@ActiveProfiles("memory")
@Transactional
@SpringBootTest
class HolidayServiceTest {

}