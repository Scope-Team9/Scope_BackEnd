package com.studycollaboproject.scope.dto;

import lombok.Getter;


@Getter
public class SignupResponseDto {

    private String githubId;
    private String kakaoId;
    private String googleId;

    public void setEmail(String githubId,String googleId, String kakaoId){
        this.githubId = githubId;
        this.googleId = googleId;
        this.kakaoId = kakaoId;
    }


}
