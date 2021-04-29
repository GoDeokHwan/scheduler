package com.example.scheduler.api.domain.sample;

import com.example.scheduler.api.domain.sample.model.UserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SampleServiceTest {

    @Autowired
    SampleService sampleService;

    @Test
    public void 저장() {
        String name = "테스트1";
        String phoneNumber = "테스트1";
        SampleUser sampleUser = sampleService.save(UserRequest
                .builder()
                .name(name)
                .phoneNumber(phoneNumber)
                .build());

        assertThat(sampleUser.getName()).isEqualTo(name);
    }

    @Test
    public void 전체_조회() {
        List<SampleUser> sampleUsers = sampleService.findAll();
    }
}
