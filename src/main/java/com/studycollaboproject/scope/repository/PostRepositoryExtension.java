package com.studycollaboproject.scope.repository;

import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.Tech;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepositoryExtension {

    List<Post> findAllByTechInOrderByCreatedAt(List<Tech> techList);

    List<Post> findAllByTechInOrderByStartDate(List<Tech> techList);

    List<Post> findAllByBookmarkOrderByStartDate(String snsId );

    List<Post> findAllByBookmarkOrderByCreatedAt(String snsId);

    List<Post> findAllByPropensityTypeOrderByStartDate(String propensity, List<Tech> techList, String snsId);

    List<Post> findAllByPropensityTypeOrderByCreatedAt(String propensity, List<Tech> techList, String snsId);

    List<Post> findMemberPostByUserSnsId(String snsID);

    List<Post> findReadyPostByUserSnsId(String snsID);

    List<Post> findAllBookmarkByUserSnsId(String snsId);

    List<Post> findAllByKeywordOrderByCreatedAt(String keyword, Pageable pageable);

    List<Post> findAllByKeywordOrderByStartDate(String keyword, Pageable pageable);

    Long countByKeyword(String keyword);
}