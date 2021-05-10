package com.example.scheduler.api.domain.user.model;

import com.example.scheduler.api.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserRequest {

    @NotNull
    private String loginId;
    @NotNull
    private String password;
    @NotNull
    private String name;

    private String phoneNumber;

    @Builder
    public UserRequest(@NotNull String loginId, @NotNull String password, @NotNull String name, String phoneNumber) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}
