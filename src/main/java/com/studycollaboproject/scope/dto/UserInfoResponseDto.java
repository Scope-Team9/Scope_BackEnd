package com.studycollaboproject.scope.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserInfoResponseDto {
    @Schema(description = "닉네임")
    private String nickname;
    @Schema(description = "아이디")
    private Long userId;
    @Schema(description = "이메일")
    private String email;
    @Schema(description = "유저 성향")
    private String userPropensityType;
}
