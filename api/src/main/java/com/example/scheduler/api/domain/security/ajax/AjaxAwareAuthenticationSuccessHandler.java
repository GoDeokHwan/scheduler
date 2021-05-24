package com.example.scheduler.api.domain.security.ajax;

import com.example.scheduler.api.config.securiy.token.JwtToken;
import com.example.scheduler.api.config.securiy.token.JwtTokenFactory;
import com.example.scheduler.api.domain.security.model.AuthorizationUserDetail;
import com.example.scheduler.api.util.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Component
public class AjaxAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtTokenFactory tokenFactory;

    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        AuthorizationUserDetail authorizationUserDetail = (AuthorizationUserDetail)authentication.getPrincipal();
        JwtToken accessToken = tokenFactory.createAccessJwtToken(authorizationUserDetail);
        JwtToken refreshToken = tokenFactory.createRefreshToken(authorizationUserDetail);

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", accessToken.getToken());
        tokenMap.put("refreshToken", refreshToken.getToken());
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(Charset.forName("UTF-8").displayName());
        response.getWriter().write(JsonHelper.toJson(tokenMap));

        clearAuthenticationAttributes(request);
    }
}
