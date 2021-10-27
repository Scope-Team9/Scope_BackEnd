package com.studycollaboproject.scope.service;

import com.studycollaboproject.scope.dto.MemberListResponseDto;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.RestApiException;
import com.studycollaboproject.scope.model.Applicant;
import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.User;
import com.studycollaboproject.scope.repository.ApplicantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional
    public void cancelApply(User user, Post post) {
        Applicant applicant = applicantRepository.findByUserAndPost(user, post).orElseThrow(
                () -> new RestApiException(ErrorCode.NO_APPLICANT_ERROR)
        );
        applicant.deleteApply();
        applicantRepository.delete(applicant);
    }

    public List<MemberListResponseDto> getApplicant(Post post) {
        return applicantRepository.findAllByPost(post)
                .stream().map(MemberListResponseDto::new).collect(Collectors.toCollection(ArrayList::new));
    }
}
