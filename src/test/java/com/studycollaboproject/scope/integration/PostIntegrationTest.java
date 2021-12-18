package com.studycollaboproject.scope.integration;

import com.studycollaboproject.scope.dto.PostRequestDto;
import com.studycollaboproject.scope.dto.PostResponseDto;
import com.studycollaboproject.scope.dto.SignupRequestDto;
import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.ProjectStatus;
import com.studycollaboproject.scope.model.User;
import com.studycollaboproject.scope.service.PostService;
import com.studycollaboproject.scope.service.TestService;
import com.studycollaboproject.scope.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class PostIntegrationTest {

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @Autowired
    TestService testService;

    String title = "제목입니다.";
    String contents = "컨텐츠입니다.";
    Integer totalMember = 10;
    List<String> tech = new ArrayList<>();
    String projectStatus = "진행중";
    String chatUrl = "https://openchatUrl";
    Timestamp startDate = Timestamp.valueOf(LocalDateTime.now());
    Timestamp endDate = Timestamp.valueOf(LocalDateTime.now().plusDays(28));
    User user1;

    Post post;

    @BeforeEach
    void init() {
        String[] type1 = {"L", "L", "L", "H", "H", "H", "G", "G", "G"};
        String[] type2 = {"F", "F", "L", "H", "H", "H", "P", "P", "P"};
        List<String> userPropensityType = Arrays.stream(type1).collect(Collectors.toList());
        List<String> memberPropensityType = Arrays.stream(type2).collect(Collectors.toList());
        tech.add("Spring");
        tech.add("React");
        String snsId = "snsId2";
        String nickname = "nickname2";
        SignupRequestDto signupRequestDto = new SignupRequestDto(
                snsId, nickname, tech, userPropensityType, memberPropensityType);

        String userTestResult = testService.testResult(userPropensityType);
        String memberTestResult = testService.testResult(memberPropensityType);
        this.user1 = new User(signupRequestDto, userTestResult, memberTestResult);
        userService.saveUser(tech, this.user1);
    }

    @Test
    @Order(1)
    @DisplayName("Post 저장")
    void writePost() {
        //given

        //when
        PostRequestDto postRequestDto = new PostRequestDto(title, contents, totalMember, projectStatus, startDate, endDate, tech, chatUrl);
        this.post = postService.writePost(postRequestDto, this.user1.getSnsId());
        //then
        Assertions.assertThat(post.getTitle()).isEqualTo(title);
        Assertions.assertThat(post.getContents()).isEqualTo(contents);
        Assertions.assertThat(post.getProjectStatus()).isEqualTo(ProjectStatus.projectStatusOf(projectStatus));
        Assertions.assertThat(post.getTotalMember()).isEqualTo(totalMember);
        Assertions.assertThat(post.getStartDate()).isEqualTo(startDate.toLocalDateTime());
        Assertions.assertThat(post.getEndDate()).isEqualTo(endDate.toLocalDateTime());
    }

    @Test
    @Order(2)
    @DisplayName("Post 읽어오기")
    void readPost() {
        //given
        PostRequestDto postRequestDto = new PostRequestDto(title, contents, totalMember, projectStatus, startDate, endDate, tech, chatUrl);
        this.post = postService.writePost(postRequestDto, this.user1.getSnsId());
        //when
        List<PostResponseDto> createdAt = postService.readPost("", "createdAt", "", "");
        this.user1 = userService.loadUserBySnsId(user1.getSnsId());
        //then
        Assertions.assertThat(createdAt.size()).isEqualTo(1);
        Assertions.assertThat(createdAt.get(0).getTitle()).isEqualTo(title);
        Assertions.assertThat(createdAt.get(0).getContents()).isEqualTo(contents);
        Assertions.assertThat(createdAt.get(0).getProjectStatus()).isEqualTo(projectStatus);
        Assertions.assertThat(createdAt.get(0).getTotalMember()).isEqualTo(totalMember);
        Assertions.assertThat(createdAt.get(0).getStartDate()).isEqualTo(startDate.toLocalDateTime().toLocalDate());
        Assertions.assertThat(createdAt.get(0).getEndDate()).isEqualTo(endDate.toLocalDateTime().toLocalDate());
        Assertions.assertThat(createdAt.get(0).getNickname()).isEqualTo(user1.getNickname());
        Assertions.assertThat(createdAt.get(0).getTechStack()).contains(tech.get(0));
        Assertions.assertThat(createdAt.get(0).getTechStack()).contains(tech.get(1));
        Assertions.assertThat(createdAt.get(0).getUserId()).isEqualTo(user1.getId());
        Assertions.assertThat(createdAt.get(0).getRecruitmentMember()).isEqualTo(1);
        Assertions.assertThat(createdAt.get(0).getChatUrl()).isEqualTo(chatUrl);

        int days = Period.between(post.getStartDate().toLocalDate(), post.getEndDate().toLocalDate()).getDays();
        String period = ((days - 1) / 7 + 1) + "주";
        Assertions.assertThat(createdAt.get(0).getPeriod()).isEqualTo(period);
    }


    @Test
    @Order(3)
    @DisplayName("Post 수정하기")
    void editPost() {
        //given
        PostRequestDto postRequestDto = new PostRequestDto(title, contents, totalMember, projectStatus, startDate, endDate, tech, chatUrl);
        this.post = postService.writePost(postRequestDto, this.user1.getSnsId());
        //when
        String editTitle = "수정된 제목입니다.";
        String editContents = "수정된 내용입니다.";
        int editTotalMember = 4;
        String editProjectStatus = "진행중";
        Timestamp editStartDate = Timestamp.valueOf(LocalDateTime.now().plusDays(5));
        Timestamp editEndDate = Timestamp.valueOf(LocalDateTime.now().plusDays(19));
        this.tech.add("Java");
        postRequestDto = new PostRequestDto(editTitle, editContents, editTotalMember, editProjectStatus, editStartDate, editEndDate, tech, chatUrl);
        postService.editPost(this.post.getId(), postRequestDto, user1.getSnsId());
        this.post = postService.loadPostByPostId(post.getId());
        //then
        Assertions.assertThat(this.post.getTitle()).isEqualTo(editTitle);
        Assertions.assertThat(this.post.getContents()).isEqualTo(editContents);
        Assertions.assertThat(this.post.getProjectStatus()).isEqualTo(ProjectStatus.projectStatusOf(editProjectStatus));
        Assertions.assertThat(this.post.getTotalMember()).isEqualTo(editTotalMember);
        Assertions.assertThat(this.post.getStartDate()).isEqualTo(editStartDate.toLocalDateTime());
        Assertions.assertThat(this.post.getEndDate()).isEqualTo(editEndDate.toLocalDateTime());
        Assertions.assertThat(this.post.getRecruitmentMember()).isEqualTo(1);
        Assertions.assertThat(this.post.getChatUrl()).isEqualTo(chatUrl);
    }
}
