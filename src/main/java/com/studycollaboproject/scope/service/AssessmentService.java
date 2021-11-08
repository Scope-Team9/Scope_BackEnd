package com.studycollaboproject.scope.service;

import com.studycollaboproject.scope.dto.MailDto;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.RestApiException;
import com.studycollaboproject.scope.model.*;
import com.studycollaboproject.scope.repository.PostRepository;
import com.studycollaboproject.scope.repository.TeamRepository;
import com.studycollaboproject.scope.repository.TotalResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssessmentService {

    private final PostRepository postRepository;
    private final TeamRepository teamRepository;
    private final TotalResultRepository totalResultRepository;

    @Transactional
    public MailDto assessmentMember(Long postId, User rater, List<Long> userIds) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RestApiException(ErrorCode.NO_POST_ERROR)
        );// [예외처리] 팀장이 아니면서 프로젝트 상태가 "동료"가 아닌 경우
        if (!post.getUser().equals(rater)&&!post.getProjectStatus().equals(ProjectStatus.PROJECT_STATUS_END)){
            throw new RestApiException(ErrorCode.NO_AUTHORIZATION_ERROR);
        }

        List<Team> teamList = teamRepository.findAllByPost(post);
        List<String> userTypeList = new ArrayList<>();
        List<User> userList=new ArrayList<>();
        Long teamUserId;

        for (Long userId : userIds) {
            for (Team team : teamList) {
                if (team.getUser().equals(rater)) {
                    if (team.isAssessment()) {
                        throw new RestApiException(ErrorCode.ALREADY_ASSESSMENT_ERROR);
                    }
                    team.setAssessment();
                }
                team.getPost().updateStatus("종료");
                teamUserId = team.getUser().getId();
                if (teamUserId.equals(userId) && !teamUserId.equals(rater.getId())) {
                    userList.add(team.getUser());
                    userTypeList.add(team.getUser().getUserPropensityType());
                    break;
                }
            }
        }
        String raterType = rater.getUserPropensityType();
        getAssessmentResult(raterType, userTypeList);
        return new MailDto(userList,post);

    }

    // 성향 추천 결과 테이블에 저장
    public void getAssessmentResult(String rater, List<String> userList) {

        for (String member : userList) {
            TotalResult totalResult = totalResultRepository.findByUserTypeAndMemberType(rater, member);
            Long result = totalResult.getResult() + 1L;
            totalResult.setResult(result);
        }

    }


}
