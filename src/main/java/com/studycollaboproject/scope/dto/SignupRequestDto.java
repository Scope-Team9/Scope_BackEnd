package com.studycollaboproject.scope.dto;

import com.studycollaboproject.scope.model.TechStack;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class SignupRequestDto {

    private String githubId;

    private String kakaoId;

    private String googleId;

    private String email;

    private String nickname;

    private List<TechStack> techStack;

}
