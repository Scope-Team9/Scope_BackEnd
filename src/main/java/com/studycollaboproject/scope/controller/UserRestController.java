package com.studycollaboproject.scope.controller;

import com.studycollaboproject.scope.dto.PostListDto;
import com.studycollaboproject.scope.dto.ResponseDto;
import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.User;
import com.studycollaboproject.scope.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserRestController {

    private final UserService userService;

    @GetMapping("/api/user")
    public ResponseDto getMyPage(@AuthenticationPrincipal UserDetails userDetails){

        User user = userService.getUserInfo(userDetails.getUsername());
        List<Post> bookmarkList= userService.getBookmarkList(user);
        PostListDto postListDto= userService.getPostList(user,bookmarkList);
        return new ResponseDto("200","",postListDto);






    }
}
