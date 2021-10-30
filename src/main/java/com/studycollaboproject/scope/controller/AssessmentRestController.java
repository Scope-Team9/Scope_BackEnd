package com.studycollaboproject.scope.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.studycollaboproject.scope.dto.ResponseDto;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.RestApiException;
import com.studycollaboproject.scope.model.User;
import com.studycollaboproject.scope.security.UserDetailsImpl;
import com.studycollaboproject.scope.service.AssessmentService;
import com.studycollaboproject.scope.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Assessment Controller", description = "팀원 평가")
public class AssessmentRestController {

    private final AssessmentService assessmentService;
    private final UserService userService;

    @Operation(summary = "팀원 평가")
    @PostMapping("/api/assessment/{postId}")
    public ResponseDto assessmentMember(@Parameter(description = "프로젝트 ID", in = ParameterIn.PATH) @PathVariable Long postId,
                                        @Schema(description = "유저 ID 리스트") @ModelAttribute("userIds") List<Long> userIds,
                                        @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) throws JsonProcessingException {
        log.info("POST, [{}], /api/assessment/{}, userIds={}", MDC.get("UUID"), postId, userIds.toString());

        if (userDetails == null) {
            throw new RestApiException(ErrorCode.NO_AUTHENTICATION_ERROR);
        }

        User user = userService.loadUserBySnsId(userDetails.getSnsId());
        return assessmentService.assessmentMember(postId, user, userIds);
    }
}
