package org.jsp.user_management.exceptionclasses;

import lombok.Builder;

@Builder
public class InvalidCredentialException extends RuntimeException {
    private String message;

    @Override
    public String getMessage() {
        return message;
    }
}



