package com.example.scheduler.api.domain.security.jwt;

import com.example.scheduler.api.config.securiy.JwtProperties;
import com.example.scheduler.api.config.securiy.token.RawAccessJwtToken;
import com.example.scheduler.api.config.securiy.token.TokenExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenAuthenticationProcessingFilter  extends AbstractAuthenticationProcessingFilter {

    private final AuthenticationFailureHandler failureHandler;
    private final JwtProperties jwtProperties;

    @Autowired
    public JwtTokenAuthenticationProcessingFilter(AuthenticationFailureHandler failureHandler,
                                                  JwtProperties jwtProperties, RequestMatcher matcher) {
        super(matcher);
        this.failureHandler = failureHandler;
        this.jwtProperties = jwtProperties;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String tokenPayload = request.getHeader(jwtProperties.getHeader());
        RawAccessJwtToken token = new RawAccessJwtToken(TokenExtractor.extract(tokenPayload));
        return getAuthenticationManager().authenticate(new JwtAuthenticationToken(token));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        failureHandler.onAuthenticationFailure(request, response, failed);
    }
}
