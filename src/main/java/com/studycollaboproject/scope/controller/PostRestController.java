package com.studycollaboproject.scope.controller;

import com.studycollaboproject.scope.dto.*;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.RestApiException;
import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.User;
import com.studycollaboproject.scope.model.UserStatus;
import com.studycollaboproject.scope.security.UserDetailsImpl;
import com.studycollaboproject.scope.service.ApplicantService;
import com.studycollaboproject.scope.service.PostService;
import com.studycollaboproject.scope.service.TeamService;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Post Controller", description = "프로젝트 조회, 삭제, 작성 및 수정")
public class PostRestController {

    private final PostService postService;
    private final TeamService teamService;
    private final ApplicantService applicantService;

    @Operation(summary = "프로젝트 작성")
    @PostMapping("/api/post")
    public ResponseEntity<Object> writePost(@RequestBody PostRequestDto postRequestDto,
                                 @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("POST, [{}], /api/post, requestDto={}", MDC.get("UUID"), postRequestDto.toString());
        // [예외처리] 로그인 정보가 없을 때
        if (userDetails == null) {
            throw new RestApiException(ErrorCode.NO_AUTHENTICATION_ERROR);
        }

        PostResponseDto responseDto = postService.writePost(postRequestDto, userDetails.getSnsId());
        return new ResponseEntity<>(
                new ResponseDto("게시물이 성공적으로 저장되었습니다.", responseDto),
                HttpStatus.CREATED
        );

    }

