//package com.studycollaboproject.scope.integration;
//
//import com.studycollaboproject.scope.dto.MemberListResponseDto;
//import com.studycollaboproject.scope.dto.PostRequestDto;
//import com.studycollaboproject.scope.dto.SignupRequestDto;
//import com.studycollaboproject.scope.exception.RestApiException;
//import com.studycollaboproject.scope.model.Applicant;
//import com.studycollaboproject.scope.model.Post;
//import com.studycollaboproject.scope.model.User;
//import com.studycollaboproject.scope.service.ApplicantService;
//import com.studycollaboproject.scope.service.PostService;
//import com.studycollaboproject.scope.service.TeamService;
//import com.studycollaboproject.scope.service.UserService;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.TestPropertySource;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@TestPropertySource(locations = "classpath:application-dev.properties")
//public class TeamIntegrationTest {
//
//    @Autowired
//    UserService userService;
//
//    @Autowired
//    ApplicantService applicantService;
//
//    @Autowired
//    TeamService teamService;
//
//    @Autowired
//    PostService postService;
//
//    User user1;
//    User user2;
//    User user3;
//    Post post;
//
//    @Test
//    @Order(1)
//    @DisplayName("회원가입, 프로젝트 작성")
//    public void init() {
//        List<String> tech = new ArrayList<>();
//        tech.add("String");
//        tech.add("React");
//        SignupRequestDto signupRequestDto = new SignupRequestDto("2", "user2@mail.com", "nickname2", tech);
//        this.user1 = new User(signupRequestDto, userTestResult, memberTestResult);
//        this.user1.updateUserPropensityType("LVG");
//        this.user1.updateMemberPropensityType("FHP");
//        userService.signup(this.user1);
//
//        SignupRequestDto signupRequestDto2 = new SignupRequestDto("3", "user3@mail.com", "nickname3", tech);
//        this.user2 = new User(signupRequestDto2, userTestResult, memberTestResult);
//        this.user2.updateUserPropensityType("LHP");
//        this.user2.updateMemberPropensityType("LHG");
//        userService.signup(this.user2);
//
//        SignupRequestDto signupRequestDto3 = new SignupRequestDto("4", "user4@mail.com", "nickname4", tech);
//        this.user3 = new User(signupRequestDto3, userTestResult, memberTestResult);
//        this.user3.updateUserPropensityType("LVP");
//        this.user3.updateMemberPropensityType("FHG");
//        userService.signup(this.user3);
//
//        String title = "title";
//        String summary = "summary";
//        String contents = "contents";
//        String techStack = "Spring;";
//        int totalMember = 6;
//        String projectStatus = "진행중";
//        String startDate = LocalDate.now().toString();
//        String endDate = LocalDate.now().toString();
//
//        PostRequestDto postRequestDto = new PostRequestDto(title, summary, contents, techStack, totalMember, projectStatus, startDate, endDate);
//        this.post = postService.writePost(postRequestDto, this.user3.getSnsId());
//    }
//
//    @Test
//    @Order(2)
//    @DisplayName("모집 지원")
//    void 모집지원() {
//        //given
//        //when
//        Applicant applicant1 = applicantService.applyPost(this.user1.getSnsId(), this.post.getId(), "comment1");
//        Applicant applicant2 = applicantService.applyPost(this.user2.getSnsId(), this.post.getId(), "comment2");
//
//        //then
//        Assertions.assertThat(applicant1.getId()).isEqualTo(1L);
//        Assertions.assertThat(applicant1.getUser().getEmail()).isEqualTo("user2@mail.com");
//        Assertions.assertThat(applicant1.getUser().getNickname()).isEqualTo("nickname2");
//        Assertions.assertThat(applicant1.getComment()).isEqualTo("comment1");
//        Assertions.assertThat(applicant2.getId()).isEqualTo(2L);
//        Assertions.assertThat(applicant2.getComment()).isEqualTo("comment2");
//        Assertions.assertThat(applicant2.getUser().getEmail()).isEqualTo("user3@mail.com");
//        Assertions.assertThat(applicant2.getUser().getNickname()).isEqualTo("nickname3");
//    }
//
//    @Test
//    @Order(3)
//    @DisplayName("모집 지원 실패")
//    void 모집지원실패() {
//        //given
//        //when
//
//        //then
//        org.junit.jupiter.api.Assertions.assertThrows(RestApiException.class, () -> applicantService.applyPost(this.user1.getSnsId(), this.post.getId(), "comment"), "이미 지원한 프로젝트 입니다.");
//    }
//
//    @Test
//    @Order(4)
//    @DisplayName("모집 지원 목록 조회")
//    void 모집지원목록조회() {
//        //given
//
//        //when
//        List<MemberListResponseDto> applicant = applicantService.getApplicant(this.post);
//        System.out.println("Get applicant = " + applicant);
//        //then
//        Assertions.assertThat(applicant.get(0).getNickname()).isEqualTo("nickname2");
//        Assertions.assertThat(applicant.get(1).getNickname()).isEqualTo("nickname3");
//    }
//
//    @Test
//    @Order(5)
//    @DisplayName("모집 지원 취소")
//    void 모집지원취소() {
//        //given
//
//        //when
//        boolean result = applicantService.cancelApply(this.user1.getSnsId(), this.post.getId());
//        //then
//        Assertions.assertThat(result).isTrue();
//
//        List<MemberListResponseDto> applicant = applicantService.getApplicant(this.post);
//        System.out.println("Delete applicant = " + applicant);
//    }
//
//    @Test
//    @Order(6)
//    @DisplayName("모집 지원 승인")
//    void 모집지원승인() {
//        teamService.acceptMember(this.post, this.user2, true);
//        List<MemberListResponseDto> applicant = applicantService.getApplicant(this.post);
//        System.out.println("Remained applicant = " + applicant);
//        Assertions.assertThat(applicant).isEmpty();
//    }
//
//    @Test
//    @Order(7)
//    @DisplayName("팀원 조회")
//    void 팀원조회() {
//        List<MemberListResponseDto> result = teamService.getMember(this.post.getId());
//        System.out.println("Team member = " + result);
//        Assertions.assertThat(result.size()).isEqualTo(1);
//    }
//}
