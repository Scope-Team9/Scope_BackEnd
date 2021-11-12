package com.studycollaboproject.scope.repository;

import com.querydsl.jpa.JPQLQuery;
import com.studycollaboproject.scope.model.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.studycollaboproject.scope.model.QPost.post;
import static com.studycollaboproject.scope.model.QUser.user;

public class PostRepositoryExtensionImpl extends QuerydslRepositorySupport implements PostRepositoryExtension {
    public PostRepositoryExtensionImpl() {
        super(Post.class);
    }


    @Override
    public List<Post> findAllByTechInOrderByCreatedAt(List<Tech> techList) {
        JPQLQuery<Post> query = from(post)
                .where(post.techStackList.any().tech.in(techList))
                .leftJoin(post.user, user).fetchJoin()
                .leftJoin(post.techStackList, QTechStack.techStack).fetchJoin()
                .orderBy(post.createdAt.desc())
                .orderBy(post.projectStatus.desc())
                .distinct();
        return query.fetch();
    }

    @Override
    public List<Post> findAllByTechInOrderByStartDate(List<Tech> techList) {
        JPQLQuery<Post> query = from(post)
                .where(post.techStackList.any().tech.in(techList))
                .leftJoin(post.user, user).fetchJoin()
                .leftJoin(post.techStackList, QTechStack.techStack).fetchJoin()
                .orderBy(post.startDate.asc())
                .orderBy(post.projectStatus.desc())
                .distinct();
        return query.fetch();
    }

    @Override
    public List<Post> findAllByBookmarkOrderByStartDate(String snsId) {
        JPQLQuery<Post> query = from(post)
                .where(post.bookmarkList.any().user.snsId.equalsIgnoreCase(snsId))
                .leftJoin(post.user, user).fetchJoin()
                .leftJoin(post.techStackList, QTechStack.techStack).fetchJoin()
                .orderBy(post.startDate.asc())
                .orderBy(post.projectStatus.desc())
                .distinct();
        return query.fetch();
    }

    @Override
    public List<Post> findAllByBookmarkOrderByCreatedAt(String snsId) {
        JPQLQuery<Post> query = from(post)
                .where(post.bookmarkList.any().user.snsId.equalsIgnoreCase(snsId))
                .leftJoin(post.user, user).fetchJoin()
                .leftJoin(post.techStackList, QTechStack.techStack).fetchJoin()
                .orderBy(post.createdAt.desc())
                .orderBy(post.projectStatus.desc())
                .distinct();
        return query.fetch();
    }

    @Override
    public List<Post> findAllByPropensityTypeOrderByStartDate(String propensity, List<Tech> techList) {
        JPQLQuery<Post> query = from(post)
                .where(post.user.snsId.notEqualsIgnoreCase("unknown")
                        .and(post.projectStatus.eq(ProjectStatus.PROJECT_STATUS_RECRUITMENT))
                        .and(post.techStackList.any().tech.in(techList))
                        .and(post.user.userPropensityType.eq(propensity)))
                .leftJoin(post.user, user).fetchJoin()
                .leftJoin(post.techStackList, QTechStack.techStack).fetchJoin()
                .orderBy(post.startDate.asc())
                .distinct();
        return query.fetch();
    }

    @Override
    public List<Post> findAllByPropensityTypeOrderByCreatedAt(String propensity, List<Tech> techList) {
        JPQLQuery<Post> query = from(post)
                .where(post.user.snsId.notEqualsIgnoreCase("unknown")
                        .and(post.projectStatus.eq(ProjectStatus.PROJECT_STATUS_RECRUITMENT))
                        .and(post.techStackList.any().tech.in(techList))
                        .and(post.user.userPropensityType.eq(propensity)))
                .leftJoin(post.user, user).fetchJoin()
                .leftJoin(post.techStackList, QTechStack.techStack).fetchJoin()
                .orderBy(post.createdAt.desc())
                .distinct();
        return query.fetch();
    }

    @Override
    public List<Post> findAllByUserSnsId(String snsId) {
        JPQLQuery<Post> query = from(post)
                .where(post.teamList.any().user.snsId.eq(snsId)
                        .or(post.applicantList.any().user.snsId.eq(snsId)))
                .leftJoin(post.user, user).fetchJoin()
                .leftJoin(post.techStackList, QTechStack.techStack).fetchJoin()
                .orderBy(post.createdAt.desc())
                .distinct();
        return query.fetch();
    }

    @Override
    public List<Post> findAllBookmarkByUserSnsId(String snsId) {
        JPQLQuery<Post> query = from(post)
                .where(post.bookmarkList.any().user.snsId.eq(snsId))
                .leftJoin(post.user, user).fetchJoin()
                .leftJoin(post.techStackList, QTechStack.techStack).fetchJoin()
                .orderBy(post.createdAt.desc())
                .distinct();
        return query.fetch();
    }
}
