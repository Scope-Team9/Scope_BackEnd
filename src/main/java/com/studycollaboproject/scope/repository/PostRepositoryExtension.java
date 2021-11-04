package com.studycollaboproject.scope.repository;

import com.studycollaboproject.scope.model.Bookmark;
import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.Tech;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface PostRepositoryExtension {

    List<Post> findAllOrderByCreatedAt(Pageable pageable);

    List<Post> findAllByTechInOrderByCreatedAt(List<Tech> techList, Pageable pageable);

    List<Post> findAllByTechInOrderByStartDate(List<Tech> techList, Pageable pageable);

    List<Post> findAllByBookmarkOrderByStartDate(String snsId, Pageable pageable);

    List<Post> findAllByBookmarkOrderByCreatedAt(String snsId, Pageable pageable);

    List<Post> findAllByPropensityTypeOrderByStartDate(String propensity);

    List<Post> findAllByPropensityTypeOrderByCreatedAt(String propensity);
}