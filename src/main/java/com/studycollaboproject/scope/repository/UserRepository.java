package com.studycollaboproject.scope.repository;

import com.studycollaboproject.scope.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findBySnsId(String snsId);

    Optional<User> findByNickname(String nickname);

    User findByUserPropensityType(String userPropensityType);
}
