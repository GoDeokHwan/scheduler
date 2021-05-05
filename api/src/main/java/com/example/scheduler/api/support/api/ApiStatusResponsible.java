package com.example.scheduler.api.support.api;

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
