package com.studycollaboproject.scope.exception;

public class NoAuthException extends RuntimeException{
    public NoAuthException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
