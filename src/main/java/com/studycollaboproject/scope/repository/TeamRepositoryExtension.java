package com.studycollaboproject.scope.repository;

import com.studycollaboproject.scope.model.Team;
import com.studycollaboproject.scope.model.User;

import java.util.List;

public interface TeamRepositoryExtension {
    List<Team> findAllByUser(User user);
}