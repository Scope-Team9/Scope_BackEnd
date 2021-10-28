package com.studycollaboproject.scope.controller;

import com.studycollaboproject.scope.dto.ResponseDto;
import com.studycollaboproject.scope.dto.TestResultDto;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.RestApiException;
import com.studycollaboproject.scope.model.Tech;
import com.studycollaboproject.scope.security.UserDetailsImpl;
import com.studycollaboproject.scope.service.TestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Test Controller", description = "성향 테스트")
public class TestRestController {
    private final TestService testService;

    @Operation(summary = "성향 테스트 업데이트")
    @PostMapping("/api/test")
    public ResponseDto updatePropensity(@Schema(description = "유저 성향 테스트 결과", example = "[\"F\",\"L\",\"F\",\"V\",\"V\",\"H\",\"P\",\"G\",\"G\"]")
                                            @ModelAttribute("userPropensityType") List<String> userPropensityType,
                                        @Schema(description = "유저 선호 성향 테스트 결과", example = "[\"F\",\"L\",\"F\",\"V\",\"V\",\"H\",\"P\",\"G\",\"G\"]")
                                            @ModelAttribute("memberPropensityType") List<String> memberPropensityType,
                                        @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("POST, [{}], /api/test, userPropensity={}, memberPropensity={}", MDC.get("UUID"), userPropensityType.toString(), memberPropensityType.toString());

        if (userDetails == null) {  //로그인 정보 확인
            throw new RestApiException(ErrorCode.NO_AUTHENTICATION_ERROR);
        }

        TestResultDto resultDto = testService.updatePropensityType(userDetails.getNickname(), userPropensityType, memberPropensityType);
        return new ResponseDto("200", "", resultDto);
    }

    @Operation(summary = "성향 테스트 조회")
    @GetMapping("/api/test")
    public ResponseDto getPropensity(@Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("GET, [{}], /api/test", MDC.get("UUID"));

        if (userDetails == null) {  //로그인 정보 확인
            throw new RestApiException(ErrorCode.NO_AUTHENTICATION_ERROR);
        }
        TestResultDto resultDto = testService.getPropensityType(userDetails.getNickname());

        return new ResponseDto("200", "", resultDto);
    }
}
