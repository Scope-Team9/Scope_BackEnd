package com.studycollaboproject.scope.service;

import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.RestApiException;
import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.PropensityType;
import com.studycollaboproject.scope.model.Team;
import com.studycollaboproject.scope.model.User;
import com.studycollaboproject.scope.repository.PostRepository;
import com.studycollaboproject.scope.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssessmentService {

    private final PostRepository postRepository;
    private final TeamRepository teamRepository;

    public void assessmentMember(Long postId, User user, List<Long> userIds) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RestApiException(ErrorCode.NO_POST_ERROR)
        );
        List<Team> member = teamRepository.findAllByPost(post);

        for (int i = 0; i < member.size(); i++) {
            if(member.get(i).getUser().getId().equals(userIds.get(i))){
                userAssessment(user.getUserPropensityType(), member.get(i).getUser().getUserPropensityType());    //user -> member.get(i) 추천
            }
        }

    }

    private void userAssessment(PropensityType user, PropensityType member) {
        //user -> member
    }
}
