package com.tr.triple.modules.review;


import com.tr.triple.modules.user.UserReview;
import com.tr.triple.modules.user.UserReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserReviewRepository userReviewRepository;

    public boolean isNewPlaceReview(Long placeId) {
        if(reviewRepository.findByPlaceId(placeId) == null)
            return true;
        else
            return false;
    }

    public List<Review> getReviews(Long userId) {
        return reviewRepository.search(userId);
    }

    public Review getReview(Long reviewId) {
        return reviewRepository.getById(reviewId);
    }

    public Long addReview(Long userId, String content, Long estimatePoint, Long placeId) throws Exception {
        Long reviewId = reviewRepository.save(Review.builder()
                                                .content(content)
                                                .placeId(placeId)
                                                .reviewPoint(estimatePoint).build()).getReviewId();
        if(reviewId != null) {
            userReviewRepository.save(UserReview.builder()
                                        .reviewId(reviewId)
                                        .userId(userId).build());
            return reviewId;
        }
        throw new Exception("not found reviewId");
    }

    public Long modReview(Long reviewId, String content, Long estimatePoint) throws Exception {
        Review review = reviewRepository.getById(reviewId);
        if(review != null) {
            Long oldReviewPoint = review.getReviewPoint();
            review.setContent(content);
            review.setReviewPoint(estimatePoint);
            reviewRepository.save(review);
            return oldReviewPoint;
        }
        throw new Exception("not found reviewId");
    }

    public Long deleteReview(Long userId, Long reviewId) throws Exception {
        Review review = reviewRepository.getById(reviewId);
        if(review != null) {
            Long oldReviewPoint = review.getReviewPoint();
            reviewRepository.delete(review);

            UserReview userReview = userReviewRepository.findByUserIdAndReviewId(userId, reviewId);
            if(userReview != null) {
                userReviewRepository.delete(userReview);
            }
            return oldReviewPoint;
        }
        throw new Exception("not found reviewId");
    }
}
