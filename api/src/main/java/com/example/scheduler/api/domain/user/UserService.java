package com.example.scheduler.api.domain.user;

import com.example.scheduler.api.domain.UseStatus;
import com.example.scheduler.api.domain.user.model.UserInfoView;
import com.example.scheduler.api.domain.user.model.UserRequest;
import com.example.scheduler.api.support.api.ApiException;
import com.example.scheduler.api.support.api.ApiStatus;
import com.example.scheduler.api.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(UserRequest userRequest) {
        return userRepository.save(User.builder()
                .name(userRequest.getName())
                .loginId(userRequest.getLoginId())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .phoneNumber(userRequest.getPhoneNumber())
                .status(UseStatus.ENABLE)
                .build());
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ApiException(ApiStatus.IS_NOT_USER));
    }

    public boolean validationUserId(String loginId) {
        return userRepository.findByLoginId(loginId).isPresent();
    }

    public UserInfoView login(String loginId, String password) {
       User user = userRepository.findByLoginId(loginId).orElseThrow(() -> new BadCredentialsException("유저가 없습니다."));

       if (!passwordEncoder.matches(password, user.getPassword())) {
           throw new BadCredentialsException(ApiStatus.AUTHENTICATION.getMessage());
       }

       if (!user.isEnable()) {
           throw new BadCredentialsException("미승인된 유저입니다.");
       }

       user.setTokenKey(StringUtils.createBase64EncodedUUID());

       return user.toUserinfoView();
    }

    public void logout(Long id) {
        User user = getUser(id);
        user.clearTokenKey();
    }

    public void validTokenKey(Long id, String token) {
        User user = userRepository.findById(id).orElseThrow(() -> new BadCredentialsException("유저가 없습니다."));
        if (org.apache.commons.lang3.StringUtils.isNotBlank(user.getTokenKey()) && !user.getTokenKey().equals(token)) {
            throw new BadCredentialsException("잘못된 사용자 접근입니다.");
        }
    }
}
