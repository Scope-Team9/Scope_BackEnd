package com.studycollaboproject.scope.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.studycollaboproject.scope.dto.ResponseDto;
import com.studycollaboproject.scope.dto.SnsInfoDto;
import com.studycollaboproject.scope.service.GithubUserService;
import com.studycollaboproject.scope.service.KakaoUserService;
import com.studycollaboproject.scope.service.NaverUserService;
import com.studycollaboproject.scope.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@Slf4j
@Tag(name = "SNS Login Controller", description = "소셜 로그인")
public class SnsLoginRestController {

    private final KakaoUserService kakaoUserService;
    private final UserService userService;
    private final GithubUserService githubUserService;
    private final NaverUserService naverUserService;

    @Operation(summary = "카카오 로그인")
    @GetMapping("/api/login/kakao")
    public ResponseDto kakaoLogin(@Parameter(description = "코드 값", in = ParameterIn.QUERY) @RequestParam String code) throws JsonProcessingException {
        log.info("GET, [{}], /api/login/kakao, code={}", MDC.get("UUID"), code);
        SnsInfoDto snsInfoDto = kakaoUserService.kakaoLogin(code);
        return userService.SignupEmailCheck(snsInfoDto.getEmail(), snsInfoDto.getId());
    }

    @Operation(summary = "Github 로그인")
    @GetMapping("/api/login/github")
    public ResponseDto githubLogin(@Parameter(description = "코드 값", in = ParameterIn.QUERY) @RequestParam String code) throws JsonProcessingException {
        log.info("GET, [{}], /api/login/github, code={}", MDC.get("UUID"), code);
        SnsInfoDto snsInfoDto = githubUserService.githubLogin(code);
        return userService.SignupEmailCheck(snsInfoDto.getEmail(),snsInfoDto.getId());
    }

    @Operation(summary = "상태 토큰값 가져오기")
    @GetMapping("/api/login/status-token")
    public ResponseDto getStatusToken() {
        log.info("GET, [{}], /api/login/status-token", MDC.get("UUID"));
        SecureRandom random = new SecureRandom();
        String token = new BigInteger(130, random).toString(32);
        Map<String, String> statusToken = new HashMap<>();
        statusToken.put("statusToken", token);

        return new ResponseDto("200", "상태토큰 발급 완료", statusToken);
    }

    @Operation(summary = "네이버 로그인")
    @GetMapping("/api/login/naver")
    public ResponseDto naverLogin(@Parameter(description = "코드 값", in = ParameterIn.QUERY) @RequestParam String code,
                                  @Parameter(description = "상태 토큰 값", in = ParameterIn.QUERY) @RequestParam String statusToken) throws JsonProcessingException {
        log.info("GET, [{}], /api/login/naver, code={}", MDC.get("UUID"), code);
        SnsInfoDto snsInfoDto = naverUserService.naverLogin(code, statusToken);
        return userService.SignupEmailCheck(snsInfoDto.getEmail(), snsInfoDto.getId());
    }
}
