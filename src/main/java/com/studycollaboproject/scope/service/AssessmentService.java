package com.studycollaboproject.scope.service;

import com.studycollaboproject.scope.dto.MailDto;
import com.studycollaboproject.scope.exception.BadRequestException;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.ForbiddenException;
import com.studycollaboproject.scope.model.*;
import com.studycollaboproject.scope.repository.PostRepository;
import com.studycollaboproject.scope.repository.TeamRepository;
import com.studycollaboproject.scope.repository.TotalResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssessmentService {

    private final PostRepository postRepository;
    private final TeamRepository teamRepository;
    private final TotalResultRepository totalResultRepository;

    @Transactional
    public MailDto assessmentMember(Long postId, User rater, List<Long> userIds) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new BadRequestException(ErrorCode.NO_POST_ERROR)
        );// [예외처리] 팀장이 아니면서 프로젝트 상태가 "진행중"가 아닌 경우

        // 팀원 평가 여부 체크
        Team teamCheck = teamRepository.findByUserAndPost(rater, post).orElseThrow(
                () -> new ForbiddenException(ErrorCode.NO_TEAM_ERROR)
        );
        if (teamCheck.isAssessment()) {
            throw new BadRequestException(ErrorCode.ALREADY_ASSESSMENT_ERROR);
        }

        // 프로젝트 게시자의 경우 진행중일 경우 평가하고 프로젝트가 종료된다.
        if (post.getUser().equals(rater) && !post.getProjectStatus().equals(ProjectStatus.PROJECT_STATUS_END)) {
            post.updateStatus("종료");
        }

        // 프로젝트 종료가 아니면 평가할 수 없다
        if(!post.getProjectStatus().equals(ProjectStatus.PROJECT_STATUS_END)){
            throw new BadRequestException(ErrorCode.NOT_AVAILABLE_ACCESS);
        }
        // 프로젝트 추천 팀원 조회
        List<Team> teamList = teamRepository.findAssessmentTeamMember(post, userIds);
        List<String> userTypeList = teamList.stream().map(o -> o.getUser().getUserPropensityType()).collect(Collectors.toList());

        totalResultRepository.updateAssessmentResult(rater.getUserPropensityType(), userTypeList);
        List<User> userList = teamRepository.findAllByPostId(postId).stream().map(Team::getUser).collect(Collectors.toList());

        teamCheck.setAssessment();

        return new MailDto(userList, post);
    }

    // 성향 추천 결과 테이블에 저장
    @Transactional
    public void testAssessmentResult(String rater, String member,Long count) {
        TotalResult totalResult = totalResultRepository.findByUserTypeAndMemberType(rater,member);
        Long result = totalResult.getResult()+count;
        totalResult.setResult(result);
    }
}
