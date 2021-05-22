package com.example.scheduler.api.config.securiy.exception;

import org.springframework.security.core.AuthenticationException;

public class AlreadyLoginException  extends AuthenticationException {
    public AlreadyLoginException(String msg, Throwable t) {
        super(msg, t);
    }

    public AlreadyLoginException(String msg) {
        super(msg);
    }
}
