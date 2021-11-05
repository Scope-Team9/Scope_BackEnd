package com.studycollaboproject.scope.service;

import com.studycollaboproject.scope.dto.MemberListResponseDto;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.RestApiException;
import com.studycollaboproject.scope.model.Applicant;
import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.Team;
import com.studycollaboproject.scope.model.User;
import com.studycollaboproject.scope.repository.ApplicantRepository;
import com.studycollaboproject.scope.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final ApplicantRepository applicantRepository;

    @Transactional
    public Team acceptMember(Post post, User user, Boolean accept) {
        // [예외처리] 대기열에서 신청자의 정보를 찾을 수 없을 때
        Applicant applicant = applicantRepository.findByUserAndPost(user, post).orElseThrow(
                () -> new RestApiException(ErrorCode.NO_APPLICANT_ERROR)
        );
        //대기열에서 삭제
        applicant.deleteApply();
        applicantRepository.delete(applicant);

        if(accept){
            Team team = Team.builder()
                    .user(user)
                    .post(post)
                    .build();
            return teamRepository.save(team);
        }
        return null;
    }

    @Transactional
    public List<MemberListResponseDto> getMember(Long postId) {
        return teamRepository.findAllByPostId(postId)
                .stream().map(MemberListResponseDto::new).collect(Collectors.toCollection(ArrayList::new));
    }

    public void memberResignation(User member,Post post) {
        teamRepository.deleteByUserAndPost(member,post);
    }

    public void memberSecession(User user, Post post) {
        teamRepository.deleteByUserAndPost(user,post);
    }
}
