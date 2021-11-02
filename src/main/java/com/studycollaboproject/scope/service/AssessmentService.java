package com.studycollaboproject.scope.service;

import com.studycollaboproject.scope.dto.ResponseDto;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.RestApiException;
import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.Team;
import com.studycollaboproject.scope.model.TotalResult;
import com.studycollaboproject.scope.model.User;
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
    public ResponseDto assessmentMember(Long postId, User user, List<Long> userIds) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RestApiException(ErrorCode.NO_POST_ERROR)
        );
        List<Team> team = teamRepository.findAllByPost(post);
        List<String> userList = new ArrayList<>();

        for (Long userId : userIds) {
            for (Team team1 : team) {
                Long teamUserId =team1.getUser().getId();
                    if (teamUserId.equals(userId)&&!teamUserId.equals(user.getId())) {
                        userList.add(team1.getUser().getUserPropensityType());
                        break;
                    }

            }
        }

        String rater = user.getUserPropensityType();
        return getAssessmentResult(rater, userList);

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
