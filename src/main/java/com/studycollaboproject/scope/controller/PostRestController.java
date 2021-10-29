package com.studycollaboproject.scope.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.studycollaboproject.scope.dto.*;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.RestApiException;
import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.ProjectStatus;
import com.studycollaboproject.scope.security.UserDetailsImpl;
import com.studycollaboproject.scope.service.PostService;
import com.studycollaboproject.scope.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Post Controller", description = "게시글 조회, 삭제, 작성 및 수정")
public class PostRestController {

    private final PostService postService;
    private final TeamService teamService;

    @Operation(summary = "게시글 작성")
    @PostMapping("/api/post")
    public ResponseDto writePost(@RequestBody PostRequestDto postRequestDto,
                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("POST, [{}], /api/post, requestDto={}", MDC.get("UUID"), postRequestDto.toString());
        if (userDetails == null) {
            throw new RestApiException(ErrorCode.NO_AUTHENTICATION_ERROR);
        }

        postService.writePost(postRequestDto);
        return new ResponseDto("200", "", "");
    }

    @Operation(summary = "게시물 조회")
    @GetMapping("/api/post")
    public ResponseDto readPost(@RequestParam String filter,
                                @RequestParam int displayNumber,
                                @RequestParam int page,
                                @RequestParam String sort,
                                @AuthenticationPrincipal UserDetails userDetails) throws JsonProcessingException {
        log.info("GET, [{}], /api/post, filter={}, displayNumber={}, page={}, sort={}", MDC.get("UUID"), filter, displayNumber, page, sort);
        String SnsId = "";
        if (userDetails != null) {
            SnsId = userDetails.getUsername();
        }
        page = page -1;
        return postService.readPost(filter, displayNumber, page, sort, username);
    }

    @Operation(summary = "게시글 수정")
    @PostMapping("/api/post/{postId}")
    public ResponseDto editPost(
            @PathVariable Long postId,
            @RequestBody PostRequestDto postRequestDto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        log.info("POST, [{}], /api/post/{}, requestDto={}", MDC.get("UUID"), postId, postRequestDto.toString());
        return postService.editPost(postId, postRequestDto);
    }

    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/api/post/{postId}")
    public ResponseDto deletePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        log.info("DELETE, [{}], /api/post/{}", MDC.get("UUID"), postId);
        postService.deletePost(postId);

        return new ResponseDto("200", "", "");
    }

    @Operation(summary = "프로젝트 상태 변경")
    @PostMapping("/api/post/{postId}/status")
    public ResponseDto updatePostStatus(@Parameter(description = "게시글 ID", in = ParameterIn.PATH) @PathVariable Long postId,
                                        @ModelAttribute("projectStatus") ProjectStatus projectStatus,
                                        @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("POST, [{}], /api/post/{}/status, projectStatus={}", MDC.get("UUID"), postId, projectStatus);
        if (userDetails == null) {
            throw new RestApiException(ErrorCode.NO_AUTHENTICATION_ERROR);
        }
        postService.updateStatus(postId, projectStatus);

        return new ResponseDto("200", "", "");
    }

    @Operation(summary = "프로젝트 상세 정보")
    @GetMapping("/api/post/{postId}")
    public ResponseDto getPost(@Parameter(description = "게시글 ID", in = ParameterIn.PATH) @PathVariable Long postId) {
        log.info("GET, [{}], /api/post/{}", MDC.get("UUID"), postId);

        Post post = postService.loadPostByPostId(postId);
        PostResponseDto postDetail = new PostResponseDto(post);
        List<MemberListResponseDto> member = teamService.getMember(postId);

        return new ResponseDto("200", "", new PostDetailDto(postDetail, member));
    }
}