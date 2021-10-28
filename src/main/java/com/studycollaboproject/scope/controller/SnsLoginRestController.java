package com.studycollaboproject.scope.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.studycollaboproject.scope.dto.ResponseDto;
import com.studycollaboproject.scope.dto.SnsInfoDto;
import com.studycollaboproject.scope.service.GithubUserService;
import com.studycollaboproject.scope.service.NaverUserService;
import com.studycollaboproject.scope.service.KakaoUserService;
import com.studycollaboproject.scope.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class SnsLoginRestController {

    private final KakaoUserService kakaoUserService;
    private final UserService userService;
    private final GithubUserService githubUserService;
    private final NaverUserService naverUserService;


    @PostMapping("/api/login/kakao")
    public ResponseDto kakaoLogin(@ModelAttribute("code") String code) throws JsonProcessingException {
        SnsInfoDto snsInfoDto = kakaoUserService.kakaoLogin(code);
        return userService.emailCheckByEmail(snsInfoDto);
    }

    @PostMapping("/api/login/github")
    public ResponseDto githubLogin(@ModelAttribute("code") String code) throws JsonProcessingException {
        SnsInfoDto snsInfoDto = githubUserService.githubLogin(code);
        return userService.emailCheckByEmail(snsInfoDto);
    }

    @GetMapping("/api/login/status-token")
    public ResponseDto getStatusToken(){
        SecureRandom random = new SecureRandom();
        String token = new BigInteger(130, random).toString(32);
        Map<String ,String> statusToken = new HashMap<>();
        statusToken.put("statusToken",token);

        return new ResponseDto("200","상태토큰 발급 완료",statusToken);

    }

    @PostMapping("/api/login/naver")
    public ResponseDto naverLogin(@ModelAttribute("code") String code,@ModelAttribute("statusToken") String statusToken) throws JsonProcessingException {
        SnsInfoDto snsInfoDto = naverUserService.naverLogin(code,statusToken);
        return userService.emailCheckByEmail(snsInfoDto);
    }
}
