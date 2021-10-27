package com.studycollaboproject.scope.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginReponseDto {
    @Schema(description = "토큰 값")
    private String token;
    @Schema(description = "Email")
    private String email;
    @Schema(description = "닉네임")
    private String nickname;
}
