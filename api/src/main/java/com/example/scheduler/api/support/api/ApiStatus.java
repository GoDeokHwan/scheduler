package com.example.scheduler.api.support.api;

import lombok.Getter;

@Getter
public enum ApiStatus implements ApiStatusResponsible {
    SUCCESS(0, "성공", true),
    ERROR(-500, "관리자에게 문의하세요.", false),
    REQUEST_METHOD_NOT_SUPPORTED(-405, "지원하지 않는 요청입니다.", false),
    BAD_REQUEST_ERROR(-400, "파라미터 형식이 잘못되었습니다.", false),
    MISSING_REQUEST_PARAMETER(-411, "필수 정보가 잘못되었습니다.", false);

    private Integer code;
    private String message;
    private boolean isShort;

    ApiStatus(Integer code, String message, boolean isShort) {
        this.code = code;
        this.message = message;
        this.isShort = isShort;

    }
}
