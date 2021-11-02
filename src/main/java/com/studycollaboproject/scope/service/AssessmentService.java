package com.studycollaboproject.scope.service;

import com.studycollaboproject.scope.dto.ResponseDto;
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
    public ResponseDto assessmentMember(Long postId, User rater, List<Long> userIds) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RestApiException(ErrorCode.NO_POST_ERROR)
        );
        if (!post.getUser().equals(rater)&&!post.getProjectStatus().equals(ProjectStatus.PROJECT_STATUS_END)){
            throw new RestApiException(ErrorCode.NO_AUTHORIZATION_ERROR);
        }
        List<Team> teamList = teamRepository.findAllByPost(post);
        List<String> userList = new ArrayList<>();
        Long teamUserId;
        for (Long userId : userIds) {
            for (Team team : teamList) {

                if (team.getUser().equals(rater)) {
                    if (team.isAssement()) {
                        throw new RestApiException(ErrorCode.ALREADY_ASSESSMENT_ERROR);
                    }
                    team.setAssement();

                }

                team.getPost().updateStatus("종료");
                teamUserId = team.getUser().getId();
                if (teamUserId.equals(userId) && !teamUserId.equals(rater.getId())) {
                    userList.add(team.getUser().getUserPropensityType());
                    break;
                }
            }
        }

        String raterType = rater.getUserPropensityType();
        return getAssessmentResult(raterType, userList);

    }


    public ResponseDto getAssessmentResult(String rater, List<String> userList) {

        for (String member : userList) {
            TotalResult totalResult = totalResultRepository.findByUserTypeAndMemberType(rater, member);
            Long result = totalResult.getResult() + 1L;
            totalResult.setResult(result);
        }
        return new ResponseDto("200", "추천 결과가 저장되었습니다.", "");
    }


}
