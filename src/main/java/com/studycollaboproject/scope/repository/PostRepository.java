package com.studycollaboproject.scope.repository;


import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByIdAndUser(Long postId, User user);
}
