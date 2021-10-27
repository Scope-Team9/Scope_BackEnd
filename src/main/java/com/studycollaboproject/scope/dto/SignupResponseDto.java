package com.studycollaboproject.scope.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;


@Getter
public class SignupResponseDto {

    @Schema(description = "Github ID")
    private String githubId;
    @Schema(description = "Kakao ID")
    private String kakaoId;
    @Schema(description = "Google ID")
    private String googleId;

    public void setEmail(String githubId,String googleId, String kakaoId){
        this.githubId = githubId;
        this.googleId = googleId;
        this.kakaoId = kakaoId;
    }


}
