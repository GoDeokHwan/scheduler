package com.example.scheduler.api.config.securiy.exception;

import org.springframework.security.authentication.AuthenticationServiceException;
public class AuthMethodNotSupportedException extends AuthenticationServiceException {
    private static final long serialVersionUID = -3678324867061537292L;

    public AuthMethodNotSupportedException(String msg) {
        super(msg);
    }
}
