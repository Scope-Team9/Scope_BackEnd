package com.studycollaboproject.scope.controller;

import com.studycollaboproject.scope.dto.ResponseDto;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.RestApiException;
import com.studycollaboproject.scope.model.User;
import com.studycollaboproject.scope.security.UserDetailsImpl;
import com.studycollaboproject.scope.service.AssessmentService;
import com.studycollaboproject.scope.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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
public class AssessmentRestController {

    private final AssessmentService assessmentService;
    private final UserService userService;

    @PostMapping("/api/assessment/{postId}")
    public ResponseDto assessmentMember(@Parameter(description = "게시글 ID", in = ParameterIn.PATH) @PathVariable Long postId,
                                       @ModelAttribute("userIds") List<Long> userIds,
                                       @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("POST, [{}], /api/assessment/{}, userIds={}", MDC.get("UUID"), postId, userIds.toString());

        if (userDetails == null) {
            throw new RestApiException(ErrorCode.NO_AUTHENTICATION_ERROR);
        }

        User user = userService.loadUserByNickname(userDetails.getNickname());
        assessmentService.assessmentMember(postId, user, userIds);

        return new ResponseDto("200", "", "");
    }
}
