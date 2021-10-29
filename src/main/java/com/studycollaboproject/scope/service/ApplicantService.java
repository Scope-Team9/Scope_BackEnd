package com.studycollaboproject.scope.service;

import com.studycollaboproject.scope.dto.MemberListResponseDto;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.RestApiException;
import com.studycollaboproject.scope.model.Applicant;
import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.User;
import com.studycollaboproject.scope.repository.ApplicantRepository;
import com.studycollaboproject.scope.repository.PostRepository;
import com.studycollaboproject.scope.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public Applicant applyPost(String nickname, Long postId, String comment) {
        User user = userRepository.findByNickname(nickname).orElseThrow(
                () -> new RestApiException(ErrorCode.NO_USER_ERROR)
        );
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RestApiException(ErrorCode.NO_POST_ERROR)
        );

        if(applicantRepository.findByUserAndPost(user, post).isPresent()){
            throw new RestApiException(ErrorCode.ALREADY_APPLY_POST_ERROR);
        }

        Applicant applicant = Applicant.builder()
                .user(user)
                .post(post)
                .comment(comment)
                .build();
        return applicantRepository.save(applicant);
    }

    @Transactional
    public boolean cancelApply(String nickname, Long postId) {
        User user = userRepository.findByNickname(nickname).orElseThrow(
                () -> new RestApiException(ErrorCode.NO_USER_ERROR)
        );
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RestApiException(ErrorCode.NO_POST_ERROR)
        );

        Applicant applicant = applicantRepository.findByUserAndPost(user, post).orElseThrow(
                () -> new RestApiException(ErrorCode.NO_APPLICANT_ERROR)
        );
        applicant.deleteApply();
        applicantRepository.delete(applicant);

        return true;
    }

    @Transactional
    public List<MemberListResponseDto> getApplicant(Post post) {
        return applicantRepository.findAllByPost(post)
                .stream().map(MemberListResponseDto::new).collect(Collectors.toCollection(ArrayList::new));
    }
}