    @Operation(summary = "프로젝트 조회")
    @GetMapping("/api/post")
    public ResponseEntity<Object> readPost(@Parameter(description = "필터", in = ParameterIn.QUERY, example = ";;;;;;;;;;;;;;") @RequestParam String filter,
                                           @Parameter(description = "정렬 기준", in = ParameterIn.QUERY, example = "createdAt") @RequestParam String sort,
                                           @Parameter(description = "북마크 / 추천", in = ParameterIn.QUERY, example = "bookmark", allowEmptyValue = true) @RequestParam String bookmarkRecommend,
                                           @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {
        log.info("GET, [{}], /api/post, filter={}, sort={}, bookmarkRecommend={}", MDC.get("UUID"), filter, sort, bookmarkRecommend);
        String SnsId = "";
        if(bookmarkRecommend.equals("recommend") || bookmarkRecommend.equals("bookmark")){
            if (userDetails == null) {
                throw new RestApiException(ErrorCode.NO_AUTHENTICATION_ERROR);
            }
            SnsId = userDetails.getUsername();
        }

        List<PostResponseDto> postResponseDtos = postService.readPost(filter, sort, SnsId, bookmarkRecommend);
        return new ResponseEntity<>(
                new ResponseDto("프로젝트 조회 성공", postResponseDtos),
                HttpStatus.OK
        );

    }

    @Operation(summary = "프로젝트 수정")
    @PostMapping("/api/post/{postId}")
    public ResponseEntity<Object> editPost(
            @Parameter(description = "프로젝트 ID", in = ParameterIn.PATH) @PathVariable Long postId,
            @RequestBody PostRequestDto postRequestDto,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails
    ) {
        log.info("POST, [{}], /api/post/{}, requestDto={}", MDC.get("UUID"), postId, postRequestDto.toString());
        // [예외처리] 로그인 정보가 없을 때
        if (userDetails == null) {
            throw new RestApiException(ErrorCode.NO_AUTHENTICATION_ERROR);
        }
        PostResponseDto responseDto = postService.editPost(postId, postRequestDto, userDetails.getUsername());
        return new ResponseEntity<>(
                new ResponseDto("게시물이 성공적으로 수정되었습니다.", responseDto),
                HttpStatus.OK
        );
    }

    @Operation(summary = "프로젝트 삭제")
    @DeleteMapping("/api/post/{postId}")
    public ResponseEntity<Object> deletePost(
            @Parameter(description = "프로젝트 ID", in = ParameterIn.PATH) @PathVariable Long postId,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails
    ) {
        log.info("DELETE, [{}], /api/post/{}", MDC.get("UUID"), postId);
        if (userDetails == null) {
            throw new RestApiException(ErrorCode.NO_AUTHENTICATION_ERROR);
        }
        Long deletedId = postService.deletePost(postId,userDetails.getUsername());
        Map<String, Long> map = new HashMap<>();
        map.put("postId",deletedId);
        return new ResponseEntity<>(
                new ResponseDto("프로젝트가 성공적으로 삭제되었습니다.", map),
                HttpStatus.OK
        );
    }

    @Operation(summary = "프로젝트 상태 변경")
    @PostMapping("/api/post/{postId}/status")
    public ResponseEntity<Object> updatePostStatus(@Parameter(description = "프로젝트 ID", in = ParameterIn.PATH) @PathVariable Long postId,
                                        @RequestBody ProjectStatusRequestDto requestDto,
                                        @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("POST, [{}], /api/post/{}/status, projectStatus={}", MDC.get("UUID"), postId, requestDto.getProjectStatus());
        if (userDetails == null) {
            throw new RestApiException(ErrorCode.NO_AUTHENTICATION_ERROR);
        }
        PostResponseDto responseDto = postService.updateStatus(postId, requestDto.getProjectStatus(), userDetails.getSnsId());
        return new ResponseEntity<>(
                new ResponseDto("프로젝트 상태가 성공적으로 수정되었습니다.", responseDto),
                HttpStatus.OK
        );

    }

    @Operation(summary = "프로젝트 상세 조회")
    @GetMapping("/api/post/{postId}")
    public ResponseEntity<Object> getPost(@Parameter(description = "프로젝트 ID", in = ParameterIn.PATH) @PathVariable Long postId,
                               @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("GET, [{}], /api/post/{}", MDC.get("UUID"), postId);

        List<MemberListResponseDto> member = teamService.getMember(postId);

        boolean isBookmarkChecked = false;
        String userStatus;
        Post post = postService.loadPostByPostId(postId);

        if (userDetails != null) {
            User user = userDetails.getUser();
            if (postService.isTeamStarter(post,user.getSnsId())){
                userStatus = UserStatus.USER_STATUS_TEAM_STARTER.getUserStatus();
            }else if (teamService.isMemeber(post,user)){
                userStatus = UserStatus.USER_STATUS_MEMBER.getUserStatus();
            }else if (applicantService.isApplicant(post,user)){
                userStatus = UserStatus.USER_STATUS_APPLICANT.getUserStatus();
            }else {
                userStatus = UserStatus.USER_STATUS_USER.getUserStatus();
            }
            isBookmarkChecked = postService.isBookmarkChecked(post,user);
        }else {
            userStatus = UserStatus.USER_STATUS_ANONYMOUS.getUserStatus();
        }
        PostResponseDto postDetail = new PostResponseDto(post, isBookmarkChecked);
        return new ResponseEntity<>(
                new ResponseDto("프로젝트 상세 정보 조회 성공", new PostDetailDto(postDetail, member,userStatus)),
                HttpStatus.OK
        );
    }


    @Operation(summary = "프로젝트 git Repository URL 업데이트")
    @PostMapping("/api/post/{postId}/url")
    public ResponseEntity<Object> updateUrl(@Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails,
                                 @RequestBody UrlUpdateRequestDto requestDto,
                                 @Parameter(description = "프로젝트 ID", in = ParameterIn.PATH) @PathVariable Long postId) {
        log.info("POST, [{}], /api/post/{}/url, frontUrl={}, backUrl={}", MDC.get("UUID"), postId, requestDto.getFrontUrl(), requestDto.getBackUrl());
        if (userDetails == null) {
            throw new RestApiException(ErrorCode.NO_AUTHENTICATION_ERROR);
        }
        PostResponseDto responseDto = postService.updateUrl(requestDto.getBackUrl(), requestDto.getFrontUrl(), userDetails.getUsername(), postId);
        return new ResponseEntity<>(
                new ResponseDto("프로젝트 URL이 성공적으로 저장되었습니다.", responseDto),
                HttpStatus.OK
        );
    }
}