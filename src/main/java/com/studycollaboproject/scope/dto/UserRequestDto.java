package com.studycollaboproject.scope.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class UserRequestDto {
    @Schema(description = "닉네임")
    private String nickname;
    @Schema(description = "유저 기술스택 리스트")
    private List<String> userTechStack;
}
