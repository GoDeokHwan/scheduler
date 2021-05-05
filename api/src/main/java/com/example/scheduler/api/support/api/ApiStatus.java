package com.example.scheduler.api.support;

import lombok.Getter;

@Getter
public enum ApiStatus implements ApiStatusResponsible {
    SUSSESS(0, "성공", false),
    ERROR(-500, "관리자에게 문의하세요.", false);

    private Integer code;
    private String message;
    private boolean isShort;

    ApiStatus(Integer code, String message, boolean isShort) {
        this.code = code;
        this.message = message;
        this.isShort = isShort;

    }
}
