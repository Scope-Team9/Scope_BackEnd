package com.studycollaboproject.scope.repository;

import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.Tech;
import com.studycollaboproject.scope.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepositoryExtension {

    List<Post> findAllByTechInOrderByCreatedAt(List<Tech> techList);

    List<Post> findAllByTechInOrderByStartDate(List<Tech> techList);

    List<Post> findAllByBookmarkOrderByStartDate(String snsId );

    List<Post> findAllByBookmarkOrderByCreatedAt(String snsId);

    List<Post> findAllByPropensityTypeOrderByStartDate(String propensity);

    List<Post> findAllByPropensityTypeOrderByCreatedAt(String propensity);

    List<Post> findAllByUserSnsId(String snsID);

    List<Post> findAllBookmarkByUserSnsId(String snsId);
}