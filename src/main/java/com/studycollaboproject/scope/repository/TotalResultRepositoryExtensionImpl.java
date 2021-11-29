package com.studycollaboproject.scope.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

import static com.studycollaboproject.scope.model.QPost.post;
import static com.studycollaboproject.scope.model.QTeam.team;
import static com.studycollaboproject.scope.model.QTotalResult.totalResult;

@RequiredArgsConstructor
public class TotalResultRepositoryExtensionImpl implements TotalResultRepositoryExtension {
    private final JPAQueryFactory queryFactory;

    @Override
    public void updateAssessmentResult(String userType, List<String> memberTypes) {
        queryFactory.update(totalResult)
                .set(totalResult.result, totalResult.result.add(1))
                .where(totalResult.userType.eq(userType)
                        .and(totalResult.memberType.in(memberTypes)))
                .execute();
    }
}