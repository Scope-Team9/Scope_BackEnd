package com.studycollaboproject.scope.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NO_USER_ERROR("해당 유저를 찾을 수 없습니다."),
    NO_POST_ERROR("해당 프로젝트를 찾을 수 없습니다."),
    NO_APPLICANT_ERROR("해당 지원 정보를 찾을 수 없습니다."),
    NO_AUTHENTICATION_ERROR("로그인 사용자만 사용할 수 있습니다."),
    NO_AUTHORIZATION_ERROR("접근 권한이 없습니다."),
    NO_TOKEN_ERROR("토큰이 존재하지 않습니다."),
    NO_MATCH_ITEM_ERROR("일치하는 값이 없습니다."),
    ALREADY_APPLY_POST_ERROR("이미 지원한 프로젝트 입니다."),
    ALREADY_EMAIL_ERROR("중복된 이메일이 존재합니다."),
    ALREADY_NICKNAME_ERROR("중복된 닉네임이 존재합니다."),
    IMAGE_SAVE_ERROR("이미지 저장에 실패하였습니다."),
    INVALID_INPUT_ERROR("입력 값이 잘못되었습니다."),
    NO_BOOKMARK_MY_POST_ERROR("자신의 게시물은 북마크할 수 없습니다."),
    NO_TEAM_ERROR("프로젝트의 팀 구성원이 아닙니다."),
    ALREADY_ASSESSMENT_ERROR("이미 평가를 완료했습니다.");

    private String message;
}
