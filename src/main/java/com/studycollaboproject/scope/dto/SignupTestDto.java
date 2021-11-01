package com.studycollaboproject.scope.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SignupTestDto {
    @Schema(description = "SNS ID")
    private String snsId;

    @Schema(description = "Email")
    private String email;

    @Schema(description = "닉네임")
    private String nickname;

    @Schema(description = "유저 성향 테스트 결과")
    private String memberPropensityType;

    @Schema(description = "유저 선호 성향 테스트 결과")
    private String userPropensityType;


}
