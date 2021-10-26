package com.studycollaboproject.scope.repository;

import com.studycollaboproject.scope.model.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {
}
