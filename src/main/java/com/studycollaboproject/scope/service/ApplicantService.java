package com.studycollaboproject.scope.service;

import com.studycollaboproject.scope.model.Applicant;
import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.User;
import com.studycollaboproject.scope.repository.ApplicantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicantService {
    private final ApplicantRepository applicantRepository;

    public void applyPost(Post post, User user, String comment) {
        Applicant applicant = Applicant.builder()
                .post(post)
                .user(user)
                .comment(comment)
                .build();
        applicantRepository.save(applicant);
    }
}
