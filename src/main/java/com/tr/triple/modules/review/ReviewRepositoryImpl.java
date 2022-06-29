package com.tr.triple.modules.review;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tr.triple.modules.user.QUserReview;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Review> search(Long userId) {
        QReview review = QReview.review;
        QUserReview userReview = QUserReview.userReview;
        QueryResults<Review> queryResult = queryFactory
                .select(review)
                .from(review)
                .leftJoin(userReview).on(review.reviewId.eq(userReview.reviewId))
                .where(userReview.userId.eq(userId))
                .fetchResults();
        return queryResult.getResults();
    }
}
