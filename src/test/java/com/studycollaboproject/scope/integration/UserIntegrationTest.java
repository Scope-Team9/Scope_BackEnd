package com.studycollaboproject.scope.integration;

import com.studycollaboproject.scope.service.PostService;
import com.studycollaboproject.scope.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "classpath:application-dev.properties")
public class UserIntegrationTest {

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;


    @Test
    @Order(1)
    @DisplayName("이메일 중복 확인")
    public void 이메일중복확인() {

//        userService.emailCheckByUser("user@mail.com", );
    }

    @Test
    @Order(2)
    @DisplayName("북마크 등록")
    public void 북마크등록() {

    }

    @Test
    @Order(3)
    @DisplayName("북마크 해제")
    public void 북마크해제() {

    }
}
