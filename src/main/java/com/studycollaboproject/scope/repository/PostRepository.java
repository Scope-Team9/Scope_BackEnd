package com.studycollaboproject.scope.repository;


import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.ProjectStatus;
import com.studycollaboproject.scope.model.Tech;
import com.studycollaboproject.scope.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryExtension {
    Optional<Post> findByIdAndUser(Long postId, User user);
    
    List<Post> findAllByUserMemberPropensityTypeAndProjectStatusAndUserSnsIdIsNotAndTechStackList_TechInOrderByStartDate(String memberPropensityType, ProjectStatus projectStatus, String snsId, List<Tech> techList);

    List<Post> findAllByUserMemberPropensityTypeAndProjectStatusAndUserSnsIdIsNotAndTechStackList_TechInOrderByCreatedAtDesc(String memberPropensityType, ProjectStatus projectStatus, String snsId, List<Tech> techList);

    List<Post> findAllByTechStackList_TechInOrderByStartDate(List<Tech> tech);

    List<Post> findAllByTechStackList_TechInOrderByCreatedAtDesc(List<Tech> tech);

    List<Post> findAllByBookmarkList_User_SnsIdOrderByStartDate(String snsId);

    List<Post> findAllByBookmarkList_User_SnsIdOrderByCreatedAtDesc(String snsId);

    List<Post> findAllByUser(User user);
}
