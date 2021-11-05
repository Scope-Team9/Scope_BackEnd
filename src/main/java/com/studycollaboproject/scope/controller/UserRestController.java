package com.studycollaboproject.scope.controller;

import com.studycollaboproject.scope.dto.*;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.RestApiException;
import com.studycollaboproject.scope.model.User;
import com.studycollaboproject.scope.security.UserDetailsImpl;
import com.studycollaboproject.scope.service.MailService;
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

import javax.mail.MessagingException;
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
    private final MailService mailService;


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
        // [예외처리] 로그인 정보가 없을 때
        else {
            throw new RestApiException(ErrorCode.NO_AUTHORIZATION_ERROR);
        }
    }

    @Operation(summary = "회원 가입 - 회원 정보와 테스트 결과 저장")
    @PostMapping("/api/signup")
    public ResponseDto signup(@RequestBody SignupRequestDto signupRequestDto) {
        log.info("POST, [{}], /api/signup, signupRequestDto={}", MDC.get("UUID"), signupRequestDto.toString());

        String userTestResult = testService.testResult(signupRequestDto.getUserPropensityType());
        String memberTestResult = testService.testResult(signupRequestDto.getMemberPropensityType());
        User user = new User(signupRequestDto,userTestResult,memberTestResult);
        String token = userService.createToken(user);
        UserResponseDto userResponseDto = userService.saveUser(signupRequestDto.getTechStack(), user,token);

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("user", userResponseDto);

        return new ResponseDto("200", "", map);
    }

    @Operation(summary = "이메일 중복 확인")
    @GetMapping("/api/login/email")
    public ResponseDto emailCheck(@Parameter(description = "이메일", in = ParameterIn.QUERY) @RequestParam String email) {
        log.info("GET, [{}], /api/login/email, email={}", MDC.get("UUID"), email);
        return userService.emailCheckByEmail(email);

    }

    @Operation(summary = "닉네임 중복 확인")
    @GetMapping("/api/login/nickname")
    public ResponseDto nicknameCheck(@Parameter(description = "닉네임", in = ParameterIn.QUERY) @RequestParam String nickname) {
        log.info("GET, [{}], /api/login/nickname, nickname={}", MDC.get("UUID"), nickname);
        return userService.nicknameCheckByNickname(nickname);

    }

    @Operation(summary = "유저 소개 업데이트")
    @PostMapping("/api/user/{userId}/desc")
    public ResponseDto updateUserDesc(@Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails,
                                      @RequestBody String introduction, @Parameter(description = "수정하고자 하는 사용자의 ID", in = ParameterIn.PATH) @PathVariable Long userId){
        log.info("POST, [{}], /api/user/desc, userDesc={}", MDC.get("UUID"), introduction);
        if (userId.equals(userDetails.getUser().getId())){
            UserResponseDto userResponseDto = userService.updateUserDesc(userDetails.getUsername(), introduction);

            return new ResponseDto("200","회원 정보가 수정되었습니다.",userResponseDto);
        }
        // [예외처리] 로그인 정보가 없을 때
        else throw new RestApiException(ErrorCode.NO_AUTHORIZATION_ERROR);

    }



    @Operation(summary = "북마크 추가")
    @PostMapping("/api/bookmark/{postId}")
    public ResponseDto bookmarkCheck(@Parameter(description = "프로젝트 ID", in = ParameterIn.PATH) @PathVariable Long postId,
                                     @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails){
        log.info("POST, [{}], /api/bookmark/{}", MDC.get("UUID"), postId);
        return userService.bookmarkCheck(postId,userDetails.getSnsId());
    }

    @Operation(summary = "회원 탈퇴")
    @DeleteMapping("api/user/{userId}")
    public ResponseDto deleteUser(@Parameter(description = "탈퇴하려는 회원 ID",in = ParameterIn.PATH) @PathVariable Long userId,
                                  @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails){
        log.info("POST, [{}], /api/user/{}", MDC.get("UUID"), userId);
        if (userDetails.getUser().getId().equals(userId)){
            return userService.deleteUser(userDetails.getUser());
        }
        // [예외처리] 로그인 정보가 없을 때
        else {
            throw new RestApiException(ErrorCode.NO_AUTHORIZATION_ERROR);
        }
    }

    @Operation(summary = "이메일 인증 전송")
    @GetMapping("api/user/email")
    public ResponseDto emailAuthentication(@Parameter(description = "이메일", in = ParameterIn.QUERY) @RequestParam String email,
                                           @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) throws MessagingException {
        mailService.authMailSender(email,userDetails.getUser());
        return new ResponseDto("200","이메일이 전송되었습니다.","");
    }

    @Operation(summary = "이메일 인증 코드 확인")
    @GetMapping("api/user/email/auth/{userId}")
    public ResponseDto recemailCode(@Parameter(description = "인증 코드", in = ParameterIn.QUERY) @RequestParam String code,
                                    @Parameter(description = "프로젝트 ID", in = ParameterIn.PATH) @PathVariable Long userId){
        mailService.emailAuthCodeCheck(code,userId);
        return new ResponseDto("200","인증이 성공적으로 이루어졌습니다.","");
    }
}
