package com.studycollaboproject.scope.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class SignupRequestDto {

    @Schema(description = "SNS ID")
    private String snsId;

    @Schema(description = "Email")
    private String email;

    @Schema(description = "닉네임")
    private String nickname;

    @Schema(description = "기술스택 리스트")
    private List<String> techStack;

    @Schema(description = "유저 성향 테스트 결과")
    List<String> userPropensityType;

    @Schema(description = "유저 선호 성향 테스트 결과")
    List<String> memberPropensityType;


}
