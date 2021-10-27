package com.studycollaboproject.scope.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.studycollaboproject.scope.dto.ResponseDto;
import com.studycollaboproject.scope.dto.SnsInfoDto;
import com.studycollaboproject.scope.service.GithubUserService;
import com.studycollaboproject.scope.service.GoogleUserService;
import com.studycollaboproject.scope.service.KakaoUserService;
import com.studycollaboproject.scope.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SnsLoginRestController {

    private final KakaoUserService kakaoUserService;
    private final UserService userService;
    private final GithubUserService githubUserService;
    private final GoogleUserService googleUserService;


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

    @PostMapping("/api/login/google")
    public ResponseDto googleLogin(@ModelAttribute("code") String code)  {
        SnsInfoDto snsInfoDto = googleUserService.googleLogin(code);
        return userService.emailCheckByEmail(snsInfoDto);
    }
}
