package com.studycollaboproject.scope.repository;

import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.Team;
import com.studycollaboproject.scope.model.User;

import java.util.List;

public interface TeamRepositoryExtension {
    List<Team> findAllByUser(User user);

    List<Team> findAssessmentTeamMember(Post post, List<Long> userIds);

    List<Team> findAllByPostId(Long postId);
}