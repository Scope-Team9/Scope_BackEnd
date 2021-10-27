package com.studycollaboproject.scope.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NO_USER_ERROR("해당 유저를 찾을 수 없습니다."),
    NO_POST_ERROR("해당 게시글을 찾을 수 없습니다."),
    NO_APPLICANT_ERROR("해당 지원 정보를 찾을 수 없습니다."),
    NO_AUTHENTICATION_ERROR("로그인 사용자만 사용할 수 있습니다."),
    NO_AUTHORIZATION_ERROR("접근 권한이 없습니다.");

    private String message;
}
