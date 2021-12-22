package com.studycollaboproject.scope.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.studycollaboproject.scope.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.studycollaboproject.scope.model.QPost.post;
import static com.studycollaboproject.scope.model.QUser.user;

@Repository
@RequiredArgsConstructor
public class PostRepositoryExtensionImpl implements PostRepositoryExtension {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Post> findAllOrderByModifiedAt() {
        return queryFactory.selectFrom(post)
//                .where(post.projectStatus.in(ProjectStatus.PROJECT_STATUS_RECRUITMENT, ProjectStatus.PROJECT_STATUS_INPROGRESS))
                .leftJoin(post.user, user).fetchJoin()
                .leftJoin(post.techStackList, QTechStack.techStack).fetchJoin()
                .orderBy(post.projectStatus.desc())
                .orderBy(post.modifiedAt.desc())
                .distinct()
                .fetch();
    }

    @Override
    public List<Post> findAllOrderByStartDate() {
        return queryFactory.selectFrom(post)
                .where(post.startDate.goe(LocalDate.now().atStartOfDay()))
//                        .and(post.projectStatus.in(ProjectStatus.PROJECT_STATUS_RECRUITMENT, ProjectStatus.PROJECT_STATUS_INPROGRESS)))
                .leftJoin(post.user, user).fetchJoin()
                .leftJoin(post.techStackList, QTechStack.techStack).fetchJoin()
                .orderBy(post.projectStatus.desc())
                .orderBy(post.startDate.asc())
                .distinct()
                .fetch();
    }

    @Override
    public List<Post> findAllByTechInOrderByModifiedAt(List<Tech> techList) {
        return queryFactory.selectFrom(post)
                .where(post.techStackList.any().tech.in(techList))
//                        .and(post.projectStatus.in(ProjectStatus.PROJECT_STATUS_RECRUITMENT, ProjectStatus.PROJECT_STATUS_INPROGRESS)))
                .leftJoin(post.user, user).fetchJoin()
                .leftJoin(post.techStackList, QTechStack.techStack).fetchJoin()
                .orderBy(post.projectStatus.desc())
                .orderBy(post.modifiedAt.desc())
                .distinct()
                .fetch();
    }

    @Override
    public List<Post> findAllByTechInOrderByStartDate(List<Tech> techList) {
        return queryFactory.selectFrom(post)
                .where(post.techStackList.any().tech.in(techList)
                        .and(post.startDate.goe(LocalDate.now().atStartOfDay())))
//                        .and(post.projectStatus.in(ProjectStatus.PROJECT_STATUS_RECRUITMENT, ProjectStatus.PROJECT_STATUS_INPROGRESS)))
                .leftJoin(post.user, user).fetchJoin()
                .leftJoin(post.techStackList, QTechStack.techStack).fetchJoin()
                .orderBy(post.projectStatus.desc())
                .orderBy(post.startDate.asc())
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
                .orderBy(post.projectStatus.desc())
                .orderBy(post.startDate.asc())
                .distinct()
                .fetch();
    }

    @Override
    public List<Post> findAllByBookmarkOrderByModifiedAt(String snsId) {
        return queryFactory.selectFrom(post)
                .where(post.bookmarkList.any().user.snsId.equalsIgnoreCase(snsId))
                .leftJoin(post.user, user).fetchJoin()
                .leftJoin(post.techStackList, QTechStack.techStack).fetchJoin()
                .orderBy(post.projectStatus.desc())
                .orderBy(post.modifiedAt.desc())
                .distinct()
                .fetch();
    }

    @Override
    public List<Post> findByInPostIdOrderByModifiedAt(List<Long> postId) {
        return queryFactory.selectFrom(post)
                .where(post.id.in(postId))
                .leftJoin(post.user, user).fetchJoin()
                .leftJoin(post.techStackList, QTechStack.techStack).fetchJoin()
                .orderBy(post.projectStatus.desc())
                .orderBy(post.modifiedAt.desc())
                .distinct()
                .fetch();
    }

    @Override
    public List<Post> findByInPostIdOrderByStartDate(List<Long> postId) {
        return queryFactory.selectFrom(post)
                .where(post.id.in(postId))
                .leftJoin(post.user, user).fetchJoin()
                .leftJoin(post.techStackList, QTechStack.techStack).fetchJoin()
                .orderBy(post.projectStatus.desc())
                .orderBy(post.startDate.asc())
                .distinct()
                .fetch();
    }

    @Override
    public List<Long> findAllPostIdListByPropensityType(String propensity, List<Tech> techList, String snsId, Pageable pageable) {
        List<Post> fetch = queryFactory.selectFrom(post)
                .where(post.user.snsId.notEqualsIgnoreCase("unknown")
                        .and(post.user.snsId.notEqualsIgnoreCase(snsId))
                        .and(post.projectStatus.eq(ProjectStatus.PROJECT_STATUS_RECRUITMENT))
                        .and(post.techStackList.any().tech.in(techList))
                        .and(post.user.userPropensityType.eq(propensity)))
                .orderBy(post.modifiedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return fetch.stream().map(Post::getId).collect(Collectors.toList());
    }

    @Override
    public List<Long> findAllPostIdListByBookmark(String snsId, Pageable pageable) {
        List<Post> fetch = queryFactory.selectFrom(post)
                .where(post.bookmarkList.any().user.snsId.equalsIgnoreCase(snsId))
                .orderBy(post.projectStatus.desc())
                .orderBy(post.modifiedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return fetch.stream().map(Post::getId).collect(Collectors.toList());
    }

    @Override
    public List<Long> findAllPostIdListOrderByStartDate(Pageable pageable) {
        List<Post> fetch = queryFactory.selectFrom(post)
                .where(post.startDate.goe(LocalDate.now().atStartOfDay()))
                .orderBy(post.projectStatus.desc())
                .orderBy(post.startDate.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return fetch.stream().map(Post::getId).collect(Collectors.toList());
    }

    @Override
    public List<Long> findAllPostIdListOrderByModifiedAt(Pageable pageable) {
        List<Post> fetch = queryFactory.selectFrom(post)
                .orderBy(post.projectStatus.desc())
                .orderBy(post.modifiedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return fetch.stream().map(Post::getId).collect(Collectors.toList());
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
                .orderBy(post.projectStatus.desc())
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
                .orderBy(post.projectStatus.desc())
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
                .orderBy(post.projectStatus.desc())
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
                .orderBy(post.projectStatus.desc())
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
                .orderBy(post.projectStatus.desc())
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
                .orderBy(post.projectStatus.desc())
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
                .orderBy(post.projectStatus.desc())
                .orderBy(post.startDate.asc())
                .distinct()
                .fetch();
    }
}
