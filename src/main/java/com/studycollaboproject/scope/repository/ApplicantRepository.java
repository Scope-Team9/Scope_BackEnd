package com.studycollaboproject.scope.repository;

import com.studycollaboproject.scope.model.Applicant;
import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {
    Optional<Applicant> findByUserAndPost(User user, Post post);
    void deleteAllByPost(Post post);
    List<Applicant> findAllByPost(Post post);

    void deleteAllByUser(User user);
}
