package com.studycollaboproject.scope.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto {
    private String status;
    private String msg;
    private Object data;


}