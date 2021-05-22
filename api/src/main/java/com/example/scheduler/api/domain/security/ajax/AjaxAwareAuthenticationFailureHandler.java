package com.example.scheduler.api.domain.security.ajax;

import com.example.scheduler.api.config.securiy.exception.AlreadyLoginException;
import com.example.scheduler.api.config.securiy.exception.AuthMethodNotSupportedException;
import com.google.common.collect.Lists;
import com.example.scheduler.api.config.securiy.token.JwtExpiredTokenException;
import com.example.scheduler.api.support.api.ApiResult;
import com.example.scheduler.api.support.api.ApiStatus;
import com.example.scheduler.api.util.JsonHelper;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

@Slf4j
@Component
public class AjaxAwareAuthenticationFailureHandler implements AuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException e) throws IOException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(Charset.forName("UTF-8").displayName());

        log.info("{}", e.getMessage());
        if (e instanceof BadCredentialsException) {
            response.getWriter().write(JsonHelper.toJson(ApiResult.of(ApiStatus.AUTHENTICATION)));
        } else if (e instanceof JwtExpiredTokenException) {
            response.getWriter().write(JsonHelper.toJson(ApiResult.of(ApiStatus.JWT_TOKEN_EXPIRED)));
        } else if (e instanceof AuthMethodNotSupportedException) {
            response.getWriter().write(JsonHelper.toJson(ApiResult.of(ApiStatus.AUTHENTICATION)));
        } else if (e instanceof InsufficientAuthenticationException) {
            ApiStatus apiStatus = ApiStatus.valueOf(e.getMessage());
            response.getWriter().write(JsonHelper.toJson(ApiResult.of(apiStatus)));
        } else if (e instanceof DisabledException) {
            response.getWriter().write(JsonHelper.toJson(ApiResult.of(ApiStatus.
                    DUPLICATION_ACCESS_EXCEPTION, Lists.newArrayList(Pair.of("tokenKey", e.getMessage())))));
        } else if (e instanceof AlreadyLoginException) {
            response.getWriter().write(JsonHelper.toJson(ApiResult.of(ApiStatus.DUPLICATION_LOGIN_EXCEPTION)));
        } else {
            response.getWriter().write(JsonHelper.toJson(ApiResult.of(ApiStatus.AUTHENTICATION_FAILED)));
        }
    }

}
