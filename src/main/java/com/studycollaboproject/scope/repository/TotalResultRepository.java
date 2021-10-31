package com.studycollaboproject.scope.repository;

import com.studycollaboproject.scope.model.TotalResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TotalResultRepository extends JpaRepository<TotalResult,Long> {
    TotalResult findByUserTypeAndMemberType(String userType,String memberType);
    List<TotalResult> findAllByUserType(String userType);
}
