package com.studycollaboproject.scope.repository;

import com.studycollaboproject.scope.model.Tech;
import com.studycollaboproject.scope.model.TechStack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TechStackRepository extends JpaRepository<TechStack, Long> {


    List<TechStack> findAllByTechIn(List<Tech> Tech);

}
