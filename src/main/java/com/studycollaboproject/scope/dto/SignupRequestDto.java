package com.studycollaboproject.scope.dto;

import com.studycollaboproject.scope.model.TechStack;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class SignupRequestDto {

    @Schema(description = "Github ID")
    private String githubId;

    @Schema(description = "Kakao ID")
    private String kakaoId;

    @Schema(description = "Google ID")
    private String googleId;

    @Schema(description = "Email")
    private String email;

    @Schema(description = "닉네임")
    private String nickname;

    @Schema(description = "기술스택 리스트")
    private List<String> techStack;

}
