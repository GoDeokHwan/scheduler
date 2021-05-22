package com.example.scheduler.api.domain.security.ajax;
import com.example.scheduler.api.domain.user.UserService;
import com.example.scheduler.api.util.JsonHelper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AjaxLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private UserService userService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        BufferedReader reader = request.getReader();
        JsonObject object = JsonHelper.fromJson(reader.lines().collect(Collectors.joining()), JsonObject.class);
        Optional.ofNullable(object.get("id"))
                .filter(id -> !id.isJsonNull())
                .map(JsonElement::getAsLong)
                .ifPresent(userId -> userService.logout(userId));

        response.addHeader("Access-Control-Allow-Origin", "*");
    }
}
