package com.studycollaboproject.scope.repository;

import com.studycollaboproject.scope.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findAllByPostId(Long postId);
}
