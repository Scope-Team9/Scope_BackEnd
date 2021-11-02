package com.studycollaboproject.scope.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SnsInfoDto {

    @Schema(description = "이메일")
    private String email;
    @Schema(description = "SNS ID")
    private String id;
}
