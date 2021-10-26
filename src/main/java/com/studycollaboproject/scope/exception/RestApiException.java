package com.studycollaboproject.scope.exception;

public class RestApiException extends RuntimeException{
    public RestApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
