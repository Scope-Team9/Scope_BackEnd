package com.studycollaboproject.scope.service;

import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.repository.ApplicantRepository;
import com.studycollaboproject.scope.repository.PostRepository;
import com.studycollaboproject.scope.repository.TeamRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AssessmentServiceTest {

    @Mock
    PostRepository postRepository;

    @Mock
    TeamRepository teamRepository;

    @Nested
    @DisplayName("평가 테스트")
    class Assessment {
        @Test
        @DisplayName("동료평가")
        void 동료평가() {

            //given

            //when

            //then
        }
    }
}