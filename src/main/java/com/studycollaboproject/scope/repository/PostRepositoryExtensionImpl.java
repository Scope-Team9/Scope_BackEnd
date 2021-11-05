package com.studycollaboproject.scope.repository;

import com.querydsl.jpa.JPQLQuery;
import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.ProjectStatus;
import com.studycollaboproject.scope.model.QTechStack;
import com.studycollaboproject.scope.model.Tech;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.studycollaboproject.scope.model.QPost.post;
import static com.studycollaboproject.scope.model.QUser.user;

public class PostRepositoryExtensionImpl extends QuerydslRepositorySupport implements PostRepositoryExtension {
    public PostRepositoryExtensionImpl() {
        super(Post.class);
    }

    @Override
    public List<Post> findAllOrderByCreatedAt(Pageable pageable) {
        JPQLQuery<Post> query = from(post)
                .leftJoin(post.user, user).fetchJoin()
                .leftJoin(post.techStackList, QTechStack.techStack).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(post.createdAt.desc());
        return query.fetch();
    }

    @Override
    public List<Post> findAllByTechInOrderByCreatedAt(List<Tech> techList, Pageable pageable) {
        JPQLQuery<Post> query = from(post)
                .where(post.techStackList.any().tech.in(techList))
                .leftJoin(post.user, user).fetchJoin()
                .leftJoin(post.techStackList, QTechStack.techStack).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(post.createdAt.desc());
        return query.fetch();
    }

    @Override
    public List<Post> findAllByTechInOrderByStartDate(List<Tech> techList, Pageable pageable) {
        JPQLQuery<Post> query = from(post)
                .where(post.techStackList.any().tech.in(techList))
                .leftJoin(post.user, user).fetchJoin()
                .leftJoin(post.techStackList, QTechStack.techStack).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(post.startDate.asc());
        return query.fetch();
    }

    @Override
    public List<Post> findAllByBookmarkOrderByStartDate(String snsId, Pageable pageable) {
        JPQLQuery<Post> query = from(post)
                .where(post.bookmarkList.any().user.snsId.equalsIgnoreCase(snsId))
                .leftJoin(post.user, user).fetchJoin()
                .leftJoin(post.techStackList, QTechStack.techStack).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(post.startDate.asc());
        return query.fetch();
    }

    @Override
    public List<Post> findAllByBookmarkOrderByCreatedAt(String snsId, Pageable pageable) {
        JPQLQuery<Post> query = from(post)
                .where(post.bookmarkList.any().user.snsId.equalsIgnoreCase(snsId))
                .leftJoin(post.user, user).fetchJoin()
                .leftJoin(post.techStackList, QTechStack.techStack).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(post.createdAt.desc());
        return query.fetch();
    }

    @Override
    public List<Post> findAllByPropensityTypeOrderByStartDate(String propensity) {
        JPQLQuery<Post> query = from(post)
                .where(post.user.snsId.notEqualsIgnoreCase("unknown")
                        .and(post.projectStatus.eq(ProjectStatus.PROJECT_STATUS_RECRUITMENT))
                        .and(post.user.userPropensityType.eq(propensity)))
                .leftJoin(post.user, user).fetchJoin()
                .leftJoin(post.techStackList, QTechStack.techStack).fetchJoin()
                .orderBy(post.startDate.asc());
        return query.fetch();
    }

    @Override
    public List<Post> findAllByPropensityTypeOrderByCreatedAt(String propensity) {
        JPQLQuery<Post> query = from(post)
                .where(post.user.snsId.notEqualsIgnoreCase("unknown")
                        .and(post.projectStatus.eq(ProjectStatus.PROJECT_STATUS_RECRUITMENT))
                        .and(post.user.userPropensityType.eq(propensity)))
                .leftJoin(post.user, user).fetchJoin()
                .leftJoin(post.techStackList, QTechStack.techStack).fetchJoin()
                .orderBy(post.createdAt.desc());
        return query.fetch();
    }
}
