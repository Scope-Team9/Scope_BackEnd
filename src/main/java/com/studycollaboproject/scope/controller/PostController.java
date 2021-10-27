package com.studycollaboproject.scope.controller;

import com.studycollaboproject.scope.dto.PostReqeustDto;
import com.studycollaboproject.scope.dto.ResponseDto;
import com.studycollaboproject.scope.repository.PostRepository;
import com.studycollaboproject.scope.service.PostService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {

    private final PostRepository postRepository;
    private final PostService postService;

    public PostController(PostRepository postRepository, PostService postService) {
        this.postService = postService;
        this.postRepository = postRepository;
    }

    @PostMapping("/api/post")
    public ResponseDto writePost (@RequestBody PostReqeustDto postReqeustDto){

        return postService.writePost(postReqeustDto);
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
            @RequestBody PostReqeustDto postReqeustDto,
            @AuthenticationPrincipal UserDetails userDetails
    )
    {
        return postService.editPost(postId,postReqeustDto);
    }

    @DeleteMapping("/api/delete/{postId}")
    public ResponseDto deletePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetails userDetails
    )
    {
        return postService.deletePost(postId);

    }
}


