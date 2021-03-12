package com.sparkystudios.traklibrary.authentication.server.exception;

import org.springframework.security.core.AuthenticationException;

public class AuthenticationMethodNotSupportedException extends AuthenticationException {

    public AuthenticationMethodNotSupportedException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public AuthenticationMethodNotSupportedException(String msg) {
        super(msg);
    }
}
