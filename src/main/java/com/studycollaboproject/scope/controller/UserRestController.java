package com.studycollaboproject.scope.controller;

import com.studycollaboproject.scope.dto.PostListDto;
import com.studycollaboproject.scope.dto.ResponseDto;
import com.studycollaboproject.scope.dto.SignupRequestDto;
import com.studycollaboproject.scope.dto.UserRequestDto;
import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.User;
import com.studycollaboproject.scope.service.PostService;
import com.studycollaboproject.scope.service.UserService;
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

@RequiredArgsConstructor
@RestController
@Slf4j
@Tag(name = "User Controller", description = "회원 가입 및 회원 조회/수정")
public class UserRestController {

    private final PostService postService;
    private final UserService userService;


    @Operation(summary = "마이 페이지")
    @GetMapping("/api/user")
    public ResponseDto getMyPage(@Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {
        log.info("GET, [{}], /api/user", MDC.get("UUID"));

        User user = userService.loadUserBySnsId(userDetails.getUsername());
        List<Post> bookmarkList = userService.getBookmarkList(user);
        PostListDto postListDto = postService.getPostList(user, bookmarkList);
        return new ResponseDto("200", "", postListDto);
    }

    @Operation(summary = "회원 소개 수정")
    @PostMapping("/api/user")
    public ResponseDto updateUserinfo(@Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails,
                                      @RequestBody UserRequestDto userRequestDto){
        log.info("POST, [{}], /api/user, userRequestDto={}", MDC.get("UUID"), userRequestDto);

        return userService.updateUserInfo(userDetails.getUsername(), userRequestDto);
    }

    @Operation(summary = "회원 가입 - 회원 정보 저장")
    @PostMapping("/api/signup")
    public ResponseDto signup(@RequestBody SignupRequestDto signupRequestDto) {
        log.info("POST, [{}], /api/signup, signupRequestDto={}", MDC.get("UUID"), signupRequestDto.toString());

        User user = new User(signupRequestDto);
        userService.saveUser(signupRequestDto.getTechStack(), user);

        Map<String, String> token = new HashMap<>();
        token.put("token", userService.signup(user));

//        String token = userService.signup(user);
//        Cookie cookie = new Cookie("token",token);
//        cookie.setMaxAge(60*60*24*30);
//        response.addCookie(cookie); // 추가구현 필요
//        return new ResponseDto("200", "", "");
        return new ResponseDto("200", "", token);
    }

    @GetMapping("/api/login/email")
    public ResponseDto emailCheck(@Parameter(description = "이메일", in = ParameterIn.QUERY) @RequestParam String email) {
        log.info("GET, [{}], /api/login/email, email={}", MDC.get("UUID"), email);
        //email이 이미 존재하면 T 존재하지 않으면 F
        boolean isEmailPresent = userService.emailCheckByEmail(email);
        if (isEmailPresent) {
            return new ResponseDto("400", "중복된 이메일이 존재합니다.", "");
        } else {
            return new ResponseDto("200", "사용가능한 메일입니다.", "");
        }
    }

    @GetMapping("/api/login/nickname")
    public ResponseDto nicknameCheck(@Parameter(description = "닉네임", in = ParameterIn.QUERY) @RequestParam String nickname) {
        log.info("GET, [{}], /api/login/nickname, nickname={}", MDC.get("UUID"), nickname);
        //nickname이 이미 존재하면 T 존재하지 않으면 F
        boolean isNicknamePresent = userService.nicknameCheckBynickname(nickname);
        if (isNicknamePresent) {
            return new ResponseDto("400", "중복된 닉네임이 존재합니다.", "");
        } else {
            return new ResponseDto("200", "사용가능한 닉네임입니다.", "");
        }
    }

    @Operation(summary = "유저 소개 업데이트")
    @PostMapping("/api/user/desc")
    public ResponseDto updateUserDesc(@Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails,
                                      @RequestBody String userDesc){
        log.info("POST, [{}], /api/user/desc, userDesc={}", MDC.get("UUID"), userDesc);

        return userService.updateUserDesc(userDetails.getUsername(),userDesc);
    }
}
