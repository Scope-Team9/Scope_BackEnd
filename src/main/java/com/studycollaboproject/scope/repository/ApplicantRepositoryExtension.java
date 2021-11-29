package com.studycollaboproject.scope.repository;

import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.Team;
import com.studycollaboproject.scope.model.User;

import java.util.List;

public interface ApplicantRepositoryExtension {
    boolean existsByUserIdAndPostId(Long userId, Long postId);
}