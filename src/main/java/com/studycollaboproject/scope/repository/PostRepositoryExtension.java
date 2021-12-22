package com.studycollaboproject.scope.repository;

import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.Tech;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepositoryExtension {

    List<Post> findAllOrderByModifiedAt();

    List<Post> findAllOrderByStartDate();

    List<Post> findAllByTechInOrderByModifiedAt(List<Tech> techList);

    List<Post> findAllByTechInOrderByStartDate(List<Tech> techList);

    List<Post> findAllByBookmarkOrderByStartDate(String snsId);

    List<Post> findAllByBookmarkOrderByModifiedAt(String snsId);

    List<Long> findAllPostIdListByPropensityType(String propensity, List<Tech> techList, String snsId, Pageable pageable);

    List<Long> findAllPostIdListByBookmark(String snsId, Pageable pageable);

    List<Long> findAllPostIdListOrderByModifiedAt(Pageable pageable);

    List<Long> findAllPostIdListOrderByStartDate(Pageable pageable);

    List<Post> findByInPostIdOrderByModifiedAt(List<Long> postId);

    List<Post> findByInPostIdOrderByStartDate(List<Long> postId);

    List<Post> findAllByPropensityTypeOrderByStartDate(String propensity, List<Tech> techList, String snsId);

    List<Post> findAllByPropensityTypeOrderByModifiedAt(String propensity, List<Tech> techList, String snsId);

    List<Post> findMemberPostByUserSnsId(String snsID);

    List<Post> findReadyPostByUserSnsId(String snsID);

    List<Post> findAllBookmarkByUserSnsId(String snsId);

    List<Post> findAllByKeywordOrderByModifiedAt(String keyword);

    List<Post> findAllByKeywordOrderByStartDate(String keyword);
}