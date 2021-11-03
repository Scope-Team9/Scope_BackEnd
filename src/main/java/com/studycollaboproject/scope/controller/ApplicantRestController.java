package com.studycollaboproject.scope.controller;

import com.studycollaboproject.scope.dto.ApplicantRequestDto;
import com.studycollaboproject.scope.dto.MailDto;
import com.studycollaboproject.scope.dto.MemberListResponseDto;
import com.studycollaboproject.scope.dto.ResponseDto;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.RestApiException;
import com.studycollaboproject.scope.model.Applicant;
import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.security.UserDetailsImpl;
import com.studycollaboproject.scope.service.ApplicantService;
import com.studycollaboproject.scope.service.MailService;
import com.studycollaboproject.scope.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;


@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Applicant Controller", description = "모집 지원하기 및 지원취소")
public class ApplicantRestController {

    private final ApplicantService applicantService;
    private final PostService postService;
    private final MailService mailService;

    @Operation(summary = "모집 지원하기")
    @PostMapping("/api/applicant/{postId}")
    public ResponseDto apply(@Parameter(in = ParameterIn.PATH, description = "프로젝트 ID") @PathVariable Long postId,
                             @RequestBody ApplicantRequestDto requestDto,
                             @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) throws MessagingException {
        log.info("POST, [{}], /api/applicant/{}, comment={}", MDC.get("UUID"), postId, requestDto.getComment());

        if (userDetails == null) {
            throw new RestApiException(ErrorCode.NO_AUTHENTICATION_ERROR);
        }

        Applicant applicant = applicantService.applyPost(userDetails.getSnsId(), postId, requestDto.getComment());
        mailService.applicantMAilBilder(new MailDto(applicant));

        return new ResponseDto("200", "", "");
    }

    @Operation(summary = "모집 지원취소")
    @DeleteMapping("/api/applicant/{postId}")
    public ResponseDto cancelApply(@Parameter(in = ParameterIn.PATH, description = "프로젝트 ID") @PathVariable Long postId,
                                   @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("DELETE, [{}], /api/applicant/{}", MDC.get("UUID"), postId);

        if (userDetails == null) {
            throw new RestApiException(ErrorCode.NO_AUTHENTICATION_ERROR);
        }
        applicantService.cancelApply(userDetails.getSnsId(), postId);

        return new ResponseDto("200", "", "");
    }

    @Operation(summary = "모집 현황")
    @GetMapping("/api/applicant/{postId}")
    public ResponseDto getApplicant(@Parameter(in = ParameterIn.PATH, description = "프로젝트 ID") @PathVariable Long postId,
                                    @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("GET, [{}], /api/applicant/{}", MDC.get("UUID"), postId);

        if (userDetails == null) {
            throw new RestApiException(ErrorCode.NO_AUTHENTICATION_ERROR);
        }

        Post post = postService.loadPostByPostId(postId);
        if (!post.getUser().getSnsId().equals(userDetails.getSnsId())) {
            throw new RestApiException(ErrorCode.NO_AUTHORIZATION_ERROR);
        }
        List<MemberListResponseDto> responseDto = applicantService.getApplicant(post);
        return new ResponseDto("200", "", responseDto);
    }
}
