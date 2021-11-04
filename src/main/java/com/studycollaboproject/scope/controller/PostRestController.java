package com.studycollaboproject.scope.controller;

import com.studycollaboproject.scope.dto.*;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.RestApiException;
import com.studycollaboproject.scope.model.Post;
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

    @Operation(summary = "프로젝트 작성")
    @PostMapping("/api/post")
    public ResponseDto writePost(@RequestBody PostRequestDto postRequestDto,
                                 @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("POST, [{}], /api/post, requestDto={}", MDC.get("UUID"), postRequestDto.toString());
        if (userDetails == null) {
            throw new RestApiException(ErrorCode.NO_AUTHENTICATION_ERROR);
        }

        PostResponseDto responseDto = postService.writePost(postRequestDto, userDetails.getSnsId());
        return new ResponseDto("200", "", responseDto);
    }

    @Operation(summary = "프로젝트 조회")
    @GetMapping("/api/post")
    public ResponseDto readPost(@Parameter(description = "필터", in = ParameterIn.QUERY, example = ";;;;;;;;;;;;;;") @RequestParam String filter,
                                @Parameter(description = "디스플레이 수", in = ParameterIn.QUERY, example = "15") @RequestParam int displayNumber,
                                @Parameter(description = "페이지 수", in = ParameterIn.QUERY, example = "1") @RequestParam int page,
                                @Parameter(description = "정렬 기준", in = ParameterIn.QUERY, example = "createdAt") @RequestParam String sort,
                                @Parameter(description = "북마크 / 추천", in = ParameterIn.QUERY, example = "bookmark", allowEmptyValue = true) @RequestParam String bookmarkRecommend,
                                @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {
        log.info("GET, [{}], /api/post, filter={}, displayNumber={}, page={}, sort={}, bookmarkRecommend={}", MDC.get("UUID"), filter, displayNumber, page, sort, bookmarkRecommend);
        String SnsId = "";
        if(bookmarkRecommend.equals("recommend") || bookmarkRecommend.equals("bookmark")){
            if (userDetails == null) {
                throw new RestApiException(ErrorCode.NO_AUTHENTICATION_ERROR);
            }
            SnsId = userDetails.getUsername();
        }

        page = page - 1;
        Map<String, Object> postResponseDtos = postService.readPost(filter, displayNumber, page, sort, SnsId, bookmarkRecommend);
        return new ResponseDto("200", "success", postResponseDtos);
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
        PostResponseDto responseDto = postService.editPost(postId, postRequestDto, userDetails.getUsername());

        return new ResponseDto("200", "", responseDto);
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
        Long deletedId = postService.deletePost(postId,userDetails.getUsername());
        Map<String, Long> map = new HashMap<>();
        map.put("postId",deletedId);
        return new ResponseDto("200", "", map);
    }

    @Operation(summary = "프로젝트 상태 변경")
    @PostMapping("/api/post/{postId}/status")
    public ResponseDto updatePostStatus(@Parameter(description = "프로젝트 ID", in = ParameterIn.PATH) @PathVariable Long postId,
                                        @RequestBody ProjectStatusRequestDto requestDto,
                                        @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("POST, [{}], /api/post/{}/status, projectStatus={}", MDC.get("UUID"), postId, requestDto.getProjectStatus());
        if (userDetails == null) {
            throw new RestApiException(ErrorCode.NO_AUTHENTICATION_ERROR);
        }
        PostResponseDto responseDto = postService.updateStatus(postId, requestDto.getProjectStatus(), userDetails.getSnsId());

        return new ResponseDto("200", "", responseDto);
    }

    @Operation(summary = "프로젝트 상세 정보")
    @GetMapping("/api/post/{postId}")
    public ResponseDto getPost(@Parameter(description = "프로젝트 ID", in = ParameterIn.PATH) @PathVariable Long postId,
                               @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {
        log.info("GET, [{}], /api/post/{}", MDC.get("UUID"), postId);

        Post post = postService.loadPostByPostId(postId);
        PostResponseDto postDetail = new PostResponseDto(post);
        List<MemberListResponseDto> member = teamService.getMember(postId);
        boolean isTeamStarter = false;
        boolean isBookmarkChecked = false;
        if (userDetails != null) {
            isTeamStarter = postService.isTeamStarter(post,userDetails.getUsername());
            isBookmarkChecked = postService.isBookmarkChecked(postId,userDetails.getUsername());
        }
        return new ResponseDto("200", "", new PostDetailDto(postDetail, member,isTeamStarter, isBookmarkChecked));
    }


    @Operation(summary = "프로젝트 git Repository URL 업데이트")
    @PostMapping("/api/post/{postId}/url")
    public ResponseDto updateUrl(@Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails,
                                 @RequestBody UrlUpdateRequestDto requestDto,
                                 @Parameter(description = "프로젝트 ID", in = ParameterIn.PATH) @PathVariable Long postId) {
        log.info("POST, [{}], /api/post/{}/url, frontUrl={}, backUrl={}", MDC.get("UUID"), postId, requestDto.getFrontUrl(), requestDto.getBackUrl());
        if (userDetails == null) {
            throw new RestApiException(ErrorCode.NO_AUTHENTICATION_ERROR);
        }
        PostResponseDto responseDto = postService.updateUrl(requestDto.getBackUrl(), requestDto.getFrontUrl(), userDetails.getUsername(), postId);
        return new ResponseDto("200", "", responseDto);
    }
}