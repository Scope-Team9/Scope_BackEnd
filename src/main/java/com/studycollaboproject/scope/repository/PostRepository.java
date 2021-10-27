package com.studycollaboproject.scope.repository;


import com.studycollaboproject.scope.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreatedAtDesc();

    Optional<Post> findByIdAndUserId(Long postId, Long userId);
}
