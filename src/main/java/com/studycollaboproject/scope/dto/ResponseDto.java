package com.studycollaboproject.scope.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseDto {
    @Schema(description = "request 결과", example = "200")
    private String status;
    @Schema(description = "메시지", example = "message")
    private String msg;
    @Schema(description = "데이터")
    private Object data;


}