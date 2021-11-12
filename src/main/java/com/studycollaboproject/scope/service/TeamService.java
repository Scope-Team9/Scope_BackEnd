package com.studycollaboproject.scope.service;

import com.studycollaboproject.scope.dto.MemberListResponseDto;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.BadRequestException;
import com.studycollaboproject.scope.model.*;
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
    public void acceptMember(Post post, User user, Boolean accept) {
        // [예외처리] 대기열에서 신청자의 정보를 찾을 수 없을 때
        Applicant applicant = applicantRepository.findByUserAndPost(user, post).orElseThrow(
                () -> new BadRequestException(ErrorCode.NO_APPLICANT_ERROR)
        );
        //대기열에서 삭제
        applicant.deleteApply();
        applicantRepository.delete(applicant);

        if (accept) {
            Team team = Team.builder()
                    .user(user)
                    .post(post)
                    .build();
            teamRepository.save(team);
        }

    }

    @Transactional
    public List<MemberListResponseDto> getMember(Long postId) {
        return teamRepository.findAllByPostId(postId)
                .stream().map(MemberListResponseDto::new).collect(Collectors.toCollection(ArrayList::new));
    }

    public void memberResignation(User member, Post post) {
        if (post.getProjectStatus().equals(ProjectStatus.PROJECT_STATUS_RECRUITMENT)) {
            teamRepository.deleteByUserAndPost(member, post);
        } else throw new BadRequestException(ErrorCode.NO_RECRUITMENT_ERROR);
    }

    public void memberSecession(User user, Post post) {

        if (post.getProjectStatus().equals(ProjectStatus.PROJECT_STATUS_RECRUITMENT)) {
            teamRepository.deleteByUserAndPost(user, post);
        } else throw new BadRequestException(ErrorCode.NO_RECRUITMENT_ERROR);

    }

    public boolean isMemeber(Post post, User user) {
        return teamRepository.findByUserAndPost(user,post).isPresent();

    }
}
