package com.example.scheduler.api.support.api;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum ApiStatus implements ApiStatusResponsible {
    SUCCESS(0, "성공", true),
    ERROR(-500, "관리자에게 문의하세요.", false),
    REQUEST_METHOD_NOT_SUPPORTED(-405, "지원하지 않는 요청입니다.", false),
    BAD_REQUEST_ERROR(-400, "파라미터 형식이 잘못되었습니다.", false),
    MISSING_REQUEST_PARAMETER(-411, "필수 정보가 잘못되었습니다.", false),

    // 사용자
    IS_NOT_USER(-1000, "사용자 정보가 없습니다.", false),

    // 스케쥴러
    IS_NOT_SCHEDULER(-2000, "스케쥴러를 찾을 수 없습니다.", true),
    IS_DATETIME_ERROR(-2001, "날짜,시간 표현식이 올바르지 않습니다.", true)
    ;

    private Integer code;
    private String message;
    private boolean isShort;

    ApiStatus(Integer code, String message, boolean isShort) {
        this.code = code;
        this.message = message;
        this.isShort = isShort;

    }
}
