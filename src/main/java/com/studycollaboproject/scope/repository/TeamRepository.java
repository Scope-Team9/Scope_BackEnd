package com.studycollaboproject.scope.repository;

import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.Team;
import com.studycollaboproject.scope.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findAllByPostId(Long postId);

    List<Team> findAllByUser(User user);

    Optional<Team> findByUserAndPost(User user, Post post);

    List<Team> findAllByPost(Post post);

    void deleteAllByPost(Post post);

    void deleteAllByUser(User user);
}
