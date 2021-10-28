package com.studycollaboproject.scope.service;

import com.studycollaboproject.scope.dto.PostRequestDto;
import com.studycollaboproject.scope.dto.SignupRequestDto;
import com.studycollaboproject.scope.model.Applicant;
import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.User;
import com.studycollaboproject.scope.repository.ApplicantRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ApplicantServiceTest {

    @Mock
    ApplicantRepository applicantRepository;

    @Nested
    @DisplayName("지원 테스트")
    class ApplicantTest {
        @Test
        @DisplayName("모집 지원")
        void 모집지원() {
            //given
            PostRequestDto postRequestDto = new PostRequestDto(
                    "title",
                    "summary",
                    "contents",
                    "Java;Spring;",
                    5,
                    2,
                    "모집중",
                    LocalDate.now().toString(),
                    LocalDate.now().toString()
            );
            Post post = new Post(postRequestDto);
            List<String> stackList = new ArrayList<>();
            stackList.add("Spring");
            stackList.add("Java");
            SignupRequestDto signupRequestDto = new SignupRequestDto(
                    "snsId",
                    "email",
                    "nickname",
                    stackList
            );
            User user = new User(signupRequestDto);
            String comment = "comment";

            ApplicantService applicantService = new ApplicantService(applicantRepository);
            Applicant applicant = Applicant.builder()
                    .post(post)
                    .user(user)
                    .comment(comment)
                    .build();
            Mockito.when(applicantRepository.save(applicant)).thenReturn(applicant);
            //when

            //then
            Applicant result = applicantService.applyPost(applicant);

            Assertions.assertThat(result.getUser()).isEqualTo(applicant.getUser());
            Assertions.assertThat(result.getComment()).isEqualTo(applicant.getComment());
            Assertions.assertThat(result.getPost()).isEqualTo(applicant.getPost());
        }

        @Test
        @DisplayName("모집 지원 취소")
        void 지원취소(){
            //given
            PostRequestDto postRequestDto = new PostRequestDto(
                    "title",
                    "summary",
                    "contents",
                    "Java;Spring;",
                    5,
                    2,
                    "모집중",
                    LocalDate.now().toString(),
                    LocalDate.now().toString()
            );
            Post post = new Post(postRequestDto);
            List<String> stackList = new ArrayList<>();
            stackList.add("Spring");
            stackList.add("Java");
            SignupRequestDto signupRequestDto = new SignupRequestDto(
                    "snsId",
                    "email",
                    "nickname",
                    stackList
            );
            User user = new User(signupRequestDto);
            String comment = "comment";

            ApplicantService applicantService = new ApplicantService(applicantRepository);
            Applicant applicant = Applicant.builder()
                    .post(post)
                    .user(user)
                    .comment(comment)
                    .build();
            //when
            Mockito.when(applicantRepository.findByUserAndPost(user, post)).thenReturn(Optional.of(applicant));
            //then
            boolean result = applicantService.cancelApply(user, post);

            Assertions.assertThat(result).isTrue();
        }
    }


}