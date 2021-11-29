package com.studycollaboproject.scope.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.studycollaboproject.scope.model.*;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.studycollaboproject.scope.model.QPost.post;
import static com.studycollaboproject.scope.model.QUser.user;

public class PostRepositoryExtensionImpl implements PostRepositoryExtension {
    private final JPAQueryFactory queryFactory;

    public PostRepositoryExtensionImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Post> findAllOrderByModifiedAt() {
        return queryFactory.selectFrom(post)
                .where(post.projectStatus.in(ProjectStatus.PROJECT_STATUS_RECRUITMENT, ProjectStatus.PROJECT_STATUS_INPROGRESS))
                .leftJoin(post.user, user).fetchJoin()
                .leftJoin(post.techStackList, QTechStack.techStack).fetchJoin()
                .orderBy(post.modifiedAt.desc())
                .orderBy(post.projectStatus.desc())
                .distinct()
                .fetch();
    }

    @Override
    public List<Post> findAllOrderByStartDate() {
        return queryFactory.selectFrom(post)
                .where(post.startDate.goe(LocalDate.now().atStartOfDay())
                        .and(post.projectStatus.in(ProjectStatus.PROJECT_STATUS_RECRUITMENT, ProjectStatus.PROJECT_STATUS_INPROGRESS)))
                .leftJoin(post.user, user).fetchJoin()
                .leftJoin(post.techStackList, QTechStack.techStack).fetchJoin()
                .orderBy(post.startDate.asc())
                .orderBy(post.projectStatus.desc())
                .distinct()
                .fetch();
    }

    @Override
    public List<Post> findAllByTechInOrderByModifiedAt(List<Tech> techList) {
        return queryFactory.selectFrom(post)
                .where(post.techStackList.any().tech.in(techList)
                        .and(post.projectStatus.in(ProjectStatus.PROJECT_STATUS_RECRUITMENT, ProjectStatus.PROJECT_STATUS_INPROGRESS)))
                .leftJoin(post.user, user).fetchJoin()
                .leftJoin(post.techStackList, QTechStack.techStack).fetchJoin()
                .orderBy(post.modifiedAt.desc())
                .orderBy(post.projectStatus.desc())
                .distinct()
                .fetch();
    }

    @Override
    public List<Post> findAllByTechInOrderByStartDate(List<Tech> techList) {
        return queryFactory.selectFrom(post)
                .where(post.techStackList.any().tech.in(techList)
                        .and(post.startDate.goe(LocalDate.now().atStartOfDay()))
                        .and(post.projectStatus.in(ProjectStatus.PROJECT_STATUS_RECRUITMENT, ProjectStatus.PROJECT_STATUS_INPROGRESS)))
                .leftJoin(post.user, user).fetchJoin()
                .leftJoin(post.techStackList, QTechStack.techStack).fetchJoin()
                .orderBy(post.startDate.asc())
                .orderBy(post.projectStatus.desc())
                .distinct()
                .fetch();
    }

    @Override
    public List<Post> findAllByBookmarkOrderByStartDate(String snsId) {
        return queryFactory.selectFrom(post)
                .where(post.bookmarkList.any().user.snsId.equalsIgnoreCase(snsId)
                        .and(post.startDate.goe(LocalDateTime.now())))
                .leftJoin(post.user, user).fetchJoin()
                .leftJoin(post.techStackList, QTechStack.techStack).fetchJoin()
                .orderBy(post.startDate.asc())
                .orderBy(post.projectStatus.desc())
                .distinct()
                .fetch();
    }

    @Override
    public List<Post> findAllByBookmarkOrderByModifiedAt(String snsId) {
        return queryFactory.selectFrom(post)
                .where(post.bookmarkList.any().user.snsId.equalsIgnoreCase(snsId))
                .leftJoin(post.user, user).fetchJoin()
                .leftJoin(post.techStackList, QTechStack.techStack).fetchJoin()
                .orderBy(post.modifiedAt.desc())
                .orderBy(post.projectStatus.desc())
                .distinct()
                .fetch();
    }

