package com.example.scheduler.api.domain.user.model;

import com.example.scheduler.api.domain.UseStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoView {

    private Long id;

    private String loginId;

    private String name;

    private String phoneNumber;

    private UseStatus status;

    private String tokenKey;
}
