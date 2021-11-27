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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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

        Team teamCheck = teamRepository.findByUserAndPost(rater, post).orElseThrow(
                () -> new ForbiddenException(ErrorCode.NO_TEAM_ERROR)
        );
        if (teamCheck.isAssessment()) {
            throw new BadRequestException(ErrorCode.ALREADY_ASSESSMENT_ERROR);
        }

        if (post.getUser().equals(rater) && !post.getProjectStatus().equals(ProjectStatus.PROJECT_STATUS_END)) {
            post.updateStatus("종료");
        }
        if(!post.getProjectStatus().equals(ProjectStatus.PROJECT_STATUS_END)){
            throw new BadRequestException(ErrorCode.NOT_AVAILABLE_ACCESS);
        }
        List<Team> teamList = teamRepository.findAllByPost(post);
        List<String> userTypeList = new ArrayList<>();
        List<User> userList = teamList.stream().map(Team::getUser).collect(Collectors.toList());
        System.out.println("userList = " + userList);

        Set<Long> ids = Set.copyOf(userIds);
        for (Long userId : ids) {
            int index = checkTeamMember(teamList, userId);
            if(index >= 0) {
                userTypeList.add(teamList.get(index).getUser().getUserPropensityType());
            }
            else {
                throw new BadRequestException(ErrorCode.NO_TEAM_ERROR);
            }
        }

        teamCheck.setAssessment();
        String raterType = rater.getUserPropensityType();
        getAssessmentResult(raterType, userTypeList);
        return new MailDto(userList, post);
    }

    private int checkTeamMember(List<Team> teamList, Long userId) {
        for (int i = 0 ;i < teamList.size(); i++){
            if(teamList.get(i).getUser().getId().equals(userId)) {
                return i;
            }
        }
        return -1;
    }
    // 성향 추천 결과 테이블에 저장
    public void getAssessmentResult(String rater, List<String> userList) {

        for (String member : userList) {
            TotalResult totalResult = totalResultRepository.findByUserTypeAndMemberType(rater, member);
            Long result = totalResult.getResult() + 1L;
            totalResult.setResult(result);
        }

    }
    // 성향 추천 결과 테이블에 저장
    @Transactional
    public void testAssessmentResult(String rater, String member,Long count) {
        TotalResult totalResult = totalResultRepository.findByUserTypeAndMemberType(rater,member);
        Long result = totalResult.getResult()+count;
        totalResult.setResult(result);
    }


}
