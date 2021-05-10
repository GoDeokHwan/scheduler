package com.example.scheduler.api.domain.user;

import com.example.scheduler.api.domain.UseStatus;
import com.example.scheduler.api.domain.user.model.UserRequest;
import com.example.scheduler.api.testsupport.ShedulerComponentTest;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@ShedulerComponentTest
@ActiveProfiles("memory")
@Transactional
@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Test
    public void 사용자_추가 () {
        String name = "테스터1";
        String loginId = "test-user01";
        String password = "12341234";
        String phoneNumber = "010-1234-1234";
        UserRequest userRequest = UserRequest.builder()
                .loginId(loginId)
                .name(name)
                .password(password)
                .phoneNumber(phoneNumber)
                .build();

        User user = userService.save(userRequest);

        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getPhoneNumber()).isEqualTo("01012341234");
    }
}