package com.example.scheduler.api.domain.security.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String loginId;

    private String password;
}
