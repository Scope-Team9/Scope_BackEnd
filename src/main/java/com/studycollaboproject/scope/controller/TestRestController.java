package com.studycollaboproject.scope.controller;

import com.studycollaboproject.scope.dto.PropensityRequestDto;
import com.studycollaboproject.scope.dto.ResponseDto;
import com.studycollaboproject.scope.dto.TestResultDto;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.RestApiException;
import com.studycollaboproject.scope.security.UserDetailsImpl;
import com.studycollaboproject.scope.service.TestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Test Controller", description = "성향 테스트")
public class TestRestController {
    private final TestService testService;

    @Operation(summary = "성향 테스트 업데이트")
    @PostMapping("/api/test/{userId}")
    public ResponseDto updatePropensity(@RequestBody PropensityRequestDto requestDto,
                                        @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails,
                                        @Parameter(description = "수정하고자 하는 사용자의 ID", in = ParameterIn.PATH) @PathVariable Long userId) {
        log.info("POST, [{}], /api/test, userPropensity={}, memberPropensity={}", MDC.get("UUID"),
                requestDto.getUserPropensityType().toString(), requestDto.getMemberPropensityType().toString());

        if (userDetails == null) {  //로그인 정보 확인
            throw new RestApiException(ErrorCode.NO_AUTHENTICATION_ERROR);
        }
        if (userId.equals(userDetails.getUser().getId())){
            TestResultDto resultDto = testService.updatePropensityType(userDetails.getSnsId(),
                    requestDto.getUserPropensityType(), requestDto.getMemberPropensityType());
            return new ResponseDto("200", "", resultDto);}
        else throw new RestApiException(ErrorCode.NO_AUTHORIZATION_ERROR);

    }

}