    package com.studycollaboproject.scope.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ServerRestController {
    private final Environment env;

    @Operation(summary = "서버 Blue/Green 상태 확인")
    @GetMapping("/profile")
    public String getProfile() {
        // jar파일 실행 시 설정한 실행 값을 배열로 저장(0번째 리스트에 저장됨)
        List<String> profile = Arrays.asList(env.getActiveProfiles());
        // real1과 real2를 리스트로 저장
        List<String> realProfiles = Arrays.asList("real1", "real2");
        // 만약 default가 아니면 현재 실행중 값 반환
        String defaultProfile = profile.isEmpty() ? "default" : profile.get(0);

        // profile 반환
        return profile.stream()
                .filter(realProfiles::contains)
                .findAny()
                .orElse(defaultProfile);
    }
}
