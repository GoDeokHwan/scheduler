package com.example.scheduler.api.domain.sample.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserRequest {

    private String name;

    private String phoneNumber;
}
