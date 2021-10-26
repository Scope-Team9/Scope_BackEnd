package com.studycollaboproject.scope.controller;

import com.studycollaboproject.scope.dto.ResponseDto;
import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.User;
import com.studycollaboproject.scope.service.ApplicantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ApplicantController {

    private final ApplicantService applicantService;
    private final UserService userService;
    private final PostService postService;

    @PostMapping("/api/applicant/{postId}")
    public ResponseDto apply(@PathVariable Long postId, Map<String, String> map) {
        String comment = map.get("comment");

        //로그인 회원 조회
        User user = userService.loadUser(userId);
        Post post = postService.loadPost(postId);
        applicantService.applyPost(post, user, comment);

        return new ResponseDto("200", "", "");
    }
}
