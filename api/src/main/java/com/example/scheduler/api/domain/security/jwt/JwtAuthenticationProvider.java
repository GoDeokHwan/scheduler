package com.example.scheduler.api.domain.security.jwt;

import com.example.scheduler.api.config.securiy.JwtProperties;
import com.example.scheduler.api.config.securiy.token.RawAccessJwtToken;
import com.example.scheduler.api.domain.security.model.AuthorizationUserDetail;
import com.example.scheduler.api.domain.user.UserService;
import com.example.scheduler.api.util.JsonHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private JwtProperties properties;

    @Autowired
    private UserService userService;

    @SuppressWarnings("unchecked")
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        RawAccessJwtToken rawAccessJwtToken = (RawAccessJwtToken) authentication.getCredentials();

        Jws<Claims> jwsClaims = rawAccessJwtToken.parseClaims(properties.getSecret());
        Claims claims = jwsClaims.getBody();
        AuthorizationUserDetail authorizationUserDetail = JsonHelper.fromJson(claims.get("details").toString(), AuthorizationUserDetail.class);

        userService.validTokenKey(authorizationUserDetail.getId(), authorizationUserDetail.getTokenKey());

        return new JwtAuthenticationToken(authorizationUserDetail, null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