    @Override
    public List<Post> findAllByPropensityTypeOrderByStartDate(String propensity, List<Tech> techList, String snsId) {
        return queryFactory.selectFrom(post)
                .where(post.user.snsId.notEqualsIgnoreCase("unknown")
                        .and(post.user.snsId.notEqualsIgnoreCase(snsId))
                        .and(post.projectStatus.eq(ProjectStatus.PROJECT_STATUS_RECRUITMENT))
                        .and(post.startDate.goe(LocalDateTime.now()))
                        .and(post.techStackList.any().tech.in(techList))
                        .and(post.user.userPropensityType.eq(propensity)))
                .leftJoin(post.user, user).fetchJoin()
                .leftJoin(post.techStackList, QTechStack.techStack).fetchJoin()
                .orderBy(post.startDate.asc())
                .distinct()
                .fetch();
    }

    @Override
    public List<Post> findAllByPropensityTypeOrderByModifiedAt(String propensity, List<Tech> techList, String snsId) {
        return queryFactory.selectFrom(post)
                .where(post.user.snsId.notEqualsIgnoreCase("unknown")
                        .and(post.user.snsId.notEqualsIgnoreCase(snsId))
                        .and(post.projectStatus.eq(ProjectStatus.PROJECT_STATUS_RECRUITMENT))
                        .and(post.techStackList.any().tech.in(techList))
                        .and(post.user.userPropensityType.eq(propensity)))
                .leftJoin(post.user, user).fetchJoin()
                .leftJoin(post.techStackList, QTechStack.techStack).fetchJoin()
                .orderBy(post.modifiedAt.desc())
                .distinct()
                .fetch();
    }

    @Override
    public List<Post> findMemberPostByUserSnsId(String snsId) {
        return queryFactory.selectFrom(post)
                .where(post.teamList.any().user.snsId.eq(snsId))
                .leftJoin(post.user, user).fetchJoin()
                .leftJoin(post.techStackList, QTechStack.techStack).fetchJoin()
                .leftJoin(post.teamList, QTeam.team).fetchJoin()
                .orderBy(post.modifiedAt.desc())
                .distinct()
                .fetch();
    }

    @Override
    public List<Post> findReadyPostByUserSnsId(String snsId) {
        return queryFactory.selectFrom(post)
                .where(post.applicantList.any().user.snsId.eq(snsId))
                .leftJoin(post.user, user).fetchJoin()
                .leftJoin(post.techStackList, QTechStack.techStack).fetchJoin()
                .leftJoin(post.teamList, QTeam.team).fetchJoin()
                .orderBy(post.modifiedAt.desc())
                .distinct()
                .fetch();
    }

    @Override
    public List<Post> findAllBookmarkByUserSnsId(String snsId) {
        return queryFactory.selectFrom(post)
                .where(post.bookmarkList.any().user.snsId.eq(snsId))
                .leftJoin(post.user, user).fetchJoin()
                .leftJoin(post.techStackList, QTechStack.techStack).fetchJoin()
                .leftJoin(post.teamList, QTeam.team).fetchJoin()
                .orderBy(post.modifiedAt.desc())
                .distinct()
                .fetch();
    }

    @Override
    public List<Post> findAllByKeywordOrderByModifiedAt(String keyword) {
        return queryFactory.selectFrom(post)
                .where(post.title.contains(keyword)
                        .or(post.contents.contains(keyword)))
                .leftJoin(post.user, user).fetchJoin()
                .leftJoin(post.techStackList, QTechStack.techStack).fetchJoin()
                .orderBy(post.modifiedAt.desc())
                .distinct()
                .fetch();
    }

    @Override
    public List<Post> findAllByKeywordOrderByStartDate(String keyword) {
        return queryFactory.selectFrom(post)
                .where(post.title.contains(keyword)
                        .or(post.contents.contains(keyword)))
                .where(post.startDate.goe(LocalDateTime.now()))
                .leftJoin(post.user, user).fetchJoin()
                .leftJoin(post.techStackList, QTechStack.techStack).fetchJoin()
                .orderBy(post.startDate.asc())
                .distinct()
                .fetch();
    }
}
