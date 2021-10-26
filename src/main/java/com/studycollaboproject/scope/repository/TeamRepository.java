package com.studycollaboproject.scope.repository;

import com.studycollaboproject.scope.model.Team;
import com.studycollaboproject.scope.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team,Long> {
    List<Team> findAllByUser(User user);
}
