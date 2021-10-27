package com.studycollaboproject.scope.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.studycollaboproject.scope.dto.ResponseDto;
import com.studycollaboproject.scope.service.GithubUserService;
import com.studycollaboproject.scope.service.GoogleUserService;
import com.studycollaboproject.scope.service.KakaoUserService;
import com.studycollaboproject.scope.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SnsLonginRestController {

    private final KakaoUserService kakaoUserService;
    private final UserService userService;
    private final GithubUserService githubUserService;
    private final GoogleUserService googleUserService;


    @PostMapping("/api/login/kakao")
    public ResponseDto kakaoLogin(@RequestBody String code) throws JsonProcessingException {
        String kakaoEmail = kakaoUserService.kakaoLogin(code);
        return userService.emailCheckByEmail(kakaoEmail,"kakao");
    }

    @PostMapping("/api/login/github")
    public ResponseDto githubLogin(@RequestBody String code)  {
        String githubEmail = githubUserService.githubLogin(code);
        return userService.emailCheckByEmail(githubEmail,"github");
    }

    @PostMapping("/api/login/google")
    public ResponseDto googleLogin(@RequestBody String code)  {
        String googleEmail = googleUserService.googleLogin(code);
        return userService.emailCheckByEmail(googleEmail,"google");
    }
}
