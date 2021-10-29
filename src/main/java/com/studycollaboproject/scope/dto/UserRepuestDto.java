package com.studycollaboproject.scope.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class UserRepuestDto {
    @Schema(description = "닉네임")
    private String nickname;
    @Schema(description = "이메일")
    private String email;
    @Schema(description = "유저 기술스택 리스트")
    private List<String> userTechStack;
}
