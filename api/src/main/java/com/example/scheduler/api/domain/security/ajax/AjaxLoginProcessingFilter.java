package com.example.scheduler.api.domain.security.ajax;

import com.example.scheduler.api.config.securiy.exception.AuthMethodNotSupportedException;
import com.example.scheduler.api.domain.security.model.LoginRequest;
import com.example.scheduler.api.util.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.scheduler.api.util.JsonHelper.fromJson;


@Slf4j
public class AjaxLoginProcessingFilter  extends AbstractAuthenticationProcessingFilter {

    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {

        if (!HttpMethod.POST.name().equals(request.getMethod()) || WebUtil.isNotAjax(request)) {
            log.info("method : {}, ajax : {}", request.getMethod(), WebUtil.isNotAjax(request));
            throw new AuthMethodNotSupportedException("Authentication method not supported");
        }

        BufferedReader reader = request.getReader();
        LoginRequest loginRequest = fromJson(reader.lines().collect(Collectors.joining()), LoginRequest.class);

        if (StringUtils.isBlank(loginRequest.getLoginId()) || StringUtils.isBlank(loginRequest.getPassword())) {
            throw new AuthenticationServiceException("Username or Password not provided");
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getLoginId(), loginRequest.getPassword());

        return this.getAuthenticationManager().authenticate(token);
    }

    public AjaxLoginProcessingFilter(String defaultProcessUrl, AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler) {
        super(defaultProcessUrl);
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        successHandler.onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        failureHandler.onAuthenticationFailure(request, response, failed);
    }
}
