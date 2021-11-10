package com.studycollaboproject.scope.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus {
    USER_STATUS_USER("user"),
    USER_STATUS_APPLICANT("applicant"),
    USER_STATUS_MEMBER("member"),
    USER_STATUS_ANONYMOUS("anonymous"),
    USER_STATUS_TEAM_STARTER("starter");

    private String userStatus;
}
