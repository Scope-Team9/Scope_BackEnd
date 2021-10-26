package com.studycollaboproject.scope.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseDto {
    private String status;
    private String msg;
    private Object data;
}