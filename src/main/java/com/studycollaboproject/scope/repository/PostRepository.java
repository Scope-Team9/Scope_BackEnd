package com.studycollaboproject.scope.repository;

import com.studycollaboproject.scope.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByIdAndUserId(Long postId, Long userId);
}
