package com.example.scheduler.api.testsupport;

import com.example.scheduler.api.testsupport.H2JpaDataConfig;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ContextConfiguration(classes = {H2JpaDataConfig.class}, initializers = { ConfigFileApplicationContextInitializer.class})
@TestPropertySource(properties = {"spring.config.location=classpath:application.yml"})
public @interface TestRepositoryTest {
}
