package com.example.scheduler.api.domain.security.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorizationUserDetail {

    private Long id;

    private String loginId;

    private String name;

    private String phoneNumber;

}
