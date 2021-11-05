package com.studycollaboproject.scope.repository;

import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.Tech;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepositoryExtension {

    List<Post> findAllOrderByCreatedAt(Pageable pageable);

    List<Post> findAllByTechInOrderByCreatedAt(List<Tech> techList, Pageable pageable);

    List<Post> findAllByTechInOrderByStartDate(List<Tech> techList, Pageable pageable);

    List<Post> findAllByBookmarkOrderByStartDate(String snsId, Pageable pageable);

    List<Post> findAllByBookmarkOrderByCreatedAt(String snsId, Pageable pageable);

    List<Post> findAllByPropensityTypeOrderByStartDate(String propensity);

    List<Post> findAllByPropensityTypeOrderByCreatedAt(String propensity);
}