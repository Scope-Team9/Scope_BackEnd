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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Post Controller", description = "프로젝트 조회, 삭제, 작성 및 수정")
public class PostRestController {

    private final PostService postService;
    private final TeamService teamService;

    @Operation(summary = "프로젝트 작성")
    @PostMapping("/api/post")
    public ResponseDto writePost(@RequestBody PostRequestDto postRequestDto,
                                 @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("POST, [{}], /api/post, requestDto={}", MDC.get("UUID"), postRequestDto.toString());
        if (userDetails == null) {
            throw new RestApiException(ErrorCode.NO_AUTHENTICATION_ERROR);
        }

        postService.writePost(postRequestDto, userDetails.getSnsId());
        return new ResponseDto("200", "", "");
    }

    @Operation(summary = "프로젝트 조회")
    @GetMapping("/api/post")
    public ResponseDto readPost(@Parameter(description = "필터", in = ParameterIn.QUERY) @RequestParam String filter,
                                @Parameter(description = "디스플레이 수", in = ParameterIn.QUERY) @RequestParam int displayNumber,
                                @Parameter(description = "페이지 수", in = ParameterIn.QUERY) @RequestParam int page,
                                @Parameter(description = "정렬 기준", in = ParameterIn.QUERY) @RequestParam String sort,
                                @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) throws JsonProcessingException {
        log.info("GET, [{}], /api/post, filter={}, displayNumber={}, page={}, sort={}", MDC.get("UUID"), filter, displayNumber, page, sort);
        String SnsId = "";
        if (userDetails != null) {
            SnsId = userDetails.getUsername();
        }
        page = page - 1;
        return postService.readPost(filter, displayNumber, page, sort, SnsId);
    }

    @Operation(summary = "프로젝트 수정")
    @PostMapping("/api/post/{postId}")
    public ResponseDto editPost(
            @Parameter(description = "프로젝트 ID", in = ParameterIn.PATH) @PathVariable Long postId,
            @RequestBody PostRequestDto postRequestDto,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails
    ) {
        log.info("POST, [{}], /api/post/{}, requestDto={}", MDC.get("UUID"), postId, postRequestDto.toString());
        if (userDetails == null) {
            throw new RestApiException(ErrorCode.NO_AUTHENTICATION_ERROR);
        }
        return postService.editPost(postId, postRequestDto,userDetails.getUsername());
    }

    @Operation(summary = "프로젝트 삭제")
    @DeleteMapping("/api/post/{postId}")
    public ResponseDto deletePost(
            @Parameter(description = "프로젝트 ID", in = ParameterIn.PATH) @PathVariable Long postId,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails
    ) {
        log.info("DELETE, [{}], /api/post/{}", MDC.get("UUID"), postId);
        if (userDetails == null) {
            throw new RestApiException(ErrorCode.NO_AUTHENTICATION_ERROR);
        }
        return postService.deletePost(postId,userDetails.getUsername());
    }

    @Operation(summary = "프로젝트 상태 변경")
    @PostMapping("/api/post/{postId}/status")
    public ResponseDto updatePostStatus(@Parameter(description = "프로젝트 ID", in = ParameterIn.PATH) @PathVariable Long postId,
                                        @Schema(description = "프로젝트 상태") @ModelAttribute("projectStatus") ProjectStatus projectStatus,
                                        @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("POST, [{}], /api/post/{}/status, projectStatus={}", MDC.get("UUID"), postId, projectStatus);
        if (userDetails == null) {
            throw new RestApiException(ErrorCode.NO_AUTHENTICATION_ERROR);
        }
        postService.updateStatus(postId, projectStatus,userDetails.getSnsId());

        return new ResponseDto("200", "", "");
    }

    @Operation(summary = "프로젝트 상세 정보")
    @GetMapping("/api/post/{postId}")
    public ResponseDto getPost(@Parameter(description = "프로젝트 ID", in = ParameterIn.PATH) @PathVariable Long postId,
                               @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {
        log.info("GET, [{}], /api/post/{}", MDC.get("UUID"), postId);

        Post post = postService.loadPostByPostId(postId);
        PostResponseDto postDetail = new PostResponseDto(post);
        List<MemberListResponseDto> member = teamService.getMember(postId);
        boolean isTeamStarter = postService.isTeamStarter(post,userDetails.getUsername());
        return new ResponseDto("200", "", new PostDetailDto(postDetail, member,isTeamStarter));
    }

    @Operation(summary = "프로젝트 git Repository URL 업데이트")
    @PostMapping("/api/post/{postId}/url")
    public ResponseDto updateUrl(@Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails,
                                 @Schema(description = "프론트엔드 Repository Url") @ModelAttribute("frontUrl") String frontUrl,
                                 @Schema(description = "백엔드 Repository Url") @ModelAttribute("backUrl") String backUrl,
                                 @Parameter(description = "프로젝트 ID", in = ParameterIn.PATH) @PathVariable Long postId) {
        log.info("POST, [{}], /api/post/{}/url, frontUrl={}, backUrl={}", MDC.get("UUID"), postId, frontUrl, backUrl);
        if (userDetails == null) {
            throw new RestApiException(ErrorCode.NO_AUTHENTICATION_ERROR);
        }
        postService.updateUrl(backUrl, frontUrl, userDetails.getUsername(), postId);
        return new ResponseDto("200", "", "");
    }
}