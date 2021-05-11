package com.example.scheduler.api.domain.user;

import com.example.scheduler.api.domain.UseStatus;
import com.example.scheduler.api.domain.user.model.UserRequest;
import com.example.scheduler.api.support.api.ApiException;
import com.example.scheduler.api.support.api.ApiStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(UserRequest userRequest) {
        return userRepository.save(User.builder()
                .name(userRequest.getName())
                .loginId(userRequest.getLoginId())
                .password(userRequest.getPassword())
                .phoneNumber(userRequest.getPhoneNumber())
                .status(UseStatus.ENABLE)
                .build());
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ApiException(ApiStatus.IS_NOT_USER));
    }

}
