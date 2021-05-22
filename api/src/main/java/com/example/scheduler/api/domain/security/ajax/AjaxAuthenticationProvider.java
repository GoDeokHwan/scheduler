package com.example.scheduler.api.domain.security.ajax;

import com.example.scheduler.api.domain.security.model.AuthorizationUserDetail;
import com.example.scheduler.api.domain.user.UserService;
import com.example.scheduler.api.domain.user.model.UserInfoView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Slf4j
@Component
@Transactional
public class AjaxAuthenticationProvider implements AuthenticationProvider {

    @Value("${scheduler.jwt.expiration}")
    private Long expiration;

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.notNull(authentication, "No authentication data provided");

        String loginId = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        log.info("[로그인 요청] loginId : {}", loginId);
        UserInfoView userInfoView = userService.login(loginId, password);

        AuthorizationUserDetail principal = convertAuthorizationUserDetail(userInfoView);

        return new UsernamePasswordAuthenticationToken(principal, null);
    }

    public AuthorizationUserDetail convertAuthorizationUserDetail(UserInfoView userInfoView) {
        AuthorizationUserDetail principal = new AuthorizationUserDetail();
        principal.setId(userInfoView.getId());
        principal.setLoginId(userInfoView.getLoginId());
        principal.setName(userInfoView.getLoginId());
        principal.setPhoneNumber(userInfoView.getPhoneNumber());

        return principal;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
