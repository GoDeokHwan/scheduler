package com.example.scheduler.api.domain.sample;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SampleServiceTest {

    @Autowired
    SampleService sampleService;

    @Test
    public void 저장() {
        SampleUser sampleUser = new SampleUser();
        sampleUser.setName("테스트1");
        sampleUser.setPhoneNumber("12341234");

        sampleService.save(sampleUser);
    }
}
