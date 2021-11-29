package com.studycollaboproject.scope.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.Team;
import com.studycollaboproject.scope.model.User;

import javax.persistence.EntityManager;
import java.util.List;

import static com.studycollaboproject.scope.model.QPost.post;
import static com.studycollaboproject.scope.model.QTeam.team;
import static com.studycollaboproject.scope.model.QUser.user;

public class TeamRepositoryExtensionImpl implements TeamRepositoryExtension {
    private final JPAQueryFactory queryFactory;

    public TeamRepositoryExtensionImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Team> findAllByUser(User targetUser) {
        return queryFactory.selectFrom(team)
                .where(team.user.id.eq(targetUser.getId()))
                .leftJoin(team.post, post).fetchJoin()
                .distinct()
                .fetch();
    }

    @Override
    public List<Team> findAssessmentTeamMember(Post post, List<Long> userIds) {
        return queryFactory.selectFrom(team)
                .where(team.post.id.eq(post.getId())
                        .and(team.user.id.in(userIds)))
                .leftJoin(team.user, user).fetchJoin()
                .distinct()
                .fetch();
    }

    @Override
    public List<Team> findAllByPostId(Long postId) {
        return queryFactory.selectFrom(team)
                .where(team.post.id.eq(postId))
                .leftJoin(team.user, user).fetchJoin()
                .leftJoin(team.post, post).fetchJoin()
                .distinct()
                .fetch();
    }

    @Override
    public boolean existsByPostIdAndUserSnsId(Long postId, String userSnsId) {
        Integer fetchOne = queryFactory.selectOne()
                .from(team)
                .where(team.user.snsId.eq(userSnsId)
                        .and(team.post.id.eq(postId)))
                .fetchFirst();

        return fetchOne != null;
    }
}
