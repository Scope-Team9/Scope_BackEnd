package com.studycollaboproject.scope.controller;

import com.studycollaboproject.scope.dto.PostListDto;
import com.studycollaboproject.scope.dto.ResponseDto;
import com.studycollaboproject.scope.dto.SignupRequestDto;
import com.studycollaboproject.scope.dto.UserRepuestDto;
import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.User;
import com.studycollaboproject.scope.service.PostService;
import com.studycollaboproject.scope.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@Slf4j
public class UserRestController {

    private final PostService postService;
    private final UserService userService;


    @GetMapping("/api/user")
    public ResponseDto getMyPage(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("GET, [{}], /api/user", MDC.get("UUID"));

        User user = userService.getUserInfo(userDetails.getUsername());
        List<Post> bookmarkList = userService.getBookmarkList(user);
        PostListDto postListDto = postService.getPostList(user, bookmarkList);
        return new ResponseDto("200", "", postListDto);
    }

    @PostMapping("/api/user")
    public ResponseDto updateUserDesc(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UserRepuestDto userRepuestDto){
        return userService.updateUserInfo(userDetails.getUsername(),userRepuestDto);

    }

    @PostMapping("/api/post/{postId}/url")
    public ResponseDto updateUrl(@AuthenticationPrincipal UserDetails userDetails,
                                 @RequestBody String frontUrl, @RequestBody String backUrl,
                                 @PathVariable Long postId) {
        log.info("POST, [{}], /api/post/{}/url, frontUrl={}, backUrl={}", MDC.get("UUID"), postId, frontUrl, backUrl);
        postService.updateUrl(backUrl, frontUrl, userDetails.getUsername(), postId);
        return new ResponseDto("200", "", "");
    }

    @PostMapping("/api/signup")
    public ResponseDto signup(@RequestBody SignupRequestDto signupRequestDto) {
        log.info("POST, [{}], /api/signup, signupRequestDto={}", MDC.get("UUID"), signupRequestDto.toString());

        User user = new User(signupRequestDto);
        userService.saveUser(signupRequestDto.getTechStack(),user);

        Map<String, String> token = new HashMap<>();
        token.put("token", userService.signup(user));

//        String token = userService.signup(user);
//        Cookie cookie = new Cookie("token",token);
//        cookie.setMaxAge(60*60*24*30);
//        response.addCookie(cookie); // 추가구현 필요
//        return new ResponseDto("200", "", "");
        return new ResponseDto("200", "", token);

    }

    @GetMapping("/api/login")
    public ResponseDto emailCheck(@RequestParam String email) {
        log.info("GET, [{}], /api/login, email={}", MDC.get("UUID"), email);
        //email이 이미 존재하면 T 존재하지 않으면 F
        boolean isEmailPresent = userService.emailCheckByUser(email);
        if (isEmailPresent) {
            return new ResponseDto("400", "중복된 이메일이 존재합니다.", "");
        } else {
            return new ResponseDto("200", "사용가능한 메일입니다.", "");
        }
    }

    @GetMapping("/api/login")
    public ResponseDto nicknameCheck(@RequestParam String nickname) {
        log.info("GET, [{}], /api/login, nickname={}", MDC.get("UUID"), nickname);
        //nickname이 이미 존재하면 T 존재하지 않으면 F
        boolean isNicknamePresent = userService.nicknameCheckBynickname(nickname);
        if (isNicknamePresent) {
            return new ResponseDto("400", "중복된 닉네임이 존재합니다.", "");
        } else {
            return new ResponseDto("200", "사용가능한 닉네임입니다.", "");
        }
    }


    @PostMapping("/api/bookmark/{postId}")
    public ResponseDto bookmarkCheck(@PathVariable Long postId, @AuthenticationPrincipal UserDetails userDetails){
        log.info("POST, [{}], /api/bookmark/{}", MDC.get("UUID"), postId);
        return userService.bookmarkCheck(postId,userDetails.getUsername());
    }


}
