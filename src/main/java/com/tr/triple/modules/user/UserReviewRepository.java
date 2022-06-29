package com.tr.triple.modules.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReviewRepository extends JpaRepository<UserReview, Long> {
    UserReview findByUserIdAndReviewId(Long userId, Long reviewId);
    UserReview findByUserId(Long userId);
}
