package com.studycollaboproject.scope.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.studycollaboproject.scope.dto.SnsInfoDto;
import com.studycollaboproject.scope.service.GithubUserService;
import com.studycollaboproject.scope.service.KakaoUserService;
import com.studycollaboproject.scope.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
@Tag(name = "SNS Login Controller", description = "소셜 로그인")
public class SnsLoginRestController {

    private final KakaoUserService kakaoUserService;
    private final UserService userService;
    private final GithubUserService githubUserService;

    @Operation(summary = "카카오 로그인")
    @GetMapping("/api/login/kakao")
    public ResponseEntity<Object> kakaoLogin(@Parameter(description = "코드 값", in = ParameterIn.QUERY) @RequestParam String code) throws JsonProcessingException {
        log.info("[{}], 카카오 로그인, GET, /api/login/kakao, code={}", MDC.get("UUID"), code);
        SnsInfoDto snsInfoDto = kakaoUserService.kakaoLogin(code);
        return new ResponseEntity<>(
                userService.SignupEmailCheck(snsInfoDto.getEmail(), snsInfoDto.getId(), "K-"),
                HttpStatus.OK
        );
    }

    @Operation(summary = "Github 로그인")
    @GetMapping("/api/login/github")
    public ResponseEntity<Object> githubLogin(@Parameter(description = "코드 값", in = ParameterIn.QUERY) @RequestParam String code) throws JsonProcessingException {
        log.info("[{}], Github 로그인, GET, /api/login/github, code={}", MDC.get("UUID"), code);
        SnsInfoDto snsInfoDto = githubUserService.githubLogin(code);
        return new ResponseEntity<>(
                userService.SignupEmailCheck(snsInfoDto.getEmail(), snsInfoDto.getId(), "G-"),
                HttpStatus.OK
        );
    }
}
