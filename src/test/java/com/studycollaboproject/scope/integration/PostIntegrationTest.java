//package com.studycollaboproject.scope.integration;
//
//import com.studycollaboproject.scope.dto.PostRequestDto;
//import com.studycollaboproject.scope.model.Post;
//import com.studycollaboproject.scope.model.ProjectStatus;
//import com.studycollaboproject.scope.model.Tech;
//import com.studycollaboproject.scope.service.PostService;
//import com.studycollaboproject.scope.util.TechStackConverter;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.TestPropertySource;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@TestPropertySource(locations="classpath:application-dev.properties")
//public class PostIntegrationTest {
//
//    @Autowired
//    PostService postService;
//
//    String title = "제목입니다.";
//    String summary = "자기소개입니다.";
//    String contents = "컨텐츠입니다.";
//    String techStack = "Spring;React;";
//    Integer totalMember = 10;
//    Integer recruitmentMember = 6;
//    String projectStatus = "진행중";
//    String startDate = "2021-10-27";
//    String endDate = "2021-11-27";
//    Long id = 1L;
//
//    @Test
//    @DisplayName("Post 저장")
//    void writePost(){
//        //given
//        PostRequestDto postRequestDto = new PostRequestDto(
//                title,
//                summary,
//                contents,
//                techStack,
//                totalMember,
//                projectStatus,
//                startDate,
//                endDate
//        );
//        //when
//        Post result = postService.writePost(postRequestDto);
//        //then
//        Assertions.assertThat(result.getTitle()).isEqualTo(title);
//        Assertions.assertThat(result.getSummary()).isEqualTo(summary);
//        Assertions.assertThat(result.getContents()).isEqualTo(contents);
//        Assertions.assertThat(result.getProjectStatus()).isEqualTo(ProjectStatus.projectStatusOf(projectStatus));
//        Assertions.assertThat(result.getTotalMember()).isEqualTo(totalMember);
//        Assertions.assertThat(result.getStartDate()).isEqualTo(startDate);
//        Assertions.assertThat(result.getEndDate()).isEqualTo(endDate);
//    }
//
//    @Test
//    @Order(2)
//    @DisplayName("Post 읽어오기")
//    void readPost(){
//
//    }
//}
