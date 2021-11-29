package com.studycollaboproject.scope.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;

import static com.studycollaboproject.scope.model.QApplicant.applicant;

@RequiredArgsConstructor
public class ApplicantRepositoryExtensionImpl implements ApplicantRepositoryExtension {
    private final JPAQueryFactory queryFactory;

    public ApplicantRepositoryExtensionImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public boolean existsByUserIdAndPostId(Long userId, Long postId) {
        Integer fetchOne = queryFactory.selectOne()
                .from(applicant)
                .where(applicant.user.id.eq(userId)
                        .and(applicant.post.id.eq(postId)))
                .fetchFirst();

        return fetchOne != null;
    }
}
