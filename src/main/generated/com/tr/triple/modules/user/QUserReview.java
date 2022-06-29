package com.tr.triple.modules.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserReview is a Querydsl query type for UserReview
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserReview extends EntityPathBase<UserReview> {

    private static final long serialVersionUID = 1697623922L;

    public static final QUserReview userReview = new QUserReview("userReview");

    public final NumberPath<Long> reviewId = createNumber("reviewId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final NumberPath<Long> userReviewId = createNumber("userReviewId", Long.class);

    public QUserReview(String variable) {
        super(UserReview.class, forVariable(variable));
    }

    public QUserReview(Path<? extends UserReview> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserReview(PathMetadata metadata) {
        super(UserReview.class, metadata);
    }

}

