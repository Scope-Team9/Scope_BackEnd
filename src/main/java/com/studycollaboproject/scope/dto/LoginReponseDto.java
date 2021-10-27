package com.studycollaboproject.scope.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Getter
public class LoginReponseDto {
    private String token;
    private String email;
    private String nickname;
}
