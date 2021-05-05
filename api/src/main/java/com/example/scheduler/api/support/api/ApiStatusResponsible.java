package com.example.scheduler.api.support;

public interface ApiStatusResponsible {
    Integer getCode();
    String getMessage();
    boolean isShort();

    default boolean isFailed() {
        return false;
    }

    default boolean isSuccess() {
        return true;
    }
}
