package com.studycollaboproject.scope.integration;

import com.studycollaboproject.scope.dto.PostRequestDto;
import com.studycollaboproject.scope.dto.SignupRequestDto;
import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.User;
import com.studycollaboproject.scope.service.PostService;
import com.studycollaboproject.scope.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "classpath:application-dev.properties")
public class UserIntegrationTest {

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    User user1;
    Post post;

    @Test
    @Order(1)
    @DisplayName("회원가입, 프로젝트 작성")
    public void init() {
        List<String> tech = new ArrayList<>();
        tech.add("String");
        tech.add("React");
        SignupRequestDto signupRequestDto = new SignupRequestDto("5", "user5@mail.com", "nickname5", tech);
        this.user1 = new User(signupRequestDto);
        this.user1.updateUserPropensityType("LVG");
        this.user1.updateMemberPropensityType("FHP");
        userService.signup(this.user1);

        String title = "title";
        String summary = "summary";
        String contents = "contents";
        String techStack = "Spring;";
        int totalMember = 6;
        String projectStatus = "진행중";
        String startDate = LocalDate.now().toString();
        String endDate = LocalDate.now().toString();

        PostRequestDto postRequestDto = new PostRequestDto(title, summary, contents, techStack, totalMember, projectStatus, startDate, endDate);
        this.post = postService.writePost(postRequestDto, this.user1.getSnsId());
    }

    @Test
    @Order(2)
    @DisplayName("이메일 중복 확인")
    public void 이메일중복확인() {

        boolean result = userService.emailCheckByEmail("user5@mail.com");
        Assertions.assertThat(result).isTrue();
    }

    @Test
    @Order(3)
    @DisplayName("북마크 등록")
    public void 북마크등록() {
        userService.bookmarkCheck(this.post.getId(), this.user1.getSnsId());
        User user = userService.loadUserBySnsId(this.user1.getSnsId());
        List<Post> bookmarkList = userService.getBookmarkList(user);
        Assertions.assertThat(bookmarkList.size()).isEqualTo(1);
    }

    @Test
    @Order(4)
    @DisplayName("북마크 해제")
    public void 북마크해제() {
        userService.bookmarkCheck(this.post.getId(), this.user1.getSnsId());
        User user = userService.loadUserBySnsId(this.user1.getSnsId());
        List<Post> bookmarkList = userService.getBookmarkList(user);
        Assertions.assertThat(bookmarkList.size()).isEqualTo(0);
    }
}
