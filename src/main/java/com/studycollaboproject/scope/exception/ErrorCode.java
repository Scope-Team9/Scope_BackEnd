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
    NO_AUTHORIZATION_ERROR("접근 권한이 없습니다."),
    NO_TOKEN_ERROR("토큰이 존재하지 않습니다."),
    NO_MATCH_ITEM_ERROR("일치하는 값이 없습니다."),
    ALREADY_APPLY_POST_ERROR("이미 지원한 게시글 입니다.");

    private String message;
}
