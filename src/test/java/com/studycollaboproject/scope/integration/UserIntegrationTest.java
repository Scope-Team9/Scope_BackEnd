//package com.studycollaboproject.scope.integration;
//
//import com.studycollaboproject.scope.dto.SignupRequestDto;
//import com.studycollaboproject.scope.dto.UserResponseDto;
//import com.studycollaboproject.scope.exception.BadRequestException;
//import com.studycollaboproject.scope.exception.ErrorCode;
//import com.studycollaboproject.scope.model.Post;
//import com.studycollaboproject.scope.model.User;
//import com.studycollaboproject.scope.service.PostService;
//import com.studycollaboproject.scope.service.TestService;
//import com.studycollaboproject.scope.service.UserService;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import javax.transaction.Transactional;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class UserIntegrationTest {
//
//    @Autowired
//    UserService userService;
//
//    @Autowired
//    PostService postService;
//
//    @Autowired
//    TestService testService;
//
//    User user1;
//    List<String> tech = new ArrayList<>();
//    List<String> userPropensityType = new ArrayList<>();
//    List<String> memberPropensityType = new ArrayList<>();
//    String userTestResult;
//    String memberTestResult;
//    String snsId = "snsId1";
//    String email = "user1@mail.com";
//    String nickname = "nickname1";
//
//    @BeforeAll
//    public void init() {
//        String[] type1 = {"L", "L", "L", "H", "H", "H", "G", "G", "G"};
//        String[] type2 = {"F", "F", "L", "H", "H", "H", "P", "P", "P"};
//        userPropensityType = Arrays.stream(type1).collect(Collectors.toList());
//        memberPropensityType = Arrays.stream(type2).collect(Collectors.toList());
//
//        System.out.println("userPropensityType = " + userPropensityType);
//        System.out.println("memberPropensityType = " + memberPropensityType);
//
//        tech.add("Spring");
//        tech.add("React");
//    }
//
//    @Test
//    @Order(1)
//    @DisplayName("성향 테스트 결과")
//    public void test1() {
//        userTestResult = testService.testResult(userPropensityType);
//        memberTestResult = testService.testResult(memberPropensityType);
//
//        assertThat(userTestResult).isEqualTo("LHG");
//        assertThat(memberTestResult).isEqualTo("RHP");
//    }
//
//    @Test
//    @Order(2)
//    @DisplayName("이메일 중복 확인 - 중복 없음")
//    public void 이메일중복확인() {
//        boolean result = userService.emailCheckByEmail(email);
//        assertThat(result).isTrue();
//    }
//
//    @Test
//    @Order(3)
//    @DisplayName("닉네임 중복 확인 - 중복 없음")
//    public void 닉네임중복확인() {
//        boolean result = userService.nicknameCheckByNickname(nickname);
//        assertThat(result).isTrue();
//    }
//
//    @Test
//    @Order(4)
//    @DisplayName("회원가입")
//    public void 회원가입() {
//        SignupRequestDto signupRequestDto = new SignupRequestDto(
//                snsId, email, nickname, tech, userPropensityType, memberPropensityType);
//
//        this.user1 = new User(signupRequestDto, userTestResult, memberTestResult);
//        String token = userService.createToken(user1);
//        UserResponseDto userResponseDto = userService.saveUser(this.tech, this.user1, token);
//
//        assertThat(userResponseDto.getUserPropensityType()).isEqualTo(userTestResult);
//        assertThat(userResponseDto.getMemberPropensityType()).isEqualTo(memberTestResult);
//        assertThat(userResponseDto.getEmail()).isEqualTo(email);
//        assertThat(userResponseDto.getNickname()).isEqualTo(nickname);
//    }
//
//    @Test
//    @Order(5)
//    @DisplayName("이메일 중복 확인 - 중복")
//    public void 이메일중복() {
//        Assertions.assertThrows(BadRequestException.class, () -> userService.emailCheckByEmail(email), ErrorCode.ALREADY_EMAIL_ERROR.getMessage());
//    }
//
//    @Test
//    @Order(6)
//    @DisplayName("닉네임 중복 확인 - 중복")
//    public void 닉네임중복() {
//        Assertions.assertThrows(BadRequestException.class, () -> userService.nicknameCheckByNickname(nickname), ErrorCode.ALREADY_NICKNAME_ERROR.getMessage());
//    }
//
//    @Test
//    @Order(7)
//    @DisplayName("회원 조회 - SNS ID")
//    @Transactional
//    public void 회원조회_snsId() {
//        User user = userService.loadUserBySnsId(snsId);
//
//        assertThat(user.getSnsId()).isEqualTo(snsId);
//        assertThat(user.getEmail()).isEqualTo(email);
//        assertThat(user.getNickname()).isEqualTo(nickname);
//        assertThat(user.getUserPropensityType()).isEqualTo(userTestResult);
//        assertThat(user.getMemberPropensityType()).isEqualTo(memberTestResult);
//        List<String> collect = user.getTechStackList().stream().map(o -> o.getTech().getTech()).collect(Collectors.toList());
//        assertThat(collect).contains("Spring");
//        assertThat(collect).contains("React");
//    }
//
//    @Test
//    @Order(8)
//    @DisplayName("회원 조회 - USER ID")
//    @Transactional
//    public void 회원조회_userId() {
//        User user = userService.loadUserByUserId(2L);
//
//        assertThat(user.getSnsId()).isEqualTo(snsId);
//        assertThat(user.getEmail()).isEqualTo(email);
//        assertThat(user.getNickname()).isEqualTo(nickname);
//        assertThat(user.getUserPropensityType()).isEqualTo(userTestResult);
//        assertThat(user.getMemberPropensityType()).isEqualTo(memberTestResult);
//        List<String> collect = user.getTechStackList().stream().map(o -> o.getTech().getTech()).collect(Collectors.toList());
//        assertThat(collect).contains("Spring");
//        assertThat(collect).contains("React");
//    }
//}
