package com.studycollaboproject.scope.service;

import com.studycollaboproject.scope.dto.MemberListResponseDto;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.RestApiException;
import com.studycollaboproject.scope.model.Applicant;
import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.ProjectStatus;
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
    public Applicant applyPost(String snsId, Long postId, String comment) {

        // [예외처리] 요청한 유저의 정보가 탈퇴 등과 같은 이유로 존재하지 않을 때
        User user = userRepository.findBySnsId(snsId).orElseThrow(
                () -> new RestApiException(ErrorCode.NO_USER_ERROR)
        );
        // [예외처리] 조회하고자 하는 게시물이 삭제 등과 같은 이유로 존재하지 않을 때
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RestApiException(ErrorCode.NO_POST_ERROR)
        );
        // [예외처리] 신청했던 프로젝트에 다시 신청하는 경우
        applicantRepository.findByUserAndPost(user, post).ifPresent(applicant->{
            throw new RestApiException(ErrorCode.ALREADY_APPLY_POST_ERROR);
        });

        // [예외처리] 이미 시작한 프로젝트에 신청할 경우
        if (!post.getProjectStatus().equals(ProjectStatus.PROJECT_STATUS_RECRUITMENT)){
            throw new RestApiException(ErrorCode.ALREADY_STARTED_ERROR);
        }

        Applicant applicant = Applicant.builder()
                .user(user)
                .post(post)
                .comment(comment)
                .build();

        return applicantRepository.save(applicant);
    }

    @Transactional
    public void cancelApply(String snsId, Long postId) {

        // [예외처리] 요청한 유저의 정보가 탈퇴 등과 같은 이유로 존재하지 않을 때
        User user = userRepository.findBySnsId(snsId).orElseThrow(
                () -> new RestApiException(ErrorCode.NO_USER_ERROR)
        );
        // [예외처리] 조회하고자 하는 게시물이 삭제 등과 같은 이유로 존재하지 않을 때
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RestApiException(ErrorCode.NO_POST_ERROR)
        );
        // [예외처리] 대기열에 대기 정보가 없을 때
        Applicant applicant = applicantRepository.findByUserAndPost(user, post).orElseThrow(
                () -> new RestApiException(ErrorCode.NO_APPLICANT_ERROR)
        );

        applicant.deleteApply();
        applicantRepository.delete(applicant);
    }

    @Transactional
    public List<MemberListResponseDto> getApplicant(Post post) {
        return applicantRepository.findAllByPost(post)
                .stream().map(MemberListResponseDto::new).collect(Collectors.toCollection(ArrayList::new));
    }

    public boolean isApplicant(Post post, User user) {
        return applicantRepository.findByUserAndPost(user,post).isPresent();
    }
}
