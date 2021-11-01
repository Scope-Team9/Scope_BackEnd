package com.studycollaboproject.scope.controller;

import com.studycollaboproject.scope.dto.*;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.RestApiException;
import com.studycollaboproject.scope.model.User;
import com.studycollaboproject.scope.security.UserDetailsImpl;
import com.studycollaboproject.scope.service.PostService;
import com.studycollaboproject.scope.service.TestService;
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
import java.util.Map;

@RequiredArgsConstructor
@RestController
@Slf4j
@Tag(name = "User Controller", description = "회원 가입 및 회원 조회/수정")
public class UserRestController {

    private final PostService postService;
    private final UserService userService;
    private final TestService testService;


    @Operation(summary = "마이 페이지")
    @GetMapping("/api/user/{userId}")
    public ResponseDto getMyPage(@Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails,
                                 @Parameter(description = "조회하고자 하는 사용자의 ID", in = ParameterIn.PATH) @PathVariable Long userId) {
        log.info("GET, [{}], /api/user", MDC.get("UUID"));

        User user = userService.loadUserByUserId(userId);
        MypagePostListDto mypagePostListDto = postService.getPostList(user);
        MypageResponseDto mypageResponseDto = userService.Mypage(user,userDetails,mypagePostListDto);


        return new ResponseDto("200", "", mypageResponseDto);
    }

    @Operation(summary = "회원 소개 수정")
    @PostMapping("/api/user/{userId}")
    public ResponseDto updateUserinfo(@Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails,
                                      @RequestBody UserRequestDto userRequestDto,@Parameter(description = "수정하고자 하는 사용자의 ID", in = ParameterIn.PATH) @PathVariable Long userId){
        log.info("POST, [{}], /api/user, userRequestDto={}", MDC.get("UUID"), userRequestDto);
        if (userId.equals(userDetails.getUser().getId())){
          UserResponseDto userResponseDto = userService.updateUserInfo(userDetails.getUsername(), userRequestDto);
          return new ResponseDto("200","회원 정보가 수정되었습니다.",userResponseDto);}
        else throw new RestApiException(ErrorCode.NO_AUTHORIZATION_ERROR);

    }

    @Operation(summary = "회원 가입 - 회원 정보와 테스트 결과 저장")
    @PostMapping("/api/signup")
    public ResponseDto signup(@RequestBody SignupRequestDto signupRequestDto) {
        log.info("POST, [{}], /api/signup, signupRequestDto={}", MDC.get("UUID"), signupRequestDto.toString());


        String userTestResult = testService.testResult(signupRequestDto.getUserPropensityType());
        String memberTestResult = testService.testResult(signupRequestDto.getMemberPropensityType());
        User user = new User(signupRequestDto,userTestResult,memberTestResult);
        UserResponseDto userResponseDto = userService.saveUser(signupRequestDto.getTechStack(), user);

        Map<String, Object> map = new HashMap<>();
        map.put("token", userService.createToken(user));
        map.put("user", userResponseDto);

//        String token = userService.signup(user);
//        Cookie cookie = new Cookie("token",token);
//        cookie.setMaxAge(60*60*24*30);
//        response.addCookie(cookie); // 추가구현 필요
//        return new ResponseDto("200", "", "");
        return new ResponseDto("200", "", map);
    }

    @Operation(summary = "이메일 중복 확인")
    @GetMapping("/api/login/email")
    public ResponseDto emailCheck(@Parameter(description = "이메일", in = ParameterIn.QUERY) @RequestParam String email) {
        log.info("GET, [{}], /api/login/email, email={}", MDC.get("UUID"), email);
        //email이 이미 존재하면 T 존재하지 않으면 F
        boolean isEmailPresent = userService.emailCheckByEmail(email);
        if (isEmailPresent) {
            throw new RestApiException(ErrorCode.ALREADY_EMAIL_ERROR);
        } else {
            return new ResponseDto("200", "사용가능한 메일입니다.", "");
        }
    }

    @Operation(summary = "닉네임 중복 확인")
    @GetMapping("/api/login/nickname")
    public ResponseDto nicknameCheck(@Parameter(description = "닉네임", in = ParameterIn.QUERY) @RequestParam String nickname) {
        log.info("GET, [{}], /api/login/nickname, nickname={}", MDC.get("UUID"), nickname);
        //nickname이 이미 존재하면 T 존재하지 않으면 F
        boolean isNicknamePresent = userService.nicknameCheckByNickname(nickname);
        if (isNicknamePresent) {
            throw  new RestApiException(ErrorCode.ALREADY_NICKNAME_ERROR);
        } else {
            return new ResponseDto("200", "사용가능한 닉네임입니다.", "");
        }
    }

    @Operation(summary = "유저 소개 업데이트")
    @PostMapping("/api/user/{userId}/desc")
    public ResponseDto updateUserDesc(@Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails,
                                      @RequestBody String userDesc, @Parameter(description = "수정하고자 하는 사용자의 ID", in = ParameterIn.PATH) @PathVariable Long userId){
        log.info("POST, [{}], /api/user/desc, userDesc={}", MDC.get("UUID"), userDesc);
        if (userId.equals(userDetails.getUser().getId())){
            UserResponseDto userResponseDto = userService.updateUserDesc(userDetails.getUsername(), userDesc);

            return new ResponseDto("200","회원 정보가 수정되었습니다.",userResponseDto);
        }
        else throw new RestApiException(ErrorCode.NO_AUTHORIZATION_ERROR);


    }



    @Operation(summary = "북마크 추가")
    @PostMapping("/api/bookmark/{postId}")
    public ResponseDto bookmarkCheck(@Parameter(description = "프로젝트 ID", in = ParameterIn.PATH) @PathVariable Long postId,
                                     @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails){
        log.info("POST, [{}], /api/bookmark/{}", MDC.get("UUID"), postId);
        return userService.bookmarkCheck(postId,userDetails.getSnsId());
    }
}
