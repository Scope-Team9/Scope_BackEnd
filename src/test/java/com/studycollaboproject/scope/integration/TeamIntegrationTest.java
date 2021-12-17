package com.studycollaboproject.scope.integration;

import com.studycollaboproject.scope.dto.MemberListResponseDto;
import com.studycollaboproject.scope.dto.PostRequestDto;
import com.studycollaboproject.scope.dto.SignupRequestDto;
import com.studycollaboproject.scope.dto.UserResponseDto;
import com.studycollaboproject.scope.exception.BadRequestException;
import com.studycollaboproject.scope.exception.RestApiException;
import com.studycollaboproject.scope.model.Applicant;
import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.User;
import com.studycollaboproject.scope.service.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TeamIntegrationTest {

    @Autowired
    UserService userService;

    @Autowired
    ApplicantService applicantService;

    @Autowired
    TeamService teamService;

    @Autowired
    PostService postService;

    @Autowired
    TestService testService;

    User user1;
    User user2;
    User user3;
    Post post;

    @Test
    @Order(1)
    @DisplayName("회원가입, 프로젝트 작성")
    public void init() {
        String[] type1 = {"L", "L", "L", "H", "H", "H", "G", "G", "G"};
        String[] type2 = {"F", "F", "L", "H", "H", "H", "P", "P", "P"};
        List<String> userPropensityType = Arrays.stream(type1).collect(Collectors.toList());
        List<String> memberPropensityType = Arrays.stream(type2).collect(Collectors.toList());
        List<String> tech = new ArrayList<>();
        tech.add("String");
        tech.add("React");
        String snsId = "snsId2";
        String nickname = "nickname2";
        SignupRequestDto signupRequestDto = new SignupRequestDto(
                snsId, nickname, tech, userPropensityType, memberPropensityType);

        String userTestResult = testService.testResult(userPropensityType);
        String memberTestResult = testService.testResult(memberPropensityType);
        this.user1 = new User(signupRequestDto, userTestResult, memberTestResult);
        userService.saveUser(tech, this.user1);

        snsId = "snsId3";
        nickname = "nickname3";

        signupRequestDto = new SignupRequestDto(
                snsId, nickname, tech, userPropensityType, memberPropensityType);

        this.user2 = new User(signupRequestDto, userTestResult, memberTestResult);
        userService.saveUser(tech, this.user2);
        this.user2.updateUserPropensityType("LHP");
        this.user2.updateMemberPropensityType("LHG");

        snsId = "snsId4";
        nickname = "nickname4";

        signupRequestDto = new SignupRequestDto(
                snsId, nickname, tech, userPropensityType, memberPropensityType);

        this.user3 = new User(signupRequestDto, userTestResult, memberTestResult);
        userService.saveUser(tech, this.user3);
        this.user3.updateUserPropensityType("LHP");
        this.user3.updateMemberPropensityType("LHG");

        String title = "title";
        String contents = "contents";
        List<String> postTechStack = new ArrayList<>();
        postTechStack.add("Spring");
        int totalMember = 6;
        String projectStatus = "모집중";
        Timestamp startDate = Timestamp.valueOf(LocalDateTime.now());
        Timestamp endDate = Timestamp.valueOf(LocalDateTime.now().plusDays(14L));

        PostRequestDto postRequestDto = new PostRequestDto(title, contents, totalMember, projectStatus, startDate, endDate, postTechStack, "https://openchatUrl");
        this.post = postService.writePost(postRequestDto, this.user3.getSnsId());
    }

    @Test
    @Order(2)
    @DisplayName("모집 지원")
    void 모집지원() {
        //given
        //when
        Applicant applicant1 = applicantService.applyPost(this.user1.getSnsId(), this.post.getId(), "comment1");
        Applicant applicant2 = applicantService.applyPost(this.user2.getSnsId(), this.post.getId(), "comment2");

        //then
        Assertions.assertThat(applicant1.getId()).isEqualTo(1L);
        Assertions.assertThat(applicant1.getUser().getNickname()).isEqualTo("nickname2");
        Assertions.assertThat(applicant1.getComment()).isEqualTo("comment1");
        Assertions.assertThat(applicant2.getId()).isEqualTo(2L);
        Assertions.assertThat(applicant2.getComment()).isEqualTo("comment2");
        Assertions.assertThat(applicant2.getUser().getNickname()).isEqualTo("nickname3");
    }

    @Test
    @Order(3)
    @DisplayName("모집 지원 실패 - 중복 지원")
    void 모집지원실패() {
        //given
        //when

        //then
        org.junit.jupiter.api.Assertions.assertThrows(BadRequestException.class, () -> applicantService.applyPost(this.user1.getSnsId(), this.post.getId(), "comment"), "이미 지원한 프로젝트 입니다.");
    }

    @Test
    @Order(4)
    @DisplayName("모집 지원 목록 조회")
    void 모집지원목록조회() {
        //given

        //when
        List<MemberListResponseDto> applicant = applicantService.getApplicant(this.post);
        System.out.println("Get applicant = " + applicant);
        //then
        Assertions.assertThat(applicant.get(0).getNickname()).isEqualTo("nickname2");
        Assertions.assertThat(applicant.get(1).getNickname()).isEqualTo("nickname3");
    }

    @Test
    @Order(5)
    @DisplayName("모집 지원 취소")
    void 모집지원취소() {
        //given

        //when
        applicantService.cancelApply(this.user1.getSnsId(), this.post.getId());
        //then
        List<MemberListResponseDto> applicant = applicantService.getApplicant(this.post);
        Assertions.assertThat(applicant.size()).isEqualTo(1);
    }

    @Test
    @Order(6)
    @DisplayName("모집 지원 승인")
    void 모집지원승인() {
        teamService.acceptMember(this.post, this.user2, true);
        List<MemberListResponseDto> applicant = applicantService.getApplicant(this.post);
        //지원 목록 비워져있어야함.
        Assertions.assertThat(applicant).isEmpty();
    }

    @Test
    @Order(7)
    @DisplayName("팀원 조회")
    void 팀원조회() {
        List<MemberListResponseDto> result = teamService.getMember(this.post.getId());

        Assertions.assertThat(result.size()).isEqualTo(2);
    }
}
