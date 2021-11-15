package com.studycollaboproject.scope.controller;

import com.studycollaboproject.scope.dto.*;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.ForbiddenException;
import com.studycollaboproject.scope.exception.NoAuthException;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    public ResponseEntity<Object> getMyPage(@Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails,
                                            @Parameter(description = "조회하고자 하는 사용자의 ID", in = ParameterIn.PATH) @PathVariable Long userId) {
        log.info("GET, [{}], /api/user", MDC.get("UUID"));
        User user = userService.loadUserByUserId(userId);

        String snsId = Optional.ofNullable(userDetails).map(UserDetailsImpl::getSnsId).orElse("");
        MypageResponseDto responseDto = postService.getMyPostList(user, snsId);

        return new ResponseEntity<>(
                new ResponseDto("회원 정보 조회 성공", responseDto),
                HttpStatus.OK
        );
    }

    @Operation(summary = "회원 정보 수정")
    @PostMapping("/api/user/{userId}")
    public ResponseEntity<Object> updateUserinfo(@Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                 @RequestBody UserRequestDto userRequestDto, @Parameter(description = "수정하고자 하는 사용자의 ID", in = ParameterIn.PATH) @PathVariable Long userId) {
        log.info("POST, [{}], /api/user, userRequestDto={}", MDC.get("UUID"), userRequestDto);
        if (userId.equals(userDetails.getUser().getId())) {
            UserResponseDto userResponseDto = userService.updateUserInfo(userDetails.getUsername(), userRequestDto);
            return new ResponseEntity<>(
                    new ResponseDto("회원 정보가 수정되었습니다.", userResponseDto),
                    HttpStatus.OK
            );
        }
        // [예외처리] 로그인 정보가 없을 때
        else {
            throw new ForbiddenException(ErrorCode.NO_AUTHORIZATION_ERROR);
        }
    }

    @Operation(summary = "회원 가입 - 회원 정보와 테스트 결과 저장")
    @PostMapping("/api/signup")
    public ResponseEntity<Object> signup(@RequestBody SignupRequestDto signupRequestDto) {
        log.info("POST, [{}], /api/signup, signupRequestDto={}", MDC.get("UUID"), signupRequestDto.toString());

        String userTestResult = testService.testResult(signupRequestDto.getUserPropensityType());
        String memberTestResult = testService.testResult(signupRequestDto.getMemberPropensityType());
        User user = new User(signupRequestDto, userTestResult, memberTestResult);
        String token = userService.createToken(user);
        UserResponseDto userResponseDto = userService.saveUser(signupRequestDto.getTechStack(), user, token);

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("user", userResponseDto);

        return new ResponseEntity<>(
                new ResponseDto("회원가입이 되었습니다.", map),
                HttpStatus.CREATED
        );
    }

    @Operation(summary = "이메일 중복 확인")
    @GetMapping("/api/login/email")
    public ResponseEntity<Object> emailCheck(@Parameter(description = "이메일", in = ParameterIn.QUERY) @RequestParam String email) {
        log.info("GET, [{}], /api/login/email, email={}", MDC.get("UUID"), email);

        return new ResponseEntity<>(
                userService.emailCheckByEmail(email),
                HttpStatus.OK
        );
    }

    @Operation(summary = "닉네임 중복 확인")
    @GetMapping("/api/login/nickname")
    public ResponseEntity<Object> nicknameCheck(@Parameter(description = "닉네임", in = ParameterIn.QUERY) @RequestParam String nickname) {
        log.info("GET, [{}], /api/login/nickname, nickname={}", MDC.get("UUID"), nickname);
        return new ResponseEntity<>(
                userService.nicknameCheckByNickname(nickname),
                HttpStatus.OK
        );
    }

    @Operation(summary = "유저 소개 업데이트")
    @PostMapping("/api/user/{userId}/desc")
    public ResponseEntity<Object> updateUserDesc(@Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                 @RequestBody String introduction, @Parameter(description = "수정하고자 하는 사용자의 ID", in = ParameterIn.PATH) @PathVariable Long userId) {
        log.info("POST, [{}], /api/user/desc, userDesc={}", MDC.get("UUID"), introduction);
        if (userId.equals(userDetails.getUser().getId())) {
            UserResponseDto userResponseDto = userService.updateUserDesc(userDetails.getUsername(), introduction);
            return new ResponseEntity<>(
                    new ResponseDto("회원 소개가 수정되었습니다.", userResponseDto),
                    HttpStatus.OK
            );
        }
        // [예외처리] 로그인 정보가 없을 때
        else throw new ForbiddenException(ErrorCode.NO_AUTHORIZATION_ERROR);

    }


    @Operation(summary = "북마크 추가")
    @PostMapping("/api/bookmark/{postId}")
    public ResponseEntity<Object> bookmarkCheck(@Parameter(description = "프로젝트 ID", in = ParameterIn.PATH) @PathVariable Long postId,
                                                @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("POST, [{}], /api/bookmark/{}", MDC.get("UUID"), postId);

        if (userDetails.getUser() == null) {
            throw new NoAuthException(ErrorCode.NO_AUTHENTICATION_ERROR);
        }
        return new ResponseEntity<>(
                userService.bookmarkCheck(postId, userDetails.getSnsId()),
                HttpStatus.CREATED
        );
    }

    @Operation(summary = "회원 탈퇴")
    @DeleteMapping("api/user/{userId}")
    public ResponseEntity<Object> deleteUser(@Parameter(description = "탈퇴하려는 회원 ID", in = ParameterIn.PATH) @PathVariable Long userId,
                                             @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("POST, [{}], /api/user/{}", MDC.get("UUID"), userId);
        if (userDetails.getUser().getId().equals(userId)) {
            return new ResponseEntity<>(
                    userService.deleteUser(userDetails.getUser()),
                    HttpStatus.OK
            );
        }
        // [예외처리] 로그인 정보가 없을 때
        else {
            throw new ForbiddenException(ErrorCode.NO_AUTHORIZATION_ERROR);
        }
    }

    @Operation(summary = "이메일 인증 전송")
    @GetMapping("api/user/email")
    public ResponseEntity<Object> emailAuthentication(@Parameter(description = "이메일", in = ParameterIn.QUERY) @RequestParam String email,
                                                      @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) throws MessagingException {
        mailService.authMailSender(email, userDetails.getUser());
        return new ResponseEntity<>(
                new ResponseDto("이메일이 전송되었습니다.", ""),
                HttpStatus.OK
        );
    }

    @Operation(summary = "이메일 인증 코드 확인")
    @GetMapping("api/user/email/auth/{userId}")
    public ResponseEntity<Object> recEmailCode(@Parameter(description = "인증 코드", in = ParameterIn.QUERY) @RequestParam String code,
                                               @Parameter(description = "프로젝트 ID", in = ParameterIn.PATH) @PathVariable Long userId) {
        mailService.emailAuthCodeCheck(code, userId);
        return new ResponseEntity<>(
                new ResponseDto("인증이 성공적으로 이루어졌습니다.", ""),
                HttpStatus.OK
        );
    }
}
