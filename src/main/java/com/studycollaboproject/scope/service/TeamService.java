package com.studycollaboproject.scope.service;

import com.studycollaboproject.scope.dto.MemberListResponseDto;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.BadRequestException;
import com.studycollaboproject.scope.exception.ForbiddenException;
import com.studycollaboproject.scope.model.*;
import com.studycollaboproject.scope.repository.ApplicantRepository;
import com.studycollaboproject.scope.repository.TeamRepository;
import com.studycollaboproject.scope.webSocket.AlertService;
import com.studycollaboproject.scope.webSocket.AlertType;
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
    private final AlertService alertService;
    @Transactional
    public void acceptMember(Post post, User user, Boolean accept) {
        // [예외처리] 대기열에서 신청자의 정보를 찾을 수 없을 때
        Applicant applicant = applicantRepository.findByUserAndPost(user, post).orElseThrow(
                () -> new BadRequestException(ErrorCode.NO_APPLICANT_ERROR)
        );
        if(accept && post.getTotalMember() == post.getRecruitmentMember()){
            throw new BadRequestException(ErrorCode.NO_EXCEED_MEMBER_ERROR);
        }
        //대기열에서 삭제
        applicant.deleteApply();
        applicantRepository.delete(applicant);

        if (accept) {
            Team team = Team.builder()
                    .user(user)
                    .post(post)
                    .build();
            teamRepository.save(team);

            // 알림 저장
            AlertType alertType = AlertType.MATCH_TEAM;
            alertService.saveAlert(post.getUser().getNickname(),alertType, team.getId(), user);

            // 알림 소켓으로 보내기
            alertService.alertToUser(user, post.getUser().getNickname() + "님의 팀에 지원이 승낙되었습니다.");
        }

    }

    @Transactional
    public List<MemberListResponseDto> getMember(Long postId) {
        return teamRepository.findAllByPostId(postId)
                .stream().map(MemberListResponseDto::new).collect(Collectors.toCollection(ArrayList::new));
    }

    public void memberDelete(User user, Post post) {
        if (post.getUser().getId().equals(user.getId())) {
            throw new BadRequestException(ErrorCode.NOT_AVAILABLE_ACCESS);
        }
        else if (post.getProjectStatus().equals(ProjectStatus.PROJECT_STATUS_RECRUITMENT)) {
            Team team = teamRepository.findByUserAndPost(user, post).orElseThrow(
                    () -> new ForbiddenException(ErrorCode.NO_TEAM_ERROR)
            );
            team.deleteTeam();
            teamRepository.deleteByUserAndPost(user, post);
        } else throw new BadRequestException(ErrorCode.NO_RECRUITMENT_ERROR);
    }

    public boolean isMember(Post post, User user) {
        return teamRepository.findByUserAndPost(user,post).isPresent();

    }
}
