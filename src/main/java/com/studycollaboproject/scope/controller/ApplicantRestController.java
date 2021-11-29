package com.studycollaboproject.scope.controller;

import com.studycollaboproject.scope.dto.ApplicantRequestDto;
import com.studycollaboproject.scope.dto.MailDto;
import com.studycollaboproject.scope.dto.MemberListResponseDto;
import com.studycollaboproject.scope.dto.ResponseDto;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.ForbiddenException;
import com.studycollaboproject.scope.exception.NoAuthException;
import com.studycollaboproject.scope.model.Applicant;
import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.User;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Optional;


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
    public ResponseEntity<Object> apply(@Parameter(in = ParameterIn.PATH, description = "프로젝트 ID") @PathVariable Long postId,
                                        @RequestBody ApplicantRequestDto requestDto,
                                        @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) throws MessagingException {
        log.info("[{}], 모집 지원하기, POST, /api/applicant/{}, comment={}", MDC.get("UUID"), postId, requestDto.getComment());

        String snsId = Optional.ofNullable(userDetails).orElseThrow(
                () -> new NoAuthException(ErrorCode.NO_AUTHENTICATION_ERROR)
        ).getSnsId();

        Applicant applicant = applicantService.applyPost(snsId, postId, requestDto.getComment());
        mailService.applicantMailBuilder(new MailDto(applicant));
        return new ResponseEntity<>(
                new ResponseDto("프로젝트에 지원되었습니다.", ""),
                HttpStatus.CREATED
        );
    }

    @Operation(summary = "모집 지원취소")
    @DeleteMapping("/api/applicant/{postId}")
    public ResponseEntity<Object> cancelApply(@Parameter(in = ParameterIn.PATH, description = "프로젝트 ID") @PathVariable Long postId,
                                              @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("[{}], 모집 지원취소, DELETE, /api/applicant/{}", MDC.get("UUID"), postId);

        String snsId = Optional.ofNullable(userDetails).orElseThrow(
                () -> new NoAuthException(ErrorCode.NO_AUTHENTICATION_ERROR)
        ).getSnsId();
        applicantService.cancelApply(snsId, postId);

        return new ResponseEntity<>(
                new ResponseDto("프로젝트 지원이 취소되었습니다.", ""),
                HttpStatus.OK
        );

    }

    @Operation(summary = "모집 현황")
    @GetMapping("/api/applicant/{postId}")
    public ResponseEntity<Object> getApplicant(@Parameter(in = ParameterIn.PATH, description = "프로젝트 ID") @PathVariable Long postId,
                                               @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("[{}], 모집 현황, GET, /api/applicant/{}", MDC.get("UUID"), postId);

        String snsId = Optional.ofNullable(userDetails).orElseThrow(
                () -> new NoAuthException(ErrorCode.NO_AUTHENTICATION_ERROR)
        ).getSnsId();

        Post post = postService.loadPostByPostId(postId);
        Optional.ofNullable(post).map(Post::getUser).map(User::getSnsId).filter(o -> o.equals(snsId)).orElseThrow(
                () -> new ForbiddenException(ErrorCode.NO_AUTHORIZATION_ERROR)
        );

        List<MemberListResponseDto> responseDto = applicantService.getApplicant(post);
        return new ResponseEntity<>(
                new ResponseDto("모집 지원 현황 조회 성공", responseDto),
                HttpStatus.OK
        );

    }
}
