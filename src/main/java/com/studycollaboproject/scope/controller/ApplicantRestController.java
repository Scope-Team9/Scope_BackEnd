package com.studycollaboproject.scope.controller;

import com.studycollaboproject.scope.dto.ResponseDto;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.RestApiException;
import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.User;
import com.studycollaboproject.scope.security.UserDetailsImpl;
import com.studycollaboproject.scope.service.ApplicantService;
import com.studycollaboproject.scope.service.PostService;
import com.studycollaboproject.scope.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Applicant Controller", description = "모집 지원하기 및 지원취소")
public class ApplicantRestController {

    private final ApplicantService applicantService;
    private final UserService userService;
    private final PostService postService;

    @Operation(summary = "모집 지원하기")
    @PostMapping("/api/applicant/{postId}")
    public ResponseDto apply(@Parameter(in = ParameterIn.PATH, description = "게시글 ID") @PathVariable Long postId,
                             Map<String, String> map,
                             @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String comment = map.get("comment");
        log.info("POST, [{}], /api/applicant/{}, comment={}", MDC.get("UUID"), postId, comment);

        if (userDetails == null) {
            throw new RestApiException(ErrorCode.NO_AUTHENTICATION_ERROR);
        }

        User user = userService.loadUserByNickname(userDetails.getNickname());    //로그인 회원 조회
        Post post = postService.loadPostByPostId(postId);
        applicantService.applyPost(post, user, comment);

        return new ResponseDto("200", "", "");
    }

    @Operation(summary = "모집 지원취소")
    @DeleteMapping("/api/applicant/{postId}")
    public ResponseDto cancelApply(@PathVariable Long postId,
                                   @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("DELETE, [{}], /api/applicant/{}", MDC.get("UUID"), postId);

        if (userDetails == null) {
            throw new RestApiException(ErrorCode.NO_AUTHENTICATION_ERROR);
        }

        User user = userService.loadUserByNickname(userDetails.getNickname());    //로그인 회원 조회
        Post post = postService.loadPostByPostId(postId);
        applicantService.cancelApply(user, post);

        return new ResponseDto("200", "", "");
    }
}
