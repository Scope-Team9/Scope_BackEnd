package com.studycollaboproject.scope.repository;

import com.studycollaboproject.scope.model.Tech;
import com.studycollaboproject.scope.model.TechStack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TechStackRepository extends JpaRepository<TechStack, Long> {

    TechStack findbyTech(Tech tech);
    List<TechStack> findAllByTechIn(@Param("Tech")List<Tech> Tech);

}
