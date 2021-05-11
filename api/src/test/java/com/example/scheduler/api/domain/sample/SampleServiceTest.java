package com.example.scheduler.api.domain.sample;

import com.example.scheduler.api.support.api.ApiException;
import com.example.scheduler.api.support.api.ApiStatus;
import com.example.scheduler.api.testsupport.ShedulerComponentTest;
import com.example.scheduler.api.domain.sample.model.UserRequest;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@ShedulerComponentTest
@ActiveProfiles("memory")
@Transactional
@SpringBootTest
public class SampleServiceTest {

    @Autowired
    SampleService sampleService;

    @Test
    public void 저장() {
        String name = "테스트2";
        String phoneNumber = "테스트2";
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
        assertThat(sampleUsers.size()).isEqualTo(0);
    }

    @Test
    public void 다중_저장 () {
        List<UserRequest> userRequests = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            userRequests.add(UserRequest
                    .builder()
                    .name("테스트 " + i)
                    .phoneNumber("0101234000" + i)
                    .build());
        }
        sampleService.saveAll(userRequests);
        List<SampleUser> sampleUsers = sampleService.findAll();
        assertThat(sampleUsers.size()).isEqualTo(10);
    }

    @Test
    public void 이름_없이_생성시_오류() {
        String phoneNumber = "테스트2";
        SampleUser sampleUser = sampleService.save(UserRequest
                .builder()
                .phoneNumber(phoneNumber)
                .build());

        assertThatThrownBy(() -> {  sampleService.save(UserRequest
                .builder()
                .phoneNumber(phoneNumber)
                .build());
        }).isInstanceOf(ApiException.class)
        .hasMessage(ApiStatus.MISSING_REQUEST_PARAMETER.getMessage());
    }



}
