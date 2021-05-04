package com.example.scheduler.api.testsupport;

import com.example.scheduler.api.config.WebConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@TestRepositoryTest
@ComponentScan(basePackages = {"com.example.scheduler.api.domain"})
@Import({WebConfig.class})
public @interface ShedulerComponentTest {
}
