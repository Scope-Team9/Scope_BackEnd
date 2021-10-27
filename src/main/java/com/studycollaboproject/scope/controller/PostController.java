package com.studycollaboproject.scope.controller;

import com.studycollaboproject.scope.dto.PostRequestDto;
import com.studycollaboproject.scope.dto.ResponseDto;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.RestApiException;
import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.ProjectStatus;
import com.studycollaboproject.scope.repository.PostRepository;
import com.studycollaboproject.scope.security.UserDetailsImpl;
import com.studycollaboproject.scope.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class PostController {

    private final PostRepository postRepository;
    private final PostService postService;

    public PostController(PostRepository postRepository, PostService postService) {
        this.postService = postService;
        this.postRepository = postRepository;
    }

    @PostMapping("/api/post")
    public ResponseDto writePost (@RequestBody PostRequestDto postRequestDto){

        return postService.writePost(postRequestDto);
    }

    @GetMapping("/api/post")
    public ResponseDto readPost(@RequestParam String filter,
                                @RequestParam int displayNumber,
                                @RequestParam int page,
                                @RequestParam String sort,
                                @AuthenticationPrincipal UserDetails userDetails)
    {
        String username = userDetails.getUsername();
        return postService.readPost(filter, displayNumber, page, sort , username);
    }

    @PostMapping("/api/post/{postId}")
    public ResponseDto editPost(
            @PathVariable Long postId,
            @RequestBody PostRequestDto postRequestDto,
            @AuthenticationPrincipal UserDetails userDetails
    )
    {
        return postService.editPost(postId, postRequestDto);
    }

    @DeleteMapping("/api/post/{postId}")
    public ResponseDto deletePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetails userDetails
    )
    {
        return postService.deletePost(postId);

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
}


