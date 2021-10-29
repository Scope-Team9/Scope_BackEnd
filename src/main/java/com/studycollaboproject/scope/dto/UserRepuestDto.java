package com.studycollaboproject.scope.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class UserRepuestDto {
    private String nickname;
    private String email;
    private List<String> userTechStack;
}
