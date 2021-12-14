package com.studycollaboproject.scope.controller;

import com.studycollaboproject.scope.dto.ResponseDto;
import com.studycollaboproject.scope.dto.UserInfoResponseDto;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.ForbiddenException;
import com.studycollaboproject.scope.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
public class UserInfoRestController {
    @Operation(summary = "user 기본정보 전달")
    @GetMapping("/api/myuser")
    public ResponseEntity<Object> userInfo(@Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails.getUser() == null) {
            throw new ForbiddenException(ErrorCode.NO_AUTHORIZATION_ERROR);
        } else {
            UserInfoResponseDto userInfoResponseDto = new UserInfoResponseDto(userDetails.getUser().getNickname(), userDetails.getUser().getId(), userDetails.getUser().getEmail(), userDetails.getUser().getUserPropensityType());
            return new ResponseEntity<>(
                    new ResponseDto("사용자 정보가 성공적으로 로드되었습니다.", userInfoResponseDto),
                    HttpStatus.OK
            );
        }
    }
}
